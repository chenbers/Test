package com.inthinc.pro.model.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.RoleDAO;
import com.inthinc.pro.model.security.AccessPoint;
import com.inthinc.pro.model.security.SiteAccessPoint;

public class SiteAccessPoints implements BaseAppEntity{

	   Logger logger = Logger.getLogger(SiteAccessPoints.class);
	    private static List<SiteAccessPoint> accessPointList;
	    private static Map<Integer, SiteAccessPoint> accessPointMap;
	    
	    private RoleDAO roleDAO;

	    public SiteAccessPoints()
	    {
	    }
	    
	    
	    public void init()
	    {
	    	accessPointList = roleDAO.getSiteAccessPts();

	    	accessPointList = Collections.unmodifiableList(accessPointList);
	        Map<Integer, SiteAccessPoint> map = new LinkedHashMap<Integer, SiteAccessPoint>();

	        for (SiteAccessPoint accessPoint : accessPointList)
	        {
	               map.put(accessPoint.getAccessPtID(), accessPoint);
	        }
	        accessPointMap = Collections.unmodifiableMap(map);
	    }
	    

	    
	    public static SiteAccessPoint getAccessPointById(Integer id)
	    {
	        return accessPointMap.get(id);
	    }
	    public  SiteAccessPoint getAccessPointByName(String  name)
	    {
	        for (SiteAccessPoint accessPoint : getAccessPointMap().values())
	        {
	            if (accessPoint.getMsgKey().equals(name))
	                return accessPoint;
	        }
	        return null;
	    }

	    

	    public RoleDAO getRoleDAO()
	    {
	        return roleDAO;
	    }

	    public void setRoleDAO(RoleDAO roleDAO)
	    {
	        this.roleDAO = roleDAO;
	    }


		public static Map<Integer, SiteAccessPoint> getAccessPointMap() {
			return accessPointMap;
		}


		public static void setAccessPointMap(Map<Integer, SiteAccessPoint> accessPointMap) {
			SiteAccessPoints.accessPointMap = accessPointMap;
		}
	    
		public static List<String> getAllAccessPointStrings(){
			
			List<String> allStrings = new ArrayList<String>();
			for (SiteAccessPoint sap:accessPointList){
				
				allStrings.add(sap.toString());
			}
			return allStrings;
		}
		
		public static List<AccessPoint> getAccessPoints(){
			
			List<AccessPoint> accessPoints = new ArrayList<AccessPoint>();
			
			for (SiteAccessPoint sap:accessPointList){
				
				accessPoints.add(new AccessPoint(sap.getAccessPtID(), 0));
			}
			return accessPoints;
		}
		public static List<SiteAccessPoint> getSiteAccessPoints(){
			
			return accessPointList;
		}
		
		public String getAccessPointMessageKey(Integer accessPtID){
			
			if(getAccessPointMap().get(accessPtID) == null) return null;
			
			return getAccessPointMap().get(accessPtID).getMsgKey();
		}
}
