package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextLinkTableHeader;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminCustomRolesEnum;

public class PageAdminCustomRoles extends AdminTables {
    
    public PageAdminCustomRoles(){
        page = "customRoles";
        checkMe.add(AdminCustomRolesEnum.TITLE);
    }
    
    @Override
    public SeleniumEnums setUrl() {
        return AdminCustomRolesEnum.URL;
    }
    
    public class CustomRolesLinks extends AdminTablesLinks {
        
        public TextTableLink entryRoleName() {
            return new TextTableLink(AdminCustomRolesEnum.ROLE_NAME);
        }
        
        public TextLink sortByRoleName() {
            return new TextLinkTableHeader(AdminCustomRolesEnum.ROLE_NAME);
        }
    }

    public class CustomRolesTexts extends AdminTablesTexts {
        
        public Text title(){
            return new Text(AdminCustomRolesEnum.TITLE);
        }
    }

    public class CustomRolesTextFields extends AdminTablesTextFields {}

    public class CustomRolesButtons extends AdminTablesButtons {
        
        public TextButton delete() {
            return new TextButton(AdminBarEnum.DELETE, page);
        }
    }

    public class CustomRolesDropDowns extends AdminTablesDropDowns {}

    public class CustomRolesPopUps extends AdminTablesPopUps {}

    public class CustomRolesPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public CustomRolesPager _page() {
        return new CustomRolesPager();
    }

    public CustomRolesLinks _link() {
        return new CustomRolesLinks();
    }

    public CustomRolesTexts _text() {
        return new CustomRolesTexts();
    }

    public CustomRolesButtons _button() {
        return new CustomRolesButtons();
    }

    public CustomRolesTextFields _textField() {
        return new CustomRolesTextFields();
    }

    public CustomRolesDropDowns _dropDown() {
        return new CustomRolesDropDowns();
    }

    public CustomRolesPopUps _popUp() {
        return new CustomRolesPopUps();
    }

    @Override
    protected boolean checkIsOnPage() {
        // TODO Auto-generated method stub
        return false;
    }

}
