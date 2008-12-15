package com.inthinc.pro.dao.hessian;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.model.ZoneAlert;

public class ZoneAlertHessianDAOTest
{
    ZoneAlertHessianDAO zoneAlertHessianDAO;

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
        zoneAlertHessianDAO = new ZoneAlertHessianDAO();
        zoneAlertHessianDAO.setSiloService(new SiloServiceCreator().getService());
    }

    @Test
    public void findInGroupHierarchy()
    {
        try
        {
            List<ZoneAlert> alerts = zoneAlertHessianDAO.getZoneAlerts(-1);

            assertTrue("expected no zone alerts to be returned", alerts.size() == 0);

            alerts = zoneAlertHessianDAO.getZoneAlerts(1);

            assertTrue("expected to retrieve zone alert records", alerts.size() > 0);
        }
        catch (RuntimeException t)
        {
            t.printStackTrace();
            throw t;
        }
    }
}
