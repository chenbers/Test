package com.inthinc.pro.automation.elements;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Pattern;

import com.inthinc.pro.automation.elements.ElementInterface.Calendar;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.WebDateFormat;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.jbehave.RegexTerms;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.utils.AutomationThread;
import com.inthinc.pro.automation.utils.MasterTest;

public class CalendarObject extends DropDown implements Calendar{
    
    private final String id;
    
    public CalendarObject(SeleniumEnums anEnum, Object ...objects){
        super(anEnum, objects);
        id = myEnum.getLocators().get(0);
    }
    
    private void clickPopUpButton(){
        SeleniumEnumWrapper temp = new SeleniumEnumWrapper(myEnum);
        temp.setID(id + "PopupButton");
        new Button(temp).click();
    }
    
    @Override
    @Deprecated
    public SelectableObject selectRow(Integer row){
        throw new IllegalAccessError("This method is deprecated, please use select(AutomationCalendar)");
    }
    
    @Override 
    @Deprecated
    public SelectableObject select(String fullMatch){
        throw new IllegalAccessError("This method is deprecated, please use select(AutomationCalendar)");
    }
    @Override 
    @Deprecated
    public SelectableObject selectThe(String partialMatch, Integer row){
        throw new IllegalAccessError("This method is deprecated, please use select(AutomationCalendar)");
    }
    @Override 
    @Deprecated
    public SelectableObject selectTheOptionContaining(String partialMatch, Integer row){
        throw new IllegalAccessError("This method is deprecated, please use select(AutomationCalendar)");
    }
    @Override 
    @Deprecated
    public String getText(Integer row){
        throw new IllegalAccessError("This method is deprecated, there is no method for Get Text on calendars");
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
    
    @Override
    public Object[] getParameters(String stepAsString, Method method) {
        
        Class<?>[] parameters = method.getParameterTypes();
        Object[] passParameters = new Object[parameters.length];

        AutomationCalendar var = new AutomationCalendar();
        if (stepAsString.contains("\"")){
            int first = stepAsString.indexOf("\"") + 1;
            int last = stepAsString.lastIndexOf("\"");
            var = new AutomationCalendar(stepAsString.substring(first, last));
        } else {
            for (Map.Entry<String, String> variable : MasterTest.getVariables().entrySet()){
                if (stepAsString.contains(variable.getKey())){
                    try {
                        var = new AutomationCalendar(variable.getValue());
                        break;
                    } catch (IllegalArgumentException e){
                        continue;
                    }
                }
            }
        }
        
        passParameters[0] = var;
        
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
