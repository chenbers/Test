package it.com.inthinc.pro.dao;


import static org.junit.Assert.*;
import it.config.IntegrationConfig;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.ScoreHessianDAO;
import com.inthinc.pro.dao.hessian.extension.HessianDebug;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.dao.hessian.proserver.ReportServiceCreator;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;

public class ReportServiceTest
{
    private static ReportService reportService;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        IntegrationConfig config = new IntegrationConfig(new File("./src/test/resources"));

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        
        reportService = new ReportServiceCreator(host, port).getService();
//        HessianDebug.debugIn = true;
//        HessianDebug.debugOut = true;
        HessianDebug.debugRequest = true;
        
        
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @Test
    @Ignore
    public void averageScoreByType()
    {
        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);
        
        
        Integer groupID = 16777218;
        ScoreableEntity scoreableEntity = scoreDAO.getAverageScoreByType(groupID, Duration.DAYS, ScoreType.SCORE_OVERALL);
        
        assertNotNull(scoreableEntity);
        
        groupID = 483;
        scoreableEntity = scoreDAO.getAverageScoreByType(groupID, Duration.DAYS, ScoreType.SCORE_OVERALL);
        
        assertNull(scoreableEntity);
        
    }

}
