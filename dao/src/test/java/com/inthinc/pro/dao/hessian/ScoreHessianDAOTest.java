package com.inthinc.pro.dao.hessian;

import static org.junit.Assert.*;

import java.util.EnumSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.data.SearchCriteria;
import com.inthinc.pro.dao.mock.proserver.CentralServiceCreator;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreValueType;
import com.inthinc.pro.model.ScoreableEntity;

public class ScoreHessianDAOTest
{
    ScoreHessianDAO      scoreHessianDAO;

    static final Integer TOP_GROUP_ID = 101;
    static final Integer SUB_GROUP_ID = 102;

    @Before
    public void setUp() throws Exception
    {
        scoreHessianDAO = new ScoreHessianDAO();
        scoreHessianDAO.setServiceCreator(new CentralServiceCreator());
        scoreHessianDAO.setSiloServiceCreator(new SiloServiceCreator());

    }

    @Test
    public void getScores()
    {
        int[] monthsBack = { 12, 6, 3, 0 };

        Integer testGroupID = SUB_GROUP_ID;

        Integer currentDate = MockData.getInstance().dateNow;
        for (ScoreType scoreType : EnumSet.allOf(ScoreType.class))
        {
            if (scoreType.equals(ScoreType.SCORE_OVERALL_TIME))
            {
                continue;
            }
            for (int i = 0; i < monthsBack.length; i++)
            {
                Integer endDate = DateUtil.getTodaysDate();
                Integer startDate = DateUtil.getDaysBackDate(endDate, monthsBack[i] * 30);
                List<ScoreableEntity> scoreList = scoreHessianDAO.getScores(TOP_GROUP_ID, startDate, endDate, ScoreType.SCORE_OVERALL);
    
                assertNotNull(scoreList);
    
                SearchCriteria searchCriteria = new SearchCriteria();
                searchCriteria.addKeyValue("entityID", testGroupID);
                searchCriteria.addKeyValue("scoreValueType", ScoreValueType.SCORE_SCALE_0_50);
                searchCriteria.addKeyValue("date", DateUtil.getDaysBackDate(currentDate, monthsBack[i] * 30));
                ScoreableEntity expectedScore = MockData.getInstance().retrieveObject(ScoreableEntity.class, searchCriteria);
    
                for (ScoreableEntity score : scoreList)
                {
                    if (score.getEntityID().equals(testGroupID))
                    {
                        System.out.println("score " + score.getEntityID() + " " + score.getEntityType().toString() + " " + score.getScore());
                        assertEquals(expectedScore.getScore(), score.getScore());
                    }
                }
            }
        }
    }

    @Test
    public void getPercentScores()
    {
        Integer testGroupID = SUB_GROUP_ID;
        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, 30);
        List<ScoreableEntity> scoreList = scoreHessianDAO.getScoreBreakdown(testGroupID, startDate, endDate, ScoreType.SCORE_OVERALL);

        assertNotNull(scoreList);
        assertEquals(5, scoreList.size());
        
        int total = 0;
        for (ScoreableEntity score : scoreList)
        {
            total+=score.getScore();
        }
        assertEquals(100, total);

    }

}
