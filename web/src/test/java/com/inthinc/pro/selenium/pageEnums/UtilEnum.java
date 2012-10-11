package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum UtilEnum implements SeleniumEnums {

    MY_ACCOUNT_URL(appUrl + "/tiwiproutil"),

    METHOD_DROPDOWN("Method:", "//select[@id='method-select-form:method-select-menu']"),
    ID_FIELD("ID:", "paramsPopulateForm:populateID"),
    ACCOUNTID_FIELD("accountID", "paramsForm:param:0:value"),
    DEVICEID_FIELD("deviceID", "paramsForm:param:1:value"),
    ACCTID_FIELD("acctID", "paramsForm:param:2:value"),
    STATUS_FIELD("status", "paramsForm:param:3:value"),
    NAME_FIELD("name", "paramsForm:param:4:value"),
    IMEI_FIELD("imei", "paramsForm:param:5:value"),
    SIM_FIELD("sim", "paramsForm:param:6:value"),
    SERIALNUM_FIELD("serialnum", "paramsForm:param:7:value"),
    PHONE_FIELD("phone", "paramsForm:param:8:value"),
    ACTIVATED_FIELD("activated", "paramsForm:param:9:value"),
    BASEID_FIELD("baseID", "paramsForm:param:10:value"),
    PRODUCTVER_FIELD("productVer", "paramsForm:param:11:value"),
    EMUMD5_FIELD("emuMd5", "paramsForm:param:12:value"),
    PRODUCTVERSION_FIELD("productVersion", "paramsForm:param:13:value"),
    MCMID_FIELD("mcmid", "paramsForm:param:14:value"),
    ALTIMEI_FIELD("altImei", "paramsForm:param:15:value"),
    POPULATE_BUTTON("Populate", "paramsPopulateForm:populate"),
    RUN_BUTTON("run", "paramsForm:query")
    ;

    private String text, url;
    private String[] IDs;
    
    private UtilEnum(String url){
    	this.url = url;
    }
    private UtilEnum(String text, String ...IDs){
        this.text=text;
    	this.IDs = IDs;
    }

    @Override
    public String[] getIDs() {
        return IDs;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getURL() {
        return url;
    }

}
