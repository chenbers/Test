package com.inthinc.pro.automation.elements;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.inthinc.pro.automation.elements.ElementInterface.TableBased;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.jbehave.RegexTerms;
import com.inthinc.pro.automation.jbehave.StepException;
import com.inthinc.pro.automation.utils.MasterTest;

public class TableNavigator <T extends ElementBase>{
    
    private final TableBased<T> instance;
    private final String stepAsString;
    private final PageScroller scroller;
    
    public TableNavigator(TableBased<T> instance, String stepAsString){
        this(instance, stepAsString, null);
    }
    
    public TableNavigator(TableBased<T> instance, String stepAsString, PageScroller scroller){
        this.instance = instance;
        this.stepAsString = stepAsString;
        this.scroller = scroller;
    }
    
    
    public T getRow(){
        String isNumber = RegexTerms.getMatch(RegexTerms.getRowNumber, stepAsString);
        Integer rowNumber = null;
        if (!isNumber.equals("")){
            rowNumber = Integer.parseInt(isNumber);
        } else {
            Pattern pat = Pattern.compile(RegexTerms.getRowTextNumber);
            Matcher mat = pat.matcher(stepAsString);
            Map<String, String> variables = MasterTest.getVariables(Thread.currentThread().getId());
            while (mat.find()){
                String variableName = stepAsString.substring(mat.start(), mat.end()).toLowerCase();
                if (variables.containsKey(variableName)){
                    rowNumber = Integer.parseInt(MasterTest.getVariables(Thread.currentThread().getId()).get(variableName));
                    break;
                }
                pat = Pattern.compile(RegexTerms.addAnyCaseWordSpaceAfter + RegexTerms.getRowTextNumber);
            }
            if (isNumber.equals("") || rowNumber == null){
                throw new StepException(stepAsString, "Unable to find a matching variable name for step", new NullPointerException("Bad variable name"));
            }
        }
        return instance.row(rowNumber);
    }
    
    public boolean isLoopingStep(){
        return Pattern.compile(RegexTerms.findRow).matcher(stepAsString).find();
    }
    
    public void loopForRow(){
        String textToFind = "";
        if (stepAsString.contains("\"")){
            textToFind = stepAsString.substring(stepAsString.indexOf("\"")+1, stepAsString.lastIndexOf("\"")-1);
        } else {
            String varName = RegexTerms.getMatch(RegexTerms.rowVariable, stepAsString).toLowerCase();
            textToFind = MasterTest.getVariables(Thread.currentThread().getId()).get(varName);
        }
        String variableToSave = RegexTerms.getMatch(RegexTerms.saveRowVariable, stepAsString);
        Iterator<T> itr = instance.iterator();
        while (itr.hasNext()){
            T next = itr.next();
            if (next instanceof TextBased & itr instanceof TableIterator){
                if (((TextBased) next).getText().equals(textToFind)){
                    int rowNum = ((TableIterator<T>)itr).getRowNumber();
                    MasterTest.getVariables(Thread.currentThread().getId()).put(variableToSave, rowNum + "");
                    return;
                }
            } else {
                throw new StepException(stepAsString, "Can only look for text, not " + instance.getClass().getSimpleName(), new IllegalArgumentException());
            }
            if (!itr.hasNext() && scroller != null){
                if (scroller.isPresent()){
                    scroller.click();
                }
            }
        }
    }

}
