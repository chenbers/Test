package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextTableBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class TextTable extends TextObject implements TextTableBased{
    public TextTable(SeleniumEnums anEnum) {
        super(anEnum);
    }
    public TextTable(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }
    public TextTable(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
    public TextTable(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }
    public TextTable(SeleniumEnums anEnum, String replaceWord, TextEnum column){
    	super(anEnum, replaceWord);
    	myEnum.replaceOldWithNew("*column*", column.getText());
    }
    
    @Override
	public Boolean assertEquals(Integer row, String compareAgainst) {
        myEnum.replaceNumber(row.toString());
		return super.assertEquals(compareAgainst);
	}
    
    @Override
	@Deprecated
	public Boolean assertEquals(String compareAgainst) {
    	addError(
				".assertEquals()",
				"please supply an Integer number for the row on the table)",
				ErrorLevel.FAIL);
		return null;
	}
    
    
    @Override
	@Deprecated
	public Boolean assertVisibility(Boolean visible) {
    	addError(
				"TextTable.assertVisibility()",
				"please supply an Integer number for the row on the table)",
				ErrorLevel.FAIL);
		return null;
	}
    
    @Override
	public Boolean assertVisibility(Integer row, Boolean visible) {
        myEnum.replaceNumber(row.toString());
		return super.assertVisibility(visible);
	}
    
    @Override
    @Deprecated
    public Boolean compare(){
    	addError(
				"TextTableLink.getText()",
				"cannot compare table Items.  please supply an Integer number)",
				ErrorLevel.FAIL);
    	return null;
    }
    
    @Override
	public Boolean compare(Integer row) {
		return this.compare(row, getText(row));
	}
    
	public Boolean compare(Integer row, String compareAgainst){
    	myEnum.replaceNumber(row.toString());
    	return compare(compareAgainst, getText(row));
    }
	@Override
    @Deprecated
    public Boolean compare(String compareAgainst){
    	addError(
				"TextTableLink.getText()",
				"more information is required to determine WHICH item to compare against.  please supply an Integer number)",
				ErrorLevel.FAIL);
    	return null;
    }
	@Override
	@Deprecated
	public ElementInterface focus() {
    	addError(
				"TextTable.focus()",
				"please supply an Integer number for the row on the table)",
				ErrorLevel.FAIL);
		return null;
	}
	@Override
	public ElementInterface focus(Integer row) {
        myEnum.replaceNumber(row.toString());
		return super.focus();
	}
	
	
	@Override 
    @Deprecated
    public String getText(){
    	addError(
				"TextTable.getText()",
				"more information is required to determine WHICH item to getText from.  please supply an Integer number)",
				ErrorLevel.FAIL);
        return null;
    }
	/**
     * Choose the row, or Item number, that you want to get the text of.<br />
     * If you are selecting the item from a 2D table<br />
     * then you want to count row by row left to right.<br />
     * @param row
     * @return
     */
    public String getText(Integer row){
        myEnum.replaceNumber(row.toString());
        return super.getText();
    }
	/**
     * Needs to be figured out how to make second replacement
     * @param row
     * @param column
     * @return
     */
    public String getText(Integer row, Integer column){
        myEnum.replaceNumber(row.toString());
        return super.getText();
    }
	@Override
	@Deprecated
	public Boolean isPresent() {
    	addError(
				"TextTable.isPresent()",
				"please supply an Integer number for the row on the table)",
				ErrorLevel.FAIL);
		return null;
	}
	@Override
	public Boolean isPresent(Integer row) {
        myEnum.replaceNumber(row.toString());
		return super.isPresent();
	}
	@Override
	@Deprecated
	public Boolean isVisible() {
    	addError(
				"TextTable.isVisible()",
				"please supply an Integer number for the row on the table)",
				ErrorLevel.FAIL);
		return null;
	}
	@Override
	public Boolean isVisible(Integer row) {
        myEnum.replaceNumber(row.toString());
		return super.isVisible();
	}
	@Override
	@Deprecated
	public Boolean validate() {
    	addError(
				"validate()",
				"please supply an Integer number for the row on the table)",
				ErrorLevel.FAIL);
		return null;
	}
	@Override
	public Boolean validate(Integer row) {
        myEnum.replaceNumber(row.toString());
		return super.validate();
	}
	@Override
	public Boolean validate(Integer row, String expected) {
        myEnum.replaceNumber(row.toString());
		return super.validate(expected);
	}
	@Override
	public Boolean validate(Integer row, TextEnum expected) {
        myEnum.replaceNumber(row.toString());
		return super.validate(expected);
	}
	
	
	@Override
	public Boolean validate(Integer row, TextEnum expected, String replaceOld,
			String withNew) {
        myEnum.replaceNumber(row.toString());
		return super.validate(expected, replaceOld, withNew);
	}
	@Override
	@Deprecated
	public Boolean validate(String expected) {
    	addError(
				"validate()",
				"please supply an Integer number for the row on the table)",
				ErrorLevel.FAIL);
		return null;
	}
	
	@Override
	@Deprecated
	public Boolean validate(TextEnum expected) {
    	addError(
				"validate()",
				"please supply an Integer number for the row on the table)",
				ErrorLevel.FAIL);
		return null;
	}
	@Override
	@Deprecated
	public Boolean validate(TextEnum expected, String replaceOld,
			String withNew) {
    	addError(
				"validate()",
				"please supply an Integer number for the row on the table)",
				ErrorLevel.FAIL);
		return null;
	}
	
	
	@Override
	public Boolean validateContains(Integer row, String expectedPart) {
        myEnum.replaceNumber(row.toString());
		return super.validateContains(expectedPart);
	}
	@Override
	@Deprecated
	public Boolean validateContains(String expectedPart) {
    	addError(
				"validateContains()",
				"please supply an Integer number for the row on the table)",
				ErrorLevel.FAIL);
		return null;
	}
}
