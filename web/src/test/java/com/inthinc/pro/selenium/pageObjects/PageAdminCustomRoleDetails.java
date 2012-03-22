package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextLabel;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AdminCustomRoleDetailsEnum;

public class PageAdminCustomRoleDetails extends AdminBar {
    
    public PageAdminCustomRoleDetails(){
        checkMe.add(AdminCustomRoleDetailsEnum.BACK_TO_TABLE);
        checkMe.add(AdminCustomRoleDetailsEnum.TITLE);
    }

    @Override
    public SeleniumEnums setUrl() {
        return AdminCustomRoleDetailsEnum.URL;
    }
    
    public class CustomRoleDetailsLinks extends AdminBarLinks {
        
        public TextLink backToCustomRoles() {
            return new TextLink(AdminCustomRoleDetailsEnum.BACK_TO_TABLE);
        }
    }

    public class CustomRoleDetailsTexts extends AdminBarTexts {
        
        public Text labelName() {
            return new TextLabel(AdminCustomRoleDetailsEnum.NAME);
        }
        
        public Text valueName() {
            return new Text(AdminCustomRoleDetailsEnum.NAME);
        }
        
        public Text valueAccessPoint(int row) {
            return new Text(AdminCustomRoleDetailsEnum.ACCESS_POINTS_ITEM, row);
        }
        
        public Text labelAccessPoint() {
            return new Text(AdminCustomRoleDetailsEnum.ACCESS_POINTS_LABEL);
        }
        
        public Text title(){
            return new Text(AdminCustomRoleDetailsEnum.TITLE);
        }
        
    }

    public class CustomRoleDetailsTextFields extends AdminBarTextFields {}

    public class CustomRoleDetailsButtons extends AdminBarButtons {
        
        public TextButton delete() {
            return new TextButton(AdminCustomRoleDetailsEnum.DELETE);
        }
        
        public TextButton edit() {
            return new TextButton(AdminCustomRoleDetailsEnum.EDIT);
        }
    }

    public class CustomRoleDetailsDropDowns extends AdminBarDropDowns {}

    public class CustomRoleDetailsPopUps extends MastheadPopUps {}

    public class CustomRoleDetailsPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public CustomRoleDetailsPager _page() {
        return new CustomRoleDetailsPager();
    }

    public CustomRoleDetailsLinks _link() {
        return new CustomRoleDetailsLinks();
    }

    public CustomRoleDetailsTexts _text() {
        return new CustomRoleDetailsTexts();
    }

    public CustomRoleDetailsButtons _button() {
        return new CustomRoleDetailsButtons();
    }

    public CustomRoleDetailsTextFields _textField() {
        return new CustomRoleDetailsTextFields();
    }

    public CustomRoleDetailsDropDowns _dropDown() {
        return new CustomRoleDetailsDropDowns();
    }

    public CustomRoleDetailsPopUps _popUp() {
        return new CustomRoleDetailsPopUps();
    }

}
