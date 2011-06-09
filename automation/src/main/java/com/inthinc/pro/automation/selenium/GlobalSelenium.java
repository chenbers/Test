package com.inthinc.pro.automation.selenium;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.inthinc.pro.automation.AutomationPropertiesBean;
import com.inthinc.pro.automation.utils.StackToString;

public class GlobalSelenium {
    private final static Logger logger = Logger.getLogger(GlobalSelenium.class);

	private volatile static HashMap<Long, CoreMethodLib> multiplicative = new HashMap<Long, CoreMethodLib>();
	private final static String BASE_URL_DEFAULT = "https://qa.tiwipro.com:8423/tiwipro/";
	
	public static CoreMethodLib getSelenium() {
		Long currentThread = Thread.currentThread().getId();
		if (multiplicative.containsKey(currentThread)){
			return multiplicative.get(currentThread);
		} else {
			return getYourOwn();
		}
	}

	public static CoreMethodLib getYourOwn() {
	    CoreMethodLib selenium;
        Long currentThread = Thread.currentThread().getId();
        if (multiplicative.containsKey(currentThread)){
        	return multiplicative.get(currentThread);
        }
        
	    try {
            String[] configFiles = new String[] { "classpath:spring/applicationContext-automation.xml" };
            BeanFactory factory = new ClassPathXmlApplicationContext(configFiles);
            AutomationPropertiesBean apb = (AutomationPropertiesBean) factory.getBean("automationPropertiesBean");
            logger.info(apb.getDefaultWebDriverName() + " on portal @" + apb.getBaseURL() + " with Thread: " + currentThread);
            selenium = new CoreMethodLib(apb.getDefaultWebDriver(), apb.getBaseURL());
        } catch (BeansException e) {
            logger.error(StackToString.toString(e));
            selenium = new CoreMethodLib(new FirefoxDriver(), BASE_URL_DEFAULT);
        } 

		multiplicative.put(currentThread, selenium);
		return multiplicative.get(currentThread);
	}


	public static void dieSeleniumDie() {
		Long currentThread = Thread.currentThread().getId();
		try{
			multiplicative.get(currentThread).close();
			multiplicative.get(currentThread).stop();
			multiplicative.get(currentThread).getWrappedDriver();
		}catch(NullPointerException e){
			logger.debug("Selenium already closed");
		}catch(Exception e){
		    logger.error(e);
		}
		multiplicative.remove(currentThread);
	}
}
