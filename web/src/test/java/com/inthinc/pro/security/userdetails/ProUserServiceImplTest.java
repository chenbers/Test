package com.inthinc.pro.security.userdetails;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
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
        
        ProUserServiceImpl pusi = new ProUserServiceImpl();
        
        UserDetails proUserDetails = null;
        Exception exception = null;
        
        try
        {
            proUserDetails = pusi.loadUserByUsername("expired");
        }
        catch (Exception e)
        {
            exception = e;
        }
        
        //assertEquals("Expected no User Details.", "expired", proUserDetails.getUsername() );
//        assertTrue("Expected an expired account.", (exception instanceof AccountExpiredException) );
        
//        //set up all the DAOs
//        IntegrationConfig config = new IntegrationConfig();
//        String host = config.get(IntegrationConfig.SILO_HOST).toString();
//        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
//        SiloService siloService = new SiloServiceCreator(host, port).getService();
//        
//        GroupHessianDAO groupDAO = new GroupHessianDAO();
//        groupDAO.setSiloService(siloService);
//        pusi.setGroupDAO(groupDAO);
//        
//        RoleHessianDAO roleDAO = new RoleHessianDAO();
//        roleDAO.setSiloService(siloService);
//        pusi.setRoleDAO(roleDAO);
//        
//        UserHessianDAO userDAO = new UserHessianDAO();
//        userDAO.setSiloService(siloService);
//        pusi.setUserDAO(userDAO);
//        
//        ZoneHessianDAO zoneDAO = new ZoneHessianDAO();
//        zoneDAO.setSiloService(siloService);
//        pusi.setZoneDAO(zoneDAO);
//        
//        UserDetails userDetails = null;
//        //Try loading jhoward
//        try{
//        	
//        	userDetails = pusi.loadUserByUsername("jhoward");
//        	assertTrue("No user was found", userDetails != null);
//            assertTrue("No user named jhoward was found", userDetails.getUsername().equals("jhoward"));
//            
//            GrantedAuthority[] gas = userDetails.getAuthorities();
//            assertTrue("Default role not Admin", gas[0].toString().equals("ROLE_ADMIN"));
//        }
//        catch (UsernameNotFoundException unfe){
//        	
//        	assertNull("No user found",userDetails);
//        }
//        catch (DataAccessException dae){
//        	
//        	assertNull("Data Access problem",userDetails);
//       	
//        }
//        
    }

}
