package com.inthinc.pro.automation.selenium;


import java.util.List;

import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;
import org.junit.runners.Suite;
import org.junit.runners.model.*;
import org.openqa.selenium.server.SeleniumServer;

public class SeleniumServerSuite extends Suite {
	
	private static SeleniumServer seleniumServer;
	
	public static void startSeleniumServer() throws Exception {
		SeleniumServerSuite.stopSeleniumServer();
		seleniumServer = new SeleniumServer();
		seleniumServer.start();
	}
	
	public static void stopSeleniumServer() {
		if (seleniumServer != null) {
			seleniumServer.stop();
			seleniumServer = null;
		}
	}
	
	public SeleniumServerSuite(Class<?> klass, Class<?>[]	suiteClasses) throws InitializationError {
		super(klass, suiteClasses);
	}
	
	public SeleniumServerSuite(Class<?> klass, List<Runner> runners) throws InitializationError {
		super(klass, runners);
	}
	
	public SeleniumServerSuite(Class<?> klass, RunnerBuilder builder) throws InitializationError {
		super(klass, builder);
	}
	
	public SeleniumServerSuite(RunnerBuilder builder, Class<?>klass, Class<?>[] suiteClasses) throws InitializationError {
		super(builder, klass, suiteClasses);
	}
	
	public SeleniumServerSuite(RunnerBuilder builder, Class<?>[] classes) throws InitializationError {
		super(builder, classes);
	}
	
	@Override
	public void run(final RunNotifier notifier) {
		EachTestNotifier testNotifier = new EachTestNotifier(notifier,
				this.getDescription());
		
		try {
			SeleniumServerSuite.startSeleniumServer();
		Statement statement = this.classBlock(notifier);
		statement.evaluate();
		} catch (AssumptionViolatedException e) {
			testNotifier.fireTestIgnored();
		} catch (StoppedByUserException e) {
			throw e;
		} catch (Throwable e) {
			testNotifier.addFailure(e);
		} finally {
			SeleniumServerSuite.stopSeleniumServer();
		}
	}
}
