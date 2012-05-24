package com.inthinc.pro.automation;

import java.util.Map;

public class AutomationPropertiesBean {
    private String silo;
    private String browserName;
    private String operatingSystem;
    private Boolean addTestSet;
    private Map<String, String> users;

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public Boolean getAddTestSet() {
        return addTestSet;
    }

    public void setAddTestSet(Boolean addTestSet) {
        this.addTestSet = addTestSet;
    }

    public String getSilo() {
        return silo;
    }

    public void setSilo(String silo) {
        this.silo = silo;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getRallyName() {
        return "automation_" + operatingSystem + "_" + browserName;
    }

    public Map<String, String> getUsers() {
        return users;
    }

    public void setUsers(Map<String, String> users) {
        this.users = users;
    }
}
