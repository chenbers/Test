/****************************************************************************************
 * Purpose: Used to process links selected on the Master Heading screen 
 * most functions are used regardless of what Main menu item is selected 
 * as Master Heading screen does not change. 
 * @author larringt , dtanner
 * Last Update:  11/18/Added comments and made changes to adhere to Java Coding Standards
 */

package com.inthinc.pro.selenium.pageObjects;


import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.selenium.pageEnums.MastheadEnum;

public abstract class Masthead extends AbstractPage{


    public String textField_version_get() {
        String version_text = selenium.getText(MastheadEnum.VERSION);
        return version_text;
    }

    public String textField_copyRight_get() {
        String copyright_text_actual = selenium.getText(MastheadEnum.COPYRIGHT);
        return copyright_text_actual;
    }

    public void link_myAccount_click() {
        selenium.click(MastheadEnum.MY_ACCOUNT);
        selenium.waitForPageToLoad();
    }

    public void link_myMessages_click() {
        selenium.click(MastheadEnum.MY_MESSAGES);
        selenium.waitForPageToLoad();
    }

    public void link_logout_click() {
        selenium.click(MastheadEnum.LOGOUT);
        selenium.waitForPageToLoad();
    }

    public void link_support_click() {
        clickNewWindowLink(MastheadEnum.SUPPORT, MastheadEnum.CUSTOMER_SUPPORT_DEFAULT);
    }

    public void link_legalNotice_click() {
        clickNewWindowLink(MastheadEnum.LEGAL, MastheadEnum.LEGAL_NOTICE);
    }
    

    public void link_privacyPolicy_click() {
        clickNewWindowLink(MastheadEnum.PRIVACY, MastheadEnum.PRIVACY_POLICY);
    }

    public void click_help(String help_page) {
//        //TODO: This method needs to be updated and fleshed out once the Help Page has been nailed down.
//        if (help_page.indexOf(".htm") == -1) {
//            help_page += ".htm";
//        }
//        selenium.click(MastheadEnum.HELP);
//        selenium.waitForPageToLoad();
    }

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
