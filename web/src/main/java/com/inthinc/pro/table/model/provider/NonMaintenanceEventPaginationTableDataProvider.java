package com.inthinc.pro.table.model.provider;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.EventSubCategory;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.pagination.PageParams;

import java.util.ArrayList;
import java.util.List;

public class NonMaintenanceEventPaginationTableDataProvider extends EventPaginationTableDataProvider {
    @Override
    public List<Event> getItemsByRange(int firstRow, int endRow) {

        if (endRow < 0) {
            return new ArrayList<Event>();
        }
        if (endDate == null || startDate == null) {
            initStartEndDates();
        }

        PageParams pageParams = new PageParams(firstRow, endRow, getSort(), getFilters());
        List<Event> eventList = super.getEventDAO().getEventPage(getGroupID(), startDate, endDate, EventDAO.INCLUDE_FORGIVEN, getNonMaintenanceNoteTypesInCategory(getEventCategory()), pageParams);

        populateDeviceNames(eventList);
        return eventList;
    }

    @Override
    public int getRowCount() {

        if (getGroupID() == null) {
            return 0;
        }
        initStartEndDates();
        return super.getEventDAO().getEventCount(getGroupID(), startDate, endDate, EventDAO.INCLUDE_FORGIVEN, getNonMaintenanceNoteTypesInCategory(getEventCategory()), getFilters());
    }

    List<NoteType> getNonMaintenanceNoteTypesInCategory(EventCategory eventCategory){
        List<NoteType> noteTypeList = new ArrayList<NoteType>();

        if (eventCategory.getSubCategorySet() == null)
            return noteTypeList;

        for (EventSubCategory subCategory : eventCategory.getSubCategorySet()) {
            if (subCategory == EventSubCategory.PREVENTATIVE_MAINTENANCE || subCategory == EventSubCategory.CONDITIONAL || subCategory == EventSubCategory.IGNITION_OFF)
                continue;

            for (EventType eventType : subCategory.getEventTypeSet()) {
                for (NoteType noteType : eventType.getNoteTypeList()) {
                    if (!noteTypeList.contains(noteType))
                        noteTypeList.add(noteType);
                }
            }
        }
        return noteTypeList;
    }
}
