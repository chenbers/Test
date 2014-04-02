package com.inthinc.pro.backing;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.reports.service.impl.ReportCriteriaServiceImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TrendBeanTest extends BaseBeanTest
{
    private ReportCriteriaServiceImpl reportCriteriaServiceImpl;
    private ReportCriteriaService reportCriteriaService;
    private Duration duration;
    private GroupHierarchy gh;
    private DurationBean durationBean;
    private ScoreableEntity scoreableEntity;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    public ScoreableEntity getScoreableEntity() {
        return scoreableEntity;
    }

    public void setScoreableEntity(ScoreableEntity scoreableEntity) {
        this.scoreableEntity = scoreableEntity;
    }

    public DurationBean getDurationBean() {
        return durationBean;
    }

    public void setDurationBean(DurationBean durationBean) {
        this.durationBean = durationBean;
    }

    public ReportCriteriaService getReportCriteriaService() {
        return reportCriteriaService;
    }

    public void setReportCriteriaService(ReportCriteriaService reportCriteriaService) {
        this.reportCriteriaService = reportCriteriaService;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public GroupHierarchy getGh() {
        return gh;
    }

    public void setGh(GroupHierarchy gh) {
        this.gh = gh;
    }

    @Test
    public void bean()
    {
        // just test the bean successfully creates all of the required pies
        
        // team level login
        loginUser("custom101");
        
        // get the bean from the applicationContext (initialized by Spring injection)
        TrendBean trendBean = (TrendBean)applicationContext.getBean("trendBean");
        Locale locale = new Locale("en", "US");

        // make sure the spring injection worked
//        trendBean.setDurationBean();
        assertNotNull(trendBean.getScoreDAO());
        assertNotNull(trendBean.getDurationBean().getDuration());

        // try grabbing some regions based on above, should be 3
        //  for normal101
        trendBean.setGroupID(106);
        trendBean.getEmailAddress();
        trendBean.getAccountDAO();

        trendBean.getGroup().setAggDate("2014-02-05");
        trendBean.getGroup();

        trendBean.getDurationBean().setDuration(Duration.DAYS);

       trendBean.getAccountID();
       trendBean.getGroup();
       trendBean.getGroup().getName();
       trendBean.getGroupHierarchy();
//        assertEquals(3,
//                trendBean.getScoreableEntities().size());
        assertEquals( 1,
                (new Integer(trendBean.getStart())).intValue());
        assertEquals( 5,
                (new Integer(trendBean.getEnd()).intValue()));
//        assertEquals( 3,
//                (new Integer(trendBean.getMaxCount()).intValue()));

        // create xy chart
        assertNotNull(trendBean.getLineDef());

        trendBean.getSummaryItem();

        assertNotNull(trendBean.buildReportCriteria());

    }

}
