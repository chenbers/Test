package com.inthinc.pro.backing.model;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.Group;

public class GroupNode {

	private Group group;
	private GroupNode parentGroupNode;
	private List<GroupNode> childGroupNodes;
	private GroupHierarchy groupHierarchyUtil;
	private GroupLevel groupLevel;

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
}
