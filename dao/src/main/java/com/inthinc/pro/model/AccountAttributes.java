package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.SimpleName;

@XmlRootElement
@SimpleName(simpleName="AcctAttr")
public class AccountAttributes extends BaseEntity {
        
    private String  wmsURL;
    private String  wmsQuery;
    private String  wmsLayers;
    private String  wmsLayerQueryParam;
//    private Boolean phoneAlertsActive;
    private String  phoneAlertsActive;    
    private String  noReplyEmail;
    private String  supportPhone1;
    private String  supportPhone2;
    
    public String getWmsURL() {
        return wmsURL;
    }
    public void setWmsURL(String wmsURL) {
        this.wmsURL = wmsURL;
    }
    public String getWmsQuery() {
        return wmsQuery;
    }
    public void setWmsQuery(String wmsQuery) {
        this.wmsQuery = wmsQuery;
    }
    public String getWmsLayers() {
        return wmsLayers;
    }
    public void setWmsLayers(String wmsLayers) {
        this.wmsLayers = wmsLayers;
    }
    public String getWmsLayerQueryParam() {
        return wmsLayerQueryParam;
    }
    public void setWmsLayerQueryParam(String wmsLayerQueryParam) {
        this.wmsLayerQueryParam = wmsLayerQueryParam;
    }
    public String getPhoneAlertsActive() {
        return phoneAlertsActive;
    }
    public void setPhoneAlertsActive(String phoneAlertsActive) {
        this.phoneAlertsActive = phoneAlertsActive;
    }    
    public String getNoReplyEmail() {
        return noReplyEmail;
    }
    public void setNoReplyEmail(String noReplyEmail) {
        this.noReplyEmail = noReplyEmail;
    }
    public String getSupportPhone1() {
        return supportPhone1;
    }
    public void setSupportPhone1(String supportPhone1) {
        this.supportPhone1 = supportPhone1;
    }
    public String getSupportPhone2() {
        return supportPhone2;
    }
    public void setSupportPhone2(String supportPhone2) {
        this.supportPhone2 = supportPhone2;
    }     
}
