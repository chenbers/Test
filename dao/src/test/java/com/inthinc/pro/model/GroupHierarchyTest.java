package com.inthinc.pro.model;

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
    		
    		
    		level+=1;
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
		topGroup.setType(GroupType.FLEET);
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
 }
