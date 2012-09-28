package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.NotificationsCrashReportEnum;

public class PageNotificationsCrashReport extends NotificationsBar {

    public PageNotificationsCrashReport() {
        // TODO Auto-generated constructor stub
    }

    public class NotificationsCrashHistoryAddEditLinks extends NotificationsBarLinks {
    	
    	public TextLink back() {
    		return new TextLink(NotificationsCrashReportEnum.BACK);
    	}
    	
        public TextLink labelVehicle() {
            return new TextLink(
                    NotificationsCrashReportEnum.VEHICLE);
        }

        public TextLink labelDriver() {
            return new TextLink(
                    NotificationsCrashReportEnum.DRIVER);
        }
    }

    public class NotificationsCrashHistoryAddEditTexts extends NotificationsBarTexts {

        public Text headerCrashDetails() {
            return new Text(
                    NotificationsCrashReportEnum.CRASH_DETAILS_HEADER);
        }

        public Text headerCrashRoute() {
            return new Text(
                    NotificationsCrashReportEnum.CRASH_ROUTE_HEADER);
        }

        public Text headerCrashEvents() {
            return new Text(
                    NotificationsCrashReportEnum.CRASH_EVENTS_HEADER);
        }

        public Text labelCrashReportStatus() {
            return new Text(
                    NotificationsCrashReportEnum.CRASH_REPORT_STATUS);
        }

        public Text labelDateTime() {
            return new Text(
                    NotificationsCrashReportEnum.DATE_TIME);
        }

        public Text labelWeather() {
            return new Text(NotificationsCrashReportEnum.WEATHER);
        }

        public Text labelOccupantCount() {
            return new Text(
                    NotificationsCrashReportEnum.OCCUPANT_COUNT);
        }

        public Text messageDescription() {
            return new Text(
                    NotificationsCrashReportEnum.DESCRIPTION_MESSAGE);
        }

    }

    public class NotificationsCrashHistoryAddEditTextFields extends NotificationsBarTextFields {}

    public class NotificationsCrashHistoryAddEditButtons extends NotificationsBarButtons {

        public TextButton edit() {
            return new TextButton(NotificationsCrashReportEnum.EDIT);
        }

    }

    public class NotificationsCrashHistoryAddEditDropDowns extends NotificationsBarDropDowns {}

    public class NotificationsCrashHistoryAddEditPopUps extends MastheadPopUps {}

    public class NotificationsCrashHistoryAddEditPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public NotificationsCrashHistoryAddEditPager _page() {
        return new NotificationsCrashHistoryAddEditPager();
    }

    public NotificationsCrashHistoryAddEditLinks _link() {
        return new NotificationsCrashHistoryAddEditLinks();
    }

    public NotificationsCrashHistoryAddEditTexts _text() {
        return new NotificationsCrashHistoryAddEditTexts();
    }

    public NotificationsCrashHistoryAddEditButtons _button() {
        return new NotificationsCrashHistoryAddEditButtons();
    }

    public NotificationsCrashHistoryAddEditTextFields _textField() {
        return new NotificationsCrashHistoryAddEditTextFields();
    }

    public NotificationsCrashHistoryAddEditDropDowns _dropDown() {
        return new NotificationsCrashHistoryAddEditDropDowns();
    }

    public NotificationsCrashHistoryAddEditPopUps _popUp() {
        return new NotificationsCrashHistoryAddEditPopUps();
    }

    @Override
    public SeleniumEnums setUrl() {
        return NotificationsCrashReportEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _link().back().isPresent() &&
               _link().labelDriver().isPresent();
    }

}
