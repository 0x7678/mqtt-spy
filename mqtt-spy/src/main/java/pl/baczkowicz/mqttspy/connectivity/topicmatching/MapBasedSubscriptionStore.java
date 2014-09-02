package pl.baczkowicz.mqttspy.connectivity.topicmatching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dna.mqtt.moquette.messaging.spi.IPersistentSubscriptionStore;
import org.dna.mqtt.moquette.messaging.spi.impl.subscriptions.Subscription;

public class MapBasedSubscriptionStore implements IPersistentSubscriptionStore
{
	private final Map<String, List<Subscription>> subscriptions = new HashMap<String, List<Subscription>>();

	public void addNewSubscription(Subscription newSubscription, String clientID)
	{
		List<Subscription> clientSubscriptions = subscriptions.get(clientID);
		
		if (clientSubscriptions == null)
		{
			clientSubscriptions = new ArrayList<Subscription>();
			subscriptions.put(clientID, clientSubscriptions);
		}
		
		clientSubscriptions.add(newSubscription);
	}

	public void removeAllSubscriptions(String clientID)
	{
		subscriptions.remove(clientID);
	}

	public List<Subscription> retrieveAllSubscriptions()
	{
		List<Subscription> list = new ArrayList<Subscription>();
		
		for (final List<Subscription>clientSubscriptions : subscriptions.values())
		{
			list.addAll(clientSubscriptions);
		}
		
		return list;
	}

}
