package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.SimpleName;
@XmlRootElement
@SimpleName(simpleName="AcctAttr")
public class AccountAttributes extends BaseEntity {
        
    private static final long serialVersionUID = 1L;
    
    private String  wmsURL;
    private String  wmsQuery;
    private String  wmsLayers;
    private String  wmsLayerQueryParam;
    private String  phoneAlertsActive;    
    private String  noReplyEmail;
    @Column(name = "supportPhone1")
    private String  supportContact1;
    @Column(name = "supportPhone2")
    private String  supportContact2;
    private String  supportContact3;
    private String  supportContact4;
    private String  supportContact5;
    private String  waySmart;

    

    @Column(updateable = false)
    private String supportContacts[];

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
    public String getSupportContact1() {
        return supportContact1;
    }
    public void setSupportContact1(String supportContact1) {
        this.supportContact1 = supportContact1;
    }
    public String getSupportContact2() {
        return supportContact2;
    }
    public void setSupportContact2(String supportContact2) {
        this.supportContact2 = supportContact2;
    }
    
    public String getSupportContact3() {
        return supportContact3;
    }
    public void setSupportContact3(String supportContact3) {
        this.supportContact3 = supportContact3;
    }
    public String getSupportContact4() {
        return supportContact4;
    }
    public void setSupportContact4(String supportContact4) {
        this.supportContact4 = supportContact4;
    }
    public String getSupportContact5() {
        return supportContact5;
    }
    public void setSupportContact5(String supportContact5) {
        this.supportContact5 = supportContact5;
    }

    public String[] getSupportContacts() {
        
        supportContacts = new String[5];
        
        supportContacts[0] = (supportContact1 == null ? "" : supportContact1);
        supportContacts[1] = (supportContact2 == null ? "" : supportContact2);
        supportContacts[2] = (supportContact3 == null ? "" : supportContact3);
        supportContacts[3] = (supportContact4 == null ? "" : supportContact4);
        supportContacts[4] = (supportContact5 == null ? "" : supportContact5);
        return supportContacts;
    }


    public void setSupportContacts(String[] supportContacts) {
        this.supportContacts = supportContacts;
        setSupportContact1(supportContacts[0]);
        setSupportContact2(supportContacts[1]);
        setSupportContact3(supportContacts[2]);
        setSupportContact4(supportContacts[3]);
        setSupportContact5(supportContacts[4]);
    }

    public String getWaySmart() {
        if (waySmart == null)
            return "false";
        return waySmart;
    }
    public void setWaySmart(String waySmart) {
        this.waySmart = waySmart;
    }

}
