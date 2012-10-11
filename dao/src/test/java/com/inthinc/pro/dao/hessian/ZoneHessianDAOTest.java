package com.inthinc.pro.dao.hessian;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Zone;

public class ZoneHessianDAOTest
{
    ZoneHessianDAO zoneHessianDAO;

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
        zoneHessianDAO = new ZoneHessianDAO();
        zoneHessianDAO.setSiloService(new SiloServiceCreator().getService());
    }

    @Test
    public void findInGroupHierarchy()
    {
        try
        {
            List<Zone> zones = zoneHessianDAO.getZones(-1);

            assertTrue("expected no zones to be returned", zones.size() == 0);

            zones = zoneHessianDAO.getZones(1);

            assertTrue("expected to retrieve zone records", zones.size() > 0);
        }
        catch (RuntimeException t)
        {
            t.printStackTrace();
            throw t;
        }
    }
}
