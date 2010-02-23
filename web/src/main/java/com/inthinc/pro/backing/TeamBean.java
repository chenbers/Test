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
    private NavigationBean navigationBean;

    public GroupReportDAO getGroupReportDAO() {
        return groupReportDAO;
    }

    public void setGroupReportDAO(GroupReportDAO groupReportDAO) {
        this.groupReportDAO = groupReportDAO;
    }

    public NavigationBean getNavigationBean() {
        return navigationBean;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    public List<DriverVehicleScoreWrapper> getDriverStatistics() {
        DateMidnight endTime = new DateTime().minusDays(1).toDateMidnight();
        DateMidnight startTime = new DateTime().minusDays(20).toDateMidnight();

        if (this.driverStatistics == null)
            driverStatistics = groupReportDAO.getDriverScores(getGroupID(), Duration.DAYS);
        return driverStatistics;
    }

    public void setDriverStatistics(List<DriverVehicleScoreWrapper> driverStatistics) {
        this.driverStatistics = driverStatistics;
    }

    public Integer getGroupID() {
        if ( groupID == null ) {
            groupID = this.navigationBean.getGroupID();
        }
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

}
