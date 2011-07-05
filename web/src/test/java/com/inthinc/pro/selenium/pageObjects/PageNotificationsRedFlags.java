package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.ButtonTable;
import com.inthinc.pro.automation.elements.DhxDropDown;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.NotificationsBarEnum;
import com.inthinc.pro.selenium.pageEnums.NotificationsRedFlagsEnum;

public class PageNotificationsRedFlags extends NotificationsBar {

    private static String page = "redFlags";

    public PageNotificationsRedFlags() {
        super.setPage(page);
        url = NotificationsRedFlagsEnum.DEFAULT_URL;
    }

    public class RedFlagsLinks extends NotificationsBarLinks {

        public TextLink editColumns() {
            return new TextLink(NotificationsBarEnum.EDIT_COLUMNS, page);
        }

        public TextTableLink alertDetailsEntry() {
            return new TextTableLink(NotificationsBarEnum.DETAILS_ENTRY, page);
        }

        public TextTableLink groupEntry() {
            return new TextTableLink(NotificationsBarEnum.GROUP_ENTRY, page);
        }

        public TextTableLink driverEntry() {
            return new TextTableLink(NotificationsBarEnum.DRIVER_ENTRY, page);
        }

        public TextTableLink vehicleEntry() {
            return new TextTableLink(NotificationsBarEnum.VEHICLE_ENTRY, page);
        }

        public TextTableLink statusEntry() {
            return new TextTableLink(NotificationsBarEnum.STATUS_ENTRY, page);
        }

        public TextLink sortByGroup() {
            return new TextLink(NotificationsBarEnum.SORT_GROUP, page);
        }

        public TextLink sortByDriver() {
            return new TextLink(NotificationsBarEnum.SORT_DRIVER, page);
        }

        public TextLink sortByVehicle() {
            return new TextLink(NotificationsBarEnum.SORT_VEHICLE, page);
        }

        public TextLink sortByDateTime() {
            return new TextLink(NotificationsBarEnum.SORT_DATE_TIME, page);
        }

    }

    public class RedFlagsTexts extends NotificationsBarTexts {

        public Text headerLevel() {
            return new Text(NotificationsBarEnum.HEADER_LEVEL, page);
        }

        public Text headerAlertDetails() {
            return new Text(NotificationsBarEnum.HEADER_DETAILS, page);
        }

        public Text headerDetail() {
            return new Text(NotificationsBarEnum.HEADER_DETAIL, page);
        }

        public Text headerStatus() {
            return new Text(NotificationsBarEnum.HEADER_STATUS, page);
        }

        public Text headerCategory() {
            return new Text(NotificationsBarEnum.HEADER_CATEGORY, page);
        }

        public TextTable levelEntry() {
            return new TextTable(NotificationsBarEnum.LEVEL_ENTRY, page);
        }

        public TextTable dateTimeEntry() {
            return new TextTable(NotificationsBarEnum.DATE_TIME_ENTRY, page);
        }

        public TextTable categoryEntry() {
            return new TextTable(NotificationsBarEnum.CATEGORY_ENTRY, page);
        }

        public TextTable detailEntry() {
            return new TextTable(NotificationsBarEnum.DETAIL_ENTRY, page);
        }

        public Text counter() {
            return new Text(NotificationsBarEnum.COUNTER, page);
        }

        public Text title() {
            return new Text(NotificationsRedFlagsEnum.MAIN_TITLE);
        }

        public Text note() {
            return new Text(NotificationsRedFlagsEnum.MAIN_TITLE_COMMENT);
        }
    }

    public class RedFlagsTextFields extends NotificationsBarTextFields {

        public TextField group() {
            return new TextField(NotificationsBarEnum.GROUP_FILTER, page);
        }

        public TextField driver() {
            return new TextField(NotificationsBarEnum.DRIVER_FILTER, page);
        }

        public TextField vehicle() {
            return new TextField(NotificationsBarEnum.VEHICLE_FILTER, page);
        }
    }

    public class RedFlagsButtons extends NotificationsBarButtons {

        public TextButton refresh() {
            return new TextButton(NotificationsBarEnum.REFRESH, page);
        }

        public Button tools() {
            return new Button(NotificationsBarEnum.TOOLS, page);
        }

        public Button exportToPDF() {
            return new Button(NotificationsBarEnum.EXPORT_TO_PDF, page);
        }

        public Button emailReport() {
            return new Button(NotificationsBarEnum.EMAIL_REPORT, page);
        }

        public Button exportToExcel() {
            return new Button(NotificationsBarEnum.EXPORT_TO_EXCEL, page);
        }

        public ButtonTable eventLocation() {
            return new ButtonTable(NotificationsBarEnum.LOCATION, page);
        }

    }

    public class RedFlagsDropDowns extends NotificationsBarDropDowns {
        private SeleniumEnums[] enums = { NotificationsBarEnum.LEVEL_FILTER_DHX, NotificationsBarEnum.TIME_FRAME_DHX, NotificationsBarEnum.TEAM_SELECTION_DHX };

        public DhxDropDown team() {
            return new DhxDropDown(NotificationsBarEnum.TEAM_SELECTION_DHX, page, enums);
        }

        public DhxDropDown timeFrame() {
            return new DhxDropDown(NotificationsBarEnum.TIME_FRAME_DHX, page, enums);
        }

        public DhxDropDown levelFilter() {
            return new DhxDropDown(NotificationsBarEnum.LEVEL_FILTER_DHX, page, enums);
        }

        public DropDown category() {
            return new DropDown(NotificationsBarEnum.CATEGORY_FILTER, page);
        }

        public DropDown statusFilter() {
            return new DropDown(NotificationsBarEnum.STATUS_FILTER, page);
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

    @Override
    public String getExpectedPath() {
        return NotificationsRedFlagsEnum.DEFAULT_URL.getURL();
    }
}
