package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.map.MapIcon;
import com.inthinc.pro.map.MapIconFactory;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.util.CircularIterator;
import com.inthinc.pro.util.MiscUtil;

@KeepAlive
public class DriverLocationBean extends BaseBean {    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(DriverLocationBean.class);    

	private DriverDAO driverDAO;
	private VehicleDAO vehicleDAO;
	private GroupDAO groupDAO;
	
    private NavigationBean navigation;
    private TeamCommonBean teamCommonBean;
    
    private LatLng center;
    private Integer zoom = 10;
	private List<Group> childGroups;
	private IconMap mapIconMap;
	private List<LegendIcon> legendIcons;
	
	private Map<Integer,Group> groupMap;
	private boolean teamLevel;
	private GroupHierarchy organizationHierarchy;
	private Integer groupID;
	
	private Integer selectedDriverID;
	private Integer selectedVehicleID;

//	private List<DriverLocation> driverLocations;
	private Map<Integer,DriverLocation> driverLocationsMap;
	
	public DriverLocationBean() {
		super();
	
	}
	public void init(){
		
		groupMap = new HashMap<Integer,Group>();
		teamLevel = false;
        organizationHierarchy = new GroupHierarchy(groupDAO.getGroupsByAcctID(getAccountID()));
        center = organizationHierarchy.getTopGroup().getMapCenter();
        zoom = organizationHierarchy.getTopGroup().getMapZoom();
        
        groupID = null;
		
	}
	
	public List<DriverLocation> getDriverLocations()
	{
//		logger.info("-----getDriverLocations groupid: " + navigation.getGroupID());	
		// cj 2/13/10 -- this was commented out, but put it back in because this method gets called several times and getDVLByGroupId is expensive
	    if (driverLocationsMap != null) {
	    	return new ArrayList<DriverLocation>(driverLocationsMap.values());
	    }
	    
//Date traceStartTime = new Date();	    
	    List<DriverLocation>driverLocations = driverDAO.getDriverLocations(getGroupID());
//        List<DriverLocation>driverLocations = driverDAO.getDriverLocations(navigation.getGroupID());	    

	    allocateIcons(driverLocations);
	    
	    driverLocations = populateAddressAndIcon(driverLocations);
	    
	    if (!teamLevel) {
	        Collections.sort(driverLocations, new DriverLocationGroupComparator());
	    }
		//calculate better map center - set to location of first driver
		if (driverLocations.size() == 0){
			
			//Set to center for the group
			setCenter(this.getGroupHierarchy().getTopGroup().getMapCenter());
		}
		else {

			setCenter(driverLocations.get(0).getLoc());
		}
		driverLocationsMap = new LinkedHashMap<Integer, DriverLocation>();
		for(DriverLocation dl:driverLocations){
			
			driverLocationsMap.put(dl.getDriver().getDriverID(),dl);
		}
		
//logger.info("TOTAL TIME: " + (new Date().getTime() - traceStartTime.getTime()) + " ms");
		selectedDriverID = driverLocations.size()>0?driverLocations.get(0).getDriver().getDriverID():null;

		return new ArrayList<DriverLocation>(driverLocationsMap.values());
	}
	
	public void resetMap() {
	    driverLocationsMap = null;
	}

	public void setDriverLocations(List<DriverLocation> driverLocations) {
		driverLocationsMap = new LinkedHashMap<Integer,DriverLocation>();
		for(DriverLocation dl : driverLocations){
			
			driverLocationsMap.put(dl.getDriver().getDriverID(),dl);
		}
        selectedDriverID = driverLocations.size()>0?driverLocations.get(0).getDriver().getDriverID():null;

	}
	
    public Integer getGroupID() {        
        // The teamCommonBean may have set this value, if not, fall back to 
        //  evil navigation bean, which is needed for current team page
        if ( groupID == null ) {
            setGroupID(navigation.getGroupID() == null ? getUser().getGroupID() : navigation.getGroupID());
        }
        return groupID;
    }	
    

    public void setGroupID(Integer groupID) {
        if (this.groupID != null && !this.groupID.equals(groupID)) {
            logger.info("TeamOverviewBean groupID changed " + groupID);
        }
        this.groupID = groupID;
    }    
	
	private List<DriverLocation> populateAddressAndIcon(List<DriverLocation> driverLocations) {
		
		List<DriverLocation> validList = new ArrayList<DriverLocation>();
		for (DriverLocation driverLocation : driverLocations) {
			
			Driver driver = driverLocation.getDriver();
			if (driver == null || driver.getPerson() == null || driverLocation.getLoc() == null) 
				continue;
			
        	driverLocation.setAddressStr(getAddress(driverLocation.getLoc()));
        	// TODO Refactor. Will look for a zone name if no address found
        	if ( driverLocation.getAddressStr() == null ) {
        	    driverLocation.setAddressStr(
        	            MiscUtil.findZoneName(this.getProUser().getZones(), 
        	                    new LatLng(driverLocation.getLoc().getLat(),driverLocation.getLoc().getLng())));
        	}
        	
        	driverLocation.setPosition(getIconKey(driverLocation));
        	validList.add(driverLocation);
		}
		
		return validList;
	}
	
	private void allocateIcons(List<DriverLocation> driverLocations){
		
//	    childGroups = getGroupHierarchy().getChildren(getGroupHierarchy().getGroup(this.navigation.getGroupID()));
        childGroups = getGroupHierarchy().getChildren(getGroupHierarchy().getGroup(getGroupID()));	    
	    
	    teamLevel = (childGroups == null);
		MapAndLegendIconManager malim = new MapAndLegendIconManager();

  		if (!teamLevel){
        	
    		malim.initMapAndLegendIcons(childGroups.size());
         	
         	//Sort the groups by name so the icons appear in the same order as the trend chart
         	Collections.sort(childGroups, new GroupComparator());

	        for (Group group:childGroups){
	        		         	
	        	groupMap.put(group.getGroupID(), group);
	        	
	        	malim.addMapAndLegendIcon(group.getGroupID(),group.getName(),null);
	        }
        }
        else {
	        Collections.sort(driverLocations, new DriverComparator());
	        
        	malim.initMapAndLegendIcons(driverLocations.size());
    		for (DriverLocation driverLocation : driverLocations){
            	
    			if (driverLocation != null && driverLocation.getDriver() != null && driverLocation.getDriver().getPerson() != null) {
   	           	   malim.addMapAndLegendIcon(driverLocation.getDriver().getDriverID(), driverLocation.getDriver().getPerson().getFullName(), 
   	           			   driverLocation.getLoc());
    			}
            }

        }

	}
	
	private int getIconKey(DriverLocation driverLocation)
	{
		Driver driver = driverLocation.getDriver();
		if (teamLevel)
			return driver.getDriverID();
		
		int groupID = driver.getGroupID();
		
		GroupHierarchy hierarchy = getOrganizationHierarchy();
		
		while (mapIconMap.getIcons().get(groupID) == null) {
			
			Group parentGroup  = hierarchy.getParentGroup(hierarchy.getGroup(groupID));
			if (parentGroup == null)
				break;
			
			groupID = parentGroup.getGroupID();
			
		}
		driverLocation.setGroup(hierarchy.getGroup(groupID));
		return groupID;	
		
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

	public TeamCommonBean getTeamCommonBean() {
        return teamCommonBean;
    }
	
    public void setTeamCommonBean(TeamCommonBean teamCommonBean) {
        this.teamCommonBean = teamCommonBean;
        this.groupID = teamCommonBean.getGroupID();
    }
    
    public LatLng getCenter() {
		return center;
	}

	public void setCenter(LatLng center) {
		this.center = center;
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
	public GroupHierarchy getOrganizationHierarchy() {
		return organizationHierarchy;
	}
	public void setOrganizationHierarchy(GroupHierarchy organizationHierarchy) {
		this.organizationHierarchy = organizationHierarchy;
	}
    public String driverDetailAction()
    {
        //navigation.setDriver(driverLastLocations.get(new Integer(selectedDriverID)).getDriver());
        navigation.setDriver(driverDAO.findByID(selectedDriverID));
        return "go_driver";
    }
    public String vehicleDetailAction()
    {
        navigation.setVehicle(vehicleDAO.findByID(selectedVehicleID));
        return "go_vehicle";
    }
    
	public class LegendIcon{
		
		private String caption;
		private String iconURL;
		private double lat;
		private double lng;
		private Integer entityID;
		
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


		public LegendIcon(String caption, String iconURL, double lat, double lng, Integer entityID) {
			super();
			this.caption = caption;
			this.iconURL = iconURL;
			this.lat = lat;
			this.lng = lng;
			this.entityID = entityID;
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

        public Integer getEntityID()
        {
            return entityID;
        }

        public void setEntityID(Integer entityID)
        {
            this.entityID = entityID;
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
            mapIconIt = new CircularIterator<MapIcon>(mapIcons);
         	
	      	legendMapIcons = mif.getMapIcons(MapIconFactory.IconType.MAP_LEGEND, iconCount);
	      	
            legendIt = new CircularIterator<MapIcon>(legendMapIcons);
	      	legendIcons = new ArrayList<LegendIcon>();
		}
		private void addMapAndLegendIcon(Integer key, String caption, LatLng latLng){
			
        	mapIconMap.addIcon(key, mapIconIt.next().getUrl());
        	if (latLng != null){
        		
        		legendIcons.add(new LegendIcon(caption, legendIt.next().getUrl(), latLng.getLat(),latLng.getLng(), key));
        	}
        	else {
        		
           		legendIcons.add(new LegendIcon(caption, legendIt.next().getUrl(),361,361,key));
        	}
		}
	}
	private class DriverLocationGroupComparator implements Comparator<DriverLocation>{
		
		public int compare(DriverLocation a, DriverLocation b){
		
			if (a == null || a.getGroup() == null)
				return -1;
			if (b == null || b.getGroup() == null)
				return 1;
	    	return a.getGroup().getName().compareTo(b.getGroup().getName());
		}
		public boolean equals(DriverLocation a, DriverLocation b){
			
			if ((a == null || a.getGroup() == null) &&
					(b == null || b.getGroup() == null))
				return true;
			if (a == null || a.getGroup() == null)
				return false;
			if (b == null || b.getGroup() == null)
				return false;
	    	return a.getGroup().getName().equals(b.getGroup().getName());
			
		}
	}
	private class GroupComparator implements Comparator<Group>{
		
		public int compare(Group a, Group b){
	    	return a.getName().compareTo(b.getName());
		}
		public boolean equals(Group a, Group b){
			
	    	return a.getName().equals(b.getName());
			
		}
	}
	private class DriverComparator implements Comparator<DriverLocation>{
		
		public int compare(DriverLocation locA, DriverLocation locB){
			
			Driver a = locA.getDriver();
			Driver b = locB.getDriver();
    		int compLast = getLastName(a).compareTo(getLastName(b));
    		int compFirst = getFirstName(a).compareTo(getFirstName(b));
    		int compMiddle = getMiddleName(a).compareTo(getMiddleName(b));
    		
    		return ((compLast == 0)?((compFirst==0)? compMiddle:compFirst):compLast);
		}
		
		public boolean equals(DriverLocation locA, DriverLocation locB){
			
			Driver a = locA.getDriver();
			Driver b = locB.getDriver();
			
			return (getLastName(a).equals(getLastName(b)) &&
					getFirstName(a).equals(getFirstName(b)) &&
					getMiddleName(a).equals(getMiddleName(b)));
			
		}
		private String getLastName(Driver driver) {
			String last = driver.getPerson().getLast();
			return (last==null) ? "" : last;
		}
		private String getFirstName(Driver driver) {
			String first = driver.getPerson().getFirst();
			return (first==null) ? "" : first;
		}
		private String getMiddleName(Driver driver) {
			String middle = driver.getPerson().getMiddle();
			return (middle==null) ? "" : middle;
		}
		
	}
	public Map<Integer, DriverLocation> getDriverLocationsMap() {
		return driverLocationsMap;
	}
	public void setDriverLocationsMap(
			Map<Integer, DriverLocation> driverLocationsMap) {
		this.driverLocationsMap = driverLocationsMap;
	}
}
