package com.inthinc.pro.automation.elements;


import java.lang.reflect.Method;
import java.util.List;

import org.jbehave.core.steps.StepCreator.PendingStep;
import org.openqa.selenium.WebElement;

import com.inthinc.pro.automation.elements.ElementInterface.Selectable;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.WordConverterEnum;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.SeleniumValueEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;
import com.inthinc.pro.automation.utils.RandomValues;

public class TDSelector extends SelectableObject implements Selectable {
    

    public TDSelector(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
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
        return getSelenium().getText(addQualifier(optionNumber.toString()));
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
    
    public static Object[] getParametersS(PendingStep step, Method method) {
        String stepAsString = step.stepAsString();
        
        // TODO: dtanner: need a way to handle overloaded methods.
        
        Class<?>[] parameters = method.getParameterTypes();
        Object[] passParameters = new Object[parameters.length];
        
        
        for (int i=0;i<parameters.length;i++){
            Class<?> next = parameters[i];
            if (next.isAssignableFrom(String.class)){
                String lastOfStep = stepAsString.substring(stepAsString.indexOf("\"")+1);
                String toType = lastOfStep.substring(0, lastOfStep.indexOf("\""));
                passParameters[i] = toType;    
            } else if (next.isAssignableFrom(Integer.class)) {
                Integer param = WordConverterEnum.getNumber(stepAsString);
                passParameters[i] = param == null || param == 0 ? 1 : param;
            }
            
            
            if (passParameters[i] == null){
                throw new NoSuchMethodError("We are missing parameters for " 
                            + method.getName() + ", working on step " + step.stepAsString());
            }
        }
        return passParameters;
    }
}
