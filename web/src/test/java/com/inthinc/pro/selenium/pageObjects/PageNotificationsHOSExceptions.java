package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.NotificationsBarEnum;
import com.inthinc.pro.selenium.pageEnums.NotificationsHOSExceptionsEnum;

public class PageNotificationsHOSExceptions extends NotificationsEventsBar {

    public PageNotificationsHOSExceptions() {
    	
        enums = new SeleniumEnums[] { NotificationsBarEnum.TIME_FRAME_DHX, NotificationsBarEnum.TEAM_SELECTION_DHX };
    	
        page = "hosEvents";
        checkMe.add(NotificationsHOSExceptionsEnum.TITLE);
        checkMe.add(NotificationsHOSExceptionsEnum.MESSAGE);
    }
    
    public class NotificationsHOSExceptionsLinks extends EventsBarLinks {}

    public class NotificationsHOSExceptionsTexts extends EventsBarTexts {
        
        public Text title(){
            return new Text(NotificationsHOSExceptionsEnum.TITLE);
        }
        
        public Text message(){
            return new Text(NotificationsHOSExceptionsEnum.MESSAGE);
        }
    }

    public class NotificationsHOSExceptionsTextFields extends EventsBarTextFields {}

    public class NotificationsHOSExceptionsButtons extends EventsBarButtons {}

    public class NotificationsHOSExceptionsDropDowns extends EventsBarDropDowns {}

    public class NotificationsHOSExceptionsPopUps extends MastheadPopUps {
        
        public NotificationsHOSExceptionsPopUps(){
            super(page, Types.REPORT, 3);
        }
        
        public EditColumns editColumns(){
            return new EditColumns();
        }
        
        public Email emailReport(){
            return new Email();
        }
    }

    public class NotificationsHOSExceptionsPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public NotificationsHOSExceptionsPager _page() {
        return new NotificationsHOSExceptionsPager();
    }

    public NotificationsHOSExceptionsLinks _link() {
        return new NotificationsHOSExceptionsLinks();
    }

    public NotificationsHOSExceptionsTexts _text() {
        return new NotificationsHOSExceptionsTexts();
    }

    public NotificationsHOSExceptionsButtons _button() {
        return new NotificationsHOSExceptionsButtons();
    }

    public NotificationsHOSExceptionsTextFields _textField() {
        return new NotificationsHOSExceptionsTextFields();
    }

    public NotificationsHOSExceptionsDropDowns _dropDown() {
        return new NotificationsHOSExceptionsDropDowns();
    }

    public NotificationsHOSExceptionsPopUps _popUp() {
        return new NotificationsHOSExceptionsPopUps();
    }

    @Override
    public SeleniumEnums setUrl() {
        return NotificationsHOSExceptionsEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _link().editColumns().isPresent();
    }

}
