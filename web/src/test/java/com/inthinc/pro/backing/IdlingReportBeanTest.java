package com.inthinc.pro.backing;

import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.backing.ui.TableColumn;

public class IdlingReportBeanTest extends BaseBeanTest
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
        // just test the bean successfully creates all of the required pies
        
        // team level login
        loginUser("custom101");
        
        // get the bean from the applicationContext (initialized by Spring injection)
        IdlingReportBean idlingReportBean = 
            (IdlingReportBean)applicationContext.getBean("idlingReportBean");
        
        // make sure the spring injection worked
        assertNotNull(idlingReportBean.getTablePreferenceDAO());
//        assertNotNull(idlingReportBean.getDriverDAO());
        
        // try grabbing some drivers based on above, should be 45 
        //  for normal101
//        assertEquals(45,
//                idlingReportBean.getIdlingData().size());        
//        assertEquals( 1,
//                (new Integer(idlingReportBean.getStart())).intValue());
//        assertEquals(25,
//                (new Integer(idlingReportBean.getEnd()).intValue()));
//        assertEquals(45,
//                (new Integer(idlingReportBean.getMaxCount()).intValue()));
        
        // make sure the seconds converter is working correctly
        assertEquals(9863,
                idlingReportBean.convertToSeconds("02:44:23"));
                
        // column map
        Map<String, TableColumn> columnMap = idlingReportBean.getTableColumns();
        assertEquals(idlingReportBean.AVAILABLE_COLUMNS.size(), columnMap.size());
        
    }
}
