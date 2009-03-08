package com.inthinc.pro.backing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.charts.Line;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.model.CategorySeriesData;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.util.MessageUtil;

public class DriverStyleBean extends BaseBean
{
    private static final Logger   logger      = Logger.getLogger(DriverStyleBean.class);
    private NavigationBean        navigation;
    private DurationBean          durationBean;
    private ScoreDAO              scoreDAO;
    private EventDAO              eventDAO;
    private TableStatsBean        tableStatsBean;
    private AddressLookup         addressLookup;

    private Integer               styleScoreOverall;
    private String                styleScoreOverallStyle;
    private Integer               styleScoreAccel;
    private String                styleScoreAccelStyle;
    private Integer               styleScoreBrake;
    private String                styleScoreBrakeStyle;
    private Integer               styleScoreBump;
    private String                styleScoreBumpStyle;
    private Integer               styleScoreTurn;
    private String                styleScoreTurnStyle;

    private String                styleScoreHistoryOverall;
    private String                styleScoreHistoryAccel;
    private String                styleScoreHistoryBrake;
    private String                styleScoreHistoryBump;
    private String                styleScoreHistoryTurn;

    private EventReportItem       clearItem;
    private static final Integer  NO_SCORE    = -1;
    private ReportRenderer        reportRenderer;
    private String                emailAddress;

    private List<EventReportItem> styleEvents = new ArrayList<EventReportItem>();

    private void init()
    {
        int driverID = navigation.getDriver().getDriverID();

        Map<ScoreType, ScoreableEntity> scoreMap = scoreDAO.getDriverScoreBreakdownByType(driverID, durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE);

        ScoreableEntity se = scoreMap.get(ScoreType.SCORE_DRIVING_STYLE);
        setStyleScoreOverall(se == null ? NO_SCORE : se.getScore());

        se = scoreMap.get(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL);
        setStyleScoreAccel(se == null ? NO_SCORE : se.getScore());

        se = scoreMap.get(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE);
        setStyleScoreBrake(se == null ? NO_SCORE : se.getScore());

        se = scoreMap.get(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP);
        setStyleScoreBump(se == null ? NO_SCORE : se.getScore());

        se = scoreMap.get(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN);
        setStyleScoreTurn(se == null ? NO_SCORE : se.getScore());
    }

    public void getViolations()
    {
        if (styleEvents.isEmpty())
        {
            List<Integer> types = new ArrayList<Integer>();
            types.add(EventMapper.TIWIPRO_EVENT_NOTEEVENT);

            List<Event> tempEvents = new ArrayList<Event>();
            tempEvents = eventDAO.getEventsForDriver(navigation.getDriver().getDriverID(), durationBean.getStartDate(), durationBean.getEndDate(), types);

            for (Event event : tempEvents)
            {
                event.setAddressStr(addressLookup.getAddress(event.getLatitude(), event.getLongitude()));
                styleEvents.add(new EventReportItem(event, this.navigation.getDriver().getPerson().getTimeZone()));
            }

            tableStatsBean.setPage(1);
            tableStatsBean.setTableRowCount(10);
            tableStatsBean.setTableSize(styleEvents.size());
        }
    }

    public String createLineDef(ScoreType scoreType)
    {
        StringBuffer sb = new StringBuffer();
        Line line = new Line();

        // Start XML Data
        sb.append(line.getControlParameters());

        List<ScoreableEntity> scoreList = scoreDAO.getDriverScoreHistory(navigation.getDriver().getDriverID(), durationBean.getDuration(), scoreType, GraphicUtil
                .getDurationSize(durationBean.getDuration()));

        // Get "x" values
        List<String> monthList = GraphicUtil.createMonthList(durationBean.getDuration());

        int cnt = 0;
        for (ScoreableEntity e : scoreList)
        {
            if (e.getScore() != null)
            {
                sb.append(line.getChartItem(new Object[] { (double) (e.getScore() / 10.0d), monthList.get(cnt) }));
            }
            else
            {
                sb.append(line.getChartItem(new Object[] { null, monthList.get(cnt) }));
            }
            cnt++;
        }

        // End XML Data
        sb.append(line.getClose());

        return sb.toString();
    }

    // STYLE OVERALL SCORE PROPERTY
    public Integer getStyleScoreOverall()
    {
        return styleScoreOverall;
    }

    public void setStyleScoreOverall(Integer styleScoreOverall)
    {
        this.styleScoreOverall = styleScoreOverall;
        setStyleScoreOverallStyle(ScoreBox.GetStyleFromScore(styleScoreOverall, ScoreBoxSizes.MEDIUM));
    }

    public String getStyleScoreOverallStyle()
    {
        return styleScoreOverallStyle;
    }

    public void setStyleScoreOverallStyle(String styleScoreOverallStyle)
    {
        this.styleScoreOverallStyle = styleScoreOverallStyle;
    }

    // STYLE SCORE HARD ACCEL
    public Integer getStyleScoreAccel()
    {
        return styleScoreAccel;
    }

    public void setStyleScoreAccel(Integer styleScoreAccel)
    {
        this.styleScoreAccel = styleScoreAccel;
        setStyleScoreAccelStyle(ScoreBox.GetStyleFromScore(styleScoreAccel, ScoreBoxSizes.MEDIUM));
    }

    public String getStyleScoreAccelStyle()
    {
        return styleScoreAccelStyle;
    }

    public void setStyleScoreAccelStyle(String styleScoreAccelStyle)
    {
        this.styleScoreAccelStyle = styleScoreAccelStyle;
    }

    // STYLE SCORE HARD BRAKE
    public Integer getStyleScoreBrake()
    {
        return styleScoreBrake;
    }

    public void setStyleScoreBrake(Integer styleScoreBrake)
    {
        setStyleScoreBrakeStyle(ScoreBox.GetStyleFromScore(styleScoreBrake, ScoreBoxSizes.MEDIUM));
        this.styleScoreBrake = styleScoreBrake;
    }

    public String getStyleScoreBrakeStyle()
    {
        return styleScoreBrakeStyle;
    }

    public void setStyleScoreBrakeStyle(String styleScoreBrakeStyle)
    {
        this.styleScoreBrakeStyle = styleScoreBrakeStyle;
    }

    // STYLE SCORE HARD BUMP
    public Integer getStyleScoreBump()
    {
        return styleScoreBump;
    }

    public void setStyleScoreBump(Integer styleScoreBump)
    {
        setStyleScoreBumpStyle(ScoreBox.GetStyleFromScore(styleScoreBump, ScoreBoxSizes.MEDIUM));
        this.styleScoreBump = styleScoreBump;
    }

    public String getStyleScoreBumpStyle()
    {
        return styleScoreBumpStyle;
    }

    public void setStyleScoreBumpStyle(String styleScoreBumpStyle)
    {
        this.styleScoreBumpStyle = styleScoreBumpStyle;
    }

    // STYLE SCORE HARD TURN
    public Integer getStyleScoreTurn()
    {
        return styleScoreTurn;
    }

    public void setStyleScoreTurn(Integer styleScoreTurn)
    {
        setStyleScoreTurnStyle(ScoreBox.GetStyleFromScore(styleScoreTurn, ScoreBoxSizes.MEDIUM));
        this.styleScoreTurn = styleScoreTurn;
    }

    public String getStyleScoreTurnStyle()
    {
        return styleScoreTurnStyle;
    }

    public void setStyleScoreTurnStyle(String styleScoreTurnStyle)
    {
        this.styleScoreTurnStyle = styleScoreTurnStyle;
    }

    // OVERALL HISTORY PROPERTY
    public String getStyleScoreHistoryOverall()
    {
        setStyleScoreHistoryOverall(createLineDef(ScoreType.SCORE_DRIVING_STYLE));
        return styleScoreHistoryOverall;
    }

    public void setStyleScoreHistoryOverall(String styleScoreHistoryOverall)
    {
        this.styleScoreHistoryOverall = styleScoreHistoryOverall;
    }

    // SCORE HISTORY HARD ACCEL
    public String getStyleScoreHistoryAccel()
    {
        setStyleScoreHistoryAccel(createLineDef(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL));
        return styleScoreHistoryAccel;
    }

    public void setStyleScoreHistoryAccel(String styleScoreHistoryAccel)
    {
        this.styleScoreHistoryAccel = styleScoreHistoryAccel;
    }

    // STYLE HISTORY HARD BRAKE
    public String getStyleScoreHistoryBrake()
    {
        setStyleScoreHistoryBrake(createLineDef(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE));
        return styleScoreHistoryBrake;
    }

    public void setStyleScoreHistoryBrake(String styleScoreHistoryBrake)
    {
        this.styleScoreHistoryBrake = styleScoreHistoryBrake;
    }

    // STYLE HISTORY HARD BUMP
    public String getStyleScoreHistoryBump()
    {
        setStyleScoreHistoryBump(createLineDef(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP));
        return styleScoreHistoryBump;
    }

    public void setStyleScoreHistoryBump(String styleScoreHistoryBump)
    {
        this.styleScoreHistoryBump = styleScoreHistoryBump;
    }

    // STYLE HISTORY HARD TURN
    public String getStyleScoreHistoryTurn()
    {
        setStyleScoreHistoryTurn(createLineDef(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN));
        return styleScoreHistoryTurn;
    }

    public void setStyleScoreHistoryTurn(String styleScoreHistoryTurn)
    {
        this.styleScoreHistoryTurn = styleScoreHistoryTurn;
    }

    // DAO PROPERTY
    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO)
    {
        this.scoreDAO = scoreDAO;
    }

    public EventDAO getEventDAO()
    {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO)
    {
        this.eventDAO = eventDAO;
    }

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

    public AddressLookup getAddressLookup()
    {
        return addressLookup;
    }

    public void setAddressLookup(AddressLookup addressLookup)
    {
        this.addressLookup = addressLookup;
    }

    public TableStatsBean getTableStatsBean()
    {
        getViolations();
        return tableStatsBean;
    }

    public void setTableStatsBean(TableStatsBean tableStatsBean)
    {
        this.tableStatsBean = tableStatsBean;
    }

    public void setDurationBean(DurationBean durationBean)
    {
        this.durationBean = durationBean;
    }

    public void setDuration(Duration duration)
    {
        durationBean.setDuration(duration);
        init();
        styleEvents.clear();
        getViolations();
    }
    public Duration getDuration()
    {
        return durationBean.getDuration();
    }

    public List<EventReportItem> getStyleEvents()
    {
        getViolations();
        return styleEvents;
    }

    public void setStyleEvents(List<EventReportItem> styleEvents)
    {
        this.styleEvents = styleEvents;
    }

    public void ClearEventAction()
    {
        Integer temp = eventDAO.forgive(navigation.getDriver().getDriverID(), clearItem.getEvent().getNoteID());
        // logger.debug("Clearing event " + clearItem.getNoteID() + " result: " + temp.toString());

        styleEvents.clear();
        getViolations();
    }

    public EventReportItem getClearItem()
    {
        return clearItem;
    }

    public void setClearItem(EventReportItem clearItem)
    {
        this.clearItem = clearItem;
    }

    public List<CategorySeriesData> createJasperDef(List<ScoreType> scoreTypes)
    {
        List<CategorySeriesData> returnList = new ArrayList<CategorySeriesData>();

        for (ScoreType st : scoreTypes)
        {
            List<ScoreableEntity> scoreList = scoreDAO.getDriverScoreHistory(navigation.getDriver().getDriverID(), durationBean.getDuration(), st, GraphicUtil
                    .getDurationSize(durationBean.getDuration()));

            List<String> monthList = GraphicUtil.createMonthList(durationBean.getDuration());
            int count = 0;
            for (ScoreableEntity se : scoreList)
            {
                Double score = null;
                if (se.getScore() != null)
                    score = se.getScore() / 10.0D;

                returnList.add(new CategorySeriesData(MessageUtil.getMessageString(st.toString()), monthList.get(count).toString(), score, monthList.get(count).toString()));

                count++;
            }
        }
        return returnList;
    }

    public ReportCriteria buildReport()
    {
        // Page 1
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.DRIVER_STYLE, getGroupHierarchy().getTopGroup().getName());

        reportCriteria.setDuration(durationBean.getDuration());
        reportCriteria.addParameter("REPORT_NAME", "Driver Performance: Style");
        reportCriteria.addParameter("ENTITY_NAME", this.getNavigation().getDriver().getPerson().getFullName());
        reportCriteria.addParameter("RECORD_COUNT", this.getStyleEvents().size());
        reportCriteria.addParameter("OVERALL_SCORE", this.getStyleScoreOverall() / 10.0D);
        reportCriteria.addParameter("SPEED_MEASUREMENT", MessageUtil.getMessageString("measurement_style"));
        reportCriteria.addParameter("SCORE_HARDBRAKE", this.getStyleScoreAccel() / 10.0D);
        reportCriteria.addParameter("SCORE_HARDACCEL", this.getStyleScoreAccel() / 10.0D);
        reportCriteria.addParameter("SCORE_HARDTURN", this.getStyleScoreTurn() / 10.0D);
        reportCriteria.addParameter("SCORE_HARDBUMP", this.getStyleScoreBump() / 10.0D);

        List<ScoreType> scoreTypes = new ArrayList<ScoreType>();
        scoreTypes.add(ScoreType.SCORE_DRIVING_STYLE);
        scoreTypes.add(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL);
        scoreTypes.add(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE);
        scoreTypes.add(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP);
        scoreTypes.add(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN);
        reportCriteria.addChartDataSet(this.createJasperDef(scoreTypes));
        reportCriteria.setMainDataset(this.styleEvents);

        return reportCriteria;
    }

    public void setReportRenderer(ReportRenderer reportRenderer)
    {
        this.reportRenderer = reportRenderer;
    }

    public ReportRenderer getReportRenderer()
    {
        return reportRenderer;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public void exportReportToPdf()
    {
        getReportRenderer().exportSingleReportToPDF(buildReport(), getFacesContext());
    }

    public void emailReport()
    {
        getReportRenderer().exportReportToEmail(buildReport(), getEmailAddress());
    }

    public void exportReportToExcel()
    {
        getReportRenderer().exportReportToExcel(buildReport(), getFacesContext());
    }
}
