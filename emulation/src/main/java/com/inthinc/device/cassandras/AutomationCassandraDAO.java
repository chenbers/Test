package com.inthinc.device.cassandras;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.device.emulation.enums.UnitType;
import com.inthinc.device.emulation.notes.DeviceNote;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public class AutomationCassandraDAO {
    
    private final CassandraFactory factory;
    
    public AutomationCassandraDAO(){
        factory = new CassandraFactory();
    }
    
    public CassandraFactory getFactory(){
        return factory;
    }
    
    public List<DeviceNote> getEvents(UnitType type, int id, AutomationCalendar start, AutomationCalendar stop){
        List<DeviceNote> notes = factory.getEventDAO().fetchEventsForAsset(type, id, start, stop, new ArrayList<Integer>(), true);
        notes.addAll(factory.getLocationDAO().fetchLocationNotes(id, start, stop, type));
        return notes;
    }

}
