package com.inthinc.pro.automation.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.models.BaseEntity;
import com.inthinc.pro.automation.models.Device;
import com.inthinc.pro.automation.models.Driver;
import com.inthinc.pro.automation.models.Group;
import com.inthinc.pro.automation.models.Person;
import com.inthinc.pro.automation.models.Roles;
import com.inthinc.pro.automation.models.User;
import com.inthinc.pro.automation.models.Vehicle;

public class RestFilters {

    
    private RestCommands rest;
    
    public RestFilters(String userName, String password){
        rest = new RestCommands(userName, password);
    }
        
    public List<Person> getPersons(){
        List<Person> list = rest.getBulk(Person.class);
        Iterator<Person> itr = list.iterator();
        List<Integer> idList = new ArrayList<Integer>();
        while (itr.hasNext()){
            idList.add(itr.next().getPersonID());
        }
        return getBulk(Person.class, idList);
    }
    
    public List<User> getUsers(){
        List<User> list = rest.getBulk(User.class);
        Iterator<User> itr = list.iterator();
        List<Integer> idList = new ArrayList<Integer>();
        while (itr.hasNext()){
            idList.add(itr.next().getUserID());
        }
        return getBulk(User.class, idList);
    }
    
    public List<Vehicle> getVehicles(){
        List<Vehicle> list = rest.getBulk(Vehicle.class);
        Iterator<Vehicle> itr = list.iterator();
        List<Integer> idList = new ArrayList<Integer>();
        while (itr.hasNext()){
            idList.add(itr.next().getVehicleID());
        }
        return getBulk(Vehicle.class, idList);
    }
    
    public List<Group> getGroups(){
        List<Group> list = rest.getBulk(Group.class);
        Iterator<Group> itr = list.iterator();
        List<Integer> idList = new ArrayList<Integer>();
        while (itr.hasNext()){
            idList.add(itr.next().getGroupID());
        }
        return getBulk(Group.class, idList);
    }
    
    public List<Driver> getDrivers(){
        List<Driver> list = rest.getBulk(Driver.class);
        Iterator<Driver> itr = list.iterator();
        List<Integer> idList = new ArrayList<Integer>();
        while (itr.hasNext()){
            idList.add(itr.next().getDriverID());
        }
        return getBulk(Driver.class, idList);
    }
    
    public List<Device> getDevices(){
        List<Device> list = rest.getBulk(Device.class);
        Iterator<Device> itr = list.iterator();
        List<Integer> idList = new ArrayList<Integer>();
        while (itr.hasNext()){
            idList.add(itr.next().getDeviceID());
        }
        return getBulk(Device.class, idList);
    }
    
    public List<Roles> getRoles(){
        List<Roles> list = rest.getBulk(Roles.class);
        
        return list;
    }
    
    private <T extends BaseEntity> List<T> getBulk(Class<T> clazz, List<Integer> ids){
        Iterator<Integer> itr = ids.iterator();
        List<T> fullList = new ArrayList<T>();
        while (itr.hasNext()){
            T full = rest.getObject(clazz, itr.next());
            fullList.add(full);
        }
        return fullList;
    }
    
    
    public static void main(String[] args){
        RestFilters test = new RestFilters("darth", "password");
        Log.info(test.getRoles());
    }
    
    
}
