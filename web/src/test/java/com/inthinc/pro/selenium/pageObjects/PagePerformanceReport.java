package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Selector;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLabelDropDown;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.ReportsPerformanceEnum;


public class PagePerformanceReport extends ReportsBar {
    
    public PagePerformanceReport() {
        checkMe.add(ReportsPerformanceEnum.REPORT_DROP_DOWN);
        checkMe.add(ReportsPerformanceEnum.TITLE);
    }
    
    public class PerformanceReportLinks extends ReportsBarLinks {
        
                           
            public TextLink excel(){
                return new TextLink(ReportsPerformanceEnum.EXCEL);
            }
        
            public TextLink email(){
                return new TextLink(ReportsPerformanceEnum.EMAIL);
            }
        }

    public class PerformanceReportTexts extends ReportsBarTexts{
    
            
        public Text labelReport(){
            return new TextLabelDropDown(ReportsPerformanceEnum.REPORT_DROP_DOWN);
        }
    
        public Text labelGroups(){
            return new Text(ReportsPerformanceEnum.GROUP_LABEL);
        }
        
    }
         
    public class PerformanceReportDropDowns extends ReportsBarDropDowns{
        
        public DropDown report(){
            return new DropDown(ReportsPerformanceEnum.REPORT_DROP_DOWN);
        }
        
        public Selector group(){
            return new Selector(ReportsPerformanceEnum.GROUP_SELECTOR);
        }
        
        public Selector timeFrame(){
            return new Selector(ReportsPerformanceEnum.TIME_FRAME_DROP_DOWN);
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
        return ReportsPerformanceEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _dropDown().report().isPresent();
    }
}
