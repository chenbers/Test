package com.inthinc.pro.automation.elements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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
	    	
	        select(desiredOption, 1);//TODO: if there is no match for desiredOption the whole thing blows up;  preferably selenium error is logged and test TRIES to continue (in case TAE is trying to change multiple selects at one go... then ALL (or at least more) errors can be logged at once)
	        String selected = selenium.getSelectedLabel(myEnum);
	        assertEquals(selected, desiredOption);
	        return this;
	    }
	    
	    public SelectableObject select(TextEnum value){
	    	if (value instanceof SeleniumValueEnums){
	    		return select(((SeleniumValueEnums) value).getPosition()+1);
	    	}
	    	return select(value.getText());
	    }

	    @Override
	    public SelectableObject select(Integer optionNumber) {
	        optionNumber--;
	        selenium.select(myEnum, "index="+optionNumber);
	        String selected = selenium.getSelectedIndex(myEnum);
	        assertEquals(selected, optionNumber.toString());
	        return this;
	    }

	    @Override
	    public SelectableObject select(String desiredOption, Integer matchNumber) {
	        matchNumber--;
	        String xpath = getSelectIDAsXpath();
	        if (xpath==null){
	        	xpath = getSelectXpath();
	        }
	        getMatches(xpath, Id.text(desiredOption), matchNumber).click(); //TODO: related to line 33 issue above
	        return this;
	    }

	    @Override
	    public SelectableObject selectPartMatch(String partialMatch, Integer matchNumber) {
	        matchNumber--;
	        String xpath = getSelectIDAsXpath();
	        if (xpath==null){
	        	xpath = getSelectXpath();
	        }
	        getMatches(xpath, Id.contains(Id.text(), partialMatch), matchNumber).click();
	        return this;
	    }
	    
	    protected WebElement getMatches(String select, String option, Integer matchNumber){
	    	String xpath = select+"/option["+option+"]";
	    	return getMatches(xpath, matchNumber); //TODO: related to line 33 issue above
	    }
	    
	    protected WebElement getMatches(String xpath, Integer matchNumber){
	    	selenium.waitForElementPresent(xpath, 10);
	    	return webDriver.findElements(By.xpath(xpath)).get(matchNumber); //TODO: related to line 33 issue above; ultimately this is where the IndexOutOfBoundsException can occur
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
