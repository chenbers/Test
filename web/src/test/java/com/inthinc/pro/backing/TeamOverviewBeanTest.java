package com.inthinc.pro.backing;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.TabAction;
import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.ScoreType;

public class TeamOverviewBeanTest extends BaseBeanTest
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
            teamOverviewBean.setSelectedAction(action);
            
            // bar graph
            String barDef = teamOverviewBean.getSelectedBarDef();
            assertNotNull("Bar missing for action: " + action.getDisplayString(), barDef);
            assertEquals(barDef, teamOverviewBean.getBarDef(action.getScoreType().getCode()));
            
            // overall score
            assertNotNull(teamOverviewBean.getSelectedOverallScore());
            
            // style
            assertEquals("score_lg_" + (((teamOverviewBean.getSelectedOverallScore()-1)/10)+1), teamOverviewBean.getOverallScoreStyle());
        }

        
        // make sure when the duration changes the bar, score get set to null so they are reinitialized
        teamOverviewBean.setDuration(Duration.THREE);
        assertNotNull(teamOverviewBean.getBarDefMap());
        assertNotNull(teamOverviewBean.getOverallScoreMap());
        
        teamOverviewBean.setSelectedAction(null);
        assertEquals(actionList.get(0), teamOverviewBean.getSelectedAction());

        
    
    }
}
