package com.inthinc.pro.backing;

import static org.easymock.classextension.EasyMock.createMock;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.richfaces.component.UITree;
import org.richfaces.event.DataScrollerEvent;

public class TableStatsBeanTest extends BaseBeanTest
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
        TableStatsBean tableStatsBean = new TableStatsBean(); 
        
        //Set Bean for a table with 500 records.  10 rows per page.
        tableStatsBean.reset(10, 500);
        
        //start and end for page one should be "showing 1 to 10 of 500"
        assertTrue( (tableStatsBean.getTableRowStart() == 1) );
        assertTrue( (tableStatsBean.getTableRowEnd() == 10) );
        assertTrue( (tableStatsBean.getTableSize() == 500) );
        
        // Navigate to page 4
        UITree uiComponent = createMock(UITree.class);
        DataScrollerEvent se = new DataScrollerEvent(uiComponent, "1", "2", 4);
        tableStatsBean.scrollerListener(se);
        
        assertTrue( (tableStatsBean.getTableRowStart() == 31) );
        assertTrue( (tableStatsBean.getTableRowEnd() == 40) );
        assertTrue( (tableStatsBean.getTableSize() == 500) );
        
        //set table to less than size of row count per page.
        tableStatsBean.updateSize(9);
        
        //test row count per page should get updated.
        assertTrue( (tableStatsBean.getTableRowEnd() == 9) );
    }
}
