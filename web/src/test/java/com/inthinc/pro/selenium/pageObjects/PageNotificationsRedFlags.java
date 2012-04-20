package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DHXDropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.NotificationsBarEnum;
import com.inthinc.pro.selenium.pageEnums.NotificationsRedFlagsEnum;

public class PageNotificationsRedFlags extends NotificationsEventsBar {

    public PageNotificationsRedFlags() {
        enums = new SeleniumEnums[] { NotificationsBarEnum.LEVEL_FILTER_DHX, NotificationsBarEnum.TIME_FRAME_DHX, NotificationsBarEnum.TEAM_SELECTION_DHX };
    }

    public class RedFlagsLinks extends EventsBarLinks {

        public TextTableLink alertDetailsEntry() {
            return new TextTableLink(NotificationsBarEnum.DETAILS_ENTRY, page);
        }

    }

    public class RedFlagsTexts extends EventsBarTexts {

        public Text headerLevel() {
            return new Text(NotificationsBarEnum.HEADER_LEVEL, page);
        }

        public Text headerAlertDetails() {
            return new Text(NotificationsBarEnum.HEADER_DETAILS, page);
        }


        public TextTable levelEntry() {
            return new TextTable(NotificationsBarEnum.LEVEL_ENTRY, page);
        }


        public Text title() {
            return new Text(NotificationsRedFlagsEnum.MAIN_TITLE);
        }

        public Text note() {
            return new Text(NotificationsRedFlagsEnum.MAIN_TITLE_COMMENT);
        }
    }

    public class RedFlagsTextFields extends EventsBarTextFields {

    }

    public class RedFlagsButtons extends EventsBarButtons {


    }

    public class RedFlagsDropDowns extends EventsBarDropDowns {
        

        public DHXDropDown levelFilter() {
            return new DHXDropDown(NotificationsBarEnum.LEVEL_FILTER_DHX, page, enums);
        }
    }

    public class RedFlagsPopUps extends MastheadPopUps {

        public RedFlagsPopUps() {
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

    public RedFlagsLinks _link() {
        return new RedFlagsLinks();
    }

    public RedFlagsTexts _text() {
        return new RedFlagsTexts();
    }

    public RedFlagsButtons _button() {
        return new RedFlagsButtons();
    }

    public RedFlagsTextFields _textField() {
        return new RedFlagsTextFields();
    }

    public RedFlagsDropDowns _dropDown() {
        return new RedFlagsDropDowns();
    }

    public RedFlagsPopUps _popUp() {
        return new RedFlagsPopUps();
    }

    public class RedFlagsPager{
        public Paging pageIndex(){
            return new Paging();
        }
    }
    

    public RedFlagsPager _page(){
        return new RedFlagsPager();
    }

    @Override
    public SeleniumEnums setUrl() {
        return NotificationsRedFlagsEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        // TODO Auto-generated method stub
        return false;
    }
}
