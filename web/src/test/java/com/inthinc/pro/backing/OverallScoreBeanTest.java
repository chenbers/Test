package com.inthinc.pro.backing;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class OverallScoreBeanTest extends BaseBeanTest {


    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }


     @Test
    public void buildReportCriteria(){

         // just test the bean successfully creates all of the required pies
         loginUser("custom101");

         OverallScoreBean overallScore = (OverallScoreBean)applicationContext.getBean("overallScoreBean");
         overallScore.setGroupID(1);
         overallScore.setEmailAddress("test@email.com");
         overallScore.setOverallScore(2);

         //build report criteria
         assertNotNull(overallScore.buildReportCriteria());
         //test getReportCriteriaService
         assertNotNull(overallScore.getReportCriteriaService());
         //getReportRenderer
         assertNotNull(overallScore.getReportRenderer());

     }

}
