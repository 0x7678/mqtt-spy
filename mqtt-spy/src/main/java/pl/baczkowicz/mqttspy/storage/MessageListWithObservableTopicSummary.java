/***********************************************************************************
 * 
 * Copyright (c) 2014 Kamil Baczkowicz
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 
 *    Kamil Baczkowicz - initial API and implementation and/or initial documentation
 *    
 */
package pl.baczkowicz.mqttspy.storage;

import pl.baczkowicz.mqttspy.configuration.generated.FormatterDetails;
import pl.baczkowicz.mqttspy.connectivity.MqttContent;
import pl.baczkowicz.mqttspy.storage.summary.ObservableTopicSummary;

/**
 * Message list with observable topic summary.
 */
public class MessageListWithObservableTopicSummary extends MessageList
{
	private final ObservableTopicSummary topicSummary;
	
	public MessageListWithObservableTopicSummary(int preferredSize, int maxSize, String name, FormatterDetails messageFormat)
	{
		super(preferredSize, maxSize, name);
				
		this.topicSummary = new ObservableTopicSummary(name);
		this.topicSummary.setFormatter(messageFormat);
	}

	public ObservableTopicSummary getTopicSummary()
	{
		return topicSummary;
	}
	
	public MqttContent add(final MqttContent message)
	{
		final MqttContent removed = super.add(message);
		
		if (removed != null)
		{
			topicSummary.decreaseCount(removed);
		}
		topicSummary.increaseCount(message);
		
		return removed;
	}
	
	public MqttContent remove(final int index)
	{
		final MqttContent removed = super.remove(index);
		
		topicSummary.decreaseCount(removed);
		
		return removed;
	}
}
