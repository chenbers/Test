package com.inthinc.pro.automation.objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;

import com.inthinc.pro.automation.enums.AccountCapabilities;
import com.inthinc.pro.automation.enums.AutoAccounts;
import com.inthinc.pro.automation.enums.DriverCapabilities;
import com.inthinc.pro.automation.enums.LoginCapabilities;
import com.inthinc.pro.automation.interfaces.Capabilities;
import com.inthinc.pro.automation.models.AutomationUser;

public class AutomationUsers {

    private final static Logger logger = Logger.getLogger(AutomationUsers.class);
    
    private static List<AutomationUser> users;
    
    private AutomationUsers(){
        if (users == null){
            users = new ArrayList<AutomationUser>();
            createUsers();
        }
    }
    
    public static AutomationUsers getUsers(){
        return new AutomationUsers();
    }
    
    private static void createUsers(){
        AutomationUser temp;
        
        temp = new AutomationUser(AutoAccounts.prime).setupPerson("Waysmart", "", "01", "").setupUser("waysmart01", "password", "Tina's Auto Team").setupDriver("Tina's Auto Team").addLoginCapabilities(LoginCapabilities.HasWaySmart, LoginCapabilities.TeamLevelLogin, LoginCapabilities.IsDriver, LoginCapabilities.StatusActive, LoginCapabilities.HasVehicle, LoginCapabilities.RoleHOS);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("akumer", "password", "Top").addLoginCapabilities(LoginCapabilities.RoleAdmin, LoginCapabilities.FleetLevelLogin);        users.add(temp);        
        temp = new AutomationUser(AutoAccounts.prime).setupUser("autovehicle", "passw0rd", "AdminVehicleTests").addLoginCapabilities(LoginCapabilities.TeamLevelLogin);        users.add(temp);        
        temp = new AutomationUser(AutoAccounts.prime).setupUser("inactive", "password", "Test Group WR").addLoginCapabilities(LoginCapabilities.TeamLevelLogin);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("CaptainNemo", "Muttley", "Test Group RW").addLoginCapabilities(LoginCapabilities.TeamLevelLogin);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("noteTester01", "password", "Test Group WR").addLoginCapabilities(LoginCapabilities.NoteTesterData, LoginCapabilities.TeamLevelLogin);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("Daisy1", "password", "Tina's Auto Team").addLoginCapabilities(LoginCapabilities.TeamLevelLogin, LoginCapabilities.IsDriver).addDriverCapabilities(DriverCapabilities.RuleSet_Texas);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.QA).setupUser("danniauto", "password", "Top").addAccountCapabilities(AccountCapabilities.PasswordExpire90, AccountCapabilities.PasswordRequireInitalChangeRequire, AccountCapabilities.HOSEnabled, AccountCapabilities.LoginExpire90);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("dastardly", "Muttley", "Test Group WR").setupDriver("Test Group WR").addLoginCapabilities(LoginCapabilities.NoteTesterData, LoginCapabilities.TeamLevelLogin, LoginCapabilities.RoleAdmin);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("hosDriver00", "password", "Top").setupDriver("Tina's Auto Team").addLoginCapabilities(LoginCapabilities.IsDriver, LoginCapabilities.FleetLevelLogin,LoginCapabilities.HasVehicle,LoginCapabilities.HasWaySmart,LoginCapabilities.RoleAdmin).addDriverCapabilities(DriverCapabilities.RuleSet_US8Day);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.QA).setupUser("hosDriver01", "password", "Top").addAccountCapabilities(AccountCapabilities.PasswordExpire90, AccountCapabilities.PasswordRequireInitalChangeRequire, AccountCapabilities.HOSEnabled, AccountCapabilities.LoginExpire90).addLoginCapabilities(LoginCapabilities.IsDriver, LoginCapabilities.FleetLevelLogin);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("jesse1", "password", "Tina's Auto Team").setupDriver("Tina's Auto Team").addLoginCapabilities(LoginCapabilities.TeamLevelLogin).addDriverCapabilities(DriverCapabilities.RuleSet_USOil8Day);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("pitstop", "Muttley", "Top").addLoginCapabilities(LoginCapabilities.NoteTesterData, LoginCapabilities.RoleAdmin);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("tinaauto", "password", "Tina's Auto Team").setupDriver("Tina's Auto Team").addLoginCapabilities(LoginCapabilities.RoleAdmin,LoginCapabilities.IsDriver, LoginCapabilities.TeamLevelLogin).addDriverCapabilities(DriverCapabilities.RuleSet_Canada2007_60DegreesOilFieldPermit);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("whiplash", "Muttley", "Test Group WR").addLoginCapabilities(LoginCapabilities.StatusInactive);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("passwordchanger00", "password", "Top").addLoginCapabilities(LoginCapabilities.PasswordChanging, LoginCapabilities.FleetLevelLogin);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("passwordchanger01", "password", "Top").addLoginCapabilities(LoginCapabilities.PasswordChanging, LoginCapabilities.FleetLevelLogin);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("passwordchanger02", "password", "Top").addLoginCapabilities(LoginCapabilities.PasswordChanging, LoginCapabilities.FleetLevelLogin);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("tiwi00", "password", "Tina's Auto Team").setupDriver("Tina's Auto Team").addLoginCapabilities(LoginCapabilities.HasTiwiPro, LoginCapabilities.TeamLevelLogin,LoginCapabilities.IsDriver,LoginCapabilities.HasVehicle,LoginCapabilities.RoleAdmin).addDriverCapabilities(DriverCapabilities.RuleSet_Canada2007_60DegreesOilFieldPermit);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("tiwi01", "password", "Tina's Auto Team").setupDriver("Tina's Auto Team").addLoginCapabilities(LoginCapabilities.HasTiwiPro, LoginCapabilities.TeamLevelLogin,LoginCapabilities.IsDriver,LoginCapabilities.HasVehicle,LoginCapabilities.RoleAdmin);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("tiwi02", "password", "Tina's Auto Team").setupDriver("Tina's Auto Team").addLoginCapabilities(LoginCapabilities.HasTiwiPro, LoginCapabilities.TeamLevelLogin,LoginCapabilities.IsDriver,LoginCapabilities.HasVehicle,LoginCapabilities.RoleAdmin).addDriverCapabilities(DriverCapabilities.RuleSet_Canada2007OilFieldPermit);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("tiwi03", "password", "Tina's Auto Team").setupDriver("Tina's Auto Team").addLoginCapabilities(LoginCapabilities.HasTiwiPro, LoginCapabilities.TeamLevelLogin,LoginCapabilities.IsDriver,LoginCapabilities.HasVehicle);        users.add(temp);
    }
    
    public List<AutomationUser> getAllBy(Capabilities... capabilities){
        ArrayList<AutomationUser> results = new ArrayList<AutomationUser>();
        Set<Capabilities> setCapabilities = new HashSet<Capabilities>();
        for(Capabilities c: capabilities){
            setCapabilities.add(c);
        }
        
        for(AutomationUser item: users){
            if(item.getAllCapabilities().containsAll(setCapabilities)){
                results.add(item);
            }
        }
        return results;
    }
    
    public List<AutomationUser> getAllBy(String groupName) {
        ArrayList<AutomationUser> results = new ArrayList<AutomationUser>();
        for (AutomationUser item : users) {
            if (item.getGroupName().equals(groupName)) {
                results.add(item);
            }
        }
        return results;
    }
    
    public List<AutomationUser> getAllBy(String groupName, Capabilities ...abilities){
        List<AutomationUser> byAbilities = getAllBy(abilities);
        List<AutomationUser> results = new ArrayList<AutomationUser>();
        for (AutomationUser user : byAbilities){
            if (user.getGroupName().equals(groupName)){
                results.add(user);
            }
        }
        return results;
    }
    
    public List<AutomationUser> getAllBy(AutoAccounts account) {
        ArrayList<AutomationUser> results = new ArrayList<AutomationUser>();
        for (AutomationUser item : users) {
            if(item.getAccount().equals(account)) {
                results.add(item);
            }
        }
        return results;
    }
    public List<AutomationUser> getAllBy(AutoAccounts account, Capabilities... capabilities){
        List<AutomationUser> byAccount = getAllBy(account);
        List<AutomationUser> results = new ArrayList<AutomationUser>();
        Set<Capabilities> setCapabilities = new HashSet<Capabilities>();
        for(Capabilities c: capabilities)
            setCapabilities.add(c);
        
        for(AutomationUser login: byAccount){
            if(login.getAllCapabilities().containsAll(setCapabilities)){
                results.add(login);
            }
        }
        return results;  
    }
    
    
    
    public AutomationUser getOneBy(AutoAccounts account, Capabilities... capabilities){
        return getOneBy(getAllBy(account, capabilities));
    }
    
    public AutomationUser getOneBy(List<AutomationUser> list){
        if (!list.isEmpty()) {
            int loginIndex = RandomUtils.nextInt(list.size());
            return list.get(loginIndex);
        } else {
            logger.warn("AutomationLogins.getOneBy(" + list + ") returning null! ");
            return null;
        } 
    }
    public AutomationUser getOneBy(AutoAccounts account){
        return getOneBy(getAllBy(account));    
    }
    public AutomationUser getOneBy(String groupName) {
        return getOneBy(getAllBy(groupName));
    }
    
    public AutomationUser getOneBy(String groupName, Capabilities ...capabilities){
        return getOneBy(getAllBy(groupName, capabilities));
    }

    public AutomationUser getOneBy(Capabilities... capabilities) {
        return getOneBy(getAllBy(capabilities));
    }
    public AutomationUser getOne(){
        return getOneBy(LoginCapabilities.StatusActive);
    }

}
