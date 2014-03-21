package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.NotificationsCrashHistoryEnum;
import com.inthinc.pro.selenium.pageObjects.Masthead.Paging;

public class PageNotificationsCrashHistory extends NotificationsEventsBar {
    
    private String pageScroller = "crashHistory-form:crashHistory-bottomScroller_table";
	
    public PageNotificationsCrashHistory() {
    	
//        checkMe.add(NotificationsCrashHistoryEnum.TEAM_DROP_DOWN);
//        checkMe.add(NotificationsCrashHistoryEnum.TITLE);
        page = "crashHistory";
    }
    
    public class NotificationsCrashHistoryLinks extends EventsBarLinks {
        
        public TextLink addCrashReport() {
            return new TextLink(NotificationsCrashHistoryEnum.ADD_CRASH_REPORT);
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
        
        public TextTableLink entryDetails() {
            return new TextTableLink(NotificationsCrashHistoryEnum.DETAILS_ENTRY);
        }
    }
    
    public class NotificationsCrashHistoryButtons extends EventsBarButtons {
        
        public Button search() {
            return new Button(NotificationsCrashHistoryEnum.SEARCH_BUTTON);
        }
        
    }
    
    public class NotificationsCrashHistoryDropDown extends EventsBarDropDowns {}
    
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
    
    public class NotificationsCrashHistoryTextFields extends EventsBarTextFields {
        
        public TextField search() {
            return new TextField(NotificationsCrashHistoryEnum.SEARCH_BOX);
        }
    }
    
    public class NotificationsCrashHistoryTexts extends EventsBarTexts {
        
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

    }
    
    public class NotificationsCrashHistoryPager{
        public Paging pageIndex(){
            return new Paging();
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
        return _link().addCrashReport().isPresent() &&
                        _button().search().isPresent();
    }
}
