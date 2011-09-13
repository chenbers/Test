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
        FULL_NAME("name"),

        FIRST_NAME("first"),
        MIDDLE_NAME("middle"),
        LAST_NAME("last"),
        SUFFIX("suffix"),
        DOB_MAIN("dob"),
        DOB_ADD_EDIT("dobInputDate"),
        EMPLOYEE_ID("empId"),
        REPORTS_TO("reportsTo"),
        TITLE("title"),
        LOCALE("displayName"),
        TIME_ZONE("timeZone"),
        MEASUREMENT("measurementType"),
        FUEL_RATIO("fuelEfficiencyType"),
        GENDER("gender"),
        PHONE_1("priPhone"),
        PHONE_2("secPhone"),
        EMAIL_1("priMail"),
        EMAIL_2("secMail"),
        TEXT_MESSAGE_1("priText"),
        TEXT_MESSAGE_2("secText"),
        INFORMATION("info"),
        WARNING("warn"),
        CRITICAL("crit"),

        /* User Attributes */
        USER_NAME("username"),
        PASSWORD("password"),
        PASSWORD_AGAIN("confirmPassword"),
        GROUP("groupName"),
        ROLES("role"),
        USER_STATUS("description"),

        /* Driver Attributes */
        LICENSE_NUMBER("license"),
        CLASS("licenseClass"),
        STATE("state"),
        EXPIRATION_MAIN("expiration"),
        EXPIRATION_ADD_EDIT("expirationInputDate"),
        CERTIFICATIONS("certification"),
        DOT("dot"),
        TEAM("teamName"),
        STATUS("description"),
        BAR_CODE("barcode"),
        RFID1("rfid1"),
        RFID2("rfid2"),

        ;

        private String text;

        private AdminUsersEntries(String text) {
            this.text = text;
        }

        @Override
        public String getText() {
            return text;
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
