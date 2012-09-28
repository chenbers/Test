package com.inthinc.pro.backing;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.model.GroupLevel;
import com.inthinc.pro.backing.model.GroupTreeNodeImpl;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Vehicle;

public class NavigationBean extends BaseBean 
{
    
    
    /**
     * 
     */
    private static final long serialVersionUID = -5482494970488726972L;

    private static final Logger logger = Logger.getLogger(NavigationBean.class);

    // Spring managed beans
    private GroupDAO groupDAO;
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;

    private DurationBean durationBean;
    
    // Key to using widgets
    private Integer groupID;
    private Group group;

    // This is used for the bread crumbs
    private GroupTreeNodeImpl groupTreeNode;
    
    // Passed to various pages
    private Driver driver;
    private Integer driverID;
    private Vehicle vehicle;
    
    
    // Capture for sort
    private String sortedFirst = "false";
    private String sortedSecond = "false";
    
    // LiveFleet result count property.
    // Unable to persist maxCount in liveFleetBean with Tomahawk when using a4j:jsFunction
    private Integer liveFleetCount;
    
    public NavigationBean()
    {

    }

    public DurationBean getDurationBean()
    {
        return durationBean;
    }

    public void setDurationBean(DurationBean durationBean)
    {
        this.durationBean = durationBean;
    }

    public Integer getGroupID()
    {
        initGroup();
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        // Lets not load a new group if we don't need to
        if (this.groupID != groupID && groupDAO != null && groupID != 0) // groupID ZERO is for Unknown Driver
        {
            group = getGroupHierarchy().getGroup(groupID);
           
            this.setGroupTreeNode(new GroupTreeNodeImpl(group, getGroupHierarchy()));
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
        setGroupID(vehicle.getGroupID());
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
        initGroup();
        return group;
    }

    public void setGroupTreeNode(GroupTreeNodeImpl groupTreeNode)
    {
        this.groupTreeNode = groupTreeNode;
    }

    public GroupTreeNodeImpl getGroupTreeNode()
    {
        return groupTreeNode;
    }

    public String groupPageAction()
    {        
        GroupLevel groupLevel = GroupLevel.getGroupLevel(group);
        return groupLevel.getLocation();
    }

    public String homeAction()
    {
        GroupLevel groupLevel = GroupLevel.getGroupLevel(getGroupHierarchy().getTopGroup());
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

    public Integer getLiveFleetCount()
    {
        return liveFleetCount;
    }

    public void setLiveFleetCount(Integer liveFleetCount)
    {
        logger.debug("Count being set to: " + liveFleetCount);
        this.liveFleetCount = liveFleetCount;
    }

    private void initGroup(){
//       //DebugUtil.dumpRequestParameterMap();
//
//        //Initially the site was set up so that if no groupID was sent in the request, then the group would be
//        // set to the top level group. This is how I believe it should be, but there are many requests that are not including the groupID.
//        // Because of this, this bean was changed to leave the group alone if the groupID is not passed... A quick fix is included to set the group to the
//        // top level if it is the home page. This needs to be changed in the future.
//        FacesContext ctx = FacesContext.getCurrentInstance();
//        String groupID = (String) ctx.getExternalContext().getRequestParameterMap().get("groupID");
//       
//        if (groupID != null)
//        {
//            logger.debug("initGroupID from request: " + groupID);
//            setGroupID(Integer.valueOf(groupID));
//        }
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }

    public void setDriverID(Integer driverID)
    {
        logger.debug("DRIVER ID: " + driverID);
        driver = driverDAO.findByID(driverID);
        if(driverID != null && driver != null)
        {
            setDriver(driver);
        }
            
        this.driverID = driverID;
    }
    
    public void setVehicleID(Integer vehicleID)
    {
        if(vehicleID != null)
            vehicle = vehicleDAO.findByID(vehicleID);
    }

    public Integer getDriverID()
    {
        return driverID;
    }
    
    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }
    
    public void testAction()
    {
        logger.debug("in test action");
    }

  
}
