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
            ReportType.TEAM_STATISTICS_REPORT),
    // HOS -- will need to figure out some sort of role/acct thing
/*
 *  TODO: NOT SURE WHERE THESE WILL FIT IN     
    NON_DOT_VIOLATIONS_SUMMARY_REPORT("NON-DOT Violations Summary Report","nonDOTViolations.jrxml", "nonDOTViolationsRaw.jrxml", "hos", "com.inthinc.pro.reports.jasper.hos.i18n.nonDOTViolations"),
    DRIVING_TIME_VIOLATIONS_SUMMARY_REPORT("Driving Time Violations Summary Report","drivingTimeViolations.jrxml", "drivingTimeViolationsRaw.jrxml", "hos", "com.inthinc.pro.reports.jasper.hos.i18n.drivingTimeViolations"),
    NON_DOT_VIOLATIONS_DETAIL_REPORT("NON-DOT Violations Detail Report","violationsDetail.jrxml", "violationsDetailRaw.jrxml", "hos", "com.inthinc.pro.reports.jasper.hos.i18n.violationsDetail", "NON_DOT_VIOLATIONS_DETAIL"),
    DRIVING_TIME_VIOLATIONS_DETAIL_REPORT("Driving Time Violations Detail Report","violationsDetail.jrxml", "violationsDetailRaw.jrxml", "hos", "com.inthinc.pro.reports.jasper.hos.i18n.violationsDetail", "DRIVING_TIME_VIOLATIONS_DETAIL"),

    PAYROLL_DETAIL("Driver Hours Report","payrollDetail.jrxml", "payrollRaw.jrxml", "hos", "com.inthinc.pro.reports.jasper.hos.i18n.payrollDetail"),
    PAYROLL_SIGNOFF("Driver Hours Signoff","payrollSignOff.jrxml", "payrollRaw.jrxml", "hos", "com.inthinc.pro.reports.jasper.hos.i18n.payrollSignOff");
            
 */
    HOS_DAILY_DRIVER_LOG_REPORT("HOS Daily Driver Log Report",7,EntityType.ENTITY_DRIVER,
            new CriteriaType[]{CriteriaType.TIMEFRAME}, 
            new GroupType[]{}, true,
            ReportType.HOS_DAILY_DRIVER_LOG_REPORT),
     HOS_VIOLATIONS_SUMMARY_REPORT("HOS Violations Summary Report",8,EntityType.ENTITY_GROUP,
            new CriteriaType[]{CriteriaType.TIMEFRAME}, 
            new GroupType[]{GroupType.DIVISION,GroupType.FLEET,GroupType.TEAM}, true,
            ReportType.HOS_VIOLATIONS_SUMMARY_REPORT),
     HOS_VIOLATIONS_DETAIL_REPORT("HOS Violations Detail Report",9,EntityType.ENTITY_GROUP,
             new CriteriaType[]{CriteriaType.TIMEFRAME}, 
             new GroupType[]{GroupType.DIVISION,GroupType.FLEET,GroupType.TEAM}, true,
             ReportType.HOS_VIOLATIONS_DETAIL_REPORT),
     HOS_DRIVER_DOT_LOG_REPORT("HOS Driver DOT Log Report",10,EntityType.ENTITY_GROUP,
            new CriteriaType[]{CriteriaType.TIMEFRAME}, 
            new GroupType[]{GroupType.DIVISION,GroupType.FLEET,GroupType.TEAM}, true,
            ReportType.HOS_DRIVER_DOT_LOG_REPORT),
     DOT_HOURS_REMAINING("DOT Time Remaining Report",11,EntityType.ENTITY_GROUP,
            new CriteriaType[]{}, 
            new GroupType[]{GroupType.DIVISION,GroupType.FLEET,GroupType.TEAM}, true,
            ReportType.DOT_HOURS_REMAINING),
     HOS_ZERO_MILES("HOS Zero Miles Report",12,EntityType.ENTITY_GROUP,
            new CriteriaType[]{CriteriaType.TIMEFRAME}, 
            new GroupType[]{GroupType.DIVISION,GroupType.FLEET,GroupType.TEAM}, true,
            ReportType.HOS_ZERO_MILES),
     PAYROLL_DETAIL("Driver Hours Report",13,EntityType.ENTITY_GROUP,
            new CriteriaType[]{CriteriaType.TIMEFRAME}, 
            new GroupType[]{GroupType.DIVISION,GroupType.FLEET,GroupType.TEAM}, true,
            ReportType.PAYROLL_DETAIL),
     PAYROLL_SIGNOFF("Driver Hours Signoff",14,EntityType.ENTITY_DRIVER,
             new CriteriaType[]{CriteriaType.TIMEFRAME}, 
             new GroupType[]{}, true,
             ReportType.PAYROLL_SIGNOFF);
    
    private ReportType[] reports;
    private Integer code;
    private String label;
    private EntityType entityType; //Type of entity this report is bound to
    private CriteriaType[] criterias;
    private boolean hos;

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
    private ReportGroup(String label, Integer code,EntityType entityType,CriteriaType[] criterias,GroupType[] groupTypes,boolean hos, ReportType... reports){
        this(label, code, entityType, criterias, groupTypes, reports);
        this.hos = hos;
    }
    private ReportGroup(String label, Integer code,EntityType entityType,CriteriaType[] criterias,GroupType[] groupTypes,ReportType... reports){
        this.reports = reports;
        this.label = label;
        this.code = code; 
        this.criterias = criterias;
        this.groupTypes = groupTypes;
        this.entityType = entityType;
        this.hos = false;
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
    
    public boolean isHos() {
        return hos;
    }
    public void setHos(boolean hos) {
        this.hos = hos;
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
