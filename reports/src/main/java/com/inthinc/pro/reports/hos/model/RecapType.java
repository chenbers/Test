package com.inthinc.pro.reports.hos.model;

public enum RecapType {
    NONE(0, ""),
    US(1, "US"),
    CANADA(2, "Canada"),
    CANADA_2007(3, "Canada2007");
    
    Integer code;
    String name;
    
    private RecapType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
    
    public String toString() {
        return name;
    }
    
    
}
