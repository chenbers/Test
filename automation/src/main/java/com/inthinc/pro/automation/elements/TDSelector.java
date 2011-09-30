package com.inthinc.pro.automation.elements;


import java.util.List;

import org.openqa.selenium.WebElement;

import com.inthinc.pro.automation.elements.ElementInterface.Selectable;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.SeleniumValueEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.RandomValues;
import com.inthinc.pro.automation.utils.Xpath;

public class TDSelector extends SelectableObject implements Selectable {
    

    public TDSelector(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }

    public TDSelector(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }

    public TDSelector(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }

    public TDSelector(SeleniumEnums anEnum) {
        super(anEnum);
    }
    

    @Override
    public SelectableObject select(String desiredOption) {
        String id = myEnum.getLocators().get(0);
        SeleniumEnumWrapper temp = new SeleniumEnumWrapper(myEnum);
        temp.replaceOldWithNew("###", "");
        id = "//tr[contains(@id,'" + id + "')]/td[text()='" + desiredOption+"']";
        temp.setID(id);
        List<WebElement> elements = getSelenium().findElements(temp);
        elements.get(0).click();
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
    
    public SelectableObject selectRandom() {
        String[] allOptions =selenium.getSelectOptions(myEnum);
        int randomIndex = RandomValues.newOne().getInt(allOptions.length);
        return select(randomIndex);
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
