package com.inthinc.pro.backing.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
    
    Trip trip;
    
    private static DateFormat dateFormatter;
    
    public TripDisplay(Trip trip)
    {
        this.trip = trip;
        
        dateFormatter = new SimpleDateFormat("dd MMM");
        setDateShort(dateFormatter.format(DateUtil.convertTimeInSecondsToDate(trip.getEndTime() )));
        
        dateFormatter = new SimpleDateFormat("h:mm");
        setTimeShort(dateFormatter.format(DateUtil.convertTimeInSecondsToDate(trip.getEndTime() )));
        
        Double mileageDouble = (double)trip.getMileage() / 100;
        setDistance(mileageDouble.toString() + "mi");
        
        setStartAddress(trip.getStartAddressStr());
        
        setEndAddress(trip.getEndAddressStr());
        
        int diff = trip.getEndTime() - trip.getStartTime();
        Double durationDouble = (double)diff / 60;
        setDuration(durationDouble.toString());   // TODO: Format as 1:32
        
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
    
}
