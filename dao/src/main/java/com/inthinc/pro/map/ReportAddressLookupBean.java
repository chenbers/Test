package com.inthinc.pro.map;

import com.inthinc.pro.map.AddressLookup;

public class ReportAddressLookupBean {
    
    private Boolean enableGoogleMapsInReports = Boolean.TRUE; //defaults to true
    private AddressLookup reportAddressLookupBean;
    private AddressLookup disabledGoogleMapsInReportsAddressLookupBean;

    public AddressLookup getAddressLookup() {
        return enableGoogleMapsInReports?reportAddressLookupBean:disabledGoogleMapsInReportsAddressLookupBean;
    }
    
    public Boolean getEnableGoogleMapsInReports() {
        return enableGoogleMapsInReports;
    }
    public void setEnableGoogleMapsInReports(Boolean enableGoogleMapsInReports) {
        this.enableGoogleMapsInReports = enableGoogleMapsInReports;
    }
    public AddressLookup getReportAddressLookupBean() {
        return reportAddressLookupBean;
    }
    public void setReportAddressLookupBean(AddressLookup reportAddressLookupBean) {
        this.reportAddressLookupBean = reportAddressLookupBean;
    }
    public AddressLookup getDisabledGoogleMapsInReportsAddressLookupBean() {
        return disabledGoogleMapsInReportsAddressLookupBean;
    }
    public void setDisabledGoogleMapsInReportsAddressLookupBean(AddressLookup disabledGoogleMapsInReportsAddressLookupBean) {
        this.disabledGoogleMapsInReportsAddressLookupBean = disabledGoogleMapsInReportsAddressLookupBean;
    }
    
    
    
    

}
