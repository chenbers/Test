package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class TableText extends TextObject implements TextBased {
    public TableText(SeleniumEnums anEnum) {
        super(anEnum);
    }
    public TableText(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }
    public TableText(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
    public TableText(SeleniumEnums anEnum, String replaceWord, TextEnum column){
    	super(anEnum, replaceWord);
    	myEnum.replaceOldWithNew("*column*", column.getText());
    }
    public TableText(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }
    
    @Override 
    @Deprecated
    public String getText(){
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
    
}
