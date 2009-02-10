package com.inthinc.pro.reports;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.model.EntityType;

public enum ReportGroup
{
    DIVISION_REPORT("Division Report",0,new CriteriaType[]{CriteriaType.DURATION,CriteriaType.GROUP},ReportType.OVERALL_SCORE,ReportType.TREND,ReportType.MPG_GROUP),
    DRIVERS_REPORT("Drivers Report",2,new CriteriaType[]{CriteriaType.DURATION,CriteriaType.GROUP},ReportType.DRIVER_REPORT),
    VEHICLES_REPORT("Vehicles Report",3,new CriteriaType[]{CriteriaType.DURATION,CriteriaType.GROUP},ReportType.VEHICLE_REPORT),
    IDLING_REPORT("Idling Report",4,new CriteriaType[]{CriteriaType.DURATION,CriteriaType.GROUP},ReportType.IDLING_REPORT),
    DEVICES_REPORT("Devices Report",5,new CriteriaType[]{CriteriaType.DURATION,CriteriaType.GROUP},ReportType.DEVICES_REPORT);
    
    
    private ReportType[] reports;
    private Integer code;
    private String label;
    private EntityType entityType; //Type of entity this report is bound to
    private CriteriaType[] criterias;
   
    
    private ReportGroup(String label, Integer code,CriteriaType[] criterias,ReportType... reports){
        this.reports = reports;
        this.label = label;
        this.code = code; 
        this.criterias = criterias;
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
    
    public CriteriaType[] getCriterias()
    {
        return criterias;
    }

    public EntityType getEntityType()
    {
        return entityType;
    }
}
