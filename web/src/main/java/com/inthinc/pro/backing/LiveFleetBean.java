package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.map.MapIcon;
import com.inthinc.pro.map.MapIconFactory;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.util.WebUtil;

public class LiveFleetBean extends BaseBean
{
    private NavigationBean       navigation;
    private GroupDAO             groupDAO;
    private DriverDAO            driverDAO;
    private static final Logger  logger        = Logger.getLogger(LiveFleetBean.class);

    private LatLng               addressLatLng = null;
    private Integer              addressZoom;
    private Integer              maxCount      = 10;
    private Integer              driverPager   = 0;

    private List<DriverLocation> drivers       = new ArrayList<DriverLocation>();

    private IconMap              mapIconMap;

    private List<Group>          childGroups;
    private GroupHierarchy       organizationHierarchy;

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

        childGroups = getGroupHierarchy().getChildren(fleetGroup);
        MapIconFactory mif = new MapIconFactory();

        List<MapIcon> mapIcons = mif.makeMapIcons("images/googleMapIcons/icon_", "images/icon_1.png", 24);
        Iterator<MapIcon> mapIconIt = mapIcons.iterator();

        mapIconMap = new IconMap();

        for (DriverLocation driver : drivers)
        {
            if (!mapIconMap.icons.containsKey(driver.getGroupID()))
            {
                mapIconMap.addIcon(driver.getGroupID(), mapIconIt.next().getUrl());
            }
        }
    }

    // DAO PROPERTIES
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

    // DRIVER PAGER PROPERTIES
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

    // MAX DRIVERS COUNT PROPERTIES
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

    public IconMap getMapIconMap()
    {
        return mapIconMap;
    }

    public void setMapIconMap(IconMap mapIconMap)
    {
        this.mapIconMap = mapIconMap;
    }
    
    public String driverAction() {
        
        Map<String,String> requestMap = new WebUtil().getRequestParameterMap();
        String driverID = requestMap.get("id");

        navigation.setDriver(driverDAO.findByID( Integer.parseInt(driverID) ));

        return "go_driver";
    }
}
