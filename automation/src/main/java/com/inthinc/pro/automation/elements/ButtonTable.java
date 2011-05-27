package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class ButtonTable extends ClickableTableObject implements Clickable {

	
	public ButtonTable(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }
    public ButtonTable(SeleniumEnums anEnum) {
        super(anEnum);
    }
    public ButtonTable(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }
    public ButtonTable(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
    public ButtonTable(SeleniumEnums anEnum, TextEnum replaceWord){
    	super(anEnum, replaceWord);
    }
}
