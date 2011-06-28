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
import com.inthinc.pro.selenium.pageEnums.NotificationsBarEnum;
import com.inthinc.pro.selenium.pageEnums.NotificationsRedFlagsEnum;
import com.inthinc.pro.selenium.pageObjects.PopUps.EditColumns;
import com.inthinc.pro.selenium.pageObjects.PopUps.Email;
import com.inthinc.pro.selenium.pageObjects.PopUps.Types;


public class PageNotificationsRedFlags extends NotificationsBar {
	private static String page = "redFlags";
	
	public PageNotificationsRedFlags(){
		super.setPage(page);
		url = NotificationsRedFlagsEnum.DEFAULT_URL;
	}
	
	public class RedFlagsLinks extends NotificationsBarLinks{//TODO: dtanner: Still Updating  change enums to main Notifications, and provide changes
		public TextLink editColumns(){
			return new TextLink(NotificationsBarEnum.EDIT_COLUMNS, page);
		}
		
		public TextLink alertDetailsEntry(){
			return new TextLink(NotificationsBarEnum.DETAILS_ENTRY, page);
		}
		
		public TextLink groupEntry(){
			return new TextLink(NotificationsBarEnum.GROUP_ENTRY, page);
		}
		
		public TextLink driverEntry(){
			return new TextLink(NotificationsBarEnum.DRIVER_ENTRY, page);
		}
		
		public TextLink vehicleEntry(){
			return new TextLink(NotificationsBarEnum.VEHICLE_ENTRY, page);
		}
		
		public TextLink statusEntry(){
			return new TextLink(NotificationsBarEnum.STATUS_ENTRY, page);
		}
		
		
	}
	
	public class RedFlagsTexts extends NotificationsBarTexts{
	    
	    public TextTable levelEntry(){
		return new TextTable(NotificationsBarEnum.LEVEL_ENTRY, page);
	    }
	    
	    public TextTable dateTimeEntry(){
		return new TextTable(NotificationsBarEnum.DATE_TIME_ENTRY, page);
	    }
	    
	    public TextTable categoryEntry(){
		return new TextTable(NotificationsBarEnum.CATEGORY_ENTRY, page);
	    }
	    
	    public TextTable detailEntry(){
		return new TextTable(NotificationsBarEnum.DETAIL_ENTRY, page);
	    }
	    
	    public Text counter(){
		return new Text(NotificationsBarEnum.COUNTER, page);
	    }
	    
	    public Text title(){
		return new Text(NotificationsRedFlagsEnum.MAIN_TITLE);
	    }
	    
	    public Text note(){
		return new Text(NotificationsRedFlagsEnum.MAIN_TITLE_COMMENT);
	    }
	}
	
	public class RedFlagsTextFields extends NotificationsBarTextFields{
		
		public TextField group(){
			return new TextField(NotificationsBarEnum.GROUP_FILTER, page);
		}
		
		public TextField driver(){
			return new TextField(NotificationsBarEnum.DRIVER_FILTER, page);
		}
		
		public TextField vehicle(){
			return new TextField(NotificationsBarEnum.VEHICLE_FILTER, page);
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
			return new DropDown(NotificationsBarEnum.CATEGORY_FILTER, page);
		}
		
		public DropDown statusFilter(){
			return new DropDown(NotificationsBarEnum.STATUS_FILTER, page);
		}
	}
	public class RedFlagsPopUps extends MastheadPopUps{
	    
	    public RedFlagsPopUps(){
		    super(page, Types.REPORT, 3 );
		}
		
		public EditColumns editColumns(){
		    return new EditColumns();
		}
		
		public Email emailReport(){
		    return new Email();
		}
	}
	
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
