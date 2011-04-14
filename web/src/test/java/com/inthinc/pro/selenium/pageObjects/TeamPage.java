package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.GlobalSelenium;
import com.inthinc.pro.automation.selenium.Page;
import com.inthinc.pro.selenium.pageEnums.DashboardEnum;
import com.inthinc.pro.selenium.pageEnums.TeamPageEnum;

public class TeamPage extends AbstractPage{
    
    protected static CoreMethodLib selenium;

    public TeamPage(){
        selenium = GlobalSelenium.getSelenium();
    }
    
    public void link_tableDriver_click(String driverFullName) {
        selenium.click("link="+driverFullName);
        
        // makes sure the next "thing" is there
        selenium.Pause(10);
    }
    
    @Override
    public String getExpectedPath() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page load() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean validate() {
        // TODO Auto-generated method stub
        return false;
    } 

}
