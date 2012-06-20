package com.inthinc.pro.selenium.pageEnums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.inthinc.pro.automation.interfaces.IndexEnum;
import com.inthinc.pro.automation.utils.AutomationStringUtil;

public enum EditColumnsEnums implements IndexEnum {
    
    REPORTS_DRIVERS_GROUP(1),
    REPORTS_DRIVERS_EMPLOYEE_ID(2),
    REPORTS_DRIVERS_DRIVER(3),
    REPORTS_DRIVERS_VEHICLE(4),
    REPORTS_DRIVERS_DISTANCE_DRIVEN(5),
    REPORTS_DRIVERS_OVERALL(6),
    REPORTS_DRIVERS_SPEED(7),
    REPORTS_DRIVERS_STYLE(8),
    REPORTS_DRIVERS_SEAT_BELT(9),
    
    
    REPORTS_DEVICES_DEVICE_ID(1),
    REPORTS_DEVICES_ASSIGNED_VEHICLE(2),
    REPORTS_DEVICES_IMEI(3),
    REPORTS_DEVICES_DEVICE_PHONE_NUMBER(4),
    REPORTS_DEVICES_STATUS(5),
    
    
    REPORTS_IDLING_GROUP(1),
    REPORTS_IDLING_DRIVER(2),
    REPORTS_IDLING_DURATION(3),
    REPORTS_IDLING_IDLE_SUPPORT(4),
    REPORTS_IDLING_LOW_IDLE_HRS(5),
    REPORTS_IDLING_LOW_PERCENT(6),
    REPORTS_IDLING_HIGH_IDLE_HRS(7),
    REPORTS_IDLING_HIGH_PERCENT(8),
    REPORTS_IDLING_TOTAL_IDLE_HRS(9),
    REPORTS_IDLING_TOTAL_PERCENT(10),
    
    
    REPORTS_VEHICLES_GROUP(1),
    REPORTS_VEHICLES_VEHICLE_ID(2),
    REPORTS_VEHICLES_YEAR_MAKE_MODEL(3),
    REPORTS_VEHICLES_DRIVER(4),
    REPORTS_VEHICLES_DISTANCE_DRIVEN(5),
    REPORTS_VEHICLES_OVERALL(6),
    REPORTS_VEHICLES_SPEED(7),
    REPORTS_VEHICLES_STYLE(8),
    REPORTS_VEHICLES_ODOMETER(9),
    
    
    NOTIFICATIONS_REDFLAGS_LEVEL(1),
    NOTIFICATIONS_REDFLAGS_ALERT_DETAILS(2),
    NOTIFICATIONS_REDFLAGS_DATE_TIME(3),
    NOTIFICATIONS_REDFLAGS_GROUP(4),
    NOTIFICATIONS_REDFLAGS_DRIVER(5),
    NOTIFICATIONS_REDFLAGS_VEHICLE(6),
    NOTIFICATIONS_REDFLAGS_CATEGORY(7),
    NOTIFICATIONS_REDFLAGS_DETAIL(8),
    
    
    NOTIFICATIONS_GENERAL_DATE_TIME(1),
    NOTIFICATIONS_GENERAL_GROUP(2),
    NOTIFICATIONS_GENERAL_DRIVER(3),
    NOTIFICATIONS_GENERAL_VEHICLE(4),
    NOTIFICATIONS_GENERAL_CATEGORY(5),
    NOTIFICATIONS_GENERAL_DETAIL(6),
    
    NOTIFICATIONS_CRASHHISTORY_DATE_TIME(1),
    NOTIFICATIONS_CRASHHISTORY_GROUP(2),
    NOTIFICATIONS_CRASHHISTORY_DRIVER(3),
    NOTIFICATIONS_CRASHHISTORY_VEHICLE(4),
    NOTIFICATIONS_CRASHHISTORY_NUMBER_OF_OCCUPANTS(5),
    NOTIFICATIONS_CRASHHISTORY_STATUS(6),
    NOTIFICATIONS_CRASHHISTORY_WEATHER(7),
    
    HOS_DRIVERLOGS_DATE_TIME(1),
    HOS_DRIVERLOGS_DRIVER(2),
    HOS_DRIVERLOGS_VEHICLE(3),
    HOS_DRIVERLOGS_TRAILER(4),
    HOS_DRIVERLOGS_SERVICE(5),
    HOS_DRIVERLOGS_LOCATION(6),
    HOS_DRIVERLOGS_EDITED(7),
    HOS_DRIVERLOGS_STATUS(8),
    
    
    HOS_FUELSTOPS_DATE_TIME(1),
    HOS_FUELSTOPS_DRIVER(2),
    HOS_FUELSTOPS_VEHICLE(3),
    HOS_FUELSTOPS_TRAILER(4),
    HOS_FUELSTOPS_SERVICE(5),
    HOS_FUELSTOPS_LOCATION(6),
    HOS_FUELSTOPS_VEHICLE_FUEL(7),
    HOS_FUELSTOPS_TRAILER_FUEL(8),
    HOS_FUELSTOPS_EDITED(9),
    
    
    
    ;
    
    private final Integer index;
    
    private EditColumnsEnums(int index){
        this.index = index;
    }

    @Override
    public Integer getIndex() {
        return index;
    }
    
    @Override
    public String toString(){
        return AutomationStringUtil.captalizeEnumName(name());
    }
    
    private static final Map<Tabs, Set<EditColumnsEnums>> pages = new HashMap<Tabs, Set<EditColumnsEnums>>();
    
    static {
        
        for (EditColumnsEnums column : EnumSet.allOf(EditColumnsEnums.class)){
            for (Tabs tab: EnumSet.allOf(Tabs.class)){
                if (column.name().startsWith(tab.name())){
                    if (!pages.containsKey(tab)){
                        pages.put(tab, new HashSet<EditColumnsEnums>());
                    }
                    
                    pages.get(tab).add(column);
                }
            }
        }
    }
    
    public static Set<EditColumnsEnums> enumsByPage(Tabs tab){
        return pages.get(tab);
    }
    
    public static enum Tabs{
        REPORTS_DRIVERS,
        REPORTS_DEVICES,
        REPORTS_IDLING,
        REPORTS_VEHICLES,
        NOTIFICATIONS_REDFLAGS,
        NOTIFICATIONS_GENERAL,
        
        
    }

}
