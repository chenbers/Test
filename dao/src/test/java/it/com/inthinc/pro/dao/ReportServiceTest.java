package it.com.inthinc.pro.dao;


import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Map;

import it.config.IntegrationConfig;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.MpgHessianDAO;
import com.inthinc.pro.dao.hessian.ScoreHessianDAO;
import com.inthinc.pro.dao.hessian.extension.HessianDebug;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.dao.hessian.proserver.ReportServiceCreator;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;

public class ReportServiceTest
{
    private static ReportService reportService;
    private static SiloService siloService;

    // these totally depend on David Story running his Speedracer script when reinitializing the db
    // these are the IDs for the speed racer driver
    private static final Integer TEST_DIVISION_GROUP_ID = 1; //16777218;
    private static final Integer TEST_TEAM_GROUP_ID = 2; //16777228;
    private static final Integer TEST_DRIVER_ID = 1; //16777308;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        
        reportService = new ReportServiceCreator(host, port).getService();
        siloService = new SiloServiceCreator(host, port).getService();
//        HessianDebug.debugIn = true;
//        HessianDebug.debugOut = true;
//        HessianDebug.debugRequest = true;
        
        
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @Test
    public void averageScoreByType()
    {
        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);
        
        
        Integer groupID = TEST_DIVISION_GROUP_ID;
        ScoreableEntity scoreableEntity = scoreDAO.getAverageScoreByType(groupID, Duration.TWELVE, ScoreType.SCORE_OVERALL);
        
        assertNotNull(scoreableEntity);
        
        
    }
    
    @Test
    public void getScores()
    {
        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);
        
        
        Integer groupID = TEST_DIVISION_GROUP_ID;
        List<ScoreableEntity> scoreableEntityList = scoreDAO.getScores(groupID, Duration.DAYS, ScoreType.SCORE_OVERALL);
        
        assertNotNull(scoreableEntityList);
        System.out.println("GroupID: " + groupID + " num entries: " + scoreableEntityList.size());
        
    }

    @Test
    public void getScoreBreakdown()
    {
        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);
        
        
        Integer groupID = TEST_DIVISION_GROUP_ID;
        List<ScoreableEntity> scoreableEntityList = scoreDAO.getScoreBreakdown(groupID, Duration.DAYS, ScoreType.SCORE_OVERALL);
        
        assertNotNull(scoreableEntityList);
        
    }

    @Test
    public void getScoreTrend()
    {
        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);
        
        
        Integer groupID = TEST_DIVISION_GROUP_ID;
        Map<Integer,List<ScoreableEntity>> scoreMap  = scoreDAO.getTrendScores(groupID, Duration.DAYS);
        
        
        
        assertNotNull(scoreMap);
        
        for (Integer id : scoreMap.keySet())
        {
            System.out.println("id: " + id);
            for (ScoreableEntity s : scoreMap.get(id))
            {
                System.out.println("    s: " + s.getScore());
                
            }
        }
        
    }
    @Test
    public void mpg()
    {
        MpgHessianDAO mpgDAO = new MpgHessianDAO();
        mpgDAO.setReportService(reportService);
        
        GroupHessianDAO groupDAO = new GroupHessianDAO();
        groupDAO.setSiloService(siloService);
        
        Group divisionGroup = groupDAO.findByID(TEST_DIVISION_GROUP_ID);
                
        List<MpgEntity> mpgList = mpgDAO.getEntities(divisionGroup, Duration.DAYS);
        assertNotNull(mpgList);
        
        for (MpgEntity mpg : mpgList)
        {
            System.out.println("groupID: " + mpg.getGroupID() + " " + mpg.getEntityName() + " heavy: " + mpg.getHeavyValue() + " med: " + mpg.getMediumValue() + " light: " + mpg.getLightValue());
        }

        Group teamGroup = groupDAO.findByID(TEST_TEAM_GROUP_ID);
        
        mpgList = mpgDAO.getEntities(teamGroup, Duration.DAYS);
        assertNotNull(mpgList);
      
        for (MpgEntity mpg : mpgList)
        {
            System.out.println("groupID: " + mpg.getGroupID() + " " + mpg.getEntityName() + " " + mpg.getEntityID() + " heavy: " + mpg.getHeavyValue() + " med: " + mpg.getMediumValue() + " light: " + mpg.getLightValue());
        }
      
        
    }
    
    @Test
    public void getTopBottomFiveScores()
    {
        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);
     
        Integer groupID = TEST_TEAM_GROUP_ID;
        List<ScoreableEntity> scoreableEntityList = scoreDAO.getTopFiveScores(groupID);
        assertNotNull(scoreableEntityList);
        assertTrue(scoreableEntityList.size() <= 5);
        
        int score = 51;
        for (ScoreableEntity s : scoreableEntityList)
        {
            System.out.println("driverID: " + s.getEntityID() + " " + s.getIdentifier() + " " + s.getEntityID() + " score" + s.getScore());
            assertTrue(s.getScore() <= score);
            score = s.getScore();
            
        }
        
        scoreableEntityList = scoreDAO.getTopFiveScores(groupID);
        assertNotNull(scoreableEntityList);
        assertTrue(scoreableEntityList.size() <= 5);
        
        score = 0;
        for (ScoreableEntity s : scoreableEntityList)
        {
            System.out.println("driverID: " + s.getEntityID() + " " + s.getIdentifier() + " " + s.getEntityID() + " score" + s.getScore());
            assertTrue(s.getScore() >= score);
            score = s.getScore();
            
        }
    }
    
    
    
    

    private void dumpScoreableEntity(ScoreableEntity scoreableEntity)
    {
        System.out.println(scoreableEntity.getIdentifier() + " " +
                scoreableEntity.getEntityID() +" " +
                scoreableEntity.getEntityType().toString() +" " +
                scoreableEntity.getScore() +" " +
                scoreableEntity.getScoreType().toString());
    }
}
