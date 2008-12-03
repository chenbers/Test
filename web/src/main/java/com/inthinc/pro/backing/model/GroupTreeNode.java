package com.inthinc.pro.backing.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;


import org.apache.log4j.Logger;
import org.richfaces.model.SwingTreeNodeImpl;

import com.inthinc.pro.model.Group;

public class GroupTreeNode extends SwingTreeNodeImpl{
	
	private Group group;
	private GroupLevel groupLevel;
	private GroupNode groupNode;
	public GroupNode getGroupNode() {
		return groupNode;
	}

	public void setGroupNode(GroupNode groupNode) {
		this.groupNode = groupNode;
	}

	private static final Logger logger = Logger.getLogger(GroupTreeNode.class);
	
	//Child going to loaded lazily
	private List<GroupTreeNode> childrenNodes;
	
	/*
	 * Make sure that any group that is passed in has a parent. For some reason the tree view doesn't render
	 * correctly if there is no parent.
	 */
	public GroupTreeNode(GroupNode groupNode){
		this.groupLevel = groupNode.getGroupLevel();
		this.group = groupNode.getGroup();
		this.groupNode = groupNode;
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
		return this.groupLevel;
	}
//	
	public Group getGroup() {
		return group;
	}
	
	//TreeNode implementation
	
	@Override
	public Enumeration children() {
		if(childrenNodes == null){
			loadChildNodes();
		}
		return Collections.enumeration(childrenNodes);
	}
	
	@Override
	public boolean getAllowsChildren() {
		boolean result;
		if(groupLevel == GroupLevel.TEAM){
			result = false;
		}else{
			result = true;
		}
		return result;
	}
	
	@Override
	public GroupTreeNode getChildAt(int childIndex) {
		if(childrenNodes == null){
			loadChildNodes();
		}
		
		return childrenNodes.get(childIndex);
	}
	
	@Override
	public int getChildCount() {
		return groupNode.getChildCount();
	}
	
	@Override
	public int getIndex(javax.swing.tree.TreeNode node) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public GroupTreeNode getParent() {
		
		GroupTreeNode gn = null;
		
		if(groupNode != null)	{		
			GroupNode parentGroup = groupNode.getParentGroupNode();
			if(parentGroup != null){
				gn = new GroupTreeNode(parentGroup);
			} else {
				//This should mean that the current node is the root node and for some reason the tree control
				//won't render if there is no parent to the node tied to the "value" attribute"
				gn = new GroupTreeNode(new GroupNode(null,groupNode.getGroupHierarchyUtil()));
			}
		}	
		return gn;
		
	}
	
	@Override
	public boolean isLeaf() {
		boolean result;
		if(group == null){
			result = true;
		} else {
			result = groupNode.hasChildren()?false:true;
		}
		return result;
	}
	
	private void loadChildNodes(){
		childrenNodes = new ArrayList<GroupTreeNode>();
		for(GroupNode group:groupNode.getChildGroupNodes()){
			childrenNodes.add(new GroupTreeNode(group));
		}
	}
	
	
}

