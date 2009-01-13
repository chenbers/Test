package com.inthinc.pro.backing;


import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.richfaces.component.html.HtmlDatascroller;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.RedFlagReportItem;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.data.UnitTestStats;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.TableType;

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
        
        // test the spring creation/injection
        assertNotNull(redFlagsBean);
        assertNotNull(redFlagsBean.getRedFlagDAO());
        assertNotNull(redFlagsBean.getTablePreferenceDAO());
        redFlagsBean.setCategoryFilter(null);
        
        Map<String, TableColumn> columnMap = redFlagsBean.getTableColumns();
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
        assertEquals(Integer.valueOf(25), redFlagsBean.getNumRowsPerPg());
        
        // clear
        int size = tableData.size();
        redFlagsBean.setClearItem(redFlagsBean.getTableData().get(0));
        redFlagsBean.clearItemAction();
        tableData = redFlagsBean.getTableData();
        assertEquals(size-1, tableData.size());
        
        // table Preferences
        TablePreference tablePref = redFlagsBean.getTablePref().getTablePreference();
        assertEquals(TableType.RED_FLAG, tablePref.getTableType());
        int cnt = 0;
        for (Boolean visible : tablePref.getVisible())
        {
            assertTrue(redFlagsBean.getAvailableColumns().get(cnt++), visible);
        }
        
        
    }
    @Test
    public void filter()
    {
        // fleet manager login
        loginUser(UnitTestStats.UNIT_TEST_LOGIN);
        
        // get the bean from the applicationContext (initialized by Spring injection)
        RedFlagsBean redFlagsBean = (RedFlagsBean)applicationContext.getBean("redFlagsBean");
        
        // test the spring creation/injection
        assertNotNull(redFlagsBean);
        assertNotNull(redFlagsBean.getRedFlagDAO());
        assertNotNull(redFlagsBean.getTablePreferenceDAO());
        
        Map<String, TableColumn> columnMap = redFlagsBean.getTableColumns();
        assertEquals(RedFlagsBean.AVAILABLE_COLUMNS.size(), columnMap.size());
        
        List<RedFlagReportItem> tableData = redFlagsBean.getTableData();
        assertNotNull(tableData);
        
        redFlagsBean.setCategoryFilter(EventCategory.WARNING);
        
        List<RedFlagReportItem> redFlagItems = redFlagsBean.getTableData();
        assertNotNull(redFlagItems);
        assertEquals(MockData.unitTestStats.totalWarningRedFlags, redFlagItems.size());
        
        assertEquals(1, redFlagsBean.getStart().intValue());
        int pageSize = (MockData.unitTestStats.totalWarningRedFlags > redFlagsBean.getNumRowsPerPg()) ? redFlagsBean.getNumRowsPerPg() : MockData.unitTestStats.totalWarningRedFlags; 
        assertEquals(new Integer(pageSize), redFlagsBean.getEnd());
        assertEquals(MockData.unitTestStats.totalWarningRedFlags, redFlagsBean.getMaxCount().intValue());
        
        Event eventFilter = redFlagItems.get(0).getEvent();
        redFlagsBean.setEventFilter(eventFilter);
        
        redFlagItems = redFlagsBean.getTableData();
        assertNotNull(redFlagItems);
        assertEquals(1, redFlagItems.size());
        
        
    }
    
    @Test
    public void search()
    {
        loginUser(UnitTestStats.UNIT_TEST_LOGIN);
        
        // get the bean from the applicationContext (initialized by Spring injection)
        RedFlagsBean redFlagsBean = (RedFlagsBean)applicationContext.getBean("redFlagsBean");
        
        // test the spring creation/injection
        assertNotNull(redFlagsBean);
        assertNotNull(redFlagsBean.getRedFlagDAO());
        assertNotNull(redFlagsBean.getTablePreferenceDAO());
        
        List<RedFlagReportItem> tableData = redFlagsBean.getTableData();
        assertNotNull(tableData);
        int totalRows = tableData.size();
        
        
        redFlagsBean.setSearchText("info");
        redFlagsBean.searchAction();
        tableData = redFlagsBean.getTableData();
        int infoRows = tableData.size();
        
        redFlagsBean.setSearchText("warning");
        redFlagsBean.searchAction();
        tableData = redFlagsBean.getTableData();
        int warningRows = tableData.size();
        
        redFlagsBean.setSearchText("critical");
        redFlagsBean.searchAction();
        tableData = redFlagsBean.getTableData();
        int criticalRows = tableData.size();
        
        assertEquals(new Integer(totalRows), new Integer(infoRows+warningRows+criticalRows));
        
        redFlagsBean.setSearchText("");
        redFlagsBean.searchAction();
        tableData = redFlagsBean.getTableData();
        int noSearchRows = tableData.size();
        
        assertEquals(new Integer(totalRows), new Integer(noSearchRows));
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
