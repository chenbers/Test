package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AdminSpeedByStreetEnum;

public class PageAdminSpeedByStreet extends AdminBar {

    public PageAdminSpeedByStreet() {
        checkMe.add(AdminSpeedByStreetEnum.DELETE);
        checkMe.add(AdminSpeedByStreetEnum.TITLE);
    }
    

    @Override
    public SeleniumEnums setUrl() {
        return AdminSpeedByStreetEnum.DEFAULT_URL;
    }

    
    public class AdminSpeedByStreetLinks extends AdminBarLinks {}

    public class AdminSpeedByStreetTexts extends AdminBarTexts {
        
        public Text title(){
            return new Text(AdminSpeedByStreetEnum.TITLE);
        }
        
        public Text message(){
            return new TextFieldLabel(AdminSpeedByStreetEnum.SEARCH_BOX);
        }
    }

    public class AdminSpeedByStreetTextFields extends AdminBarTextFields {
        
        public TextField search(){
            return new TextField(AdminSpeedByStreetEnum.SEARCH_BOX);
        }
    }

    public class AdminSpeedByStreetButtons extends AdminBarButtons {
        
        public TextButton delete(){
            return new TextButton(AdminSpeedByStreetEnum.DELETE);
        }
        
        public TextButton clearAll(){
            return new TextButton(AdminSpeedByStreetEnum.CLEAR_ALL);
        }
        
        public TextButton findAddress(){
            return new TextButton(AdminSpeedByStreetEnum.FIND_ADDRESS);
        }
        
        public TextButton submitRequest(){
            return new TextButton(AdminSpeedByStreetEnum.SUBMIT_REQUEST);
        }
    }

    public class AdminSpeedByStreetDropDowns extends AdminBarDropDowns {}

    public class AdminSpeedByStreetPopUps extends MastheadPopUps {}

    public class AdminSpeedByStreetPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public AdminSpeedByStreetPager _page() {
        return new AdminSpeedByStreetPager();
    }

    public AdminSpeedByStreetLinks _link() {
        return new AdminSpeedByStreetLinks();
    }

    public AdminSpeedByStreetTexts _text() {
        return new AdminSpeedByStreetTexts();
    }

    public AdminSpeedByStreetButtons _button() {
        return new AdminSpeedByStreetButtons();
    }

    public AdminSpeedByStreetTextFields _textField() {
        return new AdminSpeedByStreetTextFields();
    }

    public AdminSpeedByStreetDropDowns _dropDown() {
        return new AdminSpeedByStreetDropDowns();
    }

    public AdminSpeedByStreetPopUps _popUp() {
        return new AdminSpeedByStreetPopUps();
    }

}
