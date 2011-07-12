package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.selenium.pageEnums.NotificationsZonesEnum;

public class PageNotificationsZones extends NotificationsBar {

    public PageNotificationsZones() {
        page = "zone";
        super.url = NotificationsZonesEnum.URL;
        super.checkMe.add(NotificationsZonesEnum.TITLE);
        super.checkMe.add(NotificationsZonesEnum.NOTE);
    }
    
    
    public class ZonesLinks extends NotificationsBarLinks{
        
    }
    public class ZonesTexts extends NotificationsBarTexts{
        
        public Text title(){
            return new Text(NotificationsZonesEnum.TITLE);
        }
        
        public Text note(){
            return new Text(NotificationsZonesEnum.NOTE);
        }
        



    }
    public class ZonesTextFields extends NotificationsBarTextFields{
        
    }
    public class ZonesButtons extends NotificationsBarButtons{
        
    }
    public class ZonesDropDowns extends NotificationsBarDropDowns{
        
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

}
