package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.CheckBoxTable;
import com.inthinc.pro.automation.elements.ClickableObject;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;

public abstract class AdminTables extends AdminBar {

    protected String page;

    public class AdminTablesButtons extends AdminBarButtons {

        public TextButton batchEdit() {
            return new TextButton(AdminBarEnum.BATCH_EDIT, page);
        }

        public TextButton search() {
            return new TextButton(AdminBarEnum.SEARCH_BUTTON, page){
                public ClickableObject click() {
                    super.click();
                    pause(2, "arbitrary 2 second wait for ajax table refresh");
                    return this;
                }
            };
        }
    }

    public class AdminTablesDropDowns extends AdminBarDropDowns {}

    public class AdminTablesLinks extends AdminBarLinks {

        public TextLink editColumns() {
            return new TextLink(AdminBarEnum.EDIT_COLUMNS_LINK, page);
        }

        public TextTableLink edit() {
            return new TextTableLink(AdminBarEnum.EDIT_ITEM, page);
        }
    }

    public class AdminTablesTextFields extends AdminBarTextFields {

        public TextField search() {
            return new TextField(AdminBarEnum.SEARCH_TEXTFIELD, page);
        }

    }
    
    public class AdminTablesCheckBoxs {
        public CheckBox checkAll() {
            return new CheckBox(AdminBarEnum.SELECT_ALL, page);
        }

        public CheckBoxTable entryCheckRow() {
            return new CheckBoxTable(AdminBarEnum.SELECT_ROW, page);
        }
    }
    
    public AdminTablesCheckBoxs _checkBox() {
        return new AdminTablesCheckBoxs();
    }

    public class AdminTablesTexts extends AdminBarTexts {

        
        public Text labelSearchBox() {
            return new TextFieldLabel(AdminBarEnum.SEARCH_TEXTFIELD, page);
        }
        
        public Text counter() {
            return new Text(AdminBarEnum.COUNTER);
        }
    }


    public class AdminTablesPopUps extends AdminBarPopUps {

        public AdminTablesPopUps() {
            super(page + "Table");
        }

        public EditColumns editColumns() {
            return new EditColumns();
        }
    }

}
