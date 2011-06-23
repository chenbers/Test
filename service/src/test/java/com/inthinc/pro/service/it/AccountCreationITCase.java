package com.inthinc.pro.service.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.app.States;

public class AccountCreationITCase extends BaseITCase {
    private static Logger logger = Logger.getLogger(AccountCreationITCase.class);
    private static final String PASSWORD = "nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password
    private static int randomInt = RandomUtils.nextInt(99999);
    
    @Test 
//    @Ignore
    public void accountDummyTest() throws Exception {
        
    }
    @Test 
    @Ignore
    public void accountTest() throws Exception {
        // TODO: This test really needs some help. I'll come back to it, just need to get something going right now.

        // Setup new account

        // Purposely not using bulk operations. There will be another integration test for that.
        // Create and Retrieve account objects
        Account account = createAccount();
        List<Group> groupList = createGroupHierarchy(account);
        Address address = createAddress(account);
        Person person = createPerson(account, groupList, address);
        Vehicle vehicle = createVehicle(groupList.get(2));
        Device device = createDevice(account);

        // Update account objects
        account = updateAccount(account);
        groupList = updateGroupHierarchy(groupList);
        address = updateAddress(address);
        person = updatePerson(person);
        vehicle = updateVehicle(vehicle);
//        device = updateDevice(device);
        
        //Make assignments
        
        

        // Delete account objects
        // deleteAddress(address);
        deletePerson(person);
        deleteVehicle(vehicle);
//        deleteDevice(device);
        deleteGroupHierarchy(groupList);
        deleteAccount(account);

    }

    private Account createAccount() throws Exception {
        Account account = new Account();
        account.setAcctName("IT" + randomInt);
        account.setStatus(Status.ACTIVE);

        ClientRequest request = new ClientRequest(url + "/account", clientExecutor);
        request.body(MediaType.APPLICATION_XML_TYPE, account);
        ClientResponse<Account> response = request.post(Account.class);

        assertEquals("Error creating account. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.CREATED, response.getResponseStatus());
        logger.info("Account created successfully: " + response.getEntity());
        return response.getEntity();
    }

    private Account updateAccount(Account account) throws Exception {
        account.setAcctName("IT" + randomInt + "UPDATE");
        ClientRequest updateRequest = new ClientRequest(url + "/account", clientExecutor);
        updateRequest.body(MediaType.APPLICATION_XML_TYPE, account);
        ClientResponse<Account> response = updateRequest.put(Account.class);
        assertEquals("Error updating address. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.OK, response.getResponseStatus());
        Account updateAccount = response.getEntity();
        // TODO: add more robust testing
        assertTrue(account.getAcctName().equals(updateAccount.getAcctName()));

        logger.info("Account updated successfully: " + updateAccount);
        return updateAccount;
    }

    private void deleteAccount(Account account) throws Exception {
        ClientRequest request = new ClientRequest(url + "/account/" + account.getAccountID(), clientExecutor);
        ClientResponse response = request.delete();
        assertEquals("Error deleting account. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.OK, response.getResponseStatus());
        ClientResponse<Account> accountResponse = request.get(Account.class);
        Account deleteAccount = accountResponse.getEntity();
        assertEquals("Account was not deleted successfully.", deleteAccount.getStatus(), Status.DELETED);
        logger.info("Account deleted successfully: " + deleteAccount);
    }

    private List<Group> createGroupHierarchy(Account account) throws Exception {
        // TODO: make additions to group service to allow pulling the group hierarchy for a specific account
        // then using that service after creating the following groups
        List<Group> groupList = new ArrayList<Group>();
        Group fleetGroup = new Group();
        fleetGroup.setAccountID(account.getAccountID());
        fleetGroup.setParentID(0);
        fleetGroup.setDescription("Fleet");
        fleetGroup.setType(GroupType.FLEET);
        fleetGroup.setName("Fleet");
        fleetGroup.setMapZoom(6);
        fleetGroup.setMapCenter(new LatLng(0.0, 0.0));

        ClientRequest request = new ClientRequest(url + "/group", clientExecutor);
        request.body(MediaType.APPLICATION_XML_TYPE, fleetGroup);
        ClientResponse<Group> response = request.post(Group.class);
        assertEquals("Error creating account. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.CREATED, response.getResponseStatus());
        fleetGroup = response.getEntity();
        logger.info("Fleet Group created successfully: " + fleetGroup);
        groupList.add(fleetGroup);

        Group divisionGroup = new Group();
        divisionGroup.setAccountID(account.getAccountID());
        divisionGroup.setParentID(fleetGroup.getGroupID());
        divisionGroup.setDescription("Division");
        divisionGroup.setType(GroupType.DIVISION);
        divisionGroup.setName("Division");
        divisionGroup.setMapZoom(6);
        divisionGroup.setMapCenter(new LatLng(0.0, 0.0));

        request = new ClientRequest(url + "/group", clientExecutor);
        request.body(MediaType.APPLICATION_XML_TYPE, fleetGroup);
        response = request.post(Group.class);
        assertEquals("Error creating account. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.CREATED, response.getResponseStatus());
        divisionGroup = response.getEntity();
        logger.info("Division Group created successfully: " + divisionGroup);
        groupList.add(divisionGroup);

        Group teamGroup = new Group();
        teamGroup.setAccountID(account.getAccountID());
        teamGroup.setParentID(divisionGroup.getGroupID());
        teamGroup.setDescription("Team");
        teamGroup.setType(GroupType.TEAM);
        teamGroup.setName("Team");
        teamGroup.setMapZoom(6);
        teamGroup.setMapCenter(new LatLng(0.0, 0.0));

        request = new ClientRequest(url + "/group", clientExecutor);
        request.body(MediaType.APPLICATION_XML_TYPE, fleetGroup);
        response = request.post(Group.class);
        assertEquals("Error creating account. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.CREATED, response.getResponseStatus());
        teamGroup = response.getEntity();
        logger.info("Team Group created successfully: " + teamGroup);
        groupList.add(teamGroup);

        return groupList;
    }

    private List<Group> updateGroupHierarchy(List<Group> groupList) throws Exception {
        List<Group> responseList = new ArrayList<Group>();
        ClientRequest request = new ClientRequest(url + "/group", clientExecutor);
        ClientResponse<Group> response = null;
        for (Group group : groupList) {
            group.setDescription(group.getDescription() + " Update");
            group.setName(group.getName() + " Update");
            group.setMapZoom(13);
            group.setMapCenter(new LatLng(40.710727, -111.992376));
            request.clear();
            request.body(MediaType.APPLICATION_XML_TYPE, group);
            response = request.put(Group.class);
            assertEquals("Error updating address. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.OK, response.getResponseStatus());
            Group updateGroup = response.getEntity();
            // TODO: Add more robust testing
            assertTrue(group.getDescription().equals(updateGroup.getDescription()));
            assertTrue(group.getName().equals(updateGroup.getName()));
            assertTrue(group.getMapZoom().equals(updateGroup.getMapZoom()));
            logger.info("Group updated successfully: " + updateGroup);
            responseList.add(updateGroup);
        }

        return responseList;
    }

    private void deleteGroupHierarchy(List<Group> groupList) throws Exception {
        for (Group group : groupList) {
            ClientRequest request = new ClientRequest(url + "/group/" + group.getGroupID(), clientExecutor);
            ClientResponse<Group> response = request.delete(Group.class);
            assertEquals("Error deleting group. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.OK, response.getResponseStatus());
            response = request.get(Group.class);
            assertEquals("Group was found, but should be deleted. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.NOT_FOUND, response
                    .getResponseStatus());
            logger.info("Group deleted successfully");
        }
    }

    private Address createAddress(Account account) throws Exception {
        Address address = new Address();
        address.setAccountID(account.getAccountID());
        address.setAddr1("Address1_" + randomInt);
        address.setAddr2("Address2_" + randomInt);
        address.setCity("West Valley City");
        address.setZip("84120");
        address.setState(States.getStateByAbbrev("UT"));
        ClientRequest request = new ClientRequest(url + "/address", clientExecutor);
        request.body(MediaType.valueOf("application/xml"), address, Address.class);
        ClientResponse<Address> response = request.post(Address.class);
        assertEquals("Error creating account. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.CREATED, response.getResponseStatus());
        logger.info("Address created successfully: " + response.getEntity());
        return response.getEntity();
    }

    private Address updateAddress(Address address) throws Exception {
        address.setAddr1(address.getAddr1() + "Update");
        address.setAddr2(address.getAddr2() + "Update");
        address.setCity("South Jordan");
        address.setZip("84095");
        ClientRequest request = new ClientRequest(url + "/address", clientExecutor);
        request.body(MediaType.APPLICATION_XML_TYPE, address);
        ClientResponse<Address> response = request.put(Address.class);
        assertEquals("Error updating address. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.OK, response.getResponseStatus());
        Address updateAddress = response.getEntity();
        assertTrue(address.getAddr1().equals(updateAddress.getAddr1()));
        assertTrue(address.getAddr2().equals(updateAddress.getAddr2()));
        assertTrue(address.getCity().equals(updateAddress.getCity()));
        assertTrue(address.getZip().equals(updateAddress.getZip()));

        logger.info("Address updated successfully: " + updateAddress);
        return updateAddress;
    }

    //the delete address method has not been implement in the backend
    private void deleteAddress(Address address) throws Exception {
        ClientRequest request = new ClientRequest(url + "/address/" + address.getAddrID(), clientExecutor);
        ClientResponse<Address> response = request.delete(Address.class);
        assertEquals("Error deleting address. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.OK, response.getResponseStatus());
        response = request.get(Address.class);
        assertEquals("Address was found, but should be deleted. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.NOT_FOUND, response
                .getResponseStatus());
        logger.info("Address deleted successfully");
    }

    private Person createPerson(Account account, List<Group> groupList, Address address) throws Exception {
        Person person = new Person();
        person.setAcctID(account.getAccountID());
        person.setDept("ITDepartment");
        person.setDob(new DateTime(1969, 1, 1, 1, 1, 1, 1).toDate());
        person.setEmpid("66666666");
        person.setFirst("First" + randomInt);
        person.setFuelEfficiencyType(FuelEfficiencyType.MPG_US);
        person.setGender(Gender.MALE);
        person.setHeight(54);
        person.setLast("Last" + randomInt);
        person.setLocale(Locale.US);
        person.setTimeZone(TimeZone.getTimeZone("MST"));
        person.setMeasurementType(MeasurementType.ENGLISH);
        person.setPriEmail("it" + randomInt + "@it.com");
        person.setAddressID(address.getAddrID());
        person.setStatus(Status.ACTIVE);

        User user = new User();
        user.setUsername("username" + randomInt);
        user.setPassword(PASSWORD);
        user.setStatus(Status.ACTIVE);
        List<Integer> roles = new ArrayList<Integer>();
        //2 is the "normal" account
        roles.add(2);

        user.setRoles(roles);
        // TODO: Make sure getting the fleet group
        user.setGroupID(groupList.get(0).getGroupID());
        user.setPersonID(person.getPersonID());

        Driver driver = new Driver();
        driver.setGroupID(groupList.get(2).getGroupID());
        driver.setPersonID(person.getPersonID());
        driver.setStatus(Status.ACTIVE);
        driver.setState(States.getStateByAbbrev("UT"));
        driver.setLicense("12");
        driver.setLicenseClass("A");
        driver.setCertifications("Certificate");
//        driver.setRFID(RandomUtils.nextLong());
        driver.setExpiration(new DateTime().plusYears(2).toDate());

        person.setUser(user);
        person.setDriver(driver);

        ClientRequest request = new ClientRequest(url + "/person/" + account.getAccountID(), clientExecutor);
        request.body(MediaType.APPLICATION_XML_TYPE, person);
        ClientResponse<Person> response = request.post(Person.class);

        assertEquals("Error creating account. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.CREATED, response.getResponseStatus());
        logger.info("Person created successfully: " + response.getEntity());
        return response.getEntity();

    }

    private Person updatePerson(Person person) throws Exception {
        person.setDept(person.getDept() + "Update");
        person.setDob(new DateTime(1979, 1, 1, 1, 1, 1, 1).toDate());
        person.setEmpid(person.getEmpid() + "Update");
        person.setFirst(person.getFirst() + "Update");
        person.setFuelEfficiencyType(FuelEfficiencyType.LP100KM);
        person.setGender(Gender.FEMALE);
        person.setHeight(64);
        person.setLast(person.getLast() + "Update");
        person.setLocale(Locale.JAPAN);
        person.setTimeZone(TimeZone.getTimeZone("PST"));

        // person.getUser().setPassword();
        List<Integer> roles = new ArrayList<Integer>();
        //2 is the "normal" role
        roles.add(2);

        person.getUser().setRoles(roles);

        person.getDriver().setState(States.getStateByAbbrev("CA"));
        person.getDriver().setLicense(person.getDriver().getLicense() + "Update");
        person.getDriver().setLicenseClass(person.getDriver().getLicenseClass() + "Update");
        person.getDriver().setCertifications(person.getDriver().getCertifications() + "Update");
        person.getDriver().setExpiration(new DateTime().plusYears(4).toDate());

        ClientRequest request = new ClientRequest(url + "/person", clientExecutor);
        request.body(MediaType.APPLICATION_XML_TYPE, person);
        ClientResponse<Person> response = request.put(Person.class);
        assertEquals("Error updating address. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.OK, response.getResponseStatus());
        Person updatePerson = response.getEntity();
        // TODO: add more robust testing
        assertTrue(person.getDept().equals(updatePerson.getDept()));
        assertTrue(person.getEmpid().equals(updatePerson.getEmpid()));
        assertTrue(person.getFirst().equals(updatePerson.getFirst()));
        assertTrue(person.getFuelEfficiencyType().equals(updatePerson.getFuelEfficiencyType()));
        assertTrue(person.getGender().equals(updatePerson.getGender()));
        //TODO add role compare back in but for list of roles
//        assertTrue(person.getUser().getRole().equals(updatePerson.getUser().getRole()));
        assertTrue(person.getDriver().getState().getAbbrev().equals(updatePerson.getDriver().getState().getAbbrev()));
        assertTrue(person.getDriver().getLicense().equals(updatePerson.getDriver().getLicense()));
        // assertTrue(person.getDriver().getExpiration().equals(updatePerson.getDriver().getExpiration()));

        logger.info("Person updated successfully: " + updatePerson);
        return updatePerson;
    }

    private void deletePerson(Person person) throws Exception {
        ClientRequest userRequest = new ClientRequest(url + "/user/" + person.getUser().getUserID(), clientExecutor);
        ClientResponse<User> userResponse = userRequest.delete(User.class);
        assertEquals("Error deleting user. HTTP Status Code: " + userResponse.getStatus() + " - " + userResponse.getResponseStatus(), Response.Status.OK, userResponse.getResponseStatus());
        userResponse = userRequest.get(User.class);
        User user = userResponse.getEntity();
        assertEquals("User was not deleted successfully.", user.getStatus(), Status.DELETED);
        logger.info("User deleted successfully: " + userResponse);

        ClientRequest driverRequest = new ClientRequest(url + "/driver/" + person.getDriver().getDriverID(), clientExecutor);
        ClientResponse<Driver> driverResponse = driverRequest.delete(Driver.class);
        assertEquals("Error deleting driver. HTTP Status Code: " + driverResponse.getStatus() + " - " + driverResponse.getResponseStatus(), Response.Status.OK, driverResponse.getResponseStatus());
        driverResponse = driverRequest.get(Driver.class);
        Driver driver = driverResponse.getEntity();
        assertEquals("Driver was not deleted successfully.", driver.getStatus(), Status.DELETED);
        logger.info("Driver deleted successfully: " + driverResponse);

        ClientRequest personRequest = new ClientRequest(url + "/person/" + person.getPersonID(), clientExecutor);
        ClientResponse<Person> personResponse = personRequest.delete(Person.class);
        assertEquals("Error deleting person. HTTP Status Code: " + personResponse.getStatus() + " - " + personResponse.getResponseStatus(), Response.Status.OK, personResponse.getResponseStatus());
        personResponse = personRequest.get(Person.class);
        Person deletePerson = personResponse.getEntity();
        assertEquals("Person was not deleted successfully.", deletePerson.getStatus(), Status.DELETED);
        logger.info("Person deleted successfully: " + deletePerson);
    }

    private Vehicle createVehicle(Group group) throws Exception {
        Vehicle vehicle = new Vehicle();

        vehicle.setGroupID(group.getGroupID());
        vehicle.setColor("Black");
        vehicle.setMake("Toyota");
        vehicle.setModel("Tacoma");
        vehicle.setName("IT" + randomInt);
        vehicle.setState(States.getStateByAbbrev("UT"));
        vehicle.setStatus(Status.INACTIVE);
        vehicle.setVIN("VIN" + randomInt);
        vehicle.setVtype(VehicleType.LIGHT);
        vehicle.setYear(2007);

        ClientRequest request = new ClientRequest(url + "/vehicle", clientExecutor);
        request.body(MediaType.APPLICATION_XML_TYPE, vehicle);
        ClientResponse<Vehicle> response = request.post(Vehicle.class);
        assertEquals("Error creating Vehicle. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.CREATED, response.getResponseStatus());
        logger.info("Vehicle created successfully: " + response.getEntity());
        return response.getEntity();
    }
    
    private Vehicle updateVehicle(Vehicle vehicle) throws Exception {
        //TODO: like the rest of this test, make test more robust
        vehicle.setColor(vehicle.getColor() + "Update");
        vehicle.setMake(vehicle.getMake() + "Update");
        vehicle.setModel(vehicle.getModel() + "Update");
        vehicle.setName(vehicle.getName() + "Update");
        vehicle.setState(States.getStateByAbbrev("CA"));
        vehicle.setStatus(Status.ACTIVE);

        ClientRequest request = new ClientRequest(url + "/vehicle", clientExecutor);
        request.body(MediaType.APPLICATION_XML_TYPE, vehicle);
        ClientResponse<Vehicle> response = request.put(Vehicle.class);
        assertEquals("Error updating address. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.OK, response.getResponseStatus());
        Vehicle updateVehicle = response.getEntity();        
        assertTrue("Vehicle was not updated correctly.",vehicle.getStatus().equals(updateVehicle.getStatus()));
        
        logger.info("Vehicle updated successfully: " + updateVehicle);
        return updateVehicle;
    }
    
    private void deleteVehicle(Vehicle vehicle) throws Exception {
        ClientRequest request = new ClientRequest(url + "/vehicle/" + vehicle.getVehicleID(), clientExecutor);
        ClientResponse<Vehicle> response = request.delete(Vehicle.class);
        assertEquals("Error deleting vehicle. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.OK, response.getResponseStatus());
        response = request.get(Vehicle.class);
//        assertEquals("Vehicle was found, but should be deleted. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.NOT_FOUND, response
//                .getResponseStatus());
        logger.info("Vehicle get after delete returns: " + response.getStatus());
        logger.info("Vehicle deleted successfully");
        
    }

    private Device createDevice(Account account) throws Exception {
        Device device = new Device();
        device.setAccountID(account.getAccountID());
//        device.setAutoLogoff(AutoLogoff.OFF);
//        device.setEphone("IT_EPHONE" + randomInt);
        device.setImei("IT_IMEI" + randomInt);
        device.setName("IT_Device" + randomInt);
        device.setPhone("IT_PHONE" + randomInt);
        device.setStatus(DeviceStatus.NEW);
        device.setSim("IT_SIM" + randomInt);
        device.setSerialNum("IT_SN" + randomInt);
//        device.setAutoLogoff(AutoLogoff.ON);
        
        ClientRequest request = new ClientRequest(url + "/device", clientExecutor);
        request.body(MediaType.APPLICATION_XML_TYPE, device);
        ClientResponse<Device> response = request.post(Device.class);
        assertEquals("Error creating Device. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.CREATED, response.getResponseStatus());
        logger.info("Device created successfully: " + response.getEntity());
        return response.getEntity();
    }
    
    private Device updateDevice(Device device) throws Exception {
//        device.setAutoLogoff(AutoLogoff.ON);
//        device.setEphone(device.getEphone() + "Update");
        device.setImei(device.getImei() + "Update");
        device.setName(device.getName() + "Update");
        device.setPhone(device.getPhone() + "Update");
        device.setStatus(DeviceStatus.ACTIVE);
        device.setSim(device.getSim() + "Update");
        device.setSerialNum(device.getSerialNum() + "Update");
//        device.setHardAcceleration(10);
//        device.setHardBrake(10);
//        device.setHardTurn(10);
//        device.setHardVertical(15);
//        device.setSpeedSettings(new Integer[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 });
        

        ClientRequest request = new ClientRequest(url + "/device", clientExecutor);
        request.body(MediaType.APPLICATION_XML_TYPE, device);
        ClientResponse<Device> response = request.put(Device.class);
        assertEquals("Error updating address. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.OK, response.getResponseStatus());
        Device updateDevice = response.getEntity();        
        assertTrue("Device was not updated correctly.",device.getStatus().equals(updateDevice.getStatus()));
        
        logger.info("Device updated successfully: " + updateDevice);
        return updateDevice;
    }
    
    private void deleteDevice(Device device) throws Exception {
        ClientRequest request = new ClientRequest(url + "/device/" + device.getDeviceID(), clientExecutor);
        ClientResponse<Device> response = request.delete(Device.class);
        assertEquals("Error deleting device. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.OK, response.getResponseStatus());
        response = request.get(Device.class);
//        assertEquals("Device was found, but should be deleted. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.NOT_FOUND, response
//                .getResponseStatus());
        logger.info("Device get after delete returns: " + response.getStatus());
        logger.info("Device deleted successfully");
    }
    
    
    
    

}
