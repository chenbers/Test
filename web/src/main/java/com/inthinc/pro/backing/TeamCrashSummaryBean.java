package com.inthinc.pro.backing;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.CrashSummary;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.Score;

public class TeamCrashSummaryBean extends BaseBean {

    private static final Logger logger = Logger.getLogger(TeamCrashSummaryBean.class);
       
    private CrashSummary crashSummary;
    private TeamCommonBean teamCommonBean;
    private GroupReportDAO groupReportDAO;     

    public void init(){
        setCrashSummary(getCrashSummaryForGroup(teamCommonBean.getGroupID()));            
    }
    
    private CrashSummary getCrashSummaryForGroup(Integer groupID) {
        Score crashData = new Score();        

        // Get the data
//        if ( whichMethodToUse() ) {            
//            crashData = groupReportDAO.getAggregateDriverScore(groupID, 
//                    teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()));
//            
//        } else {
            crashData = groupReportDAO.getAggregateDriverScore(groupID, 
                    TimeFrame.YEAR.getAggregationDuration(), getGroupHierarchy());
//                    teamCommonBean.getTimeFrame().getAggregationDuration());
//        }       
        
        // Create the response
        CrashSummary crashSummary = new CrashSummary(0,0,0,0,0);
        if ( crashData != null ) {
            crashSummary = new CrashSummary(
                crashData.getCrashEvents() == null ? 0 : crashData.getCrashEvents().intValue(), 
                crashData.getCrashTotal() == null ? 0 : crashData.getCrashTotal().intValue(), 
                crashData.getCrashDays() == null ? 0 : crashData.getCrashDays().intValue(),
                crashData.getOdometer() == null ? 0 : crashData.getOdometer().doubleValue()/100.0, 
                crashData.getCrashOdometer() == null ? 0 : crashData.getCrashOdometer().doubleValue()/100.0);
        }

        return crashSummary;       
    }
    
    private boolean whichMethodToUse() {      
        
        if (    this.teamCommonBean.getTimeFrame().equals(TimeFrame.WEEK) ||
                this.teamCommonBean.getTimeFrame().equals(TimeFrame.MONTH) ||
                this.teamCommonBean.getTimeFrame().equals(TimeFrame.YEAR) ) {
            return false;
        }
    
        return true;
    }    

    public void setCrashSummary(CrashSummary crashSummary) {
        this.crashSummary = crashSummary;
    }
    
    public Double getCrashesPerMillionMiles() {
        return crashSummary.getCrashesPerMillionMiles();
    }
    
    public CrashSummary getCrashSummary() {
        return crashSummary;
    }
    
    public Integer getDaysSinceLastCrash() {

        return crashSummary.getDaysSinceLastCrash();
    }

    public Long getMilesSinceLastCrash() {
        return crashSummary.getMilesSinceLastCrash().longValue();
    }
    
    public Integer getTotalCrashes(){
        return crashSummary.getTotalCrashes();
    }
    
    public TeamCommonBean getTeamCommonBean() {
        return teamCommonBean;
    }
    
    public void setTeamCommonBean(TeamCommonBean teamCommonBean) {
        this.teamCommonBean = teamCommonBean;
    }

    public GroupReportDAO getGroupReportDAO() {
        return groupReportDAO;
    }

    public void setGroupReportDAO(GroupReportDAO groupReportDAO) {
        this.groupReportDAO = groupReportDAO;
    }
}

