package com.inthinc.pro.automation.selenium;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.inthinc.pro.automation.AutomationPropertiesBean;

public class AutomationProperties {

    private static final String[] configFiles = { "classpath:spring/applicationContext-automation.xml" };
    private final BeanFactory factory;
    private final AutomationPropertiesBean apb;
    
    private static AutomationProperties local;
    
    private AutomationProperties(){
        factory = new ClassPathXmlApplicationContext(configFiles);
        apb = (AutomationPropertiesBean) factory.getBean("automationPropertiesBean");
    }

    public static AutomationPropertiesBean getPropertyBean() {
        if (local==null){
            local = new AutomationProperties();
        }
        return local.apb;
    }

}
