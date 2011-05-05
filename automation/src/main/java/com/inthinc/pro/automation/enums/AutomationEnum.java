package com.inthinc.pro.automation.enums;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum AutomationEnum implements SeleniumEnums{
    
    PLACE_HOLDER,
    FIND_ANCHOR_BY_CONTAINS_TEXT(null, null, Xpath.start().a(Id.contains(Id.text(), "***")).toString(), null),
    VERSION(null, "footerForm:version", null, null)
    ;
    
    private String ID, xpath, xpath_alt, text, url;
    
    private AutomationEnum(){
        
    }
    
    private AutomationEnum(String text, String ID, String xpath, String xpath_alt){
        this.ID=ID;
        this.text=text;
        this.xpath=xpath;
        this.xpath_alt=xpath_alt;
    }
    
    public AutomationEnum setEnum(SeleniumEnums myEnum){
        ID = myEnum.getID();
        xpath = myEnum.getXpath();
        url = myEnum.getURL();
        xpath_alt = myEnum.getXpath_alt();
        text = myEnum.getText();
        return this;
    }

    @Override
    public String getID() {
        return ID;
    }

    /**
     * Returns a List of Strings representing the non-null locators for anEnum
     * @param anEnum
     * @return non-null element locator strings
     */
    public List<String> getLocators() {
        ArrayList<String> locators = new ArrayList<String>();
        if(ID != null)
            locators.add(ID);
        if(xpath!= null)
            locators.add(xpath);
        if(xpath_alt != null)
            locators.add(xpath_alt);
        return locators;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getURL() {
        return url;
    }

    @Override
    public String getXpath() {
        return xpath;
    }

    @Override
    public String getXpath_alt() {
        return xpath_alt;
    }

    public AutomationEnum replaceNumber(String number) {
        if (ID!=null){
            ID = ID.replace("###", number);
        }
        if (xpath!=null){
            xpath = xpath.replace("###", number);
        }
        if (xpath_alt!=null){
            xpath_alt = xpath_alt.replace("###", number);
        }
        return this;
    }

    public AutomationEnum replaceWord(String word) {
        if (ID!=null){
            ID = ID.replace("***", word);
        }
        if (xpath!=null){
            xpath = xpath.replace("***", word);
        }
        if (xpath_alt!=null){
            xpath_alt = xpath_alt.replace("***", word);
        }
        return this;
    }


    @Override
    public void setText(String text) {
        this.text = text;
    }
    
    

}
