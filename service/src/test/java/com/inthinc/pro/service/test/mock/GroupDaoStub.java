package com.inthinc.pro.service.test.mock;

import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.AggregationDuration;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupTrendWrapper;
import com.inthinc.pro.model.aggregation.Percentage;
import com.inthinc.pro.model.aggregation.Score;

/**
 * Stub class for GroupDAO.
 * 
 * @author dfreitas
 */
@Component
public class GroupDaoStub implements GroupDAO, GroupReportDAO {

    private Group expectedGroup;

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.GroupDAO#getGroupHierarchy(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<Group> getGroupHierarchy(Integer acctID, Integer groupID) {
        return Collections.emptyList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.GroupDAO#getGroupsByAcctID(java.lang.Integer)
     */
    @Override
    public List<Group> getGroupsByAcctID(Integer acctID) {
        return Collections.emptyList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.GenericDAO#create(java.lang.Object, java.lang.Object)
     */
    @Override
    public Integer create(Integer id, Group entity) {
        return entity.getGroupID();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.GenericDAO#deleteByID(java.lang.Object)
     */
    @Override
    public Integer deleteByID(Integer id) {
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.GenericDAO#findByID(java.lang.Object)
     */
    @Override
    public Group findByID(Integer id) {
        return expectedGroup;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.GenericDAO#update(java.lang.Object)
     */
    @Override
    public Integer update(Group entity) {
        return entity.getGroupID();
    }

    /*
     * @param group
     */
    public void setExpectedGroup(Group group) {
        this.expectedGroup = group;
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getAggregateDriverScore(java.lang.Integer, com.inthinc.pro.model.AggregationDuration)
     */
    @Override
    public Score getAggregateDriverScore(Integer groupID, AggregationDuration duration) {
        return null;
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getAggregateDriverScore(java.lang.Integer, org.joda.time.Interval)
     */
    @Override
    public Score getAggregateDriverScore(Integer groupID, Interval interval) {
        return null;
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getAggregateDriverScore(java.lang.Integer, org.joda.time.DateTime, org.joda.time.DateTime)
     */
    @Override
    public Score getAggregateDriverScore(Integer groupID, DateTime startTime, DateTime endTime) {
        return null;
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getDriverPercentage(java.lang.Integer, com.inthinc.pro.model.Duration)
     */
    @Override
    public Percentage getDriverPercentage(Integer groupID, Duration duration) {
        return null;
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getDriverScores(java.lang.Integer, int)
     */
    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, int aggregationDurationCode) {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getDriverScores(java.lang.Integer, com.inthinc.pro.model.AggregationDuration)
     */
    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, AggregationDuration aggregationDuration) {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getDriverScores(java.lang.Integer, com.inthinc.pro.model.Duration)
     */
    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Duration duration) {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getDriverScores(java.lang.Integer, org.joda.time.DateTime, org.joda.time.DateTime)
     */
    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, DateTime startTime, DateTime endTime) {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getVehicleScores(java.lang.Integer, org.joda.time.DateTime, org.joda.time.DateTime)
     */
    @Override
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, DateTime startTime, DateTime endTime) {
        return Collections.emptyList();
    }
    
    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getDriverScores(java.lang.Integer, org.joda.time.Interval)
     */
    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Interval interval) {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getVehicleScores(java.lang.Integer, org.joda.time.Interval)
     */
    @Override
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Interval interval) {
        return Collections.emptyList();
    }
    
    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getDriverScores(java.lang.Integer, org.joda.time.DateTime)
     */
    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, DateTime day) {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getVehicleScores(java.lang.Integer, org.joda.time.DateTime)
     */
    @Override
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, DateTime day) {
        return Collections.emptyList();
    }
    
    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getSubGroupsAggregateDriverScores(java.lang.Integer, com.inthinc.pro.model.Duration)
     */
    @Override
    public List<GroupScoreWrapper> getSubGroupsAggregateDriverScores(Integer groupID, Duration duration) {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getSubGroupsAggregateDriverTrends(java.lang.Integer, com.inthinc.pro.model.Duration)
     */
    @Override
    public List<GroupTrendWrapper> getSubGroupsAggregateDriverTrends(Integer groupID, Duration duration) {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getVehicleScores(java.lang.Integer, com.inthinc.pro.model.Duration)
     */
    @Override
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Duration duration) {
        return Collections.emptyList();
    }

	@Override
	public Integer delete(Group group) {
		// TODO Auto-generated method stub
		return group.getGroupID();
	}

	@Override
	public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, int aggregationDurationCode) {
		return Collections.emptyList();
	}

	@Override
	public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, AggregationDuration aggregationDuration) {
		return Collections.emptyList();
	}

}
