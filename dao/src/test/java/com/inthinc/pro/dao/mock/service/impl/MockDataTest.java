package com.inthinc.pro.dao.mock.service.impl;


import static org.junit.Assert.*;

import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.mock.data.MockDataContainer;
import com.inthinc.pro.dao.mock.data.SearchCriteria;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.OverallScore;

public class MockDataTest
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
    public void mockDataLookup() throws Exception
    {
        MockDataContainer mockDataContainer = new MockDataContainer();
        
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("userID", new Integer(1));
//        searchCriteria.addKeyValue("levelID", levelID);
        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, 30);
        searchCriteria.addKeyValueRange("date", startDate, endDate);
        
        Map<String, Object> returnMap =  mockDataContainer.lookup(OverallScore.class, searchCriteria);
        
        assertNotNull(returnMap);
        
        Integer score = (Integer)returnMap.get("score");
        
        
        assertTrue(score >= 0 && score <= 50);

    }

}
