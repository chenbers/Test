package com.inthinc.pro.backing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import com.inthinc.pro.backing.ui.TripDisplay;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.NoAddressFoundException;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.TripStatus;
import com.inthinc.pro.util.MessageUtil;

public class TeamTrip implements Comparable<TeamTrip>{

    Number distance;        // 3.2mi
    String startAddress;    // 123 Street
    String endAddress;      // 188 S 11th St
    String duration;        // 1:32
    List<LatLng> route;
    LatLng routeLastStep;
    LatLng beginningPoint;
    Long durationMiliSeconds;
    Trip trip;
    boolean inProgress = false;
 //   private static final int MINUTES_BUFFER = 5;    
	private static DateFormat dateFormatter;
    
    public TeamTrip(Trip trip, AddressLookup addressLookup)
    {
        this.trip = trip;
        
        route = trip.getRoute();
        
        
        durationMiliSeconds = trip.getEndTime().getTime() - trip.getStartTime().getTime();
        setDuration(DateUtil.getDurationFromMilliseconds(durationMiliSeconds));
        
        setDistance(trip.getMileage() / 100D);
        
        if(route.size() > 0)
        {
            routeLastStep = route.get(route.size()-1);
            routeLastStep.setLat(routeLastStep.getLat() + 0.00001);
            
            beginningPoint = route.get(0);
            beginningPoint.setLat(beginningPoint.getLat() - 0.00001);
            try{
            	setStartAddress(addressLookup.getAddress(route.get(0).getLat(), route.get(0).getLng()));
            }
            catch (NoAddressFoundException nafe){
            	
            	setStartAddress(MessageUtil.getMessageString(nafe.getMessage()));
            }
            try {
            	setEndAddress(addressLookup.getAddress(route.get(route.size()-1).getLat(), route.get(route.size()-1).getLng()));
            }
            catch (NoAddressFoundException nafe){
            	
            	setEndAddress(MessageUtil.getMessageString(nafe.getMessage()));
            }
        }
    }
    public Number getDistance()
    {
        return distance;
    }
    public void setDistance(Number distance)
    {
        this.distance = distance;
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
    
    public Long getDurationMiliSeconds()
    {
        return durationMiliSeconds;
    }

    public void setDurationMiliSeconds(Long durationMiliSeconds)
    {
        this.durationMiliSeconds = durationMiliSeconds;
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
	public void setEndPointLat(double endPointLat){
		
	}
	public void setEndPointLng(double endPointLng){
		
	}
	public double getEndPointLat() {
		return route.get(route.size()-1).getLat();
	}
	public double getEndPointLng() {
		return route.get(route.size()-1).getLng();
	}
    @Override
    public int compareTo(TeamTrip o)
    {
        return trip.getStartTime().compareTo(o.getTrip().getStartTime());
    }
}
