package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.TextEnum;

public class AdminTables {
    
    public enum AdminDevicesEntries implements TextEnum {
        DEVICE_ID("name"),
        VEHICLE_ID("vehicle"),
        IMEI("imei"),
        SIM_CARD("sim"),
        SERIAL_NUMBER("serialNumber"),
        DEVICE_PHONE("phone"),
        STATUS("status"),
        MCM_ID("mcmid"),
        ALTERNATE_IMEI("altimei"),
        PRODUCT("productVersion"),

        ;

        private String text;

        private AdminDevicesEntries(String text) {
            this.text = text;
        }

        @Override
        public String getText() {
            return text;
        }
    }

    public enum AdminVehiclesEntries implements TextEnum {
        VEHICLE_ID("name"),
        DRIVER("fullName"),
        TEAM("group"),
        YEAR("year"),
        MAKE("make"),
        MODEL("model"),
        COLOR("color"),
        ZONE_TYPE("vtype"),
        VIN("vin"),
        WEIGHT("weight"),
        LICENSE_NUMBER("license"),
        STATE("state"),
        STATUS("status"),
        DEVICE("device"),
        ODOMETER("odometer"),
        E_CALL_PHONE("ePhone"),
        DOT("DOT"),
        IFTA("IFTA"),
        PRODUCT("productVersion"),
        EDIT("edit"),
        
        ;

        private String text;

        private AdminVehiclesEntries(String text) {
            this.text = text;
        }

        @Override
        public String getText() {
            return text;
        }
    }

    public enum AdminUsersEntries implements TextEnum {
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
        private int position;
        
        private AdminUsersEntries(String text, int position){
            this(text);
            this.position = position;
        }

        private AdminUsersEntries(String text) {
            this.text = text;
        }

        @Override
        public String getText() {
            return text;
        }
        
        public Integer getPosition() {
            return position;
        }

    }
    
    public enum AdminRedFlagsEntries implements TextEnum {
        
        RED_FLAG("name"),
        DESCRIPTION("description"),
        TYPE("type"),
        STATUS("status"),
        ZONE("zoneName"),
        OWNER("owner"),

        ;

        private String text;

        private AdminRedFlagsEntries(String text) {
            this.text = text;
        }

        @Override
        public String getText() {
            return text;
        }

    }

}
