package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Selector;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLabelDropDown;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.PerformanceReportEnum;


public class PagePerformanceReport extends ReportsBar {
    
    public PagePerformanceReport() {
        checkMe.add(PerformanceReportEnum.REPORT_DROP_DOWN);
        checkMe.add(PerformanceReportEnum.TITLE);
    }
    
    public class PerformanceReportLinks extends ReportsBarLinks {
        
                           
            public TextLink excel(){
                return new TextLink(PerformanceReportEnum.EXCEL);
            }
        
            public TextLink email(){
                return new TextLink(PerformanceReportEnum.EMAIL);
            }
        }

    public class PerformanceReportTexts extends ReportsBarTexts{
    
            
        public Text labelReport(){
            return new TextLabelDropDown(PerformanceReportEnum.REPORT_DROP_DOWN);
        }
    
        public Text labelGroups(){
            return new Text(PerformanceReportEnum.GROUP_LABEL);
        }
        
    }
         
    public class PerformanceReportDropDowns extends ReportsBarDropDowns{
        
        public DropDown report(){
            return new DropDown(PerformanceReportEnum.REPORT_DROP_DOWN);
        }
        
        public Selector group(){
            return new Selector(PerformanceReportEnum.GROUP_SELECTOR);
        }
        
        public Selector timeFrame(){
            return new Selector(PerformanceReportEnum.TIME_FRAME_DROP_DOWN);
        }

    }
    public PerformanceReportLinks _link() {
        return new PerformanceReportLinks();
    }

    public PerformanceReportTexts _text() {
        return new PerformanceReportTexts();
    }

     
    public PerformanceReportDropDowns _dropDown() {
        return new PerformanceReportDropDowns();
    }

    @Override
    public SeleniumEnums setUrl() {
        return PerformanceReportEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        // TODO Auto-generated method stub
        return false;
    }
}
