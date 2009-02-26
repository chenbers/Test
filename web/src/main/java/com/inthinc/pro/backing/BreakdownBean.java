package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.util.GraphicUtil;

public class BreakdownBean extends BaseBean
{

    private static final Logger logger = Logger.getLogger(BreakdownBean.class);

    public static List<String> entityColorKey = new ArrayList<String>()
    {
        {
            add("FF0101");
            add("FF6601");
            add("F6B305");
            add("1E88C8");
            add("6B9D1B");
        }
    };

    private ScoreDAO scoreDAO;
  
    private NavigationBean navigation;
    private DurationBean durationBean;
    private ReportRenderer reportRenderer;
    private ReportCriteriaService reportCriteriaService;

    private String overallPieDef;
    private Integer overallScore;
    private String overallScoreStyle;

    private String stylePieDef;
    private String speedPieDef;
    private String seatBeltPieDef;

    public String getOverallPieDef()
    {
        overallPieDef = createPieDef(ScoreType.SCORE_OVERALL);
        // logger.debug("returned string: " + pieDef);
        return overallPieDef;
    }

    public void setOverallPieDef(String overallPieDef)
    {
        this.overallPieDef = overallPieDef;
    }

    public String createPieDef(ScoreType scoreType)
    {
        StringBuffer sb = new StringBuffer();

        // Control parameters
        sb.append(GraphicUtil.getPieControlParameters());

        // Fetch, qualifier is groupId (parent), date from, date to
        List<ScoreableEntity> s = null;
        try
        {
            logger.debug("getting scores for groupID: " + this.navigation.getGroupID());
            // s = scoreDAO.getScores(this.navigation.getGroupID(),
            // startDate, endDate, ScoreType.SCORE_OVERALL_PERCENTAGES);
            s = scoreDAO.getScoreBreakdown(this.navigation.getGroupID(), durationBean.getDuration(), scoreType);
        }
        catch (Exception e)
        {
            logger.debug("graphicDao error: " + e.getMessage());
        }
        logger.debug("found: " + s.size());

        // Calculate total observations and set the pie data
        // this may change to be actual percentages
        ScoreableEntity se = null;
        for (int i = 0; i < s.size(); i++)
        {
            se = (ScoreableEntity) s.get(i);
            Integer percent = se.getScore();
            sb.append("<set value=\'" + percent.toString() + "\' " + "label=\'\' color=\'" + (BreakdownBean.entityColorKey.get(i)) + "\'/>");
        }
        sb.append("</chart>");

        return sb.toString();
    }

    private void initStyle()
    {
        if (overallScore == null)
        {
            init();
        }

        setOverallScoreStyle(ScoreBox.GetStyleFromScore(getOverallScore(), ScoreBoxSizes.LARGE));
    }

    private void init()
    {
        logger.debug("init()");
        Integer groupID = navigation.getGroupID();
        if (groupID == null)
        {
            groupID = getUser().getGroupID();
        }
        ScoreableEntity scoreableEntity = scoreDAO.getAverageScoreByType(groupID, durationBean.getDuration(), ScoreType.SCORE_OVERALL);
        if (scoreableEntity.getScore() == null)
            setOverallScore(-1);
        else
            setOverallScore(scoreableEntity.getScore());
    }

    public Integer getOverallScore()
    {
        if (overallScore == null)
        {
            init();
        }
        return overallScore;
    }

    public void setOverallScore(Integer overallScore)
    {
        this.overallScore = overallScore;
        initStyle();
    }

    public String getOverallScoreStyle()
    {
        if (overallScoreStyle == null)
        {
            initStyle();
        }
        logger.debug("overallScoreStyle = " + overallScoreStyle);
        return overallScoreStyle;
    }

    // OVERALL SCORE STYLE PROPERTIES
    public void setOverallScoreStyle(String overallScoreStyle)
    {
        this.overallScoreStyle = overallScoreStyle;
    }

    public String getDurationAsString()
    {
        return durationBean.getDuration().toString();
    }

    // DAO PROPERTIES
    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO)
    {
        this.scoreDAO = scoreDAO;
    }

    // NAVIGATION BEAN PROPERTIES
    public NavigationBean getNavigation()
    {
        return navigation;
    }

    public void setNavigation(NavigationBean navigation)
    {
        this.navigation = navigation;
    }

    public DurationBean getDurationBean()
    {
        return durationBean;
    }

    public void setDurationBean(DurationBean durationBean)
    {
        this.durationBean = durationBean;
    }

    // DRIVING STYLE PIE PROPERTIES
    public String getStylePieDef()
    {
        stylePieDef = createPieDef(ScoreType.SCORE_DRIVING_STYLE);
        return stylePieDef;
    }

    public void setStylePieDef(String stylePieDef)
    {
        this.stylePieDef = stylePieDef;
    }

    // SPEED PIE PROPERTIES
    public String getSpeedPieDef()
    {
        speedPieDef = createPieDef(ScoreType.SCORE_SPEEDING);
        return speedPieDef;
    }

    public void setSpeedPieDef(String speedPieDef)
    {
        this.speedPieDef = speedPieDef;
    }

    // SEAT BELT PIE PROPERTIES
    public String getSeatBeltPieDef()
    {
        seatBeltPieDef = createPieDef(ScoreType.SCORE_SEATBELT);
        return seatBeltPieDef;
    }

    public void setSeatBeltPieDef(String seatBeltPieDef)
    {
        this.seatBeltPieDef = seatBeltPieDef;
    }

    public String exportToPDF()
    {
        ReportCriteria reportCriteria = buildReportCriteria();
        reportRenderer.exportSingleReportToPDF(reportCriteria, getFacesContext());
        return null;

    }
    
    public ReportCriteria buildReportCriteria(){
        ReportCriteria reportCriteria = reportCriteriaService.getOverallScoreReportCriteria(navigation.getGroupID(), durationBean.getDuration());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        return reportCriteria;
    }
    
    public ReportRenderer getReportRenderer()
    {
        return reportRenderer;
    }

    public void setReportRenderer(ReportRenderer reportRenderer)
    {
        this.reportRenderer = reportRenderer;
    }
    
    public ReportCriteriaService getReportCriteriaService()
    {
        return reportCriteriaService;
    }

    public void setReportCriteriaService(ReportCriteriaService reportCriteriaService)
    {
        this.reportCriteriaService = reportCriteriaService;
    }

}
