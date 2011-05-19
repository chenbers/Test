package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TableText;
import com.inthinc.pro.automation.elements.TableTextLink;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminUsersEnum;

public class PageAdminUsers extends ReportsBar {
	
	private String page="person";

	public class AdminUsersButtons extends ReportsBarButtons {
		public TextButton delete(){
			return new TextButton(AdminBarEnum.DELETE, page);
		}
		
		public TextButton batchEdit(){
			return new TextButton(AdminBarEnum.BATCH_EDIT, page);
		}
		
		public TextButton search(){
			return new TextButton(AdminBarEnum.SEARCH_BUTTON, page);
		}
	}

	public class AdminUsersDropDowns extends ReportsBarDropDowns {
	}

	public class AdminUsersLinks extends ReportsBarLinks {
		
		public TextLink editColumns(){
			return new TextLink(AdminBarEnum.EDIT_COLUMNS_LINK, page);
		}
		
		public TableTextLink tableEntry(AdminUsersEnum column){
			return new TableTextLink(AdminBarEnum.TABLE_ENTRIES, page, column);
		}
		
		public TableTextLink sortByColumn(AdminUsersEnum column){
			return new TableTextLink(AdminBarEnum.TABLE_HEADERS, page, column);
		}
		
		public TableTextLink edit(){
			return new TableTextLink(AdminBarEnum.EDIT_ITEM);
		}
	}

	public class AdminUsersTextFields extends ReportsBarTextFields {
		public TextField search(){
			return new TextField(AdminBarEnum.SEARCH_TEXTFIELD, page);
		}
		
	}

	public class AdminUsersTexts extends ReportsBarTexts {
		public TableText tableEntry(AdminUsersEnum column){
			return new TableText(AdminBarEnum.TABLE_ENTRIES, page, column);
		}
	}

	public AdminUsersButtons _button() {
		return new AdminUsersButtons();
	}

	public AdminUsersDropDowns _dropDown() {
		return new AdminUsersDropDowns();
	}

	public AdminUsersLinks _link() {
		return new AdminUsersLinks();
	}

	public AdminUsersTexts _text() {
		return new AdminUsersTexts();
	}

	public AdminUsersTextFields _textField() {
		return new AdminUsersTextFields();
	}
	
	public class AdminUsersPopUps extends PopUps{
		public AdminUsersPopUps(){
			super(page);
		}
		
		public EditColumns editColumns(){
			return new EditColumns();
		}
		
		public AdminDelete deleteUsers(){
			return new AdminDelete();
		}
	}
	
	public AdminUsersPopUps _popUp(){
		return new AdminUsersPopUps();
	}
	
    @Override
    public String getExpectedPath() {
        return AdminUsersEnum.DEFAULT_URL.getURL();
    }
}
