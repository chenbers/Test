package com.inthinc.pro.automation.jbehave;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import org.jbehave.core.steps.Step;
import org.jbehave.core.steps.StepCreator.PendingStep;

import com.inthinc.pro.automation.elements.ElementInterface.TableBased;
import com.inthinc.pro.automation.elements.TableNavigator;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.utils.MasterTest;

public class AutoActionFinder {
    
    private AutoStepCreator stepCreator;
    
    public void setStepCreator(AutoStepCreator stepCreator){
        this.stepCreator = stepCreator;
    }
    
    
    public AutoActionFinder(){
    }
    

    public Step findAction(Object elementInstance, String elementType, String elementName, PendingStep step) throws SecurityException, NoSuchMethodException {
        if (stepCreator == null){ 
            throw new NullPointerException("We need a copy of the StepCreator");
        }
        String stepAsString = step.stepAsString();
        try {
            if (elementInstance instanceof MasterTest){ 
                String save = RegexTerms.getMatch(RegexTerms.saveAlias, stepAsString);
                if (!save.equals("")){
                    Method method = ((MasterTest) elementInstance).parseStep(stepAsString.replace(save, "get text"), elementType);
                    Object[] parameters = ((MasterTest) elementInstance).getParameters(step, method);
                    
                    return stepCreator.createSaveVariableStep(step, elementInstance, method, parameters);
                
                } else if (stepAsString.contains("validate") || stepAsString.contains("assert")){
                    Map<Method, Object[]> methods = ((MasterTest) elementInstance).parseValidationStep(step, elementType);
                    Entry<Method, Object[]> method = methods.entrySet().iterator().next();

                    return stepCreator.createValidationStep(step, elementInstance, method.getKey(), method.getValue());
                }
                    
                    
                Method method = ((MasterTest) elementInstance).parseStep(stepAsString, elementType);
                Object[] parameters = ((MasterTest) elementInstance).getParameters(step, method);
                return stepCreator.createPageStep(step, elementInstance, method, parameters);
            } else if (elementInstance instanceof TableBased<?>){
                @SuppressWarnings({ "unchecked", "rawtypes" })
                TableNavigator<?> navigator = new TableNavigator((TableBased<?>) elementInstance, stepAsString);
                if (navigator.isLoopingStep()){
                    navigator.loopForRow();
                    return stepCreator.createEmptyStep(stepAsString);
                } else {
                    return findAction(navigator.getRow(), elementType, elementName, step);
                }
            }
        } catch (Exception e){
            Log.info(e);
        }
        
        
        throw new NoSuchMethodException("Cannot find an action for " + step.stepAsString());
    }

}
