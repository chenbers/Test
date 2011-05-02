/****************************************************************************************
 * Purpose: Used to process links selected on the Master Heading screen 
 * most functions are used regardless of what Main menu item is selected 
 * as Master Heading screen does not change. 
 * @author larringt , dtanner
 * Last Update:  11/18/Added comments and made changes to adhere to Java Coding Standards
 */

package com.inthinc.pro.selenium.pageObjects;


import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.selenium.pageEnums.MastheadEnum;

public abstract class Masthead extends AbstractPage{
    
    public class MastheadTexts {
        public Text version() {return new Text(MastheadEnum.VERSION);}
        public Text copyright() {return new Text(MastheadEnum.COPYRIGHT);}
    }
    public class MastheadTextFields {}
    public class MastheadLinks {
        public TextLink myAccount() {return new TextLink(MastheadEnum.MY_ACCOUNT);}
        public TextLink myMessages() {return new TextLink(MastheadEnum.MY_MESSAGES);}
        public TextLink logout() {return new TextLink(MastheadEnum.LOGOUT);}
        public TextLink support() {return new TextLink(MastheadEnum.SUPPORT);}
        public TextLink legalNotice() {return new TextLink(MastheadEnum.LEGAL_NOTICE);}
        public TextLink privacyPolicy() {return new TextLink(MastheadEnum.PRIVACY_POLICY);}
        public TextLink help() {return new TextLink(MastheadEnum.HELP);}
    }
    public class MastheadButtons {}
    public class MastheadSelects {}
   
    public void section_header_validate() {
        selenium.isElementPresent(MastheadEnum.LOGO);
        selenium.isElementPresent(MastheadEnum.HELP);
        selenium.isElementPresent(MastheadEnum.MY_MESSAGES);
        selenium.isElementPresent(MastheadEnum.MY_ACCOUNT);
        selenium.isElementPresent(MastheadEnum.LOGOUT);

        assertEquals(MastheadEnum.HELP);
        assertEquals(MastheadEnum.MY_MESSAGES);
        assertEquals(MastheadEnum.MY_ACCOUNT);
        assertEquals(MastheadEnum.LOGOUT);
    }

    public void section_footer_validate() {
        selenium.isElementPresent(MastheadEnum.COPYRIGHT);
        selenium.isElementPresent(MastheadEnum.PRIVACY);
        selenium.isElementPresent(MastheadEnum.LEGAL);
        selenium.isElementPresent(MastheadEnum.SUPPORT);
        selenium.isElementPresent(MastheadEnum.VERSION);

        assertEquals(MastheadEnum.COPYRIGHT);
        assertEquals(MastheadEnum.PRIVACY);
        assertEquals(MastheadEnum.LEGAL);
        assertEquals(MastheadEnum.SUPPORT);
    }
}
