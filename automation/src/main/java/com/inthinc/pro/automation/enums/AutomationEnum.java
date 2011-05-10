package com.inthinc.pro.automation.enums;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum AutomationEnum implements SeleniumEnums{
    
    PLACE_HOLDER,
    CORE_ONLY,
    FIND_ANCHOR_BY_CONTAINS_TEXT(null, Xpath.start().a(Id.contains(Id.text(), "***")).toString()),
    VERSION(null, "footerForm:version")
    ;
    
    private String text, url, name;
    private String[] IDs;
    
    private AutomationEnum(String url){
    	this.url = url;
    }
    private AutomationEnum(String text, String ...IDs){
        this.text=text;
    	this.IDs = IDs;
    }

    @Override
    public String[] getIDs() {
        return IDs;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getURL() {
        return url;
    }
    
    private AutomationEnum(){
        
    }

    /**
     * Returns a List of Strings representing the non-null locators for anEnum
     * @param anEnum
     * @return non-null element locator strings
     */
    public List<String> getLocators() {
        ArrayList<String> locators = new ArrayList<String>();
        locators = new ArrayList<String>();
    	for (String ID: IDs){
    		locators.add(ID);
    	}
        return locators;
    }
    
    public AutomationEnum setEnum(SeleniumEnums myEnum){
    	name = myEnum.toString();
    	IDs = myEnum.getIDs();
        url = myEnum.getURL();
        text = myEnum.getText();
        return this;
    }


    public AutomationEnum replaceNumber(String number) {
    	replaceOldWithNew("###", number);
        return this;
    }

    public AutomationEnum replaceWord(String word) {
    	replaceOldWithNew("***", word);
        return this;
    }
    
    public AutomationEnum replaceOldWithNew(String original, String newWord){
    	for (String ID: IDs){
    		ID.replace(original, newWord);
    	}
    	return this;
    }
    
    public String toString(){
    	return name;
    }
    

	public AutomationEnum setText(String text){
		this.text = text;
		return this;
	}
	
	public AutomationEnum setID(String ...IDs){
		this.IDs = IDs;
		return this;
	}
	
	public AutomationEnum setUrl(String url){
		this.url = url;
		return this;
	}

}
