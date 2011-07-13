package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.NotificationsBarEnum;
import com.inthinc.pro.selenium.pageEnums.NotificationsDiagnosticsEnum;


public class PageNotificationsDiagnostics extends NotificationsEventsBar {
    
    public PageNotificationsDiagnostics() {
        page = "diagnostics";
        super.enums = new SeleniumEnums[]{ NotificationsBarEnum.TIME_FRAME_DHX, NotificationsBarEnum.TEAM_SELECTION_DHX };

    }
    
    
    public class DiagnosticsLinks extends EventsBarLinks{
        
    }
    public class DiagnosticsTexts extends EventsBarTexts{
        
        public Text title(){
            return new Text(NotificationsDiagnosticsEnum.TITLE);
        }
        
        public Text note(){
            return new Text(NotificationsDiagnosticsEnum.MESSAGE);
        }
        
    }
    public class DiagnosticsTextFields extends EventsBarTextFields{
    }
    public class DiagnosticsButtons extends EventsBarButtons{
    }
    public class DiagnosticsDropDowns extends EventsBarDropDowns{
        
    }
    public class DiagnosticsPopUps extends MastheadPopUps{
        
        public DiagnosticsPopUps() {
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
    
    
    public class DiagnosticsPager{
        public Paging pageIndex(){
            return new Paging();
        }
    }
    
    public DiagnosticsLinks _link(){
        return new DiagnosticsLinks();
    }
    
    public DiagnosticsTexts _text(){
        return new DiagnosticsTexts();
    }
        
    public DiagnosticsButtons _button(){
        return new DiagnosticsButtons();
    }
    
    public DiagnosticsTextFields _textField(){
        return new DiagnosticsTextFields();
    }
    
    public DiagnosticsDropDowns _dropDown(){
        return new DiagnosticsDropDowns();
    }
    
    public DiagnosticsPopUps _popUp(){
        return new DiagnosticsPopUps();
    }
    
    public DiagnosticsPager _page(){
        return new DiagnosticsPager();
    }
    
}
