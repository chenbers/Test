package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.SortHeader;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminRedFlags;
import com.inthinc.pro.selenium.pageEnums.AdminTables.RedFlagColumns;

public class PageAdminRedFlags extends AdminTables {

    public PageAdminRedFlags() {
        page = "redFlagAndZoneAlerts";
        checkMe.add(AdminRedFlags.TITLE);
        
    }

    
    public class AdminRedFlagsLinks extends AdminTablesLinks {
        
        public TextTableLink entryRedFlagName(){
            return new TextTableLink(AdminBarEnum.TABLE_ENTRIES, RedFlagColumns.RED_FLAG);
        }
    }

    public class AdminRedFlagsTexts extends AdminTablesTexts {
        
        public TextTable tableEntry(RedFlagColumns column){
            return new TextTable(AdminBarEnum.TABLE_ENTRIES, column);
        }
        
        public Text title(){
            return new Text(AdminRedFlags.TITLE);
        }
    }

    public class AdminRedFlagsTextFields extends AdminTablesTextFields {}

    public class AdminRedFlagsButtons extends AdminTablesButtons {
        
        public TextButton sortByColumn(RedFlagColumns column){
            return new SortHeader(AdminBarEnum.TABLE_ENTRIES, column);
        }
        
        public TextButton delete(){
            return new TextButton(AdminBarEnum.DELETE);
        }
    }

    public class AdminRedFlagsDropDowns extends AdminTablesDropDowns {}

    public class AdminRedFlagsPopUps extends AdminTablesPopUps {}

    public class AdminRedFlagsPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public AdminRedFlagsPager _page() {
        return new AdminRedFlagsPager();
    }

    public AdminRedFlagsLinks _link() {
        return new AdminRedFlagsLinks();
    }

    public AdminRedFlagsTexts _text() {
        return new AdminRedFlagsTexts();
    }

    public AdminRedFlagsButtons _button() {
        return new AdminRedFlagsButtons();
    }

    public AdminRedFlagsTextFields _textField() {
        return new AdminRedFlagsTextFields();
    }

    public AdminRedFlagsDropDowns _dropDown() {
        return new AdminRedFlagsDropDowns();
    }

    public AdminRedFlagsPopUps _popUp() {
        return new AdminRedFlagsPopUps();
    }

    @Override
    public SeleniumEnums setUrl() {
        return AdminRedFlags.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _button().batchEdit().isPresent() && _text().title().isPresent();
    }
}
