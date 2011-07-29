package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum DOTTimeRemainingTableEnum implements SeleniumEnums {
    
    GROUP_NAME("Group Name", "hosReports_tableForm:hosReports_viewTable:###:column0"),
    DRIVER("Driver", "hosReports_tableForm:hosReports_viewTable:###:column1"),
    DOT_TYPE("DOT Type", "hosReports_tableForm:hosReports_viewTable:###:column2"),
    HOURS_REMAINING_TODAY("Hours Remaining Today", "hosReports_tableForm:hosReports_viewTable:###:column3"),
    CUMULATIVE_HOURS_REMAINING("Cumulative Hours Remaining", "hosReports_tableForm:hosReports_viewTable:###:column4"),
    
    COLUMN_1_TIME(null, "//table[@id='hosReports_tableForm:hosReports_viewTable']/thead/tr/td[2]/div"),
    COLUMN_2_TIME(null, "//table[@id='hosReports_tableForm:hosReports_viewTable']/thead/tr/td[3]/div"),
    COLUMN_3_TIME(null, "//table[@id='hosReports_tableForm:hosReports_viewTable']/thead/tr/td[4]/div"),
    COLUMN_4_TIME(null, "//table[@id='hosReports_tableForm:hosReports_viewTable']/thead/tr/td[5]/div"),
    COLUMN_5_TIME(null, "//table[@id='hosReports_tableForm:hosReports_viewTable']/thead/tr/td[6]/div"),
    COLUMN_6_TIME(null, "//table[@id='hosReports_tableForm:hosReports_viewTable']/thead/tr/td[7]/div"),
    COLUMN_7_TIME(null, "//table[@id='hosReports_tableForm:hosReports_viewTable']/thead/tr/td[8]/div"),
    COLUMN_8_TIME(null, "//table[@id='hosReports_tableForm:hosReports_viewTable']/thead/tr/td[9]/div"),
    COLUMN_9_TIME(null, "//table[@id='hosReports_tableForm:hosReports_viewTable']/thead/tr/td[10]/div"),
    COLUMN_10_TIME(null, "//table[@id='hosReports_tableForm:hosReports_viewTable']/thead/tr/td[11]/div"),
    COLUMN_11_TIME(null, "//table[@id='hosReports_tableForm:hosReports_viewTable']/thead/tr/td[12]/div"),
    COLUMN_12_TIME(null, "//table[@id='hosReports_tableForm:hosReports_viewTable']/thead/tr/td[13]/div"),
    COLUMN_13_TIME(null, "//table[@id='hosReports_tableForm:hosReports_viewTable']/thead/tr/td[14]/div"),
    COLUMN_14_TIME(null, "//table[@id='hosReports_tableForm:hosReports_viewTable']/thead/tr/td[15]/div"),
    
    
    
    COLUMN_1_DRIVING("Driving", "hosReports_tableForm:hosReports_viewTable:###:column5"),
    COLUMN_1_ON_DUTY_NOT_DRIVING("On Duty Not Driving", "hosReports_tableForm:hosReports_viewTable:###:column6"),
    

    COLUMN_2_DRIVING("Driving", "hosReports_tableForm:hosReports_viewTable:###:column7"),
    COLUMN_2_ON_DUTY_NOT_DRIVING("On Duty Not Driving", "hosReports_tableForm:hosReports_viewTable:###:column8"),
    

    COLUMN_3_DRIVING("Driving", "hosReports_tableForm:hosReports_viewTable:###:column9"),
    COLUMN_3_ON_DUTY_NOT_DRIVING("On Duty Not Driving", "hosReports_tableForm:hosReports_viewTable:###:column10"),
    

    COLUMN_4_DRIVING("Driving", "hosReports_tableForm:hosReports_viewTable:###:column11"),
    COLUMN_4_ON_DUTY_NOT_DRIVING("On Duty Not Driving", "hosReports_tableForm:hosReports_viewTable:###:column12"),
    
    
    COLUMN_5_DRIVING("Driving", "hosReports_tableForm:hosReports_viewTable:###:column13"),
    COLUMN_5_ON_DUTY_NOT_DRIVING("On Duty Not Driving", "hosReports_tableForm:hosReports_viewTable:###:column14"),
    

    COLUMN_6_DRIVING("Driving", "hosReports_tableForm:hosReports_viewTable:###:column15"),
    COLUMN_6_ON_DUTY_NOT_DRIVING("On Duty Not Driving", "hosReports_tableForm:hosReports_viewTable:###:column16"),
    

    COLUMN_7_DRIVING("Driving", "hosReports_tableForm:hosReports_viewTable:###:column17"),
    COLUMN_7_ON_DUTY_NOT_DRIVING("On Duty Not Driving", "hosReports_tableForm:hosReports_viewTable:###:column18"),
    

    COLUMN_8_DRIVING("Driving", "hosReports_tableForm:hosReports_viewTable:###:column19"),
    COLUMN_8_ON_DUTY_NOT_DRIVING("On Duty Not Driving", "hosReports_tableForm:hosReports_viewTable:###:column20"),
    
    COLUMN_9_DRIVING("Driving", "hosReports_tableForm:hosReports_viewTable:###:column21"),
    COLUMN_9_ON_DUTY_NOT_DRIVING("On Duty Not Driving", "hosReports_tableForm:hosReports_viewTable:###:column22"),
    

    COLUMN_10_DRIVING("Driving", "hosReports_tableForm:hosReports_viewTable:###:column23"),
    COLUMN_10_ON_DUTY_NOT_DRIVING("On Duty Not Driving", "hosReports_tableForm:hosReports_viewTable:###:column24"),
    

    COLUMN_11_DRIVING("Driving", "hosReports_tableForm:hosReports_viewTable:###:column25"),
    COLUMN_11_ON_DUTY_NOT_DRIVING("On Duty Not Driving", "hosReports_tableForm:hosReports_viewTable:###:column26"),
    

    COLUMN_12_DRIVING("Driving", "hosReports_tableForm:hosReports_viewTable:###:column27"),
    COLUMN_12_ON_DUTY_NOT_DRIVING("On Duty Not Driving", "hosReports_tableForm:hosReports_viewTable:###:column28"),
    
    
    COLUMN_13_DRIVING("Driving", "hosReports_tableForm:hosReports_viewTable:###:column29"),
    COLUMN_13_ON_DUTY_NOT_DRIVING("On Duty Not Driving", "hosReports_tableForm:hosReports_viewTable:###:column30"),
    

    COLUMN_14_DRIVING("Driving", "hosReports_tableForm:hosReports_viewTable:###:column31"),
    COLUMN_14_ON_DUTY_NOT_DRIVING("On Duty Not Driving", "hosReports_tableForm:hosReports_viewTable:###:column32"),
    
    
    ;

    private String text, url;
    private String[] IDs;
    
    private DOTTimeRemainingTableEnum(String url){
        this.url = url;
    }
    private DOTTimeRemainingTableEnum(String text, String ...IDs){
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
