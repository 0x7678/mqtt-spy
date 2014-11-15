package pl.baczkowicz.mqttspy.connectivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.baczkowicz.mqttspy.events.EventManager;
import pl.baczkowicz.mqttspy.events.ui.MqttSpyUIEvent;
import pl.baczkowicz.mqttspy.stats.StatisticsManager;
import pl.baczkowicz.mqttspy.storage.ManagedMessageStoreWithFiltering;
import pl.baczkowicz.mqttspy.ui.properties.RuntimeConnectionProperties;

public class MqttAsyncConnection extends BaseMqttConnection
{
	final static Logger logger = LoggerFactory.getLogger(MqttAsyncConnection.class);

	private final Map<String, MqttSubscription> subscriptions = new HashMap<String, MqttSubscription>();
	
	private int lastUsedSubscriptionId = 0;

	private final RuntimeConnectionProperties properties;
	
	private boolean isOpened;
	
	private boolean isOpening;
	
	private final ManagedMessageStoreWithFiltering store;

	/** Maximum number of messages to store for this connection in each message store. */
	private int preferredStoreSize;

	private StatisticsManager statisticsManager;

	private EventManager eventManager;

	public MqttAsyncConnection(final RuntimeConnectionProperties properties, 
			final MqttConnectionStatus status, final EventManager eventManager, final Queue<MqttSpyUIEvent> uiEventQueue)
	{ 
		super(properties);
		
		// Max size is double the preferred size
		store = new ManagedMessageStoreWithFiltering(properties.getName(), 
				properties.getConfiguredProperties().getMinMessagesStoredPerTopic(), 
				properties.getMaxMessagesStored(), 
				properties.getMaxMessagesStored() * 2, 
				uiEventQueue, eventManager);
		
		this.setPreferredStoreSize(properties.getMaxMessagesStored());
		this.properties = properties;
		this.eventManager = eventManager;
		// this.eventManager = eventManager;
		setConnectionStatus(status);

		// Manage subscriptions, based on moquette
//		subscriptionsStore = new SubscriptionsStore();
//		subscriptionsStore.init(new MapBasedSubscriptionStore());
	}

	public void messageReceived(final MqttContent message)
	{		
		final List<String> matchingSubscriptionTopics = getMatchingSubscriptions(message);
				
		final List<String> matchingActiveSubscriptionTopics = new ArrayList<String>();
		
		// For all found subscriptions
		for (final String matchingSubscription : matchingSubscriptionTopics)
		{						
			// Get the mqtt-spy's subscription object
			final MqttSubscription mqttSubscription = subscriptions.get(matchingSubscription);

			// If a match has been found, and the subscription is active
			if (mqttSubscription != null && (mqttSubscription.isSubscribing() || mqttSubscription.isActive()))
			{
				matchingActiveSubscriptionTopics.add(matchingSubscription);
				
				// Set subscription reference on the message
				message.setSubscription(mqttSubscription);
				
				// Pass the message for subscription handling
				mqttSubscription.messageReceived(message);
			}
		}		
		
		statisticsManager.messageReceived(getId(), matchingActiveSubscriptionTopics);

		// Pass the message for connection (all subscriptions) handling
		store.messageReceived(message);
	}
	
	public boolean publish(final String publicationTopic, final String data, final int qos, final boolean retained)
	{
		if (canPublish())
		{
			try
			{
				logger.info("Publishing message on topic \"" + publicationTopic + "\". Payload = \"" + data + "\"");
				client.publish(publicationTopic, data.getBytes(), qos, retained);
				
				logger.trace("Published message on topic \"" + publicationTopic + "\". Payload = \"" + data + "\"");
				statisticsManager.messagePublished(getId(), publicationTopic);
				
				return true;
			}
			catch (MqttException e)
			{
				logger.error("Cannot publish message on " + publicationTopic, e);
			}
		}
		else
		{
			logger.warn("No connection established yet...");
		}
		
		return false;
	}

	public void connectionLost(Throwable cause)
	{
		super.connectionLost(cause);
		unsubscribeAll();
	}

	public void addSubscription(final MqttSubscription subscription)
	{
		// Add it to the store if it hasn't been created before
		if (subscriptions.put(subscription.getTopic(), subscription) == null)
		{
			subscription.setId(lastUsedSubscriptionId++);	
			addSubscriptionToStore(subscription.getTopic());
//			subscriptionsStore.add(new Subscription(properties.getClientId(), subscription
//					.getTopic(), QOSType.MOST_ONE, true));
		}
	}

	public boolean resubscribeAll()
	{
		for (final MqttSubscription subscription : subscriptions.values())
		{
			resubscribe(subscription);
		}

		return true;
	}

	public boolean resubscribe(final MqttSubscription subscription)
	{
		return subscribe(subscription);
	}

	public boolean subscribe(final MqttSubscription subscription)
	{
		// If already active, simply ignore
		if (subscription.isActive())
		{
			return false;
		}

		if (!client.isConnected())
		{
			logger.info("Client not connected");
			return false;
		}

		try
		{			
			addSubscription(subscription);
			
			// Retained messages can be received very quickly, even so quickly we still haven't set the subscription's state to active
			subscription.setSubscribing(true);
			
			logger.debug("Subscribing to " + subscription.getTopic());			
			client.subscribe(subscription.getTopic(), subscription.getQos());			
			logger.info("Subscribed to " + subscription.getTopic());
			
			StatisticsManager.newSubscription();
			subscription.setActive(true);
			subscription.setSubscribing(false);
			
			logger.trace("Subscription " + subscription.getTopic() + " is active = "
					+ subscription.isActive());

			return true;
		}
		catch (MqttException e)
		{
			subscription.setSubscribing(false);
			logger.error("Cannot subscribe to " + subscription.getTopic(), e);
			removeSubscription(subscription);
			return false;
		}
	}

	public boolean unsubscribeAll()
	{
		for (final MqttSubscription subscription : subscriptions.values())
		{
			unsubscribe(subscription);
		}

		return true;
	}

	public boolean unsubscribe(final MqttSubscription subscription)
	{
		// If already unsubscribed, ignore
		if (!subscription.isActive())
		{
			return false;
		}

		logger.debug("Unsubscribing from " + subscription.getTopic());
		try
		{
			if (client.isConnected())
			{
				client.unsubscribe(subscription.getTopic());
			}
			logger.info("Unsubscribed from " + subscription.getTopic());
			return true;
		}
		catch (MqttException e)
		{
			logger.error("Cannot unsubscribe from " + subscription.getTopic(), e);

			return false;
		}
		finally
		{
			// As this is in 'finally', will be executed before the returns
			subscription.setActive(false);
			logger.trace("Subscription " + subscription.getTopic() + " is active = "
					+ subscription.isActive());

		}
	}

	public boolean unsubscribeAndRemove(final MqttSubscription subscription)
	{
		final boolean unsubscribed = unsubscribe(subscription);
		removeSubscription(subscription);
		logger.info("Subscription " + subscription.getTopic() + " removed");
		return unsubscribed;
	}

	public void removeSubscription(final MqttSubscription subscription)
	{
		subscriptions.remove(subscription.getTopic());
		removeSubscriptionFromStore(subscription.getTopic());
//		subscriptionsStore.removeSubscription(subscription.getTopic(), getProperties()
//				.getClientId());
	}

	public void setConnectionStatus(MqttConnectionStatus connectionStatus)
	{
		super.setConnectionStatus(connectionStatus);
		// this.connectionStatus = connectionStatus;
		eventManager.notifyConnectionStatusChanged(this);
	}

	public RuntimeConnectionProperties getProperties()
	{
		return properties;
	}

	public Map<String, MqttSubscription> getSubscriptions()
	{
		return subscriptions;
	}

	public MqttAsyncClient getClient()
	{
		return client;
	}

	public void setClient(MqttAsyncClient client)
	{
		this.client = client;
	}

	public int getPreferredStoreSize()
	{
		return preferredStoreSize;
	}

	public void setPreferredStoreSize(int preferredStoreSize)
	{
		this.preferredStoreSize = preferredStoreSize;
	}
	
	public int getId()
	{
		return properties.getId();
	}

	public boolean isOpened()
	{
		return isOpened;
	}

	public void setOpened(boolean isOpened)
	{
		this.isOpened = isOpened;
		
		eventManager.notifyConnectionStatusChanged(this);
	}
	
	public int getLastUsedSubscriptionId()
	{
		return lastUsedSubscriptionId;
	}

	public boolean isOpening()
	{
		return isOpening;
	}

	public void setOpening(boolean isOpening)
	{
		eventManager.notifyConnectionStatusChanged(this);
		
		this.isOpening = isOpening;
	}

	public ManagedMessageStoreWithFiltering getStore()
	{
		return store;
	}

	public String getName()
	{
		return store.getName();
	}	

	public void setStatisticsManager(StatisticsManager statisticsManager)
	{
		this.statisticsManager = statisticsManager;
	}
}