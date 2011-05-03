package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.selenium.pageEnums.AdminVehiclesEnum;

public class PageAdminVehicles extends NavigationBar {
    public AdminVehiclesTexts _text() {return new AdminVehiclesTexts();}
    public AdminVehiclesLinks _link() {return new AdminVehiclesLinks();}
    public AdminVehiclesSelects _select() {return new AdminVehiclesSelects();}
    public AdminVehiclesTextFields _textField() { return new AdminVehiclesTextFields();}
    public AdminVehiclesButtons _button() { return new AdminVehiclesButtons();}
    
    public class AdminVehiclesTexts { }
    
    public class AdminVehiclesLinks { }
    
    public class AdminVehiclesSelects { }
    
    public class AdminVehiclesTextFields{
        public TextField search() {return new TextField( AdminVehiclesEnum.SEARCH_TEXT_FIELD);}
    }
    
    public class AdminVehiclesButtons{
        public TextButton search() {return new TextButton( AdminVehiclesEnum.SEARCH_BUTTON);}
    }
}
