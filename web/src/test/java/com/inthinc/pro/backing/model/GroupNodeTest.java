package com.inthinc.pro.backing.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.model.Group;

import static org.junit.Assert.*;


public class GroupNodeTest {
	
	private static final Logger logger = Logger.getLogger(GroupNodeTest.class);
	private GroupTreeNode groupNode;
	private GroupHierarchy groupHierarchy;
	
	@Before
	public void setUp(){
		List<Group> groupList = new ArrayList<Group>();
		groupList.add(new Group(101,1,"United States",0));
		groupList.add(new Group(102,1,"East",101));
		groupList.add(new Group(103,1,"Utah",102));
		groupList.add(new Group(104,1,"California",102));
		groupList.add(new Group(105,1,"Oregon",102));
		groupList.add(new Group(106,1,"Washington",102));
		groupList.add(new Group(107,1,"Denver",102));
		groupList.add(new Group(108,1,"Arizona",102));
		
		groupHierarchy = new GroupHierarchy(groupList);
		
		//groupNode = new GroupTreeNode(groupHierarchy.getTopGroup());
	}
	
	@Test
	public void testChildren(){
		int childrenCount  = groupNode.getChildCount();
		logger.info("Child count: " + childrenCount);
		assertEquals(childrenCount,1);
		
		GroupTreeNode childGroup = (GroupTreeNode)groupNode.getChildAt(0);
		
		logger.info("Child ID: " + childGroup.getGroup().getGroupID());
		
		
		
		
		
	}
	
	
	
	
	

}
