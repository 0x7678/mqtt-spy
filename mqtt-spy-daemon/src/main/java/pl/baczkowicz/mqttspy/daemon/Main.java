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
package pl.baczkowicz.mqttspy.daemon;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.baczkowicz.mqttspy.common.generated.ReconnectionSettings;
import pl.baczkowicz.mqttspy.configuration.PropertyFileLoader;
import pl.baczkowicz.mqttspy.connectivity.BaseMqttConnection;
import pl.baczkowicz.mqttspy.connectivity.SimpleMqttConnection;
import pl.baczkowicz.mqttspy.connectivity.reconnection.ReconnectionManager;
import pl.baczkowicz.mqttspy.daemon.configuration.ConfigurationLoader;
import pl.baczkowicz.mqttspy.daemon.configuration.generated.DaemonMqttConnectionDetails;
import pl.baczkowicz.mqttspy.daemon.configuration.generated.RunningMode;
import pl.baczkowicz.mqttspy.daemon.connectivity.ConnectionRunnable;
import pl.baczkowicz.mqttspy.daemon.connectivity.MqttCallbackHandler;
import pl.baczkowicz.mqttspy.exceptions.MqttSpyException;
import pl.baczkowicz.mqttspy.exceptions.XMLException;
import pl.baczkowicz.mqttspy.scripts.Script;
import pl.baczkowicz.mqttspy.scripts.ScriptManager;
import pl.baczkowicz.mqttspy.utils.ThreadingUtils;

/**
 * The main class of the daemon.
 */
public class Main
{
	/** Diagnostic logger. */
	private final static Logger logger = LoggerFactory.getLogger(Main.class);
	
	/**
	 * The main method, taking one parameter.
	 * 
	 * @param The args - should contain only one argument with the configuration file location
	 */
	public static void main(String[] args)
	{
		try
		{
			final ConfigurationLoader loader = new ConfigurationLoader();
			
			logger.info("#######################################################");
			logger.info("### Starting mqtt-spy-daemon v{}", loader.getFullVersionName());
			logger.info("### If you find it useful, please donate at http://fundraise.unicef.org.uk/MyPage/mqtt-spy :)");
			logger.info("### Visit {} for more information on the project", loader.getProperty(PropertyFileLoader.DOWNLOAD_URL));
			logger.info("### To get release updates follow @mqtt_spy on Twitter");
			logger.info("#######################################################");
			
			if (args.length != 1)
			{
				logger.error("Expecting only 1 parameter with the configuration file location");
				return;
			}				
									
			// Load the configuration
			loader.loadConfiguration(new File(args[0]));
			
			// Retrieve connection details
			final DaemonMqttConnectionDetails connectionSettings = loader.getConfiguration().getConnection();

			// Wire up all classes
			final SimpleMqttConnection connection = new SimpleMqttConnection(connectionSettings);
			final ScriptManager scriptManager = new ScriptManager(null, null, connection);
			final MqttCallbackHandler callback = new MqttCallbackHandler(connection, connectionSettings, scriptManager); 
			connection.createClient(callback);
					
			// Set up reconnection
			final ReconnectionSettings reconnectionSettings = connection.getMqttConnectionDetails().getReconnectionSettings();			
			final Runnable connectionRunnable = new ConnectionRunnable(scriptManager, connection, connectionSettings);
			ReconnectionManager reconnectionManager = null;
			
			if (reconnectionSettings == null)
			{
				new Thread(connectionRunnable).start();
			}
			else
			{
				reconnectionManager = new ReconnectionManager();
				reconnectionManager.addConnection(connection, connectionRunnable);
				new Thread(reconnectionManager).start();
			}
			
			// Run all configured scripts
			scriptManager.addScripts(connectionSettings.getBackgroundScript());
			for (final Script script : scriptManager.getScripts().values())
			{
				scriptManager.runScript(script, true);
			}
			
			// If in 'scripts only' mode, exit when all scripts finished
			if (RunningMode.SCRIPTS_ONLY.equals(connectionSettings.getRunningMode()))
			{
				stop(scriptManager, reconnectionManager, connection, callback);
			}
		}
		catch (XMLException e)
		{
			logger.error("Cannot load the mqtt-spy-daemon's configuration", e);
		}
		catch (MqttSpyException e)
		{
			logger.error("Error occurred while connecting to broker", e);
		}
	}
	
	/**
	 * Tries to stop all running threads.
	 * 
	 * @param scriptManager The script manager (running all scripts)
	 * @param reconnectionManager The reconnection manager (needs stopping)
	 * @param connection The connection (needs closing)
	 * @param callback The connection callback (needs stopping)
	 */
	private static void stop(final ScriptManager scriptManager, final ReconnectionManager reconnectionManager, 
			final BaseMqttConnection connection, final MqttCallbackHandler callback)
	{
		ThreadingUtils.sleep(1000);
		
		// Wait until all scripts have completed or got frozen
		while (scriptManager.areScriptsRunning())
		{
			ThreadingUtils.sleep(1000);
		}
		
		// Stop reconnection manager
		if (reconnectionManager != null)
		{
			reconnectionManager.stop();
		}
						
		// Disconnect
		connection.disconnect();
		
		// Stop message logger
		callback.stop();
		
		ThreadingUtils.sleep(1000);
		for (final Thread thread : Thread.getAllStackTraces().keySet())
		{
			logger.trace("Thread {} is still running", thread.getName());
		}
		logger.info("All tasks completed - bye bye...");
	}
}