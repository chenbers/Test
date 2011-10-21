package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.SortHeader;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminRedFlags;
import com.inthinc.pro.selenium.pageEnums.AdminTables.RedFlagColumns;

public class PageAdminRedFlags extends AdminTables {

    public PageAdminRedFlags() {
        page = "redFlagAndZoneAlerts";
        url = AdminRedFlags.DEFAULT_URL;
        checkMe.add(AdminRedFlags.TITLE);
        
    }

    
    public class PageAdminRedFlagssLinks extends AdminTablesLinks {
        
        public TextTableLink entryRedFlagName(){
            return new TextTableLink(AdminBarEnum.TABLE_ENTRIES, RedFlagColumns.RED_FLAG);
        }
    }

    public class PageAdminRedFlagssTexts extends AdminTablesTexts {
        
        public TextTable tableEntry(RedFlagColumns column){
            return new TextTable(AdminBarEnum.TABLE_ENTRIES, column);
        }
        
        public Text title(){
            return new Text(AdminRedFlags.TITLE);
        }
    }

    public class PageAdminRedFlagssTextFields extends AdminTablesTextFields {}

    public class PageAdminRedFlagssButtons extends AdminTablesButtons {
        
        public TextButton sortByColumn(RedFlagColumns column){
            return new SortHeader(AdminBarEnum.TABLE_ENTRIES, column);
        }
        
        public TextButton delete(){
            return new TextButton(AdminBarEnum.DELETE);
        }
    }

    public class PageAdminRedFlagssDropDowns extends AdminTablesDropDowns {}

    public class PageAdminRedFlagssPopUps extends AdminTablesPopUps {}

    public class PageAdminRedFlagssPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public PageAdminRedFlagssPager _page() {
        return new PageAdminRedFlagssPager();
    }

    public PageAdminRedFlagssLinks _link() {
        return new PageAdminRedFlagssLinks();
    }

    public PageAdminRedFlagssTexts _text() {
        return new PageAdminRedFlagssTexts();
    }

    public PageAdminRedFlagssButtons _button() {
        return new PageAdminRedFlagssButtons();
    }

    public PageAdminRedFlagssTextFields _textField() {
        return new PageAdminRedFlagssTextFields();
    }

    public PageAdminRedFlagssDropDowns _dropDown() {
        return new PageAdminRedFlagssDropDowns();
    }

    public PageAdminRedFlagssPopUps _popUp() {
        return new PageAdminRedFlagssPopUps();
    }
}
