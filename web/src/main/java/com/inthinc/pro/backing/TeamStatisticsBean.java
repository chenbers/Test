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
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.MiscUtil;

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
    // logger.info("TeamStatisticsBean:init()");

    // Set the styles for the color-coded box
    // loadScoreStyles();
    // cleanData();

    // All set, save so we don't grab the data again
    // teamCommonBean.getCachedResults().put(key, driverStatistics);

    // Find the totals
    // driverTotals = getDriverTotals();

    // Set the score and style for the top of the containing page
    // teamOverallScore = driverTotals.get(0).getScore().getOverall().intValue();
    // teamOverallScoreStyle = driverTotals.get(0).getScoreStyle();

    // logger.info("TeamStatisticsBean:init()-END");
    }

    public List<DriverVehicleScoreWrapper> getDriverStatistics() {
        // Have this cached?
        List<DriverVehicleScoreWrapper> driverStatistics = null;

        String key = teamCommonBean.getTimeFrame().name();
        if (teamCommonBean.getCachedResults().containsKey(key)) {
            driverStatistics = teamCommonBean.getCachedResults().get(key);

        } else {
            // Not there, grab it
            //  0: day value, start/end day the same, if no driving in time frame will show last DAY score
            //  1: week value, calculate start and add seven, if no driving in time frame will show last DAY score
            //  2: month or year, use duration identifier, if no driving in time frame will show last MONTH score
            switch( MiscUtil.whichMethodToUse(teamCommonBean) ) {
                case 0:
                    driverStatistics = groupReportDAO.getDriverScores(teamCommonBean.getGroupID(), 
                            teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()).getStart());
                    break;
                case 1:
                    driverStatistics = groupReportDAO.getDriverScores(teamCommonBean.getGroupID(), 
                            teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()));
                    break;
                case 2:
                    driverStatistics = groupReportDAO.getDriverScores(teamCommonBean.getGroupID(), 
                            teamCommonBean.getTimeFrame().getAggregationDuration());
                    break;
                case 3:
                	driverStatistics = groupReportDAO.getVehicleScores(teamCommonBean.getGroupID(), 
                            teamCommonBean.getTimeFrame().getAggregationDuration());
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
        dvsw.setScoreStyle(ScoreBox.GetStyleFromScore(dvsw.getScore().getOverall().intValue(), ScoreBoxSizes.SMALL));

        local.add(dvsw);

        return local;
    }

    // public void setDriverTotals(List<DriverVehicleScoreWrapper> driverTotals) {
    // this.driverTotals = driverTotals;
    // }

    public int getNumRowsPerPg() {
        return numRowsPerPg;
    }

    public void setNumRowsPerPg(int numRowsPerPg) {
        this.numRowsPerPg = numRowsPerPg;
    }

    public Integer getTeamOverallScore() {
        return getDriverTotals().get(0).getScore().getOverall().intValue();
        // return teamOverallScore;
    }

    // public void setTeamOverallScore(Integer teamOverallScore) {
    // this.teamOverallScore = teamOverallScore;
    // }

    // public String getTeamOverallScoreStyle() {
    // return teamOverallScoreStyle;
    // }
    //
    // public void setTeamOverallScoreStyle(String teamOverallScoreStyle) {
    // this.teamOverallScoreStyle = teamOverallScoreStyle;
    // }

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
        return getReportCriteriaService().getTeamStatisticsReportCriteria(teamCommonBean.getGroupID(), teamCommonBean.getTimeFrame(), getDateTimeZone(), getLocale(), false);
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
