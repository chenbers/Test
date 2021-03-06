package com.inthinc.pro.service.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClientExecutor;
import org.junit.Test;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Status;



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
    public void testDummy() {}
  
//    @Test
    public void accountCRUDTest() throws Exception {
        
        ServiceClient adminClient = this.getAdminClient();

        // Posting accounts
        Account account1 = new Account();  
        account1.setAcctName("IT" + randomInt);
        account1.setStatus(Status.ACTIVE);
        
        ClientResponse<Account> response = adminClient.create(account1);

        System.out.println(response.getResponseStatus() );
        
        assertEquals("Error creating account. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.CREATED, response.getResponseStatus());
        assertEquals(response.getEntity().getAcctName(),account1.getAcctName() );
        account1.setAccountID(response.getEntity().getAccountID() ) ;
        logger.info("Account 1  created successfully");
        
        Account account2 = new Account();
        account2.setAcctName("IT" + (randomInt + 1) );
        account2.setStatus(Status.ACTIVE);

        response = adminClient.create(account2);
        
        assertEquals("Error creating account. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.CREATED, response.getResponseStatus());
        assertEquals(response.getEntity().getAcctName(),account2.getAcctName() );
        account2.setAccountID(response.getEntity().getAccountID() ) ;
        logger.info("Account 2 created successfully");
        
        // Getting accounts
        ClientResponse<List<Account>> accounts = adminClient.getAllAccounts();
        
        assertEquals(accounts.getResponseStatus(), Response.Status.OK );
        assertTrue(this.checkListcontainsElement(accounts.getEntity(), account1));
        logger.info("Account 1 found successfully");
        
        assertTrue(this.checkListcontainsElement(accounts.getEntity(), account2));
        logger.info("Account 2 found successfully");
        
        // Deleting accounts
        response = adminClient.delete(account1.getAccountID());
        
        assertEquals("Error deleting account. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.OK, response.getResponseStatus());
        assertTrue(this.checkListcontainsElement(accounts.getEntity(), account1));
        logger.info("Account 1 deleted successfully");
        
        response = adminClient.delete(account2.getAccountID());
        
        assertEquals("Error deleting account. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.OK, response.getResponseStatus());
        assertTrue(this.checkListcontainsElement(accounts.getEntity(), account2));
        logger.info("Account 2 deleted successfully");
        
        // Getting accounts again to make sure they disappeared
        accounts = adminClient.getAllAccounts();
        
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
                       && acct.getAccountID().equals(account.getAccountID())) {
               res = true;
            }
        }
        return res;
    }

   //Device service suite

//    @Test
    public void getDevicesTest() throws Exception {
        
        // Getting devices
        ClientResponse<List<Device>> devices = client.getAllDevices();

        assertEquals(devices.getResponseStatus(), Response.Status.OK );
        assertNotNull(devices.getEntity());
        logger.info("Devices retrieved successfully");
    }
    
    private ServiceClient getAdminClient() {
        
        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        httpClient = new HttpClient(params);
        Credentials defaultcreds = new UsernamePasswordCredentials("TEST_622", "password");
        httpClient.getState().setCredentials(new AuthScope(this.getDomain(),  this.getPort(), AuthScope.ANY_REALM), defaultcreds);
        clientExecutor = new ApacheHttpClientExecutor(httpClient);

        client = ProxyFactory.create(ServiceClient.class, "http://localhost:" + this.getPort(), clientExecutor);
        
        return client;
    }
}
