package com.inthinc.pro.automation.elements;

import java.util.List;

import com.inthinc.pro.automation.elements.ElementInterface.Selectable;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.SeleniumValueEnums;
import com.inthinc.pro.automation.enums.TextEnum;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public class SelectableObject extends Text implements Selectable {
	 public SelectableObject(SeleniumEnums anEnum) {
	        super(anEnum);
	    }
	    public SelectableObject(SeleniumEnums anEnum, Integer replaceNumber) {
	        super(anEnum, replaceNumber);
	    }
	    public SelectableObject(SeleniumEnums anEnum, String replaceWord) {
	        super(anEnum, replaceWord);
	    }
	    public SelectableObject(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
	        super(anEnum, replaceWord, replaceNumber);
	    }
	    
	
	 @Override
	    public SelectableObject select(String desiredOption) {
	    	
	        select(desiredOption, 1);
	        String selected = selenium.getSelectedLabel(myEnum);
	        assertEquals(desiredOption, selected);
	        return this;
	    }
	    
	    public SelectableObject select(TextEnum value){
	    	if (value instanceof SeleniumValueEnums){
	    		return select(((SeleniumValueEnums) value).getPosition()+1);
	    	}
	    	return select(value.getText());
	    }
	    
	    @Override
	    public String getText(Integer optionNumber){
	        return selenium.getSelectOptions(myEnum)[--optionNumber];
	    }

	    @Override
	    public SelectableObject select(Integer optionNumber) {
	        optionNumber--;
	        selenium.select(myEnum, "index="+optionNumber);
	        String selected = selenium.getSelectedIndex(myEnum);
	        assertEquals(optionNumber.toString(), selected);
	        return this;
	    }

	    @Override
	    public SelectableObject select(String desiredOption, Integer matchNumber) {
	        matchNumber--;
	        String xpath = getSelectIDAsXpath();
	        if (xpath==null){
	        	xpath = getSelectXpath();
	        }
	        selenium.click(xpath, Id.text(desiredOption), matchNumber);
	        return this;
	    }

	    @Override
	    public SelectableObject selectPartMatch(String partialMatch, Integer matchNumber) {
	        matchNumber--;
	        String xpath = getSelectIDAsXpath();
	        if (xpath==null){
	        	xpath = getSelectXpath();
	        }
	        selenium.click(xpath, Id.contains(Id.text(), partialMatch), matchNumber);
	        return this;
	    }
	    
	    
	    private String getSelectIDAsXpath(){
	    	List<String> ids = myEnum.getLocators();
	    	for (String locator: ids){
	    		if (!locator.contains("=") || !locator.startsWith("//")){
	    			return Xpath.start().select(Id.id(locator)).toString();
	    		}
	    	}
	    	return null;
	    }
	    
	    private String getSelectXpath(){
	    	List<String> ids = myEnum.getLocators();
	    	for (String locator: ids){
	    		if (!locator.contains("=") && locator.startsWith("//")){
	    			return locator;
	    		}
	    	}
	    	return null;
	    }

	    @Override
	    public SelectableObject selectPartMatch(String partialMatch) {
	        return selectPartMatch(partialMatch, 1);
	    }
}
