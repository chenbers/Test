package com.inthinc.pro.service.it;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.plugins.spring.SpringResourceFactory;
import org.junit.Before;
import org.junit.Test;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.service.AccountService;



public class WebServiceITCase extends BaseEmbeddedServerITCase {
    private static Logger logger = Logger.getLogger(WebServiceITCase.class);
    private static final String PASSWORD = "nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password
    private static int randomInt = RandomUtils.nextInt(99999);
    private static int GROUP_ID = 1504;
    private static int PERSON_ID = 9999;
    private static String USERNAME = "MY_USER_";
    
    
    @Before 
    public void setUp() throws Exception { 
        
        server.getDeployment().getRegistry().addResourceFactory(new SpringResourceFactory("accountService", applicationContext, AccountService.class));
     }

   //@Test
    public void userTest() throws Exception {

        User user = new User();
        user.setUserID(randomInt);
        user.setGroupID(GROUP_ID);
        user.setPersonID(PERSON_ID);
        user.setStatus(Status.ACTIVE);
        user.setUsername(USERNAME + randomInt);
        user.setPassword(PASSWORD);
        
        List<Integer> roles = new ArrayList<Integer>();
        //2 is the "normal" account
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
    
    @Test
    public void accountTest() throws Exception {
        Account account = new Account();
        account.setAcctName("IT" + randomInt);
        account.setStatus(Status.ACTIVE);

        ClientRequest request = new ClientRequest(url + "/accounts", clientExecutor);
        //request.body(MediaType.APPLICATION_XML_TYPE, account);
        //ClientResponse<Account> response = request.post(Account.class);
        ClientResponse<Account> response = request.get();
        

        assertEquals("Error retrieving all accounts. HTTP Status Code: " + response.getStatus() + " - " + response.getResponseStatus(), Response.Status.OK, response.getResponseStatus());
        logger.info("Accounts retrieved successfully");
    }
}
