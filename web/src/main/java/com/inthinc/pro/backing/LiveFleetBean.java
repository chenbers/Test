package com.inthinc.pro.backing;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.map.MapIcon;
import com.inthinc.pro.map.MapIconFactory;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.util.WebUtil;

public class LiveFleetBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(LiveFleetBean.class);

    private NavigationBean navigation;
    private GroupDAO groupDAO;
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    private LatLng addressLatLng;
    private Integer addressZoom;
    private Integer maxCount;
    private Integer numRecords;
    private List<DriverLocation> drivers;
    private IconMap mapIconMap;
    private IconMap legendIconMap;
    private GroupHierarchy organizationHierarchy;
    private Integer selectedVehicleID;

    public void initBean()
    {
        organizationHierarchy = getGroupHierarchy();
        addressLatLng = getGroupHierarchy().getTopGroup().getMapCenter();
        addressZoom = getGroupHierarchy().getTopGroup().getMapZoom();
        if(maxCount == null)
            maxCount = 10;  

    }

    private void populateDriverLocations()
    {
        logger.debug("getDriver hit.");


        // Get drivers
        Group fleetGroup = organizationHierarchy.getTopGroup();
        setDrivers(vehicleDAO.getVehiclesNearLoc(fleetGroup.getGroupID(), maxCount, addressLatLng.getLat(), addressLatLng.getLng()));
        setNumRecords(drivers.size());

        logger.debug("getDrivers retieved: " + drivers.size());

        // Get colored map icons.
        MapIconFactory mif = new MapIconFactory();
        List<MapIcon> mapIcons = mif.getMapIcons(MapIconFactory.IconType.MARKER, 24);
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

    // DRIVER LAST LOCATION PROPERTIES
    public List<DriverLocation> getDrivers()
    {
        if (drivers == null || drivers.isEmpty())
            populateDriverLocations();
        return drivers;
    }

    public void setDrivers(List<DriverLocation> drivers)
    {
        this.drivers = drivers;
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

    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
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

    public Integer getSelectedVehicleID()
    {
        return selectedVehicleID;
    }

    public void setSelectedVehicleID(Integer selectedVehicleID)
    {
        this.selectedVehicleID = selectedVehicleID;
    }

    // MAX DRIVERS COUNT PROPERTIES
    public Integer getMaxCount()
    {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount)
    {
        logger.debug("setting count. to " + maxCount.toString());
        this.maxCount = maxCount;
        populateDriverLocations();
    }

    public IconMap getMapIconMap()
    {
        return mapIconMap;
    }

    public void setMapIconMap(IconMap mapIconMap)
    {
        this.mapIconMap = mapIconMap;
    }

    public Integer getNumRecords()
    {
        return numRecords;
    }

    public void setNumRecords(Integer numRecords)
    {
        this.numRecords = numRecords;
    }

    public String driverAction()
    {

        Map<String, String> requestMap = new WebUtil().getRequestParameterMap();
        String driverID = requestMap.get("id");

        navigation.setDriver(driverDAO.findByID(Integer.parseInt(driverID)));

        return "go_driver";
    }
    
    public String vehicleDetailAction()
    {
        navigation.setVehicle(vehicleDAO.findByID(selectedVehicleID));
        return "go_vehicle";
    }
}
