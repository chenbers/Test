package com.inthinc.pro.dao;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.model.CrashTrace;
import com.inthinc.pro.model.MessageItem;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

public interface CrashTraceDAO extends GenericDAO<CrashTrace, Integer>{
    
    /**
     * Retrieve the complete list of Crash Traces for the given eventId.
     * @param eventID
     * @return
     */
    List<CrashTrace> getCrashTraces(String eventID);
    
    /**
     * Retrieve the total count of filtered list sublist of Crash Traces for the given eventId, time frame, and pageParams.
     * 
     * @param eventId
     * @param startDate
     * @param endDate
     * @param filterList
     * @return
     */
    Integer getCrashTraceCount(String eventId, Date startDate, Date endDate, List<TableFilterField> filterList);
    
    /**
     * Retrieve the sorted/filtered sublist of Crash Traces for the given eventId, time frame, and pageParams.
     * 
     * @param eventId
     * @param startDate
     * @param endDate
     * @param filterList
     * @param pageParams
     * @return
     */
    List<CrashTrace> getCrashTracePage(String eventId, Date startDate, Date endDate, List<TableFilterField> filterList, PageParams pageParams);
}
