package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DhxDropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.NotificationsBarEnum;
import com.inthinc.pro.selenium.pageEnums.NotificationsRedFlagsEnum;

public class PageNotificationsRedFlags extends NotificationsBar {

    public PageNotificationsRedFlags() {
        url = NotificationsRedFlagsEnum.DEFAULT_URL;
        
        enums = new SeleniumEnums[] { NotificationsBarEnum.LEVEL_FILTER_DHX, NotificationsBarEnum.TIME_FRAME_DHX, NotificationsBarEnum.TEAM_SELECTION_DHX };
    }

    public class RedFlagsLinks extends NotificationsBarLinks {

        public TextTableLink alertDetailsEntry() {
            return new TextTableLink(NotificationsBarEnum.DETAILS_ENTRY, page);
        }

    }

    public class RedFlagsTexts extends NotificationsBarTexts {

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

    public class RedFlagsTextFields extends NotificationsBarTextFields {

    }

    public class RedFlagsButtons extends NotificationsBarButtons {


    }

    public class RedFlagsDropDowns extends NotificationsBarDropDowns {
        

        public DhxDropDown levelFilter() {
            return new DhxDropDown(NotificationsBarEnum.LEVEL_FILTER_DHX, page, enums);
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
}
