
package com.inthinc.pro.backing;


import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.backing.ui.TabAction;
import com.inthinc.pro.model.Duration;

public class TeamOverviewBeanTest extends BaseBeanTest
{
    
    private static final Logger logger = Logger.getLogger(TeamOverviewBeanTest.class);

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
        // just test the bean successfully creates all of the required bars
        
        // team level login
        loginUser("custom114");
        
        // get the bean from the applicationContext (initialized by Spring injection)
        TeamOverviewBean teamOverviewBean = (TeamOverviewBean)applicationContext.getBean("teamOverviewBean");
        
        // make sure the spring injection worked
        assertNotNull(teamOverviewBean.getScoreDAO());
        assertNotNull(teamOverviewBean.getNavigation());
        
        // simulates the web page clicking on the tabs to get the various pies 
        List<TabAction> actionList = teamOverviewBean.getActions();
        for (TabAction action : actionList)
        {
//logger.info("setSelectedAction: " + action.getAction());            
            teamOverviewBean.setSelectedAction(action);
            
            // bar graph
//logger.info("getSelectedBarDef");            
            String barDef = teamOverviewBean.getSelectedBarDef();
            assertNotNull("Bar missing for action: " + action.getDisplayString(), barDef);
            assertEquals(barDef, teamOverviewBean.getBarDef(action.getScoreType().getCode()));
            
            // overall score
//logger.info("getOverall");            
            assertNotNull(teamOverviewBean.getSelectedOverallScore());
            
            // style
            assertEquals("score_lg_" + (((teamOverviewBean.getSelectedOverallScore()-1)/10)+1), teamOverviewBean.getOverallScoreStyle());
//logger.info("done with action: " + action.getAction());            
        }

        
        // make sure when the duration changes the bar, score get set to null so they are reinitialized
        teamOverviewBean.setDuration(Duration.THREE);
        assertNotNull(teamOverviewBean.getBarDefMap());
        assertNotNull(teamOverviewBean.getOverallScoreMap());
        
        teamOverviewBean.setSelectedAction(null);
        assertEquals(actionList.get(0), teamOverviewBean.getSelectedAction());

        
    
    }
}
