package com.inthinc.pro.model.event;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.model.BaseEnum;

@XmlRootElement
public enum EventCategory implements BaseEnum
{
    VIOLATION(1, "VIOLATION", EnumSet.of(EventSubCategory.DRIVING_STYLE, EventSubCategory.SPEED, EventSubCategory.COMPLIANCE, EventSubCategory.FATIGUE)),
    WARNING(2, "WARNING", EnumSet.of(EventSubCategory.VEHICLE, EventSubCategory.WIRELINE, EventSubCategory.INSTALLATION)),
//    DRIVER(3, "DRIVER"),
    NONE(4, "NONE"),
    EMERGENCY(5, "EMERGENCY", EnumSet.of(EventSubCategory.EMERGENCY)),
    ZONE(6,"ZONE", EnumSet.of(EventSubCategory.ZONES)),
//    NO_DRIVER(7,"NO_DRIVER"),
    HOS(8, "HOS", EnumSet.of(EventSubCategory.HOS)),
    TEXT(9,"TEXT", EnumSet.of(EventSubCategory.TEXTMESSAGE)),
    DRIVER_LOGIN(10, "DRIVER", EnumSet.of(EventSubCategory.DRIVER));

    private String description;
    private int code;
    private Set<EventSubCategory> subCategorySet;


    private EventCategory(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private EventCategory(int code, String description, Set<EventSubCategory> subCategorySet)
    {
        this.code = code;
        this.description = description;
        this.subCategorySet = subCategorySet;
    }

    private static final Map<Integer, EventCategory> lookup = new HashMap<Integer, EventCategory>();
    static
    {
        for (EventCategory p : EnumSet.allOf(EventCategory.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static EventCategory valueOf(Integer code)
    {
        return lookup.get(code);
    }
    public Set<EventSubCategory> getSubCategorySet() {
        return subCategorySet;
    }

    public void setSubCategorySet(Set<EventSubCategory> subCategorySet) {
        this.subCategorySet = subCategorySet;
    }


    @Override
    public String toString()
    {
        return this.description;
    }
 
    
    public List<NoteType> getNoteTypesInCategory()
    {
        List<NoteType> noteTypeList = new ArrayList<NoteType>();
        
        if (getSubCategorySet() == null)
            return noteTypeList;
        
        for (EventSubCategory subCategory : getSubCategorySet()) {
            
            for (EventType eventType : subCategory.getEventTypeSet()) {
                for (NoteType noteType : eventType.getNoteTypeList()) {
                    if (!noteTypeList.contains(noteType))
                        noteTypeList.add(noteType);
                }
            }
        }
        return noteTypeList;
    }
    
    public static EventCategory getCategoryForEventType(EventType eventType) {

        for (EventCategory cat : EnumSet.allOf(EventCategory.class)) {
            if (cat.getSubCategorySet() == null)
                continue;
            for (EventSubCategory subCategory : cat.getSubCategorySet()) {
                
                for (EventType subCatEventType : subCategory.getEventTypeSet()) {
                    if (subCatEventType == eventType)
                        return cat;
                }
            }
        }
        return EventCategory.NONE;
    }

}
