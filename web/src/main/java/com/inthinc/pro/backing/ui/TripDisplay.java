package com.inthinc.pro.backing.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.TripStatus;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.util.MessageUtil;

public class TripDisplay implements Comparable<TripDisplay>
{
    String timeStartShort;  // 1:32 PM
    String timeEndShort;    // 1:55 PM
    Number distance;        // 3.2mi
    String startAddress;    // 123 Street
    String endAddress;      // 188 S 11th St
    String duration;        // 1:32
    List<LatLng> route;
    LatLng routeLastStep;
    LatLng beginningPoint;
    Long durationMilliSeconds;
    Trip trip;
    TimeZone timeZone;
    boolean inProgress = false;
 //   private static final int MINUTES_BUFFER = 5;    
	private static DateFormat dateFormatter;
    private Locale locale;
    public TripDisplay(Trip trip, TimeZone timeZone, AddressLookup addressLookup,List<Zone> zones, Locale locale)
    {
        this.trip = trip;
        this.timeZone = timeZone;
        this.locale = locale;
        
        route = trip.getRoute();
        
        dateFormatter = new SimpleDateFormat(MessageUtil.getMessageString("timeFormat"), locale);
        dateFormatter.setTimeZone(timeZone);
        setTimeStartShort(dateFormatter.format(trip.getStartTime() ));
        setTimeEndShort(dateFormatter.format(trip.getEndTime() ));
        
        durationMilliSeconds = trip.getEndTime().getTime() - trip.getStartTime().getTime();
        setDuration(DateUtil.getDurationFromMilliseconds(durationMilliSeconds));
        
        setDistance(trip.getMileage() / 100D);
        
        if(isGoodRoute())
        {
            routeLastStep = route.get(route.size()-1);
            routeLastStep.setLat(routeLastStep.getLat() + 0.00001);
            
            beginningPoint = route.get(0);
            beginningPoint.setLat(beginningPoint.getLat() - 0.00001);
        	setStartAddress(addressLookup.getAddressOrZoneOrLatLng(route.get(0),zones));
        	setEndAddress(addressLookup.getAddressOrZoneOrLatLng(route.get(route.size()-1),zones));
        }
        else{
        	setEndAddress(MessageUtil.getMessageString("no_route_data_for_trip"));
        	routeLastStep = null;
        	beginningPoint = null;
        }
    }
    public String getStartDateString()
    {
        dateFormatter = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"), locale);
        dateFormatter.setTimeZone(timeZone);
        return dateFormatter.format(trip.getStartTime());
    }
    public String getEndDateString()
    {
        dateFormatter = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"), locale);
        dateFormatter.setTimeZone(timeZone);
        return dateFormatter.format(trip.getEndTime());
    }
    public String getDateString(){
        dateFormatter = new SimpleDateFormat(MessageUtil.getMessageString("dateFormat"), locale);
        dateFormatter.setTimeZone(timeZone);
        return dateFormatter.format(trip.getEndTime());    	
    }
    public Date getEndTime()
    {
        return trip.getEndTime();
    }

    public String getTimeStartShort()
    {
        return timeStartShort;
    }

    public void setTimeStartShort(String timeStartShort)
    {
        this.timeStartShort = timeStartShort;
    }

    public String getTimeEndShort()
    {
        return timeEndShort;
    }

    public void setTimeEndShort(String timeEndShort)
    {
        this.timeEndShort = timeEndShort;
    }

    public Number getDistance()
    {
        return distance;
    }
    public void setDistance(Number distance)
    {
        this.distance = distance;
    }
    public String getStartAddress(AddressLookup addressLookup,List<Zone> zones)
    {
        return addressLookup.getAddressOrZoneOrLatLng(beginningPoint, zones);
    }
    
    public String getStartAddress()
    {
        return startAddress;
    }

    public void setStartAddress(String startAddress)
    {
        this.startAddress = startAddress;
    }
    public String getEndAddress()
    {
        return endAddress;
    }

    public String getEndAddress(AddressLookup addressLookup,List<Zone> zones)
    {
        return addressLookup.getAddressOrZoneOrLatLng(route.get(route.size()-1),zones);
    }

    public void setEndAddress(String endAddress)
    {
        this.endAddress = endAddress;
    }

    public String getDuration()
    {
        return duration;
    }

    public void setDuration(String duration)
    {
        this.duration = duration;
    }

    public Trip getTrip()
    {
        return trip;
    }

    public void setTrip(Trip trip)
    {
        this.trip = trip;
    }
    
    public boolean areLocationsLoaded() {
        return trip.getFullRouteLoaded();
    }
    
	public void addLocations(List<LatLng> locations) {
        if (!trip.getFullRouteLoaded()) {
            route.addAll((route.size() >= 2) ? 1 : 0, locations);
            trip.setFullRouteLoaded(true);
        }
    }

    public List<LatLng> getRoute()
    {
        return route;
    }

    public void setRoute(List<LatLng> route)
    {
        this.route = route;
    }

    public LatLng getRouteLastStep()
    {
        return routeLastStep;
    }

    public void setRouteLastStep(LatLng routeLastStep)
    {
        this.routeLastStep = routeLastStep;
    }
    
    public Long getDurationMilliSeconds()
    {
        return durationMilliSeconds;
    }

    public void setDurationMilliSeconds(Long durationMilliSeconds)
    {
        this.durationMilliSeconds = durationMilliSeconds;
    }

    public String getTimeZoneID()
    {
        return timeZone.getID();
    }
    public TimeZone getTimeZone()
    {
        return timeZone;
    }


    public void setTimeZone(TimeZone timeZone)
    {
        this.timeZone = timeZone;
    }
    @Override
    public int compareTo(TripDisplay o)
    {
        return trip.getStartTime().compareTo(o.getTrip().getStartTime());
    }
    
    public LatLng getBeginningPoint()
    {
        return beginningPoint;
    }
    
    public void setBeginningPoint(LatLng beginningPoint)
    {
        this.beginningPoint = beginningPoint;
    }
    
    public boolean isInProgress() {
    	if (trip == null || trip.getStatus() == null)
    		return false;
    	
    	return trip.getStatus().equals(TripStatus.TRIP_IN_PROGRESS);
	}
	public void setInProgress(boolean inProgress) {
		this.inProgress = inProgress;
	}
	public void setEndPointLat(Double endPointLat){
		
	}
	public void setEndPointLng(Double endPointLng){
		
	}
	public Double getEndPointLat() {
		if(!isGoodRoute()) return null;
		return route.get(route.size()-1).getLat();
	}
	public Double getEndPointLng() {
		if(!isGoodRoute()) return null;
		return route.get(route.size()-1).getLng();
	}
	public boolean isGoodRoute(){
		return route !=null && route.size()>0;
	}
}
