package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.richfaces.component.UITree;
import org.richfaces.event.DropEvent;
import org.richfaces.event.NodeExpandedEvent;
import org.richfaces.event.NodeSelectedEvent;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.backing.model.TreeNodeImpl;
import com.inthinc.pro.backing.model.TreeNodeType;
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupStatus;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.util.MessageUtil;

/*
 * Notes TODO
 * 
 * Need to update the GroupHierarchy tied to the user when a group has been updated or added.
 * 
 */

public class OrganizationBean extends BaseBean
{

    private static final Logger logger = Logger.getLogger(OrganizationBean.class);
    /*
     * Spring managed beans
     */
    private GroupDAO groupDAO;
    private PersonDAO personDAO;
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    private AccountDAO accountDAO;

    /*
     * Organization Tree Specific
     */
    private TreeNodeImpl topLevelNode;
    private GroupHierarchy organizationHierarchy;
    private TreeNodeImpl selectedGroupNode;
    private Map<Integer, Boolean> treeStateMap = new HashMap<Integer, Boolean>();


    /*
     * For controlling state of page
     */
    private enum State
    {
        VIEW, EDIT, ADD;
    }

    private Integer selectedGroupVehicleCount;
    private Integer selectedGroupDriverCount;

    /*
     * For group editing
     */
    private State groupState;
    private Person selectedPerson;
    private TreeNodeImpl inProgressGroupNode;
    private Group selectedParentGroup;

    public OrganizationBean()
    {
        groupState = State.VIEW;
    }

    /*
     * This returns a list of nodes that are contained in top level that the user has access to view. This bean is session scope so in order to reload the group heirarchy, the
     * topLevelNodes variable needs to be set to null
     */
    public TreeNodeImpl getTopLevelNodes()
    {
        if (topLevelNode == null)
        {

            organizationHierarchy = new GroupHierarchy(groupDAO.getGroupHierarchy(getAccountID(), getGroupHierarchy().getTopGroup().getGroupID()));
            Group topLevelGroup = organizationHierarchy.getTopGroup();
            topLevelNode = new TreeNodeImpl(topLevelGroup, organizationHierarchy);
            topLevelNode.setDriverDAO(driverDAO);
            topLevelNode.setVehicleDAO(vehicleDAO);
            if (selectedGroupNode == null)
            {
                setSelectedGroupNode(topLevelNode);
            }
            logger.debug("Group Hirarchy was Loaded");

        }
        return topLevelNode;
    }

    public void setTopLevelNodes(TreeNodeImpl topLevelNode)
    {
        this.topLevelNode = topLevelNode;
    }

    public boolean adviseNodeSelected(UITree tree)
    {
        if (tree != null && tree.getRowData() != null && selectedGroupNode != null)
        {
            TreeNodeImpl treeNode = (TreeNodeImpl) tree.getRowData();
            logger.debug("Selected: " + treeNode.getLabel());
            logger.debug("Tree Node Types: " + selectedGroupNode.getTreeNodeType() + " vs " + treeNode.getTreeNodeType());
            logger.debug(("Tree Node ID: " + selectedGroupNode.getId() + " vs " + treeNode.getId()));
            if (treeNode != null && selectedGroupNode.getTreeNodeType().equals(treeNode.getTreeNodeType()) && selectedGroupNode.getId().equals(treeNode.getId()))
            {
                return true;
            }
        }
        return false;
    }

    public boolean adviseNodeExpanded(UITree tree)
    {
        boolean result = false;
        if (tree != null && tree.getRowData() != null)
        {
            TreeNodeImpl object = (TreeNodeImpl) tree.getRowData();
            logger.debug("Tree Node Expanded: " + object.getLabel());
            if (object.getGroup() != null)
            {
                if (treeStateMap.get(object.getGroup().getGroupID()) != null && treeStateMap.get(object.getGroup().getGroupID()))
                {
                    result = true;
                }
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
    public void selectNode(NodeSelectedEvent event)
    {
        UITree tree = (UITree) event.getComponent();
        TreeNodeImpl treeNode = (TreeNodeImpl) tree.getRowData();
        logger.debug(treeNode.getLabel() + " was selected.");
        setSelectedGroupNode(treeNode);
    }

    public void changeExpandListener(NodeExpandedEvent event)
    {
        UITree tree = (UITree) event.getComponent();
        TreeNodeImpl object = (TreeNodeImpl) tree.getRowData();
        if (object.getGroup() != null)
        {
            if (tree.isExpanded())
            {
                treeStateMap.put(object.getGroup().getGroupID(), Boolean.TRUE);
            }
            else
            {
                treeStateMap.put(object.getGroup().getGroupID(), Boolean.FALSE);
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
    public void processDrop(DropEvent event)
    {
        TreeNodeImpl dragTreeNode = (TreeNodeImpl) event.getDragValue();
        TreeNodeImpl dropTreeNode = (TreeNodeImpl) event.getDropValue();

        // groupState = State.EDIT;
        // selectedGroupNode = dragTreeNode;
        // selectedParentGroup = dropTreeNode.getGroup();
        // inProgressGroupNode = new TreeNodeImpl(new Group(), organizationHierarchy);
        // copyGroupTreeNode(selectedGroupNode, inProgressGroupNode);
        logger.debug(dragTreeNode.getLabel() + " was dropped onto " + dropTreeNode.getLabel());
        if (dragTreeNode.getId().equals(dropTreeNode.getId()) || dragTreeNode.getParent().getId().equals(dropTreeNode.getId()))
        {
            logger.debug("process drop stopped");
            return;
        }
        else if (dragTreeNode.findTreeNodeByGroupId(dropTreeNode.getGroup().getGroupID()) != null)
        {
            logger.debug("Cannot drop onto child");
            return;
        }

        dragTreeNode.getGroup().setParentID(dropTreeNode.getGroup().getGroupID());
        dragTreeNode.setParent(dropTreeNode);

        groupDAO.update(dragTreeNode.getGroup());
    }

    /*
     * BEGIN CRUD methods
     */

    private void cleanFields()
    {
        inProgressGroupNode = null;
        selectedParentGroup = null;
    }

    public void edit()
    {
        groupState = State.EDIT;
        logger.debug("editing " + selectedGroupNode.getLabel());
        inProgressGroupNode = new TreeNodeImpl(new Group(), organizationHierarchy);
        copyGroupTreeNode(selectedGroupNode, inProgressGroupNode);
        if (selectedGroupNode.getParent() != null && selectedGroupNode.getParent().getGroup() != null)
        {
            selectedParentGroup = selectedGroupNode.getParent().getGroup();
        }
    }

    public void view()
    {
        groupState = State.VIEW;
        cleanFields(); // Clear out the group that was being worked on.
    }

    public void add()
    {
        groupState = State.ADD;
        logger.debug("Adding New Group");
        inProgressGroupNode = createNewGroupNode();
        selectedParentGroup = selectedGroupNode.getGroup();
    }

    public void changeSelectedGroup(javax.faces.event.ActionEvent event)
    {
        groupState = State.VIEW;
    }

    /**
     * Copies the group node that is being worked on and then updates it.
     */
    public void update()
    {
        if (validate(inProgressGroupNode))
        {
            copyGroupTreeNode(inProgressGroupNode, selectedGroupNode);
            TreeNodeImpl parentNode = null;
            if (selectedParentGroup != null)
            {
                parentNode = topLevelNode.findTreeNodeByGroupId(selectedParentGroup.getGroupID());
                selectedGroupNode.getGroup().setParentID(parentNode.getGroup().getGroupID());
                selectedGroupNode.setParent(parentNode);
            }

            groupDAO.update(selectedGroupNode.getGroup());
            updateUsersGroupHeirarchy();
            this.addInfoMessage(selectedGroupNode.getGroup().getName() + " " + MessageUtil.getMessageString("group_update_confirmation"));
            groupState = State.VIEW;
            cleanFields();

        }
    }

    /**
     * Action called to create a new group node record
     */
    public void save()
    {
        TreeNodeImpl parentNode = null;
        parentNode = topLevelNode.findTreeNodeByGroupId(selectedParentGroup.getGroupID());
        inProgressGroupNode.setParent(parentNode);
        inProgressGroupNode.getGroup().setParentID(parentNode.getGroup().getGroupID());
        // treeStateMap.put(selectedGroupNode.getGroup().getGroupID(), true);
        if (validate(inProgressGroupNode))
        {
            Integer id = groupDAO.create(getAccountID(), inProgressGroupNode.getGroup());
            if (id > 0)
            {
                inProgressGroupNode.getGroup().setGroupID(id);
                inProgressGroupNode.setId(id);
                setSelectedGroupNode(inProgressGroupNode);
                updateUsersGroupHeirarchy();
                topLevelNode = null;
                this.addInfoMessage(selectedGroupNode.getGroup().getName() + " " + MessageUtil.getMessageString("group_save_confirmation"));
                groupState = State.VIEW;
                treeStateMap.put(selectedParentGroup.getGroupID(), Boolean.TRUE);
                cleanFields();
               
            }
            else
            {
                this.addErrorMessage("Error Adding:  " + selectedGroupNode.getLabel());
            }
        }

    }

    private void updateUsersGroupHeirarchy()
    {
        organizationHierarchy = new GroupHierarchy(groupDAO.getGroupHierarchy(getAccountID(), getGroupHierarchy().getTopGroup().getGroupID()));
        getProUser().setGroupHierarchy(organizationHierarchy);
    }

    /**
     * Case 1: EDIT - If there are drivers/devices/or vehicles then the group has to be a team
     * 
     * @param treeNode
     *            treeNode containing the group to validate
     * @return returns true if validation is successfully
     */
    private boolean validate(TreeNodeImpl treeNode)
    {
        boolean valid = true;
        // Case 1
        if (groupState == State.EDIT && treeNode.getGroup().getType() != GroupType.TEAM)
        {
            List<Driver> driverList = driverDAO.getDrivers(treeNode.getGroup().getGroupID());
            List<Vehicle> vehicleList = vehicleDAO.getVehiclesInGroup(treeNode.getGroup().getGroupID());
            if (vehicleList.size() > 0 || driverList.size() > 0)
            {
                addErrorMessage("Group Cannot be changed from Team if there are Drivers, Vehicles attatched to the group");
                valid = false;
            }
        }

        // Case 2
        return valid;
    }

    private void copyGroupTreeNode(TreeNodeImpl copyFromNode, TreeNodeImpl copyToNode)
    {
        Group group = copyToNode.getGroup();
        group.setAccountID(getAccountID());
        group.setGroupID(copyFromNode.getGroup().getGroupID());
        group.setManagerID(copyFromNode.getGroup().getManagerID());
        group.setName(copyFromNode.getGroup().getName());
        group.setDescription(copyFromNode.getGroup().getDescription());
        group.setCreated(copyFromNode.getGroup().getCreated());
        group.setType(copyFromNode.getGroup().getType());
        group.setMapCenter(new LatLng(copyFromNode.getGroup().getMapCenter().getLat(), copyFromNode.getGroup().getMapCenter().getLng()));
        group.setMapZoom(copyFromNode.getGroup().getMapZoom());
        copyToNode.setId(copyFromNode.getGroup().getGroupID());
        copyToNode.setLabel(copyFromNode.getGroup().getName());
        copyToNode.setTreeNodeType(copyFromNode.getTreeNodeType());
    }

    private TreeNodeImpl createNewGroupNode()
    {
        Group group = new Group();
        group.setAccountID(getAccountID());
        group.setGroupID(0);
        group.setStatus(GroupStatus.GROUP_ACTIVE);
        group.setMapZoom(selectedGroupNode.getGroup().getMapZoom());
        group.setMapCenter(selectedGroupNode.getGroup().getMapCenter());
        TreeNodeImpl newGroupNode = new TreeNodeImpl(group, organizationHierarchy);
        return newGroupNode;
    }

    /*
     * END CRUD Methods
     */

    /*
     * The available group list will set pending the parent group that is being assigned;
     */
    public List<SelectItem> getGroupTypeList()
    {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        selectItems.add(new SelectItem(null, ""));

        selectItems.add(new SelectItem(GroupType.DIVISION, GroupType.DIVISION.toString()));
        selectItems.add(new SelectItem(GroupType.TEAM, GroupType.TEAM.toString()));

        return selectItems;
    }

    /*
     * TODO: - Mike- We need define who can be assigned to this group. Right now we're bringing all people back that are within The current users group
     */
    public List<SelectItem> getPeopleSelectItems()
    {
        List<Person> personList = personDAO.getPeopleInGroupHierarchy(getUser().getPerson().getGroupID());

        List<SelectItem> selectItems = new ArrayList<SelectItem>(0);
        selectItems.add(new SelectItem(null, ""));

        for (Person person : personList)
        {
            selectItems.add(new SelectItem(person.getPersonID(), person.getFirst() + " " + person.getLast()));
        }

        return selectItems;
    }

    public List<SelectItem> getParentGroups()
    {
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        if (topLevelNode.getTreeNodeType() == TreeNodeType.FLEET || topLevelNode.getTreeNodeType() == TreeNodeType.DIVISION)
        {
            selectItemList.add(new SelectItem(topLevelNode.getGroup(), topLevelNode.getLabel()));
            selectItemList.addAll(getChildNodesAsSelectItems(topLevelNode));
        }
        selectItemList.add(0, new SelectItem(null, ""));
        return selectItemList;
    }

    private List<SelectItem> getChildNodesAsSelectItems(TreeNodeImpl node)
    {
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        if (node.getChildCount() > 0)
        {
            for (TreeNodeImpl n : node.getChildrenNodes())
            {
                if (n.getTreeNodeType() == TreeNodeType.DIVISION || n.getTreeNodeType() == TreeNodeType.FLEET)
                {
                    selectItemList.add(new SelectItem(n.getGroup(), n.getLabel()));
                    selectItemList.addAll(getChildNodesAsSelectItems(n));
                }
            }
        }

        return selectItemList;
    }

    public GroupDAO getGroupDAO()
    {
        return groupDAO;

    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    public String getAccountName()
    {
        Account account = getAccountDAO().findByID(getAccountID());
        String name = account.getAcctName();
        return name;

    }

    public TreeNodeImpl getSelectedGroupNode()
    {
        return selectedGroupNode;
    }

    public void setSelectedGroupNode(TreeNodeImpl selectedGroupNode)
    {
        if (selectedGroupNode.getTreeNodeType() == TreeNodeType.TEAM)
        {
            
            selectedGroupDriverCount = driverDAO.getAllDrivers(selectedGroupNode.getGroup().getGroupID()).size();
            selectedGroupVehicleCount = vehicleDAO.getVehiclesInGroupHierarchy(selectedGroupNode.getGroup().getGroupID()).size();
        }
        if (this.selectedGroupNode != null)
        {
            if (!(selectedGroupNode.getTreeNodeType() == this.selectedGroupNode.getTreeNodeType() && selectedGroupNode.getId() == this.selectedGroupNode.getId()))
            {
                this.groupState = State.VIEW;
            }
        }

        this.selectedGroupNode = selectedGroupNode;
    }

    public PersonDAO getPersonDAO()
    {
        return personDAO;
    }

    public void setPersonDAO(PersonDAO personDAO)
    {
        this.personDAO = personDAO;
    }

    public State getGroupState()
    {
        return groupState;
    }

    public void setGroupState(State groupState)
    {
        this.groupState = groupState;
    }

    public Person getSelectedPerson()
    {
        if (selectedGroupNode != null && selectedGroupNode.getGroup().getManagerID() != null)
        {
            selectedPerson = personDAO.findByID(selectedGroupNode.getGroup().getManagerID());
        }
        return selectedPerson;
    }

    public void setSelectedPerson(Person selectedPerson)
    {
        this.selectedPerson = selectedPerson;
    }

    public TreeNodeImpl getInProgressGroupNode()
    {
        return inProgressGroupNode;
    }

    public void setInProgressGroupNode(TreeNodeImpl inProgressGroupNode)
    {
        this.inProgressGroupNode = inProgressGroupNode;
    }

    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    public Integer getSelectedGroupVehicleCount()
    {
        return selectedGroupVehicleCount;
    }

    public Integer getSelectedGroupDriverCount()
    {
        return selectedGroupDriverCount;
    }

    public void setSelectedParentGroup(Group selectedParentGroup)
    {
        this.selectedParentGroup = selectedParentGroup;
    }

    public Group getSelectedParentGroup()
    {
        return selectedParentGroup;
    }

    public void setAccountDAO(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }

    public AccountDAO getAccountDAO()
    {
        return accountDAO;
    }
    

    public Map<Integer, Boolean> getTreeStateMap()
    {
        return treeStateMap;
    }

    public void setTreeStateMap(Map<Integer, Boolean> treeStateMap)
    {
        this.treeStateMap = treeStateMap;
    }

}
