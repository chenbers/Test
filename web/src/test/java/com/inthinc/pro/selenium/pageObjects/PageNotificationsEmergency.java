package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.NotificationsBarEnum;
import com.inthinc.pro.selenium.pageEnums.NotificationsEmergencyEnum;

public class PageNotificationsEmergency extends NotificationsEventsBar {


    public PageNotificationsEmergency() {
        page = "emergency";
        checkMe.add(NotificationsEmergencyEnum.MAIN_TITLE);
    }

    public class NotificationsEmergencyLinks extends EventsBarLinks {

    }

    public class NotificationsEmergencyTexts extends EventsBarTexts {

        public Text title() {
            return new Text(NotificationsEmergencyEnum.MAIN_TITLE);
        }

    }

    public class NotificationsEmergencyTextFields extends EventsBarTextFields {

    }

    public class NotificationsEmergencyButtons extends EventsBarButtons {


    }

    public class NotificationsEmergencyDropDowns extends EventsBarDropDowns {


    }

    public class NotificationsEmergencyPopUps extends MastheadPopUps {
        public NotificationsEmergencyPopUps() {
            super(page, Types.REPORT, 3);
        }

        public EditColumns editColumns() {
            return new EditColumns();
        }

        public Email emailReport() {
            return new Email();
        }
        
        public ExcludeEvent excludeEvent(){
            return new ExcludeEvent(true);
        }
        
        public LocationPopUp location(){
            return new LocationPopUp();
        }
    }

    public NotificationsEmergencyLinks _link() {
        return new NotificationsEmergencyLinks();
    }

    public NotificationsEmergencyTexts _text() {
        return new NotificationsEmergencyTexts();
    }

    public NotificationsEmergencyButtons _button() {
        return new NotificationsEmergencyButtons();
    }

    public NotificationsEmergencyTextFields _textField() {
        return new NotificationsEmergencyTextFields();
    }

    public NotificationsEmergencyDropDowns _dropDown() {
        return new NotificationsEmergencyDropDowns();
    }

    public NotificationsEmergencyPopUps _popUp() {
        return new NotificationsEmergencyPopUps();
    }

    public NotificationsEmergencyPager _page() {
        return new NotificationsEmergencyPager();
    }

    public class NotificationsEmergencyPager {

        public Paging pageIndex() {
            return new Paging();
        }
    }

    @Override
    public SeleniumEnums setUrl() {
        return NotificationsEmergencyEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _link().editColumns().isPresent();
    }


}
