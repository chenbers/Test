package com.inthinc.pro.backing;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperPrint;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.model.PieScoreData;
import com.inthinc.pro.reports.model.PieScoreRange;
import com.inthinc.pro.util.GraphicUtil;

public class BreakdownBean extends BaseDurationBean
{

    private static final Logger logger = Logger.getLogger(BreakdownBean.class);

    private static List<String> entityColorKey = new ArrayList<String>()
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
    private AccountDAO accountDAO;
  
    private NavigationBean navigation;
    private ReportRenderer reportRenderer;

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
            s = scoreDAO.getScoreBreakdown(this.navigation.getGroupID(), getDuration(), scoreType);
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

        ScoreBox sb = new ScoreBox(getOverallScore(), ScoreBoxSizes.LARGE);
        setOverallScoreStyle(sb.getScoreStyle());
    }

    private void init()
    {
        logger.debug("init()");
        Integer groupID = navigation.getGroupID();
        if (groupID == null)
        {
            groupID = getUser().getPerson().getGroupID();
        }
        ScoreableEntity scoreableEntity = scoreDAO.getAverageScoreByType(groupID, getDuration(), ScoreType.SCORE_OVERALL);
        if (scoreableEntity == null)
            setOverallScore(0);
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
        return getDuration().toString();
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
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(1);
        format.setMinimumFractionDigits(1);
        String overallScore = format.format((double) ((double) getOverallScore() / (double) 10.0));
        
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.OVERALL_SCORE,getNavigation().getGroup().getName(),getAccountName());
        reportCriteria.setMainDataset(getPieScoreData(ScoreType.SCORE_OVERALL));
        reportCriteria.addParameter("OVERALL_SCORE",overallScore);
        //TODO add Duration value to report
        reportCriteria.addParameter("DURATION", "");
        reportCriteria.addParameter("DRIVER_STYLE_DATA",getPieScoreData(ScoreType.SCORE_DRIVING_STYLE));
        reportCriteria.addParameter("SEATBELT_USE_DATA",  getPieScoreData(ScoreType.SCORE_SEATBELT));
        reportCriteria.addParameter("SPEED_DATA", getPieScoreData(ScoreType.SCORE_SPEEDING));
        reportRenderer.exportSingleReportToPDF(reportCriteria, (HttpServletResponse)getExternalContext().getResponse());
        return null;

    }
    
    private String getAccountName()
    {
        Account account = getAccountDAO().findByID(getAccountID());
        String name = account.getAcctName();
        return name;
    }
    
    private List<PieScoreData> getPieScoreData(ScoreType scoreType){
     // Fetch, qualifier is groupId (parent), date from, date to
        List<ScoreableEntity> s = null;
        try
        {
            logger.debug("getting scores for groupID: " + this.navigation.getGroupID());
            // s = scoreDAO.getScores(this.navigation.getGroupID(),
            // startDate, endDate, ScoreType.SCORE_OVERALL_PERCENTAGES);
            s = scoreDAO.getScoreBreakdown(this.navigation.getGroupID(), getDuration(), scoreType);
        }
        catch (Exception e)
        {
            logger.debug("graphicDao error: " + e.getMessage());
        }
        logger.debug("found: " + s.size());

        List<PieScoreData> pieChartDataList = new ArrayList<PieScoreData>();
        
        for(int i = 0;i < s.size();i++){
            PieScoreRange pieScoreRange = PieScoreRange.valueOf(i);
            pieChartDataList.add(new PieScoreData(pieScoreRange.getLabel(),s.get(i).getScore(),pieScoreRange.getLabel()));
        }
        return pieChartDataList;
    }

    public void setAccountDAO(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }

    public AccountDAO getAccountDAO()
    {
        return accountDAO;
    }
    
    public ReportRenderer getReportRenderer()
    {
        return reportRenderer;
    }

    public void setReportRenderer(ReportRenderer reportRenderer)
    {
        this.reportRenderer = reportRenderer;
    }

}
