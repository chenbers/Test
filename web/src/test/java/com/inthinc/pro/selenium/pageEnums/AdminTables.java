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

        FIRST_NAME("first"),
        MIDDLE_NAME("middle"),
        LAST_NAME("last"),
        SUFFIX("suffix"),
        DOB_MAIN("dob", 15),
        DOB_ADD_EDIT("dobInputDate"),
        EMPLOYEE_ID("empId", 12),
        REPORTS_TO("reportsTo", 13),
        TITLE("title", 14),
        LOCALE("locale", 17),
        TIME_ZONE("timeZone", 11),
        MEASUREMENT("measurementType", 18),
        FUEL_RATIO("fuelEfficiencyType", 19),
        GENDER("gender", 16),
        PHONE_1("priPhone", 2),
        PHONE_2("secPhone", 3),
        EMAIL_1("priEmail", 4),
        EMAIL_2("secEmail", 5),
        TEXT_MESSAGE_1("priText", 6),
        TEXT_MESSAGE_2("secText", 7),
        INFORMATION("info", 8),
        WARNING("warn", 9),
        CRITICAL("crit", 10),

        /* User Attributes */
        USER_NAME("username", 21),
        PASSWORD("password"),
        PASSWORD_AGAIN("confirmPassword"),
        GROUP("groupName", 22),
        ROLES("role", 23),
        USER_STATUS("description", 20),

        /* Driver Attributes */
        LICENSE_NUMBER("license", 25),
        CLASS("licenseClass", 26),
        STATE("state", 27),
        EXPIRATION_MAIN("expiration", 28),
        EXPIRATION_ADD_EDIT("expirationInputDate"),
        CERTIFICATIONS("certification", 29),
        DOT("dot", 30),
        TEAM("teamName", 34),
        STATUS("description", 24),
        BAR_CODE("barcode", 31),
        RFID1("rfid1", 32),
        RFID2("rfid2", 33),

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
