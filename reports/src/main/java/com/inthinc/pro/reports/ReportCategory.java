package com.inthinc.pro.reports;

public enum ReportCategory {

        IFTA(1, "IFTA", "International Fuel Tax Agreement"),
        Performance(2, "Performance", "Performance, including payroll"),
        Asset(3, "Asset", "Asset"),
        HOS(4,"HOS", "Hours of Service"),
        DriverPerformance(5, "Driver Performance", "Driver Performance"),
        Mileage(6,"Mileage","Mileage");
        
        /**
         * 
         * @param label Report Title
         * @param code - Code to be stored in the DB
         */
        
        private ReportCategory(Integer code, String label, String description) {
            this.code = code;
            this.label = label;
            this.description = description;
        }
        private Integer code;
        private String label;
        private String description;
        
        
        public Integer getCode() {
            return code;
        }
        
        public String getLabel() {
            return label;
        }
        
        public String getDescription(){
        	return description;
        }
        
        @Override
        public String toString(){
        	return this.getClass().getSimpleName()+"."+this.name();
        }
}
