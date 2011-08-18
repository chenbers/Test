package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextLabelDropDown;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.WaysmartReportEnum;
import com.inthinc.pro.selenium.pageObjects.Masthead.MastheadPopUps;
import com.inthinc.pro.selenium.pageObjects.Masthead.Paging;
import com.inthinc.pro.selenium.pageObjects.PageVehicleReport.VehicleReportButtons;
import com.inthinc.pro.selenium.pageObjects.PageVehicleReport.VehicleReportDropDowns;
import com.inthinc.pro.selenium.pageObjects.PageVehicleReport.VehicleReportLinks;
import com.inthinc.pro.selenium.pageObjects.PageVehicleReport.VehicleReportPager;
import com.inthinc.pro.selenium.pageObjects.PageVehicleReport.VehicleReportPopUps;
import com.inthinc.pro.selenium.pageObjects.PageVehicleReport.VehicleReportTextFields;
import com.inthinc.pro.selenium.pageObjects.PageVehicleReport.VehicleReportTexts;
import com.inthinc.pro.selenium.pageObjects.PopUps.EditColumns;
import com.inthinc.pro.selenium.pageObjects.PopUps.Email;
import com.inthinc.pro.selenium.pageObjects.PopUps.Types;

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
    
    public class WaysmartReportDropDowns extends NavigationBarDropDowns{
        
        public DropDown reportDropDown(){
            return new DropDown(WaysmartReportEnum.REPORT_DROP_DOWN);
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
}
