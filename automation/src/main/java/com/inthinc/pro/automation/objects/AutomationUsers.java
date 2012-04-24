package com.inthinc.pro.automation.objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.math.RandomUtils;

import com.inthinc.pro.automation.enums.AccountCapability;
import com.inthinc.pro.automation.enums.AutoAccounts;
import com.inthinc.pro.automation.enums.DriverCapability;
import com.inthinc.pro.automation.enums.LoginCapability;
import com.inthinc.pro.automation.interfaces.Capability;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.models.AutomationUser;

public class AutomationUsers {

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
        
        temp = new AutomationUser(AutoAccounts.prime).setupPerson("Waysmart", "", "01", "").setupUser("waysmart01", "password", "Tina's Auto Team").setupDriver("Tina's Auto Team").addLoginCapabilities(LoginCapability.HasWaySmart, LoginCapability.TeamLevelLogin, LoginCapability.IsDriver, LoginCapability.StatusActive, LoginCapability.HasVehicle, LoginCapability.RoleHOS);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("akumer", "password", "Top").addLoginCapabilities(LoginCapability.RoleAdmin, LoginCapability.FleetLevelLogin);        users.add(temp);        
        temp = new AutomationUser(AutoAccounts.prime).setupUser("autovehicle", "passw0rd", "AdminVehicleTests").addLoginCapabilities(LoginCapability.TeamLevelLogin);        users.add(temp);        
        temp = new AutomationUser(AutoAccounts.prime).setupUser("inactive", "password", "Test Group WR").addLoginCapabilities(LoginCapability.TeamLevelLogin);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("CaptainNemo", "Muttley", "Test Group RW").addLoginCapabilities(LoginCapability.TeamLevelLogin);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("noteTester01", "password", "Test Group WR").addLoginCapabilities(LoginCapability.NoteTesterData, LoginCapability.TeamLevelLogin);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("Daisy1", "password", "Tina's Auto Team").addLoginCapabilities(LoginCapability.TeamLevelLogin, LoginCapability.IsDriver).addDriverCapabilities(DriverCapability.RuleSet_Texas);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.QA).setupUser("danniauto", "password", "Top").addAccountCapabilities(AccountCapability.PasswordExpire90, AccountCapability.PasswordRequireInitalChangeRequire, AccountCapability.HOSEnabled, AccountCapability.LoginExpire90);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("dastardly", "Muttley", "Test Group WR").setupDriver("Test Group WR").addLoginCapabilities(LoginCapability.NoteTesterData, LoginCapability.TeamLevelLogin, LoginCapability.RoleAdmin);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("hosDriver00", "password", "Top").setupDriver("Tina's Auto Team").addLoginCapabilities(LoginCapability.IsDriver, LoginCapability.FleetLevelLogin,LoginCapability.HasVehicle,LoginCapability.HasWaySmart,LoginCapability.RoleAdmin).addDriverCapabilities(DriverCapability.RuleSet_US8Day);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.QA).setupUser("hosDriver01", "password", "Top").addAccountCapabilities(AccountCapability.PasswordExpire90, AccountCapability.PasswordRequireInitalChangeRequire, AccountCapability.HOSEnabled, AccountCapability.LoginExpire90).addLoginCapabilities(LoginCapability.IsDriver, LoginCapability.FleetLevelLogin);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("jesse1", "password", "Tina's Auto Team").setupDriver("Tina's Auto Team").addLoginCapabilities(LoginCapability.TeamLevelLogin).addDriverCapabilities(DriverCapability.RuleSet_USOil8Day);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("pitstop", "Muttley", "Top").addLoginCapabilities(LoginCapability.NoteTesterData, LoginCapability.RoleAdmin);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("tinaauto", "password", "Tina's Auto Team").setupDriver("Tina's Auto Team").addLoginCapabilities(LoginCapability.RoleAdmin,LoginCapability.IsDriver, LoginCapability.TeamLevelLogin).addDriverCapabilities(DriverCapability.RuleSet_Canada2007_60DegreesOilFieldPermit);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("whiplash", "Muttley", "Test Group WR").addLoginCapabilities(LoginCapability.StatusInactive);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("passwordchanger00", "password", "Top").addLoginCapabilities(LoginCapability.PasswordChanging, LoginCapability.FleetLevelLogin);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("passwordchanger01", "password", "Top").addLoginCapabilities(LoginCapability.PasswordChanging, LoginCapability.FleetLevelLogin);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("passwordchanger02", "password", "Top").addLoginCapabilities(LoginCapability.PasswordChanging, LoginCapability.FleetLevelLogin);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("tiwi00", "password", "Tina's Auto Team").setupDriver("Tina's Auto Team").addLoginCapabilities(LoginCapability.HasTiwiPro, LoginCapability.TeamLevelLogin,LoginCapability.IsDriver,LoginCapability.HasVehicle,LoginCapability.RoleAdmin).addDriverCapabilities(DriverCapability.RuleSet_Canada2007_60DegreesOilFieldPermit);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("tiwi01", "password", "Tina's Auto Team").setupDriver("Tina's Auto Team").addLoginCapabilities(LoginCapability.HasTiwiPro, LoginCapability.TeamLevelLogin,LoginCapability.IsDriver,LoginCapability.HasVehicle,LoginCapability.RoleAdmin);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("tiwi02", "password", "Tina's Auto Team").setupDriver("Tina's Auto Team").addLoginCapabilities(LoginCapability.HasTiwiPro, LoginCapability.TeamLevelLogin,LoginCapability.IsDriver,LoginCapability.HasVehicle,LoginCapability.RoleAdmin).addDriverCapabilities(DriverCapability.RuleSet_Canada2007OilFieldPermit);        users.add(temp);
        temp = new AutomationUser(AutoAccounts.prime).setupUser("tiwi03", "password", "Tina's Auto Team").setupDriver("Tina's Auto Team").addLoginCapabilities(LoginCapability.HasTiwiPro, LoginCapability.TeamLevelLogin,LoginCapability.IsDriver,LoginCapability.HasVehicle);        users.add(temp);
        
        users.add(new AutomationUser(AutoAccounts.prime).setupUser("invalidUsername", "invalidPassword", "").addLoginCapabilities(LoginCapability.PasswordInvalid));
        users.add(new AutomationUser(AutoAccounts.prime).setupUser("noRoleUserInfo", "password", "Top").addLoginCapabilities(LoginCapability.NoAccessPointUserInfo, LoginCapability.NoAccessPointVehicleInfo));
    }
    
    public List<AutomationUser> getAllBy(Capability... capabilities){
        ArrayList<AutomationUser> results = new ArrayList<AutomationUser>();
        Set<Capability> setCapabilities = new HashSet<Capability>();
        for(Capability c: capabilities){
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
    
    public List<AutomationUser> getAllBy(String groupName, Capability ...abilities){
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
    public List<AutomationUser> getAllBy(AutoAccounts account, Capability... capabilities){
        List<AutomationUser> byAccount = getAllBy(account);
        List<AutomationUser> results = new ArrayList<AutomationUser>();
        Set<Capability> setCapabilities = new HashSet<Capability>();
        for(Capability c: capabilities)
            setCapabilities.add(c);
        
        for(AutomationUser login: byAccount){
            if(login.getAllCapabilities().containsAll(setCapabilities)){
                results.add(login);
            }
        }
        return results;  
    }
    
    
    
    public AutomationUser getOneBy(AutoAccounts account, Capability... capabilities){
        return getOneBy(getAllBy(account, capabilities));
    }
    
    public AutomationUser getOneBy(List<AutomationUser> list){
        if (!list.isEmpty()) {
            int loginIndex = RandomUtils.nextInt(list.size());
            return list.get(loginIndex);
        } else {
            Log.warning("AutomationLogins.getOneBy(" + list + ") returning null! ");
            return null;
        } 
    }
    public AutomationUser getOneBy(AutoAccounts account){
        return getOneBy(getAllBy(account));    
    }
    public AutomationUser getOneBy(String groupName) {
        return getOneBy(getAllBy(groupName));
    }
    
    public AutomationUser getOneBy(String groupName, Capability ...capabilities){
        return getOneBy(getAllBy(groupName, capabilities));
    }

    public AutomationUser getOneBy(Capability... capabilities) {
        return getOneBy(getAllBy(capabilities));
    }
    public AutomationUser getOne(){
        return getOneBy(LoginCapability.StatusActive);
    }

}
