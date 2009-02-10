package com.inthinc.pro.backing.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.util.MessageUtil;

public class TripDisplay
{
    String dateShort;       // Jul 01
    String timeStartShort;  // 1:32 PM
    String timeEndShort;    // 1:55 PM
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
        
        dateFormatter = new SimpleDateFormat(MessageUtil.getMessageString("dateFormat"));
        dateFormatter.setTimeZone(timeZone);
        setDateShort(dateFormatter.format(trip.getEndTime()));
        
        dateFormatter = new SimpleDateFormat(MessageUtil.getMessageString("timeFormat"));
        dateFormatter.setTimeZone(timeZone);
        setTimeStartShort(dateFormatter.format(trip.getStartTime() ));
        setTimeEndShort(dateFormatter.format(trip.getEndTime() ));
        
        durationMiliSeconds = trip.getEndTime().getTime() - trip.getStartTime().getTime();
        setDuration(DateUtil.getDurationFromMilliseconds(durationMiliSeconds));
        
        Double mileageDouble = (double)trip.getMileage() / 100;
        setDistance(mileageDouble.toString());
        
        AddressLookup lookup = new AddressLookup();

        if(route.size() > 0)
        {
            routeLastStep = route.get(route.size()-1);
            
            setStartAddress(lookup.getAddress(route.get(0).getLat(), route.get(0).getLng()));
            setEndAddress(lookup.getAddress(route.get(route.size()-1).getLat(), route.get(route.size()-1).getLng()));
        }
    }
    public String getStartDateString()
    {
        dateFormatter = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"));
        dateFormatter.setTimeZone(timeZone);
        return dateFormatter.format(trip.getStartTime());
    }
    public String getEndDateString()
    {
        dateFormatter = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"));
        dateFormatter.setTimeZone(timeZone);
        return dateFormatter.format(trip.getEndTime());
    }

    public String getDateShort()
    {
        return dateShort;
    }

    public void setDateShort(String dateShort)
    {
        this.dateShort = dateShort;
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

    public TimeZone getTimeZone()
    {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone)
    {
        this.timeZone = timeZone;
    }
    
}
