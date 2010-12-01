package com.inthinc.pro.service.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.service.security.TiwiproPrincipal;



public class WebServiceITCaseTest extends BaseEmbeddedServerITCase {
    private static Logger logger = Logger.getLogger(WebServiceITCaseTest.class);
    private static final String PASSWORD = "nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password
    private static final String XML_CONTENT_TYPE = "application/xml";
    private static int randomInt = RandomUtils.nextInt(99999);
    private static int GROUP_ID = 1;
    private static int PERSON_ID = 9999;
    private static String USERNAME = "MY_USER_";

    //Account service suite
  
    @Test
    public void accountCRUDTest() throws Exception {

        // Posting accounts
        Account account1 = new Account();  
        account1.setAcctName("IT" + randomInt);
        account1.setStatus(Status.ACTIVE);
        
        ClientResponse<Account> response = client.create(account1);

        System.out.println(response.getResponseStatus() );
        
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
        ClientResponse<List<Account>> accounts = client.getAllAccounts();
        
        assertEquals(accounts.getResponseStatus(), Response.Status.OK );
        assertTrue(this.checkListcontainsElement(accounts.getEntity(), account1));
        logger.info("Account 1 found successfully");
        
        assertTrue(this.checkListcontainsElement(accounts.getEntity(), account2));
        logger.info("Account 2 found successfully");
        
        // Deleting accounts
        response = client.delete(account1.getAcctID());
        
        assertEquals("Error deleting account. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.OK, response.getResponseStatus());
        assertTrue(this.checkListcontainsElement(accounts.getEntity(), account1));
        logger.info("Account 1 deleted successfully");
        
        response = client.delete(account2.getAcctID());
        
        assertEquals("Error deleting account. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.OK, response.getResponseStatus());
        assertTrue(this.checkListcontainsElement(accounts.getEntity(), account2));
        logger.info("Account 2 deleted successfully");
        
        // Getting accounts again to make sure they disappeared
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

   //Device service suite

    @Test
    public void getDevicesTest() throws Exception {
        
        // Getting devices
        ClientResponse<List<Device>> devices = client.getAllDevices();

        assertEquals(devices.getResponseStatus(), Response.Status.OK );
        assertNotNull(devices.getEntity());
        logger.info("Devices retrieved successfully");
    }
    
    @BeforeClass
    public static void setup() {
        TiwiproPrincipal.adminUserBackDoor = true ;
    }
    
    @AfterClass
    public static void tearDown(){
        TiwiproPrincipal.adminUserBackDoor = false ;
    }
    
}
