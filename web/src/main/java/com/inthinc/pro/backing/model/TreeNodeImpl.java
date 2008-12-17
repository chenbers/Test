package com.inthinc.pro.backing.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.richfaces.model.SwingTreeNodeImpl;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.BaseEntity;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Vehicle;

public class TreeNodeImpl extends SwingTreeNodeImpl implements Serializable
{

    private static final long serialVersionUID = 6735997209608844223L;

    private Group group;
    private transient Vehicle vehicle;
    private transient Driver driver;
    private TreeNodeType treeNodeType;
    private Integer id;
    private String label;

    private TreeNodeImpl parentGroupTreeNode;
    private List<TreeNodeImpl> childGroupTreeNodes;
    private GroupHierarchy groupHierarchyUtil;
    private GroupLevel groupLevel;

    private transient VehicleDAO vehicleDAO;
    private transient DriverDAO driverDAO;

    // We are storing these values here because once loaded, we don't want to have to pull them again.
    private static final Logger logger = Logger.getLogger(TreeNodeImpl.class);

    /*
     * Make sure that any group that is passed in has a parent. For some reason the tree view doesn't render correctly if there is no parent.
     */
    // TODO use generics
    public TreeNodeImpl(BaseEntity hierarchalEntity, GroupHierarchy groupHierarchy)
    {
        initialize(hierarchalEntity, groupHierarchy, null);
    }

    public TreeNodeImpl(BaseEntity hierarchalEntity, GroupHierarchy groupHierarchy, TreeNodeImpl parent)
    {
        initialize(hierarchalEntity, groupHierarchy, parent);
    }

    private void initialize(BaseEntity hierarchalEntity, GroupHierarchy groupHierarchy, TreeNodeImpl parent)
    {
        if (hierarchalEntity instanceof Group)
        {
            this.group = (Group) hierarchalEntity;
            if (group.getGroupID() != null)
            {
                this.setId(this.group.getGroupID());
                this.setLabel(this.group.getName());
            }
        }
        else if (hierarchalEntity instanceof Driver)
        {
            this.driver = (Driver) hierarchalEntity;
            this.setId(this.driver.getDriverID());
            this.setLabel(this.getDriver().getPerson().getFirst() + " " + this.getDriver().getPerson().getLast());
        }
        else if (hierarchalEntity instanceof Vehicle)
        {
            this.vehicle = (Vehicle) hierarchalEntity;
            this.setLabel(this.getVehicle().getName());
            this.setId(this.vehicle.getVehicleID());
        }
        this.groupHierarchyUtil = groupHierarchy;
        this.parentGroupTreeNode = parent;
        this.setData(this);
    }

    public void setGroup(Group group)
    {
        this.group = group;
    }

    public GroupLevel getGroupLevel()
    {
        if (groupLevel == null && group != null)
        {
            switch (treeNodeType) {
            case FLEET:
                groupLevel = GroupLevel.FLEET;
                break;
            case DIVISION:
                groupLevel = GroupLevel.DIVISION;
                break;
            case TEAM:
                groupLevel = GroupLevel.DIVISION;
                break;
            }
            
            if(group.equals(groupHierarchyUtil.getTopGroup())){
                groupLevel = GroupLevel.FLEET;
            }

        }
        return groupLevel;
    }

    public Group getGroup()
    {
        return group;
    }
    
    

    // TODO get bread crumb working
    public List<TreeNodeImpl> getGroupBreadCrumb()
    {
        List<TreeNodeImpl> breadCrumbList = new ArrayList<TreeNodeImpl>();
        loadBreadCrumbs(this, breadCrumbList);
        Collections.reverse(breadCrumbList);
        return breadCrumbList;
    }

    private void loadBreadCrumbs(TreeNodeImpl node, List<TreeNodeImpl> breadCrumbList)
    {
        switch (node.getTreeNodeType()) {
        case FLEET:
        case DIVISION:
        case TEAM:
            breadCrumbList.add(node);
        }
        if (node.getParent() != null)
        {
            loadBreadCrumbs(node.getParent(), breadCrumbList);
        }
    }

    public void addChildNode(TreeNodeImpl groupTreeNode)
    {
        if (childGroupTreeNodes == null)
        {
            loadChildNodes();
        }

        childGroupTreeNodes.add(groupTreeNode);
    }

    public void removeChildNode(TreeNodeImpl groupTreeNode)
    {
        if (childGroupTreeNodes == null)
        {
            loadChildNodes();
        }

        childGroupTreeNodes.remove(groupTreeNode);
    }

    public void setParent(TreeNodeImpl parentGroupTreeNode)
    {
        if (parentGroupTreeNode == null)
        {
            this.parentGroupTreeNode.removeChildNode(this);
            this.parentGroupTreeNode = parentGroupTreeNode;
        }
        else if (parentGroupTreeNode.getGroup() != null && this.parentGroupTreeNode != parentGroupTreeNode)
        {
            parentGroupTreeNode.addChildNode(this);
            if (this.parentGroupTreeNode != null)
            {
                this.parentGroupTreeNode.removeChildNode(this);
            }
            this.parentGroupTreeNode = parentGroupTreeNode;
        }
    }

    /**
     * TreeNode Implementation
     */

    @Override
    public Enumeration children()
    {
        if (childGroupTreeNodes == null)
        {
            loadChildNodes();
        }
        return Collections.enumeration(childGroupTreeNodes);
    }

    /**
     * This is not used by the tree component
     */
    public List<TreeNodeImpl> getChildrenNodes()
    {
        if (childGroupTreeNodes == null)
        {
            loadChildNodes();
        }
        return childGroupTreeNodes;
    }

    @Override
    public boolean getAllowsChildren()
    {
        boolean result;
        if (getTreeNodeType() == TreeNodeType.DRIVER || getTreeNodeType() == TreeNodeType.VEHICLE)
        {
            result = false;
        }
        else
        {
            result = true;
        }
        return result;
    }

    @Override
    public TreeNodeImpl getChildAt(int childIndex)
    {
        if (childGroupTreeNodes == null)
        {
            loadChildNodes();
        }
        return childGroupTreeNodes.get(childIndex);
    }

    @Override
    public int getChildCount()
    {
        if (childGroupTreeNodes == null)
        {
            loadChildNodes();
        }
        return childGroupTreeNodes.size();
    }

    @Override
    public int getIndex(javax.swing.tree.TreeNode node)
    {
        TreeNodeImpl treeNode = (TreeNodeImpl)node;
        return this.childGroupTreeNodes.indexOf(treeNode);
    }

    @Override
    public TreeNodeImpl getParent()
    {

        if (parentGroupTreeNode == null && group != null)
        {
            Group parentGroup = groupHierarchyUtil.getParentGroup(group);
            if (parentGroup != null)
            {
                parentGroupTreeNode = new TreeNodeImpl(parentGroup, groupHierarchyUtil);
            }
            else
            {
                parentGroupTreeNode = new TreeNodeImpl(null, groupHierarchyUtil);
            }
        }

        return parentGroupTreeNode;

    }

    @Override
    public boolean isLeaf()
    {
        boolean result;
        if (group == null)
        {
            result = true;
        }
        else
        {
            if (childGroupTreeNodes == null)
            {
                loadChildNodes();
            }
            result = childGroupTreeNodes.size() > 0 ? false : true;
        }
        return result;
    }
    
    @Override
    public void addChild(Object key, TreeNode node)
    {
        logger.info("Add Child Called");
        super.addChild(key, node);
    }
    
    private List<TreeNodeImpl> loadChildNodes()
    {
        List<TreeNodeImpl> childNodes = new ArrayList<TreeNodeImpl>();
        if (group == null && vehicle == null && driver == null && groupHierarchyUtil.getGroupList().size() > 0)
        {
            TreeNodeImpl groupTreeNode = new TreeNodeImpl(groupHierarchyUtil.getTopGroup(), groupHierarchyUtil);
            groupTreeNode.setDriverDAO(driverDAO);
            groupTreeNode.setVehicleDAO(vehicleDAO);
            childNodes.add(groupTreeNode);
        }

        if (getTreeNodeType() != TreeNodeType.TEAM)
        {
            List<Group> groupList = groupHierarchyUtil.getChildren(group);
            if (groupList != null)
            {
                for (Group g : groupList)
                {
                    if (g.getMapCenter() == null)
                    { // Set the initial view to that of the parent
                        g.setMapCenter(new LatLng(group.getMapCenter().getLat(), group.getMapCenter().getLng()));
                        g.setMapZoom(group.getMapZoom());
                    }
                    TreeNodeImpl groupTreeNode = new TreeNodeImpl(g, groupHierarchyUtil, this);
                    groupTreeNode.setDriverDAO(driverDAO);
                    groupTreeNode.setVehicleDAO(vehicleDAO);
                    childNodes.add(groupTreeNode);
                }
            }

        }
        else
        {
            List<Driver> driverList = driverDAO.getAllDrivers(group.getGroupID());
            if (driverList != null)
            {
                for (Driver d : driverList)
                {
                    TreeNodeImpl groupTreeNode = new TreeNodeImpl(d, groupHierarchyUtil, this);
                    groupTreeNode.setDriverDAO(driverDAO);
                    groupTreeNode.setVehicleDAO(vehicleDAO);
                    childNodes.add(groupTreeNode);

                }
            }
            List<Vehicle> vehicleList = vehicleDAO.getVehiclesInGroupHierarchy(group.getGroupID());
            if (vehicleList != null)
            {
                for (Vehicle v : vehicleList)
                {
                    TreeNodeImpl groupTreeNode = new TreeNodeImpl(v, groupHierarchyUtil, this);
                    groupTreeNode.setDriverDAO(driverDAO);
                    groupTreeNode.setVehicleDAO(vehicleDAO);
                    childNodes.add(groupTreeNode);

                }
            }
        }
        childGroupTreeNodes = childNodes;
        return childGroupTreeNodes;
    }

    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle()
    {
        return vehicle;
    }

    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

    public Driver getDriver()
    {
        return driver;
    }

    public void setTreeNodeType(TreeNodeType treeNodeType)
    {
        this.treeNodeType = treeNodeType;
    }

    public TreeNodeType getTreeNodeType()
    {
        if (treeNodeType == null)
        {
            if (group != null && treeNodeType == null)
            {
                switch (group.getType()) {
                case FLEET:
                    treeNodeType = TreeNodeType.FLEET;
                    break;
                case DIVISION:
                    treeNodeType = TreeNodeType.DIVISION;
                    break;
                default:
                case TEAM:
                    treeNodeType = TreeNodeType.TEAM;
                    break;
                }
            }
            else if (vehicle != null)
            {
                treeNodeType = TreeNodeType.VEHICLE;
            }
            else
            {
                treeNodeType = TreeNodeType.DRIVER;
            }
        }

        return treeNodeType;
    }

    /**
     * find a matching tree node within itself and its children
     * 
     * @param treeNodeId
     *            (node id to search for)
     * @param type
     *            (node type to search for)
     * @return
     */
    public TreeNodeImpl findTreeNodeByGroupId(Integer groupID)
    {
        TreeNodeImpl node = null;
        if ((this.treeNodeType == TreeNodeType.DIVISION || this.treeNodeType == TreeNodeType.FLEET || this.treeNodeType == TreeNodeType.TEAM)
                && this.group.getGroupID().equals(groupID))
        {
            node = this;
        }
        else
        {
            if ((this.treeNodeType == TreeNodeType.DIVISION || this.treeNodeType == TreeNodeType.FLEET) && this.getChildCount() > 0)
            {
                for (TreeNodeImpl treeNode : getChildrenNodes())
                {
                    if (node == null)
                    {
                        node = treeNode.findTreeNodeByGroupId(groupID);
                    }

                }
            }
        }

        return node;

    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return label;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

}
