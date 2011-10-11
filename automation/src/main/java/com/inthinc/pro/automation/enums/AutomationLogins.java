package com.inthinc.pro.automation.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.math.RandomUtils;

import com.inthinc.pro.automation.utils.RandomValues;
import com.mysql.jdbc.log.Log;


public enum AutomationLogins {

    nothingSpecial_00("jwimmer", "password","Top", null),//non PRIME account; when group is "Top" there are presumably several other options for valid group names on this account
    //prime("prime", "account", null),
    
    akumer("akumer","password","Top", AutoAccounts.prime, null),
    autovehicle("autovehicle","passw0rd","AdminVehicleTests", AutoAccounts.prime, null),
    CaptainNemo("CaptainNemo", "Muttley", "Test Group RW", AutoAccounts.prime, null),
    noteTester01("noteTester01", "password", "Test Group WR", AutoAccounts.prime, LoginCapabilities.NoteTesterData),
    Daisy1("Daisy1", "password","Tina's Auto Team", AutoAccounts.prime, null),
    danniauto("danniauto", "password", "Top",  AccountCapabilities.LoginExpire90, AccountCapabilities.PasswordExpire90, AccountCapabilities.PasswordRequireInitalChangeRequire, AccountCapabilities.HOSEnabled),
    dastardly("dastardly", "Muttley","Test Group WR", AutoAccounts.prime, LoginCapabilities.NoteTesterData), 
    hosDriver00("hosDriver00","password", "Top", AutoAccounts.prime, AccountCapabilities.LoginExpire90, AccountCapabilities.PasswordExpire90, AccountCapabilities.PasswordRequireInitalChangeRequire, AccountCapabilities.HOSEnabled, LoginCapabilities.IsDriver),
    hosDriver01("hosDriver01","password", "Top", AccountCapabilities.LoginExpire90, AccountCapabilities.PasswordExpire90, AccountCapabilities.PasswordRequireInitalChangeRequire, AccountCapabilities.HOSEnabled, LoginCapabilities.IsDriver),
    jesse1("jesse1", "password","Tina's Auto Team", AutoAccounts.prime, null),
    pitstop("pitstop", "Muttley","Top", AutoAccounts.prime, LoginCapabilities.NoteTesterData), 
    tinaauto("tinaauto", "password","Tina's Auto Team", AutoAccounts.prime, LoginCapabilities.RoleAdmin ,LoginCapabilities.HasDevice, LoginCapabilities.HasVehicle, LoginCapabilities.IsDriver),    
    whiplash("whiplash", "Muttley","Test Group WR", AutoAccounts.prime, LoginCapabilities.StatusInactive),
    
    passwordchanger00("passwordchanger00", "password", "Top", AutoAccounts.prime, LoginCapabilities.PasswordChanging),
    passwordchanger01("passwordchanger01", "password", "Top", AutoAccounts.prime, LoginCapabilities.PasswordChanging),
    passwordchanger02("passwordchanger02", "password", "Top", AutoAccounts.prime, LoginCapabilities.PasswordChanging),
    
    
    
    
    
    
    
    
    
    //Login Capabilities
    
    
    ;
	
	private String username;
	private String password;
	private String group;
	private Set<Capabilities> capabilities;
	private AutoAccounts account;
	
	private AutomationLogins(String username, String password, String group, AutoAccounts account, Capabilities... capabilities) {
	    this.username = username;
        this.password = password;
        this.group = group;
        this.capabilities = new HashSet<Capabilities>();
        
        if(account != null && account.getCapabilities() !=null)
            for(Capabilities c: account.getCapabilities())
                this.capabilities.add(c);
        
        if(capabilities != null)
            for(Capabilities c: capabilities)
                this.capabilities.add(c);
        else
            this.capabilities.add(LoginCapabilities.StatusActive);
	}
	private AutomationLogins(String username, String password, String group, Capabilities... capabilities){
	    this(username, password, group, null, capabilities);
	}
	public String getUserName(){
	    return username;
	}
	public String getPassword(){
	    return password;
	}
	public static List<AutomationLogins> getAllBy(Capabilities... capabilities){
	    ArrayList<AutomationLogins> results = new ArrayList<AutomationLogins>();
	    Set<Capabilities> setCapabilities = new HashSet<Capabilities>();
	    for(Capabilities c: capabilities)
	        setCapabilities.add(c);
	    
	    for(AutomationLogins item: EnumSet.allOf(AutomationLogins.class)){
	        if(item.capabilities.containsAll(setCapabilities)){
	            results.add(item);
	        }
	    }
	    return results;
	}
	
    public static List<AutomationLogins> getAllBy(String groupName) {
        ArrayList<AutomationLogins> results = new ArrayList<AutomationLogins>();
        for (AutomationLogins item : EnumSet.allOf(AutomationLogins.class)) {
            if (item.getGroup().equals(groupName)) {
                results.add(item);
            }
        }
        return results;
    }

    public static AutomationLogins getOneBy(String groupName) {
        List<AutomationLogins> allMatching = getAllBy(groupName);
        if (!allMatching.isEmpty()) {
            int loginIndex = RandomUtils.nextInt(allMatching.size());
            return allMatching.get(loginIndex);
        } else {
            System.out.println("AutomationLogins.getOneBy(" + groupName + ") returning null! ");
            return null;// TODO: jwimmer: what is the best course of action if no matching accounts are found?
        }
    }

	public static AutomationLogins getOneBy(Capabilities... capabilities) {
	    List<AutomationLogins> allMatching = getAllBy(capabilities);
	    if(!allMatching.isEmpty()){
	        int loginIndex = RandomUtils.nextInt(allMatching.size());
	        System.out.println("AutomationLogins.getOneBy("+capabilities+") returning "+allMatching.get(loginIndex));
	        return allMatching.get(loginIndex);
	    } else {
	        System.out.println("AutomationLogins.getOneBy("+capabilities+") returning null! ");
	        return null;//TODO: jwimmer: what is the best course of action if no matching accounts are found?
	    }  
	}
	public static AutomationLogins getOne(){
	    return getOneBy(LoginCapabilities.StatusActive);
	}

	public String getGroup() {
	    return group;
	}
	
	private enum AutoAccounts {
	    prime(AccountCapabilities.HOSEnabled, AccountCapabilities.WaySmartEnabled, AccountCapabilities.LoginExpireNever, AccountCapabilities.PasswordExpireNever, AccountCapabilities.PasswordMinStrengthNone, AccountCapabilities.PasswordRequireInitialChangeNone),
	    ;
	    
	    private String accountName;
	    private Set<AccountCapabilities> capabilities;
	    private AutoAccounts(AccountCapabilities... capabilities){
	        this.capabilities = new HashSet<AccountCapabilities>();
	        for(AccountCapabilities ac: capabilities){
	            this.capabilities.add(ac);
	        }
	    }
        
        public String getAccountName() {
            return accountName;
        }
        public Set<AccountCapabilities> getCapabilities(){
            return capabilities;
        }
	   
	}
}
