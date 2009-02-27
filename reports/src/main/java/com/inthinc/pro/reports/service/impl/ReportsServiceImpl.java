package com.inthinc.pro.reports.service.impl;

import java.util.List;
import java.util.TimeZone;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.reports.dto.EventDTO;
import com.inthinc.pro.reports.service.ReportsService;

public class ReportsServiceImpl implements ReportsService
{
    private EventDAO eventDAO;
    private RedFlagDAO redFlagDAO;
    
    @Override
    public List<EventDTO> getEventsByGroupID(Integer groupID, Integer daysBack, TimeZone timeZone)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<EventDTO> getRedFlagsByGroupID(Integer groupID, Integer daysBack, TimeZone timeZone)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<EventDTO> getWarningsByGroupID(Integer groupID, Integer daysBack, TimeZone timeZone)
    {
        // TODO Auto-generated method stub
        return null;
    }

    public void setEventDAO(EventDAO eventDAO)
    {
        this.eventDAO = eventDAO;
    }

    public EventDAO getEventDAO()
    {
        return eventDAO;
    }

    public void setRedFlagDAO(RedFlagDAO redFlagDAO)
    {
        this.redFlagDAO = redFlagDAO;
    }

    public RedFlagDAO getRedFlagDAO()
    {
        return redFlagDAO;
    }

}
