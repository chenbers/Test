package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.ExecutiveExpansionBarEnum;

public class ExecutiveExpansionBar extends NavigationBar {

    public ExecutiveExpansionBar() {
        // TODO Auto-generated constructor stub
    }
    
    
    public class ExpansionBarLinks extends NavigationBarLinks{
        
        public TextLink breadCrumb(){
            return new TextLink(ExecutiveExpansionBarEnum.BREADCRUMB);
        }
        
        public TextLink trend(){
            return new TextLink(ExecutiveExpansionBarEnum.TREND);
        }
        
        public TextLink overallScore(){
            return new TextLink(ExecutiveExpansionBarEnum.OVERALL_SCORE);
        }
        
        public TextLink fleetMap(){
            return new TextLink(ExecutiveExpansionBarEnum.FLEET_MAP);
        }
        
        public TextLink fuelEfficiency(){
            return new TextLink(ExecutiveExpansionBarEnum.FUEL_EFFICIENCY);
        }
    }
    public class ExpansionBarTexts extends NavigationBarTexts{
        
        public Text overviewTitle(){
            return new Text(ExecutiveExpansionBarEnum.OVERVIEW_TITLE);
        }
    }
    public class ExpansionBarTextFields extends NavigationBarTextFields{}
    public class ExpansionBarButtons extends NavigationBarButtons{}
    public class ExpansionBarDropDowns extends NavigationBarDropDowns{}
    
    public ExpansionBarLinks _link(){
        return new ExpansionBarLinks();
    }
    
    public ExpansionBarTexts _text(){
        return new ExpansionBarTexts();
    }
        
    public ExpansionBarButtons _button(){
        return new ExpansionBarButtons();
    }
    
    public ExpansionBarTextFields _textField(){
        return new ExpansionBarTextFields();
    }
    
    public ExpansionBarDropDowns _dropDown(){
        return new ExpansionBarDropDowns();
    }
    

}
