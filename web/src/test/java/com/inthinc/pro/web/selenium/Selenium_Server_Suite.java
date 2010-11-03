package com.inthinc.pro.web.selenium;


import java.util.List;

import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;
import org.junit.runners.Suite;
import org.junit.runners.model.*;
import org.openqa.selenium.server.SeleniumServer;

public class Selenium_Server_Suite extends Suite {
	
	private static SeleniumServer seleniumServer;
	
	public static void startSeleniumServer() throws Exception {
		Selenium_Server_Suite.stopSeleniumServer();
		seleniumServer = new SeleniumServer();
		seleniumServer.start();
	}
	
	public static void stopSeleniumServer() {
		if (seleniumServer != null) {
			seleniumServer.stop();
			seleniumServer = null;
		}
	}
	
	public Selenium_Server_Suite(Class<?> klass, Class<?>[]	suiteClasses) throws InitializationError {
		super(klass, suiteClasses);
	}
	
	public Selenium_Server_Suite(Class<?> klass, List<Runner> runners) throws InitializationError {
		super(klass, runners);
	}
	
	public Selenium_Server_Suite(Class<?> klass, RunnerBuilder builder) throws InitializationError {
		super(klass, builder);
	}
	
	public Selenium_Server_Suite(RunnerBuilder builder, Class<?>klass, Class<?>[] suiteClasses) throws InitializationError {
		super(builder, klass, suiteClasses);
	}
	
	public Selenium_Server_Suite(RunnerBuilder builder, Class<?>[] classes) throws InitializationError {
		super(builder, classes);
	}
	
	@Override
	public void run(final RunNotifier notifier) {
		EachTestNotifier testNotifier = new EachTestNotifier(notifier,
				this.getDescription());
		
		try {
			Selenium_Server_Suite.startSeleniumServer();
		Statement statement = this.classBlock(notifier);
		statement.evaluate();
		} catch (AssumptionViolatedException e) {
			testNotifier.fireTestIgnored();
		} catch (StoppedByUserException e) {
			throw e;
		} catch (Throwable e) {
			testNotifier.addFailure(e);
		} finally {
			Selenium_Server_Suite.stopSeleniumServer();
		}
	}
}
