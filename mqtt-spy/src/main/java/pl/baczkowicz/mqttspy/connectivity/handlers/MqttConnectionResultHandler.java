package pl.baczkowicz.mqttspy.connectivity.handlers;

import javafx.application.Platform;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.baczkowicz.mqttspy.connectivity.MqttConnection;
import pl.baczkowicz.mqttspy.connectivity.events.MqttConnectionAttemptFailureEvent;
import pl.baczkowicz.mqttspy.connectivity.events.MqttConnectionAttemptSuccessEvent;

public class MqttConnectionResultHandler implements IMqttActionListener
{
	private final static Logger logger = LoggerFactory.getLogger(MqttConnectionResultHandler.class);

	public void onSuccess(IMqttToken asyncActionToken)
	{
		final MqttConnection connection = (MqttConnection) asyncActionToken.getUserContext();
		Platform.runLater(new MqttEventHandler(new MqttConnectionAttemptSuccessEvent(connection)));
		logger.info(connection.getProperties().getName() + " connected");
	}

	public void onFailure(IMqttToken asyncActionToken, Throwable exception)
	{
		final MqttConnection connection = (MqttConnection) asyncActionToken.getUserContext();
		Platform.runLater(new MqttEventHandler(new MqttConnectionAttemptFailureEvent(connection, exception)));
		logger.warn("Connecting to " + connection.getProperties().getName() + " failed", exception);
	}
}
