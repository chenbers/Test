package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.TripDisplay;
import com.inthinc.pro.charts.FusionMultiLineChart;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.MpgDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.CrashSummary;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.map.MapLookup;
import com.inthinc.pro.reports.model.CategorySeriesData;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.util.MessageUtil;

public class VehiclePerformanceBean extends BasePerformanceBean
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8811415462362897524L;

	private static final Logger logger          = Logger.getLogger(VehiclePerformanceBean.class);

    private VehicleDAO          vehicleDAO;
    private DriverDAO           driverDAO;
    private ScoreDAO            scoreDAO;

	private MpgDAO              mpgDAO;
    private EventDAO            eventDAO;

    private DurationBean        coachDurationBean;
    private DurationBean        mpgDurationBean;
    private DurationBean        speedDurationBean;
    private DurationBean        styleDurationBean;
    private DurationBean        seatBeltDurationBean;

    private TripDisplay         lastTrip;
    private List<Event>         violationEvents = new ArrayList<Event>();
    private List<Event>			tamperEvents;
    private Integer             overallScore;
    private String              overallScoreHistory;
    private String              overallScoreStyle;
    private String              mpgHistory;
    private String              coachingHistory;
    private Boolean             hasLastTrip;
    private TimeZone            timeZone;
    private VehicleSpeedBean    vehicleSpeedBean;
    private VehicleStyleBean    vehicleStyleBean;
    private VehicleSeatBeltBean vehicleSeatBeltBean;
    private CrashSummary 		crashSummary;
    private Boolean 			tripMayExist;
    
    public VehiclePerformanceBean() {
		super();
		tripMayExist = true;
    }
    
    @Override
    protected List<ScoreableEntity> getTrendCumulative(Integer id, Duration duration, ScoreType scoreType)
    {
        return this.getPerformanceDataBean().getTrendCumulative(id, EntityType.ENTITY_VEHICLE, duration, scoreType);
    }

    @Override
    protected List<ScoreableEntity> getTrendDaily(Integer id, Duration duration, ScoreType scoreType)
    {
        return this.getPerformanceDataBean().getTrendDaily(id, EntityType.ENTITY_VEHICLE, duration, scoreType);

    }

    
    protected Integer initAverageScore(ScoreType scoreType, Duration duration)
    {
		ScoreableEntity se = getPerformanceDataBean().getAverageScore(getVehicle().getVehicleID(), EntityType.ENTITY_VEHICLE, duration, scoreType);

        if (se != null && se.getScore() != null)
            return se.getScore();
        else
            return -1;
    }

    public void initViolations(Date start, Date end)
    {
        if (violationEvents.isEmpty())
        {
        	//test null EventMapper map item
        	System.out.println(EventMapper.getEventType(40002));
        	List<Integer> types = new ArrayList<Integer>();
            types.add(EventMapper.TIWIPRO_EVENT_SPEEDING_EX3);
            types.add(EventMapper.TIWIPRO_EVENT_SEATBELT);
            types.add(EventMapper.TIWIPRO_EVENT_NOTEEVENT);
            types.add(EventMapper.TIWIPRO_EVENT_IDLE);
            types.add(EventMapper.TIWIPRO_EVENT_UNPLUGGED);
            violationEvents = eventDAO.getEventsForVehicle(getVehicle().getVehicleID(), start, end, types,showExcludedEvents);

            // Lookup Addresses for events

            for (Event event : violationEvents)
            {
                event.setAddressStr(getAddress(event.getLatLng()));
            }
       }
    }

    public Integer getOverallScore()
    {
        setOverallScore(initAverageScore(ScoreType.SCORE_OVERALL, durationBean.getDuration()));
        return overallScore;
    }

    public void setOverallScore(Integer overallScore)
    {
        this.overallScore = overallScore;
        setOverallScoreStyle(ScoreBox.GetStyleFromScore(overallScore, ScoreBoxSizes.MEDIUM));
    }

    public String getOverallScoreHistory()
    {
        setOverallScoreHistory(createFusionMultiLineDef(getVehicle().getVehicleID(), durationBean.getDuration(), ScoreType.SCORE_OVERALL));
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
            setOverallScore(initAverageScore(ScoreType.SCORE_OVERALL, durationBean.getDuration()));
        }
        return overallScoreStyle;
    }

    public void setOverallScoreStyle(String overallScoreStyle)
    {
        if ( overallScoreStyle.equalsIgnoreCase("null") ) {
            this.overallScoreStyle = null;
            return;
        }
        
        this.overallScoreStyle = overallScoreStyle;
    }

    public String getCoachingHistory()
    {
        setCoachingHistory(createCoachingChart(getVehicle().getVehicleID(), ScoreType.SCORE_COACHING_EVENTS, coachDurationBean.getDuration()));
        return coachingHistory;
    }

    public void setCoachingHistory(String coachingHistory)
    {
        this.coachingHistory = coachingHistory;
    }

    public TripDisplay getLastTrip()
    {
        if (lastTrip == null && tripMayExist)
        {
            Trip tempTrip = vehicleDAO.getLastTrip(getVehicle().getVehicleID());

            if (tempTrip != null && tempTrip.getRoute().size() > 0)
            {
                hasLastTrip = true;
                setDriver(driverDAO.findByID(tempTrip.getDriverID()));

                TripDisplay trip = new TripDisplay(tempTrip, getTimeZone(), addressLookup.getMapServerURLString());
                setLastTrip(trip);
                initViolations(trip.getTrip().getStartTime(), trip.getTrip().getEndTime());
            }
            else
            {
                hasLastTrip = false;
            }
            tripMayExist = false;
        }
        return lastTrip;
    }

    public void setLastTrip(TripDisplay lastTrip)
    {
        this.lastTrip = lastTrip;
    }

    private TimeZone getTimeZoneFromDriver(Integer driverID)
    {
        TimeZone tz;
        Driver driver = driverDAO.findByID(driverID);
        if (driver != null && driver.getPerson() != null && driver.getPerson().getTimeZone() != null)
        {
            tz = driver.getPerson().getTimeZone();
        }
        else
        {
            // Use GMT for default if "Unknown Driver"
            tz = TimeZone.getTimeZone("GMT");
        }

        return tz;
    }

    public TimeZone getTimeZone()
    {
        if (timeZone == null)
            timeZone = getTimeZoneFromDriver(getVehicle().getDriverID());

        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone)
    {
        this.timeZone = timeZone;
    }

    public List<Event> getViolationEvents()
    {
        return violationEvents;
    }

    public void setViolationEvents(List<Event> violationEvents)
    {
        this.violationEvents = violationEvents;
    }

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
        List<MpgEntity> mpgEntities = mpgDAO.getVehicleEntities(getVehicle().getVehicleID(), mpgDurationBean.getDuration(), null);
        List<String> catLabelList = GraphicUtil.createMonthList(mpgDurationBean.getDuration(),getLocale());

        StringBuffer sb = new StringBuffer();
        FusionMultiLineChart multiLineChart = new FusionMultiLineChart();
        sb.append(multiLineChart.getControlParameters());

        int yAxisName = sb.indexOf("yAxisName");
        sb.replace(yAxisName+10, yAxisName+11, "'"+MessageUtil.getMessageString(getFuelEfficiencyType()+"_Miles_Per_Gallon"));

        Long lightValues[] = new Long[mpgEntities.size()];
        Long medValues[] = new Long[mpgEntities.size()];
        Long heavyValues[] = new Long[mpgEntities.size()];
        int cnt = 0;
        sb.append(multiLineChart.getCategoriesStart());
        for (MpgEntity entity : mpgEntities)
        {
            lightValues[cnt] = entity.getLightValue() == null ? 0 : MeasurementConversionUtil.convertMpgToFuelEfficiencyType(entity.getLightValue(),getMeasurementType(),getFuelEfficiencyType()).longValue();
            medValues[cnt] = entity.getMediumValue() == null ? 0 : MeasurementConversionUtil.convertMpgToFuelEfficiencyType(entity.getMediumValue(), getMeasurementType(),getFuelEfficiencyType()).longValue();
            heavyValues[cnt] = entity.getHeavyValue() == null ? 0 : MeasurementConversionUtil.convertMpgToFuelEfficiencyType(entity.getHeavyValue(),getMeasurementType(),getFuelEfficiencyType()).longValue();
            sb.append(multiLineChart.getCategoryLabel(catLabelList.get(cnt)));
            cnt++;
        }
        sb.append(multiLineChart.getCategoriesEnd());

        sb.append(multiLineChart.getChartDataSet(MessageUtil.getMessageString("vehicle_mpg_light"), "B1D1DC", "B1D1DC", lightValues, catLabelList));
        sb.append(multiLineChart.getChartDataSet(MessageUtil.getMessageString("vehicle_mpg_medium"), "C8A1D1", "C8A1D1", medValues, catLabelList));
        sb.append(multiLineChart.getChartDataSet(MessageUtil.getMessageString("vehicle_mpg_heavy"), "A8C634", "A8C634", heavyValues, catLabelList));

        sb.append(multiLineChart.getClose());

        return sb.toString();
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
    public Duration getMpgDuration(){
    	
    	return mpgDurationBean.getDuration();
    }
    public void setMpgDuration(Duration mpgDuration)
    {
        this.mpgDurationBean.setDuration(mpgDuration);
        mpgHistory = null;

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

    public Boolean getHasLastTrip()
    {
//        if (this.lastTrip == null)
//            this.getLastTrip();

        return hasLastTrip;
    }

    public void setHasLastTrip(Boolean hasLastTrip)
    {
        this.hasLastTrip = hasLastTrip;
    }

    public List<CategorySeriesData> createMpgJasperDef()
    {
        List<CategorySeriesData> chartDataList = new ArrayList<CategorySeriesData>();
        List<MpgEntity> mpgEntities = mpgDAO.getVehicleEntities(getVehicle().getVehicleID(), mpgDurationBean.getDuration(), null);

        List<String> monthList = GraphicUtil.createMonthList(mpgDurationBean.getDuration(), MessageUtil.getMessageString("shortDateFormat") /*"M/dd"*/,getLocale());

        int count = 0;
        for (MpgEntity me : mpgEntities)
        {
            chartDataList.add(new CategorySeriesData(MessageUtil.getMessageString("vehicle_mpg_light"), 
                    monthList.get(count).toString(), me.getLightValue(), monthList.get(count).toString()));
            
            chartDataList.add(new CategorySeriesData(MessageUtil.getMessageString("vehicle_mpg_medium"), 
                    monthList.get(count).toString(), me.getMediumValue(), monthList.get(count).toString()));
            
            chartDataList.add(new CategorySeriesData(MessageUtil.getMessageString("vehicle_mpg_heavy"),
                    monthList.get(count).toString(), me.getHeavyValue(), monthList.get(count).toString()));

            count++;
        }
        return chartDataList;
    }

    public List<ReportCriteria> buildReport()
    {
        List<ReportCriteria> tempCriteria = new ArrayList<ReportCriteria>();
        Integer id = getVehicle().getVehicleID();

        // Page 1
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.VEHICLE_SUMMARY_P1, getGroupHierarchy().getTopGroup().getName(), getLocale());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setUseMetric(getMeasurementType() == MeasurementType.METRIC);
        reportCriteria.setMeasurementType(getPerson().getMeasurementType());
        reportCriteria.setFuelEfficiencyType(getPerson().getFuelEfficiencyType());
        reportCriteria.setDuration(durationBean.getDuration());
        reportCriteria.addParameter("OVERALL_SCORE", this.getOverallScore() / 10.0D);
        reportCriteria.addParameter("DRIVER_NAME", getVehicle().getFullName());
        reportCriteria.addParameter("SPEED_SCORE", initAverageScore(ScoreType.SCORE_SPEEDING, speedDurationBean.getDuration()) / 10.0D);
        reportCriteria.addParameter("STYLE_SCORE", initAverageScore(ScoreType.SCORE_DRIVING_STYLE, styleDurationBean.getDuration()) / 10.0D);
        reportCriteria.addParameter("SEATBELT_SCORE", initAverageScore(ScoreType.SCORE_SEATBELT, seatBeltDurationBean.getDuration()) / 10.0D);
        reportCriteria.addParameter("SPEED_DUR", speedDurationBean.getDuration().toString());
        reportCriteria.addParameter("STYLE_DUR", styleDurationBean.getDuration().toString());
        reportCriteria.addParameter("SEATBELT_DUR", seatBeltDurationBean.getDuration().toString());
        reportCriteria.addChartDataSet(createSingleJasperDef(id, ScoreType.SCORE_OVERALL, durationBean.getDuration()));
        reportCriteria.addChartDataSet(createSingleJasperDef(id, ScoreType.SCORE_SPEEDING, speedDurationBean.getDuration()));
        reportCriteria.addChartDataSet(createSingleJasperDef(id, ScoreType.SCORE_DRIVING_STYLE, styleDurationBean.getDuration()));
        reportCriteria.addChartDataSet(createSingleJasperDef(id, ScoreType.SCORE_SEATBELT, seatBeltDurationBean.getDuration()));
        tempCriteria.add(reportCriteria);

        // Page 2
        reportCriteria = new ReportCriteria(ReportType.VEHICLE_SUMMARY_P2, getGroupHierarchy().getTopGroup().getName(), getLocale());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setUseMetric(getMeasurementType() == MeasurementType.METRIC);
        reportCriteria.setMeasurementType(getPerson().getMeasurementType());
        reportCriteria.setFuelEfficiencyType(getPerson().getFuelEfficiencyType());
        reportCriteria.setDuration(durationBean.getDuration());
        reportCriteria.addParameter("OVERALL_SCORE", this.getOverallScore() / 10.0D);
        reportCriteria.addParameter("DRIVER_NAME", getVehicle().getFullName());

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
        reportCriteria.addChartDataSet(createSingleJasperDefCoaching(id, coachDurationBean.getDuration()));
        reportCriteria.addParameter("COACH_DUR", coachDurationBean.getDuration().toString());
        reportCriteria.addParameter("MPG_DUR", mpgDurationBean.getDuration().toString());
        tempCriteria.add(reportCriteria);

        return tempCriteria;
    }

    public void exportReportToPdf()
    {
        getReportRenderer().exportReportToPDF(buildReport(), getFacesContext());
    }

    public void emailReport()
    {
        getReportRenderer().exportReportToEmail(buildReport(), getEmailAddress());
    }

    public void setVehicleSpeedBean(VehicleSpeedBean vehicleSpeedBean)
    {
        this.vehicleSpeedBean = vehicleSpeedBean;
    }

    public VehicleSpeedBean getVehicleSpeedBean()
    {
        return vehicleSpeedBean;
    }

    public void setVehicleStyleBean(VehicleStyleBean vehicleStyleBean)
    {
        this.vehicleStyleBean = vehicleStyleBean;
    }

    public VehicleStyleBean getVehicleStyleBean()
    {
        return vehicleStyleBean;
    }

    public void setVehicleSeatBeltBean(VehicleSeatBeltBean vehicleSeatBeltBean)
    {
        this.vehicleSeatBeltBean = vehicleSeatBeltBean;
    }

    public VehicleSeatBeltBean getVehicleSeatBeltBean()
    {
        return vehicleSeatBeltBean;
    }

	@Override
	public void setDuration(Duration duration) {
		// TODO Auto-generated method stub
		
	}
	public CrashSummary getCrashSummary() {
		
		if (crashSummary == null)
		{
			crashSummary = scoreDAO.getVehicleCrashSummaryData(getVehicleID());
		}
		return crashSummary;
	}

	public void setCrashSummary(CrashSummary crashSummary) {
		this.crashSummary = crashSummary;
	}
    public List<Event> getTamperEvents() {
		return tamperEvents;
	}

	public void setTamperEvents(List<Event> tamperEvents) {
		this.tamperEvents = tamperEvents;
	}



}
