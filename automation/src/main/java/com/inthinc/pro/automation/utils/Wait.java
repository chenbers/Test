package com.inthinc.pro.automation.utils;

import org.apache.log4j.Logger;

public class Wait {
	private final static Logger logger = Logger.getLogger(AutomationLogger.class);
	public static void pause(long s) {
		try {
			Thread.currentThread();
			Thread.sleep(s * 1000);
		}
		catch (InterruptedException e) {
			logger.debug(StackToString.toString(e));
		}
	}
}
