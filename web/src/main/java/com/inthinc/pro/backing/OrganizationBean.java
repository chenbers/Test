package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Enumeration;
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
import com.inthinc.pro.backing.model.GroupLevel;
import com.inthinc.pro.backing.model.GroupTreeNode;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Person;

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
     * Temp
     */
    private int id_counter = 300;

    /*
     * Spring managed beans
     */
    private GroupDAO groupDAO;
    private PersonDAO personDAO;
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;

    /*
     * Organization Tree Specific
     */
    private List<GroupTreeNode> topLevelNodes;
    private GroupHierarchy organizationHierarchy;
    private GroupTreeNode selectedGroupNode;
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
    private GroupTreeNode inProgressGroupNode;

    public OrganizationBean()
    {
        groupState = State.VIEW;
    }

    public List<GroupTreeNode> getTopLevelNode()
    {
        if (topLevelNodes == null)
        {
            topLevelNodes = new ArrayList<GroupTreeNode>();
            if (organizationHierarchy == null)
            {
                organizationHierarchy = new GroupHierarchy(groupDAO.getGroupHierarchy(getAccountID(), getGroupHierarchy().getTopGroup().getGroupID()));
            }
            List<Group> fleetGroups = organizationHierarchy.getGroupsByLevel(GroupLevel.FLEET);
            for (Group group : fleetGroups)
            {
                // Set default map view
                group.setMapZoom(3);
                group.setMapCenter(new LatLng(40.2, -111));
                GroupTreeNode topLevelNode = new GroupTreeNode(group, organizationHierarchy);
                topLevelNode.setDriverDAO(driverDAO);
                topLevelNode.setVehicleDAO(vehicleDAO);
                setSelectedGroupNode(topLevelNode);
                topLevelNodes.add(topLevelNode);
            }

        }

        return topLevelNodes;
    }

    /*
     * Not in use - Causes the app to run too slow
     */
    public boolean adviseNodeSelected(UITree tree)
    {
        if (tree != null && tree.getRowData() != null && selectedGroupNode != null)
        {
            GroupTreeNode node = (GroupTreeNode) tree.getRowData();
            if (node != null)
            {
                if (selectedGroupNode.getTreeNodeType() == node.getTreeNodeType() && selectedGroupNode.getId() == node.getId())
                {
                    groupState = State.VIEW;
                    return true;
                }

            }
        }
        return false;
    }

    /*
     * Not in use - Causes the app to run too slow
     */
    public boolean adviseNodeExpanded(UITree tree)
    {
        boolean result = false;
        if (tree != null && tree.getRowData() != null)
        {
            GroupTreeNode object = (GroupTreeNode) tree.getRowData();
            Logger.getLogger(OrganizationBean.class).debug("Tree Group Name: " + object.getGroup().getName());

            if (treeStateMap.get(object.getGroup().getGroupID()) != null && treeStateMap.get(object.getGroup().getGroupID()))
            {
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
    public void selectNode(NodeSelectedEvent event)
    {
        UITree tree = (UITree) event.getComponent();
        GroupTreeNode node = (GroupTreeNode) tree.getRowData();
        setSelectedGroupNode(node);
    }

    public void changeExpandListent(NodeExpandedEvent event)
    {
        UITree tree = (UITree) event.getComponent();
        GroupTreeNode object = (GroupTreeNode) tree.getRowData();
        if (tree.isExpanded())
        {
            treeStateMap.put(object.getGroup().getGroupID(), Boolean.TRUE);
        }
        else
        {
            treeStateMap.put(object.getGroup().getGroupID(), Boolean.FALSE);
        }
    }

    /**
     * Event to handle the on drop action of the Tree View
     * 
     * @param event
     */
    public void processDrop(DropEvent event)
    {
        GroupTreeNode dragTreeNode = (GroupTreeNode) event.getDragValue();
        GroupTreeNode dropTreeNode = (GroupTreeNode) event.getDropValue();

        if (dragTreeNode.equals(dropTreeNode))
        {
            return;
        }

        dragTreeNode.setParent(null);
        dropTreeNode.addChildNode(dragTreeNode);

    }

    /**
     * Action called by commandLinks or commandButtons to change the current state of the page from VIEW,EDIT,ADD
     * 
     */
    public void changeState()
    {
        String state = (String) getParameter("state");
        if (State.ADD.toString().equals(state))
        {
            groupState = State.ADD;
            inProgressGroupNode = createNewGroupNode();
        }
        if (State.EDIT.toString().equals(state))
        {
            groupState = State.EDIT;
            inProgressGroupNode = new GroupTreeNode(new Group(), organizationHierarchy);
            copyGroupTreeNode(selectedGroupNode, inProgressGroupNode);
        }
        if (State.VIEW.toString().equals(state))
        {
            groupState = State.VIEW;
            inProgressGroupNode = null; // Clear out the group that was being worked on.
        }
    }

    public void changeSelectedGroup(javax.faces.event.ActionEvent event)
    {
        groupState = State.VIEW;
    }

    public void updateGroupNode()
    {
        copyGroupTreeNode(inProgressGroupNode, selectedGroupNode);
        groupDAO.update(selectedGroupNode.getGroup());
    }

    public void saveGroupNode()
    {
        inProgressGroupNode.setParent(selectedGroupNode);
        treeStateMap.put(selectedGroupNode.getGroup().getGroupID(), true);
        groupDAO.create(inProgressGroupNode.getGroup().getGroupID(), inProgressGroupNode.getGroup());

        selectedGroupNode = inProgressGroupNode;

        // TODO this is temporary. It would be better to refresh the organizationHierarchy from the DB
        organizationHierarchy.getGroupList().add(inProgressGroupNode.getGroup());
        getProUser().setGroupHierarchy(organizationHierarchy);
        inProgressGroupNode = null;
        topLevelNodes = null;
        changeState();
    }

    private void copyGroupTreeNode(GroupTreeNode copyFromNode, GroupTreeNode copyToNode)
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

    private GroupTreeNode createNewGroupNode()
    {
        Group group = new Group();
        group.setAccountID(getAccountID());
        group.setGroupID(++id_counter);
        group.setMapZoom(3);
        group.setMapCenter(new LatLng(40.2, -111));
        GroupTreeNode newGroupNode = new GroupTreeNode(group, organizationHierarchy);
        return newGroupNode;
    }

    public String updateGroupMapValues()
    {
        // Do nothing
        return null;
    }
    
    public List<SelectItem> getGroupTypeList(){
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for(GroupType groupType:EnumSet.allOf(GroupType.class)){
            selectItems.add(new SelectItem(groupType,groupType.toString()));
        }
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
        return getAccountID().toString();
    }

    public GroupTreeNode getSelectedGroupNode()
    {
        return selectedGroupNode;
    }

    public void setSelectedGroupNode(GroupTreeNode selectedGroupNode)
    {
        if (selectedGroupNode.getGroup() != null)
        {
            selectedGroupDriverCount = driverDAO.getAllDrivers(selectedGroupNode.getGroup().getGroupID()).size();
            selectedGroupVehicleCount = vehicleDAO.getVehiclesInGroupHierarchy(selectedGroupNode.getGroup().getGroupID()).size();
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

    public String getGroupState()
    {
        return groupState.toString();
    }

    public Person getSelectedPerson()
    {
        if (selectedGroupNode != null)
        {
            selectedPerson = personDAO.findByID(selectedGroupNode.getGroup().getManagerID());
        }
        return selectedPerson;
    }

    public void setSelectedPerson(Person selectedPerson)
    {
        this.selectedPerson = selectedPerson;
    }

    public GroupTreeNode getInProgressGroupNode()
    {
        return inProgressGroupNode;
    }

    public void setInProgressGroupNode(GroupTreeNode inProgressGroupNode)
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

}
