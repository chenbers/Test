package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Calendar;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextDateFieldLabel;
import com.inthinc.pro.automation.elements.TextDropDownLabel;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TimeOfDay;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.HOSDriverLogsEditEnum;

public class PageHOSEditDriverLogs extends HOSBar {

    public PageHOSEditDriverLogs() {
        // TODO Auto-generated constructor stub
    }
    
    public class PageHOSEditDriverLogsLinks extends HOSBarLinks {}

    public class PageHOSEditDriverLogsTexts extends HOSBarTexts {
        
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
        
    }

    public class PageHOSEditDriverLogsTextFields extends HOSBarTextFields {
        
        
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
    
    public FuelStopsDateSelectors _dateSelector(){
        return new FuelStopsDateSelectors();
    }
    
    public class FuelStopsDateSelectors{

        public Calendar date() {
            return new Calendar(HOSDriverLogsEditEnum.DATE);
        }
    }

    public class PageHOSEditDriverLogsButtons extends HOSBarButtons {
        
        public TextButton topSave() {
            return new TextButton(HOSDriverLogsEditEnum.SAVE_TOP);
        }
        
        public TextButton topCancel() {
            return new TextButton(HOSDriverLogsEditEnum.CANCEL_TOP);
        }
        
        public TextButton bottomSave() {
            return new TextButton(HOSDriverLogsEditEnum.SAVE_BOTTOM);
        }
        
        public TextButton bottomCancel() {
            return new TextButton(HOSDriverLogsEditEnum.CANCEL_BOTTOM);
        }
        
        
    }

    public class PageHOSEditDriverLogsDropDowns extends HOSBarDropDowns {
        
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
        
    }

    public class PageHOSEditDriverLogsPopUps extends MastheadPopUps {}

    public class PageHOSEditDriverLogsPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public PageHOSEditDriverLogsPager _page() {
        return new PageHOSEditDriverLogsPager();
    }

    public PageHOSEditDriverLogsLinks _link() {
        return new PageHOSEditDriverLogsLinks();
    }

    public PageHOSEditDriverLogsTexts _text() {
        return new PageHOSEditDriverLogsTexts();
    }

    public PageHOSEditDriverLogsButtons _button() {
        return new PageHOSEditDriverLogsButtons();
    }

    public PageHOSEditDriverLogsTextFields _textField() {
        return new PageHOSEditDriverLogsTextFields();
    }

    public PageHOSEditDriverLogsDropDowns _dropDown() {
        return new PageHOSEditDriverLogsDropDowns();
    }

    public PageHOSEditDriverLogsPopUps _popUp() {
        return new PageHOSEditDriverLogsPopUps();
    }
    
    public TimeOfDay _timeSelector(){
        return new TimeOfDay(HOSDriverLogsEditEnum.TIME_CHANGER);
    }

    @Override
    public SeleniumEnums setUrl() {
        return HOSDriverLogsEditEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _button().topCancel().isPresent() && _text().labelDate().isPresent();
    }

}
