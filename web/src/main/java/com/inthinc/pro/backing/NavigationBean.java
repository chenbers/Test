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

public class NavigationBean extends BaseDurationBean
{
    private static final Logger logger = Logger.getLogger(NavigationBean.class);

    // Spring managed beans
    private GroupDAO groupDAO;

    // Key to using widgets
    private Integer groupID;
    private Group group;

    // This is used for the bread crumbs
    private TreeNodeImpl groupTreeNode;
    
    // Passed to various pages
    private Driver driver;
    private Vehicle vehicle;
    
    // Trend chart function
    private Integer start = 0;
    private Integer end = 0;
    private String numRowsPerPg = "5";
    
    // Capture for sort
    private String sortedFirst = "false";
    private String sortedSecond = "false";
    
    // LiveFleet result count property.
    // Unable to persist maxCount in liveFleetBean with Tomahawk when using a4j:jsFunction
    private Integer liveFleetCount;
    
    public NavigationBean()
    {

    }

    public Integer getGroupID()
    {
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        // Lets not load a new group if we don't need to
        if (this.groupID != groupID && groupDAO != null)
        {
            group = getGroupHierarchy().getGroup(groupID);
           
            this.setGroupTreeNode(new TreeNodeImpl(group, getGroupHierarchy()));
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
        setGroupID(driver.getGroupID());
    }

    public Vehicle getVehicle()
    {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
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

    public String groupPageAction()
    {        
        GroupLevel groupLevel = getGroupHierarchy().getGroupLevel(group);
        return groupLevel.getLocation();
    }

    public String homeAction()
    {
        GroupLevel groupLevel = getGroupHierarchy().getGroupLevel(getGroupHierarchy().getTopGroup());
        setGroupID(getGroupHierarchy().getTopGroup().getGroupID());

        return groupLevel.getLocation();
    }    

    public String getSortedFirst()
    {
        return sortedFirst;
    }

    public void setSortedFirst(String sortedFirst)
    {
        if (        this.sortedFirst.equalsIgnoreCase("true") ) 
        {
            this.sortedFirst = "false";
        } 
        else if (   this.sortedFirst.equalsIgnoreCase("false") )
        {
            this.sortedFirst = "true";
        }
    }

    public String getSortedSecond()
    {
        return sortedSecond;
    }

    public void setSortedSecond(String sortedSecond)
    {
        if (        this.sortedSecond.equalsIgnoreCase("true") ) 
        {
            this.sortedSecond = "false";
        } 
        else if (   this.sortedSecond.equalsIgnoreCase("false") )
        {
            this.sortedSecond = "true";
        }
    }

    
    public String getNumRowsPerPg()
    {
        return numRowsPerPg;
    }

    public void setNumRowsPerPg(String numRowsPerPg)
    {
        logger.debug("numRowsPerPg " + numRowsPerPg + 
                " start " + this.start + 
                " end " + this.end);
        
        String localStr = new String(numRowsPerPg);
        this.numRowsPerPg = localStr;
        int local = (new Integer(this.numRowsPerPg)).intValue();
        
        // A change here implies the start and end must change
        //  as we are going from standard to flyout
        this.start = 1;
        if ( this.end > local ) {
            this.end = local;
        }
    }

    public Integer getLiveFleetCount()
    {
        return liveFleetCount;
    }

    public void setLiveFleetCount(Integer liveFleetCount)
    {
        logger.debug("Count being set to: " + liveFleetCount);
        this.liveFleetCount = liveFleetCount;
    }
    
    
  
}
