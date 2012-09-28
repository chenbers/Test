package com.inthinc.pro.dao.mock.service.impl;


import static org.junit.Assert.*;

import java.util.Date;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.data.SearchCriteria;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.ScoreableEntity;

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
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("entityID", new Integer(101));
        Date endDate = new Date();
        Date startDate = DateUtil.getDaysBackDate(endDate, 30);
        searchCriteria.addKeyValueRange("date", startDate, endDate);
        
        Map<String, Object> returnMap =  MockData.getInstance().lookup(ScoreableEntity.class, searchCriteria);
        
        assertNotNull(returnMap);
        
        Integer score = (Integer)returnMap.get("score");
        
        
        assertTrue(score >= 0 && score <= 50);

    }

}
