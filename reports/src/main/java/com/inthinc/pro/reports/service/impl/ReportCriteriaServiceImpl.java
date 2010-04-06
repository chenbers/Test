package com.inthinc.pro.reports.service.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.MpgDAO;
import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.dao.ReportDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.CrashSummary;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.IdlePercentItem;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.SpeedPercentItem;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.model.CategorySeriesData;
import com.inthinc.pro.reports.model.PieScoreData;
import com.inthinc.pro.reports.model.PieScoreRange;
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
    
	private Locale locale;

    private static final Logger logger = Logger.getLogger(ReportCriteriaServiceImpl.class);
    @Override
    public ReportCriteria getDriverReportCriteria(Integer groupID, Locale locale)
    {
    	this.locale = locale;
        Group group = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.DRIVER_REPORT, group.getName(), locale);
        
		Integer rowCount = reportDAO.getDriverReportCount(groupID, null);
		PageParams pageParams = new PageParams(0, rowCount, null, null);
		reportCriteria.setMainDataset(reportDAO.getDriverReportPage(groupID, pageParams));


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
    public ReportCriteria getVehicleReportCriteria(Integer groupID, Locale locale)
    {
    	this.locale = locale;
        Group group = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.VEHICLE_REPORT, group.getName(), locale);
        
		Integer rowCount = reportDAO.getVehicleReportCount(groupID, null);
		PageParams pageParams = new PageParams(0, rowCount, null, null);
		reportCriteria.setMainDataset(reportDAO.getVehicleReportPage(groupID, pageParams));


        return reportCriteria;
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
    public ReportCriteria getDevicesReportCriteria(Integer groupID, Locale locale)
    {
    	this.locale = locale;
        Group group = groupDAO.findByID(groupID);

        Integer rowCount = reportDAO.getDeviceReportCount(groupID, null);
		PageParams pageParams = new PageParams(0, rowCount, null, null);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.DEVICES_REPORT, group.getName(), locale);
		reportCriteria.setMainDataset(reportDAO.getDeviceReportPage(groupID, pageParams));
        return reportCriteria;
    }
/*
    @Override
    public ReportCriteria getIdlingReportCriteria(Integer groupID, Date startDate, Date endDate, Locale locale)
    {
    	this.locale = locale;
        Group group = groupDAO.findByID(groupID);

        IdlingReportData data = scoreDAO.getIdlingReportData(groupID, startDate, endDate);
        List<IdlingReportItem> idlingReportItems = data.getItemList();

        for (IdlingReportItem idlingReportItem : idlingReportItems)
        {

            // Group name
            Group tmpGroup = groupDAO.findByID(idlingReportItem.getGroupID());
            idlingReportItem.setGroup(tmpGroup.getName());
//            idlingReportItem.prepareForDisplay();
        }

        ReportCriteria reportCriteria = new ReportCriteria(ReportType.IDLING_REPORT, group.getName(), locale);
        reportCriteria.setMainDataset(idlingReportItems);
        SimpleDateFormat sdf = new SimpleDateFormat(MessageUtil.getMessageString("dateFormat", getLocale()));
        reportCriteria.addParameter("BEGIN_DATE", sdf.format(startDate));
        reportCriteria.addParameter("END_DATE", sdf.format(endDate));

        return reportCriteria;
    }
*/
    @Override
    public ReportCriteria getIdlingReportCriteria(Integer groupID, Interval interval, Locale locale)
    {
    	this.locale = locale;
        Group group = groupDAO.findByID(groupID);

        Integer rowCount = reportDAO.getIdlingReportCount(groupID, interval, null);
		PageParams pageParams = new PageParams(0, rowCount, null, null);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.IDLING_REPORT, group.getName(), locale);
		reportCriteria.setMainDataset(reportDAO.getIdlingReportPage(groupID, interval, pageParams));

//        SimpleDateFormat sdf = new SimpleDateFormat(MessageUtil.getMessageString("dateFormat", getLocale()));
//        reportCriteria.addParameter("BEGIN_DATE", sdf.format(startDate));
//        reportCriteria.addParameter("END_DATE", sdf.format(endDate));
        
		DateTimeFormatter fmt = DateTimeFormat.forPattern(MessageUtil.getMessageString("dateFormat", getLocale()));
        reportCriteria.addParameter("BEGIN_DATE", fmt.print(interval.getStart()));
        reportCriteria.addParameter("END_DATE", fmt.print(interval.getEnd()));

        return reportCriteria;
    }
    @Override
    public ReportCriteria getEventsReportCriteria(Integer groupID, Locale locale)
    {
        // List<Event> eventList = eventDAO.getViolationEventsForGroup(groupID,7);
        Group tmpGroup = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.EVENT_REPORT, tmpGroup.getName(), locale);
        // reportCriteria.setMainDataset(eventList);
        return reportCriteria;
    }

    @Override
    public ReportCriteria getRedFlagsReportCriteria(Integer groupID, Locale locale)
    {
    	this.locale = locale;
        // List<RedFlag> redFlagList = redFlagDAO.getRedFlags(groupID, 7);
        Group tmpGroup = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.RED_FLAG_REPORT, tmpGroup.getName(), locale);
        // reportCriteria.setMainDataset(redFlagList);
        return reportCriteria;
    }

    @Override
    public ReportCriteria getWarningsReportCriteria(Integer groupID, Locale locale)
    {
    	this.locale = locale;
        // List<Event> eventList = eventDAO.getWarningEventsForGroup(groupID,7);
        Group tmpGroup = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.WARNING_REPORT, tmpGroup.getName(), locale);
        // reportCriteria.setMainDataset(eventList);
        return reportCriteria;
    }

    @Override
    public ReportCriteria getEmergencyReportCriteria(Integer groupID, Locale locale)
    {
    	this.locale = locale;
        // List<Event> eventList = eventDAO.getWarningEventsForGroup(groupID,7);
        Group tmpGroup = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.EMERGENCY_REPORT, tmpGroup.getName(), locale);
        // reportCriteria.setMainDataset(eventList);
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

    public void setReportDAO(ReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}

	public GroupReportDAO getGroupReportDAO() {
		return groupReportDAO;
	}


	public void setGroupReportDAO(GroupReportDAO groupReportDAO) {
		this.groupReportDAO = groupReportDAO;
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
