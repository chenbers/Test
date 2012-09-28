package com.inthinc.pro.dao.hessian;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Device;

public class DeviceHessianDAOTest
{
    DeviceHessianDAO deviceHessianDAO;

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
        deviceHessianDAO = new DeviceHessianDAO();
        deviceHessianDAO.setSiloService(new SiloServiceCreator().getService());
    }

    @Test
    public void devices() throws Exception
    {
        List<Device> deviceList = deviceHessianDAO.getDevicesByAcctID(MockData.NUM_ACCOUNTS);
        assertTrue("No devices were found", deviceList.size() > 0);
    }
}
