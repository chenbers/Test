package com.inthinc.pro.backing;

import org.apache.log4j.Logger;
import org.omg.PortableServer.POAManagerPackage.State;

import com.inthinc.pro.backing.model.GroupLevel;
import com.inthinc.pro.backing.model.TreeNodeImpl;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;

public class NavigationBean extends BaseBean
{
    private static final Logger logger   = Logger.getLogger(NavigationBean.class);
    
    //Spring managed beans
    private GroupDAO            groupDAO;

    private Integer             groupID;
    private Group               group;
    
    //This is used for the bread crumbs
    private TreeNodeImpl        groupTreeNode;
    private Driver              driver;
    private Vehicle             vehicle;
    private Duration            duration = Duration.DAYS;
    private Integer             start = 0;
    private Integer             end = 0;

    public NavigationBean()
    {
     
    }

    public Integer getGroupID()
    {
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        //Lets not load a new group if we don't need to
        if(this.groupID != groupID && groupDAO != null){
            this.group = groupDAO.findByID(groupID);
            this.setGroupTreeNode(new TreeNodeImpl(group,getGroupHierarchy()));
            logger.debug("Navigation setGroup:" + group.getName());
        }
        logger.debug("Navigation setGroupID: " + groupID);
        this.groupID = groupID;
        
    }
    
    public Driver getDriver()
    {
        return driver;
    }

    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
    public Vehicle getVehicle()
    {
        return vehicle;
    }
    
    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }
    
    public Duration getDuration()
    {
        return duration;
    }

    public void setDuration(Duration duration)
    {
        this.duration = duration;
    }

    public Integer getStart()
    {
        return start;
    }

    public void setStart(Integer start)
    {
        this.start = start;
    }

    public Integer getEnd()
    {
        return end;
    }

    public void setEnd(Integer end)
    {
        this.end = end;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    public void setGroup(Group group)
    {
        this.group = group;
    }

    public Group getGroup()
    {
        return group;
    }

    public void setGroupTreeNode(TreeNodeImpl groupTreeNode)
    {
        this.groupTreeNode = groupTreeNode;
    }

    public TreeNodeImpl getGroupTreeNode()
    {
        return groupTreeNode;
    }
    
    public String homeAction()
    {
        GroupLevel groupLevel = getGroupHierarchy().getGroupLevel(getGroupHierarchy().getTopGroup());
        
        return groupLevel.getLocation();
   
    }

}
