package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.model.SelectItem;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;
import org.richfaces.component.UITree;
import org.richfaces.event.DropEvent;
import org.richfaces.event.NodeExpandedEvent;
import org.richfaces.event.NodeSelectedEvent;

import com.inthinc.pro.backing.model.BaseTreeNodeImpl;
import com.inthinc.pro.backing.model.DeviceTreeNodeImpl;
import com.inthinc.pro.backing.model.DriverTreeNodeImpl;
import com.inthinc.pro.backing.model.GroupTreeNodeImpl;
import com.inthinc.pro.backing.model.TreeNodeType;
import com.inthinc.pro.backing.model.UserTreeNodeImpl;
import com.inthinc.pro.backing.model.VehicleTreeNodeImpl;
import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.ReportDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.DOTOfficeType;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.GroupStatus;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.SelectItemUtil;

@KeepAlive
@SuppressWarnings("rawtypes")
public class OrganizationBean extends BaseBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // TODO Mike - I'd like to create a OrganizationTreeViewBean and added it to this OrganizationBean. Wire it up using Spring.
    // TODO Jacquie - kinda doing that with the TreeNavigationBean

    private static final Logger logger = Logger.getLogger(OrganizationBean.class);
    /*
     * Spring managed beans
     */
    private GroupDAO groupDAO;
    private PersonDAO personDAO;
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    private UserDAO userDAO;
    private DeviceDAO deviceDAO;
    private AddressDAO addressDAO;
    private ReportDAO reportDAO;

    /*
     * Organization Tree Specific
     */
    private GroupTreeNodeImpl rootGroupNode;

    private GroupHierarchy organizationHierarchy;

    private BaseTreeNodeImpl selectedTreeNode;

    private GroupTreeNodeImpl selectedGroupNode;
    private DriverTreeNodeImpl selectedDriverTreeNode;
    private VehicleTreeNodeImpl selectedVehicleTreeNode;
    private UserTreeNodeImpl selectedUserTreeNode;
    private DeviceTreeNodeImpl selectedDeviceTreeNode;

    private Map<Integer, Boolean> treeStateMap = new HashMap<Integer, Boolean>();

    private TreeNavigationBean treeNavigationBean;

    /*
     * For controlling state of page
     */
    private enum State {
        VIEW,
        EDIT,
        ADD;
    }

    private Integer selectedGroupVehicleCount;
    private Integer selectedGroupDriverCount;

    /*
     * For group editing
     */
    private State groupState;
    private Person selectedPerson;
    private GroupTreeNodeImpl tempGroupTreeNode;
    private Integer selectedParentGroupID;
    private static final Map<String, com.inthinc.pro.model.State> STATES;
//    private static final List<SelectItem> STATES;
    static {
        // get states in the right order
        STATES = new TreeMap<String, com.inthinc.pro.model.State>();
        for (final com.inthinc.pro.model.State state : States.getStates().values()) {
            STATES.put(state.getName(), state);
        }
//        STATES.put("", new com.inthinc.pro.model.State(-1, "",""));
    }

    public OrganizationBean() {
        super();
        groupState = State.VIEW;
    }

    /*
     * This returns a list of nodes that are contained in top level that the user has access to view. This bean is session scope so in order to reload the group heirarchy, the
     * topLevelNodes variable needs to be set to null
     */
    public GroupTreeNodeImpl getRootGroupNode() {
        if (rootGroupNode == null) {
            organizationHierarchy = new GroupHierarchy(groupDAO.getGroupHierarchy(getAccountID(), getUser().getGroupID()));
            final Group topLevelGroup = organizationHierarchy.getGroup(getUser().getGroupID());
            rootGroupNode = createNewGroupNode(topLevelGroup);
            if (selectedGroupNode == null) {
                setSelectedTreeNode(rootGroupNode);
                setSelectedGroupNode(rootGroupNode);
            }
            logger.debug("Group Hirarchy was Loaded");

        }
        return rootGroupNode;
    }

    protected Group getTopGroup() {
        if (getGroupHierarchy().getTopGroup().getType() == GroupType.FLEET)
            return getGroupHierarchy().getTopGroup();
        else {
            final List<Group> groups = groupDAO.getGroupsByAcctID(getAccountID());
            for (final Group group : groups)
                if (group.getType() == GroupType.FLEET)
                    return group;
        }
        return null;
    }

    public void setRootGroupNode(GroupTreeNodeImpl topLevelNode) {
        this.rootGroupNode = topLevelNode;
    }

    public boolean adviseNodeSelected(UITree tree) {
        if (tree != null && tree.getRowData() != null && selectedTreeNode != null) {
            BaseTreeNodeImpl treeNode = (BaseTreeNodeImpl) tree.getRowData();
            logger.debug("Selected: " + treeNode.getLabel());
            logger.debug("Tree Node Types: " + selectedGroupNode.getTreeNodeType() + " vs " + treeNode.getTreeNodeType());
            logger.debug(("Tree Node ID: " + selectedGroupNode.getId() + " vs " + treeNode.getId()));
            if (selectedTreeNode.getTreeNodeType().equals(treeNode.getTreeNodeType()) && selectedTreeNode.getId().equals(treeNode.getId())) {
                return true;
            }
        }
        return false;
    }

    public boolean adviseNodeExpanded(UITree tree) {
        boolean result = false;
        if (tree != null && tree.getRowData() != null) {
            BaseTreeNodeImpl treeNode = (BaseTreeNodeImpl) tree.getRowData();
            logger.debug("Tree Node Expanded: " + treeNode.getLabel());
            if (treeNode.getBaseEntity() != null && treeStateMap.get(treeNode.getId()) != null && treeStateMap.get(treeNode.getId())) {
                result = true;
            } else if (treeNode.getBaseEntity() != null && treeNode.getParent().getBaseEntity() == null) {
                result = true;
            }
        }
        return result;
    }

    /**
     * When a node from the tree view is selected, this action is fired. The google map will need to be refreshed when needed. This action checks to see if the map needes to be
     * reRendered and then sets the reRender flag.
     * 
     * @param event
     */
    public void selectNode(NodeSelectedEvent event) {
        final UITree tree = (UITree) event.getComponent();
        final BaseTreeNodeImpl treeNode = (BaseTreeNodeImpl) tree.getRowData();
        logger.debug(treeNode.getLabel() + " was selected.");
        setSelectedTreeNode(treeNode);
        switch (treeNode.getTreeNodeType()) {
            case FLEET:
            case DIVISION:
            case TEAM:
                setSelectedGroupNode((GroupTreeNodeImpl) treeNode);
                break;
            case DRIVER:
                setSelectedDriverTreeNode((DriverTreeNodeImpl) treeNode);
                break;
            case USER:
                setSelectedUserTreeNode((UserTreeNodeImpl) treeNode);
                break;
            case VEHICLE:
                setSelectedVehicleTreeNode((VehicleTreeNodeImpl) treeNode);
                break;
            case DEVICE:
                setSelectedDeviceTreeNode((DeviceTreeNodeImpl) treeNode);
                break;
        }
    }

    public void changeExpandListener(NodeExpandedEvent event) {
        UITree tree = (UITree) event.getComponent();
        BaseTreeNodeImpl object = (BaseTreeNodeImpl) tree.getRowData();
        if (object.getBaseEntity() != null) {
            if (treeStateMap.get(object.getId()) != null && treeStateMap.get(object.getId())) {
                treeStateMap.put(object.getId(), Boolean.FALSE);
            } else {
                treeStateMap.put(object.getId(), Boolean.TRUE);
            }
        }
    }

    /**
     * Event to handle the on drop action of the Tree View
     * 
     * Rules: If the Drag Node equals the Drop Node then do nothing If the Drag Nodes Parent equals the Drop Node then do nothing
     * 
     * @param DropEvent
     *            event
     */
    public void processDrop(DropEvent event) {
        GroupTreeNodeImpl dragTreeNode = (GroupTreeNodeImpl) event.getDragValue();
        GroupTreeNodeImpl dropTreeNode = (GroupTreeNodeImpl) event.getDropValue();

        logger.debug(dragTreeNode.getLabel() + " was dropped onto " + dropTreeNode.getLabel());
        if (dragTreeNode.getId().equals(dropTreeNode.getId()) || dragTreeNode.getParent().getId().equals(dropTreeNode.getId())) {
            logger.debug("process drop stopped");
            return;
        } else if (dragTreeNode.findTreeNodeByGroupId(dropTreeNode.getGroup().getGroupID()) != null) {
            logger.debug("Cannot drop onto child");
            return;
        }

        dragTreeNode.getGroup().setParentID(dropTreeNode.getGroup().getGroupID());
        dragTreeNode.setParent(dropTreeNode);

        groupDAO.update(dragTreeNode.getGroup());
        treeStateMap.put(dropTreeNode.getGroup().getGroupID(), Boolean.TRUE);
        dropTreeNode.sortChildren();
    }

    private void setExpandedNode(BaseTreeNodeImpl treeNode) {
        if (treeNode instanceof GroupTreeNodeImpl && treeNode.getId() != null) {
            treeStateMap.put(treeNode.getId(), Boolean.TRUE);
        }

        if (treeNode.getParent() != null) {
            setExpandedNode(treeNode.getParent());
        }

    }

    /*
     * BEGIN CRUD methods
     */

    private void cleanFields() {
        tempGroupTreeNode = null;
        selectedParentGroupID = null;
    }

    /**
     * Setting up the bean for editing a group.
     */
    public void edit() {
        groupState = State.EDIT;
        logger.debug("editing " + selectedGroupNode.getLabel());
        tempGroupTreeNode = new GroupTreeNodeImpl(new Group(), organizationHierarchy);
        copyGroupTreeNode((GroupTreeNodeImpl) selectedGroupNode, (GroupTreeNodeImpl) tempGroupTreeNode, false);
        if (selectedGroupNode.getParent() != null && selectedGroupNode.getParent().getBaseEntity() != null) {
            selectedParentGroupID = ((Group) selectedGroupNode.getParent().getBaseEntity()).getGroupID();
        }
    }

    public String view() {
        groupState = State.VIEW;
        cleanFields(); // Clear out the group that was being worked on
        return "go_adminOrganization";
    }

    /**
     * Setting up the bean for adding a new group
     */
    public void add() {
        groupState = State.ADD;
        logger.debug("Adding New Group");
        selectedParentGroupID = ((Group) selectedGroupNode.getBaseEntity()).getGroupID();

        tempGroupTreeNode = createNewGroupNode(null);

    }

    public void changeSelectedGroup(javax.faces.event.ActionEvent event) {
        groupState = State.VIEW;
    }

    /**
     * Copies the group node that is being worked on and then updates it.
     */
    public void update() {
        if (validate((GroupTreeNodeImpl) tempGroupTreeNode)) {
            copyGroupTreeNode((GroupTreeNodeImpl) tempGroupTreeNode, (GroupTreeNodeImpl) selectedGroupNode, true);
            // selectedGroupNode.getBaseEntity().setAddressID(updateAddress(selectedGroupNode.getBaseEntity()));
            groupDAO.update((Group) selectedGroupNode.getBaseEntity());
            if (selectedParentGroupID != null) {
                setExpandedNode(selectedGroupNode.getParent());
                // treeStateMap.put(selectedParentGroup.getGroupID(), Boolean.TRUE);
            }
            getSelectedGroupNode().setTreeNodeType(null); // Reset the type
            updateUsersGroupHierarchy();
            this.addInfoMessage(selectedGroupNode.getLabel() + " " + MessageUtil.getMessageString("group_update_confirmation"));
            groupState = State.VIEW;
            cleanFields();

        }
    }

    /**
     * Action called to create a new group node record
     */
    public void save() {
        BaseTreeNodeImpl parentNode = null;
        parentNode = rootGroupNode.findTreeNodeByGroupId(selectedParentGroupID);
        tempGroupTreeNode.setParent(parentNode);
        ((GroupTreeNodeImpl) tempGroupTreeNode).getBaseEntity().setParentID(((GroupTreeNodeImpl) parentNode).getBaseEntity().getGroupID());
        tempGroupTreeNode.setLabel(((GroupTreeNodeImpl) tempGroupTreeNode).getBaseEntity().getName());
        if (validate((GroupTreeNodeImpl) tempGroupTreeNode)) {
            Integer id = groupDAO.create(getAccountID(), ((GroupTreeNodeImpl) tempGroupTreeNode).getBaseEntity());
            if (id > 0) {
                ((GroupTreeNodeImpl) tempGroupTreeNode).getBaseEntity().setGroupID(id);
                tempGroupTreeNode.setId(id);
                setSelectedGroupNode(tempGroupTreeNode);
                setSelectedTreeNode(tempGroupTreeNode);

                // Update The Group Heirarchy Stored in the Session.
                updateUsersGroupHierarchy();

                // Set the current parent node as expanded and all of its parents up to the root node.
                setExpandedNode(getRootGroupNode().findTreeNodeByGroupId(selectedParentGroupID));
                cleanFields();

                // Reset the state back to display
                groupState = State.VIEW;

                this.addInfoMessage(selectedGroupNode.getLabel() + " " + MessageUtil.getMessageString("group_save_confirmation"));
            } else {
                this.addErrorMessage("Error Adding:  " + selectedGroupNode.getLabel());
            }
        }

    }

    public void delete() {
        if (selectedGroupNode.getBaseEntity() != null) {
            // Validation
            if (selectedGroupNode.equals(rootGroupNode)) {
                addErrorMessage(MessageUtil.getMessageString("group_delete_error_top"));
            } else if (selectedGroupNode.getChildCount() > 0) {
                addErrorMessage(MessageUtil.getMessageString("group_delete_error_subordinate"));
            } else {
                groupDAO.delete(((GroupTreeNodeImpl) selectedGroupNode).getBaseEntity());
                BaseTreeNodeImpl parentNode = selectedGroupNode.getParent();
                selectedGroupNode.setParent(null);
                setSelectedGroupNode((GroupTreeNodeImpl) parentNode);
                selectedTreeNode = parentNode;
                // Make sure when the page refreshed that we pull a new list in
                rootGroupNode = null;
                updateUsersGroupHierarchy();
            }
        }

    }

    private void updateUsersGroupHierarchy() {
        List<Group> groupList = groupDAO.getGroupHierarchy(getAccountID(), getUser().getGroupID());
        organizationHierarchy = new GroupHierarchy(groupList);
        getProUser().setGroupHierarchy(organizationHierarchy);
        getProUser().setAccountGroupHierarchy(new GroupHierarchy(groupList));
        treeNavigationBean.refresh();
    }

    /**
     * Rule 1: EDIT - If there are drivers/devices/or vehicles then the group has to be a team Case 2: EDIT - If there are subordinate groups within a group, then that group cannot
     * be a team Rule 3: Edit - Cannot assign the same group to be a groups parent. Rule 4: Edit - Cannot assign a child group to be a parent.
     * 
     * @param treeNode
     *            treeNode containing the group to validate
     * @return returns true if validation is successfully
     */
    private boolean validate(GroupTreeNodeImpl treeNode) {

        // Rule 1
        if (groupState == State.EDIT && !treeNode.getBaseEntity().getType().equals(GroupType.TEAM)) {
            List<Driver> driverList = driverDAO.getDrivers(treeNode.getBaseEntity().getGroupID());
            List<Vehicle> vehicleList = vehicleDAO.getVehiclesInGroup(treeNode.getBaseEntity().getGroupID());
            if (!vehicleList.isEmpty() || !driverList.isEmpty()) {
                addErrorMessage(MessageUtil.getMessageString("group_edit_error_division"));

                return false;
            }
        }

        // Rule 2
        if (groupState == State.EDIT && treeNode.getBaseEntity().getType() == GroupType.TEAM && treeNode.hasChildCroups()) {
            addErrorMessage(MessageUtil.getMessageString("group_edit_error_team"));
            return false;
        }

        // Rule 3
        if (groupState == State.EDIT && selectedParentGroupID != null && selectedParentGroupID.equals(treeNode.getBaseEntity().getGroupID())) {
            addErrorMessage(MessageUtil.getMessageString("groupEdit_selfParentError"));
            return false;
        }

        // Rule 4
        if (groupState == State.EDIT && selectedParentGroupID != null && treeNode.findTreeNodeByGroupId(selectedParentGroupID) != null) {
            addErrorMessage(MessageUtil.getMessageString("groupEdit_childToParentError"));
            return false;
        }

        // Rule 5
        if (groupState == State.EDIT && (treeNode.getBaseEntity().getDotOfficeType() != null && treeNode.getBaseEntity().getDotOfficeType() != DOTOfficeType.NONE)
                && (addressMissingRequiredFields(treeNode.getBaseEntity().getAddress()))) {
            addErrorMessage(MessageUtil.getMessageString("groupEdit_addressRequired"));
            return false;
        }
        return true;
    }

    private boolean addressMissingRequiredFields(Address address) {
        return address.getAddr1() == null || address.getAddr1().isEmpty() || address.getState() == null || address.getCity() == null || address.getCity().isEmpty() || address.getZip() == null
                || address.getZip().isEmpty();
    }

    /**
     * Creates a copy of GroupTreeNodes.
     * 
     * @param copyFromNode
     *            Node from which to copy.
     * @param copyToNode
     *            Node to copy too.
     * @param updateTree
     *            Indicates if the copyToNode should be tied to the TreeNodes Collection. (It will be visible in the TreeView if indicated as "true".
     */
    private void copyGroupTreeNode(GroupTreeNodeImpl copyFromNode, GroupTreeNodeImpl copyToNode, boolean updateTree) {
        Group group = (Group) copyToNode.getBaseEntity();
        Group copyFromGroup = (Group) copyFromNode.getBaseEntity();
        group.setAccountID(getAccountID());
        group.setGroupID(copyFromGroup.getGroupID());
        group.setManagerID(copyFromGroup.getManagerID());
        group.setName(copyFromGroup.getName());
        group.setDescription(copyFromGroup.getDescription());
        group.setCreated(copyFromGroup.getCreated());
        group.setType(copyFromGroup.getType());
        if (copyFromGroup.getMapCenter() != null) {
            group.setMapCenter(new LatLng(copyFromGroup.getMapCenter().getLat(), copyFromGroup.getMapCenter().getLng()));
        }
        group.setMapZoom(copyFromGroup.getMapZoom());
        group.setAddressID(copyFromGroup.getAddressID());
        group.setDotOfficeType(copyFromGroup.getDotOfficeType());
        Address address = new Address();
        if (copyFromGroup.getAddress() != null) {
            Address copyFromAddress = copyFromGroup.getAddress();
            address.setAddrID(copyFromAddress.getAddrID());
            address.setAccountID(copyFromAddress.getAccountID());
            address.setAddr1(copyFromAddress.getAddr1());
            address.setAddr2(copyFromAddress.getAddr2());
            address.setCity(copyFromAddress.getCity());
            address.setState(copyFromAddress.getState());
            address.setZip(copyFromAddress.getZip());
        }
        group.setAddress(address);
        copyToNode.setId(copyFromGroup.getGroupID());
        copyToNode.setLabel(copyFromGroup.getName());
        copyToNode.setTreeNodeType(copyFromNode.getTreeNodeType());
        copyToNode.setDriverDAO(driverDAO);
        copyToNode.setVehicleDAO(vehicleDAO);
        copyToNode.setUserDAO(userDAO);
        copyToNode.setDeviceDAO(deviceDAO);
        GroupTreeNodeImpl parentNode = null;
        if (selectedParentGroupID != null && updateTree) {
            parentNode = rootGroupNode.findTreeNodeByGroupId(selectedParentGroupID);
            copyToNode.getBaseEntity().setParentID(parentNode.getBaseEntity().getGroupID());
            copyToNode.setParent(parentNode);
        }
    }

    private GroupTreeNodeImpl createNewGroupNode(Group group) {
        Group g = group;
        if (group == null) {
            g = new Group();
            g.setAccountID(getAccountID());
            g.setGroupID(0);
            g.setStatus(GroupStatus.GROUP_ACTIVE);
            g.setMapZoom(((GroupTreeNodeImpl) selectedGroupNode).getBaseEntity().getMapZoom());
            g.setMapCenter(((GroupTreeNodeImpl) selectedGroupNode).getBaseEntity().getMapCenter());
            g.setDotOfficeType(DOTOfficeType.NONE);
        }
        fetchGroupAddress(g);

        GroupTreeNodeImpl newGroupNode = new GroupTreeNodeImpl(g, organizationHierarchy);
        newGroupNode.setDriverDAO(driverDAO);
        newGroupNode.setVehicleDAO(vehicleDAO);
        newGroupNode.setUserDAO(userDAO);
        newGroupNode.setDeviceDAO(deviceDAO);
        return newGroupNode;
    }

    /*
     * END CRUD Methods
     */

    /*
     * The available group list will set pending the parent group that is being assigned;
     */
    public List<SelectItem> getGroupTypeList() {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        selectItems.add(new SelectItem(null, ""));
        selectItems.add(new SelectItem(GroupType.DIVISION, MessageUtil.getMessageString(GroupType.DIVISION.toString())));
        selectItems.add(new SelectItem(GroupType.TEAM, MessageUtil.getMessageString(GroupType.TEAM.toString())));

        return selectItems;
    }

    /*
     * TODO: - Mike- We need define who can be assigned to this group. Right now we're bringing all people back that are within The current users group
     */
    public List<SelectItem> getPeopleSelectItems() {
        logger.debug("start getPeopleSelectItems()");
        List<Person> personList = personDAO.getPeopleInGroupHierarchy(getUser().getGroupID());

        List<SelectItem> selectItems = new ArrayList<SelectItem>(0);
        selectItems.add(new SelectItem(null, ""));

        for (Person person : personList) {
            selectItems.add(new SelectItem(person.getPersonID(), person.getFirst() + " " + person.getLast()));
        }
        logger.debug("end getPeopleSelectItems()");
        return selectItems;
    }

    /**
     * @deprecated slow, relies on making (at least) one hessian call per group, replaced by {@link TreeNavigationBean#getParentGroupsSelect()}
     * @return
     */
    @Deprecated
    public List<SelectItem> getParentGroups() {
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        if (rootGroupNode.getTreeNodeType() == TreeNodeType.FLEET || rootGroupNode.getTreeNodeType() == TreeNodeType.DIVISION) {
            selectItemList.add(new SelectItem(rootGroupNode.getBaseEntity(), rootGroupNode.getLabel()));
            selectItemList.addAll(getChildNodesAsSelectItems(rootGroupNode));
        }
        selectItemList.add(0, new SelectItem(null, ""));
        return selectItemList;
    }

    @SuppressWarnings("unchecked")
    private List<SelectItem> getChildNodesAsSelectItems(BaseTreeNodeImpl node) {
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        if (node.getChildCount() > 0) {
            List<BaseTreeNodeImpl> nodes = node.getChildrenNodes();
            for (BaseTreeNodeImpl n : nodes) {
                if (n.getTreeNodeType() == TreeNodeType.DIVISION || n.getTreeNodeType() == TreeNodeType.FLEET) {
                    selectItemList.add(new SelectItem(n.getBaseEntity(), n.getLabel()));
                    selectItemList.addAll(getChildNodesAsSelectItems(n));
                }
            }
        }

        return selectItemList;
    }

    private void fetchGroupAddress(Group g) {
        if (g.getAddressID() == null) {
            g.setAddress(new Address());
            g.getAddress().setAccountID(g.getAccountID());
        } else if (g.getAddress() == null) {
            Address address = getAddressDAO().findByID(g.getAddressID());
            if ((address.getAccountID() == null) || (address.getAccountID().equals(g.getAccountID()))) {
                g.setAddress(getAddressDAO().findByID(g.getAddressID()));
            }
        }
    }

    public Map<String, com.inthinc.pro.model.State> getStates() {
        return STATES;
    }

    public List<SelectItem> getDotOfficeTypeSelectItems() {
        return SelectItemUtil.toList(DOTOfficeType.class, false);
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;

    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public GroupTreeNodeImpl getSelectedGroupNode() {
        return selectedGroupNode;
    }

    public void setSelectedGroupNode(GroupTreeNodeImpl selectedGroupNode) {
        TreeNodeType type = selectedGroupNode.getTreeNodeType();
        if (type == TreeNodeType.TEAM || type == TreeNodeType.DIVISION || type == TreeNodeType.FLEET) {
            selectedGroupDriverCount = reportDAO.getDriverReportCount(selectedGroupNode.getId(), null);
            selectedGroupVehicleCount = reportDAO.getVehicleReportCount(selectedGroupNode.getId(), null);
            fetchGroupAddress(selectedGroupNode.getGroup());
        }

        if (this.selectedGroupNode != null && !(selectedGroupNode.getTreeNodeType() == this.selectedGroupNode.getTreeNodeType() && selectedGroupNode.getId() == this.selectedGroupNode.getId())) {
            this.groupState = State.VIEW;
        }
        this.selectedGroupNode = (GroupTreeNodeImpl) selectedGroupNode;
    }

    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public State getGroupState() {
        return groupState;
    }

    public void setGroupState(State groupState) {
        this.groupState = groupState;
    }

    public Person getSelectedPerson() {
        if (selectedGroupNode != null && ((GroupTreeNodeImpl) selectedGroupNode).getBaseEntity().getManagerID() != null) {
            selectedPerson = personDAO.findByID(((GroupTreeNodeImpl) selectedGroupNode).getBaseEntity().getManagerID());
        }
        return selectedPerson;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    public GroupTreeNodeImpl getTempGroupTreeNode() {
        return tempGroupTreeNode;
    }

    public void setTempGroupTreeNode(GroupTreeNodeImpl tempGroupTreeNode) {
        this.tempGroupTreeNode = tempGroupTreeNode;
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

    public Integer getSelectedGroupVehicleCount() {
        return selectedGroupVehicleCount;
    }

    public Integer getSelectedGroupDriverCount() {
        return selectedGroupDriverCount;
    }

    public Map<Integer, Boolean> getTreeStateMap() {
        return treeStateMap;
    }

    public void setTreeStateMap(Map<Integer, Boolean> treeStateMap) {
        this.treeStateMap = treeStateMap;
    }

    public GroupHierarchy getOrganizationHierarchy() {
        return organizationHierarchy;
    }

    public void setOrganizationHierarchy(GroupHierarchy organizationHierarchy) {
        this.organizationHierarchy = organizationHierarchy;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setSelectedDriverTreeNode(DriverTreeNodeImpl selectedDriverTreeNode) {
        this.selectedDriverTreeNode = selectedDriverTreeNode;
    }

    public DriverTreeNodeImpl getSelectedDriverTreeNode() {
        return selectedDriverTreeNode;
    }

    public void setSelectedVehicleTreeNode(VehicleTreeNodeImpl selectedVehicleTreeNode) {
        this.selectedVehicleTreeNode = selectedVehicleTreeNode;
    }

    public VehicleTreeNodeImpl getSelectedVehicleTreeNode() {
        return selectedVehicleTreeNode;
    }

    public void setSelectedUserTreeNode(UserTreeNodeImpl selectedUserTreeNode) {
        this.selectedUserTreeNode = selectedUserTreeNode;
    }

    public UserTreeNodeImpl getSelectedUserTreeNode() {
        return selectedUserTreeNode;
    }

    public void setSelectedTreeNode(BaseTreeNodeImpl selectedTreeNode) {
        this.selectedTreeNode = selectedTreeNode;
    }

    public BaseTreeNodeImpl getSelectedTreeNode() {
        return selectedTreeNode;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    public DeviceDAO getDeviceDAO() {
        return deviceDAO;
    }

    public void setSelectedDeviceTreeNode(DeviceTreeNodeImpl selectedDeviceTreeNode) {
        this.selectedDeviceTreeNode = selectedDeviceTreeNode;
    }

    public DeviceTreeNodeImpl getSelectedDeviceTreeNode() {
        return selectedDeviceTreeNode;
    }

    public TreeNavigationBean getTreeNavigationBean() {
        return treeNavigationBean;
    }

    public void setTreeNavigationBean(TreeNavigationBean treeNavigationBean) {
        this.treeNavigationBean = treeNavigationBean;
    }

    public AddressDAO getAddressDAO() {
        return addressDAO;
    }

    public void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    public ReportDAO getReportDAO() {
        return reportDAO;
    }

    public void setReportDAO(ReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    public void setSelectedParentGroupID(Integer selectedParentGroupID) {
        this.selectedParentGroupID = selectedParentGroupID;
    }

    public Integer getSelectedParentGroupID() {
        return selectedParentGroupID;
    }
}
