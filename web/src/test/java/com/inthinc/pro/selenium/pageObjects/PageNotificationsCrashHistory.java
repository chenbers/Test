package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.DHXDropDown;
import com.inthinc.pro.automation.elements.SortHeader;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.NotificationsCrashHistoryEnum;

public class PageNotificationsCrashHistory extends NotificationsBar {
    
    public PageNotificationsCrashHistory() {
        checkMe.add(NotificationsCrashHistoryEnum.TEAM_DROP_DOWN);
        checkMe.add(NotificationsCrashHistoryEnum.TITLE);
        page = "crashHistory";
    }
    
    public class NotificationsCrashHistoryLinks extends NotificationsBarLinks {
        
        @Override
        public TextLink editColumns() {
            return new TextLink(NotificationsCrashHistoryEnum.EDIT_COLUMNS_LINK);    
        }
        
        public TextLink addCrashReport() {
            return new TextLink(NotificationsCrashHistoryEnum.ADD_CRASH_REPORT_LINK);
        }
        
        public TextTableLink groupName() {
            return new TextTableLink(NotificationsCrashHistoryEnum.GROUP_TABLE);
        }
        
        public TextTableLink driverName() {
            return new TextTableLink(NotificationsCrashHistoryEnum.DRIVER_TABLE);
        }
        
        public TextTableLink vehicleName() {
            return new TextTableLink(NotificationsCrashHistoryEnum.VEHICLE_TABLE);
        }
        
        public TextTableLink details() {
            return new TextTableLink(NotificationsCrashHistoryEnum.DETAIL_TABLE);
        }
    }
    
    public class NotificationsCrashHistoryButtons extends NotificationsBarButtons {
        
        public Button refresh() {
            return new Button(NotificationsCrashHistoryEnum.REFRESH_BUTTON);
        }
        
        public Button search() {
            return new Button(NotificationsCrashHistoryEnum.SEARCH_BUTTON);
        }
        
        public Button tools() {
            return new Button(NotificationsCrashHistoryEnum.TOOLS_BUTTON);
        }
        
        public Button exportToPDF() {
            return new Button(NotificationsCrashHistoryEnum.EXPORT_PDF);
        }
        
        public Button emailThisReport() {
            return new Button(NotificationsCrashHistoryEnum.EXPORT_EMAIL);
        }
        
        public Button exportToExcel() {
            return new Button(NotificationsCrashHistoryEnum.EXPORT_EXCEL);
        }
        
        public TextButton sortByDateTime(){
            return new SortHeader(NotificationsCrashHistoryEnum.DATE_TIME_TABLE);
        }
        
        public TextButton sortGroup(){
            return new SortHeader(NotificationsCrashHistoryEnum.GROUP_TABLE);
        }
        
        public TextButton sortDriver(){
            return new SortHeader(NotificationsCrashHistoryEnum.DRIVER_TABLE);
        }
        
        public TextButton sortVehicle(){
            return new SortHeader(NotificationsCrashHistoryEnum.VEHICLE_TABLE);
        }
        
        public TextButton sortOccupants(){
            return new SortHeader(NotificationsCrashHistoryEnum.OCCUPANTS_TABLE);
        }
        
        public TextButton sortStatus(){
            return new SortHeader(NotificationsCrashHistoryEnum.STATUS_TABLE);
        }
        
        public TextButton sortWeather(){
            return new SortHeader(NotificationsCrashHistoryEnum.WEATHER_TABLE);
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
            return new TextTable(NotificationsCrashHistoryEnum.DATE_TIME_TABLE);
        }
        
        public TextTable occupants() {
            return new TextTable(NotificationsCrashHistoryEnum.OCCUPANTS_TABLE);
        }
        
        public TextTable status() {
            return new TextTable(NotificationsCrashHistoryEnum.STATUS_TABLE);
        }
        
        public TextTable weather() {
            return new TextTable(NotificationsCrashHistoryEnum.WEATHER_TABLE);
        }
        
        public Text title(){
            return new Text(NotificationsCrashHistoryEnum.TITLE);
        }
        
        public Text counter(){
            return new Text(NotificationsCrashHistoryEnum.COUNTER);
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

    @Override
    public SeleniumEnums setUrl() {
        return NotificationsCrashHistoryEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _link().addCrashReport().isPresent();
    }
}
