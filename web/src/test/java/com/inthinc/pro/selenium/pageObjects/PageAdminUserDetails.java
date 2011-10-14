package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextLabel;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminTables.AdminUsersEntries;
import com.inthinc.pro.selenium.pageEnums.AdminUserDetailsEnum;

public class PageAdminUserDetails extends AdminBar {

    public PageAdminUserDetails() {
        url = null;
        checkMe.add(AdminUserDetailsEnum.FIRST_NAME);
        checkMe.add(AdminUserDetailsEnum.EMPLOYEE_INFORMATION);
        checkMe.add(AdminUserDetailsEnum.NOTIFICATIONS);
        checkMe.add(AdminUserDetailsEnum.USER_INFORMATION);
    }

    public UserDetailsPopUps _popUp() {
        return new UserDetailsPopUps();
    }

    public class UserDetailsPopUps extends AdminBarPopUps {

        public UserDetailsPopUps() {
            super(page);
        }

        public AdminDelete deleteUser() {
            return new AdminDelete(false);
        }
    }

    public class UserDetailsButtons extends AdminBarButtons {

        public TextButton delete() {
            return new TextButton(AdminBarEnum.DETAILS_DELETE, page);
        }

        public TextButton edit() {
            return new TextButton(AdminBarEnum.EDIT, page);
        }
    }

    public class UserDetailsDropDowns extends AdminBarDropDowns {}

    public class UserDetailsLinks extends AdminBarLinks {

        public TextLink backToUsers() {
            return new TextLink(AdminBarEnum.GO_BACK, page);
        }
    }

    public class UserDetailsTextFields extends AdminBarTextFields {}

    public class UserDetailsTexts extends AdminBarTexts {

        public TextLabel labels(AdminUsersEntries value) {
            SeleniumEnumWrapper temp = new SeleniumEnumWrapper(value);
            temp.setID("display-form:"+temp.getIDs()[0]);
            return new TextLabel(temp);
        }

        public Text values(AdminUsersEntries value) {
            SeleniumEnumWrapper temp = new SeleniumEnumWrapper(value);
            temp.setID("display-form:"+temp.getIDs()[0]);
            return new Text(temp);
        }

    }

    private static String page = "person";

    public UserDetailsButtons _button() {
        return new UserDetailsButtons();
    }

    public UserDetailsDropDowns _dropDown() {
        return new UserDetailsDropDowns();
    }

    public UserDetailsLinks _link() {
        return new UserDetailsLinks();
    }

    public UserDetailsTexts _text() {
        return new UserDetailsTexts();
    }

    public UserDetailsTextFields _textField() {
        return new UserDetailsTextFields();
    }

    // Helper methods
    public String getFullName() {
        String firstname = this._text().values(AdminUsersEntries.FIRST_NAME).getText();
        String lastname = this._text().values(AdminUsersEntries.LAST_NAME).getText();
        String middlename = this._text().values(AdminUsersEntries.MIDDLE_NAME).getText();
        String suffix = this._text().values(AdminUsersEntries.SUFFIX).getText();
        return (firstname + " " + middlename + " " + lastname + " " + suffix).replace("  ", " ").trim();
    }

}
