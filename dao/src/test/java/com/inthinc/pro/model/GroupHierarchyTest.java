package com.inthinc.pro.model;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.GroupType;

public class GroupHierarchyTest{

    private static final Logger logger = Logger.getLogger(GroupHierarchyTest.class);
    private GroupHierarchy groupHierarchy;
    private List<Group> list;
    private int groupID;
    
    public GroupHierarchyTest() {
		super();
		// TODO Auto-generated constructor stub
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
    			
    			list.add(subGroup);
    	    	System.out.println("GroupId is: "+subGroup.getGroupID()+", parentId is: "+subGroup.getParentID()+", level is: "+level
    	    	                   +", GroupName is: "+subGroup.getName());
    			addGroups(subGroup, level);
    		}
    	}
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
		list.add(topGroup);
    	System.out.println("GroupId is: "+topGroup.getGroupID()+", parentId is: "+topGroup.getParentID()+", level is: "+0
    	                   +", GroupName is: "+topGroup.getName());
		addGroups(topGroup, 0);
		
		groupHierarchy = new GroupHierarchy();
		groupHierarchy.setGroupList(list);
		
    }

    @After
    public void tearDown() throws Exception
    {
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
		System.out.println("topGroup is: "+topGroup);
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
	
	@Test
	public void testCompanyGroup(){
		//the company groups are the 2nd level groups when the account settings have multipleCompanies set to true.
		Group companyGroup = groupHierarchy.getCompanyGroup(63);
		Integer companyGroupID = companyGroup.getGroupID();
		assertEquals("companyGroupID should be 33",33,companyGroupID.intValue());

		companyGroup = groupHierarchy.getCompanyGroup(18);
		companyGroupID = companyGroup.getGroupID();
		assertEquals("companyGroupID should be 2",2,companyGroupID.intValue());

		companyGroup = groupHierarchy.getCompanyGroup(8);
		companyGroupID = companyGroup.getGroupID();
		assertEquals("companyGroupID should be 2",2,companyGroupID.intValue());

		companyGroup = groupHierarchy.getCompanyGroup(2);
		companyGroupID = companyGroup.getGroupID();
		assertEquals("companyGroupID should be 2",2,companyGroupID.intValue());

		companyGroup = groupHierarchy.getCompanyGroup(1);
		companyGroupID = companyGroup.getGroupID();
		assertEquals("companyGroupID should be 1",1,companyGroupID.intValue());
}
 }
