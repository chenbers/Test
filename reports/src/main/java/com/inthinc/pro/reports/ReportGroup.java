package com.inthinc.pro.reports;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.GroupType;

public enum ReportGroup {
    DIVISION_REPORT("Fleet/Division Report", 0, EntityType.ENTITY_GROUP, new CriteriaType[] { CriteriaType.DURATION }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET },
            ReportType.OVERALL_SCORE, ReportType.TREND, ReportType.MPG_GROUP),
    DRIVERS_REPORT("Driver Report", 2, EntityType.ENTITY_GROUP, new CriteriaType[] { CriteriaType.DURATION }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET, GroupType.TEAM },
            ReportType.DRIVER_REPORT),
    VEHICLES_REPORT("Vehicle Report", 3, EntityType.ENTITY_GROUP, new CriteriaType[] { CriteriaType.DURATION }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET, GroupType.TEAM },
            ReportType.VEHICLE_REPORT),
    IDLING_REPORT("Idling Report", 4, EntityType.ENTITY_GROUP, new CriteriaType[] {}, new GroupType[] { GroupType.DIVISION, GroupType.FLEET, GroupType.TEAM }, ReportType.IDLING_REPORT),
    DEVICES_REPORT("Device Report", 5, EntityType.ENTITY_GROUP, new CriteriaType[] {}, new GroupType[] { GroupType.DIVISION, GroupType.FLEET, GroupType.TEAM }, ReportType.DEVICES_REPORT),
    TEAM_STATISTICS_REPORT("Team Statistics Report", 6, EntityType.ENTITY_GROUP, new CriteriaType[] { CriteriaType.TIMEFRAME }, new GroupType[] { GroupType.TEAM }, ReportType.TEAM_STATISTICS_REPORT),
    TEAM_STOPS_REPORT("Team Stops Report", 106, EntityType.ENTITY_GROUP_LIST, new CriteriaType[] { CriteriaType.TIMEFRAME_ALT_DAYS }, new GroupType[] { GroupType.TEAM },
            ReportCategory.DriverPerformance, null, ReportType.TEAM_STOPS_REPORT),
    SEATBELT_CLICKS_REPORT("Seatbelt Clicks Report", 107, EntityType.ENTITY_GROUP, new CriteriaType[] { CriteriaType.TIMEFRAME_STRICT, CriteriaType.INACTIVE_AND_ZERO_MILES }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET,
            GroupType.TEAM }, ReportCategory.DriverPerformance, EnumSet.of(ReportAccountType.PERFORMANCE), ReportType.SEATBELT_CLICKS_REPORT),
    HOS_DAILY_DRIVER_LOG_REPORT("HOS Daily Driver Log Report", 7, EntityType.ENTITY_GROUP_LIST_OR_DRIVER, new CriteriaType[] { CriteriaType.TIMEFRAME, CriteriaType.INACTIVE_AND_ZERO_MILES }, new GroupType[] { GroupType.DIVISION,
            GroupType.FLEET, GroupType.TEAM }, ReportCategory.HOS, EnumSet.of(ReportAccountType.HOS), ReportType.HOS_DAILY_DRIVER_LOG_REPORT),
    HOS_VIOLATIONS_SUMMARY_REPORT("HOS Violations Summary Report", 8, EntityType.ENTITY_GROUP_LIST, new CriteriaType[] { CriteriaType.TIMEFRAME, CriteriaType.INACTIVE_AND_ZERO_MILES }, new GroupType[] { GroupType.DIVISION,
            GroupType.FLEET, GroupType.TEAM }, ReportCategory.HOS, EnumSet.of(ReportAccountType.HOS), ReportType.HOS_VIOLATIONS_SUMMARY_REPORT),
    HOS_VIOLATIONS_DETAIL_REPORT("HOS Violations Detail Report", 9, EntityType.ENTITY_GROUP_LIST_OR_DRIVER, new CriteriaType[] { CriteriaType.TIMEFRAME, CriteriaType.INACTIVE_AND_ZERO_MILES }, new GroupType[] { GroupType.DIVISION,
            GroupType.FLEET, GroupType.TEAM }, ReportCategory.HOS, EnumSet.of(ReportAccountType.HOS), ReportType.HOS_VIOLATIONS_DETAIL_REPORT),
    HOS_DRIVER_DOT_LOG_REPORT("HOS Driver DOT Log Report", 10, EntityType.ENTITY_DRIVER, new CriteriaType[] { CriteriaType.TIMEFRAME, CriteriaType.INACTIVE_AND_ZERO_MILES }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET,
            GroupType.TEAM }, ReportCategory.HOS, EnumSet.of(ReportAccountType.HOS), ReportType.HOS_DRIVER_DOT_LOG_REPORT),
    DOT_HOURS_REMAINING("DOT Time Remaining Report", 11, EntityType.ENTITY_GROUP_LIST, new CriteriaType[] {CriteriaType.INACTIVE_AND_ZERO_MILES}, new GroupType[] { GroupType.DIVISION, GroupType.FLEET, GroupType.TEAM },
            ReportCategory.HOS, EnumSet.of(ReportAccountType.HOS), ReportType.DOT_HOURS_REMAINING),
    HOS_ZERO_MILES("HOS Zero Miles Report", 12, EntityType.ENTITY_GROUP_LIST, new CriteriaType[] { CriteriaType.TIMEFRAME }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET, GroupType.TEAM },
            ReportCategory.HOS, EnumSet.of(ReportAccountType.HOS), ReportType.HOS_ZERO_MILES),
    HOS_EDITS("HOS Edits", 13, EntityType.ENTITY_GROUP_LIST, new CriteriaType[] { CriteriaType.TIMEFRAME, CriteriaType.INACTIVE_AND_ZERO_MILES }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET, GroupType.TEAM },
            ReportCategory.HOS, EnumSet.of(ReportAccountType.HOS), ReportType.HOS_EDITS),
    NON_DOT_VIOLATIONS_SUMMARY_REPORT("NON-DOT Violations Summary Report", 30, EntityType.ENTITY_GROUP_LIST, new CriteriaType[] { CriteriaType.TIMEFRAME, CriteriaType.INACTIVE_AND_ZERO_MILES }, new GroupType[] { GroupType.DIVISION,
            GroupType.FLEET, GroupType.TEAM }, ReportCategory.HOS, EnumSet.of(ReportAccountType.HOS), ReportType.NON_DOT_VIOLATIONS_SUMMARY_REPORT),
    NON_DOT_VIOLATIONS_DETAIL_REPORT("NON-DOT Violations Detail Report", 31, EntityType.ENTITY_GROUP_LIST_OR_DRIVER, new CriteriaType[] { CriteriaType.TIMEFRAME, CriteriaType.INACTIVE_AND_ZERO_MILES }, new GroupType[] {
            GroupType.DIVISION, GroupType.FLEET, GroupType.TEAM }, ReportCategory.HOS, EnumSet.of(ReportAccountType.HOS), ReportType.NON_DOT_VIOLATIONS_DETAIL_REPORT),
    DRIVING_TIME_VIOLATIONS_SUMMARY_REPORT("Driving Time Violations Summary Report", 32, EntityType.ENTITY_GROUP_LIST, new CriteriaType[] { CriteriaType.TIMEFRAME, CriteriaType.INACTIVE_AND_ZERO_MILES }, new GroupType[] {
            GroupType.DIVISION, GroupType.FLEET, GroupType.TEAM }, ReportCategory.Performance, EnumSet.of(ReportAccountType.HOS, ReportAccountType.WAYSMART),
            ReportType.DRIVING_TIME_VIOLATIONS_SUMMARY_REPORT),
    DRIVING_TIME_VIOLATIONS_DETAIL_REPORT("Driving Time Violations Detail Report", 33, EntityType.ENTITY_GROUP_LIST_OR_DRIVER, new CriteriaType[] { CriteriaType.TIMEFRAME, CriteriaType.INACTIVE_AND_ZERO_MILES }, new GroupType[] {
            GroupType.DIVISION, GroupType.FLEET, GroupType.TEAM }, ReportCategory.Performance, EnumSet.of(ReportAccountType.HOS, ReportAccountType.WAYSMART),
            ReportType.DRIVING_TIME_VIOLATIONS_DETAIL_REPORT),

    // IFTA
    MILEAGE_BY_VEHICLE("Mileage by vehicle", 20, EntityType.ENTITY_GROUP_LIST, new CriteriaType[] { CriteriaType.TIMEFRAME }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET, GroupType.TEAM },
            ReportCategory.IFTA, EnumSet.of(ReportAccountType.WAYSMART), ReportType.MILEAGE_BY_VEHICLE),

    STATE_MILEAGE_BY_VEHICLE("State Mileage By Vehicle", 21, EntityType.ENTITY_GROUP_LIST_AND_IFTA, new CriteriaType[] { CriteriaType.TIMEFRAME }, new GroupType[] { GroupType.DIVISION,
            GroupType.FLEET, GroupType.TEAM }, ReportCategory.IFTA, EnumSet.of(ReportAccountType.WAYSMART), ReportType.STATE_MILEAGE_BY_VEHICLE),

    STATE_MILEAGE_BY_VEHICLE_ROAD_STATUS("State mileage by vehicle road status", 22, EntityType.ENTITY_GROUP_LIST_AND_IFTA, new CriteriaType[] { CriteriaType.TIMEFRAME }, new GroupType[] {
            GroupType.DIVISION, GroupType.FLEET, GroupType.TEAM }, ReportCategory.IFTA, EnumSet.of(ReportAccountType.WAYSMART), ReportType.STATE_MILEAGE_BY_VEHICLE_ROAD_STATUS),

    STATE_MILEAGE_FUEL_BY_VEHICLE("State Mileage Fuel By Vehicle", 23, EntityType.ENTITY_GROUP_LIST_AND_IFTA, new CriteriaType[] { CriteriaType.TIMEFRAME }, new GroupType[] { GroupType.DIVISION,
            GroupType.FLEET, GroupType.TEAM }, ReportCategory.IFTA, EnumSet.of(ReportAccountType.WAYSMART), ReportType.STATE_MILEAGE_FUEL_BY_VEHICLE),

    STATE_MILEAGE_BY_MONTH("State Mileage By Month", 24, EntityType.ENTITY_GROUP_LIST_AND_IFTA, new CriteriaType[] { CriteriaType.TIMEFRAME }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET,
            GroupType.TEAM }, ReportCategory.IFTA, EnumSet.of(ReportAccountType.WAYSMART), ReportType.STATE_MILEAGE_BY_MONTH),

    STATE_MILEAGE_COMPARE_BY_GROUP("Group Comparison By State/Province", 25, EntityType.ENTITY_GROUP_LIST_AND_IFTA, new CriteriaType[] { CriteriaType.TIMEFRAME }, new GroupType[] {
            GroupType.DIVISION, GroupType.FLEET, GroupType.TEAM }, ReportCategory.IFTA, EnumSet.of(ReportAccountType.WAYSMART), ReportType.STATE_MILEAGE_COMPARE_BY_GROUP),

    /* Mileage */
    STATE_MILEAGE_BY_VEHICLE_NON_IFTA("State Mileage By Vehicle", 26, EntityType.ENTITY_GROUP, new CriteriaType[] { CriteriaType.TIMEFRAME }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET,
            GroupType.TEAM }, ReportCategory.Mileage, EnumSet.of(ReportAccountType.PERFORMANCE), ReportType.STATE_MILEAGE_BY_VEHICLE_NON_IFTA),

    /* Communication */
    NON_COMM("Vehicles Not Communicating Report", 27, EntityType.ENTITY_GROUP, new CriteriaType[] { CriteriaType.TIMEFRAME_STRICT }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET,
            GroupType.TEAM }, ReportCategory.Communication, EnumSet.of(ReportAccountType.PERFORMANCE), ReportType.NON_COMM),

    // Performance
    PAYROLL_SUMMARY("Payroll Report Summary", 14, EntityType.ENTITY_GROUP_LIST, new CriteriaType[] { CriteriaType.TIMEFRAME, CriteriaType.INACTIVE_AND_ZERO_MILES }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET, GroupType.TEAM },
            ReportCategory.Performance, EnumSet.of(ReportAccountType.WAYSMART, ReportAccountType.HOS), ReportType.PAYROLL_SUMMARY),
    PAYROLL_COMPENSATED_HOURS("Payroll Compensated Hours", 39, EntityType.ENTITY_GROUP_LIST, new CriteriaType[] { CriteriaType.TIMEFRAME, CriteriaType.INACTIVE_AND_ZERO_MILES }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET,
            GroupType.TEAM }, ReportCategory.Performance, EnumSet.of(ReportAccountType.WAYSMART, ReportAccountType.HOS), ReportType.PAYROLL_COMPENSATED_HOURS),
    PAYROLL_DETAIL("Payroll Report Driver Detail", 15, EntityType.ENTITY_GROUP_LIST, new CriteriaType[] { CriteriaType.TIMEFRAME, CriteriaType.INACTIVE_AND_ZERO_MILES }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET,
            GroupType.TEAM }, ReportCategory.Performance, EnumSet.of(ReportAccountType.WAYSMART, ReportAccountType.HOS), ReportType.PAYROLL_DETAIL),
    PAYROLL_SIGNOFF("Payroll Report Driver Signoff", 16, EntityType.ENTITY_GROUP_LIST_OR_DRIVER, new CriteriaType[] { CriteriaType.TIMEFRAME, CriteriaType.INACTIVE_AND_ZERO_MILES }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET,
            GroupType.TEAM }, ReportCategory.Performance, EnumSet.of(ReportAccountType.WAYSMART, ReportAccountType.HOS), ReportType.PAYROLL_SIGNOFF),
    TEN_HOUR_DAY_VIOLATIONS("Ten Hour Day Violations", 17, EntityType.ENTITY_GROUP, new CriteriaType[] { CriteriaType.TIMEFRAME, CriteriaType.INACTIVE_AND_ZERO_MILES }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET,
            GroupType.TEAM }, ReportCategory.Performance, EnumSet.of(ReportAccountType.WAYSMART), ReportType.TEN_HOUR_DAY_VIOLATIONS),
    DRIVER_HOURS("Driver Hours", 18, EntityType.ENTITY_GROUP, new CriteriaType[] { CriteriaType.TIMEFRAME, CriteriaType.INACTIVE_AND_ZERO_MILES }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET, GroupType.TEAM },
            ReportCategory.Performance, EnumSet.of(ReportAccountType.WAYSMART), ReportType.DRIVER_HOURS),
    DRIVER_PERFORMANCE_KEY_METRICS("Driver Performance Key Metrics", 38, EntityType.ENTITY_GROUP_LIST, new CriteriaType[] { CriteriaType.TIMEFRAME_ALT, CriteriaType.INACTIVE_AND_ZERO_MILES },
            new GroupType[] { GroupType.DIVISION, GroupType.FLEET, GroupType.TEAM }, ReportCategory.DriverPerformance, EnumSet.of(ReportAccountType.PERFORMANCE),
            ReportType.DRIVER_PERFORMANCE_KEY_METRICS),
    DRIVER_PERFORMANCE_KEY_METRICS_TF_RYG("Driver Performance Key Metrics Time Frame RYG", 43, EntityType.ENTITY_GROUP_LIST, new CriteriaType[] { CriteriaType.TIMEFRAME_ALT_PLUS_CUSTOM_RANGE, CriteriaType.INACTIVE_AND_ZERO_MILES },
            new GroupType[] { GroupType.DIVISION, GroupType.FLEET, GroupType.TEAM }, ReportCategory.DriverPerformance, EnumSet.of(ReportAccountType.PERFORMANCE),
            ReportType.DRIVER_PERFORMANCE_KEY_METRICS_TF_RYG),
    DRIVER_PERFORMANCE_TEAM("Driver Performance (Team)", 34, EntityType.ENTITY_GROUP, new CriteriaType[] { CriteriaType.TIMEFRAME, CriteriaType.INACTIVE_AND_ZERO_MILES }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET,
            GroupType.TEAM }, ReportCategory.DriverPerformance, EnumSet.of(ReportAccountType.PERFORMANCE), ReportType.DRIVER_PERFORMANCE_TEAM),
    DRIVER_PERFORMANCE_INDIVIDUAL("Driver Performance (Individual)", 35, EntityType.ENTITY_INDIVIDUAL_DRIVER, new CriteriaType[] { CriteriaType.TIMEFRAME }, new GroupType[] { GroupType.DIVISION,
            GroupType.FLEET, GroupType.TEAM }, ReportCategory.DriverPerformance, EnumSet.of(ReportAccountType.PERFORMANCE), ReportType.DRIVER_PERFORMANCE_INDIVIDUAL),
    DRIVER_PERFORMANCE_RYG_TEAM("Driver Performance RYG (Team)", 36, EntityType.ENTITY_GROUP, new CriteriaType[] { CriteriaType.TIMEFRAME, CriteriaType.INACTIVE_AND_ZERO_MILES }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET,
            GroupType.TEAM }, ReportCategory.DriverPerformance, EnumSet.of(ReportAccountType.PERFORMANCE), ReportType.DRIVER_PERFORMANCE_RYG_TEAM),
    DRIVER_PERFORMANCE_RYG_INDIVIDUAL("Driver Performance RYG (Individual)", 37, EntityType.ENTITY_INDIVIDUAL_DRIVER, new CriteriaType[] { CriteriaType.TIMEFRAME },
            new GroupType[] { GroupType.TEAM }, ReportCategory.DriverPerformance, EnumSet.of(ReportAccountType.PERFORMANCE), ReportType.DRIVER_PERFORMANCE_RYG_INDIVIDUAL),
    DRIVER_COACHING("Driver Coaching", 41, EntityType.ENTITY_GROUP_OR_DRIVER, new CriteriaType[] { CriteriaType.TIMEFRAME, CriteriaType.INACTIVE_AND_ZERO_MILES }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET, GroupType.TEAM },
            ReportCategory.DriverPerformance, EnumSet.of(ReportAccountType.PERFORMANCE), ReportType.DRIVER_COACHING),
    DRIVER_EXCLUDED_VIOLATIONS("Driver Coaching", 42, EntityType.ENTITY_GROUP, new CriteriaType[] { CriteriaType.TIMEFRAME, CriteriaType.INACTIVE_AND_ZERO_MILES }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET, GroupType.TEAM },
            ReportCategory.DriverPerformance, EnumSet.of(ReportAccountType.PERFORMANCE), ReportType.DRIVER_EXCLUDED_VIOLATIONS),
    /* Forms */
    DVIR_PRE_TRIP("DVIR Pre-Trip", 43, EntityType.ENTITY_GROUP, new CriteriaType[] { CriteriaType.TIMEFRAME }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET, GroupType.TEAM },
            ReportCategory.DVIR, EnumSet.of(ReportAccountType.DVIR), ReportType.DVIR_PRETRIP),
    DVIR_POST_TRIP("DVIR Post-Trip", 44, EntityType.ENTITY_GROUP, new CriteriaType[] { CriteriaType.TIMEFRAME }, new GroupType[] { GroupType.DIVISION, GroupType.FLEET, GroupType.TEAM },
            ReportCategory.DVIR, EnumSet.of(ReportAccountType.DVIR), ReportType.DVIR_POSTTRIP),

    ;

    private ReportType[] reports;
    private Integer code;
    private String label;
    private EntityType entityType; // Type of entity this report is bound to
    private CriteriaType[] criterias;
    private ReportCategory reportCategory;
    private Set<ReportAccountType> reportAccountTypes;

    // GroupTypes These are used to indicate which groups have access to the report as well as which type of groups that this report can be ran against
    private GroupType[] groupTypes;

    /**
     * 
     * @param label
     *            Report Title
     * @param code
     *            - Code to be stored in the DB
     * @param entityType
     *            - Entity for which this report belongs (GROUP,DRIVER,VEHICLE)
     * @param criterias
     *            - List of Criterias
     * @param groupTypes
     *            - If entityType is GROUP then this is the list of groups that are acceptable for this report
     * @param reports
     *            - List of ReportTypes
     * @param reportCategory
     *            - Category of the report
     */

    private ReportGroup(String label, Integer code, EntityType entityType, CriteriaType[] criterias, GroupType[] groupTypes, ReportCategory reportCategory, Set<ReportAccountType> reportAccountTypes,
            ReportType... reports) {
        this(label, code, entityType, criterias, groupTypes, reports);
        this.reportCategory = reportCategory;
        this.reportAccountTypes = reportAccountTypes;
    }

    private ReportGroup(String label, Integer code, EntityType entityType, CriteriaType[] criterias, GroupType[] groupTypes, ReportType... reports) {
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
     * @param label
     *            - Report Title
     * @param code
     *            - Code to be stored in the DB
     * @param criterias
     *            - List of Criterias
     * @param reports
     *            - List of ReportTypes (individual reports)
     */
    private ReportGroup(String label, Integer code, EntityType entityType, CriteriaType[] criterias, ReportType... reports) {
        this.reports = reports;
        this.label = label;
        this.code = code;
        this.criterias = criterias;
        this.entityType = entityType;
    }

    private static final Map<Integer, ReportGroup> lookup = new HashMap<Integer, ReportGroup>();
    static {
        for (ReportGroup p : EnumSet.allOf(ReportGroup.class)) {
            lookup.put(p.code, p);
        }
    }

    public static ReportGroup valueOf(Integer code) {
        return lookup.get(code);
    }

    public Integer getCode() {
        return this.code;
    }

    public String getLabel() {
        return label;
    }

    public ReportType[] getReports() {
        return reports;
    }

    public CriteriaType[] getCriterias() {
        return criterias;
    }

    public Boolean getUseTimeFrame() {
        for (CriteriaType criteria : criterias)
            if (criteria.equals(CriteriaType.TIMEFRAME) || criteria.equals(CriteriaType.TIMEFRAME_ALT) || criteria.equals(CriteriaType.TIMEFRAME_ALT_DAYS))
                return true;
        return false;
    }

    public Boolean getUseDuration() {
        for (CriteriaType criteria : criterias)
            if (criteria.equals(CriteriaType.DURATION))
                return true;
        return false;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public GroupType[] getGroupTypes() {
        return groupTypes;
    }

    public boolean isTabularSupport() {
        for (int i = 0; i < getReports().length; i++)
            if (getReports()[i].isTabularSupport())
                return true;

        return false;
    }

    public boolean isExcelSupport() {
        return (this != HOS_DAILY_DRIVER_LOG_REPORT);
    }

    public boolean isNonExcelSupport() {
        boolean nonExcelSupport = false;
        for (ReportType reportType : this.getReports())
            if (reportType.getPrettyTemplate() != null)
                nonExcelSupport = true;
        return nonExcelSupport;
    }

    public String getMessageKey() {

        return toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }

    public ReportCategory getReportCategory() {
        return reportCategory;
    }

    public boolean isPerformance() {
        return isCategory(ReportCategory.Performance);
    }

    public boolean isIfta() {
        return isCategory(ReportCategory.IFTA);
    }

    public boolean isHOS() {
        return isCategory(ReportCategory.HOS);
    }

    public boolean isAsset() {
        return isCategory(ReportCategory.Asset);
    }

    public boolean isCategory(ReportCategory category) {
        return (category == this.getReportCategory());
    }

    public Boolean getRequiresHOSAccount() {
        return reportAccountTypes != null && reportAccountTypes.contains(ReportAccountType.HOS);
    }

    public Boolean getRequiresWaySmartAccount() {
        return reportAccountTypes != null && reportAccountTypes.contains(ReportAccountType.WAYSMART);
    }

}
