package com.inthinc.pro.backing.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.DOTOfficeType;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;

public class GroupTreeNodeImpl extends BaseTreeNodeImpl<Group>
{

    /**
     * 
     */
    private static final long serialVersionUID = -142554520134338198L;

    private static final Logger logger = Logger.getLogger(GroupTreeNodeImpl.class);

    private GroupHierarchy groupHierarchy;

    private transient VehicleDAO vehicleDAO;
    private transient DriverDAO driverDAO;
    private transient UserDAO userDAO;
    private transient DeviceDAO deviceDAO;

    @SuppressWarnings("unchecked")
    public GroupTreeNodeImpl(Group group, BaseTreeNodeImpl parentNode, GroupHierarchy groupHierarchy)
    {
        super(group, parentNode);
        setId(group.getGroupID());
        setLabel(group.getName());
        this.groupHierarchy = groupHierarchy;

    }

    public GroupTreeNodeImpl(Group group, GroupHierarchy groupHierarchy)
    {
        super(group);
        if (group != null && group.getGroupID() != null)
        {
            setId(group.getGroupID());
            setLabel(group.getName());
        }
        this.groupHierarchy = groupHierarchy;

    }

    @Override
    public TreeNodeType loadTreeNodeType()
    {
        TreeNodeType resultType = TreeNodeType.TEAM;
        if (baseEntity != null && baseEntity.getType() != null)
        {
            switch (baseEntity.getType()) {
            case FLEET:
                resultType = TreeNodeType.FLEET;
                break;
            case DIVISION:
                resultType = TreeNodeType.DIVISION;
                break;
            case TEAM:
            default:
                resultType = TreeNodeType.TEAM;
                break;
            }
        }
        return resultType;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected List<BaseTreeNodeImpl> loadChildNodes()
    {
        List<BaseTreeNodeImpl> childNodes = new ArrayList<BaseTreeNodeImpl>();
        if (baseEntity == null && groupHierarchy.getGroupList().size() > 0)
        {
            GroupTreeNodeImpl groupTreeNode = new GroupTreeNodeImpl(groupHierarchy.getTopGroup(), groupHierarchy);
            groupTreeNode.setDriverDAO(driverDAO);
            groupTreeNode.setVehicleDAO(vehicleDAO);
            childNodes.add(groupTreeNode);
        }

        if (!getTreeNodeType().equals(TreeNodeType.TEAM))
        {
            List<Group> groupList = groupHierarchy.getChildren(baseEntity);
            if (groupList != null)
            {
                for (Group g : groupList)
                {
                    if (g.getMapCenter() == null)
                    { // Set the initial view to that of the parent
                        g.setMapCenter(new LatLng(baseEntity.getMapCenter().getLat(), baseEntity.getMapCenter().getLng()));
                        g.setMapZoom(baseEntity.getMapZoom());
                    }
                    GroupTreeNodeImpl treeNode = new GroupTreeNodeImpl(g, this, groupHierarchy);
                    treeNode.setDriverDAO(driverDAO);
                    treeNode.setVehicleDAO(vehicleDAO);
                    treeNode.setUserDAO(userDAO);
                    treeNode.setDeviceDAO(deviceDAO);
                    childNodes.add(treeNode);
                }
            }

        }
        else
        {
            logger.debug("Group: " + baseEntity);
            List<Driver> driverList = driverDAO.getAllDrivers(baseEntity.getGroupID());
            if (driverList != null)
            {
                for (Driver d : driverList)
                {
                    DriverTreeNodeImpl treeNode = new DriverTreeNodeImpl(d, this);
                    treeNode.setDeviceDAO(deviceDAO);
                    treeNode.setVehicleDAO(vehicleDAO);
                    treeNode.setGroupHeHierarchy(groupHierarchy);
                    childNodes.add(treeNode);
                }
            }
            List<Vehicle> vehicleList = vehicleDAO.getVehiclesInGroupHierarchy(baseEntity.getGroupID());
            if (vehicleList != null)
            {
                for (Vehicle v : vehicleList)
                {
                    VehicleTreeNodeImpl vehicleTreeNodeImpl = new VehicleTreeNodeImpl(v, this);
                    vehicleTreeNodeImpl.setDeviceDAO(deviceDAO);
                    vehicleTreeNodeImpl.setDriverDAO(driverDAO);
                    vehicleTreeNodeImpl.setVehicleDAO(vehicleDAO);
                    childNodes.add(vehicleTreeNodeImpl);
                }
            }
        }

        // Load Users
        if (baseEntity != null)
        {
            List<User> userList = userDAO.getUsersInGroupHierarchy(baseEntity.getGroupID());
            for (User u : userList)
            {
                if (baseEntity.getGroupID().equals(u.getGroupID()))
                {
                    UserTreeNodeImpl treeNode = new UserTreeNodeImpl(u, this);
                    childNodes.add(treeNode);
                }
            }
        }
        this.childNodes = childNodes;

        sortChildren();
        return childNodes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public BaseTreeNodeImpl getParent()
    {
        if (parentNode == null && baseEntity != null)
        {
            Group parentGroup = groupHierarchy.getParentGroup(baseEntity);
            if (parentGroup != null)
            {
                parentNode = new GroupTreeNodeImpl(parentGroup, groupHierarchy);
            }
            else
            // The reason for creating this empty tree node is because if the root node doesn't have a parent, it doesn't display in the tree.
            {
                parentNode = new GroupTreeNodeImpl(null, groupHierarchy);
            }
        }

        return parentNode;
    }

    @Override
    public boolean getAllowsChildren()
    {
        return true;
    }

    @Override
    public boolean isLeaf()
    {
        boolean returnBoolean = super.isLeaf();
        if (baseEntity == null)
        {
            returnBoolean = true;
        }
        return returnBoolean;
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
    public GroupTreeNodeImpl findTreeNodeByGroupId(Integer groupID)
    {
        GroupTreeNodeImpl node = null;
        if ((this.getTreeNodeType() == TreeNodeType.DIVISION || this.getTreeNodeType() == TreeNodeType.FLEET || this.getTreeNodeType() == TreeNodeType.TEAM)
                && this.baseEntity.getGroupID().equals(groupID))
        {
            node = this;
        }
        else
        {
            if ((this.getTreeNodeType() == TreeNodeType.DIVISION || this.getTreeNodeType() == TreeNodeType.FLEET) && this.getChildCount() > 0)
            {
                for (BaseTreeNodeImpl treeNode : getChildrenNodes())
                {
                    if (node == null)
                    {
                        switch (treeNode.getTreeNodeType()) {
                        case DIVISION:
                        case FLEET:
                        case TEAM:
                            GroupTreeNodeImpl groupTreeNode = (GroupTreeNodeImpl) treeNode;
                            node = groupTreeNode.findTreeNodeByGroupId(groupID);
                            break;
                        default:
                        }

                    }

                }
            }
        }

        return node;

    }

    public boolean hasChildCroups()
    {
        for(BaseTreeNodeImpl<?> treeNode : getChildrenNodes())
        {
            switch(treeNode.getTreeNodeType())
            {
            case DIVISION:
            case FLEET:
            case TEAM: return true;
            }
        }
        
        return false;
    }

    public GroupLevel getGroupLevel()
    {
        return GroupLevel.getGroupLevel(this.baseEntity);
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

    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    public UserDAO getUserDAO()
    {
        return userDAO;
    }

    public Group getGroup()
    {
        return baseEntity;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO)
    {
        this.deviceDAO = deviceDAO;
    }

    public DeviceDAO getDeviceDAO()
    {
        return deviceDAO;
    }

    @Override
    public DOTOfficeType getSubType() {
        if (baseEntity != null && baseEntity.getDotOfficeType() != null)
        {
            return baseEntity.getDotOfficeType();
        }
        return DOTOfficeType.NONE;
    }

}
