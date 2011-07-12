package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.selenium.pageEnums.NotificationsSafetyEnum;

public class PageNotificationsSafety extends NotificationsBar {

    private static String page = "safety";

    public PageNotificationsSafety() {
        url = NotificationsSafetyEnum.DEFAULT_URL;
        checkMe.add(NotificationsSafetyEnum.MAIN_TITLE);
        checkMe.add(NotificationsSafetyEnum.MAIN_TITLE_COMMENT);
    }

    public class NotificationsSafetyLinks extends NotificationsBarLinks {

    }

    public class NotificationsSafetyTexts extends NotificationsBarTexts {


        public Text title() {
            return new Text(NotificationsSafetyEnum.MAIN_TITLE);
        }

        public Text note() {
            return new Text(NotificationsSafetyEnum.MAIN_TITLE_COMMENT);
        }

    }

    public class NotificationsSafetyTextFields extends NotificationsBarTextFields {

    }

    public class NotificationsSafetyButtons extends NotificationsBarButtons {


    }

    public class NotificationsSafetyDropDowns extends NotificationsBarDropDowns {

    }

    public class NotificationsSafetyPopUps extends MastheadPopUps {
        public NotificationsSafetyPopUps() {
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

    public NotificationsSafetyLinks _link() {
        return new NotificationsSafetyLinks();
    }

    public NotificationsSafetyTexts _text() {
        return new NotificationsSafetyTexts();
    }

    public NotificationsSafetyButtons _button() {
        return new NotificationsSafetyButtons();
    }

    public NotificationsSafetyTextFields _textField() {
        return new NotificationsSafetyTextFields();
    }

    public NotificationsSafetyDropDowns _dropDown() {
        return new NotificationsSafetyDropDowns();
    }

    public NotificationsSafetyPopUps _popUp() {
        return new NotificationsSafetyPopUps();
    }

    public NotificationsSafetyPager _page() {
        return new NotificationsSafetyPager();
    }

    public class NotificationsSafetyPager {

        public Paging pageIndex() {
            return new Paging();
        }
    }

}
