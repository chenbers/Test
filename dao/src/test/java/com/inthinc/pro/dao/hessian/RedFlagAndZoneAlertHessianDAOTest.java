package com.inthinc.pro.dao.hessian;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.model.RedFlagOrZoneAlert;


public class RedFlagAndZoneAlertHessianDAOTest {

    RedFlagAndZoneAlertHessianDAO redFlagAndZoneAlertHessianDAO;

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
        redFlagAndZoneAlertHessianDAO = new RedFlagAndZoneAlertHessianDAO();
        RedFlagAlertHessianDAO redFlagAlertHessianDAO = new RedFlagAlertHessianDAO();
        ZoneAlertHessianDAO zoneAlertHessianDAO = new ZoneAlertHessianDAO();
        SiloService siloService =  new SiloServiceCreator().getService();
        
        redFlagAlertHessianDAO.setSiloService(siloService);
        zoneAlertHessianDAO.setSiloService(siloService);
        
        redFlagAndZoneAlertHessianDAO.setRedFlagAlertDAO(redFlagAlertHessianDAO);
        redFlagAndZoneAlertHessianDAO.setZoneAlertDAO(zoneAlertHessianDAO);
    }

    @Test
    public void findInGroupHierarchy()
    {
        try
        {
            List<RedFlagOrZoneAlert> alerts = redFlagAndZoneAlertHessianDAO.getRedFlagAndZoneAlerts(-1);

            assertTrue("expected no red flag alerts to be returned", alerts.size() == 0);

            alerts = redFlagAndZoneAlertHessianDAO.getRedFlagAndZoneAlerts(1);

            assertTrue("expected to retrieve red flag alert records", alerts.size() > 0);
        }
        catch (RuntimeException t)
        {
            t.printStackTrace();
            throw t;
        }
    }

}
