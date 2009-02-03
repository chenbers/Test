package com.inthinc.pro.reports;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.model.EntityType;

public enum ReportGroup
{
    DIVISION_REPORT("Division Report",0,EntityType.ENTITY_GROUP,ReportType.OVERALL_SCORE,ReportType.TREND,ReportType.MPG_GROUP),
    TEAM_REPORT("Team Report",1,EntityType.ENTITY_GROUP,ReportType.OVERALL_SCORE,ReportType.TREND,ReportType.MPG_GROUP),
    DRIVERS_REPORT("Drivers Report",2,EntityType.ENTITY_DRIVER,ReportType.OVERALL_SCORE,ReportType.TREND,ReportType.MPG_GROUP),
    VEHICLES_REPORT("Vehicles Report",3,EntityType.ENTITY_GROUP,ReportType.OVERALL_SCORE,ReportType.TREND,ReportType.MPG_GROUP),
    IDLING_REPORT("Idling Report",4,EntityType.ENTITY_GROUP,ReportType.OVERALL_SCORE,ReportType.TREND,ReportType.MPG_GROUP),
    DEVICES_REPORT("Devices Report",5,EntityType.ENTITY_GROUP,ReportType.OVERALL_SCORE,ReportType.TREND,ReportType.MPG_GROUP);
    
    private ReportType[] reports;
    private Integer code;
    private String label;
    private EntityType entityType;
    
    private ReportGroup(String label, Integer code,EntityType entityType,ReportType... reports){
        this.reports = reports;
        this.label = label;
        this.code = code;
        this.entityType = entityType;
    }
    
    private static final Map<Integer, ReportGroup> lookup = new HashMap<Integer, ReportGroup>();
    static
    {
        for (ReportGroup p : EnumSet.allOf(ReportGroup.class))
        {
            lookup.put(p.code, p);
        }
    }
    
    public static ReportGroup valueOf(Integer code)
    {
        return lookup.get(code);
    }
    
    public Integer getCode()
    {
        return this.code;
    }
    
    public String getLabel(){
        return label;
    }
    
    public ReportType[] getReports(){
        return reports;
    }

    public EntityType getEntityType()
    {
        return entityType;
    }
}
