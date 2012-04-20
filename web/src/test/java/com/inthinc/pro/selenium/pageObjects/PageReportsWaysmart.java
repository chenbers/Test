package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Calendar;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Selector;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextDateFieldLabel;
import com.inthinc.pro.automation.elements.TextLabelDropDown;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.WaysmartReportEnum;

public class PageReportsWaysmart extends ReportsBar {
    
    public PageReportsWaysmart() {
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
    
        public TextDateFieldLabel labelDateRange(){
            return new TextDateFieldLabel(WaysmartReportEnum.START_DATE);
        }
    
        public Text labelReportOn(){
            return new TextLabelDropDown(WaysmartReportEnum.REPORT_ON_DROP_DOWN);
        }
    
        public Text labelGroups(){
            return new Text(WaysmartReportEnum.GROUP_LABEL);
        }
        
    }
    public class WaysmartReportTextFields extends NavigationBarTextFields{}
    

    
    public WaysmartReportDateSelectors _dateSelector(){
        return new WaysmartReportDateSelectors();
    }
    
    public class WaysmartReportDateSelectors{
        public Calendar startDate(){
            return new Calendar(WaysmartReportEnum.START_DATE);
        }
    
        public Calendar stopDate(){
            return new Calendar(WaysmartReportEnum.STOP_DATE);
        }
    }

    
    public class WaysmartReportDropDowns extends NavigationBarDropDowns{
        
        public DropDown report(){
            return new DropDown(WaysmartReportEnum.REPORT_DROP_DOWN);
        }
        
        public Selector group(){
            return new Selector(WaysmartReportEnum.GROUP_SELECTOR);
        }
        
        public DropDown reportOnDriver(){
            return new DropDown(WaysmartReportEnum.REPORT_ON_DRIVER_DROP_DOWN);
        }
        
        public DropDown driver(){
            return new DropDown(WaysmartReportEnum.DRIVER_DROP_DOWN);
        }
        
        public DropDown reportOn(){
            return new DropDown(WaysmartReportEnum.REPORT_ON_DROP_DOWN);
        }
    }

    public WaysmartReportLinks _link() {
        return new WaysmartReportLinks();
    }

    public WaysmartReportTexts _text() {
        return new WaysmartReportTexts();
    }

    public WaysmartReportTextFields _textField() {
        return new WaysmartReportTextFields();
    }
    
    public WaysmartReportDropDowns _dropDown() {
        return new WaysmartReportDropDowns();
    }

    @Override
    public SeleniumEnums setUrl() {
        return WaysmartReportEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        // TODO Auto-generated method stub
        return false;
    }
}
