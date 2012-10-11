package com.inthinc.pro.backing;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TrendBeanTest extends BaseBeanTest
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
        TrendBean trendBean = (TrendBean)applicationContext.getBean("trendBean");

        
        // make sure the spring injection worked
        assertNotNull(trendBean.getScoreDAO());
        assertNotNull(trendBean.getDurationBean());
                
        // try grabbing some regions based on above, should be 3 
        //  for normal101
        trendBean.setGroupID(101);
        Integer start = trendBean.getStart();   
/*        
        assertEquals(3,
                trendBean.getScoreableEntities().size());        
        assertEquals( 1,
                (new Integer(trendBean.getStart())).intValue());
        assertEquals( 1,
                (new Integer(trendBean.getEnd()).intValue()));
        assertEquals( 3,
                (new Integer(trendBean.getMaxCount()).intValue()));
*/       
        // create xy chart
        assertNotNull(trendBean.getLineDef());
    }
}
