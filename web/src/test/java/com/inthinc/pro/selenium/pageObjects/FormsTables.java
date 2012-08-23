package com.inthinc.pro.selenium.pageObjects;

public abstract class FormsTables extends FormsBar {

    protected String page;

    public class FormsTablesButtons extends FormsBarButtons {}

    public class FormsTablesDropDowns extends FormsBarDropDowns {}

    public class FormsTablesLinks extends FormsBarLinks {}

    public class FormsTablesTextFields extends FormsBarTextFields {}
    
    public class FormsTablesCheckBoxes {}
    
    public FormsTablesCheckBoxes _checkBox() {
        return new FormsTablesCheckBoxes();
    }

    public class FormsTablesTexts extends FormsBarTexts {}


    public class FormsTablesPopUps {

//        public FormsTablesPopUps() {
//            super(page + "Table");
//        }

//        public EditColumns editColumns() {
//            return new EditColumns();
//        }
    }

}
