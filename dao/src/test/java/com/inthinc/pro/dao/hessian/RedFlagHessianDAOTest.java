package com.inthinc.pro.dao.hessian;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.data.UnitTestStats;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.RedFlagLevel;

public class RedFlagHessianDAOTest
{

    RedFlagHessianDAO      redFlagHessianDAO;


    @Before
    public void setUp() throws Exception
    {
        redFlagHessianDAO = new RedFlagHessianDAO();
        redFlagHessianDAO.setSiloServiceCreator(new SiloServiceCreator());
    }

    
    @Test
    public void getRedFlags()
    {
        List<RedFlag>  redFlagList = redFlagHessianDAO.getRedFlags(UnitTestStats.UNIT_TEST_GROUP_ID);
        
        assertNotNull(redFlagList);
        assertEquals(MockData.unitTestStats.totalRedFlags, redFlagList.size());
        
        RedFlagLevel level = redFlagList.get(0).getLevel();
        assertEquals(level.toString(), level.getDescription());
        
    }

}
