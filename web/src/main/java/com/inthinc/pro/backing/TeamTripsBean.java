package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.inthinc.pro.backing.ui.TripDisplay;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.map.MapIcon;
import com.inthinc.pro.map.MapIconFactory;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.util.MiscUtil;

public class TeamTripsBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Driver> drivers;
	private List<TripDisplay> selectedDriverTrips;
	private Driver selectedDriver;
    private DriverDAO driverDAO;
    private List<MapIcon> icons;
    private TeamCommonBean teamCommonBean;
	
    public void init(){
    	
		icons = MapIconFactory.IconType.TEAM_LEGEND.getIconList(15);
		drivers = driverDAO.getDrivers(teamCommonBean.getGroupID());
    }
	public List<Driver> getDrivers() {
		
		return drivers;
	}
	public void setDrivers(List<Driver> drivers) {
		this.drivers = drivers;
	}
	public void loadTrips(){
		
		Date endDate = new Date();
		Date startDate = new Date();
        Calendar gc = new GregorianCalendar();
        gc.setTime(startDate);
        gc.add(Calendar.HOUR, -(24*30));
        startDate = gc.getTime();

		List<Trip> trips = driverDAO.getTrips(selectedDriver.getDriverID(), startDate, endDate);
		selectedDriverTrips = new ArrayList<TripDisplay>();
        for (Trip trip : trips) {
            TripDisplay td = new TripDisplay(trip, selectedDriver.getPerson().getTimeZone(), addressLookup);
            // If starting or ending address is null, try to set a zone name
            if ( td.getStartAddress() == null ) {
                LatLng latLng = new LatLng(td.getRoute().get(0).getLat(),td.getRoute().get(0).getLng());
                td.setStartAddress(MiscUtil.findZoneName(this.getProUser().getZones(), latLng));
            }                
            if ( td.getEndAddress() == null ) {
                LatLng latLng = new LatLng(td.getEndPointLat(),td.getEndPointLng());
                td.setEndAddress(MiscUtil.findZoneName(this.getProUser().getZones(), latLng));
            }
            selectedDriverTrips.add(td);
            

        }
        Collections.sort(selectedDriverTrips);
        Collections.reverse(selectedDriverTrips);
	}
	public List<TripDisplay> getSelectedDriverTrips() {

		return selectedDriverTrips;
	}
	public void setSelectedDriverTrips(List<TripDisplay> selectedDriverTrips) {
		this.selectedDriverTrips = selectedDriverTrips;
	}
	public DriverDAO getDriverDAO() {
		return driverDAO;
	}
	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}
	public List<MapIcon> getIcons() {
		return icons;
	}
	public void setIcons(List<MapIcon> icons) {
		this.icons = icons;
	}
	public Integer getGroupID() {
		return teamCommonBean.getGroupID();
	}
	public TeamCommonBean getTeamCommonBean() {
		return teamCommonBean;
	}
	public void setTeamCommonBean(TeamCommonBean teamCommonBean) {
		this.teamCommonBean = teamCommonBean;
	}
	public Driver getSelectedDriver() {
		return selectedDriver;
	}
	public void setSelectedDriver(Driver selectedDriver) {
		this.selectedDriver = selectedDriver;
	}
}
