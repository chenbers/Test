package com.inthinc.pro.backing;

import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;

public class TeamStatisticsBean extends BaseBean {

//  Request scope bean for new team page    
    private static final long serialVersionUID = 1L;
    

    private List<DriverVehicleScoreWrapper> driverStatistics;
//    private Integer groupID;
//    private Group group;

    private GroupReportDAO groupReportDAO;  
    private TeamCommonBean teamCommonBean;    

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

    public List<DriverVehicleScoreWrapper> getDriverStatistics() {
        DateMidnight endTime = new DateTime().minusDays(0).toDateMidnight();
//        DateMidnight startTime = new DateTime().minusDays(20).toDateMidnight();
        DateMidnight startTime = teamCommonBean.getReportStartTime();
        
//        if (this.driverStatistics == null) {
//            driverStatistics = groupReportDAO.getDriverScores(getGroupID(), Duration.DAYS);
            driverStatistics = groupReportDAO.getDriverScores(teamCommonBean.getGroupID(), startTime.toDateTime(), endTime.toDateTime());
            loadScoreStyles();
//        }
        return driverStatistics;
    }

    public void setDriverStatistics(List<DriverVehicleScoreWrapper> driverStatistics) {
        this.driverStatistics = driverStatistics;
    }

    public Integer getGroupID() {
        return teamCommonBean.getGroupID();
    }

//    public void setGroupID(Integer groupID) {
//        // TODO: probably need some checks here on groupID and the group object        
//        group = getGroupHierarchy().getGroup(groupID);
//        this.groupID = groupID;
//    }
//
//    public Group getGroup() {
//        return group;
//    }
//
//    public void setGroup(Group group) {
//        this.group = group;
//    }

    
    private void loadScoreStyles() {
        for ( DriverVehicleScoreWrapper dvsw: driverStatistics) {
        	
        	if(dvsw.getScore().getOverall()== null){
        		
                dvsw.setScoreStyle(ScoreBox.GetStyleFromScore(
                        0, ScoreBoxSizes.SMALL));
        	}
        	else{
        		
        		dvsw.setScoreStyle(ScoreBox.GetStyleFromScore(
        				dvsw.getScore().getOverall().intValue(), ScoreBoxSizes.SMALL));
        	}
        }
    }
}
