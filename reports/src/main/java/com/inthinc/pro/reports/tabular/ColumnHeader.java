package com.inthinc.pro.reports.tabular;

public class ColumnHeader {
    
    String header;
    Integer colspan;
    
    
    public ColumnHeader(String header, Integer colspan) {
        super();
        this.header = header;
        this.colspan = colspan;
    }
    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }
    public Integer getColspan() {
        return colspan;
    }
    public void setColspan(Integer colspan) {
        this.colspan = colspan;
    }
}
