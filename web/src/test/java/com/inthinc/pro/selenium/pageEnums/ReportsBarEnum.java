package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum ReportsBarEnum implements SeleniumEnums {
    DRIVERS("Drivers", "//div[@class='sub_nav-bg']/ul/li[1]/a"),
    VEHICLES("Vehicles", "//div[@class='sub_nav-bg']/ul/li[2]/a"),
    IDLING("Idling", "//div[@class='sub_nav-bg']/ul/li[3]/a"),
    DEVICES("Devices", "//div[@class='sub_nav-bg']/ul/li[4]/a"),
    WAYSMART("waySmart", "//div[@class='sub_nav-bg']/ul/li[5]/a"),
    
    EDIT_COLUMNS("Edit Columns", "***-form:***EditColumns"),
    EDIT_COLUMNS_CHECKBOX(null, "editColumnsForm:***-editColumnsGrid:###:***-col"),
    EDIT_COLUMNS_LABEL(null, "editColumnsForm:***-editColumnsGrid:###"),
    EDIT_COLUMNS_SAVE(save, "editColumnsForm:***-editColumnsPopupSave"),
    EDIT_COLUMNS_CANCEL(cancel, "editColumnsForm:***-editColumnsPopupCancel"),
    EDIT_COLUMNS_X(null, Xpath.start().div(Id.id("editColumnsContentDiv")).div().img().toString()),
    EDIT_COLUMNS_HEADER("The selected columns will be displayed.", "//table[@id='editColumnsContentTable']/tbody/tr/td/div[@class='popupsubtitle']"),
    EDIT_COLUMNS_TITLE("Edit Columns", "editColumnsHeader"),
    
    EMAIL_REPORT_SEND(email, "***_reportEmailModal_form:emailReportPopupEmail3"),
    EMAIL_REPORT_CANCEL(cancel, "emailReportPopUpSubmit"),
    EMAIL_REPORT_TEXTFIELD(null, "***_reportEmailModal_form:***_reportEmailModal_email"),
    EMAIL_REPORT_X(null, "//div[@id='drivers_reportEmailModalContentDiv']/div/img"),
    EMAIL_REPORT_TITLE("E-mail this report to the following e-mail addresses.", "//table[@id='drivers_reportEmailModalContentTable']/tbody/tr/td/div[@class='popupsubtitle']"),
    EMAIL_REPORT_SUB_TITLE("E-mail Address(es): (e-mail addresses separated by a comma)", "//form[@id='drivers_reportEmailModal_form']/span/table/tbody/tr[1]/td"),
    EMAIL_REPORT_HEADER(null, "***_reportEmailModalHeader"),
    
    OVERALL_SCORE_DHX(null, "***-form:***:overallScoreheader:sortDiv"),
    SPEED_SCORE_DHX(null, "***-form:***:speedScoreheader:sortDiv"),
    STYLE_SCORE_DHX(null, "***-form:***:styleScoreheader:sortDiv"),
    SEATBELT_SCORE_DHX(null, "***-form:***:seatbeltScoreheader:sortDiv"),

    OVERALL_SCORE_ARROW(null, "//div[@id='***-form:***:overallScoreheader:sortDiv']/span/span/span/div/img"),
    SPEED_SCORE_ARROW(null, "//div[@id='***-form:***:speedScoreheader:sortDiv']/span/span/span/div/img"),
    STYLE_SCORE_ARROW(null, "//div[@id='***-form:***:styleScoreheader:sortDiv']/span/span/span/div/img"),
    SEATBELT_SCORE_ARROW(null, "//div[@id='***-form:***:seatbeltScoreheader:sortDiv']/span/span/span/div/img"),
    
    TOOL_BUTTON(null,"***-form:***_reportToolImageId"),
    TOOL_EMAIL("E-mail This Report", "***-form:***-export_menu_item:icon"),
    TOOL_PDF("Export To PDF", "***-form:***-export_menu_item:anchor"),
    TOOL_EXCEL("Export To Excel", "***-form:***-exportExcelMEnuItem:icon"),
    
    COUNTER("Showing XXX to YYY of ZZZ records", "***-form:header"),

    ;

    private String text, url;
    private String[] IDs;
    
    private ReportsBarEnum(String url){
    	this.url = url;
    }
    private ReportsBarEnum(String text, String ...IDs){
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
