package com.inthinc.device.emulation.enums;

import com.inthinc.pro.automation.enums.WebDateFormat;

public enum ScoringTypes {
    DAYS("","*type*AggIndex", WebDateFormat.CASSANDRA_DAYS),
    MONTHS("Month", "*type*AggMonthIndex", WebDateFormat.CASSANDRA_MONTHS),
    
    GROUP_DAYS("*Type*Group", "agg*Type*GroupIndex", WebDateFormat.CASSANDRA_DAYS),
    GROUP_MONTHS("Month*Type*Group", "aggMonth*Type*GroupIndex", WebDateFormat.CASSANDRA_MONTHS),
    
    ;
    
    private final String columnFamily;
    private final String index;
    private final WebDateFormat format;
    
    private ScoringTypes(String columnFamily, String index, WebDateFormat format){
        this.columnFamily = "agg" + columnFamily;
        this.index = index;
        this.format = format;
    }
    
    public String getColumnFamily(UnitType type){
        String name = type.name();
        return columnFamily.replace("*type*", name.toLowerCase()).replace("*Type*", capitalizeFirstLetter(name));
    }
    
    public String getIndex(UnitType type){
        String name = type.name();
        return index.replace("*type*", name.toLowerCase()).replace("*Type*", capitalizeFirstLetter(name));
    }
    
    private static String capitalizeFirstLetter(String name){
        String lower = name.toLowerCase();
        return name.substring(0, 1) + lower.substring(1);
    }

    public WebDateFormat getFormat() {
        return format;
    }

}
