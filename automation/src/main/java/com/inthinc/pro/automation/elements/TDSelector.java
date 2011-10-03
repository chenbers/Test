package com.inthinc.pro.automation.elements;


import java.util.List;

import org.openqa.selenium.WebElement;

import com.inthinc.pro.automation.elements.ElementInterface.Selectable;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.SeleniumValueEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;
import com.inthinc.pro.automation.utils.RandomValues;

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
    public void setMyEnum(SeleniumEnums anEnum){
        String[] newIds = new String[myEnum.getIDs().length];
        for (int i=0;i<myEnum.getIDs().length;i++){
            String newId = "";
            String id = myEnum.getIDs()[i];
            if(!id.startsWith("//") || !id.contains("=")){
                newId = "//tbody[@id='"+id+"']";
            }
            newIds[i]=newId;
        }
        myEnum.setID(newIds);
    }
    

    @Override
    public SelectableObject select(String desiredOption) {
        return select(desiredOption, 1);
    }
    
    public SelectableObject select(TextEnum value){
        if (value instanceof SeleniumValueEnums){
            return select(((SeleniumValueEnums) value).getPosition()+1);
        }
        return select(value.getText());
    }
    
    @Override
    public String getText(Integer optionNumber){
        return selenium.getText(addQualifier(optionNumber.toString()));
    }
    
    private SeleniumEnumWrapper addQualifier(String tr, String td){
        SeleniumEnumWrapper temp = new SeleniumEnumWrapper(myEnum);
        String[] ids = temp.getIDs();
        for (int i=0;i<ids.length;i++){
            if (!tr.isEmpty()){
                ids[i] += "/tr["+tr+"]";
            } else {
                ids[i] += "/tr";
            }
            if (!td.isEmpty()){
                ids[i] += "/td["+td+"]";
            }
        }
        return temp;
    }
    
    private SeleniumEnumWrapper addQualifier(String qualifier){
        return addQualifier(qualifier, "");
    }
    
    private SeleniumEnumWrapper addQualifier(){
        return addQualifier("", "");
    }
    
    @Override
    public SelectableObject selectRandom() {
        List<WebElement> elements = getSelenium().findElements(addQualifier());
        int randomIndex = RandomValues.newOne().getInt(elements.size());
        elements.get(randomIndex).click();
        return this;
    }
    
    @Override
    public SelectableObject select(Integer optionNumber) {
        List<WebElement> elements = getSelenium().findElements(addQualifier(optionNumber.toString()));
        elements.get(optionNumber).click();
        return this;
    }

    @Override
    public SelectableObject select(String desiredOption, Integer matchNumber) {
        List<WebElement> elements = getSelenium().findElements(addQualifier("", "text()='" + desiredOption+"'"));
        elements.get(matchNumber).click();
        return this;
    }
    
    @Override
    public SelectableObject selectPartMatch(String partialMatch, Integer matchNumber) {
        List<WebElement> elements = getSelenium().findElements(addQualifier("", "contains(text(),'" + partialMatch+"')"));
        elements.get(matchNumber).click();
        return this;
    }
    

    @Override
    public SelectableObject selectPartMatch(String partialMatch) {
        return selectPartMatch(partialMatch, 1);
    }
}
