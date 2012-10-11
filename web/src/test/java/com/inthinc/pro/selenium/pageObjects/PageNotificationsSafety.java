package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.NotificationsSafetyEnum;

public class PageNotificationsSafety extends NotificationsEventsBar {

    public PageNotificationsSafety() {
        page = "safety";
        url = NotificationsSafetyEnum.DEFAULT_URL;
        checkMe.add(NotificationsSafetyEnum.MAIN_TITLE);
        checkMe.add(NotificationsSafetyEnum.MAIN_TITLE_COMMENT);
    }

    public class NotificationsSafetyLinks extends EventsBarLinks {

    }

    public class NotificationsSafetyTexts extends EventsBarTexts {


        public Text title() {
            return new Text(NotificationsSafetyEnum.MAIN_TITLE);
        }

        public Text note() {
            return new Text(NotificationsSafetyEnum.MAIN_TITLE_COMMENT);
        }

    }

    public class NotificationsSafetyTextFields extends EventsBarTextFields {

    }

    public class NotificationsSafetyButtons extends EventsBarButtons {


    }

    public class NotificationsSafetyDropDowns extends EventsBarDropDowns {

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
        
        public LocationPopUp location(){
            return new LocationPopUp();
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

    @Override
    public SeleniumEnums setUrl() {
        return NotificationsSafetyEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _link().editColumns().isPresent();
    }

}
