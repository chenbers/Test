package com.inthinc.pro.backing.importer;

import java.util.List;

public class DataRow {
    String label;
    List<String> data;
    public DataRow(String label, List<String> data) {
        super();
        this.label = label;
        this.data = data;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public List<String> getData() {
        return data;
    }
    public void setData(List<String> data) {
        this.data = data;
    }
    
}
