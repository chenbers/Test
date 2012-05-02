package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.CheckBoxTable;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminAddEditCustomRoleEnum;

public class PageAdminAddEditCustomRole extends AdminBar {

    public PageAdminAddEditCustomRole() {
        checkMe.add(AdminAddEditCustomRoleEnum.TITLE);
        checkMe.add(AdminAddEditCustomRoleEnum.HEADER);
    }
    

    @Override
    public SeleniumEnums setUrl() {
        return AdminAddEditCustomRoleEnum.URL;
    }
    
    public class AddEditCustomRoleLinks extends AdminBarLinks {}

    public class AddEditCustomRoleTexts extends AdminBarTexts {
        
        public Text masterError(){
            return new Text(AdminBarEnum.MASTER_ERROR);
        }
        
        public Text title() {
            return new Text(AdminAddEditCustomRoleEnum.TITLE);
        }
        
        public Text header() {
            return new Text(AdminAddEditCustomRoleEnum.HEADER);
        }
        
        public Text headerAccessPoints() {
            return new Text(AdminAddEditCustomRoleEnum.ACCESS_POINTS_HEADER);
        }
        
        public TextTable entryAccessPoint() {
            return new TextTable(AdminAddEditCustomRoleEnum.ACCESS_POINT);
        }
        
        public Text name() {
            return new TextFieldLabel(AdminAddEditCustomRoleEnum.NAME);
        }
        
        public Text errorName() {
            return new Text(AdminAddEditCustomRoleEnum.NAME);
        }
        
    }

    public class AddEditCustomRoleTextFields extends AdminBarTextFields {
        
        public TextField name() {
            return new TextField(AdminAddEditCustomRoleEnum.NAME);
        }
        
    }

    public class AddEditCustomRoleButtons extends AdminBarButtons {
        
        public TextButton topSave() {
            return new TextButton(AdminAddEditCustomRoleEnum.TOP_SAVE);
        }
        
        public TextButton bottomSave() {
            return new TextButton(AdminAddEditCustomRoleEnum.BOTTOM_SAVE);
        }
        
        public TextButton topCancel() {
            return new TextButton(AdminAddEditCustomRoleEnum.TOP_CANCEL);
        }
        
        public TextButton bottomCancel() {
            return new TextButton(AdminAddEditCustomRoleEnum.BOTTOM_CANCEL);
        }
    }

    public class AddEditCustomRoleDropDowns extends AdminBarDropDowns {}

    public class AddEditCustomRolePopUps extends MastheadPopUps {}

    public class AddEditCustomRolePager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public AddEditCustomRolePager _page() {
        return new AddEditCustomRolePager();
    }

    public AddEditCustomRoleLinks _link() {
        return new AddEditCustomRoleLinks();
    }

    public AddEditCustomRoleTexts _text() {
        return new AddEditCustomRoleTexts();
    }

    public AddEditCustomRoleButtons _button() {
        return new AddEditCustomRoleButtons();
    }

    public AddEditCustomRoleTextFields _textField() {
        return new AddEditCustomRoleTextFields();
    }

    public AddEditCustomRoleDropDowns _dropDown() {
        return new AddEditCustomRoleDropDowns();
    }

    public AddEditCustomRolePopUps _popUp() {
        return new AddEditCustomRolePopUps();
    }
    
    public AddEditCustomRoleCheckBoxs _checkBox(){
        return new AddEditCustomRoleCheckBoxs();
    }
    
    public class AddEditCustomRoleCheckBoxs {
        public CheckBox checkAll() {
            return new CheckBox(AdminAddEditCustomRoleEnum.CHECK_ALL);
        }
        
        public CheckBoxTable entryCheck() {
            return new CheckBoxTable(AdminAddEditCustomRoleEnum.CHECK_ENTRY);
        }
    }

    @Override
    protected boolean checkIsOnPage() {
        return _button().topSave().isPresent() && _button().bottomCancel().isPresent();
    }

}
