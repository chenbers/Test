package com.inthinc.pro.dao.hessian;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.mock.proserver.CentralServiceCreator;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;

public class GraphicHessianDAOTest
{
    GraphicHessianDAO graphicHessianDAO;
    
    static final Integer TOP_GROUP_ID = 101;
    
    @Before
    public void setUp() throws Exception
    {
        graphicHessianDAO = new GraphicHessianDAO();
        graphicHessianDAO.setServiceCreator(new CentralServiceCreator());
        graphicHessianDAO.setSiloServiceCreator(new SiloServiceCreator());
        
    }
    
    @Test
    public void getScores()
    {
        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, 30);
        List<ScoreableEntity> scoreList = graphicHessianDAO.getScores(TOP_GROUP_ID, startDate, endDate, ScoreType.SCORE_OVERALL);
        
        assertNotNull(scoreList);
        
        for (ScoreableEntity score : scoreList)
        {
            System.out.println("score " + score.getEntityID() + " " + score.getEntityType().toString() + " " + score.getScore());
        }
        
    }
}
