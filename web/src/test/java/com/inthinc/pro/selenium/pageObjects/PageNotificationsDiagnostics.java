package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.ButtonTable;
import com.inthinc.pro.automation.elements.DhxDropDown;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.NotificationsBarEnum;
import com.inthinc.pro.selenium.pageEnums.NotificationsDiagnosticsEnum;


public class PageNotificationsDiagnostics extends NotificationsBar {
    
    private String page = "diagnostics";

    public PageNotificationsDiagnostics() {
        // TODO Auto-generated constructor stub
    }
    
    
    public class DiagnosticsLinks extends NotificationsBarLinks{
        
        public TextLink editColumns(){
            return new TextLink(NotificationsBarEnum.EDIT_COLUMNS, page);
        }
        
        public TextTableLink entryGroup(){
            return new TextTableLink(NotificationsBarEnum.GROUP_ENTRY, page);
        }
        
        public TextTableLink entryDriver(){
            return new TextTableLink(NotificationsBarEnum.DRIVER_ENTRY, page);
        }
        
        public TextTableLink entryVehicle(){
            return new TextTableLink(NotificationsBarEnum.VEHICLE_ENTRY, page);
        }
        
        public TextTableLink entryStatus(){
            return new TextTableLink(NotificationsBarEnum.STATUS_ENTRY, page);
        }
    }
    public class DiagnosticsTexts extends NotificationsBarTexts{
        
        public Text title(){
            return new Text(NotificationsDiagnosticsEnum.TITLE);
        }
        
        public Text note(){
            return new Text(NotificationsDiagnosticsEnum.MESSAGE);
        }
        

        public Text headerDetail() {
            return new Text(NotificationsBarEnum.HEADER_DETAIL, page);
        }

        public Text headerStatus() {
            return new Text(NotificationsBarEnum.HEADER_STATUS, page);
        }

        public Text headerCategory() {
            return new Text(NotificationsBarEnum.HEADER_CATEGORY, page);
        }

        public TextTable dateTimeEntry() {
            return new TextTable(NotificationsBarEnum.DATE_TIME_ENTRY, page);
        }

        public TextTable categoryEntry() {
            return new TextTable(NotificationsBarEnum.CATEGORY_ENTRY, page);
        }

        public TextTable detailEntry() {
            return new TextTable(NotificationsBarEnum.DETAIL_ENTRY, page);
        }

        public Text counter() {
            return new Text(NotificationsBarEnum.COUNTER, page);
        }


    }
    public class DiagnosticsTextFields extends NotificationsBarTextFields{
        
        public TextField group() {
            return new TextField(NotificationsBarEnum.GROUP_FILTER, page);
        }

        public TextField driver() {
            return new TextField(NotificationsBarEnum.DRIVER_FILTER, page);
        }

        public TextField vehicle() {
            return new TextField(NotificationsBarEnum.VEHICLE_FILTER, page);
        }
    }
    public class DiagnosticsButtons extends NotificationsBarButtons{
        
        public TextButton refresh() {
            return new TextButton(NotificationsBarEnum.REFRESH, page);
        }

        public Button tools() {
            return new Button(NotificationsBarEnum.TOOLS, page);
        }

        public Button exportToPDF() {
            return new Button(NotificationsBarEnum.EXPORT_TO_PDF, page);
        }

        public Button emailReport() {
            return new Button(NotificationsBarEnum.EMAIL_REPORT, page);
        }

        public Button exportToExcel() {
            return new Button(NotificationsBarEnum.EXPORT_TO_EXCEL, page);
        }

        public ButtonTable eventLocation() {
            return new ButtonTable(NotificationsBarEnum.LOCATION, page);
        }
    }
    public class DiagnosticsDropDowns extends NotificationsBarDropDowns{
        
        private SeleniumEnums[] enums = { NotificationsBarEnum.TIME_FRAME_DHX, NotificationsBarEnum.TEAM_SELECTION_DHX };

        public DhxDropDown team() {
            return new DhxDropDown(NotificationsBarEnum.TEAM_SELECTION_DHX, page, enums);
        }

        public DhxDropDown timeFrame() {
            return new DhxDropDown(NotificationsBarEnum.TIME_FRAME_DHX, page, enums);
        }


        public DropDown category() {
            return new DropDown(NotificationsBarEnum.CATEGORY_FILTER, page);
        }

        public DropDown statusFilter() {
            return new DropDown(NotificationsBarEnum.STATUS_FILTER, page);
        }
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
