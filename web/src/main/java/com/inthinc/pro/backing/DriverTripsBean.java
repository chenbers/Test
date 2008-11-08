package com.inthinc.pro.backing;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Trip;


import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.TripDAO;


public class DriverTripsBean extends BaseBean
{
    private Date startDate = new Date();
    private Date endDate = new Date();
    
    private List<Trip> trips;
    private TripDAO tripDAO;
    private ScoreDAO scoreDAO;
    
    //DATE PICKER PROPERTIES
    public Date getStartDate()
    {
        return startDate;
    }
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }
    public Date getEndDate()
    {
        //trips = tripDAO.getTrips(1, DateUtil.getTodaysDate(), DateUtil.getTodaysDate() - 7);
        return endDate;
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    //TRIP DAO PROPERTIES
    public TripDAO getTripDAO()
    {
        return tripDAO;
    }
    public void setTripDAO(TripDAO tripDAO)
    {
        this.tripDAO = tripDAO;
    }
    
    //SCORE DAO PROPERTIES
    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }
    public void setScoreDAO(ScoreDAO scoreDAO)
    {
        this.scoreDAO = scoreDAO;
    }
    
}
