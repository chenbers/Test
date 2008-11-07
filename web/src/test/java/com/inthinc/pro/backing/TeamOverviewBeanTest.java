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
        // just test the bean successfully creates all of the required pies
        
        // team level login
        loginUser("custom114");
        
        // get the bean from the applicationContext (initialized by Spring injection)
        TeamOverviewBean teamOverviewBean = (TeamOverviewBean)applicationContext.getBean("teamOverviewBean");
        
        // make sure the spring injection worked
        assertNotNull(teamOverviewBean.getScoreDAO());
        assertNotNull(teamOverviewBean.getNavigation());
        
        // overall score
        assertNotNull(teamOverviewBean.getOverallScore());
        teamOverviewBean.setOverallScore(new Integer(20));
        assertEquals(20, teamOverviewBean.getOverallScore().intValue());
        
        // style
        assertEquals("score_lg_2", teamOverviewBean.getOverallScoreStyle());
        
        // simulates the web page clicking on the tabs to get the various pies 
        List<TabAction> actionList = teamOverviewBean.getActions();
        for (TabAction action : actionList)
        {
            teamOverviewBean.setSelectedAction(action);
            String pieDef = teamOverviewBean.getSelectedPieDef();
            assertNotNull("Pie missing for action: " + action.getDisplayString(), pieDef);
            assertEquals(pieDef, teamOverviewBean.getPieDef(action.getScoreType().getCode()));
            
            if (action.getScoreType() == ScoreType.SCORE_OVERALL)
                    assertEquals(pieDef, teamOverviewBean.getOverallPieDef());
            else if (action.getScoreType() == ScoreType.SCORE_DRIVING_STYLE)
                    assertEquals(pieDef, teamOverviewBean.getDriveStylePieDef());
            else if (action.getScoreType() == ScoreType.SCORE_SPEEDING)
                    assertEquals(pieDef, teamOverviewBean.getSpeedPieDef());
            else if (action.getScoreType() == ScoreType.SCORE_SEATBELT)
                    assertEquals(pieDef, teamOverviewBean.getSeatbeltPieDef());
        }

        
        // make sure when the duration changes the pies, score get set to null so they are reinitialized
        teamOverviewBean.setDuration(Duration.THREE);
        assertNotNull(teamOverviewBean.getPieDefMap());
        assertNotNull(teamOverviewBean.getOverallScore());
        
        teamOverviewBean.setSelectedAction(null);
        assertEquals(actionList.get(0), teamOverviewBean.getSelectedAction());
    }
}
