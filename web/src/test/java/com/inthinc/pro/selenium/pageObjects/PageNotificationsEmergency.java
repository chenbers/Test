package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.selenium.pageEnums.NotificationsBarEnum;
import com.inthinc.pro.selenium.pageEnums.NotificationsEmergencyEnum;

public class PageNotificationsEmergency extends NotificationsBar {


    public PageNotificationsEmergency() {
        page = "safety";
        url = NotificationsEmergencyEnum.DEFAULT_URL;
        checkMe.add(NotificationsEmergencyEnum.MAIN_TITLE);
    }

    public class NotificationsEmergencyLinks extends NotificationsBarLinks {

    }

    public class NotificationsEmergencyTexts extends NotificationsBarTexts {



        public Text counter() {
            return new Text(NotificationsBarEnum.COUNTER, page);
        }

        public Text title() {
            return new Text(NotificationsEmergencyEnum.MAIN_TITLE);
        }

    }

    public class NotificationsEmergencyTextFields extends NotificationsBarTextFields {

    }

    public class NotificationsEmergencyButtons extends NotificationsBarButtons {


    }

    public class NotificationsEmergencyDropDowns extends NotificationsBarDropDowns {


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


}
