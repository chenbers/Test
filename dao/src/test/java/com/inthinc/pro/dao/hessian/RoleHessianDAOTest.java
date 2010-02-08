package com.inthinc.pro.dao.hessian;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.model.security.Role;
import com.inthinc.pro.model.security.SiteAccessPoint;

public class RoleHessianDAOTest {
    RoleHessianDAO roleHessianDAO;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Before
    public void setUp() throws Exception
    {
        roleHessianDAO = new RoleHessianDAO();
        roleHessianDAO.setSiloService(new SiloServiceCreator().getService());
    }

    @Test
    public void roles() throws Exception
    {
        List<Role> roleList = roleHessianDAO.getRoles(MockData.NUM_ACCOUNTS);
        assertTrue("No roles were found", roleList.size() > 0);
    }
    
    @Test
    public void siteAccessPoints() throws Exception{
    	
    	List<SiteAccessPoint> siteAccessPoints = roleHessianDAO.getSiteAccessPts();
    	assertTrue("No site access points were found",siteAccessPoints.size() > 0);
    }
    
}
