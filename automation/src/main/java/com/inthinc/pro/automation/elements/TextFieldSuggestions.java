package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextFieldWithSuggestions;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;


public class TextFieldSuggestions extends TextField implements TextFieldWithSuggestions {

    private SeleniumEnums suggestionBox;
    
    public TextFieldSuggestions(SeleniumEnums anEnum, SeleniumEnums suggestionBox) {
	super(anEnum);
	this.suggestionBox = new SeleniumEnumWrapper(suggestionBox);
    }

    public TextFieldSuggestions(SeleniumEnums anEnum, SeleniumEnums suggestionBox, TextEnum replacement) {
	super(anEnum, replacement);
	this.suggestionBox = new SeleniumEnumWrapper(suggestionBox);
    }

    public TextFieldSuggestions(SeleniumEnums anEnum, SeleniumEnums suggestionBox, Integer replaceNumber) {
	super(anEnum, replaceNumber);
	this.suggestionBox = new SeleniumEnumWrapper(suggestionBox);
    }

    public TextFieldSuggestions(SeleniumEnums anEnum, SeleniumEnums suggestionBox, String replaceWord) {
	super(anEnum, replaceWord);
	this.suggestionBox = new SeleniumEnumWrapper(suggestionBox);
    }

    public TextFieldSuggestions(SeleniumEnums anEnum, SeleniumEnums suggestionBox, String prefix,
	    TextEnum replacement) {
	super(anEnum, prefix, replacement);
	this.suggestionBox = new SeleniumEnumWrapper(suggestionBox);
    }


    @Override 
    public TextFieldSuggestions type(String toType){
	selenium.typeKeys(myEnum, toType);
	return this;
    }

    @Override
    public TextLink getSuggestion(Integer row) {
	return new TextLink(suggestionBox, row);
    }
    
    @Override
    public TextLink getSuggestion(String fullName) {
	myEnum.replaceNumber(1);
    	String[] newIds = new String[myEnum.getIDs().length];
    	String threeUpTrTdSpanText = parentXpath+parentXpath+"/tr/td/span[text()='"+fullName+"']";
    	for (int i=0;i<myEnum.getIDs().length;i++){
    		String newId = "";
    		String id = myEnum.getIDs()[i];
    		if(id.startsWith("//")){
    			newId = id + threeUpTrTdSpanText;
    		}else if( !id.contains("=")){
    			newId = "//span[@id='"+id+"']"+threeUpTrTdSpanText;
    		}
    		newIds[i]=newId;
    	}
    	myEnum.setID(newIds);
	return new TextLink(myEnum);
    }

}
