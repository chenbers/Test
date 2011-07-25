package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Selector;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextLabelDropDown;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.HosReportsEnum;

public class PageHOSReports extends HOSBar {
    

    public PageHOSReports() {
        // TODO Auto-generated constructor stub
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
    public class HOSReportsTextFields extends HOSBarTextFields{
        public TextField startDate(){
            return new TextField(HosReportsEnum.START_DATE);
        }
        
        public TextField stopDate(){
            return new TextField(HosReportsEnum.STOP_DATE);
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
    }
}
