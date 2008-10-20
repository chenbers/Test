package com.inthinc.pro.security.userdetails;


import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.AccountExpiredException;
import org.springframework.security.userdetails.UserDetails;

public class ProUserServiceImplTest
{

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @Test
    public void loadUserByUsername()
    {
        // TODO: This test is totally bogus -- but just trying to get the unit testing ball rolling.
        
        ProUserServiceImpl impl = new ProUserServiceImpl();
        
        UserDetails proUserDetails = null;
        Exception exception = null;
        
        try
        {
            proUserDetails = impl.loadUserByUsername("expired");
        }
        catch (Exception e)
        {
            exception = e;
        }
        
        assertEquals("Expected no User Details.", "expired", proUserDetails.getUsername() );
//        assertTrue("Expected an expired account.", (exception instanceof AccountExpiredException) );
        
        
    }

}
