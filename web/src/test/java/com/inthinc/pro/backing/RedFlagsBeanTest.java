package com.inthinc.pro.backing;


import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.richfaces.component.UIDatascroller;
import org.richfaces.component.html.HtmlDatascroller;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.RedFlagReportItem;
import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.data.UnitTestStats;

public class RedFlagsBeanTest extends BaseBeanTest
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
    public void bean()
    {
        // fleet manager login
        loginUser(UnitTestStats.UNIT_TEST_LOGIN);
        
        // get the bean from the applicationContext (initialized by Spring injection)
        RedFlagsBean redFlagsBean = (RedFlagsBean)applicationContext.getBean("redFlagsBean");
        
        assertNotNull(redFlagsBean);
        assertNotNull(redFlagsBean.getRedFlagDAO());
        
        Map<String, Boolean> columnMap = redFlagsBean.getTableColumns();
        assertEquals(RedFlagsBean.AVAILABLE_COLUMNS.size(), columnMap.size());
        
        List<RedFlagReportItem> tableData = redFlagsBean.getTableData();
        assertNotNull(tableData);
        
        List<RedFlagReportItem> redFlagItems = redFlagsBean.getTableData();
        assertNotNull(redFlagItems);
        assertEquals(MockData.unitTestStats.totalRedFlags, redFlagItems.size());
        
        assertEquals(1, redFlagsBean.getStart().intValue());
        assertEquals(redFlagsBean.getNumRowsPerPg(), redFlagsBean.getEnd());
        assertEquals(MockData.unitTestStats.totalRedFlags, redFlagsBean.getMaxCount().intValue());
        
        // paging
        checkScrolling(2, redFlagsBean);
        int lastPage = redFlagItems.size()/redFlagsBean.getNumRowsPerPg();
        lastPage = (redFlagItems.size() % redFlagsBean.getNumRowsPerPg() != 0) ? 1 : 0;
        checkScrolling(lastPage, redFlagsBean);
        
    }

    private void checkScrolling(int page, RedFlagsBean redFlagsBean)
    {
        List<RedFlagReportItem> redFlagItems = redFlagsBean.getTableData();
        assertNotNull(redFlagItems);
        int min = redFlagItems.size() > 0 ? 1 : 0;
        int max = redFlagItems.size();
        
        DataScrollerEvent mockEvent = new DataScrollerEvent(new HtmlDatascroller(), "", "", page);
        redFlagsBean.scrollerListener(mockEvent);
        int expectedMin = redFlagsBean.getNumRowsPerPg()*(page-1) + 1;
        expectedMin = (expectedMin > max) ? max : expectedMin;
        int expectedMax = redFlagsBean.getNumRowsPerPg()*page;
        expectedMax = (expectedMax > max) ? max : expectedMax;
        assertEquals(expectedMin, redFlagsBean.getStart().intValue());
        assertEquals(expectedMax, redFlagsBean.getEnd().intValue());
        
    }
}
