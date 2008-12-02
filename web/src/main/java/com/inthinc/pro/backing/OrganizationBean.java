package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.backing.model.GroupTreeNode;
import com.inthinc.pro.backing.model.GroupNode;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Group;


public class OrganizationBean extends BaseBean{
	
	private GroupDAO groupDAO;
	
	private List<GroupTreeNode> groupHierarchyList;
	
	private GroupTreeNode topLevelNode;
	
	private GroupHierarchy gh;
	
	public OrganizationBean(){
		
	}

	public GroupDAO getGroupDAO() {
		return groupDAO;

	}

	public List<GroupTreeNode> getGroupNodeList(){ 
		if(gh == null){
			gh = new GroupHierarchy(groupDAO.getGroupHierarchy(1, 101));
			groupHierarchyList = new ArrayList<GroupTreeNode>();
			Group rootGroup = gh.getTopGroup();
			List<Group> topLevelGroupList = gh.getChildren(rootGroup);
			
			
			for(Group group:topLevelGroupList){
				groupHierarchyList.add(new GroupTreeNode(new GroupNode(rootGroup,gh)));
			}
			
		}
		return groupHierarchyList;
	}
	
	public GroupTreeNode getTopLevelNode(){
		if(topLevelNode == null){
			gh = new GroupHierarchy(groupDAO.getGroupHierarchy(1, 101));
			groupHierarchyList = new ArrayList<GroupTreeNode>();
			Group rootGroup = gh.getTopGroup();
			topLevelNode = new GroupTreeNode(new GroupNode(rootGroup,gh));
		}
		
		return topLevelNode;
	}

	
	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}
	
	public String getAccountName(){
		return getAccountID().toString();
	}
	
	
	
}
