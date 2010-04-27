package com.inthinc.pro.reports;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.GroupType;

public enum ReportGroup
{
    DIVISION_REPORT("Fleet/Division Report",0,EntityType.ENTITY_GROUP,
            new CriteriaType[]{CriteriaType.DURATION}, 
            new GroupType[]{GroupType.DIVISION,GroupType.FLEET},
            ReportType.OVERALL_SCORE,ReportType.TREND,ReportType.MPG_GROUP),
//    TEAM_REPORT("Team Report",1,EntityType.ENTITY_GROUP,
//            new CriteriaType[]{CriteriaType.DURATION}, 
//            new GroupType[]{GroupType.TEAM},
//            ReportType.OVERALL_SCORE,ReportType.MPG_GROUP),
    DRIVERS_REPORT("Driver Report",2,EntityType.ENTITY_GROUP,
            new CriteriaType[]{CriteriaType.DURATION}, 
            new GroupType[]{GroupType.DIVISION,GroupType.FLEET,GroupType.TEAM},
            ReportType.DRIVER_REPORT),
    VEHICLES_REPORT("Vehicle Report",3,EntityType.ENTITY_GROUP,
            new CriteriaType[]{CriteriaType.DURATION}, 
            new GroupType[]{GroupType.DIVISION,GroupType.FLEET,GroupType.TEAM},
            ReportType.VEHICLE_REPORT),
    IDLING_REPORT("Idling Report",4,EntityType.ENTITY_GROUP,
            new CriteriaType[]{}, 
            new GroupType[]{GroupType.DIVISION,GroupType.FLEET,GroupType.TEAM},
            ReportType.IDLING_REPORT),
    DEVICES_REPORT("Device Report",5,EntityType.ENTITY_GROUP,
            new CriteriaType[]{}, 
            new GroupType[]{GroupType.DIVISION,GroupType.FLEET,GroupType.TEAM},
            ReportType.DEVICES_REPORT),
    TEAM_STATISTICS_REPORT("Team Statistics Report",6,EntityType.ENTITY_GROUP,
            new CriteriaType[]{CriteriaType.TIMEFRAME}, 
            new GroupType[]{GroupType.TEAM},
            ReportType.TEAM_STATISTICS_REPORT);
        
    
    private ReportType[] reports;
    private Integer code;
    private String label;
    private EntityType entityType; //Type of entity this report is bound to
    private CriteriaType[] criterias;
    
    //GroupTypes These are used to indicate which groups have access to the report as well as which type of groups that this report can be ran against
    private GroupType[] groupTypes; 
   
    /**
     * 
     * @param label Report Title
     * @param code - Code to be stored in the DB
     * @param entityType - Entity for which this report belongs (GROUP,DRIVER,VEHICLE)
     * @param criterias - List of Criterias
     * @param groupTypes - If entityType is GROUP then this is the list of groups that are acceptable for this report
     * @param reports - List of ReportTypes
     */
    private ReportGroup(String label, Integer code,EntityType entityType,CriteriaType[] criterias,GroupType[] groupTypes,ReportType... reports){
        this.reports = reports;
        this.label = label;
        this.code = code; 
        this.criterias = criterias;
        this.groupTypes = groupTypes;
        this.entityType = entityType;
    }
    
    /**
     * This constructor is to assist in creating Driver and Vehicle report groups
     * 
     * @param label - Report Title
     * @param code - Code to be stored in the DB
     * @param criterias - List of Criterias
     * @param reports - List of ReportTypes (individual reports)
     */
    private ReportGroup(String label, Integer code,EntityType entityType, CriteriaType[] criterias,ReportType... reports){
        this.reports = reports;
        this.label = label;
        this.code = code; 
        this.criterias = criterias;
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
    
    public CriteriaType[] getCriterias()
    {
        return criterias;
    }
    
    public Boolean getUseTimeFrame()
    {
    	for (CriteriaType criteria : criterias)
    		if (criteria.equals(CriteriaType.TIMEFRAME))
    			return true;
    	return false;
    }
    public Boolean getUseDuration()
    {
    	for (CriteriaType criteria : criterias)
    		if (criteria.equals(CriteriaType.DURATION))
    			return true;
    	return false;
    }

    public EntityType getEntityType()
    {
        return entityType;
    }

    public GroupType[] getGroupTypes()
    {
        return groupTypes;
    }
    
    public String getMessageKey(){
    	
    	return toString();
    }
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }
}
