package com.inthinc.pro.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import mockit.Cascading;
import mockit.Expectations;
import mockit.Mocked;

import org.junit.Assert;
import org.junit.Test;

import com.inthinc.pro.model.User;
import com.inthinc.pro.service.adapters.UserDAOAdapter;
import com.inthinc.pro.service.model.BatchResponse;

/**
 * Unit tests for UserServiceImpl.
 */
public class UserServiceImplTest extends BaseUnitTest {
    private static final String OK_RESPONSE = "Response Status: ";
    private static final String USERNAME_MOCK = "user";
    
    // JMockit mocks
    @Mocked private User userMock;
    @Mocked private UserDAOAdapter secureDaoMock;
    
    // System Under Test
    private UserServiceImpl serviceSUT = new UserServiceImpl();
    
    /**
     * Test for getAll() method of the Service class.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetAllUsers() {
        
        serviceSUT.setDao(secureDaoMock);
        
        new Expectations(){            
           // @Mocked("ok()") Response responseMock;
           // @Mocked ResponseBuilder responseBuilderMock;                
            {
                List<User> list = new ArrayList<User>();
                list.add(userMock);
                secureDaoMock.getAll(); returns(list);
                //responseMock.ok((GenericEntity<List<User>>) any); returns(responseBuilderMock);
                //responseBuilderMock.build(); returns(responseMock);
                //responseMock.getStatus(); returns(Status.OK.getStatusCode());
            }
        };
        
        Response response = serviceSUT.getAll();
        
        Assert.assertNotNull(response); 
        Assert.assertEquals(OK_RESPONSE, Status.OK.getStatusCode(), response.getStatus());

        // check the content
        GenericEntity<List<User>> entity = (GenericEntity<List<User>>)response.getEntity();
        
        Assert.assertNotNull(entity);
        Assert.assertEquals(1, entity.getEntity().size());
        Assert.assertEquals(userMock, entity.getEntity().get(0));
    }
    
    /**
     * Test for get() method of the Service class.
     */
    @Test
    public void testGetUser() {
        serviceSUT.setDao(secureDaoMock);
        
        new Expectations(){
            {
                secureDaoMock.findByUserName(USERNAME_MOCK); returns(userMock);
                secureDaoMock.findByUserName(""); returns(null);
            }
        };
        
        Response response = serviceSUT.get(USERNAME_MOCK);        
        Assert.assertNotNull(response); 
        Assert.assertEquals(OK_RESPONSE, Status.OK.getStatusCode(), response.getStatus());
        
        // check the content
        User user = (User)response.getEntity();
        Assert.assertEquals(userMock, user);
        
        response = serviceSUT.get("");        
        Assert.assertNotNull(response); 
        Assert.assertEquals(response.getStatus(), Status.NOT_FOUND.getStatusCode());
        
    }

    /**
     * Test for login() method of the Service class.
     */
    @Test
    public void testLogin() {
        serviceSUT.setDao(secureDaoMock);
        final String PASSWORD_MOCK = "abc";
        
        new Expectations(){
            {
                secureDaoMock.login(USERNAME_MOCK, PASSWORD_MOCK); returns(userMock);
                secureDaoMock.login(USERNAME_MOCK, ""); returns(null);
            }
        };
        
        Response response = serviceSUT.login(USERNAME_MOCK, PASSWORD_MOCK);        
        Assert.assertNotNull(response);
        Assert.assertEquals(OK_RESPONSE, Status.OK.getStatusCode(), response.getStatus());
        // check the content
        User user = (User)response.getEntity();
        Assert.assertEquals(userMock, user);
        
        response = serviceSUT.login(USERNAME_MOCK, "");        
        Assert.assertNotNull(response); 
        Assert.assertEquals(Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }
    
    /**
     * Test for create() method of the Service class.
     * @param uriInfoMock mock for UriInfo
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testCreateUsers(@Mocked final UriInfo uriInfoMock) {
        
        final Integer userId = new Integer(1);
        
        serviceSUT.setDao(secureDaoMock);
        
        new Expectations(){            
            @Cascading private UriBuilder uriBuilderMock;                
            {
                uriInfoMock.getBaseUriBuilder(); returns(uriBuilderMock);
                secureDaoMock.create(userMock); returns(userId);
                uriInfoMock.getBaseUriBuilder(); returns(uriBuilderMock);
                secureDaoMock.create((User)any); returns(null);
            }
        };
        
        List<User> users = new ArrayList<User>();
        users.add(userMock);
        users.add(new User());
        
        Response response = serviceSUT.create(users, uriInfoMock);
        
        assertNotNull(response); 
        assertEquals(response.getStatus(), Status.OK.getStatusCode());
        
        GenericEntity<List<BatchResponse>> entity = (GenericEntity<List<BatchResponse>>)response.getEntity();
        assertNotNull(entity);
        
        BatchResponse resp = entity.getEntity().get(0);
        assertEquals(Status.CREATED.getStatusCode(), resp.getStatus().intValue());
        
        resp = entity.getEntity().get(1);
        assertNotNull(resp);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), resp.getStatus().intValue());
        
    }
}
