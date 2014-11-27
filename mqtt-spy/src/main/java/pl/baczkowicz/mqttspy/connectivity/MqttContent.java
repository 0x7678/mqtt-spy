package pl.baczkowicz.mqttspy.connectivity;

import java.util.Date;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import pl.baczkowicz.mqttspy.configuration.generated.FormatterDetails;
import pl.baczkowicz.mqttspy.messages.ReceivedMqttMessage;
import pl.baczkowicz.mqttspy.ui.utils.FormattingUtils;

public class MqttContent extends ReceivedMqttMessage
{
	private MqttSubscription subscription;
	
	private FormatterDetails lastUsedFormatter;
	
	private String formattedPayload;
	
	public MqttContent(final MqttContent message)
	{
		super(message.getId(), message.getTopic(), copyMqttMessage(message.getMessage()));
		this.formattedPayload = message.getFormattedPayload();
		this.lastUsedFormatter = message.getLastUsedFormatter();
		this.subscription = message.getSubscription();
	}
	
	public FormatterDetails getLastUsedFormatter()
	{
		return lastUsedFormatter;
	}

	public MqttContent(final long id, final String topic, final MqttMessage message)
	{
		super(id, topic, message);
		this.formattedPayload = new String(message.getPayload());
	}
	
	public MqttContent(final long id, final String topic, final MqttMessage message, final Date date)
	{
		super(id, topic, message, date);
		this.formattedPayload = new String(message.getPayload());
	}
	
	public MqttContent(final ReceivedMqttMessage message)
	{
		super(message.getId(), message.getTopic(), message.getMessage(), message.getDate());
		this.formattedPayload = new String(message.getMessage().getPayload());
	}

	public MqttSubscription getSubscription()
	{
		return subscription;
	}

	public void setSubscription(MqttSubscription subscription)
	{
		this.subscription = subscription;
	}

	public void format(final FormatterDetails formatter)
	{
		if (formatter == null)
		{
			formattedPayload = new String(getMessage().getPayload());
		}		
		else if (!formatter.equals(lastUsedFormatter))
		{
			lastUsedFormatter = formatter;
			formattedPayload = FormattingUtils.convertText(formatter, new String(getMessage().getPayload()));
		}
	}
	
	public String getFormattedPayload(final FormatterDetails formatter)
	{
		format(formatter);
		
		return formattedPayload;
	}
	
	public String getFormattedPayload()
	{
		return formattedPayload;
	}
}
