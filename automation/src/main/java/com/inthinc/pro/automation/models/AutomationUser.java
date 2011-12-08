package com.inthinc.pro.automation.models;

import java.util.HashSet;
import java.util.Set;

import com.inthinc.pro.automation.enums.AccountCapability;
import com.inthinc.pro.automation.enums.AutoAccounts;
import com.inthinc.pro.automation.enums.DriverCapability;
import com.inthinc.pro.automation.enums.LoginCapability;
import com.inthinc.pro.automation.interfaces.Capability;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;

public class AutomationUser{
    
    private final Set<LoginCapability> loginSpecs;
    private final Set<AccountCapability> accountSpecs;
    private final Set<DriverCapability> driverSpecs;
    private final Person personPart;
    private final User userPart;
    private final Driver driverPart;
    private AutoAccounts account;
    private String teamName;
    private String groupName;
    
    private AutomationUser(){
        personPart = new Person();
        userPart = new User();
        driverPart = new Driver();
        loginSpecs = new HashSet<LoginCapability>();
        accountSpecs = new HashSet<AccountCapability>();
        driverSpecs = new HashSet<DriverCapability>();
    }
    

    public AutomationUser(AutoAccounts account) {
        this();
        this.account = account;
    }
    
    public AutomationUser setupUser(String username, String password, String groupName){
        userPart.setUsername(username);
        userPart.setPassword(password);
        this.groupName = groupName;
        return this;
    }
    
    public AutomationUser setUserStatus(boolean status){
        if (status){
            loginSpecs.add(LoginCapability.StatusActive);
        } else {
            loginSpecs.add(LoginCapability.StatusInactive);
        }
        return this;
    }
    
    public AutomationUser addLoginCapabilities(LoginCapability ...abilities){
        for (LoginCapability ability : abilities){
            loginSpecs.add(ability);
        }
        return this;
    }
    
    public AutomationUser addDriverCapabilities(DriverCapability ...abilities){
        for (DriverCapability ability : abilities){
            driverSpecs.add(ability);
        }
        return this;
    }
    
    public AutomationUser addAccountCapabilities(AccountCapability ...abilities){
        for (AccountCapability ability : abilities){
            accountSpecs.add(ability);
        }
        return this;
    }
    
    public AutomationUser removeLoginCapabilities(LoginCapability ...abilities){
        for (LoginCapability ability : abilities){
            loginSpecs.remove(ability);
        }
        return this;
    }
    
    public Set<Capability> getAllCapabilities(){
        Set<Capability> temp = new HashSet<Capability>();
        temp.addAll(loginSpecs);
        temp.addAll(accountSpecs);
        temp.addAll(driverSpecs);
        return temp;
    }
    
    public AutomationUser setupDriver(String teamName){
        this.teamName = teamName;
        return this;
    }
    
    
    public AutomationUser setupPerson(String firstName, String middleName, String lastName, String suffix){
        personPart.setFirst(firstName);
        personPart.setMiddle(middleName);
        personPart.setLast(lastName);
        personPart.setSuffix(suffix);
        return this;
    }
    
    public String getUsername(){
        return userPart.getUsername();
    }
    
    public String getPassword(){
        return userPart.getPassword();
    }
    
    public String getFullName(){
        return personPart.getFullName();
    }

    public String getTeamName() {
        return teamName;
    }

    public String getGroupName() {
        return groupName;
    }

    public AutoAccounts getAccount() {
        return account;
    }
}
