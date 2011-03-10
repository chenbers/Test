package com.inthinc.pro.web.selenium.Test_Cases;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.Test;

import com.inthinc.pro.web.selenium.InthincTest;
import com.inthinc.pro.web.selenium.portal.Login.Login;

public class LoginScript extends InthincTest {
    
    @Test
    public void UI() {
        String [][] cmds = {
                            // Each line is the selenium method to invoke followed by the arguments it requires
                            // - Log in
                            {"open","logout"," page not found."},
                            {"type","j_username","0001"," Username type text"},
                            {"type","j_password","password"," Password type text"},
                            {"click","loginLogin"," Login button click"},
                            {"wait_for_element_present","Admin", " lnk"},
                            {"getLocation","tiwipro/app", "Login button click"}, 
                            
                            // - Re-position to login page
                            {"open","logout"," page not found."},
                            
                            // - Examine the ui for completeness, start with forgot your password
                            {"getText","//a[@title='Forgot your user name or password?']","Forgot your user name or password?"," text not present"},
                            {"isElementPresent","//a[@title='Forgot your user name or password?']"," element not present"},
                            
                            // - Login page
                            {"isTextPresent","LogIn"," element not present"},
                            {"getText","//form[@id='loginForm']/table/tbody/tr[3]/td/button/span","Login"," text not present"},
                            {"isElementPresent","//form[@id='loginForm']/table/tbody/tr[3]/td/button/span"," element not present"},
                            
                            {"isElementPresent","loginLogin"," element not present"},
                            
                            // - User name
                            {"isTextPresent","User Name:"," text not present"},
                            {"isElementPresent","//form[@id='loginForm']/table/tbody/tr[1]/td[1]"," element not present"},
                            {"getText","//form[@id='loginForm']/table/tbody/tr[1]/td[1]","User Name:"," text not present"},
                            
                            // - Password
                            {"isTextPresent","Password:"," text not present"},
                            {"isElementPresent","//form[@id='loginForm']/table/tbody/tr[2]/td[1]"," element not present"},
                            {"getText","//form[@id='loginForm']/table/tbody/tr[2]/td[1]","Password:"," text not present"}  
                                                
        };
        
        //create instance of library objects
        Login l = new Login();
        //Set up test data
        set_test_case("Tiwi_data.xls", "TC4632");

// prototype for testing a page driven by data only
/* cj -- commenting out because it wasn't compiling and broke the build        
        for (int row = 0; row < cmds.length; row++) {
            String [] cmd = cmds[row];
            System.out.println("Running: " + cmd[0]);
                try {
                    Class<?> c = l.getSelenium().getClass();
                    Class<?>[] argTypes = loadArgs(cmd.length-1);
                    Method main = c.getDeclaredMethod(cmd[0], argTypes);
                    Object[] mainArgs = Arrays.copyOfRange(cmd, 1, cmd.length);
                    Object o = main.invoke(l.getSelenium(), mainArgs);
                    
                    if ( o != null ) {
                        System.out.println("o has: " + o.toString());
                    }
                } catch (Exception e) {
                    System.out.println("Script failed: " + e.getMessage());
                }
            
        }
*/
    }
    
    private Class<?> [] loadArgs(int sz) {
        Class<?> [] tmp = new Class<?>[sz];
        
        for ( int i = 0; i < sz; i++ ) {
            tmp[i] = String.class;
        }
        
        return tmp;
    }
}
