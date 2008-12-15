package com.inthinc.pro.backing.model;

import static org.junit.Assert.fail;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.inthinc.pro.backing.BaseBeanTest;
import com.inthinc.pro.model.Group;

public class GroupHierarchyTest{

    private static final Logger logger = Logger.getLogger(BaseBeanTest.class);
    protected ApplicationContext applicationContext;
    private GroupHierarchy groupHierarchy;
    private List<Group> list;
    private int groupID;
    
    public GroupHierarchyTest() {
		super();
		// TODO Auto-generated constructor stub
	}

    private void addGroups(Group group, int level){
    	
    	if (level < 5 ){
    		
    		
    		level+=1;
    		for (int j=0; j<2; j++){
    			
    			groupID+=1;
    			
    			Group subGroup = new Group();
    			subGroup.setGroupID(groupID);
    			subGroup.setParentID(group.getGroupID());
    			list.add(subGroup);
    	    	System.out.println("GroupId is: "+subGroup.getGroupID()+", parentId is: "+subGroup.getParentID()+", level is: "+level);
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
		list.add(topGroup);
    	System.out.println("GroupId is: "+topGroup.getGroupID()+", parentId is: "+topGroup.getParentID()+", level is: "+0);
		addGroups(topGroup, 0);
		
		groupHierarchy = new GroupHierarchy();
		groupHierarchy.setGroupList(list);
		
    }

    @After
    public void tearDown() throws Exception
    {
    }

	@Test
	public void testGetTopGroup() {
		
		Integer topGroupID = 1;
		Group topGroup = groupHierarchy.getTopGroup();
		System.out.println("topGroup is: "+topGroup);
		assertEquals(topGroup.getGroupID(),topGroupID);

	}

	@Test
	public void testGetGroupLevelInteger() {

		Group topGroup = groupHierarchy.getTopGroup();
		assertEquals(groupHierarchy.getGroupLevel(topGroup.getGroupID()),GroupLevel.FLEET);
		assertEquals(groupHierarchy.getGroupLevel(groupHierarchy.getGroupList().get(1).getGroupID()),GroupLevel.DIVISION);
		assertEquals(groupHierarchy.getGroupLevel(groupHierarchy.getGroupList().get(6).getGroupID()),GroupLevel.TEAM);
	}

	@Test
	public void testGetGroup() {

		assertEquals(groupHierarchy.getGroup(1).getGroupID(),new Integer(1));
		assertEquals(groupHierarchy.getGroup(63).getGroupID(),new Integer(63));
		assertEquals(groupHierarchy.getGroup(1).getGroupID(),new Integer(1));
		assertEquals(groupHierarchy.getGroup(123),null);
	}

	@Test
	public void testGetGroupLevelGroup() {
		Group topGroup = groupHierarchy.getTopGroup();
		assertEquals(groupHierarchy.getGroupLevel(topGroup),GroupLevel.FLEET);
		assertEquals(groupHierarchy.getGroupLevel(groupHierarchy.getGroupList().get(1)),GroupLevel.DIVISION);
		assertEquals(groupHierarchy.getGroupLevel(groupHierarchy.getGroupList().get(6)),GroupLevel.TEAM);
	}

//	@Test
//	public void testGroupHasChildren(){
//		
//		assertEquals(true, groupHierarchy.groupHasChildren(groupHierarchy.getGroup(2)));
//		assertEquals(false,groupHierarchy.groupHasChildren(groupHierarchy.getGroup(6)));
//	}
	
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
 }
