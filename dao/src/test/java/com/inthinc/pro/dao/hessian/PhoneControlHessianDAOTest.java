package com.inthinc.pro.dao.hessian;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Cellblock;
import com.inthinc.pro.model.Silo;

public class PhoneControlHessianDAOTest {
    PhoneControlHessianDAO phoneControlHessianDAO;

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
        phoneControlHessianDAO = new PhoneControlHessianDAO();
        phoneControlHessianDAO.setSiloService(new SiloServiceCreator().getService());
    }

    @Test
    public void cellblocks() throws Exception
    {
        List<Cellblock> cellblockList = phoneControlHessianDAO.getCellblocksForAcctID(MockData.NUM_ACCOUNTS);
        assertTrue("No cellblock records were found", cellblockList.size() > 0);
        
//        Cellblock phoneNumberCellblock = phoneControlHessianDAO.findByPhoneNumber("8017127234");
//        assertTrue("No cellblock record  was found", phoneNumberCellblock != null);
//        

//        List<Cellblock> disabledCellblockList = phoneControlHessianDAO.getDriversWithDisabledPhones();
//        assertTrue("No cellblock records were found", disabledCellblockList.size() > 0);
    }

}
