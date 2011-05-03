package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.ReportsBarEnum;


public abstract class ReportsBar extends NavigationBar {
    
    protected class ReportsBarLinks extends NavigationBarLinks{
    
        public TextLink drivers(){
            return new TextLink(ReportsBarEnum.DRIVERS);
        }
        
        public TextLink vehicles(){
            return new TextLink(ReportsBarEnum.VEHICLES);
        }
        
        public TextLink idling(){
            return new TextLink(ReportsBarEnum.IDLING);
        }
        
        public TextLink devices(){
            return new TextLink(ReportsBarEnum.DEVICES);
        }
        
        public TextLink waySmart(){
            return new TextLink(ReportsBarEnum.WAYSMART);
        }
    }

}
