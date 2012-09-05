package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.enums.WebDateFormat;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public class DropDownDateRangePicker extends CalendarObject {

	public DropDownDateRangePicker(SeleniumEnums anEnum, Object... objects) {
		super(anEnum, objects);
		
	}
	
	@Override
    public Calendar select(AutomationCalendar calendar){
		
		if (calendar.toString().contains("to")) {
			//TODO: Mweiss need to put code in here to allow for date range selection
			System.out.println("selection contained to");
		}
		
		else {
			new TextField(myEnum).type(calendar.setFormat(WebDateFormat.FILE_NAME));
		}
		
        return this;
    }

}
