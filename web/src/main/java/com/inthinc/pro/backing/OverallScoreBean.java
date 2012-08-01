package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.util.GraphicUtil;

@KeepAlive
public class OverallScoreBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(OverallScoreBean.class);

    public OverallScoreBean() {
        super();
    }

    public final static List<String> entityColorKey = new ArrayList<String>() {
        {
            add("FF0101");
            add("FF6601");
            add("F6B305");
            add("1E88C8");
            add("6B9D1B");
        }
    };

    private ScoreDAO scoreDAO;

    private Integer groupID;
    private Group group;

    private DurationBean durationBean;
    private ReportRenderer reportRenderer;
    private ReportCriteriaService reportCriteriaService;

    private String overallPieDef;
    private Integer overallScore;
    private String overallScoreStyle;

    private String stylePieDef;
    private String speedPieDef;
    private String seatBeltPieDef;

    public String getOverallPieDef() {
        if ( overallPieDef == null ) 
        overallPieDef = createPieDef(ScoreType.SCORE_OVERALL);
        // logger.debug("returned string: " + pieDef);
        return overallPieDef;
    }
    
    public void setOverallPieDef(String overallPieDef) {
        this.overallPieDef = overallPieDef;
    }
    public void createOverallScorePieDef(){
    	
    	overallPieDef = createPieDef(ScoreType.SCORE_OVERALL);
    }
    public String createPieDef(ScoreType scoreType) {
        StringBuffer sb = new StringBuffer();

        // Control parameters
        sb.append(GraphicUtil.getPieControlParameters());

        // Fetch, qualifier is groupId (parent), date from, date to
        List<ScoreableEntity> s = null;
        try {
            logger.debug("getting scores for groupID: " + groupID);
            // s = scoreDAO.getScores(this.navigation.getGroupID(),
            // startDate, endDate, ScoreType.SCORE_OVERALL_PERCENTAGES);
            s = scoreDAO.getScoreBreakdown(groupID, durationBean.getDuration(), scoreType, getGroupHierarchy());
        } catch (Exception e) {
            logger.debug("graphicDao error: " + e.getMessage());
        }
        logger.debug("found: " + s.size());

        // Calculate total observations and set the pie data
        // this may change to be actual percentages
        ScoreableEntity se = null;
        for (int i = 0; i < s.size(); i++) {
            se = (ScoreableEntity) s.get(i);
            Integer percent = se.getScore();

            if (percent == 0) // Do not display 0% pie slices.
                continue;

            sb.append("<set value=\'" + percent.toString() + "\' " + "label=\'\' color=\'" + (OverallScoreBean.entityColorKey.get(i)) + "\'/>");
        }
        sb.append("</chart>");

        return sb.toString();
    }

    private void initStyle() {
        if (overallScore == null) {
            populateOverallScore();
        }

        setOverallScoreStyle(ScoreBox.GetStyleFromScore(getOverallScore(), ScoreBoxSizes.LARGE));
    }

    private void init() {
        logger.debug("init()");
        populateOverallScore();
    }

    private void populateOverallScore() {
        ScoreableEntity scoreableEntity = scoreDAO.getAverageScoreByType(groupID, durationBean.getDuration(), ScoreType.SCORE_OVERALL, getGroupHierarchy());
        if (scoreableEntity == null || scoreableEntity.getScore() == null)
            setOverallScore(-1);
        else
            setOverallScore(scoreableEntity.getScore());
    }

    public Integer getOverallScore() {
        if (overallScore == null) {
        	populateOverallScore();
        }
        return overallScore;
    }

    public void setOverallScore(Integer overallScore) {
        this.overallScore = overallScore;
        initStyle();
    }

    public String getOverallScoreStyle() {
        if (overallScoreStyle == null) {
            initStyle();
        }
        logger.debug("overallScoreStyle = " + overallScoreStyle);
        return overallScoreStyle;
    }

    // OVERALL SCORE STYLE PROPERTIES
    public void setOverallScoreStyle(String overallScoreStyle) {
        this.overallScoreStyle = overallScoreStyle;
    }

    public String getDurationAsString() {
        return durationBean.getDuration().toString();
    }

    // DAO PROPERTIES
    public ScoreDAO getScoreDAO() {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO) {
        this.scoreDAO = scoreDAO;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
        group = getGroupHierarchy().getGroup(groupID);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public DurationBean getDurationBean() {
        return durationBean;
    }

    public void setDurationBean(DurationBean durationBean) {
        this.durationBean = durationBean;
    }

    // DRIVING STYLE PIE PROPERTIES
    public String getStylePieDef() {
        stylePieDef = createPieDef(ScoreType.SCORE_DRIVING_STYLE);
        return stylePieDef;
    }

    public void setStylePieDef(String stylePieDef) {
        this.stylePieDef = stylePieDef;
    }

    // SPEED PIE PROPERTIES
    public String getSpeedPieDef() {
        speedPieDef = createPieDef(ScoreType.SCORE_SPEEDING);
        return speedPieDef;
    }

    public void setSpeedPieDef(String speedPieDef) {
        this.speedPieDef = speedPieDef;
    }

    // SEAT BELT PIE PROPERTIES
    public String getSeatBeltPieDef() {
        seatBeltPieDef = createPieDef(ScoreType.SCORE_SEATBELT);
        return seatBeltPieDef;
    }

    public void setSeatBeltPieDef(String seatBeltPieDef) {
        this.seatBeltPieDef = seatBeltPieDef;
    }

    public String exportToPDF() {
        ReportCriteria reportCriteria = buildReportCriteria();
        reportRenderer.exportSingleReportToPDF(reportCriteria, getFacesContext());
        return null;

    }

    public ReportCriteria buildReportCriteria() {
        ReportCriteria reportCriteria = reportCriteriaService.getOverallScoreReportCriteria(groupID, durationBean.getDuration(), getLocale(), getGroupHierarchy());
        reportCriteria.setLocale(getLocale());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setUseMetric(getMeasurementType() == MeasurementType.METRIC);
        return reportCriteria;
    }

    public ReportRenderer getReportRenderer() {
        return reportRenderer;
    }

    public void setReportRenderer(ReportRenderer reportRenderer) {
        this.reportRenderer = reportRenderer;
    }

    public ReportCriteriaService getReportCriteriaService() {
        return reportCriteriaService;
    }

    public void setReportCriteriaService(ReportCriteriaService reportCriteriaService) {
        this.reportCriteriaService = reportCriteriaService;
    }

    public void durationChangeActionListener(ActionEvent event) {
        populateOverallScore();
    }
}
