package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Calendar;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Selector;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextLabelDropDown;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.HosReportsEnum;

public class PageHOSReports extends HOSBar {
    

    public PageHOSReports() {
        checkMe.add(HosReportsEnum.REPORT_DROP_DOWN);
        checkMe.add(HosReportsEnum.TITLE);
    }
    
    
    
    public class HOSReportsLinks extends HOSBarLinks{
        
        public TextLink table(){
            return new TextLink(HosReportsEnum.TABLE);
        }
        
        public TextLink html(){
            return new TextLink(HosReportsEnum.HTML);
        }
        
        public TextLink pdf(){
            return new TextLink(HosReportsEnum.PDF);
        }
        
        public TextLink excel(){
            return new TextLink(HosReportsEnum.EXCEL);
        }
        
        public TextLink email(){
            return new TextLink(HosReportsEnum.EMAIL);
        }
    }
    
    public class HOSReportsTexts extends HOSBarTexts{
        
        public Text labelReport(){
            return new TextLabelDropDown(HosReportsEnum.REPORT_DROP_DOWN);
        }
        
        public TextFieldLabel labelDateRange(){
            return new TextFieldLabel(HosReportsEnum.START_DATE);
        }
        
        public Text labelReportOn(){
            return new TextLabelDropDown(HosReportsEnum.REPORT_ON_DROP_DOWN);
        }
        
        public Text labelGroups(){
            return new Text(HosReportsEnum.GROUP_LABEL);
        }
    }
    public class HOSReportsTextFields extends HOSBarTextFields{}
    
    public HosReportsDateSelectors _dateSelector(){
        return new HosReportsDateSelectors();
    }
    
    public class HosReportsDateSelectors{
        
        public Calendar startDate(){
            return new Calendar(HosReportsEnum.START_DATE);
        }
        
        public Calendar stopDate(){
            return new Calendar(HosReportsEnum.STOP_DATE);
        }

    }
    
    public class HOSReportsButtons extends HOSBarButtons{
        
        public Button groupDownArrow(){
            return new Button(HosReportsEnum.GROUP_ARROW);
        }
    }
    public class HOSReportsDropDowns extends HOSBarDropDowns{
        
        public DropDown report(){
            return new DropDown(HosReportsEnum.REPORT_DROP_DOWN);
        }
        
        public DropDown reportOn(){
            return new DropDown(HosReportsEnum.REPORT_ON_DROP_DOWN);
        }
        
        public DropDown reportOnDriver(){
            return new DropDown(HosReportsEnum.REPORT_ON_DRIVER_DROP_DOWN);
        }
        
        public Selector group(){
            return new Selector(HosReportsEnum.GROUP_SELECTOR);
        }
        
        public DropDown driver(){
            return new DropDown(HosReportsEnum.DRIVER_DROP_DOWN);
        }
    }
    public class HOSReportsPopUps extends MastheadPopUps{
        
        public HOSReportsPopUps(){
            super(page, Types.REPORT, 3);
        }
        
        public Email emailReport(){
            return new Email();
        }
    }
    
    
    public class HOSReportsPager{
        public Paging pageIndex(){
            return new Paging();
        }
    }
    
    
    public HOSReportsPager _page(){
        return new HOSReportsPager();
    }
    
    public HOSReportsLinks _link(){
        return new HOSReportsLinks();
    }
    
    public HOSReportsTexts _text(){
        return new HOSReportsTexts();
    }
        
    public HOSReportsButtons _button(){
        return new HOSReportsButtons();
    }
    
    public HOSReportsTextFields _textField(){
        return new HOSReportsTextFields();
    }
    
    public HOSReportsDropDowns _dropDown(){
        return new HOSReportsDropDowns();
    }
    
    public HOSReportsPopUps _popUp(){
        return new HOSReportsPopUps();
    }
    
    public HOSReportsReports _reports(){
        return new HOSReportsReports();
    }
    
    public class HOSReportsReports {
        
        public HOSRecordOfDutyStatus _recordOfDutyStatusReport(){
            return new HOSRecordOfDutyStatus();
        }    
        
        public HOSReportsViolationsSummaryHTML _violationsSummaryReportHTML(){
            return new HOSReportsViolationsSummaryHTML();
        }
        
        public HOSReportsViolationsSummaryTable _violationsSummaryReportTable(){
            return new HOSReportsViolationsSummaryTable();
        }
        
        public HOSReportsDriverDOTLogHTML _driverDOTLogReportHTML(){
            return new HOSReportsDriverDOTLogHTML();
        }
        
        public HOSReportsDriverTODLogReportTable _driverDOTLogReportTable(){
            return new HOSReportsDriverTODLogReportTable();
        }
        
        public HOSReportsViolationsDetailHTML _violationsDetailReportHTML(){
            return new HOSReportsViolationsDetailHTML();
        }
        
        public HOSReportsViolationsDetailTable _violationsDetailReportTable(){
            return new HOSReportsViolationsDetailTable();
        }
        
        public HOSReportsDOTTimeRemainingTable _DOTTimeRemainingTable(){
            return new HOSReportsDOTTimeRemainingTable();
        }
        
        public HOSZeroMilesTable _zeroMilesTable(){
            return new HOSZeroMilesTable();
        }
        
        public HOSEditsTable _HOSEditsTable(){
            return new HOSEditsTable();
        }
        
        public HOSReportsNonDOTViolationsSummaryHTML _nonDOTViolationsSummaryReportHTML(){
            return new HOSReportsNonDOTViolationsSummaryHTML();
        }
        
        public HOSReportsNonDOTViolationsDetail _nonDOTViolationsDetailReportHTML(){
            return new HOSReportsNonDOTViolationsDetail();
        }
    }

    @Override
    public SeleniumEnums setUrl() {
        return HosReportsEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _dropDown().report().isPresent();
    }
}
