package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.TeamLiveEnum;

public class PageTeamLiveTeam extends TeamBar {
	
	
	public class TeamLiveTeamLinks extends TeamBarLinks{}
	public class TeamLiveTeamTexts extends TeamBarTexts{}
	public class TeamLiveTeamTextFields extends TeamBarTextFields{}
	public class TeamLiveTeamButtons extends TeamBarButtons{
		
		public Button refresh(){
			return new Button(TeamLiveEnum.REFRESH);
		}
	}
	public class TeamLiveTeamDropDowns extends TeamBarDropDowns{}
	public class TeamLiveTeamPopUps extends MastheadPopUps{}
	
	public TeamLiveTeamLinks _link(){
        return new TeamLiveTeamLinks();
    }
    
    public TeamLiveTeamTexts _text(){
        return new TeamLiveTeamTexts();
    }
        
    public TeamLiveTeamButtons _button(){
        return new TeamLiveTeamButtons();
    }
    
    public TeamLiveTeamTextFields _textField(){
        return new TeamLiveTeamTextFields();
    }
    
    public TeamLiveTeamDropDowns _dropDown(){
        return new TeamLiveTeamDropDowns();
    }
    
    public TeamLiveTeamPopUps _popUp(){
        return new TeamLiveTeamPopUps();
    }

    @Override
    public SeleniumEnums setUrl() {
        return TeamLiveEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        // TODO Auto-generated method stub
        return false;
    }
    

}
