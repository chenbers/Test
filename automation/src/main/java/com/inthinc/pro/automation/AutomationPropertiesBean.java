package com.inthinc.pro.automation;

import java.util.List;

public class AutomationPropertiesBean {
    private Boolean addTestSet;
    private String browserName;
    private String defaultUser;
    private String operatingSystem;
    private String password;
    private String silo;
    private List<String> users;

    public Boolean getAddTestSet() {
        return addTestSet;
    }

    public String getBrowserName() {
        return browserName;
    }

    public String getDefaultUser() {
        return defaultUser;
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

    public List<String> getUsers() {
        return users;
    }

    public void setAddTestSet(Boolean addTestSet) {
        this.addTestSet = addTestSet;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public void setDefaultUser(String defaultUser) {
        this.defaultUser = defaultUser;
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

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
