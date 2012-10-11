package com.inthinc.pro.service.adapters;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.DriverName;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.VehicleName;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupTrendWrapper;
import com.inthinc.pro.model.aggregation.Score;
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
    @Autowired
    private DriverDAO driverDAO;
    @Autowired
    private VehicleDAO vehicleDAO;
    @Autowired
    private PersonDAO personDAO;
    
	public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

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

	public List<Group> getSubGroups(Integer parentGroupID){
		List<Group> subGroups = groupDAO.getGroupHierarchy(getAccountID(), parentGroupID);
		return extractSubGroupIDs(parentGroupID,subGroups);
	}
	private List<Group> extractSubGroupIDs(Integer parentGroupID, List<Group> subGroups){
		
		List<Group> subGroupIDs = new ArrayList<Group>();
		for(Group group : subGroups){
			if(parentGroupID.equals(group.getParentID())){
				subGroupIDs.add(group);
			}
		}
		return subGroupIDs;
	}
	public List<GroupScoreWrapper> getChildGroupsDriverScores(Integer groupID, Interval interval) {
		List<Group> subGroups = getSubGroups(groupID);
		List<GroupScoreWrapper> list = new ArrayList<GroupScoreWrapper>();
		for(Group subGroup : subGroups){
			Score score = getAggregateDriverScore(subGroup.getGroupID(), interval);
			GroupScoreWrapper gsw = new GroupScoreWrapper();
			gsw.setGroup(subGroup);
			gsw.setScore(score);
			list.add(gsw);
		}
		return list;
	}
    public Score getAggregateDriverScore(Integer groupID, Interval interval) {
    	 return groupReportDAO.getAggregateDriverScore(groupID, interval, new GroupHierarchy(getAll()));
	}
    public List<GroupScoreWrapper> getChildGroupsDriverScores(Integer groupID, Duration duration) {
        return groupReportDAO.getSubGroupsAggregateDriverScores(groupID, duration, new GroupHierarchy(getAll()));
    }	

    public List<GroupTrendWrapper> getChildGroupsDriverTrends(Integer groupID, Duration duration) {
        return groupReportDAO.getSubGroupsAggregateDriverTrends(groupID, duration, new GroupHierarchy(getAll()));
    }    

    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Duration duration) {
        return groupReportDAO.getDriverScores(groupID, duration, new GroupHierarchy(getAll()));
    }
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Interval interval) {
        return groupReportDAO.getDriverScores(groupID, interval, new GroupHierarchy(getAll()));
    }
    
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Duration duration) {
        return groupReportDAO.getVehicleScores(groupID, duration, new GroupHierarchy(getAll()));
    }

    public List<DriverName> getGroupDriverNames(Integer groupID) {
    	return driverDAO.getDriverNames(groupID);
    }

    public List<VehicleName> getGroupVehicleNames(Integer groupID) {
    	return vehicleDAO.getVehicleNames(groupID);
    }

    public Person getPersonByID(Integer id){
        return personDAO.findByID(id);
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
	
	public DriverDAO getDriverDAO() {
		return driverDAO;
	}
	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}
	public VehicleDAO getVehicleDAO() {
		return vehicleDAO;
	}
	public void setVehicleDAO(VehicleDAO vehicleDAO) {
		this.vehicleDAO = vehicleDAO;
	}
}
