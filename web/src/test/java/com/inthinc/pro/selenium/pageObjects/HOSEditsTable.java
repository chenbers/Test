package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.SortHeader;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.selenium.pageEnums.HOSEditsTableEnum;

public class HOSEditsTable {
    
    public HOSEditsTableTexts _text(){
        return new HOSEditsTableTexts();
    }
    
    public HOSEditsTableLinks _link(){
        return new HOSEditsTableLinks();
    }

    public class HOSEditsTableTexts {
        public TextTable entryGroupName() {
            return new TextTable(HOSEditsTableEnum.GROUP_NAME);
        }

        public TextTable entryDriverName() {
            return new TextTable(HOSEditsTableEnum.DRIVER_NAME);
        }

        public TextTable entryEmployeeID() {
            return new TextTable(HOSEditsTableEnum.EMPLOYEE_ID);
        }

        public TextTable entryStatus() {
            return new TextTable(HOSEditsTableEnum.STATUS);
        }

        public TextTable entryLogTime() {
            return new TextTable(HOSEditsTableEnum.LOG_TIME);
        }

        public TextTable entryAdjustedTime() {
            return new TextTable(HOSEditsTableEnum.ADJUSTED_TIME);
        }

        public TextTable entryUsername() {
            return new TextTable(HOSEditsTableEnum.USER_NAME);
        }
    }

    public class HOSEditsTableLinks {
        public SortHeader sortGroupName() {
            return new SortHeader(HOSEditsTableEnum.GROUP_NAME);
        }

        public SortHeader sortDriverName() {
            return new SortHeader(HOSEditsTableEnum.DRIVER_NAME);
        }

        public SortHeader sortEmployeeID() {
            return new SortHeader(HOSEditsTableEnum.EMPLOYEE_ID);
        }

        public SortHeader sortStatus() {
            return new SortHeader(HOSEditsTableEnum.STATUS);
        }

        public SortHeader sortLogTime() {
            return new SortHeader(HOSEditsTableEnum.LOG_TIME);
        }

        public SortHeader sortAdjustedTime() {
            return new SortHeader(HOSEditsTableEnum.ADJUSTED_TIME);
        }

        public SortHeader sortUsername() {
            return new SortHeader(HOSEditsTableEnum.USER_NAME);
        }
    }
}
