package pl.baczkowicz.mqttspy.connectivity.handlers;

import javafx.application.Platform;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.baczkowicz.mqttspy.connectivity.MqttAsyncConnection;
import pl.baczkowicz.mqttspy.events.connectivity.MqttConnectionAttemptFailureEvent;
import pl.baczkowicz.mqttspy.events.connectivity.MqttConnectionAttemptSuccessEvent;
import pl.baczkowicz.mqttspy.stats.StatisticsManager;

public class MqttConnectionResultHandler implements IMqttActionListener
{
	private final static Logger logger = LoggerFactory.getLogger(MqttConnectionResultHandler.class);

	public void onSuccess(IMqttToken asyncActionToken)
	{
		final MqttAsyncConnection connection = (MqttAsyncConnection) asyncActionToken.getUserContext();
		StatisticsManager.newConnection();
		Platform.runLater(new MqttEventHandler(new MqttConnectionAttemptSuccessEvent(connection)));
		logger.info(connection.getProperties().getName() + " connected");
	}

	public void onFailure(IMqttToken asyncActionToken, Throwable exception)
	{
		final MqttAsyncConnection connection = (MqttAsyncConnection) asyncActionToken.getUserContext();
		Platform.runLater(new MqttEventHandler(new MqttConnectionAttemptFailureEvent(connection, exception)));
		logger.warn("Connecting to " + connection.getProperties().getName() + " failed", exception);
	}
}
