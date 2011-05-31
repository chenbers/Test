package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.ButtonTable;
import com.inthinc.pro.automation.elements.DhxDropDown;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.NotificationsBarEnum;
import com.inthinc.pro.selenium.pageEnums.NotificationsRedFlagsEnum;


public class PageNotificationsRedFlags extends NotificationsBar {
	private static String page = "redFlags";
	
	public PageNotificationsRedFlags(){
		super.setPage(page);
	}
	
	
	public class RedFlagsLinks extends NotificationsBarLinks{//TODO: dtanner: Still Updating  change enums to main Notifications, and provide changes
		public TextLink editColumns(){
			return new TextLink(NotificationsBarEnum.EDIT_COLUMNS, page);
		}
		
		public TextLink alertDetailsEntry(){
			return new TextLink(NotificationsRedFlagsEnum.ALERT_LEVEL_ENTRY);
		}
		
		public TextLink groupEntry(){
			return new TextLink(NotificationsRedFlagsEnum.GROUP_ENTRY);
		}
		
		public TextLink driverEntry(){
			return new TextLink(NotificationsRedFlagsEnum.DRIVER_ENTRY);
		}
		
		public TextLink vehicleEntry(){
			return new TextLink(NotificationsRedFlagsEnum.VEHICLE_ENTRY);
		}
		
		public TextLink statusEntry(){
			return new TextLink(NotificationsRedFlagsEnum.STATUS_ENTRY);
		}
		
		
	}
	
	public class RedFlagsTexts extends NotificationsBarTexts{}
	
	public class RedFlagsTextFields extends NotificationsBarTextFields{
		
		public TextField group(){
			return new TextField(NotificationsRedFlagsEnum.GROUP_FILTER);
		}
		
		public TextField driver(){
			return new TextField(NotificationsRedFlagsEnum.DRIVER_FILTER);
		}
		
		public TextField vehicle(){
			return new TextField(NotificationsRedFlagsEnum.VEHICLE_FILTER);
		}
	}
	
	public class RedFlagsButtons extends NotificationsBarButtons{
		
		public TextButton refresh(){
			return new TextButton(NotificationsBarEnum.REFRESH, page);
		}
		
		public Button tools(){
			return new Button(NotificationsBarEnum.TOOLS, page);
		}
		
		public Button exportToPDF(){
			return new Button(NotificationsBarEnum.EXPORT_TO_PDF, page);
		}
		
		public Button emailReport(){
			return new Button(NotificationsBarEnum.EMAIL_REPORT, page);
		}
		
		public Button exportToExcel(){
			return new Button(NotificationsBarEnum.EXPORT_TO_EXCEL, page);
		}
		
		public ButtonTable eventLocation(){
			return new ButtonTable(NotificationsBarEnum.LOCATION, page);
		}
		
	}
	
	public class RedFlagsDropDowns extends NotificationsBarDropDowns{
		
		public DhxDropDown team(){
			return new DhxDropDown(NotificationsBarEnum.TEAM_SELECTION_DHX, page);
		}
		
		public DhxDropDown timeFrame(){
			return new DhxDropDown(NotificationsBarEnum.TIME_FRAME_DHX, page);
		}
		
		public DhxDropDown levelFilter(){
			return new DhxDropDown(NotificationsBarEnum.LEVEL_FILTER_DHX, page);
		}
		
		public DropDown category(){
			return new DropDown(NotificationsRedFlagsEnum.CATEGORY_FILTER);
		}
		
		public DropDown statusFilter(){
			return new DropDown(NotificationsRedFlagsEnum.STATUS_FILTER);
		}
	}
	public class RedFlagsPopUps extends MastheadPopUps{}
	
	public RedFlagsLinks _link(){
        return new RedFlagsLinks();
    }
    
    public RedFlagsTexts _text(){
        return new RedFlagsTexts();
    }
        
    public RedFlagsButtons _button(){
        return new RedFlagsButtons();
    }
    
    public RedFlagsTextFields _textField(){
        return new RedFlagsTextFields();
    }
    
    public RedFlagsDropDowns _dropDown(){
        return new RedFlagsDropDowns();
    }
    
    public RedFlagsPopUps _popUp(){
        return new RedFlagsPopUps();
    }
    

    @Override
    public String getExpectedPath() {
        return NotificationsRedFlagsEnum.DEFAULT_URL.getURL();
    }
}
