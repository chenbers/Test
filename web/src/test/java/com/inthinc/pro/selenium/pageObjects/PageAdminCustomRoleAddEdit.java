package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.CheckBoxTable;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminCustomRoleAddEditEnum;

public class PageAdminCustomRoleAddEdit extends AdminBar {

    public PageAdminCustomRoleAddEdit() {
        checkMe.add(AdminCustomRoleAddEditEnum.TITLE);
        checkMe.add(AdminCustomRoleAddEditEnum.HEADER);
    }
    
    public class CustomRoleAddEditLinks extends AdminBarLinks {}

    public class CustomRoleAddEditTexts extends AdminBarTexts {
        
        public Text masterError(){
            return new Text(AdminBarEnum.MASTER_ERROR);
        }
        
        public Text title() {
            return new Text(AdminCustomRoleAddEditEnum.TITLE);
        }
        
        public Text header() {
            return new Text(AdminCustomRoleAddEditEnum.HEADER);
        }
        
        public Text headerAccessPoints() {
            return new Text(AdminCustomRoleAddEditEnum.ACCESS_POINTS_HEADER);
        }
        
        public TextTable entryAccessPoint() {
            return new TextTable(AdminCustomRoleAddEditEnum.ACCESS_POINT);
        }
        
        public Text name() {
            return new TextFieldLabel(AdminCustomRoleAddEditEnum.NAME);
        }
        
        public Text errorName() {
            return new Text(AdminCustomRoleAddEditEnum.NAME);
        }
        
    }

    public class CustomRoleAddEditTextFields extends AdminBarTextFields {
        
        public TextField name() {
            return new TextField(AdminCustomRoleAddEditEnum.NAME);
        }
        
    }

    public class CustomRoleAddEditButtons extends AdminBarButtons {
        
        public TextButton topSave() {
            return new TextButton(AdminCustomRoleAddEditEnum.TOP_SAVE);
        }
        
        public TextButton bottomSave() {
            return new TextButton(AdminCustomRoleAddEditEnum.BOTTOM_SAVE);
        }
        
        public TextButton topCancel() {
            return new TextButton(AdminCustomRoleAddEditEnum.TOP_CANCEL);
        }
        
        public TextButton bottomCancel() {
            return new TextButton(AdminCustomRoleAddEditEnum.BOTTOM_CANCEL);
        }
    }

    public class CustomRoleAddEditDropDowns extends AdminBarDropDowns {}

    public class CustomRoleAddEditPopUps extends MastheadPopUps {}

    public class CustomRoleAddEditPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public CustomRoleAddEditPager _page() {
        return new CustomRoleAddEditPager();
    }

    public CustomRoleAddEditLinks _link() {
        return new CustomRoleAddEditLinks();
    }

    public CustomRoleAddEditTexts _text() {
        return new CustomRoleAddEditTexts();
    }

    public CustomRoleAddEditButtons _button() {
        return new CustomRoleAddEditButtons();
    }

    public CustomRoleAddEditTextFields _textField() {
        return new CustomRoleAddEditTextFields();
    }

    public CustomRoleAddEditDropDowns _dropDown() {
        return new CustomRoleAddEditDropDowns();
    }

    public CustomRoleAddEditPopUps _popUp() {
        return new CustomRoleAddEditPopUps();
    }
    
    public CustomRoleCheckBoxs _checkBox(){
        return new CustomRoleCheckBoxs();
    }
    
    public class CustomRoleCheckBoxs {
        public CheckBox checkAll() {
            return new CheckBox(AdminCustomRoleAddEditEnum.CHECK_ALL);
        }
        
        public CheckBoxTable entryCheck() {
            return new CheckBoxTable(AdminCustomRoleAddEditEnum.CHECK_ENTRY);
        }
    }

}
