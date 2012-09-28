package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CalendarObject;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextDateFieldLabel;
import com.inthinc.pro.automation.elements.TextDropDownLabel;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextFieldWithSpinner;
import com.inthinc.pro.automation.elements.TimeOfDay;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.HOSDriverLogsEditEnum;

public class PageHOSAddEditDriverLogs extends HOSBar {

    public PageHOSAddEditDriverLogs() {
        // TODO Auto-generated constructor stub
    }
    
    public class HOSEditDriverLogsLinks extends HOSBarLinks {}

    public class HOSEditDriverLogsTexts extends HOSBarTexts {
        
        public Text labelDate() {
            return new TextDateFieldLabel(HOSDriverLogsEditEnum.DATE);
        }
        
        public Text labelStatus() {
            return new TextDropDownLabel(HOSDriverLogsEditEnum.STATUS);
        }
        
        public Text labelTrailer() {
            return new TextFieldLabel(HOSDriverLogsEditEnum.TRAILER);
        }
        
        public Text labelService() {
            return new TextFieldLabel(HOSDriverLogsEditEnum.SERVICE);
        }
        
        public Text labelDriver() {
            return new TextDropDownLabel(HOSDriverLogsEditEnum.DRIVER);
        }
        
        public Text labelVehicle() {
            return new TextDropDownLabel(HOSDriverLogsEditEnum.VEHICLE);
        }
        
        public Text labelLocation() {
            return new TextFieldLabel(HOSDriverLogsEditEnum.LOCATION);
        }
        
        public Text labelDOT() {
            return new TextDropDownLabel(HOSDriverLogsEditEnum.DOT);
        }

        public Text timeZone(){
            return duration().timeZone();
        }
        
        public Text durationLabel(){
            return duration().label();
        }
    }

    public class HOSEditDriverLogsTextFields extends HOSBarTextFields {

        
        public TextFieldWithSpinner hours(){
            return duration().hours();
        }
        
        public TextFieldWithSpinner minutes(){
            return duration().minutes();
        }
        
        public TextFieldWithSpinner seconds(){
            return duration().seconds();
        }
        
        
        public TextField trailer() {
            return new TextField(HOSDriverLogsEditEnum.TRAILER);
        }
        
        public TextField service() {
            return new TextField(HOSDriverLogsEditEnum.SERVICE);
        }
        
        public TextField location() {
            return new TextField(HOSDriverLogsEditEnum.LOCATION);
        }
    }
    

    public class HOSEditDriverLogsButtons extends HOSBarButtons {
        
        public TextButton saveTop() {
            return new TextButton(HOSDriverLogsEditEnum.SAVE_TOP);
        }
        
        public TextButton cancelTop() {
            return new TextButton(HOSDriverLogsEditEnum.CANCEL_TOP);
        }
        
        public TextButton saveBottom() {
            return new TextButton(HOSDriverLogsEditEnum.SAVE_BOTTOM);
        }
        
        public TextButton cancelBottom() {
            return new TextButton(HOSDriverLogsEditEnum.CANCEL_BOTTOM);
        }
        
        
    }

    public class HOSEditDriverLogsDropDowns extends HOSBarDropDowns {
        
        public DropDown status() {
            return new DropDown(HOSDriverLogsEditEnum.STATUS);
        }
        
        public DropDown driver() {
            return new DropDown(HOSDriverLogsEditEnum.DRIVER);
        }
        
        public DropDown vehicle() {
            return new DropDown(HOSDriverLogsEditEnum.VEHICLE);
        }
        
        public DropDown dot() {
            return new DropDown(HOSDriverLogsEditEnum.DOT);
        }

        public DropDown amPm(){
            return duration().amPm();
        }
        
        public DropDown date() {
            return new CalendarObject(HOSDriverLogsEditEnum.DATE);
        }
    }

    public class HOSEditDriverLogsPopUps extends MastheadPopUps {}

    public class HOSEditDriverLogsPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public HOSEditDriverLogsPager _page() {
        return new HOSEditDriverLogsPager();
    }

    public HOSEditDriverLogsLinks _link() {
        return new HOSEditDriverLogsLinks();
    }

    public HOSEditDriverLogsTexts _text() {
        return new HOSEditDriverLogsTexts();
    }

    public HOSEditDriverLogsButtons _button() {
        return new HOSEditDriverLogsButtons();
    }

    public HOSEditDriverLogsTextFields _textField() {
        return new HOSEditDriverLogsTextFields();
    }

    public HOSEditDriverLogsDropDowns _dropDown() {
        return new HOSEditDriverLogsDropDowns();
    }

    public HOSEditDriverLogsPopUps _popUp() {
        return new HOSEditDriverLogsPopUps();
    }
    

    private TimeOfDay duration(){
        return new TimeOfDay(HOSDriverLogsEditEnum.TIME_CHANGER); 
    }
    

    @Override
    public SeleniumEnums setUrl() {
        return HOSDriverLogsEditEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _button().cancelTop().isPresent() && _dropDown().status().isPresent();
    }

}
