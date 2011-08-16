package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextLabelDropDown;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.WaysmartReportEnum;

public class PageWaysmartReport extends ReportsBar {
    
    public PageWaysmartReport() {
        url = WaysmartReportEnum.DEFAULT_URL;
        checkMe.add(WaysmartReportEnum.REPORT_DROP_DOWN);
        checkMe.add(WaysmartReportEnum.TITLE);
    }
    
    public class WaysmartReportLinks extends NavigationBarLinks {
        
            public TextLink html(){
                return new TextLink(WaysmartReportEnum.HTML);
            }
        
            public TextLink pdf(){
                return new TextLink(WaysmartReportEnum.PDF);
            }
        
            public TextLink excel(){
                return new TextLink(WaysmartReportEnum.EXCEL);
            }
        
            public TextLink email(){
                return new TextLink(WaysmartReportEnum.EMAIL);
            }
        }

    public class WaysmartReportTexts extends NavigationBarTexts{
    
        public Text labelReport(){
            return new TextLabelDropDown(WaysmartReportEnum.REPORT_DROP_DOWN);
        }
    
        public TextFieldLabel labelDateRange(){
            return new TextFieldLabel(WaysmartReportEnum.START_DATE);
        }
    
        public Text labelReportOn(){
            return new TextLabelDropDown(WaysmartReportEnum.REPORT_ON_DROP_DOWN);
        }
    
        public Text labelGroups(){
            return new Text(WaysmartReportEnum.GROUP_LABEL);
        }
    }
    public class WaysmartReportTextFields extends NavigationBarTextFields{
        public TextField startDate(){
            return new TextField(WaysmartReportEnum.START_DATE);
        }
    
        public TextField stopDate(){
            return new TextField(WaysmartReportEnum.STOP_DATE);
        }
    }
}
