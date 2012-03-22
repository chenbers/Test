package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.NotificationsZonesEnum;

public class PageNotificationsZones extends NotificationsEventsBar {

    public PageNotificationsZones() {
        page = "zone";
        super.checkMe.add(NotificationsZonesEnum.TITLE);
        super.checkMe.add(NotificationsZonesEnum.NOTE);
    }
    
    
    public class ZonesLinks extends EventsBarLinks{
        
    }
    public class ZonesTexts extends EventsBarTexts{
        
        public Text title(){
            return new Text(NotificationsZonesEnum.TITLE);
        }
        
        public Text note(){
            return new Text(NotificationsZonesEnum.NOTE);
        }
        



    }
    public class ZonesTextFields extends EventsBarTextFields{
        
    }
    public class ZonesButtons extends EventsBarButtons{
        
    }
    public class ZonesDropDowns extends EventsBarDropDowns{
        
    }
    public class ZonesPopUps extends MastheadPopUps{
        
        public ZonesPopUps() {
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
    
    
    public class ZonesPager{
        public Paging pageIndex(){
            return new Paging();
        }
    }
    
    public ZonesLinks _link(){
        return new ZonesLinks();
    }
    
    public ZonesTexts _text(){
        return new ZonesTexts();
    }
        
    public ZonesButtons _button(){
        return new ZonesButtons();
    }
    
    public ZonesTextFields _textField(){
        return new ZonesTextFields();
    }
    
    public ZonesDropDowns _dropDown(){
        return new ZonesDropDowns();
    }
    
    public ZonesPopUps _popUp(){
        return new ZonesPopUps();
    }
    
    public ZonesPager _page(){
        return new ZonesPager();
    }

    @Override
    public SeleniumEnums setUrl() {
        return NotificationsZonesEnum.URL;
    }

}
