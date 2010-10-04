package com.inthinc.pro.reports;

public enum ReportCategory {

        HOS(1, "HOS category"),
        Waysmart(2, "Waysmart Category"),
        Performance(3, "Performance Category");
        
        /**
         * 
         * @param label Report Title
         * @param code - Code to be stored in the DB
         */
        
        private ReportCategory(Integer code, String label) {
            this.code = code;
            this.label = label;
        }
        private Integer code;
        private String label;
        
        
        public Integer getCode() {
            return code;
        }
        
        public String getLable() {
            return label;
        }
        
        
}
