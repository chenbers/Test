package com.inthinc.pro.backing;

import java.util.List;

import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;

public class TeamBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    private List<DriverVehicleScoreWrapper> driverStatistics;
    private Integer groupID;
    private Group group;

    private GroupReportDAO groupReportDAO;

    public GroupReportDAO getGroupReportDAO() {
        return groupReportDAO;
    }

    public void setGroupReportDAO(GroupReportDAO groupReportDAO) {
        this.groupReportDAO = groupReportDAO;
    }

    public List<DriverVehicleScoreWrapper> getDriverStatistics() {
        if(this.driverStatistics == null)
            driverStatistics = groupReportDAO.getDriverScores(getGroupID(), Duration.DAYS);
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

}
