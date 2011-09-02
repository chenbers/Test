package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.SortHeader;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextButtonTable;
import com.inthinc.pro.automation.elements.TextDateFieldLabel;
import com.inthinc.pro.automation.elements.TextDropDownLabel;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextFieldWithSpinner;
import com.inthinc.pro.automation.elements.TextLabel;
import com.inthinc.pro.automation.elements.TextRadioButtonLabel;
import com.inthinc.pro.selenium.pageEnums.NotificationsCrashHistoryAddEditEnum;

public class PageNotificationsCrashHistoryAddEdit extends NotificationsBar {

    public PageNotificationsCrashHistoryAddEdit() {
        // TODO Auto-generated constructor stub
    }

    public class PageNotificationsCrashHistoryAddEditLinks extends NotificationsBarLinks {}

    public class PageNotificationsCrashHistoryAddEditTexts extends NotificationsBarTexts {
        public Text title() {
            return new Text(NotificationsCrashHistoryAddEditEnum.TITLE);
        }

        public Text headerCrashSummary() {
            return new Text(
                    NotificationsCrashHistoryAddEditEnum.CRASH_SUMMARY_HEADER);
        }

        public Text headerDescription() {
            return new Text(
                    NotificationsCrashHistoryAddEditEnum.DESCRIPTION_HEADER);
        }

        public Text headerCrashHistory() {
            return new Text(
                    NotificationsCrashHistoryAddEditEnum.CRASH_LOCATION_HEADER);
        }

        public Text labelCrashReportStatus() {
            return new TextDropDownLabel(
                    NotificationsCrashHistoryAddEditEnum.CRASH_REPORT_STATUS);
        }

        public Text labelDateTime() {
            return new TextDateFieldLabel(
                    NotificationsCrashHistoryAddEditEnum.DATE_TIME);
        }

        public Text labelVehicle() {
            return new TextDropDownLabel(
                    NotificationsCrashHistoryAddEditEnum.VEHICLE);
        }

        public Text labelDriver() {
            return new TextDropDownLabel(
                    NotificationsCrashHistoryAddEditEnum.DRIVER);
        }

        public Text labelWeather() {
            return new TextLabel(NotificationsCrashHistoryAddEditEnum.WEATHER);
        }

        public Text labelOccupantCount() {
            return new TextFieldLabel(
                    NotificationsCrashHistoryAddEditEnum.OCCUPANT_COUNT, "");
        }

        public Text messageDescription() {
            return new Text(
                    NotificationsCrashHistoryAddEditEnum.DESCRIPTION_MESSAGE);
        }

        public Text labelSelectLocationBy() {
            return new Text(
                    NotificationsCrashHistoryAddEditEnum.LABEL_SELECT_LOCATION_BY);
        }

        public Text labelTrips() {
            return new TextRadioButtonLabel(
                    NotificationsCrashHistoryAddEditEnum.SELECT_LOCATION_BY_TRIPS);
        }

        public Text labelgFindAddress() {
            return new TextRadioButtonLabel(
                    NotificationsCrashHistoryAddEditEnum.SELECT_LOCATION_BY_FIND_ADDRESS);
        }

        public Text labelTripsByDriver() {
            return new TextRadioButtonLabel(
                    NotificationsCrashHistoryAddEditEnum.FIND_BY_DRIVER);
        }

        public Text labelTripsByVehicle() {
            return new TextRadioButtonLabel(
                    NotificationsCrashHistoryAddEditEnum.FIND_BY_VEHICLE);
        }

        public Text headerEndTime() {
            return new Text(
                    NotificationsCrashHistoryAddEditEnum.END_TIME_HEADER);
        }
    }

    public class PageNotificationsCrashHistoryAddEditTextFields extends NotificationsBarTextFields {

        public TextFieldWithSpinner occupantCount() {
            return new TextFieldWithSpinner(
                    NotificationsCrashHistoryAddEditEnum.OCCUPANT_COUNT);
        }

        public TextField description() {
            return new TextField(
                    NotificationsCrashHistoryAddEditEnum.DESCRIPTION_BOX);
        }

        public TextField findAddress() {
            return new TextField(
                    NotificationsCrashHistoryAddEditEnum.ADDRESS_TEXT_FIELD);
        }

        public TextField dateTime() {
            return new TextField(NotificationsCrashHistoryAddEditEnum.DATE_TIME);
        }

    }

    public class PageNotificationsCrashHistoryAddEditButtons extends NotificationsBarButtons {

        public TextButton topSave() {
            return new TextButton(NotificationsCrashHistoryAddEditEnum.TOP_SAVE);
        }

        public TextButton topCancel() {
            return new TextButton(
                    NotificationsCrashHistoryAddEditEnum.TOP_CANCEL);
        }

        public TextButton bottomSave() {
            return new TextButton(
                    NotificationsCrashHistoryAddEditEnum.BOTTOM_SAVE);
        }

        public TextButton bottomCancel() {
            return new TextButton(
                    NotificationsCrashHistoryAddEditEnum.BOTTOM_CANCEL);
        }

        public Button trips() {
            return new Button(
                    NotificationsCrashHistoryAddEditEnum.SELECT_LOCATION_BY_TRIPS);
        }

        public Button findAddress() {
            return new Button(
                    NotificationsCrashHistoryAddEditEnum.SELECT_LOCATION_BY_FIND_ADDRESS);
        }

        public Button driver() {
            return new Button(
                    NotificationsCrashHistoryAddEditEnum.FIND_BY_DRIVER);
        }

        public Button vehicle() {
            return new Button(
                    NotificationsCrashHistoryAddEditEnum.FIND_BY_VEHICLE);
        }

        public TextButtonTable entryStartTime() {
            return new TextButtonTable(
                    NotificationsCrashHistoryAddEditEnum.START_TIME);
        }

        public TextButtonTable entryEndTime() {
            return new TextButtonTable(
                    NotificationsCrashHistoryAddEditEnum.END_TIME);
        }

        public SortHeader sortByStartTime() {
            return new SortHeader(
                    NotificationsCrashHistoryAddEditEnum.START_TIME);
        }

    }

    public class PageNotificationsCrashHistoryAddEditDropDowns extends NotificationsBarDropDowns {

        public DropDown crashReportStatus() {
            return new DropDown(
                    NotificationsCrashHistoryAddEditEnum.CRASH_REPORT_STATUS);
        }

        public DropDown vehicle() {
            return new DropDown(NotificationsCrashHistoryAddEditEnum.VEHICLE);
        }

        public DropDown driver() {
            return new DropDown(NotificationsCrashHistoryAddEditEnum.DRIVER);
        }
    }

    public class PageNotificationsCrashHistoryAddEditPopUps extends MastheadPopUps {}

    public class PageNotificationsCrashHistoryAddEditPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public PageNotificationsCrashHistoryAddEditPager _page() {
        return new PageNotificationsCrashHistoryAddEditPager();
    }

    public PageNotificationsCrashHistoryAddEditLinks _link() {
        return new PageNotificationsCrashHistoryAddEditLinks();
    }

    public PageNotificationsCrashHistoryAddEditTexts _text() {
        return new PageNotificationsCrashHistoryAddEditTexts();
    }

    public PageNotificationsCrashHistoryAddEditButtons _button() {
        return new PageNotificationsCrashHistoryAddEditButtons();
    }

    public PageNotificationsCrashHistoryAddEditTextFields _textField() {
        return new PageNotificationsCrashHistoryAddEditTextFields();
    }

    public PageNotificationsCrashHistoryAddEditDropDowns _dropDown() {
        return new PageNotificationsCrashHistoryAddEditDropDowns();
    }

    public PageNotificationsCrashHistoryAddEditPopUps _popUp() {
        return new PageNotificationsCrashHistoryAddEditPopUps();
    }

}
