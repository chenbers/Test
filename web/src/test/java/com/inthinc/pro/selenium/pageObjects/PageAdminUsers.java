package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminTables.AdminUsersEntries;
import com.inthinc.pro.selenium.pageEnums.AdminUsersEnum;

public class PageAdminUsers extends AdminBar {

    public PageAdminUsers() {
	url = AdminUsersEnum.DEFAULT_URL;
	checkMe.add(AdminUsersEnum.BATCH_EDIT);
	checkMe.add(AdminUsersEnum.EDIT_COLUMNS_LINK);
	checkMe.add(AdminUsersEnum.SEARCH_BUTTON);
    }

    private String page = "person";

    public class AdminUsersButtons extends AdminBarButtons {

	public TextButton delete() {
	    return new TextButton(AdminBarEnum.DELETE, page);
	}

	public TextButton batchEdit() {
	    return new TextButton(AdminBarEnum.BATCH_EDIT, page);
	}

	public TextButton search() {
	    return new TextButton(AdminBarEnum.SEARCH_BUTTON, page);
	}
    }

    public class AdminUsersDropDowns extends AdminBarDropDowns {
    }

    public class AdminUsersLinks extends AdminBarLinks {

	public TextLink editColumns() {
	    return new TextLink(AdminBarEnum.EDIT_COLUMNS_LINK, page);
	}

	public TextTableLink tableEntry(AdminUsersEntries column) {
	    return new TextTableLink(AdminBarEnum.TABLE_ENTRIES, page, column);
	}

	public TextTableLink sortByColumn(AdminUsersEntries column) {
	    return new TextTableLink(AdminBarEnum.TABLE_HEADERS, page, column);
	}

	public TextTableLink edit() {
	    return new TextTableLink(AdminBarEnum.EDIT_ITEM);
	}
    }

    public class AdminUsersTextFields extends AdminBarTextFields {

	public TextField search() {
	    return new TextField(AdminBarEnum.SEARCH_TEXTFIELD, page);
	}

    }

    public class AdminUsersTexts extends AdminBarTexts {

	public TextTable tableEntry(AdminUsersEntries column) {
	    return new TextTable(AdminBarEnum.TABLE_ENTRIES, page, column);
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

    public class AdminUsersPopUps extends AdminBarPopUps {

	public AdminUsersPopUps() {
	    super(page);
	}

	public EditColumns editColumns() {
	    return new EditColumns();
	}

	public AdminDelete deleteUsers() {
	    return new AdminDelete(true);
	}
    }

    public AdminUsersPopUps _popUp() {
	return new AdminUsersPopUps();
    }

    @Override
    public String getExpectedPath() {
	return AdminUsersEnum.DEFAULT_URL.getURL();
    }
}
