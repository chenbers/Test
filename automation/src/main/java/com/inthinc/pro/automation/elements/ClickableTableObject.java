package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TableBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class ClickableTableObject extends ClickableObject implements Clickable, TableBased{
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
    @Deprecated
    public ClickableTableObject click(){
    	addError(
				".click()",
				"please supply an Integer number for the row on the table)",
				ErrorLevel.FAIL);
    	return null;
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
    
    
    @Override
	public Boolean isVisible(Integer row) {
        myEnum.replaceNumber(row.toString());
		return super.isVisible();
	}
	@Override
	public Boolean isPresent(Integer row) {
        myEnum.replaceNumber(row.toString());
		return super.isPresent();
	}
	@Override
	public ElementInterface focus(Integer row) {
        myEnum.replaceNumber(row.toString());
		return super.focus();
	}
	@Override
	public Boolean assertVisibility(Integer row, Boolean visible) {
        myEnum.replaceNumber(row.toString());
		return super.assertVisibility(visible);
	}
	
	
	@Override
	@Deprecated
	public Boolean isVisible() {
    	addError(
				".isVisible()",
				"please supply an Integer number for the row on the table)",
				ErrorLevel.FAIL);
		return null;
	}
	@Override
	@Deprecated
	public Boolean isPresent() {
    	addError(
				".isPresent()",
				"please supply an Integer number for the row on the table)",
				ErrorLevel.FAIL);
		return null;
	}
	@Override
	@Deprecated
	public ElementInterface focus() {
    	addError(
				".focus()",
				"please supply an Integer number for the row on the table)",
				ErrorLevel.FAIL);
		return null;
	}
	@Override
	@Deprecated
	public Boolean assertVisibility(Boolean visible) {
    	addError(
				".assertVisibility()",
				"please supply an Integer number for the row on the table)",
				ErrorLevel.FAIL);
		return null;
	}
    
}
