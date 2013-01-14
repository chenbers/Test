package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.NotificationsBarEnum;
import com.inthinc.pro.selenium.pageEnums.NotificationsZonesEnum;

public class PageNotificationsZones extends NotificationsEventsBar {

    public PageNotificationsZones() {
    	
        enums = new SeleniumEnums[] { NotificationsBarEnum.TIME_FRAME_DHX, NotificationsBarEnum.TEAM_SELECTION_DHX };
    	
        page = "zoneEvents";
        url = NotificationsZonesEnum.DEFAULT_URL;
        checkMe.add(NotificationsZonesEnum.MAIN_TITLE);
        checkMe.add(NotificationsZonesEnum.MAIN_TITLE_COMMENT);
    }

    public class NotificationsZonesLinks extends EventsBarLinks{}
    
    public class NotificationsZonesTexts extends EventsBarTexts{
        
        public Text title() {
            return new Text(NotificationsZonesEnum.MAIN_TITLE);
        }
        
        public Text note() {
            return new Text(NotificationsZonesEnum.MAIN_TITLE_COMMENT);
        }
    }
    
    public class NotificationsZonesTextFields extends EventsBarTextFields{}
    
    public class NotificationsZonesButtons extends EventsBarButtons{}
    
    public class NotificationsZonesDropDowns extends EventsBarDropDowns{}
    
    public class NotificationsZonesPopUps extends MastheadPopUps{
        
        public NotificationsZonesPopUps() {
            super(page, Types.REPORT, 3);
        }

        public EditColumns editColumns() {
            return new EditColumns();
        }

        public Email emailReport() {
            return new Email();
        }
        
        public ExcludeEvent excludeEvent() {
            return new ExcludeEvent(true);
        }
        
        public LocationPopUp location() {
            return new LocationPopUp();
        }
    }
    
    public NotificationsZonesLinks _link() {
        return new NotificationsZonesLinks();
    }
    
    public NotificationsZonesTexts _text() {
        return new NotificationsZonesTexts();
    }
        
    public NotificationsZonesButtons _button() {
        return new NotificationsZonesButtons();
    }
    
    public NotificationsZonesTextFields _textField() {
        return new NotificationsZonesTextFields();
    }
    
    public NotificationsZonesDropDowns _dropDown() {
        return new NotificationsZonesDropDowns();
    }
    
    public NotificationsZonesPopUps _popUp() {
        return new NotificationsZonesPopUps();
    }
    
    public NotificationsZonesPager _page() {
        return new NotificationsZonesPager();
    }
    
    public class NotificationsZonesPager {
    	
        public Paging pageIndex() {
            return new Paging();
        }
    }

    @Override
    public SeleniumEnums setUrl() {
        return NotificationsZonesEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _link().editColumns().isPresent();
    }

}
