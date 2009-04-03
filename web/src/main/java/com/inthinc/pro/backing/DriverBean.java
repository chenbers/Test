package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.TripDisplay;
import com.inthinc.pro.charts.FusionColumnChart;
import com.inthinc.pro.charts.FusionMultiAreaChart;
import com.inthinc.pro.charts.FusionMultiLineChart;
import com.inthinc.pro.charts.Line;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.MpgDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.map.MapLookup;
import com.inthinc.pro.reports.model.CategorySeriesData;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.util.MessageUtil;

public class DriverBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(DriverBean.class);

    private DriverDAO driverDAO;
    private ScoreDAO scoreDAO;
    private MpgDAO mpgDAO;
    private EventDAO eventDAO;
    private NavigationBean navigation;
    private DurationBean durationBean;
    private DurationBean coachDurationBean;
    private DurationBean mpgDurationBean;
    private DurationBean speedDurationBean;
    private DurationBean styleDurationBean;
    private DurationBean seatBeltDurationBean;
    private AddressLookup addressLookup;

    private TripDisplay lastTrip;
    private List<Event> violationEvents = new ArrayList<Event>();
    private Integer overallScore;
    private String overallScoreHistory;
    private String overallScoreHistory2;  //FOR TESTING..  used in driverTest.xhtml
    private String overallScoreStyle;
    private String mpgHistory;
    private String coachingHistory;
    private Boolean hasLastTrip;
    private ReportRenderer reportRenderer;
    private String emailAddress;

    private Integer initAverageScore(ScoreType scoreType, Duration duration)
    {
        ScoreableEntity se = scoreDAO.getDriverAverageScoreByType(navigation.getDriver().getDriverID(), duration, scoreType);
        
        if(se != null && se.getScore() != null)
            return se.getScore();
        else
            return -1;
    }

    // INIT VIOLATIONS
    public void initViolations(Date start, Date end)
    {
        if (violationEvents.isEmpty())
        {
            List<Integer> types = new ArrayList<Integer>();
            types.add(EventMapper.TIWIPRO_EVENT_SPEEDING_EX3);
            types.add(EventMapper.TIWIPRO_EVENT_SEATBELT);
            types.add(EventMapper.TIWIPRO_EVENT_NOTEEVENT);
            types.add(EventMapper.TIWIPRO_EVENT_IDLE);
    
            violationEvents = eventDAO.getEventsForDriver(navigation.getDriver().getDriverID(), start, end, types);
    
            // Lookup Addresses for events
            
            for (Event event : violationEvents)
            {
                event.setAddressStr(addressLookup.getAddress(event.getLatitude(), event.getLongitude()));
            }
        }
    }

    // OVERALL SCORE properties
    public Integer getOverallScore()
    {
        if (overallScore == null)
        {
            setOverallScore(initAverageScore(ScoreType.SCORE_OVERALL, durationBean.getDuration()));
        }
        return overallScore;
    }

    public void setOverallScore(Integer overallScore)
    {
        this.overallScore = overallScore;
        setOverallScoreStyle(ScoreBox.GetStyleFromScore(overallScore, ScoreBoxSizes.MEDIUM));
    }

    public String getOverallScoreHistory()
    {

        setOverallScoreHistory(createSingleLineDef(ScoreType.SCORE_OVERALL, durationBean.getDuration()));
        return overallScoreHistory;
    }

    public void setOverallScoreHistory(String overallScoreHistory)
    {
        this.overallScoreHistory = overallScoreHistory;
    }
    
    //TESING PROPERTIES
    public String getOverallScoreHistory2()
    {

        setOverallScoreHistory2(createMultiLineDef(ScoreType.SCORE_OVERALL, durationBean.getDuration()));
        return overallScoreHistory2;
    }

    public void setOverallScoreHistory2(String overallScoreHistory2)
    {
        this.overallScoreHistory2 = overallScoreHistory2;
    }

    public String getOverallScoreStyle()
    {
        if (overallScoreStyle == null)
        {
            setOverallScore(initAverageScore(ScoreType.SCORE_OVERALL, durationBean.getDuration()));
        }
        return overallScoreStyle;
    }

    public void setOverallScoreStyle(String overallScoreStyle)
    {
        this.overallScoreStyle = overallScoreStyle;
    }

    // COACHING properties
    public String getCoachingHistory()
    {
        setCoachingHistory(createColumnDef(ScoreType.SCORE_COACHING_EVENTS, coachDurationBean.getDuration()));
        return coachingHistory;
    }

    public void setCoachingHistory(String coachingHistory)
    {
        this.coachingHistory = coachingHistory;
    }

    // LAST TRIP
    public TripDisplay getLastTrip()
    {
        if (lastTrip == null)
        {
            Trip tempTrip = driverDAO.getLastTrip(navigation.getDriver().getDriverID());

            if (tempTrip != null && tempTrip.getRoute().size() > 0)
            {
                hasLastTrip = true;
                TripDisplay trip = new TripDisplay(tempTrip, navigation.getDriver().getPerson().getTimeZone(), addressLookup.getMapServerURLString());
                setLastTrip(trip);
                initViolations(trip.getTrip().getStartTime(), trip.getTrip().getEndTime());
            }
            else
            {
                hasLastTrip = false;
            }
        }
        return lastTrip;
    }

    public void setLastTrip(TripDisplay lastTrip)
    {
        this.lastTrip = lastTrip;
    }

    // VIOLATIONS PROPERTIES
    public List<Event> getViolationEvents()
    {
        return violationEvents;
    }

    public void setViolationEvents(List<Event> violationEvents)
    {
        this.violationEvents = violationEvents;
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

    public MpgDAO getMpgDAO()
    {
        return mpgDAO;
    }

    public void setMpgDAO(MpgDAO mpgDAO)
    {
        this.mpgDAO = mpgDAO;
    }

    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    public EventDAO getEventDAO()
    {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO)
    {
        this.eventDAO = eventDAO;
    }

    public AddressLookup getAddressLookup()
    {
        return addressLookup;
    }

    public void setAddressLookup(AddressLookup addressLookup)
    {
        this.addressLookup = addressLookup;
    }

    // MPG PROPERTIES
    public String getMpgHistory()
    {
        if (mpgHistory == null)
            mpgHistory = this.createMpgLineDef();

        return mpgHistory;
    }

    public void setMpgHistory(String mpgHistory)
    {
        this.mpgHistory = mpgHistory;
    }

    private String createMpgLineDef()
    {
        List<MpgEntity> mpgEntities = mpgDAO.getDriverEntities(navigation.getDriver().getDriverID(), mpgDurationBean.getDuration(), null);
        List<String> catLabelList = GraphicUtil.createMonthList(mpgDurationBean.getDuration());

        StringBuffer sb = new StringBuffer();
        FusionMultiLineChart multiLineChart = new FusionMultiLineChart();
        sb.append(multiLineChart.getControlParameters());

        Integer lightValues[] = new Integer[mpgEntities.size()];
        Integer medValues[] = new Integer[mpgEntities.size()];
        Integer heavyValues[] = new Integer[mpgEntities.size()];
        int cnt = 0;
        sb.append(multiLineChart.getCategoriesStart());
        for (MpgEntity entity : mpgEntities)
        {
            lightValues[cnt] = entity.getLightValue() == null ? 0 : entity.getLightValue();
            medValues[cnt] = entity.getMediumValue() == null ? 0 : entity.getMediumValue();
            heavyValues[cnt] = entity.getHeavyValue() == null ? 0 : entity.getHeavyValue();
            sb.append(multiLineChart.getCategoryLabel(catLabelList.get(cnt)));
            cnt++;

        }
        sb.append(multiLineChart.getCategoriesEnd());

        sb.append(multiLineChart.getChartDataSet("Light", "B1D1DC", "B1D1DC", lightValues, catLabelList));
        sb.append(multiLineChart.getChartDataSet("Medium", "C8A1D1", "C8A1D1", medValues, catLabelList));
        sb.append(multiLineChart.getChartDataSet("Heavy", "A8C634", "A8C634", heavyValues, catLabelList));

        sb.append(multiLineChart.getClose());

        return sb.toString();
    }

    public String createMultiLineDef(ScoreType scoreType, Duration duration)
    {
        StringBuffer sb = new StringBuffer();
        FusionMultiAreaChart multiAreaChart = new FusionMultiAreaChart();

        // Start XML Data
        sb.append(multiAreaChart.getControlParameters());
              
        List<ScoreableEntity> cumulativeList = scoreDAO.getDriverTrendCumulativeTest
                                            (navigation.getDriver().getDriverID(), duration, scoreType);
        
        List<ScoreableEntity> dailyList = scoreDAO.getDriverTrendDaily
                                            (navigation.getDriver().getDriverID(), duration, scoreType);
        
        List<String> catLabelList = GraphicUtil.createMonthList(duration);

        Double cumulativeValues[]   = new Double[cumulativeList.size()];
        Double dailyValues[]        = new Double[dailyList.size()];
        Double odometerValues[]     = new Double[dailyList.size()];
        
        //Start Categories List
        sb.append(multiAreaChart.getCategoriesStart());
        
        int cnt = 0;
        for (ScoreableEntity entity : dailyList)
        {
            Double score;
            // Set Score to NULL on non driving days 
            if(entity.getIdentifierNum() != null && entity.getIdentifierNum() == 0)
                score = null;
            else
                score = entity.getScore() == null ? null : entity.getScore() / 10D;
            
            //Add Score to array.
            dailyValues[cnt] = score;
            
            //logger.debug("mileage " + entity.getIdentifierNum());
            if(entity.getIdentifierNum() != null)
            {
                odometerValues[cnt] = entity.getIdentifierNum() / 100D;  // Odometer values in this entity are miles driven for the day.
            }
            sb.append(multiAreaChart.getCategoryLabel(catLabelList.get(cnt)));     
            cnt++;
        }
        sb.append(multiAreaChart.getCategoriesEnd());
        
        
        cnt = 0;
        for (ScoreableEntity entity : cumulativeList)
        {
            Double score;

            // Set Score to NULL on non driving days. 
            if(odometerValues[cnt] != null && odometerValues[cnt] == 0)
                score = null;
            else
                score = entity.getScore() == null ? null : entity.getScore() / 10D;
            
            cumulativeValues[cnt] = score;
            //logger.debug("mileage " + entity.getIdentifierNum());
                  
            cnt++;
        }
  
        sb.append(multiAreaChart.getChartLineDataSet("Daily Score", "#407EFF", dailyValues, catLabelList));
        sb.append(multiAreaChart.getChartAreaDataSet("Cumulative Score", "#B0CB48", cumulativeValues, catLabelList));
        sb.append(multiAreaChart.getChartBarDataSet("Mileage", "#C0C0C0", odometerValues, catLabelList));
        sb.append(multiAreaChart.getClose());

        return sb.toString();
    }
    
    public String createSingleLineDef(ScoreType scoreType, Duration duration)
    {
        StringBuffer sb = new StringBuffer();
        Line line = new Line();

        // Start XML Data
        sb.append(line.getControlParameters());
              
        List<ScoreableEntity> scoreList = scoreDAO
                .getDriverTrendCumulative(navigation.getDriver().getDriverID(), duration, scoreType);

        // Get "x" values
        List<String> monthList = GraphicUtil.createMonthList(duration);

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
    
    public String createColumnDef(ScoreType scoreType, Duration duration)
    {
        StringBuffer sb = new StringBuffer();
        FusionColumnChart column = new FusionColumnChart();

        // Start XML Data
        sb.append(column.getControlParameters());
              
        List<ScoreableEntity> scoreList = scoreDAO
                .getDriverTrendDaily(navigation.getDriver().getDriverID(), duration, scoreType);

        // Get "x" values
        List<String> monthList = GraphicUtil.createMonthList(duration);

        int cnt = 0;
        for (ScoreableEntity e : scoreList)
        {
            if (e.getScore() != null)
            {
                sb.append(column.getChartItem(new Object[] { (e.getScore() / 10), monthList.get(cnt) }));
            }
            else
            {
                sb.append(column.getChartItem(new Object[] { null, monthList.get(cnt) }));
            }
            cnt++;
        }

        // End XML Data
        sb.append(column.getClose());

        return sb.toString();
    }

    public List<CategorySeriesData> createSingleJasperDef(ScoreType scoreType, Duration duration)
    {
        List<ScoreableEntity> scoreList = scoreDAO
                .getDriverTrendCumulative(navigation.getDriver().getDriverID(), duration, scoreType);

        List<CategorySeriesData> chartDataList = new ArrayList<CategorySeriesData>();
        List<String> monthList = GraphicUtil.createMonthList(duration, "M/dd");

        int count = 0;
        for (ScoreableEntity se : scoreList)
        {
            Double score = null;
            if(se.getScore() != null)
                score = se.getScore() / 10.0D;
            
            chartDataList.add(new CategorySeriesData(MessageUtil.getMessageString(scoreType.toString()), 
                                                    monthList.get(count).toString(),
                                                    score, 
                                                    monthList.get(count).toString()));
            count++;
        }
        return chartDataList;
    }

    public List<CategorySeriesData> createMpgJasperDef()
    {
        List<CategorySeriesData> chartDataList = new ArrayList<CategorySeriesData>();
        List<MpgEntity> mpgEntities = mpgDAO.getDriverEntities(navigation.getDriver().getDriverID(), mpgDurationBean.getDuration(), null);

        List<String> monthList = GraphicUtil.createMonthList(mpgDurationBean.getDuration(), "M/dd");

        int count = 0;
        for (MpgEntity me : mpgEntities)
        {
            chartDataList.add(new CategorySeriesData("Light", monthList.get(count).toString(), me.getLightValue(), monthList.get(count).toString()));
            chartDataList.add(new CategorySeriesData("Medium", monthList.get(count).toString(), me.getMediumValue(), monthList.get(count).toString()));
            chartDataList.add(new CategorySeriesData("Heavy", monthList.get(count).toString(), me.getHeavyValue(), monthList.get(count).toString()));

            count++;
        }
        return chartDataList;
    }

    public List<ReportCriteria> buildReportCriteria()
    {
        List<ReportCriteria> tempCriteria = new ArrayList<ReportCriteria>();

        // Page 1
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.DRIVER_SUMMARY_P1, getGroupHierarchy().getTopGroup().getName());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setDuration(durationBean.getDuration());
        reportCriteria.addParameter("REPORT_NAME", "Driver Performance: Summary");
        reportCriteria.addParameter("OVERALL_SCORE", this.getOverallScore() / 10.0D);
        reportCriteria.addParameter("DRIVER_NAME", this.getNavigation().getDriver().getPerson().getFullName());
        reportCriteria.addParameter("SPEED_SCORE", initAverageScore(ScoreType.SCORE_SPEEDING, speedDurationBean.getDuration()) / 10.0D);
        reportCriteria.addParameter("STYLE_SCORE", initAverageScore(ScoreType.SCORE_DRIVING_STYLE, styleDurationBean.getDuration()) / 10.0D);
        reportCriteria.addParameter("SEATBELT_SCORE", initAverageScore(ScoreType.SCORE_SEATBELT, seatBeltDurationBean.getDuration()) / 10.0D);
        reportCriteria.addParameter("SPEED_DUR", speedDurationBean.getDuration().toString());
        reportCriteria.addParameter("STYLE_DUR", styleDurationBean.getDuration().toString());
        reportCriteria.addParameter("SEATBELT_DUR", seatBeltDurationBean.getDuration().toString());
        reportCriteria.addChartDataSet(createSingleJasperDef(ScoreType.SCORE_OVERALL, durationBean.getDuration()));
        reportCriteria.addChartDataSet(createSingleJasperDef(ScoreType.SCORE_SPEEDING, speedDurationBean.getDuration()));
        reportCriteria.addChartDataSet(createSingleJasperDef(ScoreType.SCORE_DRIVING_STYLE, styleDurationBean.getDuration()));
        reportCriteria.addChartDataSet(createSingleJasperDef(ScoreType.SCORE_SEATBELT, seatBeltDurationBean.getDuration()));
        tempCriteria.add(reportCriteria);

        // Page 2
        reportCriteria = new ReportCriteria(ReportType.DRIVER_SUMMARY_P2, getGroupHierarchy().getTopGroup().getName());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setDuration(durationBean.getDuration());
        reportCriteria.addParameter("REPORT_NAME", "Driver Performance: Summary");
        reportCriteria.addParameter("OVERALL_SCORE", this.getOverallScore() / 10.0D);
        reportCriteria.addParameter("DRIVER_NAME", this.getNavigation().getDriver().getPerson().getFullName());

        if (lastTrip != null)
        {
            reportCriteria.addParameter("START_TIME", lastTrip.getStartDateString());
            reportCriteria.addParameter("START_LOCATION", lastTrip.getStartAddress());
            reportCriteria.addParameter("END_TIME", lastTrip.getEndDateString());
            reportCriteria.addParameter("END_LOCATION", lastTrip.getEndAddress());
            
            String imageUrl = MapLookup.getMap(lastTrip.getRouteLastStep().getLat(), lastTrip.getRouteLastStep().getLng(), 250, 200); 
            reportCriteria.addParameter("MAP_URL", imageUrl);
        }
        reportCriteria.addChartDataSet(createMpgJasperDef());
        reportCriteria.addChartDataSet(createSingleJasperDef(ScoreType.SCORE_COACHING_EVENTS, coachDurationBean.getDuration()));
        reportCriteria.addParameter("COACH_DUR", coachDurationBean.getDuration().toString());
        reportCriteria.addParameter("MPG_DUR", mpgDurationBean.getDuration().toString());
        tempCriteria.add(reportCriteria);

        return tempCriteria;
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

    public DurationBean getSpeedDurationBean()
    {
        return speedDurationBean;
    }

    public void setSpeedDurationBean(DurationBean speedDurationBean)
    {
        this.speedDurationBean = speedDurationBean;
    }

    public DurationBean getStyleDurationBean()
    {
        return styleDurationBean;
    }

    public void setStyleDurationBean(DurationBean styleDurationBean)
    {
        this.styleDurationBean = styleDurationBean;
    }

    public DurationBean getSeatBeltDurationBean()
    {
        return seatBeltDurationBean;
    }

    public void setSeatBeltDurationBean(DurationBean seatBeltDurationBean)
    {
        this.seatBeltDurationBean = seatBeltDurationBean;
    }

    public DurationBean getCoachDurationBean()
    {
        return coachDurationBean;
    }

    public void setCoachDurationBean(DurationBean coachDurationBean)
    {
        this.coachDurationBean = coachDurationBean;
    }

    public DurationBean getMpgDurationBean()
    {
        return mpgDurationBean;
    }

    public void setMpgDurationBean(DurationBean mpgDurationBean)
    {
        this.mpgDurationBean = mpgDurationBean;
    }

    public Boolean getHasLastTrip()
    {
        if(this.lastTrip == null)
            this.getLastTrip();
        
        return hasLastTrip;
    }

    public void setHasLastTrip(Boolean hasLastTrip)
    {
        this.hasLastTrip = hasLastTrip;
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
        getReportRenderer().exportReportToPDF(buildReportCriteria(), getFacesContext());
    }

    public void emailReport()
    {
        getReportRenderer().exportReportToEmail(buildReportCriteria(), getEmailAddress());
    }
}
