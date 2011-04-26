package com.inthinc.pro.automation.selenium;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.inthinc.pro.automation.AutomationPropertiesBean;
import com.inthinc.pro.automation.utils.StackToString;

public class GlobalSelenium {
    private final static Logger logger = Logger.getLogger(GlobalSelenium.class);

	// volatile is needed so that multiple thread can reconcile the instance
	// semantics for volatile changed in Java 5.
	// private volatile static GlobalSelenium globalSelenium;
	private volatile static HashMap<Long, CoreMethodLib> multiplicative = new HashMap<Long, CoreMethodLib>();

	public static CoreMethodLib getSelenium() {
		Long currentThread = Thread.currentThread().getId();
		return multiplicative.get(currentThread);
	}

	public static CoreMethodLib getYourOwn() {
	    CoreMethodLib selenium;

        Long currentThread = Thread.currentThread().getId();
        
	    try {
            String[] configFiles = new String[] { "classpath:spring/applicationContext-automation.xml" };
            BeanFactory factory = new ClassPathXmlApplicationContext(configFiles);
            AutomationPropertiesBean apb = (AutomationPropertiesBean) factory.getBean("automationPropertiesBean");
            logger.info(apb.getDefaultWebDriverName() + " on portal @" + apb.getBaseURL() + " with Thread: " + currentThread);
            selenium = new CoreMethodLib(apb.getDefaultWebDriver(), apb.getBaseURL());
        } catch (NoSuchBeanDefinitionException e) {
            logger.error(StackToString.toString(e));
            selenium = new CoreMethodLib(new FirefoxDriver(), "https://qa.tiwipro.com:8423/tiwipro/");
        } catch (BeanCreationException e) {
            logger.error(StackToString.toString(e));
            selenium = new CoreMethodLib(new FirefoxDriver(), "https://qa.tiwipro.com:8423/tiwipro/");
        }

		multiplicative.put(currentThread, selenium);
		
		return multiplicative.get(currentThread);
	}


	public static void dieSeleniumDie() {
		Long currentThread = Thread.currentThread().getId();
		multiplicative.get(currentThread).close();
		multiplicative.get(currentThread).stop();
		((CoreMethodLib) multiplicative.get(currentThread)).getWrappedDriver();
		multiplicative.remove(currentThread);
	}
}
