package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.richfaces.component.UITree;
import org.richfaces.event.NodeExpandedEvent;
import org.richfaces.event.NodeSelectedEvent;
import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.backing.model.GroupLevel;
import com.inthinc.pro.backing.model.GroupTreeNode;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Vehicle;

/*
 * Notes TODO
 * 
 * Need to update the GroupHierarchy tied to the user when a group has been updated or added.
 * 
 */


public class OrganizationBean extends BaseBean{
	
	private static final Logger logger = Logger.getLogger(OrganizationBean.class);
	
	//Temp
	private int id_counter=300;
	
	//Spring managed beans
	private GroupDAO groupDAO;
	private PersonDAO personDAO;
	private DriverDAO driverDAO;
	private VehicleDAO vehicleDAO;

	//Organization Tree Specific
	private List<GroupTreeNode> topLevelNodes;
	private GroupHierarchy organizationHierarchy;
	private GroupTreeNode selectedGroupNode;
	private Integer selectedGroupVehicleCount;
	private Integer selectedGroupDriverCount;
	

	private Map<Integer, Boolean> treeStateMap = new HashMap<Integer, Boolean>();
	
	//For editing groups
	private enum State{VIEW,EDIT,ADD;}
	private State groupState;
	private Person selectedPerson;
	private GroupTreeNode inProgressGroupNode;
	
	//Driver/Vehicle Search
	private Boolean driverSearchSelected = true;
	private Boolean vehicleSearchSelected = false;
	private List<DriverReportItem> driverData;
	private List<Vehicle> vehicleData;
	private Map<String,TableColumn> driverColumns;
	private Map<String,TableColumn> vehicleColumns;

	public OrganizationBean(){
		groupState = State.VIEW;
		
//		driverColumns.put("employeeID",new TableColumn(true,"Employee ID"));
//		driverColumns.put("employee",new TableColumn(true,"Employee ID"));
//		driverColumns.put("employeeId",new TableColumn(true,"Employee ID"));
		
	}

	public List<GroupTreeNode> getTopLevelNode(){
		if(topLevelNodes == null){
			topLevelNodes = new ArrayList<GroupTreeNode>();
			if(organizationHierarchy == null){
				organizationHierarchy = new GroupHierarchy(groupDAO.getGroupHierarchy(getAccountID(),getGroupHierarchy().getTopGroup().getGroupID()));
			}
			List<Group> fleetGroups = organizationHierarchy.getGroupsByLevel(GroupLevel.FLEET);
			for(Group group: fleetGroups){
				//Set default map view
				group.setMapZoom(3);
				group.setMapCenter(new LatLng(40.2,-111));
				GroupTreeNode topLevelNode = new GroupTreeNode(group,organizationHierarchy);
				setSelectedGroupNode(topLevelNode);
				topLevelNodes.add(topLevelNode);
			}
			
		}
		
		return topLevelNodes;
	}
	
	/*
	 * Not in use - Causes the app to run too slow
	 */
	public boolean adviseNodeSelected(UITree tree){
		if(tree != null && tree.getRowData() != null && selectedGroupNode != null){
			GroupTreeNode object = (GroupTreeNode)tree.getRowData();
			logger.debug("Tree Group Name: " + object.getGroup().getName());
			if(object != null && selectedGroupNode.getGroup() != null && object.getGroup().getName().equals(selectedGroupNode.getGroup().getName())){
				groupState = State.VIEW;
				return true;
			}
		}
		return false;
	}
	
	
	/*
	 * Not in use - Causes the app to run too slow
	 * 
	 */
	public boolean adviseNodeExpanded(UITree tree){
		boolean result = false;
		if(tree != null && tree.getRowData() != null){
			GroupTreeNode object = (GroupTreeNode)tree.getRowData();
			Logger.getLogger(OrganizationBean.class).debug("Tree Group Name: " + object.getGroup().getName());
			
			if(treeStateMap.get(object.getGroup().getGroupID()) != null && treeStateMap.get(object.getGroup().getGroupID())){
				result = true;
			}
		}
		return result;
	}
	
	public void selectNode(NodeSelectedEvent event){
		UITree tree = (UITree)event.getComponent();
		GroupTreeNode groupTreeNode = (GroupTreeNode)tree.getRowData();
		setSelectedGroupNode(groupTreeNode);
	}
	
	public void changeExpandListent(NodeExpandedEvent event){
		UITree tree = (UITree)event.getComponent();
		GroupTreeNode object = (GroupTreeNode)tree.getRowData();
		if(tree.isExpanded()){
			treeStateMap.put(object.getGroup().getGroupID(), Boolean.TRUE);
		}else{
			treeStateMap.put(object.getGroup().getGroupID(), Boolean.FALSE);
		}
	}
	
	public void changeState(){
		String state = (String)getParameter("state");
		if(State.ADD.toString().equals(state)){
			groupState = State.ADD;
			inProgressGroupNode = createNewGroupNode();
		}
		if(State.EDIT.toString().equals(state)){
			groupState = State.EDIT;
			inProgressGroupNode = new GroupTreeNode(new Group(),organizationHierarchy);
			copyGroupTreeNode(selectedGroupNode, inProgressGroupNode);
		}
		if(State.VIEW.toString().equals(state)){
			groupState = State.VIEW;
			inProgressGroupNode = null; //Clear out the group that was being worked on.
		}
	}
	
	public void changeSelectedGroup(javax.faces.event.ActionEvent event){
		groupState = State.VIEW;
	}
	
	public void updateGroupNode(){
		copyGroupTreeNode(inProgressGroupNode, selectedGroupNode);
		groupDAO.update(selectedGroupNode.getGroup());
	}
	
	public void saveGroupNode(){
		///
		
		inProgressGroupNode.setParent(selectedGroupNode);
		treeStateMap.put(selectedGroupNode.getGroup().getGroupID(), true);
		groupDAO.create(inProgressGroupNode.getGroup().getGroupID(), inProgressGroupNode.getGroup());
		
		selectedGroupNode = inProgressGroupNode;
		
		//TODO this is temporary. It would be better to refresh the organizationHierarchy from the DB
		organizationHierarchy.getGroupList().add(inProgressGroupNode.getGroup());
		getProUser().setGroupHierarchy(organizationHierarchy);
		inProgressGroupNode = null;
		topLevelNodes = null;
		changeState();
	}
	
	private void copyGroupTreeNode(GroupTreeNode copyFromNode,GroupTreeNode copyToNode){
		Group group = copyToNode.getGroup();
		group.setAccountID(getAccountID());
		group.setGroupID(copyFromNode.getGroup().getGroupID());
		group.setManagerID(copyFromNode.getGroup().getManagerID());
		group.setName(copyFromNode.getGroup().getName());
		group.setDescription(copyFromNode.getGroup().getDescription());
		group.setCreated(copyFromNode.getGroup().getCreated());
		group.setMapCenter(new LatLng(copyFromNode.getGroup().getMapCenter().getLat(),copyFromNode.getGroup().getMapCenter().getLng()));
		group.setMapZoom(copyFromNode.getGroup().getMapZoom());
	}
	
	private GroupTreeNode createNewGroupNode(){
		Group group = new Group();
		group.setAccountID(getAccountID());
		group.setGroupID(++id_counter);
		group.setMapZoom(3);
		group.setMapCenter(new LatLng(40.2,-111));
		GroupTreeNode newGroupNode = new GroupTreeNode(group,organizationHierarchy);
		return newGroupNode;
	}
	
	public String updateGroupMapValues(){
		//Do nothing
		return null;
	}
	
	/*
	 * TODO: - Mike- We need define who can be assigned to this group. Right now we're bringing all people back that are within
	 * The current users group 
	 */
	public List<SelectItem> getPeopleSelectItems(){
		List<Person> personList = personDAO.getPeopleInGroupHierarchy(getUser().getPerson().getGroupID());
		
		List<SelectItem> selectItems = new ArrayList<SelectItem>(0);
		selectItems.add(new SelectItem(null,""));
		
		for(Person person: personList){
			selectItems.add(new SelectItem(person.getPersonID(),person.getFirst() + " " + person.getLast()));
		}

		return selectItems; 
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
	
	public GroupTreeNode getSelectedGroupNode() {
		return selectedGroupNode;
	}

	public void setSelectedGroupNode(GroupTreeNode selectedGroupNode) {
		selectedGroupDriverCount = driverDAO.getAllDrivers(selectedGroupNode.getGroup().getGroupID()).size();
		selectedGroupVehicleCount = vehicleDAO.getVehiclesInGroupHierarchy(selectedGroupNode.getGroup().getGroupID()).size();
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
	
	public Person getSelectedPerson() {
		if(selectedGroupNode != null){
			selectedPerson = personDAO.findByID(selectedGroupNode.getGroup().getManagerID());
		}
		return selectedPerson;
	}

	public void setSelectedPerson(Person selectedPerson) {
		this.selectedPerson = selectedPerson;
	}
	
	public GroupTreeNode getInProgressGroupNode() {
		return inProgressGroupNode;
	}

	public void setInProgressGroupNode(GroupTreeNode inProgressGroupNode) {
		this.inProgressGroupNode = inProgressGroupNode;
	}
	public Boolean getDriverSearchSelected() {
		return driverSearchSelected;
	}

	public void setDriverSearchSelected(Boolean driverSearchSelected) {
		this.driverSearchSelected = driverSearchSelected;
	}

	public Boolean getVehicleSearchSelected() {
		return vehicleSearchSelected;
	}

	public void setVehicleSearchSelected(Boolean vehicleSearchSelected) {
		this.vehicleSearchSelected = vehicleSearchSelected;
	}

	public DriverDAO getDriverDAO() {
		return driverDAO;
	}

	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}

	public VehicleDAO getVehicleDAO() {
		return vehicleDAO;
	}

	public void setVehicleDAO(VehicleDAO vehicleDAO) {
		this.vehicleDAO = vehicleDAO;
	}

	public List<DriverReportItem> getDriverData() {
		return driverData;
	}

	public void setDriverData(List<DriverReportItem> driverData) {
		this.driverData = driverData;
	}

	public List<Vehicle> getVehicleData() {
		return vehicleData;
	}

	public void setVehicleData(List<Vehicle> vehicleData) {
		this.vehicleData = vehicleData;
	}
	public Integer getSelectedGroupVehicleCount() {
		return selectedGroupVehicleCount;
	}

	public Integer getSelectedGroupDriverCount() {
		return selectedGroupDriverCount;
	}
	
}
