package com.inthinc.pro.reports.service.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.inthinc.pro.reports.performance.*;
import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriveTimeDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventAggregationDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.FormsDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.MpgDAO;
import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.dao.ReportDAO;
import com.inthinc.pro.dao.ReportIdlingDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.StateMileageDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.report.DVIRInspectionRepairReportDAO;
import com.inthinc.pro.dao.report.DVIRViolationReportDAO;
import com.inthinc.pro.dao.report.DriverPerformanceDAO;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.dao.report.TrailerReportDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.map.ReportAddressLookupBean;
import com.inthinc.pro.model.AccountHOSType;
import com.inthinc.pro.model.DriverStopReport;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.IdlePercentItem;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.ReportParamType;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.SpeedPercentItem;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportGroup;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.asset.WarrantyListReportCriteria;
import com.inthinc.pro.reports.communication.NonCommReportCriteria;
import com.inthinc.pro.reports.dao.WaysmartDAO;
import com.inthinc.pro.reports.dvir.DVIRInspectionRepairCompleteCriteria;
import com.inthinc.pro.reports.dvir.DVIRViolationReportCriteria;
import com.inthinc.pro.reports.forms.DVIRPostTripReportCriteria;
import com.inthinc.pro.reports.forms.DVIRPreTripReportCriteria;
import com.inthinc.pro.reports.hos.DotHoursRemainingReportCriteria;
import com.inthinc.pro.reports.hos.DrivingTimeViolationsDetailReportCriteria;
import com.inthinc.pro.reports.hos.DrivingTimeViolationsSummaryReportCriteria;
import com.inthinc.pro.reports.hos.HosDailyDriverLogReportCriteria;
import com.inthinc.pro.reports.hos.HosDriverDOTLogReportCriteria;
import com.inthinc.pro.reports.hos.HosEditsReportCriteria;
import com.inthinc.pro.reports.hos.HosViolationsDetailReportCriteria;
import com.inthinc.pro.reports.hos.HosViolationsSummaryReportCriteria;
import com.inthinc.pro.reports.hos.HosZeroMilesReportCriteria;
import com.inthinc.pro.reports.hos.NonDOTViolationsDetailReportCriteria;
import com.inthinc.pro.reports.hos.NonDOTViolationsSummaryReportCriteria;
import com.inthinc.pro.reports.ifta.MileageByVehicleReportCriteria;
import com.inthinc.pro.reports.ifta.StateMileageByMonthReportCriteria;
import com.inthinc.pro.reports.ifta.StateMileageByVehicleReportCriteria;
import com.inthinc.pro.reports.ifta.StateMileageByVehicleRoadStatusReportCriteria;
import com.inthinc.pro.reports.ifta.StateMileageCompareByGroupReportCriteria;
import com.inthinc.pro.reports.ifta.StateMileageFuelByVehicleReportCriteria;
import com.inthinc.pro.reports.model.CategorySeriesData;
import com.inthinc.pro.reports.model.PieScoreData;
import com.inthinc.pro.reports.model.PieScoreRange;
import com.inthinc.pro.reports.performance.BackingReportCriteria;
import com.inthinc.pro.reports.performance.DriverCoachingReportCriteria;
import com.inthinc.pro.reports.performance.DriverCoachingScoreReportCriteria;
import com.inthinc.pro.reports.performance.DriverExcludedViolationsCriteria;
import com.inthinc.pro.reports.performance.DriverHoursReportCriteria;
import com.inthinc.pro.reports.performance.DriverPerformanceKeyMetricsReportCriteria;
import com.inthinc.pro.reports.performance.DriverPerformanceReportCriteria;
import com.inthinc.pro.reports.performance.PayrollDetailReportCriteria;
import com.inthinc.pro.reports.performance.PayrollReportCompensatedHoursCriteria;
import com.inthinc.pro.reports.performance.PayrollSignoffReportCriteria;
import com.inthinc.pro.reports.performance.PayrollSummaryReportCriteria;
import com.inthinc.pro.reports.performance.SeatbeltClicksReportCriteria;
import com.inthinc.pro.reports.performance.TeamStopsReportCriteria;
import com.inthinc.pro.reports.performance.TenHoursViolationReportCriteria;
import com.inthinc.pro.reports.performance.ThirtyMinuteBreaksReportCriteria;
import com.inthinc.pro.reports.performance.VehicleUsageReportCriteria;
import com.inthinc.pro.reports.performance.MaintenanceEventsReportCriteria;
import com.inthinc.pro.reports.performance.MaintenanceIntervalReportCriteria;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.reports.util.MessageUtil;
import com.inthinc.pro.reports.util.ReportUtil;

import com.inthinc.pro.dao.ConfiguratorDAO;

public class ReportCriteriaServiceImpl implements ReportCriteriaService {

    private GroupDAO groupDAO;
    private ScoreDAO scoreDAO;
    private MpgDAO mpgDAO;
    private VehicleDAO vehicleDAO;
    private EventDAO eventDAO;
    private RedFlagDAO redFlagDAO;
    private EventAggregationDAO eventAggregationDAO;

    private DeviceDAO deviceDAO;
    private ReportDAO reportDAO;
    private ReportIdlingDAO reportIdlingDAO;
    private GroupReportDAO groupReportDAO;
    private DriverDAO driverDAO;
    private AccountDAO accountDAO;
    private HOSDAO hosDAO;
    private WaysmartDAO waysmartDAO;
    private AddressDAO addressDAO;
    private StateMileageDAO stateMileageDAO;
    private DriveTimeDAO driveTimeDAO;
    private DriverPerformanceDAO driverPerformanceDAO;
    private UserDAO userDAO;
    private FormsDAO formsDAO;
    private DVIRViolationReportDAO dvirViolationReportDAO;
    private DVIRInspectionRepairReportDAO dvirInspectionRepairReportDAO;
    private TrailerReportDAO trailerReportDAO;

    private ConfiguratorDAO configuratorJDBCDAO;

    public ReportIdlingDAO getReportIdlingDAO() {
        return reportIdlingDAO;
    }

    public void setReportIdlingDAO(ReportIdlingDAO reportIdlingDAO) {
        this.reportIdlingDAO = reportIdlingDAO;
    }
    
    public TrailerReportDAO getTrailerReportDAO() {
        return trailerReportDAO;
    }

    public void setTrailerReportDAO(TrailerReportDAO trailerReportDAO) {
        this.trailerReportDAO = trailerReportDAO;
    }

    public FormsDAO getFormsDAO() {
        return formsDAO;
    }

    public void setFormsDAO(FormsDAO formsDAO) {
        this.formsDAO = formsDAO;
    }
    
    public DVIRViolationReportDAO getDvirViolationReportDAO() {
        return dvirViolationReportDAO;
    }
    
    public void setDvirViolationReportDAO(DVIRViolationReportDAO dvirViolationReportDAO) {
        this.dvirViolationReportDAO = dvirViolationReportDAO;
    }

	public DVIRInspectionRepairReportDAO getDvirInspectionRepairReportDAO() {
        return dvirInspectionRepairReportDAO;
    }

    public void setDvirInspectionRepairReportDAO(DVIRInspectionRepairReportDAO dvirInspectionRepairReportDAO) {
        this.dvirInspectionRepairReportDAO = dvirInspectionRepairReportDAO;
    }



    private Locale locale;
    private ReportAddressLookupBean reportAddressLookupBean;

    private static final Logger logger = Logger.getLogger(ReportCriteriaServiceImpl.class);
    private static final long ONE_MINUTE = 60000L;
    private static final String NO_SCORE = "N/A";

    public ReportCriteriaServiceImpl() {}

    @Override
    public ReportCriteria getDriverReportCriteria(Integer groupID, Duration duration, Locale locale, Boolean initDataSet) {
        this.locale = locale;
        Group group = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.DRIVER_REPORT, group.getName(), locale);

        if (initDataSet) {
            if (duration.equals(Duration.TWELVE)) {
                Integer rowCount = reportDAO.getDriverReportCount(groupID, null);
                PageParams pageParams = new PageParams(0, rowCount, new TableSortField(SortOrder.ASCENDING, "driverName"), null);
                reportCriteria.setMainDataset(reportDAO.getDriverReportPage(groupID, pageParams));
            } else {
                reportCriteria.setMainDataset(scoreDAO.getDriverReportData(groupID, duration, getGroupMap(group)));
            }
            reportCriteria.setDuration(duration);
        }
        return reportCriteria;
    }

    @Override
    public ReportCriteria getMpgReportCriteria(Integer groupID, Duration duration, Locale locale, GroupHierarchy gh) {
        this.locale = locale;
        Group group = groupDAO.findByID(groupID);
        List<MpgEntity> entities = mpgDAO.getEntities(group, duration, gh);

        List<CategorySeriesData> seriesData = new ArrayList<CategorySeriesData>();

        for (MpgEntity entity : entities) {
            String seriesID = entity.getEntityName();
            Number heavyValue = entity.getHeavyValue();
            Number mediumValue = entity.getMediumValue();
            Number lightValue = entity.getLightValue();
            seriesData.add(new CategorySeriesData(MessageUtil.getMessageString("report.mpg.light", locale), seriesID, lightValue, seriesID));
            seriesData.add(new CategorySeriesData(MessageUtil.getMessageString("report.mpg.medium", locale), seriesID, mediumValue, seriesID));
            seriesData.add(new CategorySeriesData(MessageUtil.getMessageString("report.mpg.heavy", locale), seriesID, heavyValue, seriesID));
        }

        ReportCriteria reportCriteria = new ReportCriteria(ReportType.MPG_GROUP, group.getName(), locale);
        reportCriteria.setMainDataset(entities);
        reportCriteria.addChartDataSet(seriesData);
        reportCriteria.setDuration(duration);
        reportCriteria.setRecordsPerReportParameters(6, "entityName", "category");
        return reportCriteria;
    }

    @Override
    public ReportCriteria getOverallScoreReportCriteria(Integer groupID, Duration duration, Locale locale, GroupHierarchy gh) {
        this.locale = locale;

        // TODO: Needs to be optimized by not calling on every report.
        Group group = groupDAO.findByID(groupID);
        String overallScore = getOverallScore(groupID, duration, locale, gh);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.OVERALL_SCORE, group.getName(), locale);
        reportCriteria.setMainDataset(getPieScoreData(ScoreType.SCORE_OVERALL, groupID, duration, gh));
        reportCriteria.addParameter("OVERALL_SCORE", overallScore);
        reportCriteria.addParameter("OVERALL_SCORE_DATA", getPieScoreData(ScoreType.SCORE_OVERALL, groupID, duration, gh));
        reportCriteria.addParameter("DRIVER_STYLE_DATA", getPieScoreData(ScoreType.SCORE_DRIVING_STYLE, groupID, duration, gh));
        reportCriteria.addParameter("SEATBELT_USE_DATA", getPieScoreData(ScoreType.SCORE_SEATBELT, groupID, duration, gh));
        reportCriteria.addParameter("SPEED_DATA", getPieScoreData(ScoreType.SCORE_SPEEDING, groupID, duration, gh));
        reportCriteria.setDuration(duration);
        return reportCriteria;
    }

    private String getOverallScore(Integer groupID, Duration duration, Locale locale, GroupHierarchy gh){
        ScoreableEntity scoreableEntity = scoreDAO.getAverageScoreByType(groupID, duration, ScoreType.SCORE_OVERALL, gh);
        NumberFormat format = NumberFormat.getInstance(locale);
        format.setMaximumFractionDigits(1);
        format.setMinimumFractionDigits(1);

        return((scoreableEntity == null)|| (scoreableEntity.getScore()==null)||(scoreableEntity.getScore() < 0))?NO_SCORE:format.format((double) ((double) scoreableEntity.getScore() /10.0D));

    }
    private List<PieScoreData> getPieScoreData(ScoreType scoreType, Integer groupID, Duration duration, GroupHierarchy gh) {
        // Fetch, qualifier is groupId (parent), date from, date to
        List<ScoreableEntity> s = null;
        try {
            logger.debug("getting scores for groupID: " + groupID);
            // s = scoreDAO.getScores(this.navigation.getGroupID(),
            // startDate, endDate, ScoreType.SCORE_OVERALL_PERCENTAGES);
            s = scoreDAO.getScoreBreakdown(groupID, duration, scoreType, gh);
        } catch (Exception e) {
            logger.debug("graphicDao error: " + e.getMessage());
        }
        logger.debug("found: " + s.size());

        List<PieScoreData> pieChartDataList = new ArrayList<PieScoreData>();

        for (int i = 0; i < s.size(); i++) {
            PieScoreRange pieScoreRange = PieScoreRange.valueOf(i);
            pieChartDataList.add(new PieScoreData(pieScoreRange.getLabel(), s.get(i).getScore(), pieScoreRange.getLabel()));
        }
        return pieChartDataList;
    }

    @Override
    public ReportCriteria getTrendChartReportCriteria(Integer groupID, Duration duration, Locale locale, GroupHierarchy gh) {
        this.locale = locale;
        List<CategorySeriesData> lineGraphDataList = new ArrayList<CategorySeriesData>();
        List<ScoreableEntity> s = getScores(groupID, duration, gh);
        Group group = groupDAO.findByID(groupID);
        // ScoreableEntity summaryScore = scoreDAO.getTrendSummaryScore(groupID, duration, ScoreType.SCORE_OVERALL);
        ScoreableEntity summaryScore = scoreDAO.getSummaryScore(groupID, duration, ScoreType.SCORE_OVERALL, gh);
        if (summaryScore != null) {
            String summaryTitle = MessageUtil.formatMessageString("report.trend.summary", (getLocale() == null) ? Locale.getDefault() : getLocale(), group.getName());
            summaryScore.setIdentifier(summaryTitle);
            s.add(0, summaryScore);
        }
        // Loop over returned set of group ids, controlled by scroller
        Map<Integer, List<ScoreableEntity>> groupTrendMap = scoreDAO.getTrendScores(groupID, duration, gh);

        List<String> monthList = null;
        if (s != null && s.size() > 0) {
            ScoreableEntity groupScore = s.get(0);
            List<ScoreableEntity> firstGroupScoreList = groupTrendMap.get(groupScore.getEntityID());
            monthList = ReportUtil.createDateLabelList(firstGroupScoreList, duration, MessageUtil.getMessageString("shortDateFormat", locale)/* "M/dd" */, locale);
        }

        for (int i = 0; i < s.size(); i++) {
            ScoreableEntity se = s.get(i);
            List<ScoreableEntity> scoreableEntityList = groupTrendMap.get(se.getEntityID());

            // Not a full range, pad w/ zero
            int holes = duration.getDvqCount() - scoreableEntityList.size();
            int index = 0;
            for (int k = 0; k < holes; k++) {
                lineGraphDataList.add(new CategorySeriesData(se.getIdentifier(), monthList.get(index++), 0F, se.getIdentifier()));
            }
            for (ScoreableEntity scoreableEntity : scoreableEntityList) {
                if (scoreableEntity.getScore() != null && scoreableEntity.getScore() >= 0) {
                    Float score = new Float((scoreableEntity.getScore() == null || scoreableEntity.getScore() < 0) ? 5 : scoreableEntity.getScore() / 10.0);
                    lineGraphDataList.add(new CategorySeriesData(se.getIdentifier(), monthList.get(index++), score, se.getIdentifier()));
                } else {
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
    public ReportCriteria getVehicleReportCriteria(Integer groupID, Duration duration, Locale locale, Boolean initDataSet) {
        this.locale = locale;
        Group group = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.VEHICLE_REPORT, group.getName(), locale);

        if (initDataSet) {
            if (duration.equals(Duration.TWELVE)) {
                Integer rowCount = reportDAO.getVehicleReportCount(groupID, null);
                PageParams pageParams = new PageParams(0, rowCount, null, null);
                reportCriteria.setMainDataset(reportDAO.getVehicleReportPage(groupID, pageParams));
            } else {
                reportCriteria.setMainDataset(scoreDAO.getVehicleReportData(groupID, duration, getGroupMap(group)));
            }
            reportCriteria.setDuration(duration);
        }

        return reportCriteria;
    }

    @Override
    public ReportCriteria getVehicleAdminReportCriteria(Integer groupID, Duration duration, Locale locale) {
        this.locale = locale;
        Group group = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.VEHICLE_ADMIN_REPORT, group.getName(), locale);


            if (duration.equals(Duration.TWELVE)) {
                Integer rowCount = reportDAO.getVehicleReportCount(groupID, null);
                PageParams pageParams = new PageParams(0, rowCount, null, null);
                reportCriteria.setMainDataset(reportDAO.getVehicleReportPage(groupID, pageParams));
            } else {
                reportCriteria.setMainDataset(scoreDAO.getVehicleReportData(groupID, duration, getGroupMap(group)));
            }
            reportCriteria.setDuration(duration);


        return reportCriteria;
    }

    @Override
    public ReportCriteria getTrailerReportCriteria(Integer groupID, Duration duration, Locale locale, Boolean initDataSet) {
        logger.warn("ReportCriteria getTrailerReportCriteria(Integer "+groupID+", Duration "+duration+", Locale "+locale+", Boolean "+initDataSet+")");
        this.locale = locale;
        Group group = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.TRAILER_REPORT, group.getName(), locale);
        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(groupID);
        Integer accountID = (group == null) ? 0 : group.getAccountID();
        if (initDataSet) {
            if (duration.equals(Duration.TWELVE)) {
                Integer rowCount = trailerReportDAO.getTrailerReportCount(accountID, groupIDList, null);
                PageParams pageParams = new PageParams(0, rowCount, null, null);
                reportCriteria.setMainDataset(trailerReportDAO.getTrailerReportItemByGroupPaging(accountID, groupIDList, pageParams));
            
                reportCriteria.setDuration(duration);
            }
        }

        return reportCriteria;
    }
    @Override
    public ReportCriteria getTrailerReportCriteria(Integer groupID, List<Integer> groupIDList, Duration duration, Locale locale, Boolean initDataSet) {
        this.locale = locale;
        Group group = groupDAO.findByID(groupID);
        Integer accountID = (group == null) ? 0 : group.getAccountID();

        ReportCriteria reportCriteria = new ReportCriteria(ReportType.TRAILER_REPORT, group.getName(), locale);

        if (initDataSet) {
            if (duration.equals(Duration.TWELVE)) {
                Integer rowCount = trailerReportDAO.getTrailerReportCount(accountID, groupIDList, null);
                PageParams pageParams = new PageParams(0, rowCount, null, null);
                reportCriteria.setMainDataset(trailerReportDAO.getTrailerReportItemByGroupPaging(accountID, groupIDList, pageParams));
            
                reportCriteria.setDuration(duration);
            }
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

    private List<ScoreableEntity> getScores(Integer groupID, Duration duration, GroupHierarchy gh) {
        List<ScoreableEntity> s = null;
        try {
            s = scoreDAO.getScores(groupID, duration, ScoreType.SCORE_OVERALL, gh);
        } catch (Exception e) {
            logger.debug("scoreDao error: " + e.getMessage());
        }

        return s;
    }

    @Override
    public ReportCriteria getDevicesReportCriteria(Integer groupID, Locale locale, Boolean initDataSet) {
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
    public ReportCriteria getIdlingReportCriteria(Integer groupID, Interval interval, Locale locale, Boolean initDataSet) {
        this.locale = locale;
        Group group = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.IDLING_REPORT, group.getName(), locale);
        DateTimeFormatter fmt = DateTimeFormat.forPattern(MessageUtil.getMessageString("dateFormat", getLocale()));
        reportCriteria.addParameter("BEGIN_DATE", fmt.print(interval.getStart()));
        reportCriteria.addParameter("END_DATE", fmt.print(interval.getEnd()));

        if (initDataSet) {
//            Integer rowCount = reportDAO.getIdlingReportCount(groupID, interval, null);
//            PageParams pageParams = new PageParams(0, rowCount, null, null);
//            reportCriteria.setMainDataset(reportDAO.getIdlingReportPage(groupID, interval, pageParams));
            reportCriteria.setMainDataset(reportIdlingDAO.getIdlingReportData(groupID, interval));
        }

        return reportCriteria;
    }

    @Override
    public ReportCriteria getIdlingVehicleReportCriteria(Integer groupID, Interval interval, Locale locale, Boolean initDataSet) {
        this.locale = locale;
        Group group = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.IDLING_VEHICLE_REPORT, group.getName(), locale);
        DateTimeFormatter fmt = DateTimeFormat.forPattern(MessageUtil.getMessageString("dateFormat", getLocale()));
        reportCriteria.addParameter("BEGIN_DATE", fmt.print(interval.getStart()));
        reportCriteria.addParameter("END_DATE", fmt.print(interval.getEnd()));

        if (initDataSet) {
            Integer rowCount = reportDAO.getIdlingVehicleReportCount(groupID, interval, null);
            PageParams pageParams = new PageParams(0, rowCount, null, null);
            reportCriteria.setMainDataset(reportDAO.getIdlingVehicleReportPage(groupID, interval, pageParams));
        }

        return reportCriteria;
    }

    @Override
    public ReportCriteria getEventsReportCriteria(Integer groupID, Locale locale) {
        Group tmpGroup = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.EVENT_REPORT, tmpGroup.getName(), locale);
        return reportCriteria;
    }

    @Override
    public ReportCriteria getRedFlagsReportCriteria(Integer groupID, Locale locale) {
        this.locale = locale;
        Group tmpGroup = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.RED_FLAG_REPORT, tmpGroup.getName(), locale);
        return reportCriteria;
    }

    @Override
    public ReportCriteria getWarningsReportCriteria(Integer groupID, Locale locale) {
        this.locale = locale;
        Group tmpGroup = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.WARNING_REPORT, tmpGroup.getName(), locale);
        return reportCriteria;
    }

    @Override
    public ReportCriteria getEmergencyReportCriteria(Integer groupID, Locale locale) {
        this.locale = locale;
        Group tmpGroup = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.EMERGENCY_REPORT, tmpGroup.getName(), locale);
        return reportCriteria;
    }

    @Override
    public ReportCriteria getZoneAlertsReportCriteria(Integer groupID, Locale locale) {
        this.locale = locale;
        // List<Event> eventList = eventDAO.getZoneAlertsForGroup(groupID,7);
        Group tmpGroup = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.EMERGENCY_REPORT, tmpGroup.getName(), locale);
        // reportCriteria.setMainDataset(eventList);
        return reportCriteria;
    }

    @Override
    public ReportCriteria getCrashHistoryReportCriteria(Integer groupID, Locale locale) {
        this.locale = locale;
        // List<Event> eventList = eventDAO.getZoneAlertsForGroup(groupID,7);
        Group tmpGroup = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.CRASH_HISTORY_REPORT, tmpGroup.getName(), locale);
        // reportCriteria.setMainDataset(eventList);
        return reportCriteria;
    }

    @Override
    public ReportCriteria getSpeedPercentageReportCriteria(Integer groupID, Duration duration, Locale locale, GroupHierarchy gh) {

        this.locale = locale;
        List<CategorySeriesData> barChartList = new ArrayList<CategorySeriesData>();
        List<CategorySeriesData> lineChartList = new ArrayList<CategorySeriesData>();
        List<SpeedPercentItem> speedPercentItemList = scoreDAO.getSpeedPercentItems(groupID, duration, gh);
        List<String> monthList = ReportUtil.createDateLabelList(speedPercentItemList, duration, MessageUtil.getMessageString("shortDateFormat", locale)/* "M/dd" */, locale);
        int index = 0;

        String distanceSeries = "driving";
        String speedingSeries = "speeding";
        String percentSeries = "percent";
        if (locale != null) {
            String prefix = "report.speedpercent.";
            distanceSeries = MessageUtil.getMessageString(prefix + distanceSeries, locale);
            speedingSeries = MessageUtil.getMessageString(prefix + speedingSeries, locale);
            percentSeries = MessageUtil.getMessageString(prefix + percentSeries, locale);
        }
        for (SpeedPercentItem speedItem : speedPercentItemList) {
            long distance = (speedItem.getMiles() == null) ? 0 : (speedItem.getMiles().longValue() / 100);
            long speeding = (speedItem.getMilesSpeeding() == null) ? 0 : (speedItem.getMilesSpeeding().longValue() / 100);
            float percent = ((distance == 0l) ? 0f : ((float) speeding / (float) distance));
            // logger.info(distance + " " + speeding + " " + percent);
            barChartList.add(new CategorySeriesData(speedingSeries, monthList.get(index), speeding, speedingSeries));
            barChartList.add(new CategorySeriesData(distanceSeries, monthList.get(index), (distance - speeding), distanceSeries));
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
    public ReportCriteria getIdlePercentageReportCriteria(Integer groupID, Duration duration, Locale locale, GroupHierarchy gh) {

        this.locale = locale;
        List<CategorySeriesData> barChartList = new ArrayList<CategorySeriesData>();
        List<CategorySeriesData> lineChartList = new ArrayList<CategorySeriesData>();
        List<IdlePercentItem> idlePercentItemList = scoreDAO.getIdlePercentItems(groupID, duration, gh);
        List<String> monthList = ReportUtil.createDateLabelList(idlePercentItemList, duration, MessageUtil.getMessageString("shortDateFormat", locale)/* "M/dd" */, locale);
        int index = 0;

        String drivingSeries = "driving";
        String idlingSeries = "idling";
        String percentSeries = "percent";
        if (locale != null) {
            String prefix = "report.idlepercent.";
            drivingSeries = MessageUtil.getMessageString(prefix + drivingSeries, locale);
            idlingSeries = MessageUtil.getMessageString(prefix + idlingSeries, locale);
            percentSeries = MessageUtil.getMessageString(prefix + percentSeries, locale);
        }
        Integer totalVehicles = 0;
        Integer totalEMUVehicles = 0;
        for (IdlePercentItem idleItem : idlePercentItemList) {
            long diff = idleItem.getDrivingTime() - idleItem.getIdlingTime();
            diff = (diff < 0l) ? 0l : diff;
            float driving = DateUtil.convertSecondsToHours(diff);
            float idling = DateUtil.convertSecondsToHours(idleItem.getIdlingTime());
            float percent = ((idleItem.getDrivingTime() == 0l) ? 0f : ((float) idleItem.getIdlingTime() / (float) idleItem.getDrivingTime()));
            // logger.info(driving + " " + idling + " " + percent);
            barChartList.add(new CategorySeriesData(idlingSeries, monthList.get(index), idling, idlingSeries));
            barChartList.add(new CategorySeriesData(drivingSeries, monthList.get(index), driving, drivingSeries));
            lineChartList.add(new CategorySeriesData(percentSeries, monthList.get(index), percent, percentSeries));
            // this ends up being the last count in the list that is > 0(i.e. last item)
            if (idleItem.getNumVehicles() != null && idleItem.getNumVehicles() > 0) {
                totalVehicles = idleItem.getNumVehicles();
                totalEMUVehicles = idleItem.getNumEMUVehicles();
            }

            index++;
        }

        Group group = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.IDLE_PERCENTAGE, group.getName(), locale);
        reportCriteria.addChartDataSet(barChartList);
        reportCriteria.addChartDataSet(lineChartList);
        reportCriteria.setDuration(duration);
        reportCriteria.addParameter("VEHICLE_STATS", MessageUtil.formatMessageString("report.idlepercent.vehicleStats", locale, new Object[] { totalEMUVehicles, totalVehicles }));
        return reportCriteria;
    }

    @Override
    public ReportCriteria getTeamStatisticsReportCriteria(Integer groupID, TimeFrame timeFrame, DateTimeZone timeZone, Locale locale, Boolean initDataSet, GroupHierarchy gh) {
        Group group = groupDAO.findByID(groupID);
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.TEAM_STATISTICS_REPORT, group.getName(), locale);
        reportCriteria.setTimeFrame(timeFrame);

        if (initDataSet) {
            List<DriverVehicleScoreWrapper> driverStatistics;

            if (timeFrame.equals(TimeFrame.WEEK) || timeFrame.equals(TimeFrame.MONTH) || timeFrame.equals(TimeFrame.YEAR))
                driverStatistics = groupReportDAO.getDriverScores(groupID, timeFrame.getAggregationDuration(), gh);
            else
                driverStatistics = groupReportDAO.getDriverScores(groupID, timeFrame.getInterval(timeZone), gh);
            DriverVehicleScoreWrapper totals = DriverVehicleScoreWrapper.summarize(driverStatistics, group);
            if (totals != null) {
                driverStatistics.add(totals);
            }
            reportCriteria.setMainDataset(driverStatistics);

        }
        return reportCriteria;
    }

    @Override
    public ReportCriteria getTeamStopsReportCriteria(Interval interval, GroupHierarchy accountGroupHierarchy, Integer driverID, TimeFrame timeFrame, DateTimeZone timeZone, Locale locale,
            DriverStopReport driverStopReport) {

        TeamStopsReportCriteria reportCriteria = new TeamStopsReportCriteria(locale);
        reportCriteria.setDriverDAO(driverDAO);
        reportCriteria.setReportAddressLookupBean(reportAddressLookupBean);
        Boolean active = true;
        reportCriteria.init(active, interval, accountGroupHierarchy, driverID, timeFrame, timeZone, driverStopReport);
        return reportCriteria;
    }

    @Override
    public ReportCriteria getTeamStopsReportCriteriaByGroup(Interval interval, GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, TimeFrame timeFrame, DateTimeZone timeZone, Locale locale) {
        TeamStopsReportCriteria reportCriteria = new TeamStopsReportCriteria(locale);
        reportCriteria.setDriverDAO(driverDAO);
        reportCriteria.setReportAddressLookupBean(reportAddressLookupBean);

        reportCriteria.init(interval, accountGroupHierarchy, groupIDList, timeFrame, timeZone);
        return reportCriteria;
    }
    @Override
    public List<ReportCriteria> getHosDailyDriverLogReportCriteria(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale, Boolean defaultUseMetric, DateTimeZone dateTimeZone) {
        return getHosDailyDriverLogReportCriteria(accountGroupHierarchy, driverID, interval, locale, defaultUseMetric, dateTimeZone, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
    }
    @Override
    public List<ReportCriteria> getHosDailyDriverLogReportCriteria(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale, Boolean defaultUseMetric, DateTimeZone dateTimeZone, boolean includeInactiveDrivers) {
        HosDailyDriverLogReportCriteria hosDailyDriverLogReportCriteria = new HosDailyDriverLogReportCriteria(locale, defaultUseMetric, dateTimeZone);
        hosDailyDriverLogReportCriteria.setDriverDAO(driverDAO);
        hosDailyDriverLogReportCriteria.setGroupDAO(groupDAO);
        hosDailyDriverLogReportCriteria.setAccountDAO(accountDAO);
        hosDailyDriverLogReportCriteria.setHosDAO(hosDAO);
        hosDailyDriverLogReportCriteria.setAddressDAO(addressDAO);
        hosDailyDriverLogReportCriteria.setUserDAO(userDAO);
        hosDailyDriverLogReportCriteria.setVehicleDAO(vehicleDAO);
        hosDailyDriverLogReportCriteria.setIncludeInactiveDrivers(includeInactiveDrivers);

        hosDailyDriverLogReportCriteria.init(accountGroupHierarchy, driverID, interval);
        return hosDailyDriverLogReportCriteria.getCriteriaList();
    }
    
    @Override
    public List<ReportCriteria> getHosDailyDriverLogReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, Boolean defaultUseMetric, DateTimeZone dateTimeZone) {
        return getHosDailyDriverLogReportCriteria(accountGroupHierarchy, groupIDList, interval, locale, defaultUseMetric, dateTimeZone, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
    }
    @Override
    public List<ReportCriteria> getHosDailyDriverLogReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, Boolean defaultUseMetric, DateTimeZone dateTimeZone, boolean includeInactiveDrivers) {
        HosDailyDriverLogReportCriteria hosDailyDriverLogReportCriteria = new HosDailyDriverLogReportCriteria(locale, defaultUseMetric, dateTimeZone);
        hosDailyDriverLogReportCriteria.setDriverDAO(driverDAO);
        hosDailyDriverLogReportCriteria.setGroupDAO(groupDAO);
        hosDailyDriverLogReportCriteria.setAccountDAO(accountDAO);
        hosDailyDriverLogReportCriteria.setHosDAO(hosDAO);
        hosDailyDriverLogReportCriteria.setAddressDAO(addressDAO);
        hosDailyDriverLogReportCriteria.setUserDAO(userDAO);
        hosDailyDriverLogReportCriteria.setVehicleDAO(vehicleDAO);
        hosDailyDriverLogReportCriteria.setIncludeInactiveDrivers(includeInactiveDrivers);

        hosDailyDriverLogReportCriteria.init(accountGroupHierarchy, groupIDList, interval);
        return hosDailyDriverLogReportCriteria.getCriteriaList();

    }

    @Override
    public ReportCriteria getHosViolationsSummaryReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale) {
        return getHosViolationsSummaryReportCriteria(accountGroupHierarchy, groupIDList, interval, locale, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
    }
    @Override
    public ReportCriteria getHosViolationsSummaryReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, boolean includeInactiveDrivers) {
        HosViolationsSummaryReportCriteria criteria = new HosViolationsSummaryReportCriteria(locale);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
        criteria.init(accountGroupHierarchy, groupIDList, interval);
        return criteria;
    }

    @Override
    public ReportCriteria getHosViolationsDetailReportCriteria(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale) {
        return getHosViolationsDetailReportCriteria(accountGroupHierarchy, driverID, interval, locale, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
    }
    
    @Override
    public ReportCriteria getHosViolationsDetailReportCriteria(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale, boolean includeInactiveDrivers) {

        HosViolationsDetailReportCriteria criteria = new HosViolationsDetailReportCriteria(locale);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
        criteria.init(accountGroupHierarchy, driverID, interval);
        return criteria;
    }
    
    @Override
    public ReportCriteria getHosViolationsDetailReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale) {
        return getHosViolationsDetailReportCriteria(accountGroupHierarchy, groupIDList, interval, locale, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
    }
    
    @Override
    public ReportCriteria getHosViolationsDetailReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, boolean includeInactiveDrivers ) {
        HosViolationsDetailReportCriteria criteria = new HosViolationsDetailReportCriteria(locale);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
        criteria.init(accountGroupHierarchy, groupIDList, interval);
        return criteria;
    }

    @Override
    public ReportCriteria getHosDriverDOTLogReportCriteria(Integer driverID, Interval interval, Locale locale) {
        return getHosDriverDOTLogReportCriteria(driverID, interval, locale, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
    }
    
    @Override
    public ReportCriteria getHosDriverDOTLogReportCriteria(Integer driverID, Interval interval, Locale locale, boolean includeInactiveDrivers) {
        HosDriverDOTLogReportCriteria criteria = new HosDriverDOTLogReportCriteria(locale);
        criteria.setDriverDAO(driverDAO);
        criteria.setHosDAO(hosDAO);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
        criteria.init(driverID, interval);
        return criteria;
    }

    @Override
    public ReportCriteria getDotHoursRemainingReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Locale locale) {
        return getDotHoursRemainingReportCriteria(accountGroupHierarchy, groupIDList, locale, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
    }
    
    @Override
    public ReportCriteria getDotHoursRemainingReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Locale locale, boolean includeInactiveDrivers) {
        DotHoursRemainingReportCriteria criteria = new DotHoursRemainingReportCriteria(locale);
        criteria.setGroupDAO(groupDAO);
        criteria.setDriverDAO(driverDAO);
        criteria.setHosDAO(hosDAO);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
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
        HosEditsReportCriteria criteria = new HosEditsReportCriteria(locale);
        criteria.setGroupDAO(groupDAO);
        criteria.setDriverDAO(driverDAO);
        criteria.setHosDAO(hosDAO);

        criteria.init(accountGroupHierarchy, groupIDList, interval);
        return criteria;
    }

    @Override
    public ReportCriteria getPayrollDetailReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale) {
        return getPayrollDetailReportCriteria(accountGroupHierarchy, groupIDList, interval, locale, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS, ReportCriteria.DEFAULT_INCLUDE_ZERO_MILES_DRIVERS );
    }
    
    @Override
    public ReportCriteria getPayrollDetailReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
        PayrollDetailReportCriteria criteria = new PayrollDetailReportCriteria(locale);
        criteria.setAccountDAO(accountDAO);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
        criteria.setAddressDAO(addressDAO);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
        criteria.setIncludeZeroMilesDrivers(includeZeroMilesDrivers);

        criteria.init(accountGroupHierarchy, groupIDList, interval);
        return criteria;
    }

    @Override
    public ReportCriteria getPayrollSignoffReportCriteria(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale) {
        return getPayrollSignoffReportCriteria(accountGroupHierarchy, driverID, interval, locale, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS, ReportCriteria.DEFAULT_INCLUDE_ZERO_MILES_DRIVERS);
    }
    
    @Override
    public ReportCriteria getPayrollSignoffReportCriteria(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
        PayrollSignoffReportCriteria criteria = new PayrollSignoffReportCriteria(locale);
        criteria.setAccountDAO(accountDAO);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
        criteria.setAddressDAO(addressDAO);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
        criteria.setIncludeZeroMilesDrivers(includeZeroMilesDrivers);

        criteria.init(accountGroupHierarchy, driverID, interval);
        return criteria;

    }

    public ReportCriteria getPayrollSignoffReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale) {
        return getPayrollSignoffReportCriteria(accountGroupHierarchy, groupIDList, interval, locale,  ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS, ReportCriteria.DEFAULT_INCLUDE_ZERO_MILES_DRIVERS);
    }
    
    @Override
    public ReportCriteria getPayrollSignoffReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
        PayrollSignoffReportCriteria criteria = new PayrollSignoffReportCriteria(locale);
        criteria.setAccountDAO(accountDAO);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
        criteria.setAddressDAO(addressDAO);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
        criteria.setIncludeZeroMilesDrivers(includeZeroMilesDrivers);
        
        criteria.init(accountGroupHierarchy, groupIDList, interval);
        return criteria;

    }

    @Override
    public ReportCriteria getPayrollSummaryReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale) {
        return getPayrollSummaryReportCriteria(accountGroupHierarchy, groupIDList, interval, locale, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
    }
    
    @Override
    public ReportCriteria getPayrollSummaryReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, boolean includeInactiveDrivers) {
        PayrollSummaryReportCriteria criteria = new PayrollSummaryReportCriteria(locale);
        criteria.setAccountDAO(accountDAO);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
        criteria.setAddressDAO(addressDAO);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
        criteria.init(accountGroupHierarchy, groupIDList, interval);
        return criteria;
    }

    @Override
    public ReportCriteria getPayrollCompensatedHoursReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale) {
        return getPayrollCompensatedHoursReportCriteria(accountGroupHierarchy, groupIDList, interval, locale, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
    }
    
    @Override
    public ReportCriteria getPayrollCompensatedHoursReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, boolean includeInactiveDrivers) {
        PayrollReportCompensatedHoursCriteria criteria = new PayrollReportCompensatedHoursCriteria(locale);
        criteria.setAccountDAO(accountDAO);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
        criteria.setAddressDAO(addressDAO);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);

        criteria.init(accountGroupHierarchy, groupIDList, interval);
        return criteria;
    }

    /**
     * Provide all data criteria including layout and data.
     * 
     * @param groupeID
     *            ID of the group chosen by the user
     * @param interval
     *            Interval chosen by the user
     * @param locale
     *            Local settings of the user - internationalization
     * @return criteria All report criteria including layout and data
     */
    @Override
    public ReportCriteria getTenHoursDayViolationsCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, Interval interval, Locale locale) {
        return getTenHoursDayViolationsCriteria(accountGroupHierarchy, groupID, interval, locale, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS, ReportCriteria.DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS);
    }
    
    @Override
    public ReportCriteria getTenHoursDayViolationsCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, Interval interval, Locale locale, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
        TenHoursViolationReportCriteria criteria = new TenHoursViolationReportCriteria(locale);
        criteria.setDriverDAO(driverDAO);
        criteria.setDriveTimeDAO(driveTimeDAO);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
        criteria.setIncludeZeroMilesDrivers(includeZeroMilesDrivers);

        criteria.init(accountGroupHierarchy, groupID, interval);
        return criteria;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.reports.service.ReportCriteriaService#getDriverHoursReportCriteria(Integer, org.joda.time.Interval, java.util.Locale)
     */
    @Override
    public ReportCriteria getDriverHoursReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale) {
        return getDriverHoursReportCriteria(accountGroupHierarchy, groupIDList, interval, locale, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS, ReportCriteria.DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS);
    }
    
    @Override
    public ReportCriteria getDriverHoursReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
        DriverHoursReportCriteria criteria = new DriverHoursReportCriteria(locale);
        criteria.setAccountDAO(accountDAO);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
        criteria.init(accountGroupHierarchy, groupIDList, interval);
        
        return criteria;
    }
    
    @Override
    public ReportCriteria getThirtyMinuteBreaksReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale local) {
        return getThirtyMinuteBreaksReportCriteria(accountGroupHierarchy, groupIDList, interval, locale, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
        
    }
    
    @Override
    public ReportCriteria getThirtyMinuteBreaksReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, boolean includeInactiveDrivers) {
        ThirtyMinuteBreaksReportCriteria criteria = new ThirtyMinuteBreaksReportCriteria(locale);
        criteria.setAccountDAO(accountDAO);
        criteria.setDriverDAO(driverDAO);
        criteria.setHosDAO(hosDAO);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
        criteria.init(accountGroupHierarchy, groupIDList, interval);
        return criteria;
    }

    /**
     * Provide all data criteria including layout and data.
     * 
     * @param id
     *            ID of the group chosen by the user
     * @param interval
     *            Interval chosen by the user
     * @param locale
     *            Local settings of the user - internationalization
     * @param group
     *            Indicating if type of ID is group or driver
     * @return criteria All report criteria including layout and data
     */
    @Override
    public ReportCriteria getVehicleUsageReportCriteria(Integer id, Interval interval, Locale locale, boolean group) {
        VehicleUsageReportCriteria criteria = new VehicleUsageReportCriteria(locale);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setWaysmartDAO(waysmartDAO);

        criteria.init(id, interval, group);
        return criteria;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.reports.service.ReportCriteriaService#getMileageByVehicleReportCriteria(Integer, org.joda.time.Interval, java.util.Locale, boolean)
     */
    @Override
    public ReportCriteria getMileageByVehicleReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, MeasurementType measurementType,
            boolean iftaOnly) {
        MileageByVehicleReportCriteria criteria = new MileageByVehicleReportCriteria(locale);
        criteria.setGroupDAO(groupDAO);
        criteria.setStateMileageDAO(stateMileageDAO);
        criteria.setMeasurementType(measurementType);

        criteria.init(accountGroupHierarchy, groupIDList, interval, iftaOnly);
        return criteria;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.reports.service.ReportCriteriaService#getMileageByVehicleReportCriteria(Integer, org.joda.time.Interval, java.util.Locale, boolean)
     */
    @Override
    public ReportCriteria getStateMileageByVehicleRoadStatusReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, MeasurementType type,
            boolean iftaOnly) {
        StateMileageByVehicleRoadStatusReportCriteria criteria = new StateMileageByVehicleRoadStatusReportCriteria(locale);
        criteria.setGroupDAO(groupDAO);
        criteria.setStateMileageDAO(stateMileageDAO);
        criteria.setMeasurementType(type);
        criteria.init(accountGroupHierarchy, groupIDList, interval, iftaOnly);
        return criteria;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.reports.service.ReportCriteriaService#getStateMileageByVehicleReportCriteria(java.util.List, org.joda.time.Interval, java.util.Locale, boolean)
     */
    @Override
    public ReportCriteria getStateMileageByVehicleReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, MeasurementType type,
            boolean iftaOnly) {
        StateMileageByVehicleReportCriteria criteria = new StateMileageByVehicleReportCriteria(locale);
        criteria.setGroupDAO(groupDAO);
        criteria.setStateMileageDAO(stateMileageDAO);
        criteria.setMeasurementType(type);
        criteria.init(accountGroupHierarchy, groupIDList, interval, iftaOnly);
        return criteria;
    }

    @Override
    public ReportCriteria getStateMileageFuelByVehicleReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, MeasurementType type,
            boolean iftaOnly) {
        StateMileageFuelByVehicleReportCriteria criteria = new StateMileageFuelByVehicleReportCriteria(locale);
        criteria.setGroupDAO(groupDAO);
        criteria.setStateMileageDAO(stateMileageDAO);
        criteria.setMeasurementType(type);
        criteria.init(accountGroupHierarchy, groupIDList, interval, iftaOnly);
        return criteria;
    }

    @Override
    public ReportCriteria getStateMileageCompareByGroupReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, MeasurementType type,
            boolean iftaOnly) {
        StateMileageCompareByGroupReportCriteria criteria = new StateMileageCompareByGroupReportCriteria(locale);
        criteria.setGroupDAO(groupDAO);
        criteria.setStateMileageDAO(stateMileageDAO);

        criteria.setMeasurementType(type);
        criteria.init(accountGroupHierarchy, groupIDList, interval, iftaOnly);
        return criteria;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.reports.service.ReportCriteriaService#getStateMileageByVehicleReportCriteria(java.util.List, org.joda.time.Interval, java.util.Locale, boolean)
     */
    @Override
    public ReportCriteria getStateMileageByMonthReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, MeasurementType type, boolean iftaOnly) {
        StateMileageByMonthReportCriteria criteria = new StateMileageByMonthReportCriteria(locale);
        criteria.setGroupDAO(groupDAO);
        criteria.setStateMileageDAO(stateMileageDAO);
        criteria.setMeasurementType(type);
        criteria.init(accountGroupHierarchy, groupIDList, interval, iftaOnly);
        return criteria;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.reports.service.ReportCriteriaService#getWarrantyListReportCriteria(Integer, java.util.Locale, boolean)
     */
    @Override
    public ReportCriteria getWarrantyListReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, Integer accountID, String accountName, Locale locale, boolean expiredOnly) {
        WarrantyListReportCriteria criteria = new WarrantyListReportCriteria(locale);
        criteria.setGroupDAO(groupDAO);
        criteria.setWaysmartDAO(waysmartDAO);
        criteria.init(accountGroupHierarchy, groupID, accountID, accountName, expiredOnly);
        return criteria;
    }

    /* Mileage */
    @Override
    public ReportCriteria getStateMileageByVehicleReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, Interval interval, Locale locale, MeasurementType measurementType) {
        StateMileageByVehicleReportCriteria criteria = new StateMileageByVehicleReportCriteria(locale);
        criteria.setGroupDAO(groupDAO);
        criteria.setStateMileageDAO(stateMileageDAO);
        criteria.setMeasurementType(measurementType);
        List<Integer> groupIDs = accountGroupHierarchy.getGroupIDList(groupID);
        criteria.init(accountGroupHierarchy, groupIDs, interval, false, true);
        return criteria;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO) {
        this.scoreDAO = scoreDAO;
    }

    public ScoreDAO getScoreDAO() {
        return scoreDAO;
    }

    public void setMpgDAO(MpgDAO mpgDAO) {
        this.mpgDAO = mpgDAO;
    }

    public MpgDAO getMpgDAO() {
        return mpgDAO;
    }

    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public DeviceDAO getDeviceDAO() {
        return deviceDAO;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    public EventDAO getEventDAO() {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public void setRedFlagDAO(RedFlagDAO redFlagDAO) {
        this.redFlagDAO = redFlagDAO;
    }

    public RedFlagDAO getRedFlagDAO() {
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

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public HOSDAO getHosDAO() {
        return hosDAO;
    }

    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }

    public ConfiguratorDAO getConfiguratorJDBCDAO() {
           return configuratorJDBCDAO;
    }

    public void setConfiguratorJDBCDAO(ConfiguratorDAO configuratorDAO) {
           this.configuratorJDBCDAO = configuratorDAO;
    }

    /**
     * Setter for WaysmartDAO.
     * 
     * @param waysmartDAO
     *            the DAO to set
     */
    public void setWaysmartDAO(WaysmartDAO waysmartDAO) {
        this.waysmartDAO = waysmartDAO;
    }

    /**
     * Getter for WaysmartDAO.
     * 
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
     * 
     * @param stateMileageDAO
     *            the stateMileageDAO to set
     */
    public void setStateMileageDAO(StateMileageDAO stateMileageDAO) {
        this.stateMileageDAO = stateMileageDAO;
    }

    /**
     * The stateMileageDAO getter.
     * 
     * @return the stateMileageDAO
     */
    public StateMileageDAO getStateMileageDAO() {
        return stateMileageDAO;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
    
    @Override
    public ReportCriteria getDrivingTimeViolationsDetailReportCriteria(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale) {
        return getDrivingTimeViolationsDetailReportCriteria(accountGroupHierarchy, driverID, interval, locale, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
    }
    
    @Override
    public ReportCriteria getDrivingTimeViolationsDetailReportCriteria(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale, boolean includeInactiveDrivers) {
        DrivingTimeViolationsDetailReportCriteria criteria = new DrivingTimeViolationsDetailReportCriteria(locale);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
        criteria.init(accountGroupHierarchy, driverID, interval);
        return criteria;
    }
    
    @Override
    public ReportCriteria getDrivingTimeViolationsDetailReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale) {
        return getDrivingTimeViolationsDetailReportCriteria(accountGroupHierarchy, groupIDList, interval, locale, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
    }
    
    @Override
    public ReportCriteria getDrivingTimeViolationsDetailReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, boolean includeInactiveDrivers) {
        DrivingTimeViolationsDetailReportCriteria criteria = new DrivingTimeViolationsDetailReportCriteria(locale);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
        criteria.init(accountGroupHierarchy, groupIDList, interval);
        return criteria;
    }
    
    @Override
    public ReportCriteria getDrivingTimeViolationsSummaryReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale) {
        return getDrivingTimeViolationsSummaryReportCriteria(accountGroupHierarchy, groupIDList, interval, locale, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
    }
    
    @Override
    public ReportCriteria getDrivingTimeViolationsSummaryReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, boolean includeInactiveDrivers) {
        DrivingTimeViolationsSummaryReportCriteria criteria = new DrivingTimeViolationsSummaryReportCriteria(locale);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
        criteria.init(accountGroupHierarchy, groupIDList, interval);
        return criteria;
    }
    
    @Override
    public ReportCriteria getNonDOTViolationsDetailReportCriteria(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale) {
        return getNonDOTViolationsDetailReportCriteria(accountGroupHierarchy, driverID, interval, locale, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
    }
    
    @Override
    public ReportCriteria getNonDOTViolationsDetailReportCriteria(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale, boolean includeInactiveDrivers) {
        NonDOTViolationsDetailReportCriteria criteria = new NonDOTViolationsDetailReportCriteria(locale);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
        criteria.init(accountGroupHierarchy, driverID, interval);
        return criteria;
    }
    
    @Override
    public ReportCriteria getNonDOTViolationsDetailReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale) {
        return getNonDOTViolationsDetailReportCriteria(accountGroupHierarchy, groupIDList, interval, locale, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
    }
    
    @Override
    public ReportCriteria getNonDOTViolationsDetailReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, boolean includeInactiveDrivers) {
        NonDOTViolationsDetailReportCriteria criteria = new NonDOTViolationsDetailReportCriteria(locale);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
        criteria.init(accountGroupHierarchy, groupIDList, interval);
        return criteria;
    }
    
    @Override
    public ReportCriteria getNonDOTViolationsSummaryReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale) {
        return getNonDOTViolationsSummaryReportCriteria(accountGroupHierarchy, groupIDList, interval, locale, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
    }
    
    @Override
    public ReportCriteria getNonDOTViolationsSummaryReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, boolean includeInactiveDrivers) {
        NonDOTViolationsSummaryReportCriteria criteria = new NonDOTViolationsSummaryReportCriteria(locale);
        criteria.setDriverDAO(driverDAO);
        criteria.setGroupDAO(groupDAO);
        criteria.setHosDAO(hosDAO);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
        criteria.init(accountGroupHierarchy, groupIDList, interval);
        return criteria;
    }

    @Override
    public List<ReportCriteria> getDriverPerformanceIndividualReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, List<Integer> driverIDList, Interval interval, Locale locale,
            Boolean ryg, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
        DriverPerformanceReportCriteria criteria = new DriverPerformanceReportCriteria(ReportType.DRIVER_PERFORMANCE_INDIVIDUAL, locale);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
        criteria.setIncludeZeroMilesDrivers(includeZeroMilesDrivers);
        criteria.setDriverPerformanceDAO(driverPerformanceDAO);
        criteria.init(accountGroupHierarchy, groupID, driverIDList, interval, ryg);
        return criteria.getCriteriaList();
    }
    @Override
    public List<ReportCriteria> getDriverPerformanceIndividualReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, List<Integer> driverIDList, Interval interval, Locale locale,
            Boolean ryg) {
        return getDriverPerformanceIndividualReportCriteria(accountGroupHierarchy, groupID, driverIDList, interval, locale, ryg, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS, ReportCriteria.DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS);
    }

    @Override
    public ReportCriteria getDriverPerformanceReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, Interval interval, Locale locale, Boolean ryg){
        return getDriverPerformanceReportCriteria(accountGroupHierarchy, groupID, interval, locale, ryg, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS, ReportCriteria.DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS);
    }
    @Override
    public ReportCriteria getDriverPerformanceReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, Interval interval, Locale locale, Boolean ryg, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
        DriverPerformanceReportCriteria criteria = new DriverPerformanceReportCriteria(ReportType.DRIVER_PERFORMANCE_TEAM, locale);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
        criteria.setIncludeZeroMilesDrivers(includeZeroMilesDrivers);
        criteria.setDriverPerformanceDAO(driverPerformanceDAO);
        criteria.init(accountGroupHierarchy, groupID, interval, ryg);
        return criteria;
    }

    public ReportCriteria getDriverPerformanceKeyMetricsReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale,
            MeasurementType measurementType) {
        return getDriverPerformanceKeyMetricsReportCriteria(accountGroupHierarchy, groupIDList, null, interval, locale, measurementType);
    }

    @Override
    public ReportCriteria getDriverPerformanceKeyMetricsReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, TimeFrame timeFrame, Interval interval, Locale locale,
            MeasurementType measurementType) {
        return getDriverPerformanceKeyMetricsReportCriteria(accountGroupHierarchy, groupIDList, timeFrame, interval, locale, measurementType, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS,
                ReportCriteria.DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS);
    }

    @Override
    public ReportCriteria getDriverPerformanceKeyMetricsReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, TimeFrame timeFrame, Interval interval, Locale locale,
            MeasurementType measurementType, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
        DriverPerformanceKeyMetricsReportCriteria criteria = new DriverPerformanceKeyMetricsReportCriteria(ReportType.DRIVER_PERFORMANCE_KEY_METRICS, locale);
        criteria.setDriverPerformanceDAO(driverPerformanceDAO);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
        criteria.setIncludeZeroMilesDrivers(includeZeroMilesDrivers);
        if (timeFrame != null && !timeFrame.equals(TimeFrame.CUSTOM_RANGE))
            criteria.init(accountGroupHierarchy, groupIDList, timeFrame, measurementType);
        else
            criteria.init(accountGroupHierarchy, groupIDList, interval, measurementType);
        return criteria;
    }

    @Override
    public ReportCriteria getDriverPerformanceKeyMetricsTimeFrameReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, TimeFrame timeFrame, Interval interval, Locale locale,
            MeasurementType measurementType) {
        return getDriverPerformanceKeyMetricsTimeFrameReportCriteria(accountGroupHierarchy, groupIDList, timeFrame, interval, locale, measurementType, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS, ReportCriteria.DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS);
    }
    @Override
    public ReportCriteria getDriverPerformanceKeyMetricsTimeFrameReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, TimeFrame timeFrame, Interval interval, Locale locale,
            MeasurementType measurementType, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
        DriverPerformanceKeyMetricsReportCriteria criteria = new DriverPerformanceKeyMetricsReportCriteria(ReportType.DRIVER_PERFORMANCE_KEY_METRICS_TF_RYG, locale);
        criteria.setDriverPerformanceDAO(driverPerformanceDAO);
        criteria.setIncludeInactiveDrivers(includeInactiveDrivers);
        criteria.setIncludeZeroMilesDrivers(includeZeroMilesDrivers);
        if (timeFrame != null && !timeFrame.equals(TimeFrame.CUSTOM_RANGE))
            criteria.init(accountGroupHierarchy, groupIDList, timeFrame, measurementType);
        else
            criteria.init(accountGroupHierarchy, groupIDList, interval, measurementType);
        return criteria;
    }

    @Override
    public ReportCriteria getDriverCoachingReportCriteriaByDriver(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale, DateTimeZone timeZone) {
        return getDriverCoachingReportCriteriaByDriver(accountGroupHierarchy, driverID, interval, locale, timeZone, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS, ReportCriteria.DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS);
    }

    @Override
    public ReportCriteria getDriverCoachingScoreReportCriteriaByDriver(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale, DateTimeZone timeZone) {
        return getDriverCoachingScoreReportCriteriaByDriver(accountGroupHierarchy, driverID, interval, locale, timeZone, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS, ReportCriteria.DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS);
    }
    @Override
    public ReportCriteria getDriverCoachingReportCriteriaByDriver(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale, DateTimeZone timeZone, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
        DriverCoachingReportCriteria.Builder builder = new DriverCoachingReportCriteria.Builder(groupReportDAO, driverPerformanceDAO, driverDAO, driverID, interval, includeInactiveDrivers, includeZeroMilesDrivers);
        builder.setDateTimeZone(timeZone);
        builder.setLocale(locale);
        builder.setGroupHierarchy(accountGroupHierarchy);
        builder.setAccountHOSEnabled(isAccountHOSEnabled(accountGroupHierarchy.getGroupList().get(0).getAccountID()));
        builder.setHosDAO(getHosDAO());
        return builder.buildSingle();
    }
    @Override
    public ReportCriteria getDriverCoachingScoreReportCriteriaByDriver(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale, DateTimeZone timeZone, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
        DriverCoachingScoreReportCriteria.Builder builder = new DriverCoachingScoreReportCriteria.Builder(groupReportDAO, driverPerformanceDAO, driverDAO, driverID, interval, includeInactiveDrivers, includeZeroMilesDrivers);
        builder.setDateTimeZone(timeZone);
        builder.setLocale(locale);
        builder.setGroupHierarchy(accountGroupHierarchy);
        builder.setAccountHOSEnabled(isAccountHOSEnabled(accountGroupHierarchy.getGroupList().get(0).getAccountID()));
        builder.setHosDAO(getHosDAO());
        return builder.buildSingle();
    }
    
    private boolean isAccountHOSEnabled(Integer accountID) {
        return accountDAO.findByID(accountID).getHos() == AccountHOSType.HOS_SUPPORT;
    }
    @Override
    public List<ReportCriteria> getDriverCoachingReportCriteriaByGroup(GroupHierarchy accountGroupHierarchy, Integer groupID, Interval interval, Locale locale, DateTimeZone timeZone,
            boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
        DriverCoachingReportCriteria.Builder builder = new DriverCoachingReportCriteria.Builder(groupReportDAO, driverPerformanceDAO, groupID, interval, includeInactiveDrivers, includeZeroMilesDrivers);
        builder.setDateTimeZone(timeZone);
        builder.setLocale(locale);
        builder.setGroupHierarchy(accountGroupHierarchy);
        builder.setAccountHOSEnabled(isAccountHOSEnabled(accountGroupHierarchy.getGroupList().get(0).getAccountID()));
        builder.setHosDAO(getHosDAO());
        builder.setDriverDAO(driverDAO);
        return builder.build();
    }
    @Override
    public List<ReportCriteria> getDriverCoachingScoreReportCriteriaByGroup(GroupHierarchy accountGroupHierarchy, Integer groupID, Interval interval, Locale locale, DateTimeZone timeZone,
            boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
        DriverCoachingScoreReportCriteria.Builder builder = new DriverCoachingScoreReportCriteria.Builder(groupReportDAO, driverPerformanceDAO, groupID, interval, includeInactiveDrivers, includeZeroMilesDrivers);
        builder.setDateTimeZone(timeZone);
        builder.setLocale(locale);
        builder.setGroupHierarchy(accountGroupHierarchy);
        builder.setAccountHOSEnabled(isAccountHOSEnabled(accountGroupHierarchy.getGroupList().get(0).getAccountID()));
        builder.setHosDAO(getHosDAO());
        builder.setDriverDAO(driverDAO);
        return builder.build();
    }
    @Override
    public List<ReportCriteria> getDriverCoachingReportCriteriaByGroup(GroupHierarchy accountGroupHierarchy, Integer groupID, Interval interval, Locale locale, DateTimeZone timeZone) {
        return getDriverCoachingReportCriteriaByGroup(accountGroupHierarchy, groupID, interval, locale, timeZone, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS, ReportCriteria.DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS);
    }
    @Override
    public List<ReportCriteria> getDriverCoachingScoreReportCriteriaByGroup(GroupHierarchy accountGroupHierarchy, Integer groupID, Interval interval, Locale locale, DateTimeZone timeZone) {
        return getDriverCoachingScoreReportCriteriaByGroup(accountGroupHierarchy, groupID, interval, locale, timeZone, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS, ReportCriteria.DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS);
    }

    @Override
    public ReportCriteria getDriverExcludedViolationCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, Interval interval, Locale locale, DateTimeZone timeZone) {
        return getDriverExcludedViolationCriteria(accountGroupHierarchy, groupID, interval, locale, timeZone, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS, ReportCriteria.DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS);
    }
    
    @Override
    public ReportCriteria getDriverExcludedViolationCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, Interval interval, Locale locale, DateTimeZone timeZone, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
        // Load the group id list for the parent group and it's children
        List<Integer> groupIDs = accountGroupHierarchy.getGroupIDList(groupID);
        DriverExcludedViolationsCriteria.Builder builder = new DriverExcludedViolationsCriteria.Builder(accountGroupHierarchy, eventAggregationDAO, groupDAO, driverDAO, groupIDs, interval, includeInactiveDrivers, includeZeroMilesDrivers);
        builder.setLocale(locale);
        builder.setDateTimeZone(timeZone);
        return builder.build();
    }

    /* Communication */
    @Override
    public ReportCriteria getNonCommReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, TimeFrame timeFrame, Locale locale, DateTimeZone timeZone) {
        List<Integer> groupIDs = accountGroupHierarchy.getGroupIDList(groupID);
        NonCommReportCriteria.Builder builder = new NonCommReportCriteria.Builder(accountGroupHierarchy, eventAggregationDAO, groupIDs, timeFrame);
        builder.setLocale(locale);
        builder.setDateTimeZone(timeZone);
        return builder.build();
    }
    @Override
    public ReportCriteria getSeatbeltClicksReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, TimeFrame timeFrame,Interval interval, Locale locale, DateTimeZone timeZone, MeasurementType measurementType) {
        return getSeatbeltClicksReportCriteria(accountGroupHierarchy, groupID, timeFrame, interval, locale, timeZone, measurementType, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS, ReportCriteria.DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS);
        
    }
    @Override
    public ReportCriteria getSeatbeltClicksReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, TimeFrame timeFrame,Interval interval, Locale locale, DateTimeZone timeZone, MeasurementType measurementType, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
        SeatbeltClicksReportCriteria.Builder builder = new SeatbeltClicksReportCriteria.Builder(accountGroupHierarchy, groupReportDAO, groupID, timeFrame,interval, measurementType, includeInactiveDrivers, includeZeroMilesDrivers);
        builder.setLocale(locale);
        builder.setDateTimeZone(timeZone);
        return builder.build();
    }

    /* Forms - DVIR */
    @Override
    public ReportCriteria getDVIRPreTripReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, TimeFrame timeFrame, Locale locale, DateTimeZone timeZone) {
        return new DVIRPreTripReportCriteria(locale).build(accountGroupHierarchy, formsDAO, groupID, timeFrame);
    }

    @Override
    public ReportCriteria getDVIRPostTripReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, TimeFrame timeFrame, Locale locale, DateTimeZone timeZone) {
        return new DVIRPostTripReportCriteria(locale).build(accountGroupHierarchy, formsDAO, groupID, timeFrame);
    }
    
    /* DVIR */
    @Override
    public ReportCriteria getDVIRViolationReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, TimeFrame timeFrame, Locale locale, DateTimeZone timeZone) {
        List<Integer> groupIDs = accountGroupHierarchy.getGroupIDList(groupID);
        DVIRViolationReportCriteria.Builder builder = new DVIRViolationReportCriteria.Builder(accountGroupHierarchy, groupID, dvirViolationReportDAO, groupIDs, timeFrame); // accountGroupHierarchy
        
        builder.setLocale(locale);
        builder.setDateTimeZone(timeZone);
        return builder.build();
    }
    
    @Override
    public ReportCriteria getDVIRInspectionRepairCompleteCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, TimeFrame timeFrame, Locale locale, DateTimeZone timeZone, Boolean isDetailed) {
        List<Integer> groupIDs = accountGroupHierarchy.getGroupIDList(groupID);
        DVIRInspectionRepairCompleteCriteria.Builder builder = new DVIRInspectionRepairCompleteCriteria.Builder(accountGroupHierarchy, groupID, dvirInspectionRepairReportDAO, driverDAO, formsDAO, groupIDs, timeFrame, isDetailed);
        
        builder.setLocale(locale);
        builder.setDateTimeZone(timeZone);
        return builder.build();
    }


    
    @Override
    public ReportCriteria getBackingReportCriteria(Interval interval, GroupHierarchy accountGroupHierarchy, Integer groupID, TimeFrame timeFrame, Locale locale, DateTimeZone timeZone, MeasurementType measurementType) {
        return getBackingReportCriteria(interval, accountGroupHierarchy, groupID, timeFrame, locale, timeZone, measurementType, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS, ReportCriteria.DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS);
        
    }
    @Override
    public ReportCriteria getBackingReportCriteria(Interval interval, GroupHierarchy accountGroupHierarchy, Integer groupID, TimeFrame timeFrame, Locale locale, DateTimeZone timeZone, MeasurementType measurementType, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
        BackingReportCriteria.Builder builder = new BackingReportCriteria.Builder(interval, accountGroupHierarchy, groupReportDAO, groupID, timeFrame, measurementType, includeInactiveDrivers, includeZeroMilesDrivers);
        builder.setLocale(locale);
        builder.setDateTimeZone(timeZone);
        return builder.build();
    }


    @Override
    public ReportCriteria getMaintenanceEventsReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, DateTimeZone timeZone, MeasurementType measurementType) {
        MaintenanceEventsReportCriteria.Builder builder = new MaintenanceEventsReportCriteria.Builder(accountGroupHierarchy, groupReportDAO, groupDAO, vehicleDAO, eventDAO, groupIDList, interval, measurementType, configuratorJDBCDAO, driveTimeDAO);
        builder.setLocale(locale);
        builder.setDateTimeZone(timeZone);
        return builder.build();
    }



    @Override
    public ReportCriteria getMaintenanceIntervalReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, DateTimeZone timeZone, MeasurementType measurementType) {
        MaintenanceIntervalReportCriteria.Builder builder = new MaintenanceIntervalReportCriteria.Builder(accountGroupHierarchy, groupReportDAO, groupDAO, vehicleDAO, eventDAO, groupIDList, interval, measurementType, configuratorJDBCDAO, driveTimeDAO);
        builder.setLocale(locale);
        builder.setDateTimeZone(timeZone);
        return builder.build();
    }

    public DriveTimeDAO getDriveTimeDAO() {
        return driveTimeDAO;
    }

    public void setDriveTimeDAO(DriveTimeDAO driveTimeDAO) {
        this.driveTimeDAO = driveTimeDAO;
    }

    public DriverPerformanceDAO getDriverPerformanceDAO() {
        return driverPerformanceDAO;
    }

    public void setDriverPerformanceDAO(DriverPerformanceDAO driverPerformanceDAO) {
        this.driverPerformanceDAO = driverPerformanceDAO;
    }

    public ReportAddressLookupBean getReportAddressLookupBean() {
        return reportAddressLookupBean;
    }

    public void setReportAddressLookupBean(ReportAddressLookupBean reportAddressLookupBean) {
        this.reportAddressLookupBean = reportAddressLookupBean;
    }

    public void setEventAggregationDAO(EventAggregationDAO eventAggregationDAO) {
        this.eventAggregationDAO = eventAggregationDAO;
    }

    public EventAggregationDAO getEventAggregationDAO() {
        return eventAggregationDAO;
    }

    public List<ReportCriteria> getReportCriteria(ReportSchedule reportSchedule, GroupHierarchy groupHierarchy, Person person) {
        if (person.getLocale() == null)
            person.setLocale(Locale.ENGLISH);

        ReportGroup reportGroup = ReportGroup.valueOf(reportSchedule.getReportID());
        if (reportGroup == null) {
            logger.error("null reportGroup for schedule ID " + reportSchedule.getReportID());
            return null;
        }
        List<ReportCriteria> reportCriteriaList = new ArrayList<ReportCriteria>();
        for (int i = 0; i < reportGroup.getReports().length; i++) {
            Duration duration = reportSchedule.getReportDuration();
            if (duration == null) {
                duration = Duration.DAYS;
            }
            TimeFrame timeFrame = reportSchedule.getReportTimeFrame();
            if (timeFrame == null) {
                timeFrame = TimeFrame.TODAY;
            }
            if (reportGroup.getEntityType() == EntityType.ENTITY_GROUP && reportSchedule.getGroupID() == null) {
                logger.error("no group id specified so skipping report id: " + reportSchedule.getReportScheduleID());
                continue;
            }
            switch (reportGroup.getReports()[i]) {
                case FIRST_MOVE_FORWARD_REPORT:
                    reportCriteriaList.add(getFirstMoveForwardCriteria(timeFrame.getInterval(),
                            groupHierarchy,
                            reportSchedule.getGroupID(),
                            timeFrame,
                            person.getLocale(),
                            DateTimeZone.forID(person.getTimeZone().getID()),
                            person.getMeasurementType(),
                            reportSchedule.getIncludeInactiveDrivers(),
                            reportSchedule.getIncludeZeroMilesDrivers()));
                    break;
                case SEATBELT_CLICKS_REPORT:
                    reportCriteriaList.add(getSeatbeltClicksReportCriteria(groupHierarchy,
                                                                           reportSchedule.getGroupID(),
                                                                           timeFrame,
                                                                           timeFrame.getInterval(),
                                                                           person.getLocale(),
                                                                           DateTimeZone.forID(person.getTimeZone().getID()),
                                                                           person.getMeasurementType(),
                                                                           reportSchedule.getIncludeInactiveDrivers(),
                                                                           reportSchedule.getIncludeZeroMilesDrivers()  )                                                                          
                                           );
                    break;
                case OVERALL_SCORE:
                    reportCriteriaList.add(getOverallScoreReportCriteria(reportSchedule.getGroupID(), duration, person.getLocale(), groupHierarchy));
                    break;
                case TREND:
                    reportCriteriaList.add(getTrendChartReportCriteria(reportSchedule.getGroupID(), duration, person.getLocale(), groupHierarchy));
                    break;
                case MPG_GROUP:
                    reportCriteriaList.add(getMpgReportCriteria(reportSchedule.getGroupID(), duration, person.getLocale(), groupHierarchy));
                    break;
                case DEVICES_REPORT:
                    reportCriteriaList.add(getDevicesReportCriteria(reportSchedule.getGroupID(), person.getLocale(), true));
                    break;
                case DRIVER_REPORT:
                    reportCriteriaList.add(getDriverReportCriteria(reportSchedule.getGroupID(), duration, person.getLocale(), true));
                    break;
                case VEHICLE_REPORT:
                    reportCriteriaList.add(getVehicleReportCriteria(reportSchedule.getGroupID(), duration, person.getLocale(), true));
                    break;
                case IDLING_REPORT:
                    DateTimeZone dateTimeZone = DateTimeZone.forID(person.getTimeZone().getID());
                    Interval interval = new Interval(new DateMidnight(new DateTime().minusWeeks(1), dateTimeZone), new DateMidnight(new DateTime(), dateTimeZone).toDateTime().plusDays(1)
                            .minus(ONE_MINUTE));
                    reportCriteriaList.add(getIdlingReportCriteria(reportSchedule.getGroupID(), interval, person.getLocale(), true));
                    break;
                case IDLING_VEHICLE_REPORT:
                    DateTimeZone vDateTimeZone = DateTimeZone.forID(person.getTimeZone().getID());
                    Interval vInterval = new Interval(new DateMidnight(new DateTime().minusWeeks(1), vDateTimeZone), new DateMidnight(new DateTime(), vDateTimeZone).toDateTime().plusDays(1)
                            .minus(ONE_MINUTE));
                    reportCriteriaList.add(getIdlingVehicleReportCriteria(reportSchedule.getGroupID(), vInterval, person.getLocale(), true));
                    break;
                case TEAM_STATISTICS_REPORT:
                    reportCriteriaList.add(getTeamStatisticsReportCriteria(reportSchedule.getGroupID(), timeFrame, DateTimeZone.forTimeZone(person.getTimeZone()), person.getLocale(), true,
                            groupHierarchy));
                    break;
                case TEAM_STOPS_REPORT:
                    reportCriteriaList.add(getTeamStopsReportCriteriaByGroup(timeFrame.getInterval(), groupHierarchy, reportSchedule.getGroupIDList(), timeFrame, DateTimeZone.forTimeZone(person.getTimeZone()),
                            person.getLocale()));
                    break;
                case HOS_DAILY_DRIVER_LOG_REPORT:
                    if (reportSchedule.getParamType() == ReportParamType.DRIVER)
                    {
                        reportCriteriaList.addAll(getHosDailyDriverLogReportCriteria(
                                        groupHierarchy,
                                        reportSchedule.getDriverID(),
                                        timeFrame.getInterval(),
                                        person.getLocale(),
                                        person.getMeasurementType() == MeasurementType.METRIC,
                                        DateTimeZone.forTimeZone(person.getTimeZone()),
                                        reportSchedule.getIncludeInactiveDrivers()
                                        )
                                       );
                    }
                    else
                    {
                        reportCriteriaList.addAll(getHosDailyDriverLogReportCriteria(
                                        groupHierarchy,
                                        reportSchedule.getGroupIDList(),
                                        timeFrame.getInterval(),
                                        person.getLocale(),
                                        person.getMeasurementType() == MeasurementType.METRIC,
                                        DateTimeZone.forTimeZone(person.getTimeZone()),
                                        reportSchedule.getIncludeInactiveDrivers()
                                        )
                                       );
                    }
                    break;
                case HOS_VIOLATIONS_SUMMARY_REPORT:
                    reportCriteriaList.add(getHosViolationsSummaryReportCriteria(
                                    groupHierarchy,
                                    reportSchedule.getGroupIDList(),
                                    timeFrame.getInterval(),
                                    person.getLocale(),
                                    reportSchedule.getIncludeInactiveDrivers()
                                    ));
                    break;
                case HOS_VIOLATIONS_DETAIL_REPORT:
                    if (reportSchedule.getParamType() == ReportParamType.DRIVER)
                    { reportCriteriaList.add(getHosViolationsDetailReportCriteria(
                                        groupHierarchy,
                                        reportSchedule.getDriverID(),
                                        timeFrame.getInterval(),
                                        person.getLocale(),  
                                        reportSchedule.getIncludeInactiveDrivers()
                                        )
                                        );
                    }
                    else{
                        reportCriteriaList.add(getHosViolationsDetailReportCriteria(
                                        groupHierarchy, 
                                        reportSchedule.getGroupIDList(),
                                        timeFrame.getInterval()
                                        , person.getLocale(),
                                        reportSchedule.getIncludeInactiveDrivers()
                                        )
                                        );
                    }
                    break;
                case HOS_DRIVER_DOT_LOG_REPORT:
                    reportCriteriaList.add(getHosDriverDOTLogReportCriteria(
                                    reportSchedule.getDriverID(),
                                    timeFrame.getInterval(),
                                    person.getLocale(),
                                    reportSchedule.getIncludeInactiveDrivers()
                                    ));
                    break;
                case DOT_HOURS_REMAINING:
                    reportCriteriaList.add(getDotHoursRemainingReportCriteria(
                                    groupHierarchy,
                                    reportSchedule.getGroupIDList(),
                                    person.getLocale(),
                                    reportSchedule.getIncludeInactiveDrivers()
                                    ));
                    break;
                case HOS_ZERO_MILES:
                    reportCriteriaList.add(getHosZeroMilesReportCriteria(groupHierarchy, reportSchedule.getGroupIDList(), timeFrame.getInterval(), person.getLocale()));
                    break;
                case HOS_EDITS:
                    reportCriteriaList.add(getHosEditsReportCriteria(groupHierarchy, reportSchedule.getGroupIDList(), timeFrame.getInterval(), person.getLocale()));
                    break;
                case PAYROLL_DETAIL:
                    reportCriteriaList.add(getPayrollDetailReportCriteria(
                                    groupHierarchy,
                                    reportSchedule.getGroupIDList(),
                                    timeFrame.getInterval(),
                                    person.getLocale(),
                                    reportSchedule.getIncludeInactiveDrivers(),
                                    ReportCriteria.DEFAULT_INCLUDE_ZERO_MILES_DRIVERS
                                    ));
                    break;
                case PAYROLL_SIGNOFF:
                    if(reportSchedule.getParamType() == ReportParamType.DRIVER){
                        reportCriteriaList.add(getPayrollSignoffReportCriteria(
                                        groupHierarchy,
                                        reportSchedule.getDriverID(),
                                        timeFrame.getInterval(),
                                        person.getLocale(),
                                        reportSchedule.getIncludeInactiveDrivers(),
                                        ReportCriteria.DEFAULT_INCLUDE_ZERO_MILES_DRIVERS
                                        )
                                        );
                    } else {
                        reportCriteriaList.add(getPayrollSignoffReportCriteria(
                                        groupHierarchy,
                                        reportSchedule.getGroupIDList(),
                                        timeFrame.getInterval(),
                                        person.getLocale(),
                                        reportSchedule.getIncludeInactiveDrivers(),
                                        ReportCriteria.DEFAULT_INCLUDE_ZERO_MILES_DRIVERS
                                        )
                                        );
                    }
                    break;
                case PAYROLL_SUMMARY:
                    reportCriteriaList.add(getPayrollSummaryReportCriteria(
                                    groupHierarchy,
                                    reportSchedule.getGroupIDList(),
                                    timeFrame.getInterval(),
                                    person.getLocale(),
                                    reportSchedule.getIncludeInactiveDrivers()
                                    )
                                    );
                    break;
                case PAYROLL_COMPENSATED_HOURS:
                    reportCriteriaList.add(getPayrollCompensatedHoursReportCriteria(
                                    groupHierarchy,
                                    reportSchedule.getGroupIDList(),
                                    timeFrame.getInterval(),
                                    person.getLocale(),
                                    reportSchedule.getIncludeInactiveDrivers()
                                    )
                                    );
                    break;

                case TEN_HOUR_DAY_VIOLATIONS:
                    reportCriteriaList.add(getTenHoursDayViolationsCriteria(
                                    groupHierarchy,
                                    reportSchedule.getGroupID(),
                                    timeFrame.getInterval(),
                                    person.getLocale(),
                                    reportSchedule.getIncludeInactiveDrivers(),
                                    reportSchedule.getIncludeZeroMilesDrivers()
                                    )
                                    );
                    break;

                case DRIVER_HOURS:
                    reportCriteriaList.add(getDriverHoursReportCriteria(
                                    groupHierarchy,
                                    reportSchedule.getGroupIDList(),
                                    timeFrame.getInterval(),
                                    person.getLocale(),
                                    reportSchedule.getIncludeInactiveDrivers(),
                                    reportSchedule.getIncludeZeroMilesDrivers()
                                    ));
                    break;
                case THIRTY_MINUTE_BREAKS:
                    reportCriteriaList.add(getThirtyMinuteBreaksReportCriteria(
                                    groupHierarchy,
                                    reportSchedule.getGroupIDList(),
                                    timeFrame.getInterval(),
                                    person.getLocale(),
                                    reportSchedule.getIncludeInactiveDrivers()
                                    )
                                    );
                    break;

                case MILEAGE_BY_VEHICLE:
                    reportCriteriaList.add(getMileageByVehicleReportCriteria(groupHierarchy, reportSchedule.getGroupIDList(), timeFrame.getInterval(), person.getLocale(), person.getMeasurementType(),
                            reportSchedule.getIftaOnly()));
                    break;
                case STATE_MILEAGE_BY_VEHICLE:
                    reportCriteriaList.add(getStateMileageByVehicleReportCriteria(groupHierarchy, reportSchedule.getGroupIDList(), timeFrame.getInterval(), person.getLocale(),
                            person.getMeasurementType(), reportSchedule.getIftaOnly()));
                    break;
                case STATE_MILEAGE_BY_VEHICLE_ROAD_STATUS:
                    reportCriteriaList.add(getStateMileageByVehicleRoadStatusReportCriteria(groupHierarchy, reportSchedule.getGroupIDList(), timeFrame.getInterval(), person.getLocale(),
                            person.getMeasurementType(), reportSchedule.getIftaOnly()));
                    break;
                case STATE_MILEAGE_COMPARE_BY_GROUP:
                    reportCriteriaList.add(getStateMileageCompareByGroupReportCriteria(groupHierarchy, reportSchedule.getGroupIDList(), timeFrame.getInterval(), person.getLocale(),
                            person.getMeasurementType(), reportSchedule.getIftaOnly()));
                    break;
                case STATE_MILEAGE_BY_MONTH:
                    reportCriteriaList.add(getStateMileageByMonthReportCriteria(groupHierarchy, reportSchedule.getGroupIDList(), timeFrame.getInterval(), person.getLocale(),
                            person.getMeasurementType(), reportSchedule.getIftaOnly()));
                    break;
                case STATE_MILEAGE_FUEL_BY_VEHICLE:
                    reportCriteriaList.add(getStateMileageFuelByVehicleReportCriteria(groupHierarchy, reportSchedule.getGroupIDList(), timeFrame.getInterval(), person.getLocale(),
                            person.getMeasurementType(), reportSchedule.getIftaOnly()));
                    break;
                case STATE_MILEAGE_BY_VEHICLE_NON_IFTA:
                    reportCriteriaList
                            .add(getStateMileageByVehicleReportCriteria(groupHierarchy, reportSchedule.getGroupID(), timeFrame.getInterval(), person.getLocale(), person.getMeasurementType()));
                    break;
                case DRIVING_TIME_VIOLATIONS_SUMMARY_REPORT:
                    reportCriteriaList.add(getDrivingTimeViolationsSummaryReportCriteria(
                                    groupHierarchy,
                                    reportSchedule.getGroupIDList(),
                                    timeFrame.getInterval(),
                                    person.getLocale(),
                                    reportSchedule.getIncludeInactiveDrivers()
                                    )
                                    );
                    break;
                case DRIVING_TIME_VIOLATIONS_DETAIL_REPORT:
                    if (reportSchedule.getParamType() == ReportParamType.DRIVER)
                        reportCriteriaList.add(getDrivingTimeViolationsDetailReportCriteria(
                                        groupHierarchy,
                                        reportSchedule.getDriverID(),
                                        timeFrame.getInterval(),
                                        person.getLocale(),
                                        reportSchedule.getIncludeInactiveDrivers()
                                        )
                                        );
                    else
                        reportCriteriaList.add(getDrivingTimeViolationsDetailReportCriteria(
                                        groupHierarchy,
                                        reportSchedule.getGroupIDList(),
                                        timeFrame.getInterval(),
                                        person.getLocale(),
                                        reportSchedule.getIncludeInactiveDrivers()
                                        )
                                        );
                    break;

                case NON_DOT_VIOLATIONS_SUMMARY_REPORT:
                    reportCriteriaList.add(getNonDOTViolationsSummaryReportCriteria(
                                    groupHierarchy,
                                    reportSchedule.getGroupIDList(), 
                                    timeFrame.getInterval(),
                                    person.getLocale(),
                                    reportSchedule.getIncludeInactiveDrivers()
                                    )
                                    );
                    break;
                case NON_DOT_VIOLATIONS_DETAIL_REPORT:
                    if (reportSchedule.getParamType() == ReportParamType.DRIVER)
                        reportCriteriaList.add(getNonDOTViolationsDetailReportCriteria(
                                        groupHierarchy,
                                        reportSchedule.getDriverID(),
                                        timeFrame.getInterval(),
                                        person.getLocale(),
                                        reportSchedule.getIncludeInactiveDrivers()
                                        )
                                        );
                    else
                        reportCriteriaList.add(getNonDOTViolationsDetailReportCriteria(
                                        groupHierarchy,
                                        reportSchedule.getGroupIDList(), 
                                        timeFrame.getInterval(),
                                        person.getLocale(),
                                        reportSchedule.getIncludeInactiveDrivers()
                                        )
                                        );
                    break;

                case DRIVER_PERFORMANCE_TEAM:
                case DRIVER_PERFORMANCE_RYG_TEAM:
                    Boolean ryg = (reportGroup.getReports()[i] == ReportType.DRIVER_PERFORMANCE_RYG_TEAM);
                    reportCriteriaList.add(getDriverPerformanceReportCriteria(
                                    groupHierarchy,
                                    reportSchedule.getGroupID(),
                                    timeFrame.getInterval(),
                                    person.getLocale(),
                                    ryg,
                                    reportSchedule.getIncludeInactiveDrivers(),
                                    reportSchedule.getIncludeZeroMilesDrivers()
                                    )
                                    );
                    break;
                case DRIVER_PERFORMANCE_KEY_METRICS:
                    reportCriteriaList.add(getDriverPerformanceKeyMetricsReportCriteria(
                                    groupHierarchy, 
                                    reportSchedule.getGroupIDList(),
                                    timeFrame,
                                    timeFrame.getInterval(),
                                    person.getLocale(),
                                    person.getMeasurementType(),
                                    reportSchedule.getIncludeInactiveDrivers(),
                                    reportSchedule.getIncludeZeroMilesDrivers()
                                    )
                                    );
                    break;
                case DRIVER_PERFORMANCE_KEY_METRICS_TF_RYG:
                    reportCriteriaList.add(getDriverPerformanceKeyMetricsTimeFrameReportCriteria(
                                    groupHierarchy,
                                    reportSchedule.getGroupIDList(), 
                                    timeFrame,
                                    timeFrame.getInterval(),         
                                    person.getLocale(),
                                    person.getMeasurementType(),   
                                    reportSchedule.getIncludeInactiveDrivers(),
                                    reportSchedule.getIncludeZeroMilesDrivers()
                                    )
                                    );
                    break;

                case DRIVER_COACHING:
                    DateTimeZone dtz = DateTimeZone.forTimeZone(person.getTimeZone());
                    if (reportSchedule.getGroupID() != null) {
                        reportCriteriaList.addAll(getDriverCoachingReportCriteriaByGroup(
                                        groupHierarchy,
                                        reportSchedule.getGroupID(),
                                        timeFrame.getInterval(),
                                        person.getLocale(),
                                        dtz,   
                                        reportSchedule.getIncludeInactiveDrivers(),
                                        reportSchedule.getIncludeZeroMilesDrivers()
                                        )
                                        );
                    } else {
                        reportCriteriaList.add(getDriverCoachingReportCriteriaByDriver(
                                        groupHierarchy, reportSchedule.getDriverID(),
                                        timeFrame.getInterval(), 
                                        person.getLocale(),
                                        dtz,   
                                        reportSchedule.getIncludeInactiveDrivers(),
                                        reportSchedule.getIncludeZeroMilesDrivers()
                                        )
                                        );
                    }
                    break;
                    
                case DRIVER_COACHING_SCORE:
                    DateTimeZone dtz1 = DateTimeZone.forTimeZone(person.getTimeZone());
                    if (reportSchedule.getGroupID() != null) {
                        reportCriteriaList.addAll(getDriverCoachingScoreReportCriteriaByGroup(
                                        groupHierarchy,
                                        reportSchedule.getGroupID(),
                                        timeFrame.getInterval(),
                                        person.getLocale(),
                                        dtz1,   
                                        reportSchedule.getIncludeInactiveDrivers(),
                                        reportSchedule.getIncludeZeroMilesDrivers()
                                        )
                                        );
                    } else {
                        reportCriteriaList.add(getDriverCoachingScoreReportCriteriaByDriver(
                                        groupHierarchy, reportSchedule.getDriverID(),
                                        timeFrame.getInterval(), 
                                        person.getLocale(),
                                        dtz1,   
                                        reportSchedule.getIncludeInactiveDrivers(),
                                        reportSchedule.getIncludeZeroMilesDrivers()
                                        )
                                        );
                    }
                    break;
                case DRIVER_EXCLUDED_VIOLATIONS:
                    reportCriteriaList.add(getDriverExcludedViolationCriteria(
                                    groupHierarchy,
                                    reportSchedule.getGroupID(),
                                    timeFrame.getInterval(),
                                    person.getLocale(),
                                    DateTimeZone.forTimeZone(person.getTimeZone()),   
                                    reportSchedule.getIncludeInactiveDrivers(),
                                    reportSchedule.getIncludeZeroMilesDrivers()
                                    )
                                    );
                    break;
                case NON_COMM:
                    reportCriteriaList.add(getNonCommReportCriteria(groupHierarchy, reportSchedule.getGroupID(), timeFrame, person.getLocale(), DateTimeZone.forTimeZone(person.getTimeZone())));
                    break;
                case DVIR_PRETRIP:
                    reportCriteriaList.add(getDVIRPreTripReportCriteria(groupHierarchy, reportSchedule.getGroupID(), timeFrame, person.getLocale(), DateTimeZone.forTimeZone(person.getTimeZone())));
                    break;
                case DVIR_POSTTRIP:
                    reportCriteriaList.add(getDVIRPostTripReportCriteria(groupHierarchy, reportSchedule.getGroupID(), timeFrame, person.getLocale(), DateTimeZone.forTimeZone(person.getTimeZone())));
                    break;
                case DVIR_VIOLATION:
                    reportCriteriaList.add(getDVIRViolationReportCriteria(groupHierarchy, reportSchedule.getGroupID(), timeFrame, person.getLocale(), DateTimeZone.forTimeZone(person.getTimeZone())));
                    break;
                case DVIR_REPAIR:
                    reportCriteriaList.add(getDVIRInspectionRepairCompleteCriteria(groupHierarchy, reportSchedule.getGroupID(), timeFrame, person.getLocale(), DateTimeZone.forTimeZone(person.getTimeZone()), false));
                    break;
                case DVIR_REPAIR_DETAIL:
                    reportCriteriaList.add(getDVIRInspectionRepairCompleteCriteria(groupHierarchy, reportSchedule.getGroupID(), timeFrame, person.getLocale(), DateTimeZone.forTimeZone(person.getTimeZone()), true));
                    break;
                case BACKING_REPORT:
                    reportCriteriaList.add(getBackingReportCriteria(timeFrame.getInterval(), groupHierarchy, reportSchedule.getGroupID(), timeFrame, person.getLocale(), DateTimeZone.forID(person.getTimeZone().getID()),
                            person.getMeasurementType(), reportSchedule.getIncludeInactiveDrivers(), reportSchedule.getIncludeZeroMilesDrivers()));
                    break;
                case VEHICLE_MAINTENANCE_EVENTS_REPORT:
                   dateTimeZone = DateTimeZone.forID(person.getTimeZone().getID());
                   interval = new Interval(new DateMidnight(new DateTime().minusWeeks(1), dateTimeZone), new DateMidnight(new DateTime(), dateTimeZone).toDateTime().plusDays(1).minus(ONE_MINUTE));
                    reportCriteriaList.add(getMaintenanceEventsReportCriteria(groupHierarchy,reportSchedule.getGroupIDList(),interval, person.getLocale(),
                            DateTimeZone.forID(person.getTimeZone().getID()),person.getMeasurementType()));
                    break;
                case VEHICLE_MAINTENANCE_INTERVAL_REPORT:
                    dateTimeZone = DateTimeZone.forID(person.getTimeZone().getID());
                    interval = new Interval(new DateMidnight(new DateTime().minusWeeks(1), dateTimeZone), new DateMidnight(new DateTime(), dateTimeZone).toDateTime().plusDays(1).minus(ONE_MINUTE));
                    reportCriteriaList.add(getMaintenanceIntervalReportCriteria(groupHierarchy, reportSchedule.getGroupIDList(), interval, person.getLocale(),
                            DateTimeZone.forID(person.getTimeZone().getID()),person.getMeasurementType()));
                    break;
                default:
                    break;

            }
        }
        Date reportDate = new Date();
        for (ReportCriteria reportCriteria : reportCriteriaList) { 
            reportCriteria.setLocale(person.getLocale());
            reportCriteria.setReportDate(reportDate, person.getTimeZone());
            reportCriteria.setUseMetric((person.getMeasurementType() != null && person.getMeasurementType().equals(MeasurementType.METRIC)));
            reportCriteria.setMeasurementType(person.getMeasurementType());
            reportCriteria.setFuelEfficiencyType(person.getFuelEfficiencyType());
            reportCriteria.setTimeZone(person.getTimeZone());
        }

        return reportCriteriaList;
    }
}
