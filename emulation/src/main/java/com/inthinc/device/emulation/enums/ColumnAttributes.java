package com.inthinc.device.emulation.enums;

public enum ColumnAttributes {
    ATTRIBS_COL("a"), 
    RAW_COL("r"),
    DRIVERID_COL("d"), 
    FORGIVEN_COL("f"), 
    METHOD_COL("m"),
    
    ;
    
    private final String value;
    
    private ColumnAttributes (String value){
        this.value = value;
    }
    
    public String getValue(){
        return value;
    }
}