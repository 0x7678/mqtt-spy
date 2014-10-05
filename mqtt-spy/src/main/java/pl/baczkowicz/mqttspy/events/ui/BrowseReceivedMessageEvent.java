package pl.baczkowicz.mqttspy.events.ui;

import pl.baczkowicz.mqttspy.connectivity.MqttContent;
import pl.baczkowicz.mqttspy.storage.MessageListWithObservableTopicSummary;

public class BrowseReceivedMessageEvent implements MqttSpyUIEvent
{
	private final MqttContent message;
	
	private final MessageListWithObservableTopicSummary store;

	public BrowseReceivedMessageEvent(final MessageListWithObservableTopicSummary store, final MqttContent message)
	{
		this.store = store;
		this.message = message;
	}

	public MqttContent getMessage()
	{
		return message;
	}

	public MessageListWithObservableTopicSummary getList()
	{
		return store;
	}
}
