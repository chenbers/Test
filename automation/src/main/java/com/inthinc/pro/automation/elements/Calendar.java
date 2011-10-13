package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutomationCalendar.WebDateFormat;
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
    
    private boolean changeMonths(int desiredMonth){
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
}
