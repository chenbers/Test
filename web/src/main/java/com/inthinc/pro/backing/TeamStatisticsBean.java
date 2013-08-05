package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.AggregationDuration;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.util.MessageUtil;

@KeepAlive
public class TeamStatisticsBean extends BaseBean {

    // Request scope bean for new team page
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(TeamStatisticsBean.class);

    private int numRowsPerPg = 25;
    // private List<DriverVehicleScoreWrapper> driverStatistics;
    // private List<DriverVehicleScoreWrapper> driverTotals;

    private ScoreDAO scoreDAO;
    private GroupReportDAO groupReportDAO;
    private TeamCommonBean teamCommonBean;

    // private Integer teamOverallScore;
    // private String teamOverallScoreStyle;

    private ReportRenderer reportRenderer;
    private ReportCriteriaService reportCriteriaService;
    
    public ScoreDAO getScoreDAO() {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO) {
        this.scoreDAO = scoreDAO;
    }

    public GroupReportDAO getGroupReportDAO() {
        return groupReportDAO;
    }

    public void setGroupReportDAO(GroupReportDAO groupReportDAO) {
        this.groupReportDAO = groupReportDAO;
    }

    public TeamCommonBean getTeamCommonBean() {
        return teamCommonBean;
    }

    public void setTeamCommonBean(TeamCommonBean teamCommonBean) {
        this.teamCommonBean = teamCommonBean;
    }

    public void init() {
    }

    public List<DriverVehicleScoreWrapper> getDriverStatistics() {
        // Have this cached?
        List<DriverVehicleScoreWrapper> driverStatistics = null;

        String key = teamCommonBean.getTimeFrame().name();
        if (teamCommonBean.getCachedResults().containsKey(key)) {
            driverStatistics = teamCommonBean.getCachedResults().get(key);

        } else {
            boolean isVehicleStats = ("teamVehicleStatistics").equals(teamCommonBean.getSelectedTabId());

            // Not there, grab it
            // 0: day value, start/end day the same, if no driving in time frame will show last DAY score
            // 1: week value, calculate start and add seven, if no driving in time frame will show last DAY score
            // 2: month or year, use duration identifier, if no driving in time frame will show last MONTH score
            // switch( MiscUtil.whichMethodToUse(teamCommonBean) ) {
            switch (teamCommonBean.getTimeFrame()) {
                case WEEK:
                    driverStatistics = (isVehicleStats) ? groupReportDAO.getVehicleScores(teamCommonBean.getGroupID(), teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()),
                            getGroupHierarchy()) : groupReportDAO.getDriverScores(teamCommonBean.getGroupID(), teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()), getGroupHierarchy());
                    break;
                case MONTH:
                case YEAR:
                    driverStatistics = (isVehicleStats) ? groupReportDAO.getVehicleScores(teamCommonBean.getGroupID(), teamCommonBean.getTimeFrame().getAggregationDuration(), getGroupHierarchy())
                            : groupReportDAO.getDriverScores(teamCommonBean.getGroupID(), teamCommonBean.getTimeFrame().getAggregationDuration(), getGroupHierarchy());
                    break;
                default:
                    driverStatistics = (isVehicleStats) ? groupReportDAO.getVehicleScores(teamCommonBean.getGroupID(), teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()).getStart(),
                            getGroupHierarchy()) : groupReportDAO.getDriverScores(teamCommonBean.getGroupID(), teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()).getStart(),
                            getGroupHierarchy());
                    break;
            }

            cleanData(driverStatistics);

            // All set, save so we don't grab the data again
            teamCommonBean.getCachedResults().put(key, driverStatistics);
        }
        return driverStatistics;
    }
    
    private void setOverallScoreUsingTrendScore(DriverVehicleScoreWrapper driverVehicleScoreWrapper) {
        Duration duration = Duration.DAYS;
        
        if(teamCommonBean.getTimeFrame().getAggregationDuration() == AggregationDuration.ONE_DAY) {
            duration = Duration.DAYS;
        } else if(teamCommonBean.getTimeFrame().getAggregationDuration() == AggregationDuration.ONE_MONTH) {
            duration = Duration.THREE;
        }
        
        Map<Integer, List<ScoreableEntity>> groupTrendMap;
        
        String key = teamCommonBean.getTimeFrame().name();
        if (teamCommonBean.getCachedTrendResults().containsKey(key)) {
            groupTrendMap = teamCommonBean.getCachedTrendResults().get(key);
        } else {
            groupTrendMap = getScoreDAO().getTrendScores(teamCommonBean.getGroup().getParentID(), duration, getGroupHierarchy());
            
            //fill in missing dates
            this.populateDateGaps(groupTrendMap.get(teamCommonBean.getGroupID()));
            
            teamCommonBean.getCachedTrendResults().put(key, groupTrendMap);
        }
        
        List<ScoreableEntity> trendsList = new ArrayList<ScoreableEntity>();
        trendsList = teamCommonBean.getCachedTrendResults().get(teamCommonBean.getTimeFrame().name()).get(teamCommonBean.getGroupID());
        
        ScoreableEntity se = this.getMatchingEntity(trendsList);
        
        if(se != null){
            driverVehicleScoreWrapper.getScore().setOverall(se.getScore());
        }    
    }
    
    private void populateDateGaps(List<ScoreableEntity> list) {
        if(list == null)
            return;
        
        Integer gap = 0;
        
        int lastIndex = 0;
        if(list != null && list.size() > 0)
            lastIndex = list.size() -1;
        
        ScoreableEntity lastEntity = list.get(lastIndex);
            
        DateTime lastEntityDateTime = new DateTime(lastEntity.getDate());
        lastEntityDateTime = lastEntityDateTime.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        
        DateTime currentDateTime = teamCommonBean.getTimeFrame().getCurrent();
        currentDateTime = currentDateTime.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        
        if (teamCommonBean.getTimeFrame() == TimeFrame.TODAY || 
            teamCommonBean.getTimeFrame() == TimeFrame.ONE_DAY_AGO ||
            teamCommonBean.getTimeFrame() == TimeFrame.TWO_DAYS_AGO || 
            teamCommonBean.getTimeFrame() == TimeFrame.THREE_DAYS_AGO ||
            teamCommonBean.getTimeFrame() == TimeFrame.FOUR_DAYS_AGO || 
            teamCommonBean.getTimeFrame() == TimeFrame.FIVE_DAYS_AGO) {
            gap = Days.daysBetween(lastEntityDateTime, currentDateTime).getDays();
            
            DateTime missingDateTime = new DateTime(lastEntity.getDate());
            for(int i = 0; i < gap; i++) {
                missingDateTime = missingDateTime.plusDays(1);
                ScoreableEntity se = new ScoreableEntity(lastEntity.getEntityID(),lastEntity.getEntityType(), lastEntity.getIdentifier(), lastEntity.getScore(), missingDateTime.toDate(), lastEntity.getScoreType());
                list.add(se);
            }
            return;
        } else if (teamCommonBean.getTimeFrame() == TimeFrame.MONTH) {
            currentDateTime = currentDateTime.dayOfMonth().withMaximumValue();
            gap = Months.monthsBetween(lastEntityDateTime, currentDateTime).getMonths();
            
            DateTime missingDateTime = new DateTime(lastEntity.getDate());
            for(int i = 0; i < gap; i++) {
                missingDateTime = missingDateTime.plusMonths(1);
                ScoreableEntity se = new ScoreableEntity(lastEntity.getEntityID(),lastEntity.getEntityType(), lastEntity.getIdentifier(), lastEntity.getScore(), missingDateTime.toDate(), lastEntity.getScoreType());
                list.add(se);
            }
            return;
        }
    }
    
    private DateTime getFilterDateTime() {
        DateTime filter = teamCommonBean.getTimeFrame().getCurrent();
        filter = filter.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        if (teamCommonBean.getTimeFrame() == TimeFrame.TODAY) {
            filter = filter.minusDays(1);
        } else if (teamCommonBean.getTimeFrame() == TimeFrame.ONE_DAY_AGO) {
            filter = filter.minusDays(2);
        } else if (teamCommonBean.getTimeFrame() == TimeFrame.TWO_DAYS_AGO) {
            filter = filter.minusDays(3);
        } else if (teamCommonBean.getTimeFrame() == TimeFrame.THREE_DAYS_AGO) {
            filter = filter.minusDays(4);
        } else if (teamCommonBean.getTimeFrame() == TimeFrame.FOUR_DAYS_AGO) {
            filter = filter.minusDays(5);
        } else if (teamCommonBean.getTimeFrame() == TimeFrame.FIVE_DAYS_AGO) {
            filter = filter.minusDays(6);
        } else if (teamCommonBean.getTimeFrame() == TimeFrame.MONTH) {
            filter = filter.minusMonths(1);
            filter = filter.dayOfMonth().withMaximumValue();
        }
        
        return filter;
    }
    
    private ScoreableEntity getMatchingEntity(List<ScoreableEntity> trendlist) {
        
        DateTime filterDateTime = this.getFilterDateTime();
        
        for(ScoreableEntity se : trendlist){
            DateTime dateTime = new DateTime(se.getDate());
            dateTime = dateTime.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
            
            if(dateTime.equals(filterDateTime)){
                return se;
            }
        }
        return null;
    }

    public void cleanData(List<DriverVehicleScoreWrapper> driverStatistics) {

        // A place to facilitate sorting and other good things
        for (DriverVehicleScoreWrapper dvsw : driverStatistics) {

            if (dvsw.getScore().getTrips() == null) {
                dvsw.getScore().setTrips(0);
            }

            if (dvsw.getScore().getCrashEvents() == null) {
                dvsw.getScore().setCrashEvents(0);
            }

            if (dvsw.getVehicle() == null) {
                Vehicle v = new Vehicle();
                v.setName(MessageUtil.getMessageString("reports_none_assigned"));
                dvsw.setVehicle(v);
            }
        }
    }

    public List<DriverVehicleScoreWrapper> getDriverTotals() {
        List<DriverVehicleScoreWrapper> local = new ArrayList<DriverVehicleScoreWrapper>();

        DriverVehicleScoreWrapper dvsw = DriverVehicleScoreWrapper.summarize(getDriverStatistics(), teamCommonBean.getGroup());
        
        this.setOverallScoreUsingTrendScore(dvsw);
        
        dvsw.setScoreStyle(ScoreBox.GetStyleFromScore(dvsw.getScore().getOverall().intValue(), ScoreBoxSizes.SMALL));

        local.add(dvsw);

        return local;
    }

    public int getNumRowsPerPg() {
        return numRowsPerPg;
    }

    public void setNumRowsPerPg(int numRowsPerPg) {
        this.numRowsPerPg = numRowsPerPg;
    }

    public Integer getTeamOverallScore() {
        return getDriverTotals().get(0).getScore().getOverall().intValue();
    }

    public void exportReportToPdf() {
        getReportRenderer().exportSingleReportToPDF(buildReportCriteria(), getFacesContext());
    }

    public void emailReport() {
        getReportRenderer().exportReportToEmail(buildReportCriteria(), getEmailAddress(), getNoReplyEmailAddress());
    }

    public void exportReportToExcel() {
        getReportRenderer().exportReportToExcel(buildReportCriteria(), getFacesContext());
    }

    protected ReportCriteria buildReportCriteria() {
        ReportCriteria reportCriteria = getReportCriteria();
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setUseMetric(getMeasurementType() == MeasurementType.METRIC);
        reportCriteria.setMeasurementType(getMeasurementType());
        reportCriteria.setFuelEfficiencyType(getFuelEfficiencyType());
        List<DriverVehicleScoreWrapper> reportDataSet = new ArrayList<DriverVehicleScoreWrapper>();
        reportDataSet.addAll(this.getDriverTotals());
        List<DriverVehicleScoreWrapper> driverStatList = getDriverStatistics();
        Collections.sort(driverStatList);
        reportDataSet.addAll(driverStatList);
        reportCriteria.setMainDataset(reportDataSet);
        return reportCriteria;
    }

    protected ReportCriteria getReportCriteria() {
        return getReportCriteriaService().getTeamStatisticsReportCriteria(teamCommonBean.getGroupID(), teamCommonBean.getTimeFrame(), getDateTimeZone(), getLocale(), false, getAccountGroupHierarchy());
    }

    public void setReportRenderer(ReportRenderer reportRenderer) {
        this.reportRenderer = reportRenderer;
    }

    public ReportRenderer getReportRenderer() {
        return reportRenderer;
    }

    public void setReportCriteriaService(ReportCriteriaService reportCriteriaService) {
        this.reportCriteriaService = reportCriteriaService;
    }

    public ReportCriteriaService getReportCriteriaService() {
        return reportCriteriaService;
    }

}
