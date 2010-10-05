package com.inthinc.pro.reports;

public enum ReportCategory {

        DOT_IFTA(1, "DOT/IFTA"),
        Performance(2, "Performance"),
        Asset(3, "Asset");
        
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
