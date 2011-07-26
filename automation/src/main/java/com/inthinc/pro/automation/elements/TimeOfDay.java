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
