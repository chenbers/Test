/****************************************************************************************
 * Purpose: Used to process links selected on the Master Heading screen<br /> 
 * most functions are used regardless of what Main menu item is selected<br />
 * as Master Heading screen does not change.<br /> 
 * 
 */

package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.PageScroller;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextLinkNewWindow;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.models.AutomationUser;
import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.selenium.pageEnums.MastheadEnum;

public abstract class Masthead extends AbstractPage {
	

	public Masthead(){
		checkMe.add(MastheadEnum.COPYRIGHT);
		checkMe.add(MastheadEnum.PRIVACY);
		checkMe.add(MastheadEnum.LEGAL);
		checkMe.add(MastheadEnum.SUPPORT);
		checkMe.add(MastheadEnum.VERSION);
	}
	
	public class Paging{
		
		private String page;
		
		public Paging(){
			page=null;
		}
		public Paging(String page){
			this.page=page;
		}
		
		public Paging(SeleniumEnums page){
            this.page=page.getIDs()[0];
        }
		
		public PageScroller backAll(){
			return new PageScroller(MastheadEnum.BACK_ALL, page);
		}
		
		public PageScroller backOne(){
			return new PageScroller(MastheadEnum.BACK_ONE, page);
		}
		
		public PageScroller forwardAll(){
			return new PageScroller(MastheadEnum.FORWARD_ALL, page);
		}
		
		public PageScroller forwardOne(){
			return new PageScroller(MastheadEnum.FORWARD_ONE, page);
		}
		
		public PageScroller selectPageNumber(int pageNumber){
			return new PageScroller(MastheadEnum.CHOOSE_PAGE, page, pageNumber);
		}
		
	}
	
	public class MastheadPopUps extends PopUps{
		public MastheadPopUps(){
			
		}
		public MastheadPopUps(String page) {
			super(page);
		}

		public MastheadPopUps(String page, Types report, Integer i) {
			super(page, report, i);
		}
		public PasswordChangeRequired changePasswordRequired(){
			return new PasswordChangeRequired();
		}
		
		public PasswordChange changePassword(){
			return new PasswordChange();
		}
		
		public UpdatePasswordReminder updatePasswordReminder(){
			return new UpdatePasswordReminder();
		}
	}
	
	public MastheadPopUps _popUp(){
		return new MastheadPopUps();
	}
	
	public Masthead loginProcess(String username, String password){
		PageLogin login = new PageLogin();
		login.loginProcess(username, password);
		return this;
	}
	
	public Masthead loginProcess(AutomationUser login){
	    return loginProcess(login.getUsername(), login.getPassword());
	}
	
	public class MastheadButtons {
	}

	public class MastheadDropDowns {
	}

	public class MastheadLinks {
		public TextLinkNewWindow help() {
			return new TextLinkNewWindow(MastheadEnum.HELP);
		}

		public TextLinkNewWindow legalNotice() {
			return new TextLinkNewWindow(MastheadEnum.LEGAL);
		}

		public TextLink logout() {
			return new TextLink(MastheadEnum.LOGOUT);
		}

		public TextLink myAccount() {
			return new TextLink(MastheadEnum.MY_ACCOUNT);
		}

		public TextLink myMessages() {
			return new TextLink(MastheadEnum.MY_MESSAGES);
		}

		public TextLinkNewWindow privacyPolicy() {
			return new TextLinkNewWindow(MastheadEnum.PRIVACY);
		}

		public TextLinkNewWindow support() {
			return new TextLinkNewWindow(MastheadEnum.SUPPORT);
		}
	}

	public class MastheadTextFields {
	}

	public class MastheadTexts {
		public Text copyright() {
			return new Text(MastheadEnum.COPYRIGHT);
		}

		public Text version() {
			return new Text(MastheadEnum.VERSION);
		}
	}

}
