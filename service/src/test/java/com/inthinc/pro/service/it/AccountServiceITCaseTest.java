package com.inthinc.pro.service.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.model.Account;
@Ignore
public class AccountServiceITCaseTest extends BaseITCase {
    private static Logger logger = Logger.getLogger(AccountServiceITCaseTest.class);

    @SuppressWarnings("unchecked")
    @Test
    public void getAccountDefaultXmlTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/account");
       ClientResponse<Account> response = request.get();
       Account account = response.getEntity(Account.class);

        assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), MediaType.APPLICATION_XML);
        assertNotNull(account);
//        assertEquals(account.getAccountID(),new Integer(DRIVER_ID));
        logger.info("Account retrieved successfully in the default XML");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getAccountXmlTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/account.xml");
       ClientResponse<Account> response = request.get();
       Account account = response.getEntity(Account.class);

        assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), MediaType.APPLICATION_XML);
        assertNotNull(account);
//        assertEquals(account.getAccountID(),new Integer(DRIVER_ID));
        logger.info("Account retrieved successfully in XML");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getAccountJSONTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/account.json");
       ClientResponse<Account> response = request.get();
       Account account = response.getEntity(Account.class);

       assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), MediaType.APPLICATION_JSON);
        assertNotNull(account);
//        assertEquals(account.getAccountID(),new Integer(DRIVER_ID));
        logger.info("Account retrieved successfully in JSON");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getAccountFastInfoSetTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/account.fastinfoset");
       ClientResponse<Account> response = request.get();

       assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), "application/fastinfoset");
       Account account = response.getEntity(Account.class);
        assertNotNull(account);
//        assertEquals(account.getAccountID(),new Integer(DRIVER_ID));
        logger.info("Account retrieved successfully in fastInfoset");
    }

}
