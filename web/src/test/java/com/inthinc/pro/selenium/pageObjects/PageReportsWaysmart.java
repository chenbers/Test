package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CalendarObject;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Selector;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextDateFieldLabel;
import com.inthinc.pro.automation.elements.TextLabelDropDown;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.ReportsWaysmartEnum;

public class PageReportsWaysmart extends ReportsBar {
    
    public PageReportsWaysmart() {
        checkMe.add(ReportsWaysmartEnum.REPORT_DROP_DOWN);
        checkMe.add(ReportsWaysmartEnum.TITLE);
    }
    
    public class WaysmartReportLinks extends NavigationBarLinks {
        
            public TextLink html(){
                return new TextLink(ReportsWaysmartEnum.HTML);
            }
        
            public TextLink pdf(){
                return new TextLink(ReportsWaysmartEnum.PDF);
            }
        
            public TextLink excel(){
                return new TextLink(ReportsWaysmartEnum.EXCEL);
            }
        
            public TextLink email(){
                return new TextLink(ReportsWaysmartEnum.EMAIL);
            }
        }

    public class WaysmartReportTexts extends NavigationBarTexts{
    
        public TextDateFieldLabel labelDateRange(){
            return new TextDateFieldLabel(ReportsWaysmartEnum.START_DATE);
        }
    
        public Text labelReportOn(){
            return new TextLabelDropDown(ReportsWaysmartEnum.REPORT_ON_DROP_DOWN);
        }
    
        public Text labelGroups(){
            return new Text(ReportsWaysmartEnum.GROUP_LABEL);
        }
        
    }
    public class WaysmartReportTextFields extends NavigationBarTextFields{}
    

    

    
    public class WaysmartReportDropDowns extends NavigationBarDropDowns{
        
        public DropDown report(){
            return new DropDown(ReportsWaysmartEnum.REPORT_DROP_DOWN);
        }
        
        public Selector group(){
            return new Selector(ReportsWaysmartEnum.GROUP_SELECTOR);
        }
        
        public DropDown reportOnDriver(){
            return new DropDown(ReportsWaysmartEnum.REPORT_ON_DRIVER_DROP_DOWN);
        }
        
        public DropDown driver(){
            return new DropDown(ReportsWaysmartEnum.DRIVER_DROP_DOWN);
        }
        
        public DropDown reportOn(){
            return new DropDown(ReportsWaysmartEnum.REPORT_ON_DROP_DOWN);
        }
        
        public CalendarObject startDate(){
            return new CalendarObject(ReportsWaysmartEnum.START_DATE);
        }
    
        public CalendarObject stopDate(){
            return new CalendarObject(ReportsWaysmartEnum.STOP_DATE);
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
        return ReportsWaysmartEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _dropDown().report().isPresent();
    }
}
