package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.selenium.pageEnums.NotificationsHOSExceptionsEnum;

public class PageNotificationsHOSExceptions extends NotificationsEventsBar {

    public PageNotificationsHOSExceptions() {
        page = "hosEvents";
        url = NotificationsHOSExceptionsEnum.DEFAULT_URL;
        checkMe.add(NotificationsHOSExceptionsEnum.TITLE);
        checkMe.add(NotificationsHOSExceptionsEnum.MESSAGE);
    }
    
    public class PageNotificationsHOSExceptionsLinks extends EventsBarLinks {}

    public class PageNotificationsHOSExceptionsTexts extends EventsBarTexts {
        
        public Text title(){
            return new Text(NotificationsHOSExceptionsEnum.TITLE);
        }
        
        public Text message(){
            return new Text(NotificationsHOSExceptionsEnum.MESSAGE);
        }
    }

    public class PageNotificationsHOSExceptionsTextFields extends EventsBarTextFields {}

    public class PageNotificationsHOSExceptionsButtons extends EventsBarButtons {}

    public class PageNotificationsHOSExceptionsDropDowns extends EventsBarDropDowns {}

    public class PageNotificationsHOSExceptionsPopUps extends MastheadPopUps {
        
        public PageNotificationsHOSExceptionsPopUps(){
            super(page, Types.REPORT, 3);
        }
        
        public EditColumns editColumns(){
            return new EditColumns();
        }
        
        public Email emailReport(){
            return new Email();
        }
    }

    public class PageNotificationsHOSExceptionsPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public PageNotificationsHOSExceptionsPager _page() {
        return new PageNotificationsHOSExceptionsPager();
    }

    public PageNotificationsHOSExceptionsLinks _link() {
        return new PageNotificationsHOSExceptionsLinks();
    }

    public PageNotificationsHOSExceptionsTexts _text() {
        return new PageNotificationsHOSExceptionsTexts();
    }

    public PageNotificationsHOSExceptionsButtons _button() {
        return new PageNotificationsHOSExceptionsButtons();
    }

    public PageNotificationsHOSExceptionsTextFields _textField() {
        return new PageNotificationsHOSExceptionsTextFields();
    }

    public PageNotificationsHOSExceptionsDropDowns _dropDown() {
        return new PageNotificationsHOSExceptionsDropDowns();
    }

    public PageNotificationsHOSExceptionsPopUps _popUp() {
        return new PageNotificationsHOSExceptionsPopUps();
    }

}
