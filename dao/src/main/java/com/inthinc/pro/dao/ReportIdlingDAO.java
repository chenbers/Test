package com.inthinc.pro.dao;

import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.model.IdlingReportItem;

public interface ReportIdlingDAO {

    /**
     * Gets the idling report items for the specified group and interval
     * 
     * @param groupID
     *              The groupID (deep) to retrieve.
     * @param pageParams
     *              Defines the page to return -- start,end rows, filter criteria and sort criteria            
     * @return List of idlingReportItems in group that meet filtering criteria, sorted by sort criteria.
     */
    List<IdlingReportItem> getIdlingReportData(Integer groupID, Interval interval);

}
