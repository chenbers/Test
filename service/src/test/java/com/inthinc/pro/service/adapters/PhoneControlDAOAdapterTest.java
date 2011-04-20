package com.inthinc.pro.service.adapters;

import static mockit.Mockit.tearDownMocks;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import mockit.Expectations;
import mockit.Mocked;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.PhoneControlDAO;
import com.inthinc.pro.model.Cellblock;

public class PhoneControlDAOAdapterTest {

    private final Integer ACCOUNT_ID = new Integer(1);
    private final Cellblock cellblock = new Cellblock();
    
   
    @Mocked (methods = {"getGroupID"})
    private PhoneControlDAOAdapter adapterSUT;
    
    @Mocked private PhoneControlDAO phoneControlDAOMock;
    
    @Before
    public void beforeMethod() {
        adapterSUT.setPhoneControlDAO(phoneControlDAOMock);
    }
    
    @After
    public void afterMethod(){
        tearDownMocks();
    }
    @Test
    public void testGetDAO(){
        assertEquals(adapterSUT.getDAO(), phoneControlDAOMock);
    }       

    @Test
    public void testGetCellblocksByAcctID(){
        final List<Cellblock> cellblockList = new ArrayList<Cellblock>();
        cellblockList.add(cellblock);
        
        new Expectations(){{
            phoneControlDAOMock.getCellblocksByAcctID(ACCOUNT_ID); returns(cellblockList);
        }};
        assertEquals(adapterSUT.getCellblocksByAcctID(ACCOUNT_ID), cellblockList);
    }   
    @Test
    public void testGetDriversWithDisabledPhones(){
        final List<Cellblock> cellblockList = new ArrayList<Cellblock>();
        cellblockList.add(cellblock);
        
        new Expectations(){{
            phoneControlDAOMock.getDriversWithDisabledPhones(); returns(cellblockList);
        }};
        assertEquals(adapterSUT.getDriversWithDisabledPhones(), cellblockList);
    }   
}
