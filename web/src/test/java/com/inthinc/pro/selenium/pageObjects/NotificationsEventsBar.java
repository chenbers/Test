package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.ButtonTable;
import com.inthinc.pro.automation.elements.DHXDropDown;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.NotificationsBarEnum;

public abstract class NotificationsEventsBar extends NotificationsBar {
	
    protected SeleniumEnums[] enums = { NotificationsBarEnum.TIME_FRAME_DHX, NotificationsBarEnum.TEAM_SELECTION_DHX, NotificationsBarEnum.LEVEL_FILTER_DHX };
	
	protected class EventsBarButtons extends NotificationsBarButtons{
	    
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
	protected class EventsBarTextFields extends NotificationsBarTextFields{
	    
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
	protected class EventsBarTexts extends NotificationsBarTexts{

        public Text headerDetail() {
            return new Text(NotificationsBarEnum.HEADER_DETAIL, page);
        }

        public Text headerStatus() {
            return new Text(NotificationsBarEnum.HEADER_STATUS, page);
        }

        public Text headerCategory() {
            return new Text(NotificationsBarEnum.HEADER_CATEGORY, page);
        }

        public TextTable entryDateTime() {
            return new TextTable(NotificationsBarEnum.DATE_TIME_ENTRY, page);
        }

        public TextTable entryCategory() {
            return new TextTable(NotificationsBarEnum.CATEGORY_ENTRY, page);
        }

        public TextTable entryDetail() {
            return new TextTable(NotificationsBarEnum.DETAIL_ENTRY, page);
        }

        public Text counter() {
            return new Text(NotificationsBarEnum.COUNTER, page);
        }
	}
	
	protected class EventsBarDropDowns extends NotificationsBarDropDowns{
	    
	    
	    public DHXDropDown team() {
            return new DHXDropDown(NotificationsBarEnum.TEAM_SELECTION_DHX, page, enums);
        }

        public DHXDropDown timeFrame() {
            return new DHXDropDown(NotificationsBarEnum.TIME_FRAME_DHX, page, enums);
        }


        public DropDown category() {
            return new DropDown(NotificationsBarEnum.CATEGORY_FILTER, page);
        }

        public DropDown statusFilter() {
            return new DropDown(NotificationsBarEnum.STATUS_FILTER, page);
        }
	}
	

	protected class EventsBarLinks extends NotificationsBarLinks{
	    

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
        
        public TextLink sortByGroup() {
            return new TextLink(NotificationsBarEnum.SORT_GROUP, page);
        }

        public TextLink sortByDriver() {
            return new TextLink(NotificationsBarEnum.SORT_DRIVER, page);
        }

        public TextLink sortByVehicle() {
            return new TextLink(NotificationsBarEnum.SORT_VEHICLE, page);
        }

        public TextLink sortByDateTime() {
            return new TextLink(NotificationsBarEnum.SORT_DATE_TIME, page);
        }
		
	}
}
