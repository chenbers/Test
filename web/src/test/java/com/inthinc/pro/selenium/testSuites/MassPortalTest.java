package com.inthinc.pro.selenium.testSuites;

import org.junit.Test;

import com.inthinc.pro.automation.models.AutomationUser;
import com.inthinc.pro.automation.objects.AutomationUsers;
import com.inthinc.pro.selenium.pageObjects.PageAdminUserDetails;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;

public class MassPortalTest extends WebTest {
    

    private PageAdminUsers my;
    private PageMyAccount myAccount;
    private PageAdminUserDetails myAdminUserDetails;
    private AutomationUser login;
    
    private MassPortalTest(){
        
    }
    
    
    @Test
    public void startTest(){
        
    }
    
    
    public class MassTest extends Thread {
        
        @Override
        public void run(){
            login = AutomationUsers.getUsers().getOne();
            try {
                
            } catch (Exception e){
                
            }
        }
    }

}
