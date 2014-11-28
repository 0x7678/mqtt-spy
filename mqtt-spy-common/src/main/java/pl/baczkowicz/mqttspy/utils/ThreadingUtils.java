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
package pl.baczkowicz.mqttspy.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * Threading related utilities.
 */
public class ThreadingUtils
{
	public final static Logger logger = LoggerFactory.getLogger(ThreadingUtils.class);
	
	public static final String STARTING_THREAD = "Starting %s thread...";
	
	public static final String ENDING_THREAD = "Ending %s thread...";	 
	
	public static void logStarting()
	{
		if (logger.isTraceEnabled())
		{
			logger.trace(String.format(ThreadingUtils.STARTING_THREAD, Thread.currentThread().getName()));
		}
	}
	
	public static void logEnding()
	{
		if (logger.isTraceEnabled())
		{
			logger.trace(String.format(ThreadingUtils.ENDING_THREAD, Thread.currentThread().getName()));
		}
	}
	
	public static boolean sleep(final long milliseconds)	
	{
		try
		{
			Thread.sleep(milliseconds);
			return false;
		}
		catch (InterruptedException e)
		{
			logger.warn("Thread {} has been interrupted", Thread.currentThread().getName(), e);
			return true;
		}
	}
}
