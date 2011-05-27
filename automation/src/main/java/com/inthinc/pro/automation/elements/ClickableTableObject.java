package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class ClickableTableObject extends ClickableObject implements Clickable {
	public ClickableTableObject(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }
    public ClickableTableObject(SeleniumEnums anEnum) {
        super(anEnum);
    }
    public ClickableTableObject(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }
    public ClickableTableObject(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
    public ClickableTableObject(SeleniumEnums anEnum, TextEnum replaceWord){
    	super(anEnum, replaceWord);
    }

    
    @Override
    public ClickableTableObject click(){
    	return click(1);
    }
    
    
    /**
     * Choose the row, or Item number, that you want to click.<br />
     * If you are selecting the item from a 2D table<br />
     * then you want to count row by row left to right.<br />
     * @param row
     * @return
     */
    public ClickableTableObject click(Integer row){
    	myEnum.replaceNumber(row.toString());
    	super.click();
    	return this;
    }
    
    
}
