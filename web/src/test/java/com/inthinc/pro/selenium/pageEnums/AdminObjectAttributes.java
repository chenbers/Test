package com.inthinc.pro.selenium.pageEnums;

public class AdminObjectAttributes {
    
    public interface AdminStuff{
        public String tableName();
        public String addEditName();
        
        
    }
    
    
    public enum Person implements AdminStuff{
        FULL_NAME("name"),
        
        FIRST_NAME("first"),
        MIDDLE_NAME("middle"),
        LAST_NAME("last"),
        SUFFIX("suffix"),
        DOB("dob", "InputDate"),
        EMP_ID("empId"),
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
        ;

        private String idName, addEditName;
        
        private Person(String idName){
            this.idName=idName;
            this.addEditName=idName;
        }
        
        private Person(String idName, String addEditName){
            this.idName=idName;
            this.addEditName="user_"+idName+addEditName;
        }
        
        public String tableName() {
            return this.idName;
        }
        public String addEditName() {
            return addEditName;
        }
    }
    
    public enum User implements AdminStuff{
        USER_NAME("username"),
        PASSWORD("password"),
        PASSWORD_AGAIN("confirmPassword"),
        GROUP("groupName"),
        ROLES("role"),
        USER_STATUS("description"),
        ;
        

        private String idName, addEditName;
        
        private User(String idName){
            this.idName=idName;
            this.addEditName="user_"+idName;
        }
        
        private User(String idName, String addEditName){
            this.idName=idName;
            this.addEditName="user_"+idName+addEditName;
        }
        
        public String tableName() {
            return this.idName;
        }
        public String addEditName() {
            return addEditName;
        }
    }
    
    public enum Driver implements AdminStuff{
        LICENSE_NUMBER("license"),
        CLASS("licenseClass"),
        STATE("state"),
        EXPIRATION("expiration", "InputDate"),
        CERTIFICATIONS("certification"),
        DOT("dot"),
        TEAM("teamName"),
        STATUS("description"),
        BAR_CODE("barcode"),
        RFID1("rfid1"),
        RFID2("rfid2"),
        ;
        

        private String idName, addEditName;
        
        private Driver(String idName){
            this.idName=idName;
            this.addEditName="driver_"+idName;
        }
        
        private Driver(String idName, String addEditName){
            this.idName=idName;
            this.addEditName="driver_"+idName+addEditName;
        }
        
        public String tableName() {
            return this.idName;
        }
        public String addEditName() {
            return addEditName;
        }
    }
}
