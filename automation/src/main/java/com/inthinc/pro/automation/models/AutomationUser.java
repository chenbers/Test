package com.inthinc.pro.automation.models;

import java.util.HashSet;
import java.util.Set;

import com.inthinc.pro.automation.enums.AccountCapabilities;
import com.inthinc.pro.automation.enums.AutoAccounts;
import com.inthinc.pro.automation.enums.DriverCapabilities;
import com.inthinc.pro.automation.enums.LoginCapabilities;
import com.inthinc.pro.automation.interfaces.Capabilities;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;

public class AutomationUser{
    
    private final Set<LoginCapabilities> loginSpecs;
    private final Set<AccountCapabilities> accountSpecs;
    private final Set<DriverCapabilities> driverSpecs;
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
        loginSpecs = new HashSet<LoginCapabilities>();
        accountSpecs = new HashSet<AccountCapabilities>();
        driverSpecs = new HashSet<DriverCapabilities>();
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
            loginSpecs.add(LoginCapabilities.StatusActive);
        } else {
            loginSpecs.add(LoginCapabilities.StatusInactive);
        }
        return this;
    }
    
    public AutomationUser addLoginCapabilities(LoginCapabilities ...abilities){
        for (LoginCapabilities ability : abilities){
            loginSpecs.add(ability);
        }
        return this;
    }
    
    public AutomationUser addDriverCapabilities(DriverCapabilities ...abilities){
        for (DriverCapabilities ability : abilities){
            driverSpecs.add(ability);
        }
        return this;
    }
    
    public AutomationUser addAccountCapabilities(AccountCapabilities ...abilities){
        for (AccountCapabilities ability : abilities){
            accountSpecs.add(ability);
        }
        return this;
    }
    
    public AutomationUser removeLoginCapabilities(LoginCapabilities ...abilities){
        for (LoginCapabilities ability : abilities){
            loginSpecs.remove(ability);
        }
        return this;
    }
    
    public Set<Capabilities> getAllCapabilities(){
        Set<Capabilities> temp = new HashSet<Capabilities>();
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
