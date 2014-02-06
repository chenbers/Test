package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.MeasurementType;
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

    private GroupReportDAO groupReportDAO;
    private TeamCommonBean teamCommonBean;

    // private Integer teamOverallScore;
    // private String teamOverallScoreStyle;

    private ReportRenderer reportRenderer;
    private ReportCriteriaService reportCriteriaService;

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

            // note: uses new methods that do not convert dates to UTC
            switch (teamCommonBean.getTimeFrame()) {
                case WEEK:
                    driverStatistics = (isVehicleStats) ? groupReportDAO.getVehicleScores(teamCommonBean.getGroupID(), teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()),
                            getGroupHierarchy()) : groupReportDAO.getDriverScoresWithNaturalInterval(teamCommonBean.getGroupID(), teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()), getGroupHierarchy());
                    break;
                case MONTH:
                case YEAR:
                    driverStatistics = (isVehicleStats) ? groupReportDAO.getVehicleScores(teamCommonBean.getGroupID(), teamCommonBean.getTimeFrame().getAggregationDuration(), getGroupHierarchy())
                            : groupReportDAO.getDriverScores(teamCommonBean.getGroupID(), teamCommonBean.getTimeFrame().getAggregationDuration(), getGroupHierarchy());
                    break;
                default:
                    driverStatistics = (isVehicleStats) ? groupReportDAO.getVehicleScores(teamCommonBean.getGroupID(), teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()).getStart(),
                            getGroupHierarchy()) : groupReportDAO.getDriverScoresWithNaturalInterval(teamCommonBean.getGroupID(), teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()).getStart(),
                            getGroupHierarchy());
                    break;
            }

            cleanData(driverStatistics);

            // All set, save so we don't grab the data again
            teamCommonBean.getCachedResults().put(key, driverStatistics);
        }
        return driverStatistics;
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
        
        if(teamCommonBean.useTrendScores()){
            Number score = this.getTeamCommonBean().getOverallScoreUsingTrendScore(groupReportDAO);
            dvsw.getScore().setOverall(score);
        }
        
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
