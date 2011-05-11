package com.inthinc.pro.automation.elements;

import java.util.List;

import org.openqa.selenium.By;

import com.inthinc.pro.automation.elements.ElementInterface.Selectable;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.SeleniumValueEnums;
import com.inthinc.pro.automation.enums.TextEnum;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public class DropDown extends Text implements Selectable {
    
    public DropDown(SeleniumEnums anEnum) {
        super(anEnum);
    }
    public DropDown(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }
    public DropDown(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
    public DropDown(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }
    

    @Override
    public DropDown select(String fullMatch) {
    	
        select(fullMatch, 1);
        String selected = selenium.getSelectedLabel(myEnum);
        assertEquals(selected, fullMatch);
        return this;
    }
    
    public DropDown select(TextEnum value){
    	if (value instanceof SeleniumValueEnums){
    		return select(((SeleniumValueEnums) value).getPosition()+1);
    	}
    	return select(value.getText());
    }

    @Override
    public DropDown select(Integer optionNumber) {
        optionNumber--;
        selenium.select(myEnum, "index="+optionNumber);
        String selected = selenium.getSelectedIndex(myEnum);
        assertEquals(selected, optionNumber.toString());
        return this;
    }

    @Override
    public DropDown select(String fullMatch, Integer matchNumber) {
        matchNumber--;
        String xpath = getSelectIDAsXpath();
        if (xpath==null){
        	xpath = getSelectXpath();
        }
        xpath = xpath + "/option["+Id.text(fullMatch)+"]";
        webDriver.findElements(By.xpath(xpath)).get(matchNumber).setSelected();
        return this;
    }

    @Override
    public DropDown selectPartMatch(String partialMatch, Integer matchNumber) {
        matchNumber--;
        String xpath = getSelectIDAsXpath();
        if (xpath==null){
        	xpath = getSelectXpath();
        }
        xpath = xpath + "/option["+Id.contains(Id.text(), partialMatch)+"]";
        webDriver.findElements(By.xpath(xpath)).get(matchNumber).setSelected();
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
    public DropDown selectPartMatch(String partialMatch) {
        return selectPartMatch(partialMatch, 1);
    }
    
    /**
     * Used for the Red Flag selectors on Edit My Account, and Edit User
     * @param selection
     * @return
     */
    protected String getTextValue(SeleniumValueEnums selection) { 
    	String textValue = selenium.getText(selection.getID());
    	if (textValue.isEmpty()) {
	      return selection.getPrefix().getText().replace(":", "");
	  } else {
	      return selection.getPrefix().getText() + selenium.getText(selection.getID());
	  }
    }
}
