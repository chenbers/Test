package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CheckBoxTable;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.TeamTripsEnum;

public class PageTeamTrips extends TeamBar {
	
	public class TeamTripsLinks extends TeamBarLinks{
		
		public TextTableLink driverName(){
			return new TextTableLink(TeamTripsEnum.DRIVER_NAME);
		}
	}
	public class TeamTripsTexts extends TeamBarTexts{
		
		public TextTable driverLetter(){
			return new TextTable(TeamTripsEnum.DRIVER_LETTER);
		}
		
		public Text tableHeader(){
			return new Text(TeamTripsEnum.DRIVER_HEADER);
		}
	}
	public class TeamTripsTextFields extends TeamBarTextFields{}
	public class TeamTripsButtons extends TeamBarButtons{}
	public class TeamTripsDropDowns extends TeamBarDropDowns{}
	public class TeamTripsPopUps extends MastheadPopUps{}
	
	public CheckBoxTable _checkBox(){
		return new CheckBoxTable(TeamTripsEnum.CHECK_BOX_ENTRY);
	}
	
	public TeamTripsLinks _link(){
        return new TeamTripsLinks();
    }
    
    public TeamTripsTexts _text(){
        return new TeamTripsTexts();
    }
        
    public TeamTripsButtons _button(){
        return new TeamTripsButtons();
    }
    
    public TeamTripsTextFields _textField(){
        return new TeamTripsTextFields();
    }
    
    public TeamTripsDropDowns _dropDown(){
        return new TeamTripsDropDowns();
    }
    
    public TeamTripsPopUps _popUp(){
        return new TeamTripsPopUps();
    }

    @Override
    public SeleniumEnums setUrl() {
        return TeamTripsEnum.DEFAULT_URL;
    }
    
}
