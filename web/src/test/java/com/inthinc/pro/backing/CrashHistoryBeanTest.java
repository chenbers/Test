package com.inthinc.pro.backing;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.richfaces.component.html.HtmlDatascroller;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.CrashHistoryReportItem;
import com.inthinc.pro.backing.ui.RedFlagReportItem;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.mock.data.UnitTestStats;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.TableType;

public class CrashHistoryBeanTest extends BaseBeanTest {
    
    // Patterned after redflagsbeantest.

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
        LocaleBean localeBean = new LocaleBean();
        localeBean.setLocale(Locale.US);
        CrashHistoryBean crashHistoryBean = (CrashHistoryBean)applicationContext.getBean("crashHistoryBean");
        
        // test the spring creation/injection
        assertNotNull(crashHistoryBean);
        assertNotNull(crashHistoryBean.getCrashReportDAO());
        assertNotNull(crashHistoryBean.getTablePreferenceDAO());
        assertNotNull(crashHistoryBean.getSearchCoordinationBean());
        
        Map<String, TableColumn> columnMap = crashHistoryBean.getTableColumns();
        assertEquals(CrashHistoryBean.AVAILABLE_COLUMNS.size(), columnMap.size());
        
//        List<CrashHistoryReportItem> tableData = crashHistoryBean.getTableData();
//        assertNotNull(tableData);
        
//        List<RedFlagReportItem> redFlagItems = redFlagsBean.getTableData();
//        assertNotNull(redFlagItems);
//        assertEquals(MockData.unitTestStats.totalRedFlags, redFlagItems.size());
        
//        assertEquals(1, redFlagsBean.getStart().intValue());
//        assertEquals(redFlagsBean.getNumRowsPerPg(), redFlagsBean.getEnd());
//        assertEquals(MockData.unitTestStats.totalRedFlags, redFlagsBean.getMaxCount().intValue());
        
        // paging
//        checkScrolling(2, redFlagsBean);
//        int lastPage = redFlagItems.size()/redFlagsBean.getNumRowsPerPg();
//        lastPage = (redFlagItems.size() % redFlagsBean.getNumRowsPerPg() != 0) ? 1 : 0;
//        checkScrolling(lastPage, redFlagsBean);
//        assertEquals(Integer.valueOf(25), redFlagsBean.getNumRowsPerPg());
        
        // forgive
//        int size = tableData.size();
//        redFlagsBean.setClearItem(redFlagsBean.getTableData().get(0));
//        redFlagsBean.excludeEventAction();
//        tableData = redFlagsBean.getTableData();
//        assertEquals(redFlagsBean.getTableData().get(0).getRedFlag().getEvent().getForgiven().intValue(),1);
        
        // unforgive
//        size = tableData.size();
//        redFlagsBean.setClearItem(redFlagsBean.getTableData().get(0));
//        redFlagsBean.includeEventAction();
//        tableData = redFlagsBean.getTableData();
//        assertEquals(redFlagsBean.getTableData().get(0).getRedFlag().getEvent().getForgiven().intValue(),0);
        
       // table Preferences
        TablePreference tablePref = crashHistoryBean.getTablePref().getTablePreference();
        assertEquals(TableType.CRASH_HISTORY, tablePref.getTableType());
        int cnt = 0;
        for (Boolean visible : tablePref.getVisible())
        {
            assertTrue(crashHistoryBean.getAvailableColumns().get(cnt++), visible);
        }
        
        
    }
    
    @Ignore
    @Test
    public void filter()
    {
        // fleet manager login
//        loginUser(UnitTestStats.UNIT_TEST_LOGIN);
        
        // get the bean from the applicationContext (initialized by Spring injection)
//        RedFlagsBean redFlagsBean = (RedFlagsBean)applicationContext.getBean("redFlagsBean");
        
        // test the spring creation/injection
//        assertNotNull(redFlagsBean);
//        assertNotNull(redFlagsBean.getRedFlagDAO());
//        assertNotNull(redFlagsBean.getTablePreferenceDAO());
//        
//        Map<String, TableColumn> columnMap = redFlagsBean.getTableColumns();
//        assertEquals(RedFlagsBean.AVAILABLE_COLUMNS.size(), columnMap.size());
//        
//        List<RedFlagReportItem> tableData = redFlagsBean.getTableData();
//        assertNotNull(tableData);
//        
//        redFlagsBean.setCategoryFilter(EventCategory.WARNING);
//        
//        List<RedFlagReportItem> redFlagItems = redFlagsBean.getTableData();
//        assertNotNull(redFlagItems);
//        assertEquals(MockData.unitTestStats.totalWarningRedFlags, redFlagItems.size());
//        
//        assertEquals(1, redFlagsBean.getStart().intValue());
//        int pageSize = (MockData.unitTestStats.totalWarningRedFlags > redFlagsBean.getNumRowsPerPg()) ? redFlagsBean.getNumRowsPerPg() : MockData.unitTestStats.totalWarningRedFlags; 
//        assertEquals(new Integer(pageSize), redFlagsBean.getEnd());
//        assertEquals(MockData.unitTestStats.totalWarningRedFlags, redFlagsBean.getMaxCount().intValue());
//        
//        Event eventFilter = redFlagItems.get(0).getRedFlag().getEvent();
//        redFlagsBean.setEventFilter(eventFilter);
//        
//        redFlagItems = redFlagsBean.getTableData();
//        assertNotNull(redFlagItems);
//        assertEquals(1, redFlagItems.size());
        
        
    }
    
    @Ignore
    @Test
    public void search()
    {
        loginUser(UnitTestStats.UNIT_TEST_LOGIN);
        
        // get the bean from the applicationContext (initialized by Spring injection)
        CrashHistoryBean crashHistoryBean = (CrashHistoryBean)applicationContext.getBean("crashHistoryBean");
        
        // test the spring creation/injection
        assertNotNull(crashHistoryBean);
        assertNotNull(crashHistoryBean.getCrashReportDAO());
        assertNotNull(crashHistoryBean.getTablePreferenceDAO());
        
        crashHistoryBean.initTableData();
        List<CrashHistoryReportItem> tableData = crashHistoryBean.getTableData();
        assertNotNull(tableData);
        int totalRows = tableData.size();
        
        crashHistoryBean.getSearchCoordinationBean().setSearchFor("info");
        crashHistoryBean.searchAction();
        tableData = crashHistoryBean.getTableData();
        int infoRows = tableData.size();
        assertTrue("found some info rows", infoRows > 0);
        
        crashHistoryBean.getSearchCoordinationBean().setSearchFor("");
        crashHistoryBean.searchAction();
        tableData = crashHistoryBean.getTableData();
        int noSearchRows = tableData.size();
        
        assertEquals(new Integer(totalRows), new Integer(noSearchRows));
    }
    
    private void checkScrolling(int page, BaseNotificationsBean<RedFlagReportItem> redFlagsBean)
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
