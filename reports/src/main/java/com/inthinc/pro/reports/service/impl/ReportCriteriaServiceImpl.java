package com.inthinc.pro.reports.service.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.MpgDAO;
import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.dao.ReportDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.StateMileageDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.CrashSummary;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.IdlePercentItem;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.SpeedPercentItem;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.dao.WaysmartDAO;
import com.inthinc.pro.reports.hos.DotHoursRemainingReportCriteria;
import com.inthinc.pro.reports.hos.HosDailyDriverLogReportCriteria;
import com.inthinc.pro.reports.hos.HosDriverDOTLogReportCriteria;
import com.inthinc.pro.reports.hos.HosEditsReportCriteria;
import com.inthinc.pro.reports.hos.HosViolationsDetailReportCriteria;
import com.inthinc.pro.reports.hos.HosViolationsSummaryReportCriteria;
import com.inthinc.pro.reports.hos.HosZeroMilesReportCriteria;
import com.inthinc.pro.reports.ifta.MileageByVehicleReportCriteria;
import com.inthinc.pro.reports.ifta.StateMileageByVehicleRoadStatusReportCriteria;
import com.inthinc.pro.reports.model.CategorySeriesData;
import com.inthinc.pro.reports.model.PieScoreData;
import com.inthinc.pro.reports.model.PieScoreRange;
import com.inthinc.pro.reports.performance.DriverHoursReportCriteria;
import com.inthinc.pro.reports.performance.PayrollDetailReportCriteria;
import com.inthinc.pro.reports.performance.PayrollSignoffReportCriteria;
import com.inthinc.pro.reports.performance.PayrollSummaryReportCriteria;
import com.inthinc.pro.reports.performance.TenHoursViolationReportCriteria;
import com.inthinc.pro.reports.performance.VehicleUsageReportCriteria;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.reports.util.MessageUtil;
import com.inthinc.pro.reports.util.ReportUtil;

public class ReportCriteriaServiceImpl implements ReportCriteriaService
{
    private GroupDAO groupDAO;
    private ScoreDAO scoreDAO;
    private MpgDAO mpgDAO;
    private VehicleDAO vehicleDAO;
    private EventDAO eventDAO;
    private RedFlagDAO redFlagDAO;

    private DeviceDAO deviceDAO;
    private ReportDAO reportDAO;
    private GroupReportDAO groupReportDAO;
    private DriverDAO driverDAO;
    private AccountDAO accountDAO;
    private HOSDAO hosDAO;
    private WaysmartDAO waysmartDAO;
    private AddressDAO addressDAO;
    private StateMileageDAO stateMileageDAO;
    
    private Locale locale;

    private static final Logger logger = Logger.getLogger(ReportCriteriaServiceImpl.class);
    
    @Override
    public ReportCriteria getDriverReportCriteria(Integer groupID, Duration duration, Locale locale, Boolean initDataSet)
    {
    	this.locale = locale;
        Group group = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.DRIVER_REPORT, group.getName(), locale);
        
        if (initDataSet) {
        	if (duration.equals(Duration.TWELVE)) {
        		Integer rowCount = reportDAO.getDriverReportCount(groupID, null);
        		PageParams pageParams = new PageParams(0, rowCount, new TableSortField(SortOrder.ASCENDING, "driverName"), null);
        		reportCriteria.setMainDataset(reportDAO.getDriverReportPage(groupID, pageParams));
        	}
        	else {
        		reportCriteria.setMainDataset(scoreDAO.getDriverReportData(groupID, duration, getGroupMap(group)));
        	}
            reportCriteria.setDuration(duration);
        }
        return reportCriteria;
    }


    @Override
    public ReportCriteria getMpgReportCriteria(Integer groupID, Duration duration, Locale locale)
    {
    	this.locale = locale;
        Group group = groupDAO.findByID(groupID);
        List<MpgEntity> entities = mpgDAO.getEntities(group, duration);

        List<CategorySeriesData> seriesData = new ArrayList<CategorySeriesData>();

        for (MpgEntity entity : entities)
        {
            String seriesID = entity.getEntityName();
            Number heavyValue = entity.getHeavyValue();
            Number mediumValue = entity.getMediumValue();
            Number lightValue = entity.getLightValue();
            seriesData.add(new CategorySeriesData("Light", seriesID, lightValue, seriesID));
            seriesData.add(new CategorySeriesData("Medium", seriesID, mediumValue, seriesID));
            seriesData.add(new CategorySeriesData("Heavy", seriesID, heavyValue, seriesID));
        }

        ReportCriteria reportCriteria = new ReportCriteria(ReportType.MPG_GROUP, group.getName(), locale);
        reportCriteria.setMainDataset(entities);
        reportCriteria.addChartDataSet(seriesData);
        reportCriteria.setDuration(duration);
        reportCriteria.setRecordsPerReportParameters(6, "entityName", "category");
        return reportCriteria;
    }

    @Override
    public ReportCriteria getOverallScoreReportCriteria(Integer groupID, Duration duration, Locale locale)
    {
    	this.locale = locale;
        NumberFormat format = NumberFormat.getInstance(locale);
        format.setMaximumFractionDigits(1);
        format.setMinimumFractionDigits(1);
        ScoreableEntity scoreableEntity = scoreDAO.getAverageScoreByType(groupID, duration, ScoreType.SCORE_OVERALL);

        // TODO: Needs to be optimized by not calling on every report.
        Group group = groupDAO.findByID(groupID);

        String overallScore = format.format((double) ((double) scoreableEntity.getScore() / (double) 10.0));
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.OVERALL_SCORE, group.getName(), locale);
        reportCriteria.setMainDataset(getPieScoreData(ScoreType.SCORE_OVERALL, groupID, duration));
        reportCriteria.addParameter("OVERALL_SCORE", overallScore);
        reportCriteria.addParameter("OVERALL_SCORE_DATA", getPieScoreData(ScoreType.SCORE_OVERALL, groupID, duration));
        reportCriteria.addParameter("DRIVER_STYLE_DATA", getPieScoreData(ScoreType.SCORE_DRIVING_STYLE, groupID, duration));
        reportCriteria.addParameter("SEATBELT_USE_DATA", getPieScoreData(ScoreType.SCORE_SEATBELT, groupID, duration));
        reportCriteria.addParameter("SPEED_DATA", getPieScoreData(ScoreType.SCORE_SPEEDING, groupID, duration));
        reportCriteria.setDuration(duration);
        return reportCriteria;
    }

    private List<PieScoreData> getPieScoreData(ScoreType scoreType, Integer groupID, Duration duration)
    {
        // Fetch, qualifier is groupId (parent), date from, date to
        List<ScoreableEntity> s = null;
        try
        {
            logger.debug("getting scores for groupID: " + groupID);
            // s = scoreDAO.getScores(this.navigation.getGroupID(),
            // startDate, endDate, ScoreType.SCORE_OVERALL_PERCENTAGES);
            s = scoreDAO.getScoreBreakdown(groupID, duration, scoreType);
        }
        catch (Exception e)
        {
            logger.debug("graphicDao error: " + e.getMessage());
        }
        logger.debug("found: " + s.size());

        List<PieScoreData> pieChartDataList = new ArrayList<PieScoreData>();

        for (int i = 0; i < s.size(); i++)
        {
            PieScoreRange pieScoreRange = PieScoreRange.valueOf(i);
            pieChartDataList.add(new PieScoreData(pieScoreRange.getLabel(), s.get(i).getScore(), pieScoreRange.getLabel()));
        }
        return pieChartDataList;
    }

    @Override
    public ReportCriteria getTrendChartReportCriteria(Integer groupID, Duration duration, Locale locale)
    {
    	this.locale = locale;
        List<CategorySeriesData> lineGraphDataList = new ArrayList<CategorySeriesData>();
        List<ScoreableEntity> s = getScores(groupID, duration);
    	Group group = groupDAO.findByID(groupID);
//        ScoreableEntity summaryScore = scoreDAO.getTrendSummaryScore(groupID, duration, ScoreType.SCORE_OVERALL);
        ScoreableEntity summaryScore = scoreDAO.getSummaryScore(groupID, duration, ScoreType.SCORE_OVERALL);
        if (summaryScore != null)
        {
        	String summaryTitle = MessageUtil.formatMessageString("report.trend.summary", (getLocale() == null) ? Locale.getDefault() : getLocale(), group.getName());
        	summaryScore.setIdentifier(summaryTitle);
        	s.add(0,summaryScore);
        }
        // Loop over returned set of group ids, controlled by scroller
        Map<Integer, List<ScoreableEntity>> groupTrendMap = scoreDAO.getTrendScores(groupID, duration);

        List<String> monthList = null;
        if (s != null && s.size() > 0)
        {
        	ScoreableEntity groupScore = s.get(0);
        	List<ScoreableEntity> firstGroupScoreList = groupTrendMap.get(groupScore.getEntityID());
        	monthList = ReportUtil.createDateLabelList(firstGroupScoreList, duration, MessageUtil.getMessageString("shortDateFormat", locale)/*"M/dd"*/, locale);
        }


        for (int i = 0; i < s.size(); i++)
        {
            ScoreableEntity se = s.get(i);
            List<ScoreableEntity> scoreableEntityList = groupTrendMap.get(se.getEntityID());
            CrashSummary crashSummary = scoreDAO.getGroupCrashSummaryData(se.getEntityID());
//            trendBeanItem.setCrashSummary(crashSummary);


            // Not a full range, pad w/ zero
    		int holes = duration.getDvqCount() - scoreableEntityList.size();
//    		if (duration == Duration.DAYS)
//    		{
//    		    holes = duration.getNumberOfDays() - scoreableEntityList.size();
//    		}
//    		else
//    		{
//    		    holes = ReportUtil.convertToMonths(duration) - scoreableEntityList.size();
//    		}
    		int index = 0;
    		for (int k = 0; k < holes; k++)
    		{
    		    lineGraphDataList.add(new CategorySeriesData(se.getIdentifier(), monthList.get(index++), 0F, se.getIdentifier()));
    		}
    		for (ScoreableEntity scoreableEntity : scoreableEntityList)
    		{
    		    if (scoreableEntity.getScore() != null && scoreableEntity.getScore() >= 0)
    		    {
    		        Float score = new Float((scoreableEntity.getScore() == null || scoreableEntity.getScore() < 0) ? 5 : scoreableEntity.getScore() / 10.0);
    		        lineGraphDataList.add(new CategorySeriesData(se.getIdentifier(), monthList.get(index++), score, se.getIdentifier()));
    		    }
    		    else
    		    {
    		        lineGraphDataList.add(new CategorySeriesData(se.getIdentifier(), monthList.get(index++), null, se.getIdentifier()));
    		    } 
    		}
        }

        
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.TREND, group.getName(), locale);
        reportCriteria.addChartDataSet(lineGraphDataList);
        reportCriteria.setMainDataset(s);
        reportCriteria.setDuration(duration);
        reportCriteria.setRecordsPerReportParameters(8, "identifier", "seriesID");
        return reportCriteria;
    }
    @Override
    public ReportCriteria getVehicleReportCriteria(Integer groupID, Duration duration, Locale locale, Boolean initDataSet)
    {
    	this.locale = locale;
        Group group = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.VEHICLE_REPORT, group.getName(), locale);
        
        if (initDataSet) {
        	if (duration.equals(Duration.TWELVE)) {
        		Integer rowCount = reportDAO.getVehicleReportCount(groupID, null);
        		PageParams pageParams = new PageParams(0, rowCount, null, null);
        		reportCriteria.setMainDataset(reportDAO.getVehicleReportPage(groupID, pageParams));
        	}
        	else {
        		reportCriteria.setMainDataset(scoreDAO.getVehicleReportData(groupID, duration, getGroupMap(group)));
        	}
            reportCriteria.setDuration(duration);
        }

        return reportCriteria;
    }

    private Map<Integer, Group> getGroupMap(Group group) {
    	
    	Map<Integer, Group> groupMap = new HashMap<Integer, Group>();
    	List<Group> allGroups = groupDAO.getGroupsByAcctID(group.getAccountID());
    	for (Group g : allGroups)
    		groupMap.put(g.getGroupID(), g);
		return groupMap;
	}


	private List<ScoreableEntity> getScores(Integer groupID, Duration duration)
    {
        List<ScoreableEntity> s = null;
        try
        {
            s = scoreDAO.getScores(groupID, duration, ScoreType.SCORE_OVERALL);
        }
        catch (Exception e)
        {
            logger.debug("scoreDao error: " + e.getMessage());
        }

        return s;
    }

    @Override
    public ReportCriteria getDevicesReportCriteria(Integer groupID, Locale locale, Boolean initDataSet) 
    {
    	this.locale = locale;
        Group group = groupDAO.findByID(groupID);
    	ReportCriteria reportCriteria = new ReportCriteria(ReportType.DEVICES_REPORT, group.getName(), locale);
        if (initDataSet) {
        	Integer rowCount = reportDAO.getDeviceReportCount(groupID, null);
        	PageParams pageParams = new PageParams(0, rowCount, null, null);
        	reportCriteria.setMainDataset(reportDAO.getDeviceReportPage(groupID, pageParams));
        }
        return reportCriteria;
    }
    @Override
    public ReportCriteria getIdlingReportCriteria(Integer groupID, Interval interval, Locale locale, Boolean initDataSet)
    {
    	this.locale = locale;
        Group group = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.IDLING_REPORT, group.getName(), locale);
		DateTimeFormatter fmt = DateTimeFormat.forPattern(MessageUtil.getMessageString("dateFormat", getLocale()));
        reportCriteria.addParameter("BEGIN_DATE", fmt.print(interval.getStart()));
        reportCriteria.addParameter("END_DATE", fmt.print(interval.getEnd()));

        if (initDataSet) {
        	Integer rowCount = reportDAO.getIdlingReportCount(groupID, interval, null);
        	PageParams pageParams = new PageParams(0, rowCount, null, null);
        	reportCriteria.setMainDataset(reportDAO.getIdlingReportPage(groupID, interval, pageParams));
        }


        return reportCriteria;
    }
    @Override
    public ReportCriteria getEventsReportCriteria(Integer groupID, Locale locale)
    {
        Group tmpGroup = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.EVENT_REPORT, tmpGroup.getName(), locale);
        return reportCriteria;
    }

    @Override
    public ReportCriteria getRedFlagsReportCriteria(Integer groupID, Locale locale)
    {
    	this.locale = locale;
        Group tmpGroup = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.RED_FLAG_REPORT, tmpGroup.getName(), locale);
        return reportCriteria;
    }

    @Override
    public ReportCriteria getWarningsReportCriteria(Integer groupID, Locale locale)
    {
    	this.locale = locale;
        Group tmpGroup = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.WARNING_REPORT, tmpGroup.getName(), locale);
        return reportCriteria;
    }

    @Override
    public ReportCriteria getEmergencyReportCriteria(Integer groupID, Locale locale)
    {
    	this.locale = locale;
        Group tmpGroup = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.EMERGENCY_REPORT, tmpGroup.getName(), locale);
        return reportCriteria;
    }
    
    @Override
    public ReportCriteria getZoneAlertsReportCriteria(Integer groupID, Locale locale)
    {
    	this.locale = locale;
        // List<Event> eventList = eventDAO.getZoneAlertsForGroup(groupID,7);
        Group tmpGroup = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.EMERGENCY_REPORT, tmpGroup.getName(), locale);
        // reportCriteria.setMainDataset(eventList);
        return reportCriteria;
    }
    
    @Override
    public ReportCriteria getCrashHistoryReportCriteria(Integer groupID, Locale locale)
    {
    	this.locale = locale;
        // List<Event> eventList = eventDAO.getZoneAlertsForGroup(groupID,7);
        Group tmpGroup = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.CRASH_HISTORY_REPORT, tmpGroup.getName(), locale);
        // reportCriteria.setMainDataset(eventList);
        return reportCriteria;
    }    

    @Override
	public ReportCriteria getSpeedPercentageReportCriteria(Integer groupID, Duration duration, Locale locale) {
        
        this.locale = locale;
        List<CategorySeriesData> barChartList = new ArrayList<CategorySeriesData>();
        List<CategorySeriesData> lineChartList = new ArrayList<CategorySeriesData>();
		List<SpeedPercentItem> speedPercentItemList = scoreDAO.getSpeedPercentItems(groupID, duration);
        List<String> monthList = ReportUtil.createDateLabelList(speedPercentItemList, duration,MessageUtil.getMessageString("shortDateFormat", locale)/*"M/dd"*/, locale);
       	int index = 0;

   		String distanceSeries = "driving";
   		String speedingSeries = "speeding";
   		String percentSeries = "percent";
   		if (locale != null)
   		{
   			String prefix = "report.speedpercent.";
   			distanceSeries = MessageUtil.getMessageString(prefix+distanceSeries, locale);
   			speedingSeries = MessageUtil.getMessageString(prefix+speedingSeries, locale);
   			percentSeries = MessageUtil.getMessageString(prefix+percentSeries, locale);
   		}
       	for (SpeedPercentItem speedItem : speedPercentItemList)
        {
       		long distance = (speedItem.getMiles() == null) ? 0  : (speedItem.getMiles().longValue()/100);
       		long speeding = (speedItem.getMilesSpeeding() == null) ? 0  : (speedItem.getMilesSpeeding().longValue()/100);
       		float percent = ((distance == 0l) ? 0f : ((float)speeding /(float)distance));
//       		logger.info(distance + " " + speeding + " " + percent);
       		barChartList.add(new CategorySeriesData(speedingSeries, monthList.get(index), speeding, speedingSeries));
       		barChartList.add(new CategorySeriesData(distanceSeries, monthList.get(index), (distance-speeding), distanceSeries));
       		lineChartList.add(new CategorySeriesData(percentSeries, monthList.get(index), percent, percentSeries));
            index++;
        }

        Group group = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.SPEED_PERCENTAGE, group.getName(), locale);
        reportCriteria.addChartDataSet(barChartList);
        reportCriteria.addChartDataSet(lineChartList);
        reportCriteria.setDuration(duration);
        return reportCriteria;
	}

    @Override
	public ReportCriteria getIdlePercentageReportCriteria(Integer groupID, Duration duration, Locale locale) {
        
    	this.locale = locale;
        List<CategorySeriesData> barChartList = new ArrayList<CategorySeriesData>();
        List<CategorySeriesData> lineChartList = new ArrayList<CategorySeriesData>();
		List<IdlePercentItem> idlePercentItemList = scoreDAO.getIdlePercentItems(groupID, duration);
        List<String> monthList = ReportUtil.createDateLabelList(idlePercentItemList, duration, MessageUtil.getMessageString("shortDateFormat", locale)/*"M/dd"*/, locale);
       	int index = 0;

   		String drivingSeries = "driving";
   		String idlingSeries = "idling";
   		String percentSeries = "percent";
   		if (locale != null)
   		{
   			String prefix = "report.idlepercent.";
   			drivingSeries = MessageUtil.getMessageString(prefix+drivingSeries, locale);
   			idlingSeries = MessageUtil.getMessageString(prefix+idlingSeries, locale);
   			percentSeries = MessageUtil.getMessageString(prefix+percentSeries, locale);
   		}
   		Integer totalVehicles = 0;
   		Integer totalEMUVehicles = 0;
       	for (IdlePercentItem idleItem : idlePercentItemList)
        {
       		long diff = idleItem.getDrivingTime() - idleItem.getIdlingTime();
       		diff = (diff < 0l) ? 0l : diff;
       		float driving = DateUtil.convertSecondsToHours(diff);
       		float idling = DateUtil.convertSecondsToHours(idleItem.getIdlingTime());
       		float percent = ((idleItem.getDrivingTime() == 0l) ? 0f : ((float)idleItem.getIdlingTime() /(float)idleItem.getDrivingTime()));
//       		logger.info(driving + " " + idling + " " + percent);
       		barChartList.add(new CategorySeriesData(idlingSeries, monthList.get(index), idling, idlingSeries));
       		barChartList.add(new CategorySeriesData(drivingSeries, monthList.get(index), driving, drivingSeries));
       		lineChartList.add(new CategorySeriesData(percentSeries, monthList.get(index), percent, percentSeries));
       		totalVehicles = idleItem.getNumVehicles();
       		totalEMUVehicles = idleItem.getNumEMUVehicles();
            index++;
        }

        Group group = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.IDLE_PERCENTAGE, group.getName(), locale);
        reportCriteria.addChartDataSet(barChartList);
        reportCriteria.addChartDataSet(lineChartList);
        reportCriteria.setDuration(duration);
        reportCriteria.addParameter("VEHICLE_STATS", MessageUtil.formatMessageString("report.idlepercent.vehicleStats", locale, new Object[] {totalEMUVehicles, totalVehicles}));
        return reportCriteria;
	}

    @Override
	public ReportCriteria getTeamStatisticsReportCriteria(Integer groupID, TimeFrame timeFrame, DateTimeZone timeZone, Locale locale, Boolean initDataSet) {
        Group group = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.TEAM_STATISTICS_REPORT, group.getName(), locale);
        reportCriteria.setTimeFrame(timeFrame);
        
        if (initDataSet) {
        	List<DriverVehicleScoreWrapper> driverStatistics;
        	
            if (    timeFrame.equals(TimeFrame.WEEK) ||
            		timeFrame.equals(TimeFrame.MONTH) ||
            		timeFrame.equals(TimeFrame.YEAR) ) 
                driverStatistics = groupReportDAO.getDriverScores(groupID, timeFrame.getAggregationDuration());
            else
                driverStatistics = groupReportDAO.getDriverScores(groupID, timeFrame.getInterval(timeZone));
            DriverVehicleScoreWrapper totals = DriverVehicleScoreWrapper.summarize(driverStatistics, group);
            if (totals != null) {
            	driverStatistics.add(totals);
            }
            reportCriteria.setMainDataset(driverStatistics);
        	

        }
		return reportCriteria;
	}

    @Override
    public ReportCriteria getTeamStopsReportCriteria(Integer driverID, TimeFrame timeFrame, DateTimeZone timeZone, Locale locale, Boolean initDataSet) {
        Driver driver = driverDAO.findByID(driverID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.TEAM_STOPS_REPORT, driver.getPerson().getFullName(), locale);
        reportCriteria.setTimeFrame(timeFrame);
        
        if (initDataSet) {
            List<DriverStops> driverStops;
            
            driverStops = driverDAO.getStops(driverID, timeFrame.getInterval(timeZone));
            
            DriverStops totals = DriverStops.summarize(driverStops);
            
            // Clean-up the totals before we ship-it
            if (totals != null) {
                totals.setDepartTime(null);
                totals.setArriveTime(null);
                driverStops.add(totals);
            }
            reportCriteria.setMainDataset(driverStops);
            

        }
        return reportCriteria;
    }    

    @Override
    public List<ReportCriteria> getHosDailyDriverLogReportCriteria(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale, Boolean defaultUseMetric) {
        HosDailyDriverLogReportCriteria hosDailyDriverLogReportCriteria = new HosDailyDriverLogReportCriteria(locale, defaultUseMetric);
        hosDailyDriverLogReportCriteria.setDriverDAO(driverDAO);
        hosDailyDriverLogReportCriteria.setGroupDAO(groupDAO);
        hosDailyDriverLogReportCriteria.setAccountDAO(accountDAO);
        hosDailyDriverLogReportCriteria.setHosDAO(hosDAO);
        hosDailyDriverLogReportCriteria.setAddressDAO(addressDAO);
        
        hosDailyDriverLogReportCriteria.init(accountGroupHierarchy, driverID, interval);
        return hosDailyDriverLogReportCriteria.getCriteriaList();
        
    }
    @Override
    public List<ReportCriteria> getHosDailyDriverLogReportCriteria(GroupHierarchy accountGroupHierarchy,  List<Integer> groupIDList, Interval interval, Locale locale, Boolean defaultUseMetric) {
        HosDailyDriverLogReportCriteria hosDailyDriverLogReportCriteria = new HosDailyDriverLogReportCriteria(locale, defaultUseMetric);
        hosDailyDriverLogReportCriteria.setDriverDAO(driverDAO);
        hosDailyDriverLogReportCriteria.setGroupDAO(groupDAO);
        hosDailyDriverLogReportCriteria.setAccountDAO(accountDAO);
        hosDailyDriverLogReportCriteria.setHosDAO(hosDAO);
        hosDailyDriverLogReportCriteria.setAddressDAO(addressDAO);
        
        hosDailyDriverLogReportCriteria.init(accountGroupHierarchy, groupIDList, interval);
        return hosDailyDriverLogReportCriteria.getCriteriaList();
        
    }

    @Override
    public ReportCriteria getHosViolationsSummaryReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale) {
        HosViolationsSummaryReportCriteria criteria = new HosViolationsSummaryReportCriteria (locale);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
               
        criteria.init(accountGroupHierarchy, groupIDList, interval);
        return criteria;
    }

    @Override
    public ReportCriteria getHosViolationsDetailReportCriteria(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale) {
        
        HosViolationsDetailReportCriteria criteria = new HosViolationsDetailReportCriteria (locale);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
               
        criteria.init(accountGroupHierarchy, driverID, interval);
        return criteria;
    }
    
    @Override
    public ReportCriteria getHosViolationsDetailReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale) {
        
        HosViolationsDetailReportCriteria criteria = new HosViolationsDetailReportCriteria (locale);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
               
        criteria.init(accountGroupHierarchy, groupIDList, interval);
        return criteria;
    }
    
    @Override
    public ReportCriteria getHosDriverDOTLogReportCriteria(Integer driverID, Interval interval, Locale locale) {
        HosDriverDOTLogReportCriteria criteria = new HosDriverDOTLogReportCriteria(locale);
        
        criteria.setDriverDAO(driverDAO);
        criteria.setHosDAO(hosDAO);
               
        criteria.init(driverID, interval);
        return criteria;
    }

    

    @Override
    public ReportCriteria getDotHoursRemainingReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Locale locale) {
        DotHoursRemainingReportCriteria criteria = new DotHoursRemainingReportCriteria(locale);
        
        criteria.setGroupDAO(groupDAO);
        criteria.setDriverDAO(driverDAO);
        criteria.setHosDAO(hosDAO);
               
        criteria.init(accountGroupHierarchy, groupIDList);
        return criteria;
    }

    
    @Override
    public ReportCriteria getHosZeroMilesReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale) {
        HosZeroMilesReportCriteria criteria = new HosZeroMilesReportCriteria(locale);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
               
        criteria.init(accountGroupHierarchy, groupIDList, interval);
        return criteria;
    }


    @Override
    public ReportCriteria getHosEditsReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale) {
        HosEditsReportCriteria criteria  = new HosEditsReportCriteria(locale);
        criteria.setGroupDAO(groupDAO);
        criteria.setDriverDAO(driverDAO);
        criteria.setHosDAO(hosDAO);
               
        criteria.init(accountGroupHierarchy, groupIDList, interval);
        return criteria;
    }
    
    @Override
    public ReportCriteria getPayrollDetailReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale) {
        PayrollDetailReportCriteria criteria = new PayrollDetailReportCriteria(locale);
        criteria.setAccountDAO(accountDAO);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
               
        criteria.init(accountGroupHierarchy, groupIDList, interval);
        return criteria;
    }
    
    @Override
    public ReportCriteria getPayrollSignoffReportCriteria(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale)
    {
        PayrollSignoffReportCriteria criteria = new PayrollSignoffReportCriteria(locale);
        criteria.setAccountDAO(accountDAO);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
               
        criteria.init(accountGroupHierarchy, driverID, interval);
        return criteria;
        
    }
    @Override
    public ReportCriteria getPayrollSignoffReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale)
    {
        PayrollSignoffReportCriteria criteria = new PayrollSignoffReportCriteria(locale);
        criteria.setAccountDAO(accountDAO);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
               
        criteria.init(accountGroupHierarchy, groupIDList, interval);
        return criteria;
        
    }


    @Override
    public ReportCriteria getPayrollSummaryReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale) {
        PayrollSummaryReportCriteria criteria = new PayrollSummaryReportCriteria(locale);
        criteria.setAccountDAO(accountDAO);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
               
        criteria.init(accountGroupHierarchy, groupIDList, interval);
        return criteria;
    }

    /**
     * Provide all data criteria including layout and data.
     * 
     * @param  groupeID ID of the group chosen by the user
     * @param  interval Interval chosen by the user
     * @param  locale   Local settings of the user - internationalization 
     * @return criteria All report criteria including layout and data 
     */ 
    @Override
    public ReportCriteria getTenHoursDayViolationsCriteria(Integer groupID, Interval interval, Locale locale) {
        TenHoursViolationReportCriteria criteria = new TenHoursViolationReportCriteria(locale);
        criteria.setAccountDAO(accountDAO);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setWaysmartDAO(waysmartDAO);
               
        criteria.init(groupID, interval);
        return criteria;
    }
    
    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.reports.service.ReportCriteriaService#getDriverHoursReportCriteria(java.lang.Integer, org.joda.time.Interval, java.util.Locale)
     */
    @Override
    public ReportCriteria getDriverHoursReportCriteria(Integer groupID, Interval interval, Locale locale) {
    	DriverHoursReportCriteria criteria = new DriverHoursReportCriteria(locale);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setWaysmartDAO(waysmartDAO);
               
        criteria.init(groupID, interval);
        return criteria;
    }

    /**
     * Provide all data criteria including layout and data.
     * 
     * @param  id       ID of the group chosen by the user
     * @param  interval Interval chosen by the user
     * @param  locale   Local settings of the user - internationalization 
     * @param  group    Indicating if type of ID is group or driver 
     * @return criteria All report criteria including layout and data 
     */ 
    @Override
    public ReportCriteria getVehicleUsageReportCriteria(Integer id, Interval interval, Locale locale, boolean group) {
        VehicleUsageReportCriteria criteria = new VehicleUsageReportCriteria(locale);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setWaysmartDao(waysmartDAO);
               
        criteria.init(id, interval, group);
        return criteria;
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.reports.service.ReportCriteriaService#getMileageByVehicleReportCriteria(java.lang.Integer, org.joda.time.Interval, java.util.Locale, boolean)
     */
    public ReportCriteria getMileageByVehicleReportCriteria(List<Integer> groupIDList, Interval interval, Locale locale, boolean iftaOnly) 
    {
        MileageByVehicleReportCriteria criteria = new MileageByVehicleReportCriteria(locale);
        criteria.setGroupDAO(groupDAO);
        criteria.setStateMileageDAO(stateMileageDAO);
               
        criteria.init(groupIDList, interval, iftaOnly);
        return criteria;
    }
 
    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.reports.service.ReportCriteriaService#getMileageByVehicleReportCriteria(java.lang.Integer, org.joda.time.Interval, java.util.Locale, boolean)
     */
    public ReportCriteria getStateMileageByVehicleRoadStatusReportCriteria(List<Integer> groupIDList, Interval interval, Locale locale, boolean iftaOnly) 
    {
        StateMileageByVehicleRoadStatusReportCriteria criteria = new StateMileageByVehicleRoadStatusReportCriteria(locale);
        criteria.setGroupDAO(groupDAO);
        criteria.setStateMileageDAO(stateMileageDAO);
               
        criteria.init(groupIDList, interval, iftaOnly);
        return criteria;
    }
    
    
    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO)
    {
        this.scoreDAO = scoreDAO;
    }

    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }

    public void setMpgDAO(MpgDAO mpgDAO)
    {
        this.mpgDAO = mpgDAO;
    }

    public MpgDAO getMpgDAO()
    {
        return mpgDAO;
    }

    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    public DeviceDAO getDeviceDAO()
    {
        return deviceDAO;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO)
    {
        this.deviceDAO = deviceDAO;
    }

    public EventDAO getEventDAO()
    {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO)
    {
        this.eventDAO = eventDAO;
    }

    public void setRedFlagDAO(RedFlagDAO redFlagDAO)
    {
        this.redFlagDAO = redFlagDAO;
    }

    public RedFlagDAO getRedFlagDAO()
    {
        return redFlagDAO;
    }
    public ReportDAO getReportDAO() {
		return reportDAO;
	}

    public void setReportDAO(ReportDAO reportDAO) 
    {
		this.reportDAO = reportDAO;
	}

	public GroupReportDAO getGroupReportDAO() 
	{
		return groupReportDAO;
	}


	public void setGroupReportDAO(GroupReportDAO groupReportDAO) 
	{
		this.groupReportDAO = groupReportDAO;
	}

    public DriverDAO getDriverDAO() 
    {
        return driverDAO;
    }


    public void setDriverDAO(DriverDAO driverDAO) 
    {
        this.driverDAO = driverDAO;
    }

    public AccountDAO getAccountDAO() 
    {
        return accountDAO;
    }


    public void setAccountDAO(AccountDAO accountDAO) 
    {
        this.accountDAO = accountDAO;
    }

    public HOSDAO getHosDAO() 
    {
        return hosDAO;
    }


    public void setHosDAO(HOSDAO hosDAO) 
    {
        this.hosDAO = hosDAO;
    }

    /**
     * Setter for WaysmartDAO.
     * @param waysmartDAO the DAO to set
     */
    public void setWaysmartDAO(WaysmartDAO waysmartDAO) 
    {
        this.waysmartDAO = waysmartDAO;
    }


    /**
     * Getter for WaysmartDAO.
     * @return the waysmartDAO
     */
    public WaysmartDAO getWaysmartDAO() {
        return waysmartDAO;
    }

    public AddressDAO getAddressDAO() {
        return addressDAO;
    }


    public void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    /**
     * The stateMileageDAO setter.
     * @param stateMileageDAO the stateMileageDAO to set
     */
    public void setStateMileageDAO(StateMileageDAO stateMileageDAO) {
        this.stateMileageDAO = stateMileageDAO;
    }

    /**
     * The stateMileageDAO getter.
     * @return the stateMileageDAO
     */
    public StateMileageDAO getStateMileageDAO() {
        return stateMileageDAO;
    }


    public void setLocale(Locale locale)
    {
        this.locale = locale;
    }

    public Locale getLocale()
    {
        return locale;
    }

}
