package com.inthinc.pro.service.adapters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupTrendWrapper;

/**
 * Adapter for the Driver resources.
 *  
 * @author dcueva
 */
@Component
public class GroupDAOAdapter extends BaseDAOAdapter<Group> {

    @Autowired
    private GroupDAO groupDAO;	
    @Autowired
    private GroupReportDAO groupReportDAO;
    
	@Override
	public List<Group> getAll() {
        return groupDAO.getGroupHierarchy(getAccountID(), getGroupID());
	}

	@Override
	protected GenericDAO<Group, Integer> getDAO() {
		return groupDAO;
	}

	@Override
	protected Integer getResourceID(Group group) {
		return group.getGroupID();
	}

	// Specialized methods ----------------------------------------------------

    public List<GroupScoreWrapper> getChildGroupsDriverScores(Integer groupID, Duration duration) {
        return groupReportDAO.getSubGroupsAggregateDriverScores(groupID, duration);
    }	

    public List<GroupTrendWrapper> getChildGroupsDriverTrends(Integer groupID, Duration duration) {
        return groupReportDAO.getSubGroupsAggregateDriverTrends(groupID, duration);
    }    

    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Duration duration) {
        return groupReportDAO.getDriverScores(groupID, duration);
    }
    
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Duration duration) {
        return groupReportDAO.getVehicleScores(groupID, duration);
    }

	// Getters and setters -----------------------------------------------------
    
	/**
	 * @return the groupDAO
	 */
	public GroupDAO getGroupDAO() {
		return groupDAO;
	}

	/**
	 * @param groupDAO the groupDAO to set
	 */
	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

	/**
	 * @return the groupReportDAO
	 */
	public GroupReportDAO getGroupReportDAO() {
		return groupReportDAO;
	}

	/**
	 * @param groupReportDAO the groupReportDAO to set
	 */
	public void setGroupReportDAO(GroupReportDAO groupReportDAO) {
		this.groupReportDAO = groupReportDAO;
	}    
}
