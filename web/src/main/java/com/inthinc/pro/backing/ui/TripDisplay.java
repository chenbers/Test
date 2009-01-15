package com.inthinc.pro.backing.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.dao.util.DateUtil;

public class TripDisplay
{
    String dateShort;       // Jul 01
    String timeShort;       // 1:32 pm
    String distance;        // 3.2mi
    String startAddress;    // 123 Street
    String endAddress;      // 188 S 11th St
    String duration;        // 1:32
    List<LatLng> route;
    LatLng routeLastStep;
    Long durationMiliSeconds;
    Trip trip;
    TimeZone timeZone;
    
    private static DateFormat dateFormatter;
    
    public TripDisplay(Trip trip, TimeZone timeZone)
    {
        this.trip = trip;
        this.timeZone = timeZone;
        
        route = trip.getRoute();
        
        dateFormatter = new SimpleDateFormat("dd MMM");
        dateFormatter.setTimeZone(timeZone);
        setDateShort(dateFormatter.format(trip.getEndTime()));
        
        dateFormatter = new SimpleDateFormat("h:mm a");
        dateFormatter.setTimeZone(timeZone);
        setTimeShort(dateFormatter.format(trip.getEndTime() ));
        
        durationMiliSeconds = trip.getEndTime().getTime() - trip.getStartTime().getTime();
        setDuration(getDurationFromSeconds(durationMiliSeconds / 1000));
        
        Double mileageDouble = (double)trip.getMileage() / 100;
        setDistance(mileageDouble.toString() + "mi");
        
        AddressLookup lookup = new AddressLookup();

        if(route.size() > 0)
        {
            routeLastStep = route.get(route.size()-1);
            
            setStartAddress(lookup.getAddress(route.get(0).getLat(), route.get(0).getLng()));
            setEndAddress(lookup.getAddress(route.get(route.size()-1).getLat(), route.get(route.size()-1).getLng()));
        }
    }

    public String getDateShort()
    {
        return dateShort;
    }

    public void setDateShort(String dateShort)
    {
        this.dateShort = dateShort;
    }

    public String getTimeShort()
    {
        return timeShort;
    }

    public void setTimeShort(String timeShort)
    {
        this.timeShort = timeShort;
    }

    public String getDistance()
    {
        return distance;
    }

    public void setDistance(String distance)
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
    
    public static String getDurationFromSeconds(Long secsIn)
    {
        Long hours = secsIn / 3600,
        remainder = secsIn % 3600,
        minutes = remainder / 60,
        seconds = remainder % 60;

        return ( (hours < 10 ? "0" : "") + hours
        + ":" + (minutes < 10 ? "0" : "") + minutes
        + ":" + (seconds< 10 ? "0" : "") + seconds );
    }
    
}
