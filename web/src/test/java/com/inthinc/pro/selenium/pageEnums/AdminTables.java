package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.IndexEnum;
import com.inthinc.pro.automation.interfaces.TextEnum;

public class AdminTables {
    
    public enum DeviceColumns implements TextEnum, IndexEnum {
        DEVICE_ID("name", 1),
        VEHICLE_ID("vehicle", 2),
        IMEI("imei", 3),
        SIM_CARD("sim", 4),
        SERIAL_NUMBER("serialNumber", 5),
        DEVICE_PHONE("phone", 6),
        STATUS("status", 7),
        MCM_ID("mcmid", 8),
        ALTERNATE_IMEI("altimei", 9),
        PRODUCT("productVersion", 10),

        ;

        private String text;
        private Integer index;

        private DeviceColumns(String text, int index) {
            this.text = text;
            this.index = index;
        }

        @Override
        public String getText() {
            return text;
        }
        
        @Override
        public Integer getIndex(){
            return index;
        }
    }

    public enum VehicleColumns implements TextEnum, IndexEnum {
        VEHICLE_ID("name", 1),
        DRIVER("fullName", 2),
        TEAM("group", 3),
        YEAR("year", 4),
        MAKE("make", 5),
        MODEL("model", 6),
        COLOR("color", 7),
        ZONE_TYPE("vtype", 8),
        VIN("vin", 9),
        WEIGHT("weight", 10),
        LICENSE_NUMBER("license", 11),
        STATE("state", 12),
        STATUS("status", 13),
        DEVICE("device", 14),
        ODOMETER("odometer", 15),
        E_CALL_PHONE("ePhone", 16),
        DOT("DOT", 17),
        IFTA("IFTA", 18),
        PRODUCT("productVersion", 19),
        EDIT("edit"),
        
        ;

        private String text;
        private Integer index;
        
        private VehicleColumns(String text, int index){
            this.text = text;
            this.index = index;
        }

        private VehicleColumns(String text) {
            this.text = text;
        }

        @Override
        public String getText() {
            return text;
        }
        
        @Override
        public Integer getIndex(){
            return index;
        }
    }

    public enum UserColumns implements TextEnum, IndexEnum {
        /* Person Attributes */
        FULL_NAME("name", 1),
        USER_STATUS("status", 2),
        USER_NAME("username", 3),
        GROUP("groupName", 4),
        ROLES("role", 5),
        PHONE_1("priPhone", 6),
        PHONE_2("secPhone", 7),
        EMAIL_1("priEmail", 8),
        EMAIL_2("secEmail", 9),
        TEXT_MESSAGE_1("priText", 10),
        TEXT_MESSAGE_2("secText", 11),
        INFORMATION("info", 12),
        WARNING("warn", 13),
        CRITICAL("crit", 14),
        TIME_ZONE("timeZone", 15),
        EMPLOYEE_ID("empId", 16),
        REPORTS_TO("reportsTo", 17),
        TITLE("title", 18),
        DOB_MAIN("dob", 19),
        GENDER("gender", 20),
        BAR_CODE("barcode", 21),
        RFID1("rfid1", 22),
        RFID2("rfid2", 23),
        FOBID("fobID", 24),
        LOCALE("displayName", 25),        
        MEASUREMENT("measurementType", 26),
        FUEL_RATIO("fuelEfficiencyType", 27),
        DRIVER_STATUS("description", 28),
        LICENSE_NUMBER("license", 29),
        CLASS("licenseClass", 30),
        STATE("state", 31),
        EXPIRATION_MAIN("expiration", 32),
        CERTIFICATIONS("certification", 33),
        DOT("dot", 34),
        TEAM("teamName", 35),
        
        /* Non table enums */ 
        FIRST_NAME("first"),
        MIDDLE_NAME("middle"),
        LAST_NAME("last"),
        SUFFIX("suffix"),
        DOB_ADD_EDIT("dobInputDate"),
        PASSWORD("password"),
        PASSWORD_AGAIN("confirmPassword"),
        EXPIRATION_ADD_EDIT("expirationInputDate")
        ;

        private String text;
        private int index;
        
        private UserColumns(String text, int index){
            this(text);
            this.index = index;
        }

        private UserColumns(String text) {
            this.text = text;
        }

        @Override
        public String getText() {
            return text;
        }
        
        @Override
        public Integer getIndex() {
            return index;
        }
    }
    
    public enum RedFlagColumns implements TextEnum, IndexEnum {
        
        RED_FLAG("name", 1),
        DESCRIPTION("description", 2),
        TYPE("type", 3),
        STATUS("status", 4),
        ZONE("zoneName", 5),
        OWNER("owner", 6),

        ;

        private String text;
        private Integer index;

        private RedFlagColumns(String text, int index) {
            this.text = text;
            this.index = index;
        }

        @Override
        public String getText() {
            return text;
        }
        
        @Override
        public Integer getIndex(){
            return index;
        }
    }
    
    public enum ReportsColumns implements TextEnum, IndexEnum {
        NAME("name", 1),
        OCCURRENCE("occurrence", 2),
        LAST_SENT("lastEmail", 3),
        REPORT("report", 4),
        STATUS("status", 5),
        OWNER("owner", 6),
        EDIT("edit"),
        
        ;
        
        private String text;
        private Integer index;
        
        private ReportsColumns(String text, Integer index){
            this.text = text;
            this.index = index;
        }
        
        private ReportsColumns(String text){
            this.text = text;
        }

        @Override
        public Integer getIndex() {
            return index;
        }

        @Override
        public String getText() {
            return text;
        }
    }
    
    
    public enum CustomRoles implements TextEnum, IndexEnum {
        ROLE_NAME("name", 1),
        
        ;
        
        private String text;
        private Integer index;
        
        private CustomRoles(String text, int index){
            this.text = text;
            this.index = index;
        }

        @Override
        public Integer getIndex() {
            return index;
        }

        @Override
        public String getText() {
            return text;
        }
        
        
    }

}
