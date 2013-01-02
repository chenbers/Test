package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AdminRoadHazardsEnum;

public class PageAdminRoadHazards extends AdminBar {

    public PageAdminRoadHazards() {
        url = AdminRoadHazardsEnum.DEFAULT_URL;
        checkMe.add(AdminRoadHazardsEnum.TITLE);
    }

    
    public class AdminRoadHazardsLinks extends AdminBarLinks {
    	
    	public TextLink sortByRegion() {
    		return new TextLink(AdminRoadHazardsEnum.SORT_BY_REGION_LINK);
    	}
    	
    	public TextLink sortByLocation() {
    		return new TextLink(AdminRoadHazardsEnum.SORT_BY_LOCATION_LINK);
    	}
    	
    	public TextLink sortByDescription() {
    		return new TextLink(AdminRoadHazardsEnum.SORT_BY_DESCRIPTION_LINK);
    	}
    	
    	public TextLink sortByDriver() {
    		return new TextLink(AdminRoadHazardsEnum.SORT_BY_DRIVER_LINK);
    	}
    	
    	public TextLink sortByUser() {
    		return new TextLink(AdminRoadHazardsEnum.SORT_BY_USER_LINK);
    	}
    	
    	public TextLink sortByType() {
    		return new TextLink(AdminRoadHazardsEnum.SORT_BY_TYPE_LINK);
    	}
    	
    	public TextLink sortByStatus() {
    		return new TextLink(AdminRoadHazardsEnum.SORT_BY_STATUS_LINK);
    	}
    	
    	public TextLink sortByStartTime() {
    		return new TextLink(AdminRoadHazardsEnum.SORT_BY_START_TIME_LINK);
    	}
    	
    	public TextLink sortByExpTime() {
    		return new TextLink(AdminRoadHazardsEnum.SORT_BY_EXP_TIME_LINK);
    	}
    	
    	public TextLink entryDriver() {
    		return new TextLink(AdminRoadHazardsEnum.ENTRY_DRIVER_LINK);
    	}
    	
    	public TextLink entryEdit() {
    		return new TextLink(AdminRoadHazardsEnum.ENTRY_EDIT_LINK);
    	}

    }

    public class AdminRoadHazardsTexts extends AdminBarTexts {
        
        public Text title(){
            return new Text(AdminRoadHazardsEnum.TITLE);
        }
        
        public Text entryRegion(){
            return new Text(AdminRoadHazardsEnum.ENTRY_REGION_TEXT);
        }
        
        public Text entryLocation(){
            return new Text(AdminRoadHazardsEnum.ENTRY_LOCATION_TEXT);
        }
        
        public Text entryDescription(){
            return new Text(AdminRoadHazardsEnum.ENTRY_DESCRIPTION_TEXT);
        }
        
        public Text entryUser(){
            return new Text(AdminRoadHazardsEnum.ENTRY_USER_TEXT);
        }
        
        public Text entryType(){
            return new Text(AdminRoadHazardsEnum.ENTRY_TYPE_TEXT);
        }
        
        public Text entryStatus(){
            return new Text(AdminRoadHazardsEnum.ENTRY_STATUS_TEXT);
        }
        
        public Text entryStartingTime(){
            return new Text(AdminRoadHazardsEnum.ENTRY_STARTING_TIME_TEXT);
        }
        
        public Text entryExpTime(){
            return new Text(AdminRoadHazardsEnum.ENTRY_EXP_TIME_TEXT);
        }
    }

    public class AdminRoadHazardsTextFields extends AdminBarTextFields {}

    public class AdminRoadHazardsButtons extends AdminBarButtons {
        
    	public TextButton addHazard() {
    		return new TextButton(AdminRoadHazardsEnum.ADD_HAZARD_BUTTON);
    	}
    	
        public TextButton entryMap(){
            return new TextButton(AdminRoadHazardsEnum.ENTRY_MAP_BUTTON);
        }
           
    }

    public class AdminRoadHazardsDropDowns extends AdminBarDropDowns {}

    public class AdminRoadHazardsPopUps extends MastheadPopUps {}

    public class AdminRoadHazardsPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public AdminRoadHazardsPager _page() {
        return new AdminRoadHazardsPager();
    }

    public AdminRoadHazardsLinks _link() {
        return new AdminRoadHazardsLinks();
    }

    public AdminRoadHazardsTexts _text() {
        return new AdminRoadHazardsTexts();
    }

    public AdminRoadHazardsButtons _button() {
        return new AdminRoadHazardsButtons();
    }

    public AdminRoadHazardsTextFields _textField() {
        return new AdminRoadHazardsTextFields();
    }

    public AdminRoadHazardsDropDowns _dropDown() {
        return new AdminRoadHazardsDropDowns();
    }

    public AdminRoadHazardsPopUps _popUp() {
        return new AdminRoadHazardsPopUps();
    }

    @Override
    public SeleniumEnums setUrl() {
        return AdminRoadHazardsEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        // TODO Auto-generated method stub
        return false;
    }
}
