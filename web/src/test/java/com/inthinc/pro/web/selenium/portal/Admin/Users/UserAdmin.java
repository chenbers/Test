package com.inthinc.pro.web.selenium.portal.Admin.Users;

import com.inthinc.pro.web.selenium.InthincTest;
import com.inthinc.pro.web.selenium.CoreMethodLib;
import com.inthinc.pro.web.selenium.GlobalSelenium;
import com.inthinc.pro.web.selenium.Debug.ErrorCatcher;

/****************************************************************************************
 * @author : Lee Arrington
 * Purpose: Define methods and objects to enter and manipulate information on Admin - User Screen
 * Last Update:  11/24/10
 ****************************************************************************************/

public class UserAdmin extends InthincTest {
	
	//Define Class Objects
	private final String add_user_page = "/tiwipro/app/admin/editPerson";
	private final String userlistrowitem = "admin-table-form:personTable:";
	private final String editcolumnlink = "admin-table-form:personTable-adminTableEditColumns";
	private final String editcolradiobutton = "editColumnsForm:personTable-editColumnsGrid:";
	
	// Screen Buttons
	private final String searchbutton = "admin-table-form:personTable-adminTableSearch";
	private final String userdeletebutton = "admin-table-form:personTable-adminTableDelete";
	private final String batcheditbutton = "admin-table-form:personTable-adminTableEdit";
	private final String confirmdelbutton = "confirmDeleteForm:personTable-deleteButton";
	private final String canceldelbutton = "personTable-confirmDeleteCancel";
	private final String savepopupbutton = "editColumnsForm:personTable-editColumnsPopupSave";
	private final String cancelpopupbutton = "editColumnsForm:personTable-editColumnsPopupCancel";
	
	//General text box
	private final String search_text_box = "admin-table-form:personTable-filterTable";
	
	protected static CoreMethodLib selenium;

	public UserAdmin(){
			this(GlobalSelenium.getSingleton().getSelenium());
		}
	
	public UserAdmin(GlobalSelenium tvar ){
			this(tvar.getSelenium());
		}
	
	public void chk_User_Admin_Screen(String error_name){
		//check title
		selenium.isTextPresent("exact:Admin: Users", error_name);
		//check buttons
		selenium.isTextPresent("Delete", error_name);
		selenium.isTextPresent("Batch Edit", error_name);
		selenium.isTextPresent("Search", error_name);
		//check column headings
		selenium.isTextPresent("Edit Columns", error_name);
		selenium.isTextPresent("Name", error_name);
		selenium.isTextPresent("User Group", error_name);
		selenium.isTextPresent("Reports To", error_name);
		selenium.isTextPresent("Driver Team", error_name);
	}
	
	public String SearchList(String searchtext, int total, String error_name){
			//Initialize local variables 
			String found = "no";	
			String curtext = "";
			//loop though list incrementing by one
			for (int currow = 0;currow<total; currow++){
				//get text for current row
				curtext = selenium.getText(userlistrowitem + currow + ":personTableName", error_name );
				//when item matches set return flag and break out of loop
				if (searchtext.contentEquals(curtext)){
					found = "yes";
					System.out.print(searchtext + "found in list " + error_name);
					break;
					}
				}
			//return result
			return found;
		}
	
	
	public void SelectCancelPopup(String error_name){
			selenium.click(savepopupbutton);
			selenium.waitForPageToLoad("30000");
		}
	
	public void SelectSavePopup(String error_name){
			selenium.click(cancelpopupbutton);
			selenium.waitForPageToLoad("30000");
		}
	
	public void SetColumnRadioButton (int col, String error_name){
		String ck = selenium.isnotChecked(editcolradiobutton + col + ":personTable-col", error_name);
		if (ck.contentEquals("yes")) {
				selenium.click(editcolradiobutton + col + ":personTable-col", error_name);
				}
		}
	
	public void SelectEditColumnLink(String errorname){
			selenium.click(editcolumnlink, errorname);
			selenium.waitForPageToLoad("30000");
		}
	
	public void SelectBatchEditButton (String errorname){
			selenium.click(batcheditbutton, errorname);
			selenium.waitForPageToLoad("30000");
		}
	
	public void SelectULRadioButton(int item_num, String item_name, String errorname){
			selenium.click(userlistrowitem + item_num + ":select", errorname);
		}
	
	public void SelectULEditLink(int item_num, String errorname){
			selenium.click(userlistrowitem + item_num + ":edit", errorname);
			selenium.waitForPageToLoad("30000");
		}
	
	public void SelectListName(int item_num, String errorname){
			selenium.click(userlistrowitem + item_num + ":personTableName", errorname );
			selenium.waitForPageToLoad("30000");
		}
	
	public void ClickUserDelete(String errorname){
			selenium.click(userdeletebutton, errorname);
			selenium.waitForPageToLoad("30000");
		}
	
	public void ClickCancelDelButton(String errorname){
			selenium.click(canceldelbutton);
			selenium.waitForPageToLoad("30000");
		}
	public void ClickConfirmDelButton(String errorname){
			selenium.click(confirmdelbutton, errorname);
			selenium.waitForPageToLoad("30000");
		}
	
	public void search_admin(String text, String errorname){
			selenium.type(search_text_box, text,errorname);
			selenium.click(searchbutton, "Select Search Button");
			selenium.waitForPageToLoad("30000");
		}
	
	public UserAdmin( CoreMethodLib sel ){
			selenium = sel;
		}
	
	public ErrorCatcher get_errors(){
			return selenium.getErrors();
		}
		
	
}

