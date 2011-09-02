package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.automation.utils.Xpath;

public class TimeOfDay extends MasterTest{

    private SeleniumEnumWrapper myEnum;
    
    public TimeOfDay(SeleniumEnums anEnum){
        myEnum = new SeleniumEnumWrapper(anEnum);
    }
    
    private SeleniumEnumWrapper setID(){
        String[] newIDs = myEnum.getIDs();
        int last = newIDs.length - 1;
        newIDs[last] = Xpath.start().table(Id.id(newIDs[last])).tbody().tr().td(Id.id(newIDs[last]+"Edit")).input().toString();
        return myEnum;
    }

    
    public TextFieldWithSpinner hours(){
        return new TextFieldWithSpinner(setID(), "hh");
    }
    
    public TextFieldWithSpinner minutes(){
        return new TextFieldWithSpinner(setID(), "mm");
    }
    
    public TextFieldWithSpinner seconds(){
        return new TextFieldWithSpinner(setID(), "ss");
    }
    
    public DropDown amPm(){
        return new DropDown(myEnum, "am_pm");
    }
    
    public Text label(){
        return new TextFieldLabel(myEnum, "timeValue");
    }
    
    public Text timeZone(){
        return new Text(setTimeZoneEnum());
    }
    
    private SeleniumEnumWrapper setTimeZoneEnum(){
        SeleniumEnumWrapper temp = new SeleniumEnumWrapper(myEnum);
        temp.replaceWord("timeValue");
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
