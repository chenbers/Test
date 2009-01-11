package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.map.MapIcon;
import com.inthinc.pro.map.MapIconFactory;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.util.WebUtil;

public class DriverLocationBean extends BaseBean {

	private DriverDAO driverDAO;
	private GroupDAO groupDAO;
    private NavigationBean navigation;
    private boolean pageChange = false;
    private LatLng center;
    private Integer zoom = 10;
	private TreeMap<Integer,DriverLastLocationBean> driverLastLocations;
	private List<Group> childGroups;
	private IconMap mapIconMap;
	private IconMap legendIconMap;
	private Map<Integer,Group> groupMap;
	private boolean showLegend;
	private List<DriverLastLocationBean> driverLastLocationBeanList;
    private static final String WEBAPP_CONTEXT = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
	private static final Logger logger = Logger.getLogger(DriverLocationBean.class);
	private GroupHierarchy       organizationHierarchy;

	public DriverLocationBean() {
		super();
	
		center = new LatLng(37.4419, -122.1419);
		legendIconMap = new IconMap();
		mapIconMap = new IconMap();
		groupMap = new HashMap<Integer,Group>();
		showLegend = true;
	}

	public List<DriverLastLocationBean> getDriverLastLocationBeans() {

        organizationHierarchy = new GroupHierarchy(groupDAO.getGroupsByAcctID(getAccountID()));
        center = organizationHierarchy.getTopGroup().getMapCenter();
        zoom = organizationHierarchy.getTopGroup().getMapZoom();
	        
    	MapIconFactory mif = new MapIconFactory();
    	
    	driverLastLocations = new TreeMap<Integer,DriverLastLocationBean>();
        driverLastLocationBeanList = new ArrayList<DriverLastLocationBean>();
        childGroups = getGroupHierarchy().getChildren(getGroupHierarchy().getGroup(this.navigation.getGroupID()));
        if (childGroups != null){
        	showLegend = true;
        	
          	List<MapIcon> mapIcons = mif.getMapIcons(MapIconFactory.IconType.MARKER, childGroups.size());
         	List<MapIcon> legendIcons = mif.getMapIcons(MapIconFactory.IconType.MAP_LEGEND, childGroups.size());
         	Iterator<MapIcon> mapIconIt = mapIcons.iterator();
         	Iterator<MapIcon> legendIconIt = legendIcons.iterator();
         	          	
	        for (Group group:childGroups){
	        		         	
	        	groupMap.put(group.getGroupID(), group);
	        	mapIconMap.addIcon(group.getGroupID(), mapIconIt.next().getUrl());
	        	legendIconMap.addIcon(group.getGroupID(), legendIconIt.next().getUrl());
		        List<Driver> drivers = driverDAO.getAllDrivers(group.getGroupID());
		        // Do something to get driverLastLocations or last trips to get location
		        locateDrivers(drivers,group.getGroupID());
	        }
        }
        else {
        	showLegend = false;
	        List<Driver> drivers = driverDAO.getAllDrivers(this.navigation.getGroupID());
	        
         	List<MapIcon> mapIcons = mif.getMapIcons(MapIconFactory.IconType.MARKER, 1);
         	List<MapIcon> legendIcons = mif.getMapIcons(MapIconFactory.IconType.MAP_LEGEND, 1);
	        // Do something to get driverLastLocations or last trips to get location
        	mapIconMap.addIcon(navigation.getGroupID(), mapIcons.get(0).getUrl());
        	legendIconMap.addIcon(navigation.getGroupID(), legendIcons.get(0).getUrl());
	        locateDrivers(drivers,navigation.getGroupID());
      	
        }
		return driverLastLocationBeanList;
	}
	private void locateDrivers(List<Driver> drivers, int groupId){

		int validDriverCount = 0;
		
		for (Driver driver:drivers){
        	
        	DriverLastLocationBean db = new DriverLastLocationBean();
            LastLocation loc = null;
            if (driver.getDriverID() != null)
                loc = driverDAO.getLastLocation(driver.getDriverID());
            
          
            if (loc != null && loc.getLoc() != null)
            {
                db.setLastLocation(loc.getLoc());
                
                validDriverCount++;
            }
            else
            {
                logger.debug("last loc is null for driver: " + driver.getDriverID());                    
                // TODO: What do we do in case where there is no last location?
                
                continue;  //dont add to list.
                
                //db.setLastLocation(new LatLng(center.getLat()+Math.random()/10, center.getLng()+Math.random()/10));
                //db.setLastLocation(null);
            }
            db.setGroupID(groupId);
        	db.setDriver(driver);
        	db.setDriverName(driver.getPerson().getFirst()+" "+driver.getPerson().getLast());
        	driverLastLocations.put(driver.getDriverID(),db);
        	driverLastLocationBeanList.add(db);
        }
		//calculate better map center - set to average of all positions if there are any
		if (validDriverCount == 0){
			
			//Set to center for the group
			setCenter(this.getGroupHierarchy().getTopGroup().getMapCenter());
		}
		else {

			setCenter(driverLastLocationBeanList.get(0).getLastLocation());
		}
		
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
		this.driverLastLocationBeanList = driverLastLocationBeans;
	}

	public List<Group> getChildGroups() {
		return childGroups;
	}

	public void setChildGroups(List<Group> childGroups) {
		this.childGroups = childGroups;
	}

	public IconMap getLegend() {
		return legendIconMap;
	}

	public void setLegend(IconMap iconMap) {
		this.legendIconMap = iconMap;
	}

	public IconMap getMapIconMap() {
		return mapIconMap;
	}

	public void setMapIconMap(IconMap mapIconMap) {
		this.mapIconMap = mapIconMap;
	}

	public IconMap getLegendIconMap() {
		return legendIconMap;
	}

	public void setLegendIconMap(IconMap legendIconMap) {
		this.legendIconMap = legendIconMap;
	}

	public boolean isShowLegend() {
		return showLegend;
	}

	public void setShowLegend(boolean showLegend) {
		this.showLegend = showLegend;
	}
	public boolean showLegend(){
		
		return showLegend;
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

    public String driverAction(){
    	
    	Map<String,String> requestMap = new WebUtil().getRequestParameterMap();
    	String driverID = requestMap.get("id");
//    	Driver d = driverLastLocations.get(new Integer(driverID)).getDriver();
//    	logger.debug("driverAction driver is: "+d.getDriverID()+" "+d.getPerson().getFirst());
    	navigation.setDriver(driverLastLocations.get(new Integer(driverID)).getDriver());
    	
    	return "go_driver";
    }

	public void setDriverLastLocationBeanList(
			List<DriverLastLocationBean> driverLastLocationBeanList) {
		this.driverLastLocationBeanList = driverLastLocationBeanList;
	}
	
}
