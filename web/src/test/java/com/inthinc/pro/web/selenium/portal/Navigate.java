package com.inthinc.pro.web.selenium.portal;

import com.inthinc.pro.web.selenium.Core;
import com.inthinc.pro.web.selenium.Selenium_Server;
import com.inthinc.pro.web.selenium.Singleton;
import com.inthinc.pro.web.selenium.Debug.Error_Catcher;

/****************************************************************************************
 * Purpose: Process the Main menu displayed at the top of the Masthead screen. 
 * @author Lee Arrington
 * Last Update:  
 ****************************************************************************************/

public class Navigate extends Selenium_Server {
	
	//Define Class Objects
	private final String main_screen_id = "/tiwipro/app/admin/people";
	private final String notifications_page = "/tiwipro/app/notifications/";
	private final String search_edit_box = "navigation:layout-redirectSearch";
	private final String search_list_box = "navigation:layout-navigationRedirectTo";
	private final String search_button = "navigation:layout-navigation_search_button";
	private final String hometree_id = "//span[@id='tree_link']/span";
	protected static Core selenium;
	private String curr_notif_menu = "";
	private String curr_admin_menu = "";
	
	public Navigate(){
			this(Singleton.getSingleton().getSelenium());
		}
	
	public Navigate(Singleton tvar ){
			this(tvar.getSelenium());
		}
	
	public Navigate( Core sel ){
			selenium = sel;
		}
	
	public Error_Catcher get_errors(){
			return selenium.getErrors();
		}
		
	public void main_search(String texttosearch, String section, String errorname){
		selenium.type(search_edit_box, texttosearch,errorname);
		selenium.select(search_list_box, section, errorname);
		selenium.click(search_button, errorname);
		selenium.waitForPageToLoad("30000");
	}

public void home_select(String link, String error_message){
		selenium.open(main_screen_id);
		selenium.click(link, error_message);
		selenium.waitForPageToLoad("30000");
	}

//Main Menu displayed on Master Head Screen 
public void main_menu_select(String menuitem,String error_message){
		//menuitem example: Admin , LiveFleet
		selenium.click("//a[@id='navigation:layout-navigation" + menuitem + "']/span", error_message);
		selenium.waitForPageToLoad("80000");
	}


public void admin_menu_select(String screen, String errorname){
	selenium.click("link=" + screen);
	selenium.waitForPageToLoad("30000");
	}

public void admin_menu_select(String screen, String errorname, String waittime){
	//overloaded function that allows users to increase wait times 
	selenium.click("link=" + screen);
	selenium.waitForPageToLoad(waittime);
	}

public void notification_menu_select(String screen, String errorname){
		//Since each button's id changes depending on what is currently select
		//a var is set to the curbutton is used to append to the name of the button selected.
		if (curr_notif_menu.contentEquals("")){
				curr_notif_menu = "redFlags";}		
			//select item and reset current screen var
			selenium.open(notifications_page, screen);
			selenium.click("//a[@id='" + curr_notif_menu + "-" + screen + "']/span", errorname);
			selenium.waitForPageToLoad("80000");
			curr_notif_menu = screen;
			selenium.open(notifications_page + screen,"Refresh Button on Section:" + screen);
			}

//used to select item form home tree by selecting link
	public void select_home_tree (String tierone, String errorname){
 		selenium.Pause(10);	//pause for tree to populate after page is loaded
 		selenium.mouseOver(hometree_id);
 		selenium.click("link=" + tierone,errorname);
 		selenium.waitForPageToLoad("80000");
 	}

	//Overloaded function used to select 2nd tier item from home tree by select two links
	public void select_home_tree (String tierone, String tiertwo, String errorname){
 		selenium.Pause(10);	//pause for tree to populate after page is loaded
 		selenium.mouseOver(hometree_id);
 		selenium.click("link=" + tierone, errorname);
 		selenium.click("link=" + tiertwo, errorname);
 		selenium.waitForPageToLoad("80000");
 	}
	
	//Overloaded function used to select 3nd tier item from home tree by select two links
	public void select_home_tree (String tierone, String tiertwo, String tierthree,String errorname){
 		selenium.Pause(10);	//pause for tree to populate after page is loaded
 		selenium.mouseOver(hometree_id);
 		selenium.click("link=" + tierone, errorname);
 		selenium.click("link=" + tiertwo, errorname);
 		selenium.click("link=" + tierthree, errorname);
 		selenium.waitForPageToLoad("80000");
 	}
	
}