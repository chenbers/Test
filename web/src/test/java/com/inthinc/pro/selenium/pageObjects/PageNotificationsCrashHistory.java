package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.ButtonTable;
import com.inthinc.pro.automation.elements.DHXDropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.NotificationsBarEnum;
import com.inthinc.pro.selenium.pageEnums.NotificationsCrashHistoryEnum;

public class PageNotificationsCrashHistory extends NotificationsBar {
    
    private String pageScroller = "crashHistory-form:crashHistory-bottomScroller_table";
	
    public PageNotificationsCrashHistory() {
        checkMe.add(NotificationsCrashHistoryEnum.TEAM_DROP_DOWN);
        checkMe.add(NotificationsCrashHistoryEnum.TITLE);
        page = "crashHistory";
    }
    
    public class NotificationsCrashHistoryLinks extends NotificationsBarLinks {
        
        @Override
        public TextLink editColumns() {
            return new TextLink(NotificationsBarEnum.EDIT_COLUMNS);    
        }
        
        public TextLink addCrashReport() {
            return new TextLink(NotificationsCrashHistoryEnum.ADD_CRASH_REPORT);
        }
        
        public TextLink sortByDateTime(){
            return new TextLink(NotificationsBarEnum.SORT_DATE_TIME);
        }
        
        public TextLink sortByGroup(){
            return new TextLink(NotificationsBarEnum.SORT_GROUP);
        }
        
        public TextLink sortByDriver(){
            return new TextLink(NotificationsBarEnum.SORT_DRIVER);
        }
        
        public TextLink sortByVehicle(){
            return new TextLink(NotificationsBarEnum.SORT_VEHICLE);
        }
        
        public TextLink sortByOccupants(){
            return new TextLink(NotificationsCrashHistoryEnum.SORT_OCCUPANTS);
        }
        
        public TextLink sortByStatus(){
            return new TextLink(NotificationsCrashHistoryEnum.SORT_STATUS);
        }
        
        public TextLink sortByWeather(){
            return new TextLink(NotificationsCrashHistoryEnum.SORT_WEATHER);
        }
        
        public TextTableLink entryGroupName() {
            return new TextTableLink(NotificationsBarEnum.GROUP_ENTRY);
        }
        
        public TextTableLink entryDriverName() {
            return new TextTableLink(NotificationsBarEnum.DRIVER_ENTRY);
        }
        
        public TextTableLink entryVehicleName() {
            return new TextTableLink(NotificationsBarEnum.VEHICLE_ENTRY);
        }
        
        public TextTableLink entryDetails() {
            return new TextTableLink(NotificationsCrashHistoryEnum.DETAILS_ENTRY);
        }
    }
    
    public class NotificationsCrashHistoryButtons extends NotificationsBarButtons {
        
        public Button refresh() {
            return new Button(NotificationsBarEnum.REFRESH);
        }
        
        public Button tools() {
            return new Button(NotificationsBarEnum.TOOLS);
        }
        
        public Button exportToPDF() {
            return new Button(NotificationsBarEnum.EXPORT_TO_PDF);
        }
        
        public Button emailThisReport() {
            return new Button(NotificationsBarEnum.EMAIL_REPORT);
        }
        
        public Button exportToExcel() {
            return new Button(NotificationsBarEnum.EXPORT_TO_EXCEL);
        }
        
        public Button search() {
            return new Button(NotificationsCrashHistoryEnum.SEARCH_BUTTON);
        }
        
        public ButtonTable entryLocation() {
        	return new ButtonTable(NotificationsBarEnum.LOCATION_ENTRY);
        }
        
    }
    
    public class NotificationsCrashHistoryDropDown extends NotificationsBarDropDowns {
    	SeleniumEnums[] enums = {NotificationsCrashHistoryEnum.TIME_FRAME_DROP_DOWN, NotificationsCrashHistoryEnum.TEAM_DROP_DOWN};
        
        public DHXDropDown team() {
            return new DHXDropDown(NotificationsCrashHistoryEnum.TEAM_DROP_DOWN, enums);
        }
        
        public DHXDropDown timeFrame() {
            return new DHXDropDown(NotificationsCrashHistoryEnum.TIME_FRAME_DROP_DOWN, enums );
        }
    }
    
    public class NotificationsCrashHistoryPopUps extends MastheadPopUps {
        
        public NotificationsCrashHistoryPopUps() {
            super(page, Types.REPORT, 3);
        }
        
        public EditColumns editColumns() {
            return new EditColumns();
        }
        
        public Email emailReport() {
            return new Email();
        }
        
        public LocationPopUp location(){
            return new LocationPopUp();
        }
        
    }
    
    public class NotificationsCrashHistoryTextFields extends NotificationsBarTextFields {
        
        public TextField search() {
            return new TextField(NotificationsCrashHistoryEnum.SEARCH_BOX);
        }
    }
    
    public class NotificationsCrashHistoryTexts extends NotificationsBarTexts {
        
        public TextTable dateTime() {
            return new TextTable(NotificationsBarEnum.DATE_TIME_ENTRY);
        }
        
        public TextTable entryOccupants() {
            return new TextTable(NotificationsCrashHistoryEnum.OCCUPANTS_ENTRY);
        }
        
        public TextTable entryStatus() {
            return new TextTable(NotificationsCrashHistoryEnum.STATUS_ENTRY);
        }
        
        public TextTable entryWeather() {
            return new TextTable(NotificationsCrashHistoryEnum.WEATHER_ENTRY);
        }
        
        public Text title(){
            return new Text(NotificationsCrashHistoryEnum.TITLE);
        }
        
        public Text counter(){
            return new Text(NotificationsBarEnum.COUNTER);
        }
    }
    
    public NotificationsCrashHistoryLinks _link() {
        return new NotificationsCrashHistoryLinks();
    }
    
    public NotificationsCrashHistoryButtons _button() {
        return new NotificationsCrashHistoryButtons();
    }
    
    public NotificationsCrashHistoryDropDown _dropDown() {
        return new NotificationsCrashHistoryDropDown();
    }
    
    public NotificationsCrashHistoryPopUps _popUp() {
        return new NotificationsCrashHistoryPopUps();
    }
    
    public NotificationsCrashHistoryTextFields _textField() {
        return new NotificationsCrashHistoryTextFields();
    }
    
    public NotificationsCrashHistoryTexts _text() {
        return new NotificationsCrashHistoryTexts();
    }
    
    public Paging _page() {
        return new Paging(pageScroller);
    }

    @Override
    public SeleniumEnums setUrl() {
        return NotificationsCrashHistoryEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _link().addCrashReport().isPresent();
    }
}
