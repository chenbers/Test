package com.inthinc.pro.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GroupHierarchyTest{

    private static final Logger logger = Logger.getLogger(GroupHierarchyTest.class);
    private GroupHierarchy groupHierarchy;
    private List<Group> list;
    private int groupID;
    private static final String addressFormat = "123 {0} Street, {1}, 12345";
    
    public GroupHierarchyTest() {
		super();
	}

    
    private void addGroups(Group group, int level){
    	
    	if (level < 5 ){
    		
    		
    		level++;
    		for (int j=0; j<2; j++){
    			
    			groupID+=1;
    			
    			Group subGroup = new Group();
    			subGroup.setGroupID(groupID);
    			if(level == 5)
    			{
    			    subGroup.setType(GroupType.TEAM);
    			}else
    			{
    			    subGroup.setType(GroupType.DIVISION);
    			}
    			subGroup.setParentID(group.getGroupID());
    			subGroup.setName("Group_"+groupID);
    			subGroup.setAddress(initAddress(subGroup));
    			
    			list.add(subGroup);
    			addGroups(subGroup, level);
    		}
    	}
    }
    
    
    private Address initAddress(Group group) {
        Address address = new Address();
        address.setAccountID(group.getAccountID());
        address.setAddr1("123 " +group.getName() + " Street");
        address.setCity(group.getName());
//        address.setState(State.valueOf(10));
        address.setZip("12345");
        address.setAddrID(group.getGroupID());
        
        return address;
    }
	@Before
    public void setUp() throws Exception
    {
		list = new ArrayList<Group>();
		groupID=1;
		Group topGroup = new Group();
		topGroup.setParentID(0);
		topGroup.setGroupID(groupID);
		topGroup.setType(GroupType.FLEET);
		topGroup.setName("Group_1");
		topGroup.setAddress(initAddress(topGroup));
		list.add(topGroup);
		addGroups(topGroup, 0);
		
		groupHierarchy = new GroupHierarchy();
		groupHierarchy.setGroupList(list);
		
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void mainAndTerminalAddresses() {
        
        Group fleetGroup = groupHierarchy.getTopGroup();
        fleetGroup.setDotOfficeType(DOTOfficeType.MAIN);
        String expectedMainAddress = MessageFormat.format(addressFormat, new String[] {fleetGroup.getName(), fleetGroup.getName()});
        for (Group divisionGroup : groupHierarchy.getSubGroupList(fleetGroup.getGroupID())) {
            if (divisionGroup.getGroupID().equals(fleetGroup.getGroupID()) || divisionGroup.getType() != GroupType.DIVISION)
                continue;
            divisionGroup.setDotOfficeType(DOTOfficeType.TERMINAL);
            String expectedTerminalAddress = MessageFormat.format(addressFormat, new String[] {divisionGroup.getName(), divisionGroup.getName()});
            for (Group teamGroup : groupHierarchy.getSubGroupList(divisionGroup.getGroupID())) {
                if (teamGroup.getGroupID().equals(divisionGroup.getGroupID()) || teamGroup.getType() != GroupType.TEAM)
                    continue;
                assertEquals("Main Address", expectedMainAddress, groupHierarchy.getMainAddress(teamGroup.getGroupID()));
                assertEquals("Terminal Address", expectedTerminalAddress, groupHierarchy.getTerminalAddress(teamGroup.getGroupID()));
            }
        }
    }
    @Test
    public void mainAndTerminalAddressesNotSet() {
        
        Group fleetGroup = groupHierarchy.getTopGroup();
        for (Group divisionGroup : groupHierarchy.getSubGroupList(fleetGroup.getGroupID())) {
            if (divisionGroup.getGroupID().equals(fleetGroup.getGroupID()) || divisionGroup.getType() != GroupType.DIVISION)
                continue;
            for (Group teamGroup : groupHierarchy.getSubGroupList(divisionGroup.getGroupID())) {
                if (teamGroup.getGroupID().equals(divisionGroup.getGroupID()) || teamGroup.getType() != GroupType.TEAM)
                    continue;
                assertEquals("Main Address", "", groupHierarchy.getMainAddress(teamGroup.getGroupID()));
                assertEquals("Terminal Address", "", groupHierarchy.getTerminalAddress(teamGroup.getGroupID()));
            }
        }
    }
    @Test
    public void mainSetAndTerminalAddressesNotSet() {
        
        Group fleetGroup = groupHierarchy.getTopGroup();
        fleetGroup.setDotOfficeType(DOTOfficeType.MAIN);
        String expectedMainAddress = MessageFormat.format(addressFormat, new String[] {fleetGroup.getName(), fleetGroup.getName()});
        for (Group divisionGroup : groupHierarchy.getSubGroupList(fleetGroup.getGroupID())) {
            if (divisionGroup.getGroupID().equals(fleetGroup.getGroupID()) || divisionGroup.getType() != GroupType.DIVISION)
                continue;
            for (Group teamGroup : groupHierarchy.getSubGroupList(divisionGroup.getGroupID())) {
                if (teamGroup.getGroupID().equals(divisionGroup.getGroupID()) || teamGroup.getType() != GroupType.TEAM)
                    continue;
                assertEquals("Main Address", expectedMainAddress, groupHierarchy.getMainAddress(teamGroup.getGroupID()));
                assertEquals("Terminal Address", "", groupHierarchy.getTerminalAddress(teamGroup.getGroupID()));
            }
        }
    }
    
    @Test
    public void testGetTopGroup_topGroupNotLowestID() {
    	Integer groupID = 1;
    	list = new ArrayList<Group>();
    	groupHierarchy = new GroupHierarchy();
    	Group topGroup = new Group();
    	topGroup.setParentID(null);
    	topGroup.setGroupID(9);
    	topGroup.setName("The Top Group");
    	topGroup.setType(GroupType.FLEET);
    	list.add(topGroup);
    	
    	Group groupWithIdLowerThanTop = new Group();
    	groupWithIdLowerThanTop.setParentID(topGroup.getGroupID());
    	groupWithIdLowerThanTop.setType(GroupType.DIVISION);
    	groupWithIdLowerThanTop.setGroupID(groupID++);
    	list.add(groupWithIdLowerThanTop);
    	
    	groupHierarchy = new GroupHierarchy(list);
    	assertEquals("top group test", topGroup.getGroupID() ,groupHierarchy.getTopGroup().getGroupID());
    	
    }
	@Test
	public void testGetTopGroup() {
		
		Integer topGroupID = 1;
		Group topGroup = groupHierarchy.getTopGroup();
		assertEquals(topGroup.getGroupID(),topGroupID);

	}


	@Test
	public void testGetGroup() {

		assertEquals(groupHierarchy.getGroup(1).getGroupID(),new Integer(1));
		assertEquals(groupHierarchy.getGroup(63).getGroupID(),new Integer(63));
		assertEquals(groupHierarchy.getGroup(1).getGroupID(),new Integer(1));
		assertEquals(groupHierarchy.getGroup(123),null);
	}

	
	@Test
	public void testGetChildren(){
		
		assertEquals(null, groupHierarchy.getChildren(groupHierarchy.getGroup(59)));
		List<Group> children = groupHierarchy.getChildren(groupHierarchy.getGroup(58));
		Group group59 = groupHierarchy.getGroup(59);
		Group group60 = groupHierarchy.getGroup(60);
		assertEquals(2, children.size());
		assertEquals(true, children.contains(group59));
		assertEquals(true, children.contains(group60));
		
		
	}
	
	@Test
    public void testShortGroupName(){      
	    //test 1: with continuation prefix
        String shortGroupName =  groupHierarchy.getShortGroupName(10, groupHierarchy.GROUP_SEPERATOR);
        assertEquals(groupHierarchy.CONTINUATION_PREFIX+groupHierarchy.GROUP_SEPERATOR+"Group_4"+groupHierarchy.GROUP_SEPERATOR+"Group_8"+groupHierarchy.GROUP_SEPERATOR+"Group_10",shortGroupName);
        
        //test 2: without continuation prefix and only one node
        shortGroupName =  groupHierarchy.getShortGroupName(1, groupHierarchy.GROUP_SEPERATOR);
        assertEquals("Group_1",shortGroupName);
        
        //test 3: without continuation prefix and many nodes
        shortGroupName =  groupHierarchy.getShortGroupName(2, groupHierarchy.GROUP_SEPERATOR);
        assertEquals("Group_1"+GroupHierarchy.GROUP_SEPERATOR+"Group_2",shortGroupName);
   
      //test 4: missing group
        shortGroupName =  groupHierarchy.getShortGroupName(0, groupHierarchy.GROUP_SEPERATOR);
        assertTrue(shortGroupName.isEmpty());
    }
 }
