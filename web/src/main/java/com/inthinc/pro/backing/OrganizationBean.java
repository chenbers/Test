package com.inthinc.pro.backing;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.richfaces.component.UITree;
import org.richfaces.event.NodeSelectedEvent;
import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.backing.model.GroupLevel;
import com.inthinc.pro.backing.model.GroupTreeNode;
import com.inthinc.pro.backing.model.GroupNode;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.model.Group;


public class OrganizationBean extends BaseBean{
	
	private GroupDAO groupDAO;
	private PersonDAO personDAO;

	//Organization Tree Specific
	private List<GroupTreeNode> topLevelNodes;
	private GroupHierarchy organizationHierarchy;
	private GroupNode selectedGroupNode;
	
	//Edit Mode 
	private enum State{VIEW,EDIT,ADD;}
	
	private State groupState;
	
	public OrganizationBean(){
		groupState = State.VIEW;
	}

	public List<GroupTreeNode> getTopLevelNode(){
		if(topLevelNodes == null){
			topLevelNodes = new ArrayList<GroupTreeNode>();
			organizationHierarchy = new GroupHierarchy(groupDAO.getGroupsByAcctID(getAccountID()));
			List<Group> fleetGroups = organizationHierarchy.getGroupsByLevel(GroupLevel.FLEET);
			for(Group group: fleetGroups){
				topLevelNodes.add(new GroupTreeNode(new GroupNode(group,organizationHierarchy)));
			}
			
		}
		
		return topLevelNodes;
	}
	
	
	public boolean adviseNodeSelected(UITree tree){
		if(tree != null && tree.getRowData() != null && selectedGroupNode != null){
			GroupTreeNode object = (GroupTreeNode)tree.getRowData();
			Logger.getLogger(OrganizationBean.class).debug("Tree Group Name: " + object.getGroup().getName());
			if(object.getGroupNode().getGroup().getName().equals(selectedGroupNode.getGroup().getName())){
				groupState = State.VIEW;
				return true;
			}
		}
		return false;
	}
	
	
	/*
	 * We need to make sure that the current node is expanded and all the parents as well
	 * 
	 */
	public boolean adviseNodeExpanded(UITree tree){
		if(tree != null && tree.getRowData() != null && selectedGroupNode != null){
			GroupTreeNode object = (GroupTreeNode)tree.getRowData();
			Logger.getLogger(OrganizationBean.class).debug("Tree Group Name: " + object.getGroup().getName());
			return isNodeExpanded(selectedGroupNode, object.getGroupNode());
		}
		return false;
	}
	
	private boolean isNodeExpanded(GroupNode groupNode,GroupNode toCompareWithNode){
		if(toCompareWithNode.getGroup().getGroupID().equals(groupNode.getGroup().getGroupID())){
			return true;
		}else{
			if(groupNode.getParentGroupNode() != null && groupNode.getParentGroupNode().getGroup() !=  null){
				return isNodeExpanded(groupNode.getParentGroupNode(), toCompareWithNode);
			}
		}
		return false;
	}
	
	public void selectNode(NodeSelectedEvent event){
		UITree tree = (UITree)event.getComponent();
		GroupTreeNode object = (GroupTreeNode)tree.getRowData();
		selectedGroupNode = object.getGroupNode();
	}
	
	public void changeState(){
		String state = (String)getParameter("state");
		if(State.ADD.toString().equals(state)){
			groupState = State.ADD;
		}
		if(State.EDIT.toString().equals(state)){
			groupState = State.EDIT;
		}
		if(State.VIEW.toString().equals(state)){
			groupState = State.VIEW;
		}
	}
	
	public void changeSelectedGroup(javax.faces.event.ActionEvent event){
		groupState = State.VIEW;
	}
	
	private GroupNode getParentGroupNodeById(GroupNode groupNode,Integer id){
		GroupNode result = null;
		if(groupNode.getGroup().getGroupID() == id){
			result = groupNode;
		}else{
			result = getParentGroupNodeById(groupNode.getParentGroupNode(), id);
		}
		
		return result;
	}

	public GroupDAO getGroupDAO() {
		return groupDAO;

	}
	
	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}
	
	public String getAccountName(){
		return getAccountID().toString();
	}
	
	public GroupNode getSelectedGroupNode() {
		return selectedGroupNode;
	}

	public void setSelectedGroupNode(GroupNode selectedGroupNode) {
		this.selectedGroupNode = selectedGroupNode;
	}
	
	public PersonDAO getPersonDAO() {
		return personDAO;
	}

	public void setPersonDAO(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}
	
	public String getGroupState() {
		return groupState.toString();
	}

	
	
}
