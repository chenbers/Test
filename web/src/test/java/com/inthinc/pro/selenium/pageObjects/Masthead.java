/****************************************************************************************
 * Purpose: Used to process links selected on the Master Heading screen 
 * most functions are used regardless of what Main menu item is selected 
 * as Master Heading screen does not change. 
 * @author larringt , dtanner
 * Last Update:  11/18/Added comments and made changes to adhere to Java Coding Standards
 */

package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.ErrorCatcher;
import com.inthinc.pro.automation.selenium.GlobalSelenium;
import com.inthinc.pro.selenium.pageEnums.MastheadEnum;

public class Masthead {


    protected static CoreMethodLib selenium;

    public Masthead() {
        selenium = GlobalSelenium.getSelenium();
    }

    public String text_version_get() {
        String version_text = selenium.getText(MastheadEnum.VERSION);
        return version_text;
    }

    public String text_copyRight_get() {
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
        selenium.verifyLocation("messages/");// TODO: jwimmer: DTanner: no hard coded Strings in the FRAMEWORK code
    }

    public void link_logout_click() {
        selenium.click(MastheadEnum.LOGOUT);
        selenium.waitForPageToLoad();
        selenium.verifyLocation("tiwipro/login");// TODO: jwimmer: DTanner: no hard coded Strings in the FRAMEWORK code
    }

    public void link_support_click() {
        selenium.click(MastheadEnum.SUPPORT);
        selenium.selectWindow(null);
    }

    public void link_legalNotice_link() {
        selenium.click(MastheadEnum.LEGAL);
        selenium.waitForPopUp("popup", CoreMethodLib.PAGE_TIMEOUT.toString());
        selenium.selectPopUp("");
        selenium.getText(MastheadEnum.LEGAL_NOTICE);
        selenium.close();
        selenium.selectWindow(null);
    }

    public void link_privacyPolicy_click() {
        selenium.click(MastheadEnum.PRIVACY);
        selenium.waitForPopUp("popup", CoreMethodLib.PAGE_TIMEOUT.toString());
        selenium.selectPopUp("");
        selenium.getText(MastheadEnum.PRIVACY_POLICY);
        selenium.close();
        selenium.selectWindow(null);
    }

    public void click_help(String help_page) {
        if (help_page.indexOf(".htm") == -1) {
            help_page += ".htm";
        }// TODO: jwimmer: DTanner: no hard coded Strings in the FRAMEWORK code... I'm not sure this line is doing anything (effective) anyway?
        selenium.click(MastheadEnum.HELP);
        selenium.waitForPageToLoad();
    }

    public void section_header_validate() {
        selenium.isElementPresent(MastheadEnum.LOGO);
        selenium.isElementPresent(MastheadEnum.HELP);
        selenium.isElementPresent(MastheadEnum.MY_MESSAGES);
        selenium.isElementPresent(MastheadEnum.MY_ACCOUNT);
        selenium.isElementPresent(MastheadEnum.LOGOUT);

        selenium.getText(MastheadEnum.HELP);
        selenium.getText(MastheadEnum.MY_MESSAGES);
        selenium.getText(MastheadEnum.MY_ACCOUNT);
        selenium.getText(MastheadEnum.LOGOUT);
    }

    public void section_footer_validate() {
        selenium.isElementPresent(MastheadEnum.COPYRIGHT);
        selenium.isElementPresent(MastheadEnum.PRIVACY);
        selenium.isElementPresent(MastheadEnum.LEGAL);
        selenium.isElementPresent(MastheadEnum.SUPPORT);
        selenium.isElementPresent(MastheadEnum.VERSION);

        selenium.getText(MastheadEnum.COPYRIGHT);
        selenium.getText(MastheadEnum.PRIVACY);
        selenium.getText(MastheadEnum.LEGAL);
        selenium.getText(MastheadEnum.SUPPORT);
    }

    public void page_validate() {
        this.section_footer_validate();
        this.section_header_validate();
    }

    public ErrorCatcher get_errors() {
        return selenium.getErrors();
    }

    public CoreMethodLib get_selenium() {
        return selenium;
    }
}
