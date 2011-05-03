package com.inthinc.pro.selenium.pageObjects;


import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.NavTree;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.LiveFleetEnum;
import com.inthinc.pro.selenium.pageEnums.NavigationBarEnum;

public abstract class NavigationBar extends Masthead {
    
    public NavigationTree _navTree(){
        return new NavigationTree();
    }
    
    protected class NavigationBarTexts extends MastheadTexts {
        
        public Text liveFleetHeader() {
            return new Text(LiveFleetEnum.HEADER_BOX_DISPATCH);
        }
    }

    protected class NavigationBarTextFields extends MastheadTextFields{
        
        public TextField searchBox() {
            return new TextField(NavigationBarEnum.SEARCH_BOX);
        }
    }

    protected class NavigationBarLinks extends MastheadLinks {
        
        public TextLink admin() {
            return new TextLink(NavigationBarEnum.ADMIN);
        }

        public TextLink hos() {
            return new TextLink(NavigationBarEnum.HOS);
        }

        public TextLink liveFleet() {
            return new TextLink(NavigationBarEnum.LIVE_FLEET);
        }

        public TextLink notifications() {
            return new TextLink(NavigationBarEnum.NOTIFICATIONS);
        }

        public TextLink reports() {
            return new TextLink(NavigationBarEnum.REPORTS);
        }
    }

    protected class NavigationBarButtons extends MastheadButtons{
        
        public Button search() {
            return new Button(NavigationBarEnum.SEARCH_BUTTON);
        }
    }

    protected class NavigationBarDropDowns extends MastheadDropDowns{
        public DropDown searchDrop() {
            return new DropDown(NavigationBarEnum.SEARCH_BOX);
        }
    }

    protected class NavigationTree{
        public NavTree groups(){
            return new NavTree(NavigationBarEnum.HOME);
        }
    }
}
