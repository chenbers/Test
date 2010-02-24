package com.inthinc.pro.backing;

import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;

public class TeamBean extends BaseBean {

//  Request scope bean for new team page
    
    private static final long serialVersionUID = 1L;

    private List<DriverVehicleScoreWrapper> driverStatistics;
    private Integer groupID;
    private Group group;

    private GroupReportDAO groupReportDAO;  
    private DurationBean durationBean;

    public GroupReportDAO getGroupReportDAO() {
        return groupReportDAO;
    }

    public void setGroupReportDAO(GroupReportDAO groupReportDAO) {
        this.groupReportDAO = groupReportDAO;
    }

    public DurationBean getDurationBean() {
        return durationBean;
    }

    public void setDurationBean(DurationBean durationBean) {
        this.durationBean = durationBean;
    }

    public List<DriverVehicleScoreWrapper> getDriverStatistics() {
        DateMidnight endTime = new DateTime().minusDays(1).toDateMidnight();
//        DateMidnight startTime = new DateTime().minusDays(20).toDateMidnight();
        DateMidnight startTime = getReportStartTime();
        
//        if (this.driverStatistics == null) {
//            driverStatistics = groupReportDAO.getDriverScores(getGroupID(), Duration.DAYS);
            driverStatistics = groupReportDAO.getDriverScores(groupID, startTime.toDateTime(), endTime.toDateTime());
//        }
        return driverStatistics;
    }

    public void setDriverStatistics(List<DriverVehicleScoreWrapper> driverStatistics) {
        this.driverStatistics = driverStatistics;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        // TODO: probably need some checks here on groupID and the group object        
        group = getGroupHierarchy().getGroup(groupID);
        this.groupID = groupID;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    private DateMidnight getReportStartTime() {
        DateMidnight local = new DateTime().minusDays(30).toDateMidnight();
        
        if (            durationBean.getDuration().equals(Duration.THREE) ) {
            return new DateTime().minusDays(Duration.THREE.getNumberOfDays()).toDateMidnight();
            
        } else if (     durationBean.getDuration().equals(Duration.SIX) ) {
            return new DateTime().minusDays(Duration.SIX.getNumberOfDays()).toDateMidnight();
            
        } else if (     durationBean.getDuration().equals(Duration.TWELVE) ) {
            return new DateTime().minusDays(Duration.TWELVE.getNumberOfDays()).toDateMidnight();
        }
        
        
        return local;
    }
}
