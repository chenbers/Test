package com.inthinc.pro.service.adapters;

import static mockit.Mockit.tearDownMocks;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import mockit.Expectations;
import mockit.Mocked;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupTrendWrapper;

/**
 * Test for the GroupDAOAdapter class
 * 
 * @author dcueva
 */
public class GroupDAOAdapterTest {

	private final Integer GROUP_ID = new Integer(1);
	private final Integer ACCOUNT_ID = new Integer(2);
	private final Group group = new Group();
	
	@Mocked(methods = {"getAccountID", "getGroupID"})
	private GroupDAOAdapter adapterSUT; 
	
	@Mocked private GroupDAO groupDAOMock;	
	@Mocked private GroupReportDAO groupReportDAOMock;
    
	@Before
	public void beforeMethod() {
		adapterSUT.setGroupDAO(groupDAOMock);
		adapterSUT.setGroupReportDAO(groupReportDAOMock);
	}
	
	@After
	public void afterMethod(){
		tearDownMocks();
	}
	
	@Test
	public void testGetAll(){
		final List<Group> groupList = new ArrayList<Group>();
		groupList.add(group);
		
		new Expectations(){{
			adapterSUT.getAccountID(); returns(ACCOUNT_ID);
			adapterSUT.getGroupID(); returns(GROUP_ID);
			groupDAOMock.getGroupHierarchy(ACCOUNT_ID, GROUP_ID); returns(groupList);
		}};
		assertEquals(adapterSUT.getAll(), groupList);
	}	
	
	@Test
	public void testGetDAO(){
		assertEquals(adapterSUT.getDAO(), groupDAOMock);
	}		

	@Test
	public void testGetResourceID(){
		group.setGroupID(GROUP_ID);
		
		assertEquals(adapterSUT.getResourceID(group), GROUP_ID);
	}
	
	@Test
	public void testGetChildGroupsDriverScores(){
		final List<GroupScoreWrapper> scoreList = new ArrayList<GroupScoreWrapper>();
		scoreList.add(new GroupScoreWrapper());
		
		new Expectations(){{
			groupReportDAOMock.getSubGroupsAggregateDriverScores(GROUP_ID, Duration.SIX, (GroupHierarchy)any); returns(scoreList);
		}};
		assertEquals(adapterSUT.getChildGroupsDriverScores(GROUP_ID, Duration.SIX), scoreList);
	}
	
	@Test
	public void testGetChildGroupsDriverTrends(){
		final List<GroupTrendWrapper> trendList = new ArrayList<GroupTrendWrapper>();
		trendList.add(new GroupTrendWrapper());
		
		new Expectations(){{
			groupReportDAOMock.getSubGroupsAggregateDriverTrends(GROUP_ID, Duration.SIX, (GroupHierarchy)any); returns(trendList);
		}};
		assertEquals(adapterSUT.getChildGroupsDriverTrends(GROUP_ID, Duration.SIX), trendList);
	}

	@Test
	public void getDriverScores(){
		final List<DriverVehicleScoreWrapper> scoreList = new ArrayList<DriverVehicleScoreWrapper>();
		scoreList.add(new DriverVehicleScoreWrapper());
		
		new Expectations(){{
			groupReportDAOMock.getDriverScores(GROUP_ID, Duration.SIX, (GroupHierarchy)any); returns(scoreList);
		}};
		assertEquals(adapterSUT.getDriverScores(GROUP_ID, Duration.SIX), scoreList);
	}	

	@Test
	public void getVehicleScores(){
		final List<DriverVehicleScoreWrapper> scoreList = new ArrayList<DriverVehicleScoreWrapper>();
		scoreList.add(new DriverVehicleScoreWrapper());
		
		new Expectations(){{
			groupReportDAOMock.getVehicleScores(GROUP_ID, Duration.SIX, (GroupHierarchy)any); returns(scoreList);
		}};
		assertEquals(adapterSUT.getVehicleScores(GROUP_ID, Duration.SIX), scoreList);
	}		
	
}
