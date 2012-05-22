package com.inthinc.pro.automation.elements;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Pattern;

import org.jbehave.core.steps.StepCreator.PendingStep;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.WebDateFormat;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.jbehave.RegexTerms;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.utils.AutomationThread;
import com.inthinc.pro.automation.utils.MasterTest;

public class Calendar {
    
    private final SeleniumEnumWrapper myEnum;
    private final String id;
    
    public Calendar(SeleniumEnums anEnum){
        myEnum = new SeleniumEnumWrapper(anEnum);
        id = myEnum.getLocators().get(0);
    }
    
    private void clickPopUpButton(){
        SeleniumEnumWrapper temp = new SeleniumEnumWrapper(myEnum);
        temp.setID(id + "PopupButton");
        new Button(temp).click();
    }
    
    
    public Calendar select(AutomationCalendar calendar){
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
        String monthID = "//td[@id='" + id + "Header']/table/tbody/tr/td[3]/div";
        String month = CoreMethodLib.getSeleniumThread().getText(monthID);
        AutomationCalendar now = new AutomationCalendar(month, WebDateFormat.MONTH_YEAR);

        int currentMonth = now.getMonth();
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
        
        Class<?>[] parameters = method.getParameterTypes();
        Object[] passParameters = new Object[parameters.length];

        AutomationCalendar var = new AutomationCalendar();
        passParameters[0] = var;
        if (stepAsString.contains("from \"")){
            int first = stepAsString.indexOf("\"");
            int last = stepAsString.lastIndexOf("\"");
            var = new AutomationCalendar(stepAsString.substring(first, last));
        } else { 
            for (Map.Entry<String, String> variable : MasterTest.getVariables(Thread.currentThread().getId()).entrySet()){
                if (stepAsString.contains("from " + variable.getKey())){
                    var = new AutomationCalendar(variable.getValue());
                }
            }
        }
        int sign = 1;
        if (Pattern.compile(RegexTerms.calendarSubtract).matcher(stepAsString).find()){
            sign = -1;
        }
        
        String days = RegexTerms.getMatch(RegexTerms.calendarDayDelta, stepAsString);
        String months = RegexTerms.getMatch(RegexTerms.calendarMonthDelta, stepAsString);
        String years = RegexTerms.getMatch(RegexTerms.calendarYearDelta, stepAsString);
        if (!days.equals("")){
            var.addToDay(Integer.parseInt(days) * sign);
        }
        if (!months.equals("")){
            var.addToMonth(Integer.parseInt(months) * sign);
        }
        if (!years.equals("")){
            var.addToYear(Integer.parseInt(years) * sign);
        }
        
        return passParameters;
    }
}
