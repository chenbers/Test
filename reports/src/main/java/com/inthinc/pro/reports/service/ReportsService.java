package com.inthinc.pro.reports.service;

import java.util.List;
import java.util.TimeZone;

import com.inthinc.pro.reports.dto.EventDTO;

public interface ReportsService
{
    List<EventDTO> getEventsByGroupID(Integer groupID,Integer daysBack, TimeZone timeZone); 
    List<EventDTO> getWarningsByGroupID(Integer groupID,Integer daysBack, TimeZone timeZone); 
    List<EventDTO> getRedFlagsByGroupID(Integer groupID,Integer daysBack, TimeZone timeZone); 
}
