package com.inthinc.pro.backing.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.inthinc.pro.model.Group;

public class GroupNode implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8051275693741193596L;
	
	private Group group;
	private GroupNode parentGroupNode;
	private List<GroupNode> childGroupNodes;
	private GroupHierarchy groupHierarchyUtil;
	private GroupLevel groupLevel;
	private GroupNode fleetNode;

	public GroupNode(Group group, GroupHierarchy groupHierarchy) {
		this.group = group;
		this.groupHierarchyUtil = groupHierarchy;
	}
	
	public GroupNode(Group group, GroupHierarchy groupHierarchy,GroupNode parent) {
		this.group = group;
		this.groupHierarchyUtil = groupHierarchy;
		this.parentGroupNode = parent;
	}

	public int getChildCount() {
		if (childGroupNodes == null) {
			loadChildGroupNodes(groupHierarchyUtil.getChildren(group));
		}
		return childGroupNodes.size();
	}

	public GroupNode getParentGroupNode() {
		if (parentGroupNode == null && group != null) {
			Group parentGroup = groupHierarchyUtil
			.getParentGroup(group);
			if(parentGroup != null){
				parentGroupNode = new GroupNode(parentGroup, groupHierarchyUtil);
			}
		}
		return parentGroupNode;
	}

	public List<GroupNode> getChildGroupNodes() {
		if (childGroupNodes == null) {
			loadChildGroupNodes(groupHierarchyUtil.getChildren(group));
		}
		return childGroupNodes;
	}

	public boolean hasChildren() {
		if (childGroupNodes == null) {
			loadChildGroupNodes(groupHierarchyUtil.getChildren(group));
		}

		return childGroupNodes.size() > 0 ? true : false;
	}

	private void loadChildGroupNodes(List<Group> groupList) {
		 List<GroupNode> childNodes = new ArrayList<GroupNode>();
		 if(group == null && groupHierarchyUtil.getGroupList().size() > 0){
			 childNodes.add(new GroupNode(groupHierarchyUtil.getTopGroup(),groupHierarchyUtil));		 }

		
		if (groupList != null) {
			for (Group g : groupList) {
				childNodes.add(new GroupNode(g, groupHierarchyUtil,this));
			}
		}
		childGroupNodes = childNodes;
	}
	
	public List<GroupNode> getGroupBreadCrumb(){
		List<GroupNode> breadCrumbList = new ArrayList<GroupNode>();
		loadBreadCrumbs(this, breadCrumbList);
		Collections.reverse(breadCrumbList);
		return breadCrumbList;
	}
	
	private void loadBreadCrumbs(GroupNode node,List<GroupNode> breadCrumbList){
		breadCrumbList.add(node);
		if(node.getParentGroupNode() != null){
			loadBreadCrumbs(node.getParentGroupNode(),breadCrumbList);
		}
	}
	
	private GroupNode getFleet(GroupNode groupNode){
		GroupNode result = null;
		if(groupNode.getGroup().getParentID() == 0){
			result = groupNode;
		}else{
			result = getFleet(groupNode.getParentGroupNode());
		}	
		
		return result;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public GroupHierarchy getGroupHierarchyUtil() {
		return groupHierarchyUtil;
	}

	public void setGroupHierarchyUtil(GroupHierarchy groupHierarchyUtil) {
		this.groupHierarchyUtil = groupHierarchyUtil;
	}

	public GroupLevel getGroupLevel() {
		if(groupLevel == null && group != null){
			groupLevel = groupHierarchyUtil.getGroupLevel(group);
		}
		return groupLevel;
	}

	public void setGroupLevel(GroupLevel groupLevel) {
		this.groupLevel = groupLevel;
	}

	public void setParentGroupNode(GroupNode parentGroupNode) {
		this.parentGroupNode = parentGroupNode;
	}

	public void setChildGroupNodes(List<GroupNode> childGroupNodes) {
		this.childGroupNodes = childGroupNodes;
	}
	
	public GroupNode getFleetNode() {
		if(fleetNode == null){
			fleetNode = getFleet(this);
		}
		return fleetNode;
	}
	
	

	public void setFleetNode(GroupNode fleet) {
		this.fleetNode = fleet;
	}
}
