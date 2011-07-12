package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.ButtonTable;
import com.inthinc.pro.automation.elements.DhxDropDown;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextLinkContextSense;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.NotificationsBarEnum;

public abstract class NotificationsBar extends NavigationBar {
	
	protected String page="redFlags";
    protected SeleniumEnums[] enums = { NotificationsBarEnum.TIME_FRAME_DHX, NotificationsBarEnum.TEAM_SELECTION_DHX, NotificationsBarEnum.LEVEL_FILTER_DHX };
	
	protected class NotificationsBarButtons extends NavigationBarButtons{
	    
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
	protected class NotificationsBarTextFields extends NavigationBarTextFields{
	    
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
	protected class NotificationsBarTexts extends NavigationBarTexts{

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
	protected class NotificationsBarDropDowns extends NavigationBarDropDowns{
	    
	    
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
	

	protected class NotificationsBarLinks extends NavigationBarLinks{
	    

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
		
		public TextLinkContextSense redFlags(){
			return new TextLinkContextSense(NotificationsBarEnum.RED_FLAGS, page);
		}
	
	    public TextLinkContextSense safety() {
	    	return new TextLinkContextSense(NotificationsBarEnum.SAFETY, page);
	    }
	
	    public TextLinkContextSense diagnostics() {
	    	return new TextLinkContextSense(NotificationsBarEnum.DIAGNOSTICS, page);
	    }
	
	    public TextLinkContextSense zones() {
	    	return new TextLinkContextSense(NotificationsBarEnum.ZONES, page);
	    }
	
	    public TextLinkContextSense hosExceptions() {
	    	return new TextLinkContextSense(NotificationsBarEnum.HOS_EXCEPTIONS, page);
	    }
	
	    public TextLinkContextSense emergency() {
	    	return new TextLinkContextSense(NotificationsBarEnum.SAFETY, page);
	    }
	
	    public TextLinkContextSense crashHistory() {
	    	return new TextLinkContextSense(NotificationsBarEnum.SAFETY);
	    }
	}
}
