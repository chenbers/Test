package com.inthinc.pro.automation.enums;

import java.util.ArrayList;
import java.util.List;

public class SeleniumEnumUtil {
    /**
     * Returns a List of Strings representing the non-null locators for anEnum
     * @param anEnum
     * @return non-null element locator strings
     */
    public static List<String> getLocators(SeleniumEnums anEnum) {
        ArrayList<String> locators = new ArrayList<String>();
        if(anEnum.getID() != null)
            locators.add(anEnum.getID());
        if(anEnum.getXpath()!= null)
            locators.add(anEnum.getXpath());
        if(anEnum.getXpath_alt() != null)
            locators.add(anEnum.getXpath_alt());
        return locators;
    }
    
    
    
    
    
    
    
    public enum UtilEnum implements SeleniumEnums {
        FIND_ANCHOR_BY_CONTAINS_TEXT(null, null, "//a[contains(text(), \"***\")]", "",null)
        ;
        
        private String text, ID, xpath, xpath_alt, url;
        
        private UtilEnum( String text, String ID, String xpath, String xpath_alt, String url) {
            this.text=text;
            this.ID=ID;
            this.xpath=xpath;
            this.xpath_alt=xpath_alt;
            this.url=url;
        }

        @Override
        public String getID() {
            // TODO Auto-generated method stub
            return this.ID;
        }

        @Override
        public String getText() {
            // TODO Auto-generated method stub
            return this.text;
        }

        @Override
        public String getXpath() {
            // TODO Auto-generated method stub
            return this.xpath;
        }

        @Override
        public String getXpath_alt() {
            // TODO Auto-generated method stub
            return this.xpath_alt;
        }

        @Override
        public void setText(String text) {
            // TODO Auto-generated method stub
            this.text=text;
        }

        @Override
        public String getURL() {
            // TODO Auto-generated method stub
            return this.url;
        }
        @Override
        public List<String> getLocators() {        
            return SeleniumEnumUtil.getLocators(this);
        }
    }
}
