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
import com.inthinc.pro.model.GroupHierarchy;
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
    public Score getAggregateDriverScore(Integer groupID, AggregationDuration duration) {
        return null;
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getAggregateDriverScore(java.lang.Integer, org.joda.time.Interval)
     */
    public Score getAggregateDriverScore(Integer groupID, Interval interval) {
        return null;
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getAggregateDriverScore(java.lang.Integer, org.joda.time.DateTime, org.joda.time.DateTime)
     */
    public Score getAggregateDriverScore(Integer groupID, DateTime startTime, DateTime endTime) {
        return null;
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getDriverPercentage(java.lang.Integer, com.inthinc.pro.model.Duration)
     */
    public Percentage getDriverPercentage(Integer groupID, Duration duration) {
        return null;
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getDriverScores(java.lang.Integer, int)
     */
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, int aggregationDurationCode) {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getDriverScores(java.lang.Integer, com.inthinc.pro.model.AggregationDuration)
     */
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, AggregationDuration aggregationDuration) {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getDriverScores(java.lang.Integer, com.inthinc.pro.model.Duration)
     */
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Duration duration) {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getDriverScores(java.lang.Integer, org.joda.time.DateTime, org.joda.time.DateTime)
     */
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, DateTime startTime, DateTime endTime) {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getVehicleScores(java.lang.Integer, org.joda.time.DateTime, org.joda.time.DateTime)
     */
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, DateTime startTime, DateTime endTime) {
        return Collections.emptyList();
    }
    
    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getDriverScores(java.lang.Integer, org.joda.time.Interval)
     */
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Interval interval) {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getVehicleScores(java.lang.Integer, org.joda.time.Interval)
     */
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Interval interval) {
        return Collections.emptyList();
    }
    
    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getDriverScores(java.lang.Integer, org.joda.time.DateTime)
     */
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, DateTime day) {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getVehicleScores(java.lang.Integer, org.joda.time.DateTime)
     */
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, DateTime day) {
        return Collections.emptyList();
    }
    
    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getSubGroupsAggregateDriverScores(java.lang.Integer, com.inthinc.pro.model.Duration)
     */
    public List<GroupScoreWrapper> getSubGroupsAggregateDriverScores(Integer groupID, Duration duration) {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getSubGroupsAggregateDriverTrends(java.lang.Integer, com.inthinc.pro.model.Duration)
     */
    public List<GroupTrendWrapper> getSubGroupsAggregateDriverTrends(Integer groupID, Duration duration) {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.GroupReportDAO#getVehicleScores(java.lang.Integer, com.inthinc.pro.model.Duration)
     */
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Duration duration) {
        return Collections.emptyList();
    }

	@Override
	public Integer delete(Group group) {
		// TODO Auto-generated method stub
		return group.getGroupID();
	}

	public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, int aggregationDurationCode) {
		return Collections.emptyList();
	}

	public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, AggregationDuration aggregationDuration) {
		return Collections.emptyList();
	}

    @Override
    public Score getAggregateDriverScore(Integer groupID, AggregationDuration duration, GroupHierarchy gh) {
     // TODO temporarily calling OLD (non GroupHierarch stub), assuming Bill has his own version of GroupDaoStub
        return getAggregateDriverScore(groupID, duration);
    }

    @Override
    public Score getAggregateDriverScore(Integer groupID, Interval interval, GroupHierarchy gh) {
     // TODO temporarily calling OLD (non GroupHierarch stub), assuming Bill has his own version of GroupDaoStub
        return getAggregateDriverScore(groupID, interval);
    }

    @Override
    public Score getAggregateDriverScore(Integer groupID, DateTime startTime, DateTime endTime, GroupHierarchy gh) {
     // TODO temporarily calling OLD (non GroupHierarch stub), assuming Bill has his own version of GroupDaoStub
        return getAggregateDriverScore(groupID, startTime, endTime);
    }

    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, int aggregationDurationCode, GroupHierarchy gh) {
     // TODO temporarily calling OLD (non GroupHierarch stub), assuming Bill has his own version of GroupDaoStub
        return getDriverScores(groupID, aggregationDurationCode);
    }

    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, AggregationDuration aggregationDuration, GroupHierarchy gh) {
     // TODO temporarily calling OLD (non GroupHierarch stub), assuming Bill has his own version of GroupDaoStub
        return getDriverScores(groupID, aggregationDuration);
    }

    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Duration duration, GroupHierarchy gh) {
     // TODO temporarily calling OLD (non GroupHierarch stub), assuming Bill has his own version of GroupDaoStub
        return getDriverScores(groupID, duration);
    }

    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, DateTime startTime, DateTime endTime, GroupHierarchy gh) {
     // TODO temporarily calling OLD (non GroupHierarch stub), assuming Bill has his own version of GroupDaoStub
        return getDriverScores(groupID, startTime, endTime);
    }

    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Interval interval, GroupHierarchy gh) {
     // TODO temporarily calling OLD (non GroupHierarch stub), assuming Bill has his own version of GroupDaoStub
        return getDriverScores(groupID, interval);
    }

    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, DateTime day, GroupHierarchy gh) {
     // TODO temporarily calling OLD (non GroupHierarch stub), assuming Bill has his own version of GroupDaoStub
        return getDriverScores(groupID, day);
    }

    @Override
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Duration duration, GroupHierarchy gh) {
     // TODO temporarily calling OLD (non GroupHierarch stub), assuming Bill has his own version of GroupDaoStub
        return getVehicleScores(groupID, duration);
    }

    @Override
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, int aggregationDurationCode, GroupHierarchy gh) {
     // TODO temporarily calling OLD (non GroupHierarch stub), assuming Bill has his own version of GroupDaoStub
        return getVehicleScores(groupID, aggregationDurationCode);
    }

    @Override
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, AggregationDuration aggregationDuration, GroupHierarchy gh) {
     // TODO temporarily calling OLD (non GroupHierarch stub), assuming Bill has his own version of GroupDaoStub
        return getVehicleScores(groupID, aggregationDuration);
    }

    @Override
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Interval interval, GroupHierarchy gh) {
     // TODO temporarily calling OLD (non GroupHierarch stub), assuming Bill has his own version of GroupDaoStub
        return getVehicleScores(groupID, interval);
    }

    @Override
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, DateTime day, GroupHierarchy gh) {
     // TODO temporarily calling OLD (non GroupHierarch stub), assuming Bill has his own version of GroupDaoStub
        return getVehicleScores(groupID, day);
    }

    @Override
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, DateTime startTime, DateTime endTime, GroupHierarchy gh) {
     // TODO temporarily calling OLD (non GroupHierarch stub), assuming Bill has his own version of GroupDaoStub
        return getVehicleScores(groupID, startTime, endTime);
    }

    @Override
    public List<GroupTrendWrapper> getSubGroupsAggregateDriverTrends(Integer groupID, Duration duration, GroupHierarchy gh) {
     // TODO temporarily calling OLD (non GroupHierarch stub), assuming Bill has his own version of GroupDaoStub
        return getSubGroupsAggregateDriverTrends(groupID, duration);
    }

    @Override
    public Percentage getDriverPercentage(Integer groupID, Duration duration, GroupHierarchy gh) {
     // TODO temporarily calling OLD (non GroupHierarch stub), assuming Bill has his own version of GroupDaoStub
        return getDriverPercentage(groupID, duration);
    }

    @Override
    public List<GroupScoreWrapper> getSubGroupsAggregateDriverScores(Integer groupID, Duration duration, GroupHierarchy gh) {
        // TODO temporarily calling OLD (non GroupHierarch stub), assuming Bill has his own version of GroupDaoStub
        return getSubGroupsAggregateDriverScores(groupID, duration);
    }

}
