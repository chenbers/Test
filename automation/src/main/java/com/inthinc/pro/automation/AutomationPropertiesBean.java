package com.inthinc.pro.automation;

import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AutomationPropertiesBean {
    
    private static final String[] configFiles = { "classpath:spring/applicationContext-automation.xml" };
    private static final BeanFactory factory = new ClassPathXmlApplicationContext(configFiles);
    private static final AutomationPropertiesBean apb = (AutomationPropertiesBean) factory.getBean("automationPropertiesBean");

    public static AutomationPropertiesBean getPropertyBean() {
        return apb;
    }
    
    
    private Boolean addTestSet;
    private String browserName;
    private List<String> editableAccount;
    private List<String> mainAutomation;
    private String operatingSystem;
    private String password;
    private String silo;

    public Boolean getAddTestSet() {
        return addTestSet;
    }

    public String getBrowserName() {
        return browserName;
    }

    public List<String> getEditableAccount() {
        return editableAccount;
    }

    public List<String> getMainAutomation() {
        return mainAutomation;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public String getPassword() {
        return password;
    }

    public String getRallyName() {
        return "automation_" + operatingSystem + "_" + browserName;
    }

    public String getSilo() {
        return silo;
    }

    public void setAddTestSet(Boolean addTestSet) {
        this.addTestSet = addTestSet;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public void setEditableAccount(List<String> editableAccount) {
        this.editableAccount = editableAccount;
    }

    public void setMainAutomation(List<String> mainAutomation) {
        this.mainAutomation = mainAutomation;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSilo(String silo) {
        this.silo = silo;
    }
}
