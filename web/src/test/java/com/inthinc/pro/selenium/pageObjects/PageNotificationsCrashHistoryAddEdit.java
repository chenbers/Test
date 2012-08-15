package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.CalendarObject;
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
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextRadioButtonLabel;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.NotificationsCrashHistoryAddEditEnum;

public class PageNotificationsCrashHistoryAddEdit extends NotificationsBar {

    public PageNotificationsCrashHistoryAddEdit() {
        // TODO Auto-generated constructor stub
    }

    public class NotificationsCrashHistoryAddEditLinks extends NotificationsBarLinks {
    	
    	public TextLink clean() {
    		return new TextLink(NotificationsCrashHistoryAddEditEnum.CLEAN_LINK);
    	}
    	
    	public TextLink time() {
    		return new TextLink(NotificationsCrashHistoryAddEditEnum.TIME_LINK);
    	}
    	
    	public TextLink today() {
    		return new TextLink(NotificationsCrashHistoryAddEditEnum.TODAY_LINK);
    	}

    }

    public class NotificationsCrashHistoryAddEditTexts extends NotificationsBarTexts {
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

    public class NotificationsCrashHistoryAddEditTextFields extends NotificationsBarTextFields {

        public TextFieldWithSpinner occupantCount() {
            return new TextFieldWithSpinner(NotificationsCrashHistoryAddEditEnum.OCCUPANT_COUNT);
        }

        public TextField description() {
            return new TextField(NotificationsCrashHistoryAddEditEnum.DESCRIPTION_BOX);
        }

        public TextField findAddress() {
            return new TextField(NotificationsCrashHistoryAddEditEnum.ADDRESS_TEXT_FIELD);
        }
        
        public TextField hours() {
            return new TextField(NotificationsCrashHistoryAddEditEnum.HOURS_FIELD);
        }
        
        public TextField minutes() {
            return new TextField(NotificationsCrashHistoryAddEditEnum.MINUTES_FIELD);
        }
        
        public TextField timeOfDay() {
            return new TextField(NotificationsCrashHistoryAddEditEnum.TIME_OF_DAY_FIELD);
        }

    }

    public class NotificationsCrashHistoryAddEditButtons extends NotificationsBarButtons {

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
        
        public TextButton locate() {
        	return new TextButton(
        			NotificationsCrashHistoryAddEditEnum.LOCATE);
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
        
        public Button hoursUp() {
            return new Button(NotificationsCrashHistoryAddEditEnum.HOURS_BUTTON_UP);
        }
        
        public Button hoursDown() {
            return new Button(NotificationsCrashHistoryAddEditEnum.HOURS_BUTTON_DOWN);
        }
        
        public Button minutesUp() {
            return new Button(NotificationsCrashHistoryAddEditEnum.MINUTES_BUTTON_UP);
        }
        
        public Button minutesDown() {
            return new Button(NotificationsCrashHistoryAddEditEnum.MINUTES_BUTTON_DOWN);
        }
        
        public Button timeOfDayUp() {
            return new Button(NotificationsCrashHistoryAddEditEnum.TIME_OF_DAY_UP);
        }
        
        public Button timeOfDayDown() {
            return new Button(NotificationsCrashHistoryAddEditEnum.TIME_OF_DAY_DOWN);
        }
        
        public Button ok() {
            return new Button(NotificationsCrashHistoryAddEditEnum.TIME_OK_BUTTON);
        }
        
        public Button cancel() {
            return new Button(NotificationsCrashHistoryAddEditEnum.TIME_CANCEL_BUTTON);
        }

    }

    public class NotificationsCrashHistoryAddEditDropDowns extends NotificationsBarDropDowns {

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
        
        public DropDown dateTime() {
            return new CalendarObject(NotificationsCrashHistoryAddEditEnum.DATE_TIME);
        }
    }

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
        return NotificationsCrashHistoryAddEditEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _dropDown().crashReportStatus().isPresent() &&
               _dropDown().driver().isPresent();
    }

}
