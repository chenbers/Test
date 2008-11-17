package com.inthinc.pro.dao.hessian;


import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.proserver.CentralServiceCreator;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Role;
import com.inthinc.pro.model.User;

public class UserHessianDAOTest
{
    UserHessianDAO userHessianDAO;

    @BeforeClass
    public static void runOnceBeforeAllTests() throws Exception
    {

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @Before
    public void setUp() throws Exception
    {
        userHessianDAO = new UserHessianDAO();
        userHessianDAO.setSiloServiceCreator(new SiloServiceCreator());
        
    }
    
    @Test
    public void findByEmail()
    {
        
        Exception ex = null;
        User user = null;
        try
        {
            user = userHessianDAO.findByEmail("hello@yahoo.com");
        }
        catch (EmptyResultSetException e)
        {
            ex = e;
        }

        assertNull("expected no user to be returned" , user);
        assertNotNull("expected an empty result set" , ex);
        
        user = userHessianDAO.findByEmail("custom101@email.com");
        
        assertNotNull("expected to retrieve a user record", user);
        assertEquals("custom101", user.getUsername());
    }

    @Test
    public void findByUsername()
    {
        
        Exception ex = null;
        User user = null;
        try
        {
            user = userHessianDAO.findByUserName("bogus");
        }
        catch (EmptyResultSetException e)
        {
            ex = e;
        }

        assertNull("expected no user to be returned" , user);
        assertNotNull("expected an empty result set" , ex);
        
        user = userHessianDAO.findByUserName("custom101");
        
        assertNotNull("expected to retrieve a user record", user);
        assertEquals("custom101", user.getUsername());
        assertEquals("custom101@email.com", user.getPerson().getEmail());
        assertEquals(new Integer(101), user.getPerson().getGroupID());
        assertEquals(Role.ROLE_CUSTOM_USER, user.getRole());
    }
    
    @Test
    public void phoneNumbersToModel()
    {
        User user = null;
        user = userHessianDAO.findByUserName("custom101");
        
        String home = "4441112222";
        String work = "8889997654";
            
//        userHessianDAO.phoneNumbersToModel(user, work+";"+home);
//        assertEquals(home, user.getPerson().getHomePhone());
//        assertEquals(work, user.getPerson().getWorkPhone());
//        
//        userHessianDAO.phoneNumbersToModel(user, ";");
//        assertEquals("", user.getPerson().getHomePhone());
//        assertEquals("", user.getPerson().getWorkPhone());
    }

}
