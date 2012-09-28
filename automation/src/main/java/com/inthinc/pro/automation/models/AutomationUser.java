package com.inthinc.pro.automation.models;

import java.util.HashSet;
import java.util.Set;

import com.inthinc.pro.automation.enums.AccountCapability;
import com.inthinc.pro.automation.enums.AutoAccounts;
import com.inthinc.pro.automation.enums.DriverCapability;
import com.inthinc.pro.automation.enums.LoginCapability;
import com.inthinc.pro.automation.interfaces.Capability;

public class AutomationUser{
    
    private final Set<LoginCapability> loginSpecs;
    private final Set<AccountCapability> accountSpecs;
    private final Set<DriverCapability> driverSpecs;
    private AutoAccounts account;
    private String teamName;
    private String groupName;
	private String firstName;
	private String middleName;
	private String lastName;
	private String suffix;
	private String fullName;
	private String username;
	private String password;
    
    private AutomationUser(){
        loginSpecs = new HashSet<LoginCapability>();
        accountSpecs = new HashSet<AccountCapability>();
        driverSpecs = new HashSet<DriverCapability>();
    }
    

    public AutomationUser(AutoAccounts account) {
        this();
        this.account = account;
    }
    
    public AutomationUser setupUser(String username, String password, String groupName){
    	this.username = username;
    	this.password = password;
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
    	this.firstName = firstName;
    	this.middleName = middleName;
    	this.lastName = lastName;
    	this.suffix = suffix;
    	this.fullName = firstName + ( middleName.isEmpty() ? "" : (" " + middleName ) ) + " " + lastName + ( suffix.isEmpty() ? "": (" " + suffix));
        return this;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }
    
    public String getFullName(){
        return fullName;
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
