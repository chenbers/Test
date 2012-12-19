package com.inthinc.pro.automation.webservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.AutomationPropertiesBean;
import com.inthinc.pro.automation.enums.TimeZones;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.models.Account;
import com.inthinc.pro.automation.models.BaseEntity;
import com.inthinc.pro.automation.models.DOTOfficeType;
import com.inthinc.pro.automation.models.FuelEfficiencyType;
import com.inthinc.pro.automation.models.Gender;
import com.inthinc.pro.automation.models.Group;
import com.inthinc.pro.automation.models.GroupStatus;
import com.inthinc.pro.automation.models.GroupType;
import com.inthinc.pro.automation.models.Hazard;
import com.inthinc.pro.automation.models.Hazard.HazardUrls;
import com.inthinc.pro.automation.models.Person;
import com.inthinc.pro.automation.models.Roles;
import com.inthinc.pro.automation.models.Status;
import com.inthinc.pro.automation.models.User;
import com.inthinc.pro.automation.models.Vehicle;
import com.inthinc.pro.automation.rest.RestCommands;

@Ignore
public class TestingWebService {
    
    /**
     * AccountID [429]
     * Account Name [WebServiceTest]
     * GroupID [7622]
     * PersonID [81052]
     * UserID [51100]
     */
    
    private static final String restUsername = "webServiceTest";
    private final String automationUsername;
    private final String password;
    private RestCommands services;
    
    private class Tuple<X, Y> {
        private final X x;
        private final Y y;
        private Tuple(X x, Y y){
            this.x = x;
            this.y = y;
        }
    }
    
    private final Stack<Tuple<Class<? extends BaseEntity>, Integer>> toDelete = new Stack<Tuple<Class<? extends BaseEntity>, Integer>>();
    
    public TestingWebService(){
        AutomationPropertiesBean apb = AutomationPropertiesBean.getPropertyBean();
        automationUsername = apb.getMainAutomation().get(0);
        password = apb.getPassword();
    }
    
    @After
    public void afterTest(){
        if (services != null && !toDelete.isEmpty()){
            while (!toDelete.isEmpty()){
                Tuple<Class<? extends BaseEntity>, Integer> stack = toDelete.pop();
                services.deleteObject(stack.x, stack.y);
            }
        }
    }
    
    private void addToDeleteList(BaseEntity instance, int id){
        toDelete.push(new Tuple<Class<? extends BaseEntity>, Integer>(instance.getClass(), id));
    }
    

    @Test
    @Ignore
    public void testGettingRoadHazard() {
    	
    	services = new RestCommands("0001", "password");
    	
    	HazardUrls custom = new HazardUrls("MCM100343", "18.9", "-89.67");
    	Hazard haz = new Hazard();
    	haz.setCustomUrl(custom);
    	List<Hazard> list = services.getCustomUrl(Hazard.class, haz);
    	for (Hazard hazard : list) {
    		Log.info(hazard);
    	}
    }
    
    @Test
    @Ignore
    public void testUpdateNoChanges(){
        services = new RestCommands(automationUsername, password);
        List<Account> accounts = services.getBulk(Account.class);
        for (Account base : accounts){
            Account original = services.getObject(base.getClass(), base.getAccountID());
            Account updated = services.putObject(original.getClass(), original, null);
            assertEquals("Accounts should be the same", original.toJsonString(), updated.toJsonString());
        }
        
//        List<Device> devices = services.getBulk(Device.class);
//        for (Device base : devices){
//            Device original = services.getObject(base.getClass(), base.getDeviceID());
//            Device updated = services.putObject(original.getClass(), original, null);
//            assertEquals("Devices should be the same", null, updated);
//        }
        
        List<Person> persons = services.getBulk(Person.class);
        for (Person base : persons){
            Person original = services.getObject(base.getClass(), base.getPersonID());
            Person updated = services.putObject(original.getClass(), original, null);
            String a = original.toJsonString();
            String b = updated.toJsonString();
            if (!a.equals(b)){
                Log.info(a); 
                Log.info(b);
            }
            assertEquals("Persons should be the same", original.toJsonString(), updated.toJsonString());
        }
        
        List<Vehicle> vehicles = services.getBulk(Vehicle.class);
        for (Vehicle base : vehicles){
            Vehicle original = services.getObject(base.getClass(), base.getVehicleID());
            Vehicle updated = services.putObject(original.getClass(), original, null);
            assertEquals("Vehicles should be the same", original.toJsonString(), updated.toJsonString());
        }
    }
    
    @Test
    @Ignore
    public void testDivisionToTeam(){
        services = new RestCommands(restUsername, password);
        List<Group> groups = services.getBulk(Group.class);
        assertNotNull("Unable to grab groups", groups);
        Group fleet = null;
        for (Group group : groups){
            if (group.getType().equals(GroupType.FLEET)){
                fleet = group;
                break;
            }
        }
        assertNotNull("Unable to get the fleet", fleet);
        
        Group division = new Group();
        division.setAccountID(fleet.getAccountID());
        division.setParentID(fleet.getGroupID());
        division.setDescription("Testing moving a Division to a team with children");
        division.setDotOfficeType(DOTOfficeType.NONE);
        division.setStatus(GroupStatus.GROUP_ACTIVE);
        division.setName("Test move group to team");
        division.setType(GroupType.DIVISION);
        division = services.createObject(division.getClass(), division);
        
        assertNotNull("Unable to create the division", division);
        assertNotNull("Division ID is null", division.getGroupID());
        
        addToDeleteList(division, division.getGroupID());
        
        Group child = new Group();
        child.setAccountID(fleet.getAccountID());
        child.setParentID(division.getGroupID());
        child.setDescription("Team under division");
        child.setDotOfficeType(DOTOfficeType.NONE);
        child.setStatus(GroupStatus.GROUP_ACTIVE);
        child.setType(GroupType.DIVISION);
        child.setName("Child Team");
        
        child = services.createObject(child.getClass(), child);
        
        assertNotNull("Unable to create the child", division);
        assertNotNull("Unable to create the child", division.getGroupID());
        
        addToDeleteList(child, child.getGroupID());
        
        division.setType(GroupType.TEAM);
        
        try {
            division = services.putObject(division.getClass(), division, null);
            assertTrue("Turned a DIVISION with Children into a TEAM", false);
        } catch (Exception e){
            Log.info(e);
        }
        
        services.deleteObject(Group.class, child.getGroupID());
        
        division = services.putObject(division.getClass(), division, null);
        assertNotNull("Division should have been updated ok", division);
        
        
        services.deleteObject(Group.class, division.getGroupID());
        
    }
    
    
    
    @Test
    @Ignore
    public void testInactivatingEmployee(){
        services = new RestCommands(restUsername, password);
        
        Account account = services.getObject(Account.class, null);
        
        Group fleet = null;
        
        List<Group> groups = services.getBulk(Group.class);
        for (Group group : groups){
            if (group.getType().equals(GroupType.FLEET)){
                fleet = group;
                break;
            }
        }
        
        List<Person> persons = new ArrayList<Person>();
        
        for (int i=1;i<11;i++){
            Person next = new Person();
            next.setAcctID(account.getAccountID());
            next.setCrit(0);
            next.setEmpid(String.format("personWebServiceTest%03d", i)); 
            next.setFirst("First");
            next.setLast("Last");
            next.setFuelEfficiencyType(FuelEfficiencyType.MPG_US);
            next.setGender(Gender.MALE);
            next.setPriEmail(next.getEmpid() + "@tiwisucks.com");
            next.setStatus(Status.ACTIVE);
            next.setTimeZone(TimeZones.US_MOUNTAIN.getTimeZone());
            next.setLocale(Locale.ENGLISH);
            
            
            Person createdPerson = services.createObject(next.getClass(), next);
            assertNotNull("Unable to create Person: " + next, createdPerson);
            next = createdPerson;
            addToDeleteList(next, next.getPersonID());
            
            User user = new User();
            user.setUsername("testingservices" + i);
            user.setPassword("password");
            user.setGroupID(fleet.getGroupID());
            user.setRoles(new Roles());
            user.getRoles().getRole().add(5545);
            user.setStatus(Status.ACTIVE);
            user.setPerson(next);
            
            
            
            User createdUser = services.createObject(user.getClass(), user);
            assertNotNull("Unable to create user: " + user.toJsonString(), createdUser);
            user = createdUser;
            addToDeleteList(user, user.getUserID());
            
            next.setUser(user);
            persons.add(next);
        }
        
        for (Person person : persons){
            Person original = person.autoCopy();
            person.setStatus(Status.INACTIVE);
            person = services.putObject(person.getClass(), person, null);
            assertEquals("Didn't update status", Status.INACTIVE, person.getStatus());
            person.setStatus(Status.ACTIVE);
            person = services.putObject(person.getClass(), person, null);
            assertEquals("Didn't update status", Status.ACTIVE, person.getStatus());
            assertEquals("Should have original object back", original.toJsonString(), person.toJsonString());
        }
    }
    
    
    
}
