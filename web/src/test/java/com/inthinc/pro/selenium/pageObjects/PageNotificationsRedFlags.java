package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DhxDropDown;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.selenium.pageEnums.NotificationsBarEnum;
import com.inthinc.pro.selenium.pageEnums.NotificationsRedFlagsEnum;


public class PageNotificationsRedFlags extends NotificationsBar {
	private static String page = "redFlags";
	
	public PageNotificationsRedFlags(){
		super.setPage(page);
	}
	
	
	public class RedFlagsLinks extends NotificationsBarLinks{}
	
	public class RedFlagsTexts extends NotificationsBarTexts{}
	
	public class RedFlagsTextFields extends NotificationsBarTextFields{}
	
	public class RedFlagsButtons extends NotificationsBarButtons{
		
		
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
	public class RedFlagsPopUps extends PopUps{}
	
	public RedFlagsLinks _links(){
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
