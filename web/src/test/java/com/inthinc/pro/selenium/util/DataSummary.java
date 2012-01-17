package com.inthinc.pro.selenium.util;

import java.util.Vector;

public class DataSummary extends XAxisInfo{
    
    private Vector<Data> data = new Vector<Data>();
    
    public DataSummary(){
        this.getThirtyDays();
        this.getThreeMonths();
        this.getSixMonths();
        this.getTwelveMonths();
    }

    public Vector<Data> getData() {
        return data;
    }

    public void setData(Vector<Data> data) {
        this.data = data;
    }

}
