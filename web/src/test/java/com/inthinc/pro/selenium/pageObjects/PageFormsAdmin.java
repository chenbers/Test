package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.FormsAdminEnum;

public class PageFormsAdmin extends FormsTables {

    public PageFormsAdmin() {
        page = "person";
        checkMe.add(FormsAdminEnum.DELETE);
        checkMe.add(FormsAdminEnum.APPROVED);
        checkMe.add(FormsAdminEnum.SEARCH_TEXTFIELD);
        checkMe.add(FormsAdminEnum.SEARCH_BUTTON);
        checkMe.add(FormsAdminEnum.EDIT_COLUMNS);
        checkMe.add(FormsAdminEnum.PUBLISH);
        checkMe.add(FormsAdminEnum.FORMS_HEADER);
        checkMe.add(FormsAdminEnum.DESCRIPTION_HEADER);
        checkMe.add(FormsAdminEnum.APPROVED_HEADER);
        checkMe.add(FormsAdminEnum.STATUS_HEADER);
        checkMe.add(FormsAdminEnum.OWNER_HEADER);
        checkMe.add(FormsAdminEnum.EDIT_HEADER);
        checkMe.add(FormsAdminEnum.CHECKBOX);
        checkMe.add(FormsAdminEnum.FORMS_ENTRY);
        checkMe.add(FormsAdminEnum.DESCRIPTION_ENTRY);
        checkMe.add(FormsAdminEnum.APPROVED_ENTRY);
        checkMe.add(FormsAdminEnum.STATUS_ENTRY);
        checkMe.add(FormsAdminEnum.OWNER_ENTRY);
        checkMe.add(FormsAdminEnum.EDIT_ENTRY);        
        
    }

    public class FormsAdminButtons extends NavigationBarButtons {

        public TextButton delete() {
            return new TextButton(FormsAdminEnum.DELETE);
        }
        
        public TextButton approved() {
            return new TextButton(FormsAdminEnum.APPROVED);
        }
        
        public TextButton search() {
        	return new TextButton(FormsAdminEnum.SEARCH_BUTTON);
        }

    }

    public class FormsAdminDropDowns extends NavigationBarDropDowns {}
    
    public class FormsAdminLinks extends NavigationBarLinks {

    	public TextLink editColumns() {
    		return new TextLink(FormsAdminEnum.EDIT_COLUMNS);
    	}
    	
    	public TextLink publish() {
    		return new TextLink(FormsAdminEnum.PUBLISH);
    	}
    	
        public TextTableLink formsEntry() {
            return new TextTableLink(FormsAdminEnum.FORMS_ENTRY);
        }
        
        public TextTableLink descriptionEntry() {
            return new TextTableLink(FormsAdminEnum.DESCRIPTION_ENTRY);
        }
        
        public TextTableLink approvedEntry() {
            return new TextTableLink(FormsAdminEnum.APPROVED_ENTRY);
        }
        
        public TextTableLink statusEntry() {
            return new TextTableLink(FormsAdminEnum.STATUS_ENTRY);
        }
        
        public TextTableLink ownerEntry() {
            return new TextTableLink(FormsAdminEnum.OWNER_ENTRY);
        }
        
        public TextTableLink editEntry() {
            return new TextTableLink(FormsAdminEnum.EDIT_ENTRY);
        }

        public TextLink sortByForms(){
            return new TextLink(FormsAdminEnum.FORMS_HEADER);
        }
        
        public TextLink sortByDescription(){
            return new TextLink(FormsAdminEnum.DESCRIPTION_HEADER);
        }
        
        public TextLink sortByApproved(){
            return new TextLink(FormsAdminEnum.APPROVED_HEADER);
        }
        
        public TextLink sortByStatus(){
            return new TextLink(FormsAdminEnum.STATUS_HEADER);
        }
        
        public TextLink sortByOwner(){
            return new TextLink(FormsAdminEnum.OWNER_HEADER);
        }
        
        public TextLink sortByEdit(){
            return new TextLink(FormsAdminEnum.EDIT_HEADER);
        }

    }

    public class FormsAdminTextFields extends NavigationBarTextFields {

        public TextField search() {
            return new TextField(FormsAdminEnum.SEARCH_TEXTFIELD);
        }

    }
    
    public class FormsAdminTexts extends NavigationBarTexts {}

    public FormsAdminButtons _button() {
        return new FormsAdminButtons();
    }

    public FormsAdminDropDowns _dropDown() {
        return new FormsAdminDropDowns();
    }

    public FormsAdminLinks _link() {
        return new FormsAdminLinks();
    }

    public FormsAdminTexts _text() {
        return new FormsAdminTexts();
    }

    public FormsAdminTextFields _textField() {
        return new FormsAdminTextFields();
    }
    
    public FormsAdminPopUps _popUp(){
        return new FormsAdminPopUps();
    }


    public class FormsAdminPopUps extends NavigationBarPopUps {
        public FormsDelete delete(){
            return new FormsDelete(true);
        }
    }
    
    @Override
    public SeleniumEnums setUrl() {
        return FormsAdminEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _link().sortByForms().isPresent() && _link().sortByApproved().isPresent();
    }
    
}
