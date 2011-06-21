package com.inthinc.pro.automation.enums;

import java.util.ArrayList;
import java.util.List;


public class SeleniumEnumWrapper implements SeleniumEnums{
    
    
    private String text, url, name;
    private String[] IDs;
    

    public SeleniumEnumWrapper(SeleniumEnums myEnum){
    	name = new String(myEnum.toString());
    	if (myEnum.getIDs()!=null){
    		IDs = myEnum.getIDs().clone();
    	}
        if (myEnum.getURL()!=null){
        	url = new String(myEnum.getURL());
        }
        if (myEnum.getText()!=null){
        	text = new String(myEnum.getText());
        }
    }

    @Override
    public String[] getIDs() {
        return IDs;
    }

    /**
     * Returns a List of Strings representing the non-null locators for anEnum
     * @param anEnum
     * @return non-null element locator strings
     */
    public List<String> getLocators() {
        List<String> locators = new ArrayList<String>();
        if(IDs != null) {
        	for (String ID: IDs){
        		locators.add(ID);
        	}
        }
        return locators;
    }
    

    public String getLocatorsAsString() {
        StringBuffer results = new StringBuffer();
        for(String s: getLocators()){
            results.append(s+"; ");
        }
        return results.toString();
    }
    
    @Override
    public String getText() {
        return text;
    }
    
    @Override
    public String getURL() {
        return url;
    }


    public SeleniumEnumWrapper replaceNumber(Integer number) {
    	replaceOldWithNew("###", number.toString());
        return this;
    }

    public SeleniumEnumWrapper replaceOldWithNew(String original, String newWord){
    	for (int i=0;i<IDs.length;i++){
    		if (original.equals("###") && !IDs[i].startsWith("//")){
    			newWord = Integer.parseInt(newWord)-1+"";
    		}
    		IDs[i]=IDs[i].replace(original, newWord);
    	}
    	return this;
    }
    
    public SeleniumEnumWrapper replaceWord(String word) {
    	replaceOldWithNew("***", word);
        return this;
    }
    
    public SeleniumEnumWrapper setID(String ...IDs){
		this.IDs = IDs;
		return this;
	}
    

	public SeleniumEnumWrapper setName(String name){
		this.name = name;
		return this;
	}
	
	public SeleniumEnumWrapper setText(String text){
		this.text = text;
		return this;
	}
	
	public SeleniumEnumWrapper setUrl(String url){
		this.url = url;
		return this;
	}
	
	public String toString(){
    	return name;
    }

}
