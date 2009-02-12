package com.inthinc.pro.backing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.inthinc.pro.model.SpeedingEvent;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.model.CategorySeriesData;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.util.MessageUtil;

public class VehicleSpeedBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(VehicleSpeedBean.class);
    private NavigationBean navigation;
    private DurationBean durationBean;
    private ScoreDAO scoreDAO;
    private EventDAO eventDAO;

    private Integer speedScoreOverall;
    private String speedScoreOverallStyle;
    private Integer speedScoreTwentyOne;
    private String speedScoreTwentyOneStyle;
    private Integer speedScoreThirtyOne;
    private String speedScoreThirtyOneStyle;
    private Integer speedScoreFourtyOne;
    private String speedScoreFourtyOneStyle;
    private Integer speedScoreFiftyFive;
    private String speedScoreFiftyFiveStyle;
    private Integer speedScoreSixtyFive;
    private String speedScoreSixtyFiveStyle;

    private String speedScoreHistoryOverall;
    private String speedScoreHistoryTwentyOne;
    private String speedScoreHistoryThirtyOne;
    private String speedScoreHistoryFourtyOne;
    private String speedScoreHistoryFiftyFive;
    private String speedScoreHistorySixtyFive;

    private static final Integer NO_SCORE = -1;
    private List<EventReportItem> speedingEvents = new ArrayList<EventReportItem>();
    private EventReportItem clearItem;
    private ReportRenderer reportRenderer;
    private String emailAddress;

    private void init()
    {
        // Set Events table rows per page in BaseDurationBean
        durationBean.setTableRowCount(10);

        if (navigation.getVehicle() == null)
        {
            return;
        }
        int vehicleID = navigation.getVehicle().getVehicleID();

        Map<ScoreType, ScoreableEntity> scoreMap = scoreDAO.getVehicleScoreBreakdownByType(vehicleID, durationBean.getDuration(), ScoreType.SCORE_SPEEDING);

        ScoreableEntity se = scoreMap.get(ScoreType.SCORE_SPEEDING);
        setSpeedScoreOverall(se == null ? NO_SCORE : se.getScore());

        se = scoreMap.get(ScoreType.SCORE_SPEEDING_21_30);
        setSpeedScoreTwentyOne(se == null ? NO_SCORE : se.getScore());

        se = scoreMap.get(ScoreType.SCORE_SPEEDING_31_40);
        setSpeedScoreThirtyOne(se == null ? NO_SCORE : se.getScore());

        se = scoreMap.get(ScoreType.SCORE_SPEEDING_55_64);
        setSpeedScoreFourtyOne(se == null ? NO_SCORE : se.getScore());

        se = scoreMap.get(ScoreType.SCORE_SPEEDING_55_64);
        setSpeedScoreFiftyFive(se == null ? NO_SCORE : se.getScore());

        se = scoreMap.get(ScoreType.SCORE_SPEEDING_65_80);
        setSpeedScoreSixtyFive(se == null ? NO_SCORE : se.getScore());

        getViolations();
    }

    public void getViolations()
    {
        if (speedingEvents.size() < 1)
        {
            List<Event> tempEvents = new ArrayList<Event>();
            List<Integer> types = new ArrayList<Integer>();
            types.add(EventMapper.TIWIPRO_EVENT_SPEEDING_EX3);

            tempEvents = eventDAO.getEventsForVehicle(navigation.getVehicle().getVehicleID(), durationBean.getStartDate(), durationBean.getEndDate(), types);

            AddressLookup lookup = new AddressLookup();

            for (Event event : tempEvents)
            {
                // TODO: Lookup each driver and get timezone.

                event.setAddressStr(lookup.getAddress(event.getLatitude(), event.getLongitude()));
                speedingEvents.add(new EventReportItem(event, null));
            }

            durationBean.setTableSize(speedingEvents.size());
        }
    }

    public String createLineDef(ScoreType scoreType)
    {
        StringBuffer sb = new StringBuffer();
        Line line = new Line();

        // Start XML Data
        sb.append(line.getControlParameters());

        List<ScoreableEntity> scoreList = scoreDAO.getVehicleScoreHistory(navigation.getVehicle().getVehicleID(), durationBean.getDuration(), scoreType, GraphicUtil
                .getDurationSize(durationBean.getDuration()));
        // 10);
        DateFormat dateFormatter = new SimpleDateFormat(durationBean.getDuration().getDatePattern());

        // Get "x" values
        List<String> monthList = GraphicUtil.createMonthList(durationBean.getDuration());

        int cnt = 0;
        for (ScoreableEntity e : scoreList)
        {
            // sb.append(line.getChartItem(new Object[] { (double) (e.getScore() / 10.0d), monthList.get(cnt) }));
            if (e.getScore() != null)
            {
                sb.append(line.getChartItem(new Object[] { (double) (e.getScore() / 10.0d), monthList.get(cnt) }));
            }
            else
            {
                sb.append(line.getChartItem(new Object[] { null, monthList.get(cnt) }));
            }
            // sb.append(line.getChartItem(new Object[] { (double) (e.getScore() / 10.0d),
            // dateFormatter.format(e.getCreated()) }));
            cnt++;
        }

        // End XML Data
        sb.append(line.getClose());

        return sb.toString();
    }

    // SPEED OVERALL SCORE PROPERTY
    public Integer getSpeedScoreOverall()
    {
        return speedScoreOverall;
    }

    public void setSpeedScoreOverall(Integer speedScoreOverall)
    {
        this.speedScoreOverall = speedScoreOverall;
        setSpeedScoreOverallStyle(ScoreBox.GetStyleFromScore(speedScoreOverall, ScoreBoxSizes.MEDIUM));
    }

    public String getSpeedScoreOverallStyle()
    {
        return speedScoreOverallStyle;
    }

    public void setSpeedScoreOverallStyle(String speedScoreOverallStyle)
    {
        this.speedScoreOverallStyle = speedScoreOverallStyle;
    }

    // SPEED SCORE 21-30 MPH
    public Integer getSpeedScoreTwentyOne()
    {
        return speedScoreTwentyOne;
    }

    public void setSpeedScoreTwentyOne(Integer speedScoreTwentyOne)
    {
        this.speedScoreTwentyOne = speedScoreTwentyOne;
        setSpeedScoreTwentyOneStyle(ScoreBox.GetStyleFromScore(speedScoreTwentyOne, ScoreBoxSizes.MEDIUM));
    }

    public String getSpeedScoreTwentyOneStyle()
    {
        return speedScoreTwentyOneStyle;
    }

    public void setSpeedScoreTwentyOneStyle(String speedScoreTwentyOneStyle)
    {
        this.speedScoreTwentyOneStyle = speedScoreTwentyOneStyle;
    }

    // SPEED SCORE 31-40 MPH
    public Integer getSpeedScoreThirtyOne()
    {
        return speedScoreThirtyOne;
    }

    public void setSpeedScoreThirtyOne(Integer speedScoreThirtyOne)
    {
        setSpeedScoreThirtyOneStyle(ScoreBox.GetStyleFromScore(speedScoreThirtyOne, ScoreBoxSizes.MEDIUM));
        this.speedScoreThirtyOne = speedScoreThirtyOne;
    }

    public String getSpeedScoreThirtyOneStyle()
    {
        return speedScoreThirtyOneStyle;
    }

    public void setSpeedScoreThirtyOneStyle(String speedScoreThirtyOneStyle)
    {
        this.speedScoreThirtyOneStyle = speedScoreThirtyOneStyle;
    }

    // SPEED SCORE 41-54 MPH
    public Integer getSpeedScoreFourtyOne()
    {
        return speedScoreFourtyOne;
    }

    public void setSpeedScoreFourtyOne(Integer speedScoreFourtyOne)
    {
        setSpeedScoreFourtyOneStyle(ScoreBox.GetStyleFromScore(speedScoreFourtyOne, ScoreBoxSizes.MEDIUM));
        this.speedScoreFourtyOne = speedScoreFourtyOne;
    }

    public String getSpeedScoreFourtyOneStyle()
    {
        return speedScoreFourtyOneStyle;
    }

    public void setSpeedScoreFourtyOneStyle(String speedScoreFourtyOneStyle)
    {
        this.speedScoreFourtyOneStyle = speedScoreFourtyOneStyle;
    }

    // SPEED SCORE 55-64 MPH
    public Integer getSpeedScoreFiftyFive()
    {
        return speedScoreFiftyFive;
    }

    public void setSpeedScoreFiftyFive(Integer speedScoreFiftyFive)
    {
        setSpeedScoreFiftyFiveStyle(ScoreBox.GetStyleFromScore(speedScoreFiftyFive, ScoreBoxSizes.MEDIUM));
        this.speedScoreFiftyFive = speedScoreFiftyFive;
    }

    public String getSpeedScoreFiftyFiveStyle()
    {
        return speedScoreFiftyFiveStyle;
    }

    public void setSpeedScoreFiftyFiveStyle(String speedScoreFiftyFiveStyle)
    {
        this.speedScoreFiftyFiveStyle = speedScoreFiftyFiveStyle;
    }

    // SPEED SCORE 65+
    public Integer getSpeedScoreSixtyFive()
    {
        return speedScoreSixtyFive;
    }

    public void setSpeedScoreSixtyFive(Integer speedScoreSixtyFive)
    {
        setSpeedScoreSixtyFiveStyle(ScoreBox.GetStyleFromScore(speedScoreSixtyFive, ScoreBoxSizes.MEDIUM));
        this.speedScoreSixtyFive = speedScoreSixtyFive;
    }

    public String getSpeedScoreSixtyFiveStyle()
    {
        return speedScoreSixtyFiveStyle;
    }

    public void setSpeedScoreSixtyFiveStyle(String speedScoreSixtyFiveStyle)
    {
        this.speedScoreSixtyFiveStyle = speedScoreSixtyFiveStyle;
    }

    // OVERALL HISTORY PROPERTY
    public String getSpeedScoreHistoryOverall()
    {
        setSpeedScoreHistoryOverall(createLineDef(ScoreType.SCORE_SPEEDING));
        return speedScoreHistoryOverall;
    }

    public void setSpeedScoreHistoryOverall(String speedScoreHistoryOverall)
    {
        this.speedScoreHistoryOverall = speedScoreHistoryOverall;
    }

    // SCORE HISTORY 21-30 MPH
    public String getSpeedScoreHistoryTwentyOne()
    {
        setSpeedScoreHistoryTwentyOne(createLineDef(ScoreType.SCORE_SPEEDING_21_30));
        return speedScoreHistoryTwentyOne;
    }

    public void setSpeedScoreHistoryTwentyOne(String speedScoreHistoryTwentyOne)
    {
        this.speedScoreHistoryTwentyOne = speedScoreHistoryTwentyOne;
    }

    // SPEED HISTORY 31-40 MPH
    public String getSpeedScoreHistoryThirtyOne()
    {
        setSpeedScoreHistoryThirtyOne(createLineDef(ScoreType.SCORE_SPEEDING_31_40));
        return speedScoreHistoryThirtyOne;
    }

    public void setSpeedScoreHistoryThirtyOne(String speedScoreHistoryThirtyOne)
    {
        this.speedScoreHistoryThirtyOne = speedScoreHistoryThirtyOne;
    }

    // SPEED HISTORY 41-54 MPH
    public String getSpeedScoreHistoryFourtyOne()
    {
        setSpeedScoreHistoryFourtyOne(createLineDef(ScoreType.SCORE_SPEEDING_41_54));
        return speedScoreHistoryFourtyOne;
    }

    public void setSpeedScoreHistoryFourtyOne(String speedScoreHistoryFourtyOne)
    {
        this.speedScoreHistoryFourtyOne = speedScoreHistoryFourtyOne;
    }

    // SPEED HISTORY 55-64 MPH
    public String getSpeedScoreHistoryFiftyFive()
    {
        setSpeedScoreHistoryFiftyFive(createLineDef(ScoreType.SCORE_SPEEDING_55_64));
        return speedScoreHistoryFiftyFive;
    }

    public void setSpeedScoreHistoryFiftyFive(String speedScoreHistoryFiftyFive)
    {
        this.speedScoreHistoryFiftyFive = speedScoreHistoryFiftyFive;
    }

    // SPEED HISTORY 65+
    public String getSpeedScoreHistorySixtyFive()
    {
        setSpeedScoreHistorySixtyFive(createLineDef(ScoreType.SCORE_SPEEDING_65_80));
        return speedScoreHistorySixtyFive;
    }

    public void setSpeedScoreHistorySixtyFive(String speedScoreHistorySixtyFive)
    {
        this.speedScoreHistorySixtyFive = speedScoreHistorySixtyFive;
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

    
    public void setDuration(Duration duration)
    {
        durationBean.setDuration(duration);
        init();
    }

    // SPEEDING EVENTS LIST
    public List<EventReportItem> getSpeedingEvents()
    {
        getViolations();
        return speedingEvents;
    }

    public void setSpeedingEvents(List<EventReportItem> speedingEvents)
    {
        this.speedingEvents = speedingEvents;
    }

    public void ClearEventAction()
    {
        Integer temp = eventDAO.forgive(navigation.getVehicle().getVehicleID(), clearItem.getEvent().getNoteID());
        // logger.debug("Clearing event " + clearItem.getNoteID() + " result: " + temp.toString());

        speedingEvents.clear();
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

    // NAVIGATION PROPERTY
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

    public List<CategorySeriesData> createJasperDef(List<ScoreType> scoreTypes)
    {
        List<CategorySeriesData> returnList = new ArrayList<CategorySeriesData>();

        for (ScoreType st : scoreTypes)
        {
            List<ScoreableEntity> scoreList = scoreDAO
                    .getVehicleScoreHistory(navigation.getVehicle().getVehicleID(), durationBean.getDuration(), st, GraphicUtil.getDurationSize(durationBean.getDuration()));

            List<String> monthList = GraphicUtil.createMonthList(durationBean.getDuration());
            int count = 0;
            for (ScoreableEntity se : scoreList)
            {
                returnList.add(new CategorySeriesData(MessageUtil.getMessageString(st.toString()), monthList.get(count).toString(), se.getScore() / 10.0D, monthList.get(count)
                        .toString()));

                count++;
            }
        }
        return returnList;
    }

    public ReportCriteria buildReport()
    {
        // Page 1
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.VEHICLE_SPEED, getGroupHierarchy().getTopGroup().getName());

        reportCriteria.setDuration(durationBean.getDuration());
        reportCriteria.addParameter("REPORT_NAME", "Vehicle Performance: Speed");
        reportCriteria.addParameter("ENTITY_NAME", this.getNavigation().getVehicle().getFullName());
        reportCriteria.addParameter("RECORD_COUNT", getSpeedingEvents().size());
        reportCriteria.addParameter("OVERALL_SCORE", this.getSpeedScoreOverall() / 10.0D);
        reportCriteria.addParameter("SPEED_MEASUREMENT", MessageUtil.getMessageString("measurement_speed"));
        reportCriteria.addParameter("SCORE_TWENTYONE", this.getSpeedScoreTwentyOne() / 10.0D);
        reportCriteria.addParameter("SCORE_THIRTYONE", this.getSpeedScoreThirtyOne() / 10.0D);
        reportCriteria.addParameter("SCORE_FOURTYONE", this.getSpeedScoreFourtyOne() / 10.0D);
        reportCriteria.addParameter("SCORE_FIFTYFIVE", this.getSpeedScoreFiftyFive() / 10.0D);
        reportCriteria.addParameter("SCORE_SIXTYFIVE", this.getSpeedScoreSixtyFive() / 10.0D);

        List<ScoreType> scoreTypes = new ArrayList<ScoreType>();
        scoreTypes.add(ScoreType.SCORE_SPEEDING);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_21_30);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_31_40);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_41_54);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_55_64);
        scoreTypes.add(ScoreType.SCORE_SPEEDING_65_80);
        reportCriteria.addChartDataSet(this.createJasperDef(scoreTypes));
        reportCriteria.setMainDataset(this.speedingEvents);

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
        // getReportRenderer().exportReportToEmail(buildReport(), getEmailAddress());
    }
}
