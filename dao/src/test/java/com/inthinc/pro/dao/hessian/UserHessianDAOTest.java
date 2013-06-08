package com.inthinc.pro.dao.hessian;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.mock.data.MockRoles;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.app.States;

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
        userHessianDAO.setSiloService(new SiloServiceCreator().getService());

        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(new SiloServiceCreator().getService());
        
        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();

    }
    

    @Test
    public void findByUsername()
    {
        
        User user = userHessianDAO.findByUserName("bogus");
        assertNull("expected no user to be returned" , user);
        
        user = userHessianDAO.findByUserName("custom101");
        
        assertNotNull("expected to retrieve a user record", user);
        assertEquals("custom101", user.getUsername());
        assertEquals("custom101@example.com", user.getPerson().getPriEmail());
        assertEquals(new Integer(101), user.getGroupID());
        assertEquals(MockRoles.getAdminUser().getRoleID(), user.getRoles().get(0));
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
