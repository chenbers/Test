package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum FuelEfficiencyExpansionEnum implements SeleniumEnums {

    DURATION(null, "details-mpgChart_form:details-mpgChart-***"),
    TOOLS(null, "details-mpgChart_toolImage"),
    EMAIL_REPORT(emailReport, "details-mpgChart_detail_form:details-mpgChart_email:anchor"),
    EXPORT_PDF(exportPDF, "details-mpgChart_detail_form:details-mpgChart-export:anchor"),
    
    RESTORE(null, "details-mpgChart_details"),
    
    TITLE("                 Fuel Efficiency                 ", "//span[@class='gas']"),
    
    SORT_DIVISION_TEAM("Division/Team", "mpgTableForm:details:nameheader:sortDiv"),
    SORT_LIGHT("Light", "mpgTableForm:details:lightheader:sortDiv"),
    SORT_MEDIUM("Medium", "mpgTableForm:details:mediumheader:sortDiv"),
    SORT_HEAVY("Heavy", "mpgTableForm:details:heavyheader:sortDiv"),
    
    ENTRY_DIVISION_TEAM(null, "mpgTableForm:details:###:name"),
    ENTRY_LIGHT(null, "mpgTableForm:details:###:light"),
    ENTRY_MEDIUM(null, "mpgTableForm:details:###:medium"),
    ENTRY_HEAVY(null, "mpgTableForm:details:###:heavy"),
    DEFAULT_URL("app/dashboard/detail/MPG"),
    
    ;

    private String text, url;
    private String[] IDs;
    
    private FuelEfficiencyExpansionEnum(String url){
        this.url = url;
    }
    private FuelEfficiencyExpansionEnum(String text, String ...IDs){
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
