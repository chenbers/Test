package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.map.MapIcon;
import com.inthinc.pro.map.MapIconFactory;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.util.WebUtil;

public class DriverLocationBean extends BaseBean {

	private DriverDAO driverDAO;
	private VehicleDAO vehicleDAO;
	private GroupDAO groupDAO;
    private NavigationBean navigation;
 //   private boolean pageChange = false;
    private LatLng center;
    private Integer zoom = 10;
	private Map<Integer,DriverLastLocationBean> driverLastLocations;
	private List<Group> childGroups;
	private IconMap mapIconMap;
	private List<LegendIcon> legendIcons;
	private Map<Integer,Group> groupMap;
	private boolean teamLevel;
	private List<DriverLastLocationBean> driverLastLocationBeans = new ArrayList<DriverLastLocationBean>();
 	private static final Logger logger = Logger.getLogger(DriverLocationBean.class);
	private GroupHierarchy       organizationHierarchy;
	private Integer selectedDriverID;
	private Integer selectedVehicleID;
	private AddressLookup addressLookup = new AddressLookup();

	public DriverLocationBean() {
		super();
	
	}
	public void init(){
		
		groupMap = new HashMap<Integer,Group>();
		teamLevel = false;
        organizationHierarchy = new GroupHierarchy(groupDAO.getGroupsByAcctID(getAccountID()));
        center = organizationHierarchy.getTopGroup().getMapCenter();
        zoom = organizationHierarchy.getTopGroup().getMapZoom();
		
	}
	public List<DriverLastLocationBean> getDriverLastLocationBeans() 
	{
	    
	    if(driverLastLocationBeans.size() > 0) return driverLastLocationBeans;
	    
	    int validDriverCount = 0;
    	driverLastLocations = new HashMap<Integer,DriverLastLocationBean>();
        driverLastLocationBeans = new ArrayList<DriverLastLocationBean>();
        childGroups = getGroupHierarchy().getChildren(getGroupHierarchy().getGroup(this.navigation.getGroupID()));

   		if (childGroups != null){
        	teamLevel = false;
        	
    		MapAndLegendIconManager malim = new MapAndLegendIconManager();
    		malim.initMapAndLegendIcons(childGroups.size());
         	
         	//Sort the groups by name so the icons appear in the same order as the trend chart
         	Collections.sort(childGroups, new GroupComparator());
 
	        for (Group group:childGroups){
	        		         	
	        	groupMap.put(group.getGroupID(), group);
	        	
	        	malim.addMapAndLegendIcon(group.getGroupID(),group.getName(),null);

		        List<Driver> drivers = driverDAO.getAllDrivers(group.getGroupID());
		        // Do something to get driverLastLocations or last trips to get location
		        validDriverCount += locateGroupDrivers(drivers,group.getGroupID());
	        }
        }
        else {
        	teamLevel = true;
	        List<Driver> drivers = driverDAO.getAllDrivers(this.navigation.getGroupID());
	        Collections.sort(drivers, new DriverComparator());
	        

         	// Do something to get driverLastLocations or last trips to get location
	        validDriverCount = locateTeamDrivers(drivers);
      	
        }
		//calculate better map center - set to location of first driver
		if (validDriverCount == 0){
			
			//Set to center for the group
			setCenter(this.getGroupHierarchy().getTopGroup().getMapCenter());
		}
		else {

			setCenter(driverLastLocationBeans.get(0).getLastLocation());
		}

		return driverLastLocationBeans;
	}
	private DriverLastLocationBean locateOneDriver(Driver driver){
		
        LastLocation loc = null;
        
        if (driver.getDriverID() != null)
            loc = driverDAO.getLastLocation(driver.getDriverID());
        
      
        if (loc != null && loc.getLoc() != null)
        {
        	DriverLastLocationBean db = new DriverLastLocationBean();
            db.setLastLocation(loc.getLoc());
            db.setVehicle(vehicleDAO.findByID(loc.getVehicleID()));
        	db.setDriver(driver);
        	db.setDriverName(driver.getPerson().getFirst()+" "+ driver.getPerson().getLast());
        	db.setTime(loc.getTime());
        	db.setAddress(addressLookup.getAddress(loc.getLoc().getLat(), loc.getLoc().getLng()));
        	
        	driverLastLocations.put(driver.getDriverID(),db);
        	driverLastLocationBeans.add(db);
        	
        	return db;
       }
        else
        {
        	//Driver does not have a last location
            logger.debug("last loc is null for driver: " + driver.getDriverID());                    
            
            return null;  //don't add to list.
            
            //db.setLastLocation(new LatLng(center.getLat()+Math.random()/10, center.getLng()+Math.random()/10));
            //db.setLastLocation(null);
        }
	}
	private int locateGroupDrivers(List<Driver> drivers, int groupId){

		int validDriverCount = 0;
		
		for (Driver driver:drivers){
        	
			DriverLastLocationBean db = locateOneDriver(driver);
			if (db != null){
				
	               validDriverCount++;
	               db.setIconKey(groupId);

			}
        }
		return validDriverCount;
	}
	
	private int locateTeamDrivers(List<Driver> drivers){
		
		int validDriverCount = 0;
		
		MapAndLegendIconManager malim = new MapAndLegendIconManager();
    	malim.initMapAndLegendIcons(drivers.size());
		
		for (Driver driver:drivers){
        	
			DriverLastLocationBean db = locateOneDriver(driver);
			if (db != null){
				
	               validDriverCount++;
	           	   malim.addMapAndLegendIcon(driver.getDriverID(),driver.getPerson().getFullName(),db.getLastLocation());
	               db.setIconKey(driver.getDriverID());
			}
        }
		return validDriverCount;
	}
	
	public DriverDAO getDriverDAO() {
		return driverDAO;
	}

	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}

	public NavigationBean getNavigation() {
		return navigation;
	}

	public void setNavigation(NavigationBean navigation) {
		this.navigation = navigation;
	}

	public LatLng getCenter() {
		return center;
	}

	public void setCenter(LatLng center) {
		this.center = center;
	}

	public void setDriverLastLocationBeans(List<DriverLastLocationBean> driverLastLocationBeans) {
		this.driverLastLocationBeans = driverLastLocationBeans;
	}

	public List<Group> getChildGroups() {
		return childGroups;
	}

	public void setChildGroups(List<Group> childGroups) {
		this.childGroups = childGroups;
	}

	public IconMap getMapIconMap() {
		return mapIconMap;
	}

	public void setMapIconMap(IconMap mapIconMap) {
		this.mapIconMap = mapIconMap;
	}

	public List<LegendIcon> getLegendIcons() {
		return legendIcons;
	}

	public void setLegendIcons(List<LegendIcon> legendIcons) {
		this.legendIcons = legendIcons;
	}
	
    public boolean isTeamLevel() {
		return teamLevel;
	}
	public void setTeamLevel(boolean teamLevel) {
		this.teamLevel = teamLevel;
	}
	public Integer getZoom()
    {
        return zoom;
    }

    public void setZoom(Integer zoom)
    {
        this.zoom = zoom;
    }

    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }
    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }
    public Integer getSelectedDriverID()
    {
        return selectedDriverID;
    }
    public void setSelectedDriverID(Integer selectedDriverID)
    {
        this.selectedDriverID = selectedDriverID;
    }
    public Integer getSelectedVehicleID()
    {
        return selectedVehicleID;
    }
    public void setSelectedVehicleID(Integer selectedVehicleID)
    {
        this.selectedVehicleID = selectedVehicleID;
    }
    public String driverDetailAction()
    {
        navigation.setDriver(driverLastLocations.get(new Integer(selectedDriverID)).getDriver());
        return "go_driver";
    }
    public String vehicleDetailAction()
    {
        navigation.setVehicle(vehicleDAO.findByID(selectedVehicleID));
        return "go_vehicle";
    }

	public void setDriverLastLocationBeanList(List<DriverLastLocationBean> driverLastLocationBeanList) {
		
		this.driverLastLocationBeans = driverLastLocationBeanList;
	}
	public class LegendIcon{
		
		private String caption;
		private String iconURL;
		private double lat;
		private double lng;
		
		public double getLat() {
			return lat;
		}

		public void setLat(double lat) {
			this.lat = lat;
		}

		public double getLng() {
			return lng;
		}

		public void setLng(double lng) {
			this.lng = lng;
		}


		public LegendIcon(String caption, String iconURL, double lat, double lng) {
			super();
			this.caption = caption;
			this.iconURL = iconURL;
			this.lat = lat;
			this.lng = lng;
		}

		public String getCaption() {
			return caption;
		}

		public void setCaption(String caption) {
			this.caption = caption;
		}

		public String getIconURL() {
			return iconURL;
		}

		public void setIconURL(String iconURL) {
			this.iconURL = iconURL;
		}
		
	}
	private class MapAndLegendIconManager {
		
		private MapIconFactory 		mif;
		
 		private List<MapIcon> 		mapIcons;
 		private Iterator<MapIcon> 	mapIconIt;
 		
 		private List<MapIcon>		legendMapIcons;
 		private Iterator<MapIcon> 	legendIt;
 		
		private void initMapAndLegendIcons(int iconCount){
			
	    	mif = new MapIconFactory();

	      	mapIcons = mif.getMapIcons(MapIconFactory.IconType.MARKER, iconCount);

	   		mapIconMap = new IconMap();
         	mapIconIt = mapIcons.iterator();
         	
	      	legendMapIcons = mif.getMapIcons(MapIconFactory.IconType.MAP_LEGEND, iconCount);
	      	legendIt = legendMapIcons.iterator();
	      	legendIcons = new ArrayList<LegendIcon>();
		}
		private void addMapAndLegendIcon(Integer key, String caption, LatLng latLng){
			
        	mapIconMap.addIcon(key, mapIconIt.next().getUrl());
        	if (latLng != null){
        		
        		legendIcons.add(new LegendIcon(caption, legendIt.next().getUrl(), latLng.getLat(),latLng.getLng()));
        	}
        	else {
        		
           		legendIcons.add(new LegendIcon(caption, legendIt.next().getUrl(),361,361));
        	}
		}
	}
	private class GroupComparator implements Comparator<Group>{
		
		public int compare(Group a, Group b){
			String aName = a.getName(); if (aName==null) aName = "";
			String bName = b.getName(); if (bName==null) bName = "";
	    	
	    	return a.getName().compareTo(b.getName());
		}
		public boolean equals(Group a, Group b){
			
			String aName = a.getName(); if (aName==null) aName = "";
			String bName = b.getName(); if (bName==null) bName = "";
	    	
	    	return a.getName().equals(b.getName());
			
		}
	}
	private class DriverComparator implements Comparator<Driver>{
		
		public int compare(Driver a, Driver b){
			String aLast = a.getPerson().getLast(); 	if (aLast==null)aLast = "";
			String aFirst = a.getPerson().getLast(); 	if (aFirst==null)aFirst = "";
			String aMiddle = a.getPerson().getMiddle();	if (aMiddle==null)aMiddle = "";
			
			String bLast = b.getPerson().getLast();		if (bLast==null)bLast = "";
			String bFirst = b.getPerson().getLast();	if (bFirst==null)bFirst = "";
			String bMiddle = b.getPerson().getMiddle();	if (bMiddle==null)bMiddle = "";
			
    		int compLast = aLast.compareTo(bLast);
    		int compFirst = aFirst.compareTo(bFirst);
    		
    		return ((compLast == 0)?((compFirst==0)?aMiddle.compareTo(bMiddle):compFirst):compLast);
		}
		
		public boolean equals(Driver a, Driver b){
			
			String aLast = a.getPerson().getLast(); 	if (aLast==null)aLast = "";
			String aFirst = a.getPerson().getLast(); 	if (aFirst==null)aFirst = "";
			String aMiddle = a.getPerson().getMiddle();	if (aMiddle==null)aMiddle = "";
			
			String bLast = b.getPerson().getLast();		if (bLast==null)bLast = "";
			String bFirst = b.getPerson().getLast();	if (bFirst==null)bFirst = "";
			String bMiddle = b.getPerson().getMiddle();	if (bMiddle==null)bMiddle = "";
			
			return (aLast.equals(bLast)?(aFirst.equals(bFirst)?(aMiddle.equals(bMiddle)?true:false):false):false);
			
		}
	}
}
