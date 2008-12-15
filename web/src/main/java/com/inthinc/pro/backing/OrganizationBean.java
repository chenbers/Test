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
import com.inthinc.pro.backing.model.GroupTreeNode;
import com.inthinc.pro.backing.model.TreeNodeType;
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
    private Group selectedParentGroup;

    public OrganizationBean()
    {
        groupState = State.VIEW;
    }

    /*
     * This returns a list of nodes that are contained in top level that the user has access to view. This bean is session scope so in order to reload the group heirarchy, the
     * topLevelNodes variable needs to be set to null
     */
    public List<GroupTreeNode> getTopLevelNodes()
    {
        if (topLevelNodes == null)
        {
            topLevelNodes = new ArrayList<GroupTreeNode>();

            organizationHierarchy = new GroupHierarchy(groupDAO.getGroupHierarchy(getAccountID(), getGroupHierarchy().getTopGroup().getGroupID()));

            Group topLevelGroup = organizationHierarchy.getTopGroup();
           
            GroupTreeNode topLevelNode = new GroupTreeNode(topLevelGroup, organizationHierarchy);
            topLevelNode.setDriverDAO(driverDAO);
            topLevelNode.setVehicleDAO(vehicleDAO);
            setSelectedGroupNode(topLevelNode);
            topLevelNodes.add(topLevelNode);

            logger.info("Group Hirarchy was Loaded");

        }
        return topLevelNodes;
    }

    public void setTopLevelNodes(List<GroupTreeNode> topLevelNodes)
    {
        this.topLevelNodes = topLevelNodes;
    }

    /*
     * Not in use - Causes the app to run too slow
     */
    public boolean adviseNodeSelected(UITree tree)
    {
        if (tree != null && tree.getRowData() != null && selectedGroupNode != null)
        {
            GroupTreeNode treeNode = (GroupTreeNode) tree.getRowData();
            if (treeNode != null && selectedGroupNode.getTreeNodeType() == treeNode.getTreeNodeType() && selectedGroupNode.getId() == treeNode.getId())
            {
                return true;

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
        GroupTreeNode treeNode = (GroupTreeNode) tree.getRowData();
        setSelectedGroupNode(treeNode);
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

    /*
     * BEGIN CRUD methods
     */

    public void edit()
    {
        groupState = State.EDIT;
        inProgressGroupNode = new GroupTreeNode(new Group(), organizationHierarchy);
        copyGroupTreeNode(selectedGroupNode, inProgressGroupNode);
        if (selectedGroupNode.getParent() != null && selectedGroupNode.getParent().getGroup() != null)
        {
            selectedParentGroup = selectedGroupNode.getParent().getGroup();
        }
    }

    public void view()
    {
        groupState = State.VIEW;
        inProgressGroupNode = null; // Clear out the group that was being worked on.
    }

    public void add()
    {
        groupState = State.ADD;

        inProgressGroupNode = createNewGroupNode();
        if (selectedGroupNode.getParent() != null && selectedGroupNode.getParent().getGroup() != null)
        {
            selectedParentGroup = selectedGroupNode.getGroup();
        }
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
            GroupTreeNode parentNode = null;
            if (selectedParentGroup != null)
            {
                for (GroupTreeNode treeNode : topLevelNodes)
                {
                    parentNode = treeNode.findTreeNodeByGroupId(selectedParentGroup.getGroupID());
                }
                selectedGroupNode.getGroup().setParentID(parentNode.getGroup().getGroupID());
                selectedGroupNode.setParent(parentNode);
            }
            
           
            groupDAO.update(selectedGroupNode.getGroup());
            updateUsersGroupHeirarchy();
            this.addInfoMessage("Group: " + selectedGroupNode.getLabel() + " - Updated");
            selectedParentGroup = null;
            groupState = State.VIEW;
          
        }
    }

    /**
     * Action called to create a new group node record
     */
    public void save()
    {
        GroupTreeNode parentNode = null;
        for (GroupTreeNode treeNode : topLevelNodes)
        {
            parentNode = treeNode.findTreeNodeByGroupId(selectedParentGroup.getGroupID());
        }
        inProgressGroupNode.setParent(parentNode);
        inProgressGroupNode.getGroup().setParentID(parentNode.getGroup().getGroupID());
        // treeStateMap.put(selectedGroupNode.getGroup().getGroupID(), true);
        if (validate(inProgressGroupNode))
        {
            Integer id = groupDAO.create(null, inProgressGroupNode.getGroup());
            if (id > 0)
            {
                inProgressGroupNode.getGroup().setGroupID(id);
                selectedGroupNode = inProgressGroupNode;
                updateUsersGroupHeirarchy();
                inProgressGroupNode = null;
                topLevelNodes = null;
                selectedParentGroup = null;
                this.addInfoMessage("Group: " + selectedGroupNode.getLabel() + " - Added");
                groupState = State.VIEW;
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
     * Case 1: If there are drivers/devices/or vehicles then the group has to be a team
     * 
     * @param treeNode treeNode containing the group to validate
     * @return returns true if validation is successfully
     */
    private boolean validate(GroupTreeNode treeNode)
    {
        boolean valid = true;
        //Case 1
        if(treeNode.getGroup().getType() != GroupType.TEAM){
            int driverCount = driverDAO.getAllDrivers(treeNode.getGroup().getGroupID()).size();
            int vehicleCount = vehicleDAO.getVehiclesInGroupHierarchy(treeNode.getGroup().getGroupID()).size();
            if(vehicleCount > 0 || driverCount > 0)
            {
                addErrorMessage("Group Cannot be changed from Team if there are Drivers, Vehicles attatched to the group");
                valid = false;
            }
        }
        
        //Case 2
        return valid;
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

        for (GroupType groupType : createPossibleGroupTypeList(selectedGroupNode.getGroup()))
        {
            selectItems.add(new SelectItem(groupType, groupType.toString()));
        }

        return selectItems;
    }

    /**
     * This will return a list of group types that are allowed when editing or adding a new group.
     * 
     * @param group
     * @return list of groupTypes that can be used as sub groups under the parent group
     */
    private List<GroupType> createPossibleGroupTypeList(Group group)
    {
        List<GroupType> groupTypeList = new ArrayList<GroupType>(0);

        if (group != null)
        {
            switch (selectedParentGroup.getType()) {
            case FLEET:
            case DIVISION:
                groupTypeList.add(GroupType.DIVISION);
                group: groupTypeList.add(GroupType.TEAM);
                break;
            default:
                break;
            }
        }

        return groupTypeList;
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
        for (GroupTreeNode treeNode : topLevelNodes)
        {
            if (treeNode.getTreeNodeType() == TreeNodeType.FLEET || treeNode.getTreeNodeType() == TreeNodeType.DIVISION)
            {
                selectItemList.add(new SelectItem(treeNode.getGroup(), treeNode.getLabel()));
                selectItemList.addAll(getChildNodesAsSelectItems(treeNode));
            }
        }

        selectItemList.add(0, new SelectItem(null, ""));
        return selectItemList;
    }

    private List<SelectItem> getChildNodesAsSelectItems(GroupTreeNode node)
    {
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        if (node.getChildCount() > 0)
        {
            for (GroupTreeNode n : node.getChildrenNodes())
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

    public void setSelectedParentGroup(Group selectedParentGroup)
    {
        this.selectedParentGroup = selectedParentGroup;
    }

    public Group getSelectedParentGroup()
    {
        return selectedParentGroup;
    }

}
