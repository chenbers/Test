package com.inthinc.pro.backing.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;


import org.apache.log4j.Logger;
import org.richfaces.model.SwingTreeNodeImpl;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LatLng;

public class GroupTreeNode extends SwingTreeNodeImpl implements Serializable{
	
	private static final long serialVersionUID = 6735997209608844223L;
	
	private Group group;
	private GroupTreeNode parentGroupTreeNode;
	private List<GroupTreeNode> childGroupTreeNodes;
	private GroupHierarchy groupHierarchyUtil;
	private GroupLevel groupLevel;
	
	private VehicleDAO vehicleDAO;
	private DriverDAO driverDAO;
	
	//We are storing these values here because once loaded, we don't want to have to pull them again.


	private static final Logger logger = Logger.getLogger(GroupTreeNode.class);
	
	/*
	 * Make sure that any group that is passed in has a parent. For some reason the tree view doesn't render
	 * correctly if there is no parent.
	 */
	public GroupTreeNode(Group group, GroupHierarchy groupHierarchy){
		this.group = group;
		this.groupHierarchyUtil = groupHierarchy;
		this.setData(this);
	}
	
	public GroupTreeNode(Group group, GroupHierarchy groupHierarchy,GroupTreeNode parent) {
		this.group = group;
		this.groupHierarchyUtil = groupHierarchy;
		this.parentGroupTreeNode = parent;
		this.setData(this);
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getType() {
		String result = null;
		if(groupLevel != null){
			result = groupLevel.getDescription();
		}else{
			result = "";
		}
		return result;
	}
	
	public GroupLevel getGroupLevel(){
		if(groupLevel == null && group != null){
			groupLevel = groupHierarchyUtil.getGroupLevel(group);
		}
		return groupLevel;
	}
//	
	public Group getGroup() {
		return group;
	}
	
	public List<GroupTreeNode> getGroupBreadCrumb(){
		List<GroupTreeNode> breadCrumbList = new ArrayList<GroupTreeNode>();
		loadBreadCrumbs(this, breadCrumbList);
		Collections.reverse(breadCrumbList);
		return breadCrumbList;
	}
	
	private void loadBreadCrumbs(GroupTreeNode node,List<GroupTreeNode> breadCrumbList){
		breadCrumbList.add(node);
		if(node.getParent() != null){
			loadBreadCrumbs(node.getParent(),breadCrumbList);
		}
	}
	
	public void addChildNode(GroupTreeNode groupTreeNode){
		if(childGroupTreeNodes == null){
			loadChildNodes();
		}
		
		childGroupTreeNodes.add(groupTreeNode);
	}
	
	public void setParent(GroupTreeNode parentGroupTreeNode) {
		if(parentGroupTreeNode.getGroup() != null){
			this.group.setParentID(parentGroupTreeNode.getGroup().getGroupID());
			parentGroupTreeNode.addChildNode(this);
			this.parentGroupTreeNode = parentGroupTreeNode;
		}
	}
	
	/**
	 * TreeNode Implementation
	 */
	
	@Override
	public Enumeration children() {
		if(childGroupTreeNodes == null){
			loadChildNodes();
		}
		return Collections.enumeration(childGroupTreeNodes);
	}
	
	@Override
	public boolean getAllowsChildren() {
		boolean result;
		if(groupLevel == null){
			getGroupLevel();
		}
		if(groupLevel == GroupLevel.TEAM){
			result = false;
		}else{
			result = true;
		}
		return result;
	}
	
	@Override
	public GroupTreeNode getChildAt(int childIndex) {
		if(childGroupTreeNodes == null){
			loadChildNodes();
		}
		return childGroupTreeNodes.get(childIndex);
	}
	
	@Override
	public int getChildCount() {
		if(childGroupTreeNodes == null){
			loadChildNodes();
		}
		return childGroupTreeNodes.size();
	}
	
	@Override
	public int getIndex(javax.swing.tree.TreeNode node) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public GroupTreeNode getParent() {
		
		if (parentGroupTreeNode == null && group != null) {
			Group parentGroup = groupHierarchyUtil.getParentGroup(group);
			if(parentGroup != null){
				parentGroupTreeNode = new GroupTreeNode(parentGroup, groupHierarchyUtil);
			}else{
				parentGroupTreeNode = new GroupTreeNode(null,groupHierarchyUtil);
			}
		}
			
		return parentGroupTreeNode;
		
	}
	
	@Override
	public boolean isLeaf() {
		boolean result;
		if(group == null){
			result = true;
		} else {
			if(childGroupTreeNodes == null){
				loadChildNodes();
			}
			result = childGroupTreeNodes.size()>0?false:true;
		}
		return result;
	}
	
	private List<GroupTreeNode> loadChildNodes(){
		List<GroupTreeNode> childNodes = new ArrayList<GroupTreeNode>();
		 if(group == null && groupHierarchyUtil.getGroupList().size() > 0){
			 childNodes.add(new GroupTreeNode(groupHierarchyUtil.getTopGroup(),groupHierarchyUtil));		 }

		List<Group> groupList = groupHierarchyUtil.getChildren(group);
		if (groupList != null) {
			for (Group g : groupList) {
				if(g.getMapCenter() == null){ //Set the initial view to that of the parent
					g.setMapCenter(new LatLng(group.getMapCenter().getLat(),group.getMapCenter().getLng()));
					g.setMapZoom(group.getMapZoom());
				}
				childNodes.add(new GroupTreeNode(g, groupHierarchyUtil,this));
			}
		}
		childGroupTreeNodes = childNodes;
		return childGroupTreeNodes;
	}

	
	
}

