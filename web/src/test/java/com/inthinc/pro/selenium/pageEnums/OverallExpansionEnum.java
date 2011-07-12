package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum OverallExpansionEnum implements SeleniumEnums {
    
    TITLE("Overall Score", "//span[@class='overall']"),
    DURATION(null, "details_form:details-***"),
    TOOLS(null, "details_toolImage"),
    EMAIL_REPORT(emailReport, "details_detail_form:details_email:anchor"),
    EXPORT_TO_PDF(exportPDF, "details_detail_form:details-export:anchor"),
    RESTORE(null, "details_details"),
    
    OVERALL_SCORE(null, "//div[@id='overallScore_body']/div/div[@class='middle']"),
    
    LABEL_SCORE("OVERALL SCORE", "//div[@id='overallScore_body']/div/div[@class='middle']/text()"),
    LABEL_STYLE("Driving Style", "//td[1]/div[@class='piegrid_label']"),
    LABEL_SEATBELT("Seatbelt","//td[1]/div[@class='piegrid_label']"),
    LABEL_SPEEDING("Speeding","//td[1]/div[@class='piegrid_label']"),
    
    
    ;

    private String text, url;
    private String[] IDs;

    private OverallExpansionEnum(String url) {
        this.url = url;
    }

    private OverallExpansionEnum(String text, String... IDs) {
        this.text = text;
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
