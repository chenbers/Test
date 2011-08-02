package com.inthinc.pro.automation.selenium;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.inthinc.pro.automation.AutomationPropertiesBean;

public class AutomationProperties {
    
    private static final String[] configFiles = {"classpath:spring/applicationContext-automation.xml"};
    private static final BeanFactory factory = new ClassPathXmlApplicationContext(configFiles);
    private static final AutomationPropertiesBean apb = (AutomationPropertiesBean) factory.getBean("automationPropertiesBean");
    
        public static AutomationPropertiesBean getPropertyBean(){
            return apb;
        }

}
