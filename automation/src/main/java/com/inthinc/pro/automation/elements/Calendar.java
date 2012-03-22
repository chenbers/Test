package com.inthinc.pro.automation.elements;

import java.lang.reflect.Method;

import org.jbehave.core.steps.StepCreator.PendingStep;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.WordConverterEnum;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.objects.AutomationCalendar.WebDateFormat;
import com.inthinc.pro.automation.utils.AutomationThread;

public class Calendar {
    
    private final SeleniumEnumWrapper myEnum;
    private final String id;
    private static AutomationCalendar today = new AutomationCalendar(WebDateFormat.DATE_RANGE_FIELDS); 
    
    public Calendar(SeleniumEnums anEnum){
        myEnum = new SeleniumEnumWrapper(anEnum);
        id = myEnum.getLocators().get(0);
    }
    
    private void clickPopUpButton(){
        SeleniumEnumWrapper temp = new SeleniumEnumWrapper(myEnum);
        temp.setID(id + "PopupButton");
        new Button(temp).click();
    }
    
    
    public Calendar click(AutomationCalendar calendar){
        clickPopUpButton();
        changeMonths(calendar.getMonth());
        clickDay(calendar);
        
        return this;
    }
    
    private boolean clickDay(AutomationCalendar calendar){
        String tempId = "//tr[@id='" + id + "WeekNum" + calendar.getWeekOfMonth() + "']/td[" + (calendar.getDayOfWeek()+1)+ "]";

        SeleniumEnumWrapper temp = new SeleniumEnumWrapper(myEnum);
        temp.setID(tempId);
        new Button(temp).click();
        
        return true;
    }
    
    private boolean changeMonths(Integer desiredMonth){
        int currentMonth = today.getMonth();
        int changeBy = currentMonth - desiredMonth;
        int button;
        if (changeBy > 0){
            button = 2;
        } else if (changeBy < 0){
            button = 4;
        } else {
            return true;
        }
        
        String tempId = "//td[@id='" + id + "Header']/table/tbody/tr/td[" + button + "]/div";

        SeleniumEnumWrapper temp = new SeleniumEnumWrapper(myEnum);
        temp.setID(tempId);
        
        Button arrow = new Button(temp);
        changeBy = Math.abs(changeBy);
        for (;changeBy>0;changeBy--){
            arrow.click();
            AutomationThread.pause(250l);
        }
        return true;
    }
    
    public static Object[] getParametersS(PendingStep step, Method method) {
        String stepAsString = step.stepAsString();
        
        // TODO: dtanner: need a way to handle overloaded methods.
        
        Class<?>[] parameters = method.getParameterTypes();
        Object[] passParameters = new Object[parameters.length];
        
        
        for (int i=0;i<parameters.length;i++){
            Class<?> next = parameters[i];
            if (next.isAssignableFrom(AutomationCalendar.class)){
                String lastOfStep = stepAsString.substring(stepAsString.indexOf("\"")+1);
                String time = lastOfStep.substring(0, lastOfStep.indexOf("\""));
                passParameters[i] = new AutomationCalendar(time, WebDateFormat.DATE_RANGE_FIELDS);
            } else if (next.isAssignableFrom(Integer.class)) {
                passParameters[i] = WordConverterEnum.getNumber(stepAsString);
            }
            
            
            if (passParameters[i] == null){
                throw new NoSuchMethodError("We are missing parameters for " 
                            + method.getName() + ", working on step " + step.stepAsString());
            }
        }
        return passParameters;
    }
}
