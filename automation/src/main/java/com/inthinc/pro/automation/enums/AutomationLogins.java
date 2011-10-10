package com.inthinc.pro.automation.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public enum AutomationLogins {

    nothingSpecial_00("jwimmer", "password","Top", null),//non PRIME account; when group is "Top" there are presumably several other options for valid group names on this account
    //prime("prime", "account", null),
    
    akumer("akumer","password","Top", null),//prime
    autovehicle("autovehicle","passw0rd","AdminVehicleTests", null),//prime
    captnemo("CaptainNemo", "Muttley", "Test Group RW", EnumSet.of(LoginCapabilities.NoteTesterData)), //prime
    daisy1("Daisy1", "password","Tina's Auto Team", null),//prime
    danni("danniauto", "password", "Top", EnumSet.of(LoginCapabilities.LoginExpire90, LoginCapabilities.PasswordExpire90, LoginCapabilities.PasswordRequireInitalChangeRequire, LoginCapabilities.HOSEnabled)),
    dastardly("dastardly", "Muttley","Test Group WR", EnumSet.of(LoginCapabilities.NoteTesterData)), //prime
    jesse1("jesse1", "password","Tina's Auto Team", null),//prime
    pitstop("pitstop", "Muttley","Top", EnumSet.of(LoginCapabilities.NoteTesterData)), //prime
    tinaauto("tinaauto", "password","Tina's Auto Team", EnumSet.of(LoginCapabilities.RoleAdmin ,LoginCapabilities.HasDevice, LoginCapabilities.HasVehicle, LoginCapabilities.IsDriver)),//prime    
    whiplash("whiplash", "Muttley","Test Group WR", EnumSet.of(LoginCapabilities.StatusInactive)),//prime
    
    
    
    
    
    
    
    
    
    
    
    //Login Capabilities
    
    
    ;
	
	private String username;
	private String password;
	private String group;
	private Set<LoginCapabilities> capabilites;
	
	private AutomationLogins(String username, String password, String group, EnumSet<LoginCapabilities> capabilities){
	    this.username = username;
	    this.password = password;
	    this.group = group;
	    
	    if(capabilities != null)
	        this.capabilites = capabilities;
	    else
	        this.capabilites = EnumSet.of(LoginCapabilities.StatusActive);
	    
	}
	public String getUserName(){
	    return username;
	}
	public String getPassword(){
	    return password;
	}
	public static List<AutomationLogins> getAllBy(Set<LoginCapabilities> capabilities){
	    ArrayList<AutomationLogins> results = new ArrayList<AutomationLogins>();
	    for(AutomationLogins item: EnumSet.allOf(AutomationLogins.class)){
	        if(item.capabilites.containsAll(capabilities)){
	            results.add(item);
	        }
	    }
	    return results;
	}
	public static List<AutomationLogins> getAllBy(LoginCapabilities capability){
	    Set<LoginCapabilities> wrapper = new HashSet<LoginCapabilities>();
	    wrapper.add(capability);
	    return getAllBy(wrapper);
	}
	public static AutomationLogins getOneBy(Set<LoginCapabilities> capabilities) {
	    List<AutomationLogins> allMatching = getAllBy(capabilities);
	    if(!allMatching.isEmpty()){
	        System.out.println("AutomationLogins.getOneBy("+capabilities+") returning "+allMatching.get(0));
	        return allMatching.get(0);
	    } else {
	        System.out.println("AutomationLogins.getOneBy("+capabilities+") returning null! ");
	        return null;//TODO: jwimmer: what is the best course of action if no matching accounts are found?
	    }  
	}

	public String getGroup() {
	    return group;
	}
}
