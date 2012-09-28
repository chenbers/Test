package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class TimeOfDay {
	
	private final static String hours = "hh";
	private final static String minutes = "mm";
	private final static String seconds = "ss";
	private final static String am_pm = "am_pm";
	private final static String label = "timeValue";
	

    private SeleniumEnumWrapper myEnum;
    
    public TimeOfDay(SeleniumEnums anEnum){
        myEnum = new SeleniumEnumWrapper(anEnum);
    }
    
    private SeleniumEnumWrapper setID(){
        String[] newIDs = myEnum.getIDs();
        
        int last = newIDs.length - 1;
        String buttons = "//table[@id='" + newIDs[last] + "']";
        String textField = "//td[@id='" + newIDs[last] + "']/input";
        myEnum.setID(textField, buttons);
        return myEnum;
    }

    
    public TextFieldWithSpinner hours(){
        return new TextFieldWithSpinner(setID(), hours);
    }
    
    public TextFieldWithSpinner minutes(){
        return new TextFieldWithSpinner(setID(), minutes);
    }
    
    public TextFieldWithSpinner seconds(){
        return new TextFieldWithSpinner(setID(), seconds);
    }
    
    public DropDown amPm(){
        return new DropDown(myEnum, am_pm);
    }
    
    public Text label(){
        return new TextFieldLabel(myEnum, label);
    }
    
    public Text timeZone(){
        return new Text(setTimeZoneEnum());
    }
    
    private SeleniumEnumWrapper setTimeZoneEnum(){
        SeleniumEnumWrapper temp = new SeleniumEnumWrapper(myEnum);
        temp.replaceWord(label);
        String[] newIds = new String[myEnum.getIDs().length];
        String oneUpFirstTd = ElementBase.parentXpath+"/div/span[5]";
        for (int i=0;i<myEnum.getIDs().length;i++){
            String newId = "";
            String id = myEnum.getIDs()[i];
            if(id.startsWith("//")){
                newId = id + oneUpFirstTd;
            }else if( !id.contains("=")){
                newId = "//input[@id='"+id+"']"+oneUpFirstTd;
            }
            newIds[i]=newId;
        }
        return temp;
    }
    
    
    
}
