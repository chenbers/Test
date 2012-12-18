package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.NotificationsBarEnum;
import com.inthinc.pro.selenium.pageEnums.NotificationsDriverLoginsEnum;

public class PageNotificationsDriverLogins extends NotificationsEventsBar {

    public PageNotificationsDriverLogins() {
    	
        enums = new SeleniumEnums[] { NotificationsBarEnum.TIME_FRAME_DHX, NotificationsBarEnum.TEAM_SELECTION_DHX };
    	
        page = "driverLogins";
        checkMe.add(NotificationsDriverLoginsEnum.TITLE);
        checkMe.add(NotificationsDriverLoginsEnum.MESSAGE);
    }
    
    
    public class DriverLoginsLinks extends EventsBarLinks{}
    public class DriverLoginsTexts extends EventsBarTexts{
        public Text title(){
            return new Text(NotificationsDriverLoginsEnum.TITLE);
        }
        
        public Text message(){
            return new Text(NotificationsDriverLoginsEnum.MESSAGE);
        }
    }
    public class DriverLoginsTextFields extends EventsBarTextFields{}
    public class DriverLoginsButtons extends EventsBarButtons{}
    public class DriverLoginsDropDowns extends EventsBarDropDowns{}
    public class DriverLoginsPopUps extends MastheadPopUps {
        public DriverLoginsPopUps(){
            super(page, Types.REPORT, 3);
        }
        
        public EditColumns editColumns() {
            return new EditColumns();
        }
        
        public Email emailReport(){
            return new Email();
        }
        
        public ExcludeEvent excludeEvent(){
            return new ExcludeEvent(true);
        }
    }
    
    
    public class DriverLoginsPager{
        public Paging pageIndex(){
            return new Paging();
        }
    }
    
    
    public DriverLoginsPager _page(){
        return new DriverLoginsPager();
    }
    
    public DriverLoginsLinks _link(){
        return new DriverLoginsLinks();
    }
    
    public DriverLoginsTexts _text(){
        return new DriverLoginsTexts();
    }
        
    public DriverLoginsButtons _button(){
        return new DriverLoginsButtons();
    }
    
    public DriverLoginsTextFields _textField(){
        return new DriverLoginsTextFields();
    }
    
    public DriverLoginsDropDowns _dropDown(){
        return new DriverLoginsDropDowns();
    }
    
    public DriverLoginsPopUps _popUp(){
        return new DriverLoginsPopUps();
    }

    @Override
    public SeleniumEnums setUrl() {
        return NotificationsDriverLoginsEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _link().editColumns().isPresent();
    }
}
