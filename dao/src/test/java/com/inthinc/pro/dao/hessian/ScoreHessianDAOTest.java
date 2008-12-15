package com.inthinc.pro.dao.hessian;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.EnumSet;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.data.SearchCriteria;
import com.inthinc.pro.dao.mock.proserver.ReportServiceCreator;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreTypeBreakdown;
import com.inthinc.pro.model.ScoreableEntity;

public class ScoreHessianDAOTest
{
    ScoreHessianDAO      scoreHessianDAO;


    @Before
    public void setUp() throws Exception
    {
        scoreHessianDAO = new ScoreHessianDAO();
        scoreHessianDAO.setSiloService(new SiloServiceCreator().getService());
        ReportServiceCreator reportServiceCreator = new ReportServiceCreator();
        scoreHessianDAO.setReportService(reportServiceCreator.getService());

    }

    @Test
    public void getOverallScore()
    {
        for (Duration d : EnumSet.allOf(Duration.class))
        {
            ScoreableEntity score = scoreHessianDAO.getAverageScoreByType(MockData.TOP_GROUP_ID, d, ScoreType.SCORE_OVERALL);

            assertNotNull(d.name() + " " + score.toString(), score);
            assertEquals(MockData.TOP_GROUP_ID, score.getEntityID());
            assertTrue(d.name() + " " + score.toString(), (score.getScore() >= 0 && score.getScore() <= 50));
        }
    }

    @Test
    public void getScores()
    {

        Integer testGroupID = MockData.DIVISION_GROUP_ID;
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("parentID",testGroupID);
        int totalChildGroups = MockData.getInstance().retrieveObjectList(Group.class, searchCriteria).size();

        for (ScoreType scoreType : EnumSet.allOf(ScoreType.class))
        {
            if (scoreType.equals(ScoreType.SCORE_OVERALL_TIME))
            {
                continue;
            }
            for (Duration d : EnumSet.allOf(Duration.class))
            {
                List<ScoreableEntity> scoreList = scoreHessianDAO.getScores(testGroupID, d, scoreType);
    
                assertNotNull(d.name() + " " + scoreType.toString(), scoreList);
                assertEquals(d.name() + " " + scoreType.toString(), totalChildGroups, scoreList.size());
            }
        }
    }

    @Test
    public void getPercentScores()
    {
        Integer testGroupID = MockData.DIVISION_GROUP_ID;
        List<ScoreableEntity> scoreList = scoreHessianDAO.getScoreBreakdown(testGroupID, Duration.DAYS, ScoreType.SCORE_OVERALL);

        assertNotNull(scoreList);
        assertEquals(5, scoreList.size());
        
        int total = 0;
        for (ScoreableEntity score : scoreList)
        {
            total+=score.getScore();
        }
        assertEquals(100, total);

    }

    @Test
    public void getScoreBreakdownByType()
    {
        Integer testGroupID = MockData.unitTestStats.UNIT_TEST_GROUP_ID;
        
        checkBreakdown(testGroupID, Duration.DAYS, ScoreType.SCORE_OVERALL, 4);
        checkBreakdown(testGroupID, Duration.DAYS, ScoreType.SCORE_SPEEDING, 6);
        checkBreakdown(testGroupID, Duration.DAYS, ScoreType.SCORE_SEATBELT, 5);
        checkBreakdown(testGroupID, Duration.DAYS, ScoreType.SCORE_DRIVING_STYLE, 6);
    }

    private void checkBreakdown(Integer testGroupID, Duration duration, ScoreType scoreType, int expectedBreakdownSize)
    {
        Integer totalDrivers = MockData.getInstance().unitTestStats.totalDrivers;
        List<ScoreTypeBreakdown> scoreBreakdownList = scoreHessianDAO.getScoreBreakdownByType(testGroupID, duration, scoreType);
        assertEquals(expectedBreakdownSize, scoreBreakdownList.size());
        List<ScoreType> subTypeList = scoreType.getSubTypes();
        assertEquals(subTypeList.size(), scoreBreakdownList.size());
        for (ScoreType subType : subTypeList)
        {
           
           List<Integer> valueList = MockData.getInstance().unitTestStats.totalScoreCntPerType.get(subType);
           boolean found = false;
           for (ScoreTypeBreakdown breakdown : scoreBreakdownList)
           {
               if (breakdown.getScoreType().equals(subType))
               {
                   found = true;
                   assertEquals(" " + subType , 5, breakdown.getPercentageList().size());
                   int total = 0; 
                   for (int i = 0; i < 5; i++)
                   {
                       total += valueList.get(i);
                   }
                   int percentTotal = 0;
                   for (int i = 0; i < 5; i++)
                   {
                       Integer expected = null;
                       if (i == 4)
                       {
                           expected = 100 - percentTotal;
                       }
                       else
                       {
                           expected = Math.round((float) ((float) valueList.get(i) * 100f) / (float) total);
                           percentTotal += expected;
                       }
                       assertEquals(expected, breakdown.getPercentageList().get(i).getScore());
                   }
                   break;
               }
           }
           
           assertTrue(scoreType + " SubType: " + subType + " not found", found);
        }
    }
}
