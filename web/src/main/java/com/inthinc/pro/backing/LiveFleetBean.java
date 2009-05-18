package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.map.MapIcon;
import com.inthinc.pro.map.MapIconFactory;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LatLng;

public class LiveFleetBean extends BaseBean
{
    private static final Logger  logger = Logger.getLogger(LiveFleetBean.class);

    private NavigationBean       navigation;
    private GroupDAO             groupDAO;
    private DriverDAO            driverDAO;
    private VehicleDAO           vehicleDAO;
    private DeviceDAO            deviceDAO;
    private LatLng               addressLatLng;
    private Integer              addressZoom;
    private Integer              maxCount;
    private Integer              numRecords;
    private List<DriverLocation> drivers;
    private IconMap              mapIconMap;
    private IconMap              legendIconMap;
    private GroupHierarchy       organizationHierarchy;
    private Integer              selectedVehicleID;
    private Integer              selectedDriverID;
    private List<Group>          displayedGroups;
    private AddressLookup        addressLookup;

    public void initBean()
    {
        organizationHierarchy = getGroupHierarchy();
        addressLatLng = getGroupHierarchy().getTopGroup().getMapCenter();
        addressZoom = getGroupHierarchy().getTopGroup().getMapZoom();
        
        if (maxCount == null)
            maxCount = 10;
    }

    private void populateDriverLocations()
    {
        // Get drivers
        Group fleetGroup = organizationHierarchy.getTopGroup();
        setDrivers(vehicleDAO.getVehiclesNearLoc(fleetGroup.getGroupID(), maxCount, addressLatLng.getLat(), addressLatLng.getLng()));
        setNumRecords(drivers.size());

        // Get colored map icons.
        MapIconFactory mif = new MapIconFactory();
        List<MapIcon> mapIcons = mif.getMapIcons(MapIconFactory.IconType.MARKER, 24);
        List<MapIcon> legendIcons = mif.getMapIcons(MapIconFactory.IconType.LEGEND, 24);

        Iterator<MapIcon> mapIconIt = mapIcons.iterator();
        Iterator<MapIcon> legendIconIt = legendIcons.iterator();

        mapIconMap = new IconMap();
        legendIconMap = new IconMap();
        displayedGroups = new ArrayList<Group>();

        Integer count = 1;
        for (DriverLocation driver : drivers)
        {
            driver.setPosition((count++));
            driver.setAddressStr(addressLookup.getAddress(driver.getLoc().getLat(), driver.getLoc().getLng()));
            driver.setDevice(deviceDAO.findByID(driver.getVehicle().getDeviceID()));  // ADD TO return map for getVehiclesNearLoc()
            
            // Add groups to Group map for Legend
            if (!displayedGroups.contains(organizationHierarchy.getGroup(driver.getVehicle().getGroupID())))
            {
                displayedGroups.add(organizationHierarchy.getGroup(driver.getVehicle().getGroupID()));
            }

            // Build list of map icons
            if (!mapIconMap.icons.containsKey(driver.getVehicle().getGroupID()))
            {
                mapIconMap.addIcon(driver.getVehicle().getGroupID(), mapIconIt.next().getUrl());
            }

            // Build list of legend icons
            if (!legendIconMap.icons.containsKey(driver.getVehicle().getGroupID()))
            {
                legendIconMap.addIcon(driver.getVehicle().getGroupID(), legendIconIt.next().getUrl());
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

    public List<Group> getDisplayedGroups()
    {
        return displayedGroups;
    }

    public void setDisplayedGroups(List<Group> displayedGroups)
    {
        this.displayedGroups = displayedGroups;
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
    
    public DeviceDAO getDeviceDAO()
    {
        return deviceDAO;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO)
    {
        this.deviceDAO = deviceDAO;
    }

    public AddressLookup getAddressLookup()
    {
        return addressLookup;
    }

    public void setAddressLookup(AddressLookup addressLookup)
    {
        this.addressLookup = addressLookup;
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

    public Integer getSelectedDriverID()
    {
        return selectedDriverID;
    }

    public void setSelectedDriverID(Integer selectedDriverID)
    {
        this.selectedDriverID = selectedDriverID;
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

    public IconMap getLegendIconMap()
    {
        return legendIconMap;
    }

    public void setLegendIconMap(IconMap legendIconMap)
    {
        this.legendIconMap = legendIconMap;
    }

    public Integer getNumRecords()
    {
        return numRecords;
    }

    public void setNumRecords(Integer numRecords)
    {
        this.numRecords = numRecords;
    }

    public String driverDetailAction()
    {
        navigation.setDriver(driverDAO.findByID(selectedDriverID));
        return "go_driver";
    }
}
