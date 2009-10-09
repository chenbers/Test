package com.inthinc.pro.util;

import org.jboss.resteasy.spi.NotFoundException;
import org.jboss.resteasy.spi.UnauthorizedException;
import org.springframework.security.context.SecurityContextHolder;

import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.security.userdetails.ProUser;

public class SecurityBean {

    private UserDAO userDAO;
    private GroupDAO groupDAO;
    private VehicleDAO vehicleDAO;
    private DeviceDAO deviceDAO;
    private DriverDAO driverDAO;
    private PersonDAO personDAO;
    private AddressDAO addressDAO;

    public ProUser getProUser() {
        return (ProUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public User getUser() {
        return getProUser().getUser();
    }

    public Person getPerson() {
        return getUser().getPerson();
    }

    public Integer getGroupID() {
        return getUser().getGroupID();
    }

    public Integer getAccountID() {
        return getUser().getPerson().getAcctID();
    }

    public boolean isAuthorized(Vehicle vehicle) {
        if (vehicle != null) {
            // TODO do we give user access to all groups, regardless of the users group????
            // TODO if so, we need a fast security check to verify a group intersects with user's groups
            // TODO get Account from logged in user
            Group vehiclegroup = getGroup(vehicle.getGroupID());

            if (vehiclegroup != null && getAccountID().equals(vehiclegroup.getAccountID()))
                return true;

        }
        throw new UnauthorizedException();
    }

    public boolean isAuthorized(Device device) {
        if (device != null) {
            // TODO do we give user access to all groups, regardless of the users group????
            // TODO if so, we need a fast security check to verify a group intersects with user's groups
            // TODO get Account from logged in user

            if (getAccountID().equals(device.getAccountID()))
                return true;

        }
        throw new UnauthorizedException();
    }

    public boolean isAuthorized(Driver driver) {
        if (driver != null) {
            // TODO do we give user access to all groups, regardless of the users group????
            // TODO if so, we need a fast security check to verify a group intersects with user's groups
            // TODO get Account from logged in user
            Person person = getPerson(driver.getPersonID());
            if (person == null)
                throw new UnauthorizedException();

            if (!person.getAcctID().equals(getAccountID()))
                throw new UnauthorizedException();

            Group drivergroup = groupDAO.findByID(driver.getGroupID());

            if (drivergroup != null && getAccountID().equals(drivergroup.getAccountID()))
                return true;

        }
        throw new UnauthorizedException();
    }

    public boolean isAuthorized(User user) {
        if (user != null) {
            // TODO do we give user access to all groups, regardless of the users group????
            // TODO if so, we need a fast security check to verify a group intersects with user's groups
            // TODO get Account from logged in user

            if (user.getPerson() == null) {
                Person person = getPerson(user.getPersonID());
                user.setPerson(person);
            }

            if (getAccountID().equals(user.getPerson().getAcctID()))
                return true;

        }
        throw new UnauthorizedException();
    }

    public boolean isAuthorized(Person person) {
        if (person != null) {
            // TODO do we give user access to all groups, regardless of the users group????
            // TODO if so, we need a fast security check to verify a group intersects with user's groups
            // TODO get Account from logged in user
            if (getAccountID().equals(person.getAcctID()))
                return true;

        }
        throw new UnauthorizedException();
    }

    public boolean isAuthorized(Group group) {
        if (group != null) {
            // TODO do we give user access to all groups, regardless of the users group????
            // TODO if so, we need a fast security check to verify a group intersects with user's groups
            // TODO get Account from logged in user

            if (getAccountID().equals(group.getAccountID()))
                return true;

            throw new UnauthorizedException("accountID not found" + group.getAccountID());
        }
        throw new UnauthorizedException("Group not found");
    }

    public boolean isAuthorized(Address address) {
        if (address != null) {
        	//TODO We need an accountID element in Address
        	//DANGER WILL ROBINSON
//          if (getAccountID().equals(group.getAccountID()))

                return true;

        }
        throw new UnauthorizedException();
    }
    
    public Vehicle getVehicle(Integer vehicleID)
    {
        Vehicle vehicle = vehicleDAO.findByID(vehicleID);
        if (vehicle == null)
            throw new NotFoundException("vehicleID not found: " + vehicleID);
        return vehicle;
    }

    public Vehicle getVehicleByVIN(String vin)
    {
        Vehicle vehicle = vehicleDAO.findByVIN(vin);
        if (vehicle == null)
            throw new NotFoundException("vehicle VIN not found: " + vin);
        return vehicle;
    }

    public Driver getDriver(Integer driverID) {
        Driver driver = driverDAO.findByID(driverID);
        if (driver == null)
            throw new NotFoundException("driverID not found: " + driverID);
        return driver;
    }

    public User getUser(Integer userID) {
        User user = userDAO.findByID(userID);
        if (user == null)
            throw new NotFoundException("userID not found: " + userID);
        return user;
    }

    public Person getPerson(Integer personID) {
        Person person = personDAO.findByID(personID);
        if (person == null)
            throw new NotFoundException("personID not found: " + personID);
        return person;
    }

    public Device getDevice(Integer deviceID) {
        Device device = deviceDAO.findByID(deviceID);
        if (device == null)
            throw new NotFoundException("deviceID not found: " + deviceID);
        return device;
    }

    public Device getDeviceByIMEI(String imei) {
        Device device = deviceDAO.findByIMEI(imei);
        if (device == null)
            throw new NotFoundException("device IMEI not found: " + imei);
        return device;
    }

    public Device getDeviceBySerialNum(String serialNum) {
        Device device = deviceDAO.findBySerialNum(serialNum);
        if (device == null)
            throw new NotFoundException("device serialNum not found: " + serialNum);
        return device;
    }

    public Group getGroup(Integer groupID) {
        Group group = groupDAO.findByID(groupID);
        if (group == null)
            throw new NotFoundException("groupID not found: " + groupID);
        return group;
    }

    public Address getAddress(Integer addressID) {
        Address address = addressDAO.findByID(addressID);
        if (address == null)
            throw new NotFoundException("addressID not found: " + addressID);
        return address;
    }    
    
    // TODO this is dangerous because parameters are not strongly typed
    // It would be easy to send in an id for the wrong entity type
    public boolean isAuthorizedByVehicleID(Integer vehicleID) {
        return isAuthorized(getVehicle(vehicleID));
    }

    // TODO this is dangerous because parameters are not strongly typed
    // It would be easy to send in an id for the wrong entity type
    public boolean isAuthorizedByDriverID(Integer driverID) {
        return isAuthorized(getDriver(driverID));
    }

    // TODO this is dangerous because parameters are not strongly typed
    // It would be easy to send in an id for the wrong entity type
    public boolean isAuthorizedByUserID(Integer userID) {
        return isAuthorized(getUser(userID));
    }

    // TODO this is dangerous because parameters are not strongly typed
    // It would be easy to send in an id for the wrong entity type
    public boolean isAuthorizedByPersonID(Integer personID) {
        return isAuthorized(getPerson(personID));
    }

    // TODO this is dangerous because parameters are not strongly typed
    // It would be easy to send in an id for the wrong entity type
    public boolean isAuthorizedByDeviceID(Integer deviceID) {
        return isAuthorized(getDevice(deviceID));
    }

    // TODO this is dangerous because parameters are not strongly typed
    // It would be easy to send in an id for the wrong entity type
    public boolean isAuthorizedByGroupID(Integer groupID) {
        return isAuthorized(getGroup(groupID));
    }

    // TODO this is dangerous because parameters are not strongly typed
    // It would be easy to send in an id for the wrong entity type
    public boolean isAuthorizedByAddressID(Integer addressID) {
        return isAuthorized(getAddress(addressID));
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public DeviceDAO getDeviceDAO() {
        return deviceDAO;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public AddressDAO getAddressDAO() {
        return addressDAO;
    }

    public void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }
}
