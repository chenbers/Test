package com.inthinc.pro.selenium.pageObjects;

public class PageAdminDevicesEdit extends AdminBar {

    public PageAdminDevicesEdit() {
        // TODO Auto-generated constructor stub
    }
    
    public class AdminDevicesEditLinks extends AdminBarLinks{}
    public class AdminDevicesEditTexts extends AdminBarTexts{}
    public class AdminDevicesEditTextFields extends AdminBarTextFields{}
    public class AdminDevicesEditButtons extends AdminBarButtons{}
    public class AdminDevicesEditDropDowns extends AdminBarDropDowns{}
    public class AdminDevicesEditPopUps extends MastheadPopUps{}
    
    
    public class AdminDevicesEditPager{
        public Paging pageIndex(){
            return new Paging();
        }
    }
    
    
    public AdminDevicesEditPager _page(){
        return new AdminDevicesEditPager();
    }
    
    public AdminDevicesEditLinks _link(){
        return new AdminDevicesEditLinks();
    }
    
    public AdminDevicesEditTexts _text(){
        return new AdminDevicesEditTexts();
    }
        
    public AdminDevicesEditButtons _button(){
        return new AdminDevicesEditButtons();
    }
    
    public AdminDevicesEditTextFields _textField(){
        return new AdminDevicesEditTextFields();
    }
    
    public AdminDevicesEditDropDowns _dropDown(){
        return new AdminDevicesEditDropDowns();
    }
    
    public AdminDevicesEditPopUps _popUp(){
        return new AdminDevicesEditPopUps();
    }
    
    
}
