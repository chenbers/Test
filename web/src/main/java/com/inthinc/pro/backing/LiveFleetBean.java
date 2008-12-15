package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.LatLng;

public class LiveFleetBean extends BaseBean
{
    private NavigationBean               navigation;
    private GroupDAO                     groupDAO;
    private DriverDAO                    driverDAO;
    private static final Logger          logger        = Logger.getLogger(LiveFleetBean.class);

    private LatLng                       addressLatLng = null;
    private Integer                      addressZoom;

    private Integer                      maxCount      = 10;
    private Integer                      driverPager   = 1;

    private List<DriverLocation> drivers       = new ArrayList<DriverLocation>();

    private GroupHierarchy               organizationHierarchy;

    public void initBean()
    {
        organizationHierarchy = new GroupHierarchy(groupDAO.getGroupsByAcctID(getAccountID()));
        addressLatLng = organizationHierarchy.getTopGroup().getMapCenter();
        addressZoom = organizationHierarchy.getTopGroup().getMapZoom();
    }
    
    public void initDrivers()
    {
        Group fleetGroup = organizationHierarchy.getTopGroup();
        drivers = driverDAO.getDriversNearLoc(fleetGroup.getGroupID(), maxCount, addressLatLng.getLat(), addressLatLng.getLng());
    }
    
    //DAO PROPERTIES
    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }
    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }
    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }
    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    //DRIVER PAGER PROPERTIES
    public Integer getDriverPager()
    {
        return driverPager;
    }
    public void setDriverPager(Integer driverPager)
    {
        this.driverPager = driverPager;
    }
    
    // ADDRESS LATLNG PROPERTIES
    public LatLng getAddressLatLng()
    {   
        return addressLatLng;
    }
    public void setAddressLatLng(LatLng addressLatLng)
    {
        this.addressLatLng = addressLatLng;
    }
    
    // DEFAULT ZOOM PROPERTIES
    public Integer getAddressZoom()
    {
        return addressZoom;
    }
    public void setAddressZoom(Integer addressZoom)
    {
        this.addressZoom = addressZoom;
    }

    // NAVIGATION PROPERTIES
    public NavigationBean getNavigation()
    {
        return navigation;
    }
    public void setNavigation(NavigationBean navigation)
    {
        this.navigation = navigation;
    }

    //MAX DRIVERS COUNT PROPERTIES
    public Integer getMaxCount()
    {
        return maxCount;
    }
    public void setMaxCount(Integer maxCount)
    {
        this.maxCount = maxCount;
    }

    // DRIVER LAST LOCATION PROPERTIES
    public List<DriverLocation> getDrivers()
    {
        initDrivers();
        return drivers;
    }
    public void setDrivers(List<DriverLocation> drivers)
    {
        this.drivers = drivers;
    }
}
