package com.inthinc.pro.service.it;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.plugins.spring.SpringResourceFactory;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.service.AccountService;
import com.inthinc.pro.service.DeviceService;
import com.inthinc.pro.service.UserService;



public class WebServiceITCase extends BaseEmbeddedServerITCase {
    private static Logger logger = Logger.getLogger(WebServiceITCase.class);
    private static final String PASSWORD = "nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password
    private static final String XML_CONTENT_TYPE = "application/xml";
    private static int randomInt = RandomUtils.nextInt(99999);
    private static int GROUP_ID = 1504;
    private static int PERSON_ID = 9999;
    private static String USERNAME = "MY_USER_";


    @Before 
    public void setUp() throws Exception { 

        server.getDeployment().getRegistry().addResourceFactory(new SpringResourceFactory("accountService", applicationContext, AccountService.class));
        server.getDeployment().getRegistry().addResourceFactory(new SpringResourceFactory("userService", applicationContext, UserService.class));
        server.getDeployment().getRegistry().addResourceFactory(new SpringResourceFactory("deviceService", applicationContext, DeviceService.class));
    }

    //Account service suite
  
    @Test
    public void accountCRUDTest() throws Exception {
        RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
        
        // Posting accounts
        Account account1 = new Account();
        account1.setAcctName("IT" + randomInt);
        account1.setStatus(Status.ACTIVE);
        
        ServiceClient client = ProxyFactory.create(ServiceClient.class, "http://localhost:" + this.getPort() );
        ClientResponse<Account> response = client.create(account1);
        
        assertEquals("Error creating account. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.CREATED, response.getResponseStatus());
        assertEquals(response.getEntity().getAcctName(),account1.getAcctName() );
        account1.setAcctID(response.getEntity().getAcctID() ) ;
        logger.info("Account 1  created successfully");
        
        Account account2 = new Account();
        account2.setAcctName("IT" + (randomInt + 1) );
        account2.setStatus(Status.ACTIVE);

        response = client.create(account2);
        
        assertEquals("Error creating account. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.CREATED, response.getResponseStatus());
        assertEquals(response.getEntity().getAcctName(),account2.getAcctName() );
        account2.setAcctID(response.getEntity().getAcctID() ) ;
        logger.info("Account 2 created successfully");
        
        // Getting accounts
        client = ProxyFactory.create(ServiceClient.class, "http://localhost:" + this.getPort() );
        ClientResponse<List<Account>> accounts = client.getAllAccounts();
        
        assertEquals(accounts.getResponseStatus(), Response.Status.OK );
        assertTrue(this.checkListcontainsElement(accounts.getEntity(), account1));
        logger.info("Account 1 found successfully");
        
        assertTrue(this.checkListcontainsElement(accounts.getEntity(), account2));
        logger.info("Account 2 found successfully");
        
        // Deleting accounts
        client = ProxyFactory.create(ServiceClient.class, "http://localhost:" + this.getPort() );
        response = client.delete(account1.getAcctID());
        
        assertEquals("Error deleting account. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.OK, response.getResponseStatus());
        assertTrue(this.checkListcontainsElement(accounts.getEntity(), account1));
        logger.info("Account 1 deleted successfully");
        
        response = client.delete(account2.getAcctID());
        
        assertEquals("Error deleting account. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.OK, response.getResponseStatus());
        assertTrue(this.checkListcontainsElement(accounts.getEntity(), account2));
        logger.info("Account 2 deleted successfully");
        
        // Getting accounts again to make sure they disappeared
        client = ProxyFactory.create(ServiceClient.class, "http://localhost:" + this.getPort() );
        accounts = client.getAllAccounts();
        
        assertEquals(accounts.getResponseStatus(), Response.Status.OK );
        assertFalse(this.checkListcontainsElement(accounts.getEntity(), account1));
        logger.info("Account 1 disappeared successfully");
        
        assertFalse(this.checkListcontainsElement(accounts.getEntity(), account2));
        logger.info("Account 2 disappeared successfully");
    }

    private boolean checkListcontainsElement(List<Account> accounts, Account account) {
        boolean res = false;
        for (Account acct: accounts){
            if(acct.getAcctName().equals(account.getAcctName())
                       && acct.getAcctID().equals(account.getAcctID())) {
               res = true;
            }
        }
        return res;
    }

   //User service suite
   // FIXME Needs to setup Spring config into TJWS server
   // @Test
    public void getUsersTest() throws Exception {
        ClientRequest request = new ClientRequest(url + "/users", clientExecutor);
        ClientResponse<User> response = request.get();

        assertEquals("Error retrieving all users. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.OK, response.getResponseStatus());
        logger.info("Users retrieved successfully");
    }

    // FIXME Needs to setup Spring config into TJWS server
    //@Test
    public void addUserTest() throws Exception {

        User user = new User();
        user.setUserID(randomInt);
        user.setGroupID(GROUP_ID);
        user.setPersonID(PERSON_ID);
        user.setStatus(Status.ACTIVE);
        user.setUsername(USERNAME + randomInt);
        user.setPassword(PASSWORD);

        List<Integer> roles = new ArrayList<Integer>();
        roles.add(2);
        user.setRoles(roles);
        // TODO: Make sure getting the fleet group
        user.setGroupID(GROUP_ID);

        ClientRequest request = new ClientRequest(url + "/user", clientExecutor);
        request.body(MediaType.APPLICATION_XML_TYPE, user);
        ClientResponse<User> response = request.post(User.class);
        assertEquals("Error creating User. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.CREATED, response.getResponseStatus());
        logger.info("User created successfully: " + response.getEntity());

    }

   //Device service suite
   // FIXME Needs to setup Spring config into TJWS server
   // @Test
    public void getDevicesTest() throws Exception {
        ClientRequest request = new ClientRequest(url + "/devices", clientExecutor);
        ClientResponse<Device> response = request.get();

        assertEquals("Error retrieving all devices. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.OK, response.getResponseStatus());
        logger.info("Devices retrieved successfully");
    }
    
}
