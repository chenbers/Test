package com.inthinc.device.emulation.enums;


public enum NoteColumnFamily {
    NOTE("note"),
    
    NOTE_TIME_TYPE("***NoteTimeTypeIndex"),
    NOTE_TYPE_TIME("***NoteTypeTimeIndex"),
    NOTE_TIME_TYPE_30("***NoteTimeTypeIndex30"),
    NOTE_TYPE_TIME_30("***NoteTypeTimeIndex30"),
    NOTE_TIME_TYPE_60("***NoteTimeTypeIndex60"),
    NOTE_TYPE_TIME_60("***NoteTypeTimeIndex60"),
    
    BREADCRUMB("***BreadCrumb"),
    BREADCRUMB_60("***BreadCrumb60"),
    
    CURRENT_TRIP_DESCRIPTION("currentTripDescription"),
    CURRENT_TRIP_COUNTER("currentTripCounter"),
    TRIP("trip"),
    TRIP_INDEX("***TripIndex"),
    
    ;
    
    private final String base;
    private NoteColumnFamily(String base){
        this.base = base;
    }
    
    public String getColumnDef(UnitType type){
        return base.replace("***", type == null ? "" : type.toString().toLowerCase());
    }
    
}