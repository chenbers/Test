package com.inthinc.pro.backing;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.BreakdownSelections;
import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.TripDisplay;
import com.inthinc.pro.charts.FusionMultiLineChart;
import com.inthinc.pro.charts.Line;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.VehicleDAO;
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

public class VehicleBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(VehicleBean.class);

    private VehicleDAO vehicleDAO;
    private DriverDAO driverDAO;
    private ScoreDAO scoreDAO;
    private MpgDAO mpgDAO;
    private EventDAO eventDAO;
    private NavigationBean navigation;
    private DurationBean durationBean;

    private TripDisplay lastTrip;
    private List<Event> violationEvents = new ArrayList<Event>();
    private Integer overallScore;
    private String overallScoreHistory;
    private String overallScoreStyle;
    private String mpgHistory;
    private String coachingHistory;
    private Boolean hasLastTrip;
    private ReportRenderer reportRenderer;
    private String emailAddress;

    private Integer initAverageScore(ScoreType scoreType)
    {
        ScoreableEntity se = scoreDAO.getVehicleAverageScoreByType(navigation.getVehicle().getVehicleID(), durationBean.getDuration(), scoreType);

        if (se == null)
            return -1;
        else
            return se.getScore();
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
    
            violationEvents = eventDAO.getEventsForVehicle(navigation.getVehicle().getVehicleID(), start, end, types);
    
            // Lookup Addresses for events
            AddressLookup lookup = new AddressLookup();
            for (Event event : violationEvents)
            {
                event.setAddressStr(lookup.getAddress(event.getLatitude(), event.getLongitude()));
            }
        }
    }

    // OVERALL SCORE properties
    public Integer getOverallScore()
    {
        if (overallScore == null)
        {
            setOverallScore(initAverageScore(ScoreType.SCORE_OVERALL));
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

        setOverallScoreHistory(createLineDef(ScoreType.SCORE_OVERALL));
        return overallScoreHistory;
    }

    public void setOverallScoreHistory(String overallScoreHistory)
    {
        this.overallScoreHistory = overallScoreHistory;
    }

    public String getOverallScoreStyle()
    {
        if (overallScoreStyle == null)
        {
            setOverallScore(initAverageScore(ScoreType.SCORE_OVERALL));
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
        setCoachingHistory(createLineDef(ScoreType.SCORE_COACHING_EVENTS));
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
            Trip tempTrip = vehicleDAO.getLastTrip(navigation.getVehicle().getVehicleID());

            if(tempTrip == null)
            {
                logger.debug("LastTrip NULL - VehicleID: " + navigation.getVehicle().getVehicleID());
            }
            
            if (tempTrip != null && tempTrip.getRoute().size() > 0)
            {
                logger.debug("LastTrip found for - VehicleID: " + navigation.getVehicle().getVehicleID());
                hasLastTrip = true;
                navigation.setDriver(driverDAO.findByID(tempTrip.getDriverID()));

                TripDisplay trip = new TripDisplay(tempTrip, navigation.getDriver().getPerson().getTimeZone());
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

    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    public EventDAO getEventDAO()
    {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO)
    {
        this.eventDAO = eventDAO;
    }

    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    // MPG PROPERTIES
    public String getMpgHistory()
    {
        if (mpgHistory == null)
            mpgHistory = this.createMultiLineDef();

        return mpgHistory;
    }

    public void setMpgHistory(String mpgHistory)
    {
        this.mpgHistory = mpgHistory;
    }

    private String createMultiLineDef()
    {
        List<MpgEntity> mpgEntities = mpgDAO.getVehicleEntities(navigation.getVehicle().getVehicleID(), durationBean.getDuration(), null);
        List<String> catLabelList = GraphicUtil.createMonthList(durationBean.getDuration());

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

    public String createLineDef(ScoreType scoreType)
    {
        StringBuffer sb = new StringBuffer();
        Line line = new Line();

        // Start XML Data
        sb.append(line.getControlParameters());

        List<ScoreableEntity> scoreList = scoreDAO.getVehicleScoreHistory(navigation.getVehicle().getVehicleID(), durationBean.getDuration(), scoreType, GraphicUtil
                .getDurationSize(durationBean.getDuration()));

        List<String> monthList = GraphicUtil.createMonthList(durationBean.getDuration());

        int cnt = 0;
        for (ScoreableEntity e : scoreList)
        {
            if (e.getScore() != null)
            {
              //Adding special condition for coaching events.
                if(scoreType == ScoreType.SCORE_COACHING_EVENTS)
                    sb.append(line.getChartItem(new Object[] { (int) (e.getScore()), monthList.get(cnt) }));
                else
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

    public List<CategorySeriesData> createJasperDef(ScoreType scoreType)
    {
        List<ScoreableEntity> scoreList = scoreDAO.getVehicleScoreHistory(navigation.getVehicle().getVehicleID(), durationBean.getDuration(), scoreType, GraphicUtil
                .getDurationSize(durationBean.getDuration()));

        List<CategorySeriesData> chartDataList = new ArrayList<CategorySeriesData>();
        List<String> monthList = GraphicUtil.createMonthList(durationBean.getDuration());

        int count = 0;
        for (ScoreableEntity se : scoreList)
        {
            chartDataList.add(new CategorySeriesData(MessageUtil.getMessageString(scoreType.toString()), monthList.get(count).toString(), se.getScore() / 10.0D, monthList.get(
                    count).toString()));
            count++;
        }
        return chartDataList;
    }

    public List<CategorySeriesData> createMpgJasperDef()
    {
        List<CategorySeriesData> chartDataList = new ArrayList<CategorySeriesData>();
        List<MpgEntity> mpgEntities = mpgDAO.getVehicleEntities(navigation.getVehicle().getVehicleID(), durationBean.getDuration(), null);

        List<String> monthList = GraphicUtil.createMonthList(durationBean.getDuration());

        int count = 0;
        for (MpgEntity me : mpgEntities)
        {
            chartDataList.add(new CategorySeriesData("Light", monthList.get(count).toString(), me.getLightValue(), monthList.get(count).toString()));
            chartDataList.add(new CategorySeriesData("Medium", monthList.get(count).toString(), me.getMediumValue(), monthList.get(count).toString()));
            chartDataList.add(new CategorySeriesData("Heavy", monthList.get(count).toString(), me.getHeavyValue(), monthList.get(count).toString()));

            count++;
        }
        logger.debug("CHART Data List for MPG " + chartDataList.size());
        return chartDataList;
    }

    public List<ReportCriteria> buildReport() throws IOException
    {
        List<ReportCriteria> tempCriteria = new ArrayList<ReportCriteria>();

        // Page 1
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.VEHICLE_SUMMARY_P1, getGroupHierarchy().getTopGroup().getName());
        reportCriteria.addChartDataSet(createJasperDef(ScoreType.SCORE_OVERALL));
        reportCriteria.setDuration(durationBean.getDuration());
        reportCriteria.addParameter("REPORT_NAME", "Vehicle Performance: Summary");
        reportCriteria.addParameter("OVERALL_SCORE", this.getOverallScore() / 10.0D);
        reportCriteria.addParameter("DRIVER_NAME", this.getNavigation().getVehicle().getFullName());
        reportCriteria.addParameter("SPEED_SCORE", initAverageScore(ScoreType.SCORE_SPEEDING) / 10.0D);
        reportCriteria.addParameter("STYLE_SCORE", initAverageScore(ScoreType.SCORE_DRIVING_STYLE) / 10.0D);
        reportCriteria.addParameter("SEATBELT_SCORE", initAverageScore(ScoreType.SCORE_SEATBELT) / 10.0D);
        reportCriteria.addChartDataSet(createJasperDef(ScoreType.SCORE_SPEEDING));
        reportCriteria.addChartDataSet(createJasperDef(ScoreType.SCORE_DRIVING_STYLE));
        reportCriteria.addChartDataSet(createJasperDef(ScoreType.SCORE_SEATBELT));
        tempCriteria.add(reportCriteria);

        // Page 2
        reportCriteria = new ReportCriteria(ReportType.VEHICLE_SUMMARY_P2, getGroupHierarchy().getTopGroup().getName());
        reportCriteria.setDuration(durationBean.getDuration());
        reportCriteria.addParameter("REPORT_NAME", "Vehicle Performance: Summary");
        reportCriteria.addParameter("OVERALL_SCORE", this.getOverallScore() / 10.0D);
        reportCriteria.addParameter("DRIVER_NAME", this.getNavigation().getVehicle().getFullName());

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
        reportCriteria.addChartDataSet(createJasperDef(ScoreType.SCORE_COACHING_EVENTS));
        tempCriteria.add(reportCriteria);

        return tempCriteria;
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

    public void exportReportToPdf() throws IOException
    {
        getReportRenderer().exportReportToPDF(buildReport(), getFacesContext());
    }

    public void emailReport() throws IOException
    {
        getReportRenderer().exportReportToEmail(buildReport(), getEmailAddress());
    }

}
