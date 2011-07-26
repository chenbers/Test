package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public class TimeOfDay {

    private SeleniumEnumWrapper myEnum;
    
    public TimeOfDay(SeleniumEnums anEnum){
        myEnum = new SeleniumEnumWrapper(anEnum);
    }

    
    public TextField hours(){
        return new TextField(myEnum, "hh");
    }
    
    public Spinner hourSpinner(){
        return new Spinner("hh");
    }
    
    public TextField minutes(){
        return new TextField(myEnum, "mm");
    }
    
    public Spinner minutesSpinner(){
        return new Spinner("mm");
    }
    
    public TextField seconds(){
        return new TextField(myEnum, "ss");
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
            spinnerEnum.replaceWord(type+"buttons");
        }
        
        private void setIds(Integer upOrDown){
            String[] newIDs = spinnerEnum.getIDs();
            newIDs[1] = Xpath.start().table(Id.id(newIDs[1])).tr(upOrDown.toString()).td().input().toString();
        }
        
        public Button up(){
            setIds(1);
            return new Button(spinnerEnum){
                @Override
                public Button click(){
                    selenium.mouseDown(myEnum);
                    selenium.mouseUp(myEnum);
                    return this;
                }
            };
        }
        
        public Button down(){
            setIds(2);
            return new Button(spinnerEnum){
                @Override
                public Button click(){
                    selenium.mouseDown(myEnum);
                    selenium.mouseUp(myEnum);
                    return this;
                }
            };
        }
    }
    
    
}
