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

    
    public TextField hours(){
        return new TextField(setID(), "hh");
    }
    
    public Spinner hourSpinner(){
        return new Spinner("hh");
    }
    
    public TextField minutes(){
        return new TextField(setID(), "mm");
    }
    
    public Spinner minutesSpinner(){
        return new Spinner("mm");
    }
    
    public TextField seconds(){
        return new TextField(setID(), "ss");
    }
    
    public Spinner secondsSpinner(){
        return new Spinner("ss");
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
    
    public class Spinner {
        private SeleniumEnumWrapper spinnerEnum;
        
        public Spinner(String type){
            spinnerEnum = new SeleniumEnumWrapper(myEnum);
            spinnerEnum.replaceWord(type+"Buttons");
        }
        
        private void setIds(Integer upOrDown){
            String[] newIDs = spinnerEnum.getIDs();
            int last = newIDs.length - 1;
            newIDs[last] = Xpath.start().table(Id.id(newIDs[last])).tbody().tr(upOrDown.toString()).td().input().toString();
        }
        
        public Button up(){
            setIds(1);
            return new Button(spinnerEnum){
                @Override
                public Button click(){
                    selenium.mouseDown(spinnerEnum);
                    selenium.mouseUp(spinnerEnum);
                    return this;
                }
            };
        }
        
        public Button down(){
            setIds(2);
            return new Button(spinnerEnum){
                @Override
                public Button click(){
                    selenium.mouseDown(spinnerEnum);
                    selenium.mouseUp(spinnerEnum);
                    return this;
                }
            };
        }
    }
    
    
}
