package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Link;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldTable;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AdminRouteHazardsEnum;

public class PageAdminRouteHazards extends AdminBar {

    public PageAdminRouteHazards() {
        checkMe.add(AdminRouteHazardsEnum.TITLE);
    }

    public class AdminRouteHazardsLinks extends AdminBarLinks {
    	
    	public Link addDestination() {
    		return new Link(AdminRouteHazardsEnum.ADD_DESTINATION);
    	}
    	
    	public Link sortByDescription() {
    		return new Link(AdminRouteHazardsEnum.SORT_BY_DESCRIPTION);
    	}
    	
    	public Link sortByType() {
    		return new Link(AdminRouteHazardsEnum.SORT_BY_TYPE);
    	}
    	
    	public Link sortByExpTime() {
    		return new Link(AdminRouteHazardsEnum.SORT_BY_EXP_TIME);
    	}
    }

    public class AdminRouteHazardsTexts extends AdminBarTexts {
        
        public Text title(){
            return new Text(AdminRouteHazardsEnum.TITLE);
        }
        
        public Text entryDescription() {
        	return new Text(AdminRouteHazardsEnum.ENTRY_DESCRIPTION);
        }
        
        public Text entryType() {
        	return new Text(AdminRouteHazardsEnum.ENTRY_TYPE);
        }
        
        public Text entryExpTime() {
        	return new Text(AdminRouteHazardsEnum.ENTRY_EXP_TIME);
        }

    }

    public class AdminRouteHazardsTextFields extends AdminBarTextFields {
        
        public TextField origin(){
            return new TextField(AdminRouteHazardsEnum.ORIGIN_TEXTFIELD);
        }
        
        public TextFieldTable addDestination(){
            return new TextFieldTable(AdminRouteHazardsEnum.ADD_DESTINATION_TEXTFIELD);
        }
        
        public TextField destination(){
            return new TextField(AdminRouteHazardsEnum.DESTINATION_TEXTFIELD);
        }

    }

    public class AdminRouteHazardsButtons extends AdminBarButtons {
        
        public TextButton findRoute(){
            return new TextButton(AdminRouteHazardsEnum.FIND_ROUTE_BUTTON);
        }
        
    }

    public class AdminRouteHazardsDropDowns extends AdminBarDropDowns {}
    
    public class RouteHazardsCheckBoxs{}
    
    public RouteHazardsCheckBoxs _checkBox(){
        return new RouteHazardsCheckBoxs();
    }
    
    public class AdminRouteHazardsPopUps extends MastheadPopUps {}

    public class AdminRouteHazardsPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public AdminRouteHazardsPager _page() {
        return new AdminRouteHazardsPager();
    }

    public AdminRouteHazardsLinks _link() {
        return new AdminRouteHazardsLinks();
    }

    public AdminRouteHazardsTexts _text() {
        return new AdminRouteHazardsTexts();
    }

    public AdminRouteHazardsButtons _button() {
        return new AdminRouteHazardsButtons();
    }

    public AdminRouteHazardsTextFields _textField() {
        return new AdminRouteHazardsTextFields();
    }

    public AdminRouteHazardsDropDowns _dropDown() {
        return new AdminRouteHazardsDropDowns();
    }

    public AdminRouteHazardsPopUps _popUp() {
        return new AdminRouteHazardsPopUps();
    }

    @Override
    public SeleniumEnums setUrl() {
        return AdminRouteHazardsEnum.URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _button().findRoute().isPresent() && _textField().origin().isPresent();
    }
}