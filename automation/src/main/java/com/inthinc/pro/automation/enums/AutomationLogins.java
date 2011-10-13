package com.inthinc.pro.automation.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;

import com.inthinc.pro.automation.interfaces.Capabilities;
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
    hosDriver00("hosDriver00","password", "Top", AutoAccounts.prime, LoginCapabilities.IsDriver, LoginCapabilities.HasVehicle, LoginCapabilities.HasWaySmart, LoginCapabilities.RoleAdmin), //TODO: vehicle should be named the SAME as the username?
    hosDriver01("hosDriver01","password", "Top", AccountCapabilities.LoginExpire90, AccountCapabilities.PasswordExpire90, AccountCapabilities.PasswordRequireInitalChangeRequire, AccountCapabilities.HOSEnabled, LoginCapabilities.IsDriver),
    jesse1("jesse1", "password","Tina's Auto Team", AutoAccounts.prime, null),
    pitstop("pitstop", "Muttley","Top", AutoAccounts.prime, LoginCapabilities.NoteTesterData), 
    tinaauto("tinaauto", "password","Tina's Auto Team", AutoAccounts.prime, LoginCapabilities.RoleAdmin, LoginCapabilities.IsDriver),    
    whiplash("whiplash", "Muttley","Test Group WR", AutoAccounts.prime, LoginCapabilities.StatusInactive),
    
    passwordchanger00("passwordchanger00", "password", "Top", AutoAccounts.prime, LoginCapabilities.PasswordChanging),
    passwordchanger01("passwordchanger01", "password", "Top", AutoAccounts.prime, LoginCapabilities.PasswordChanging),
    passwordchanger02("passwordchanger02", "password", "Top", AutoAccounts.prime, LoginCapabilities.PasswordChanging),
    tiwi00("tiwi00", "password", "Tina's Auto Team", AutoAccounts.prime, LoginCapabilities.HasTiwiPro, LoginCapabilities.IsDriver, LoginCapabilities.HasVehicle, LoginCapabilities.RoleAdmin),
    tiwi01("tiwi01", "password", "Tina's Auto Team", AutoAccounts.prime, LoginCapabilities.HasTiwiPro, LoginCapabilities.IsDriver, LoginCapabilities.HasVehicle, LoginCapabilities.RoleAdmin),
    tiwi02("tiwi02", "password", "Tina's Auto Team", AutoAccounts.prime, LoginCapabilities.HasTiwiPro, LoginCapabilities.IsDriver, LoginCapabilities.HasVehicle, LoginCapabilities.RoleAdmin),
    tiwi03("tiwi03", "password", "Tina's Auto Team", AutoAccounts.prime, LoginCapabilities.HasTiwiPro, LoginCapabilities.IsDriver, LoginCapabilities.HasVehicle, LoginCapabilities.RoleAdmin),
    
    
    
    
    
    
    
    //Login Capabilities
    
    
    ;
	
	private String username;
	private String password;
	private String group;
	private Set<Capabilities> capabilities;
	private AutoAccounts account;
	private final static Logger logger = Logger.getLogger(AutomationLogins.class);
	
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
    public static List<AutomationLogins> getAllBy(AutoAccounts account) {
        ArrayList<AutomationLogins> results = new ArrayList<AutomationLogins>();
        for (AutomationLogins item : EnumSet.allOf(AutomationLogins.class)) {
            if(item.getAccount().equals(account)) {
                results.add(item);
            }
        }
        return results;
    }
    public static List<AutomationLogins> getAllBy(AutoAccounts account, Capabilities... capabilities){
        List<AutomationLogins> byAccount = getAllBy(account);
        List<AutomationLogins> results = new ArrayList<AutomationLogins>();
        Set<Capabilities> setCapabilities = new HashSet<Capabilities>();
        for(Capabilities c: capabilities)
            setCapabilities.add(c);
        
        for(AutomationLogins login: byAccount){
            if(login.capabilities.containsAll(setCapabilities))
                results.add(login);
        }
        return results;  
    }
    public static AutomationLogins getOneBy(AutoAccounts account, Capabilities... capabilities){
        return getOneBy(getAllBy(account, capabilities));
    }
    private static AutomationLogins getOneBy(List<AutomationLogins> list){
        if (!list.isEmpty()) {
            int loginIndex = RandomUtils.nextInt(list.size());
            return list.get(loginIndex);
        } else {
            logger.warn("AutomationLogins.getOneBy(" + list + ") returning null! ");
            return null;
        } 
    }
    public static AutomationLogins getOneBy(AutoAccounts account){
        return getOneBy(getAllBy(account));    
    }
    public static AutomationLogins getOneBy(String groupName) {
        return getOneBy(getAllBy(groupName));
    }

	public static AutomationLogins getOneBy(Capabilities... capabilities) {
	    return getOneBy(getAllBy(capabilities));
	}
	public static AutomationLogins getOne(){
	    return getOneBy(LoginCapabilities.StatusActive);
	}

	public String getGroup() {
	    return group;
	}
	public AutoAccounts getAccount() {
	    return account;
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
