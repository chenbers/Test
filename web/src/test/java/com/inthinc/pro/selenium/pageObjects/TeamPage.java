package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.selenium.pageEnums.TeamPageEnum;

public class TeamPage extends NavigationBar{
    
    public void link_tableDriver_click(String driverFullName) {
        selenium.click("link="+driverFullName);
        
        // makes sure the next "thing" is there
        selenium.Pause(10);
    }
}
