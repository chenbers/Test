package com.inthinc.pro.service.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Locale;
import java.util.TimeZone;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.joda.time.DateTime;
import org.junit.Test;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.app.Roles;

public class AccountCreationITCase extends BaseITCase {
    private static Logger logger = Logger.getLogger(AccountCreationITCase.class);
    private static final String PASSWORD = "nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password

    @Test
    public void createAccount() throws Exception {
        int randomInt = RandomUtils.nextInt(99999);
        Account account = new Account();
        account.setAcctName("IntegrationTest" + randomInt);
        account.setStatus(Status.ACTIVE);

        ClientRequest request = new ClientRequest(url + "/account", httpClient);
        request.body(MediaType.APPLICATION_XML_TYPE, account);
        ClientResponse<Account> accountResponse = request.post(Account.class);

        assertEquals("Creating Account returned " + accountResponse.getResponseStatus(), Response.Status.CREATED, accountResponse.getResponseStatus());
        account = accountResponse.getEntity();
        assertNotNull("New account does not have an Account ID", account.getAcctID());
        logger.info("Account created successfully: " + account);

        Group fleetGroup = new Group();
        fleetGroup.setAccountID(account.getAcctID());
        fleetGroup.setDescription("Top Group");
        fleetGroup.setType(GroupType.FLEET);
        fleetGroup.setName("Top");
        fleetGroup.setMapZoom(6);
        fleetGroup.setMapCenter(new LatLng(0.0, 0.0));

        request = new ClientRequest(url + "/group", httpClient);
        request.body(MediaType.APPLICATION_XML_TYPE, fleetGroup);
        ClientResponse<Group> groupResponse = request.post(Group.class);

        fleetGroup = groupResponse.getEntity();

        Address address = new Address();
        address.setAccountID(account.getAcctID());
        address.setAddr1("Address" + randomInt);
        address.setCity("West Valley City");
        address.setZip("84095");
        request = new ClientRequest(url + "/address", httpClient);
        request.body(MediaType.valueOf("application/xml"), address, Address.class);
        ClientResponse<Address> addressResponse = request.post(Address.class);
        address = addressResponse.getEntity();

        Person person = new Person();
        person.setAcctID(account.getAcctID());
        person.setFirst("FirstName" + randomInt);
        person.setLast("LastName" + randomInt);
        person.setLocale(Locale.US);
        person.setTimeZone(TimeZone.getDefault());
        person.setMeasurementType(MeasurementType.ENGLISH);
        person.setFuelEfficiencyType(FuelEfficiencyType.MPG_US);
        person.setDob(new DateTime(1969, 1, 1, 1, 1, 1, 1).toDate());
        person.setDept("Integration Test Department");
        person.setEmpid("66666666");
        person.setGender(Gender.MALE);
        person.setPriEmail("it" + randomInt + "@it.com");
        person.setAddressID(address.getAddrID());

        User user = new User();
        user.setUsername("username" + randomInt);
        user.setPassword(PASSWORD);
        user.setStatus(Status.ACTIVE);
        user.setRole(Roles.getRoleByName("normalUser"));
        user.setGroupID(fleetGroup.getGroupID());
        user.setPersonID(person.getPersonID());
        user.setUserID(0);

        // request = new ClientRequest(url + "/user", httpClient);
        // request.body(MediaType.APPLICATION_XML_TYPE, user);
        // ClientResponse<User> userResponse = request.post(User.class);
        // user = userResponse.getEntity();
        // logger.info(user);

        person.setUser(user);

        request = new ClientRequest(url + "/person", httpClient);
        request.body(MediaType.APPLICATION_XML_TYPE, person);
        ClientResponse<Person> personResponse = request.post(Person.class);

        assertEquals(Response.Status.CREATED, personResponse.getResponseStatus());
        person = personResponse.getEntity();
        assertNotNull("New Person does not have a Person ID", person.getPersonID());

        logger.info("Person created successfully: " + person);

    }
}
