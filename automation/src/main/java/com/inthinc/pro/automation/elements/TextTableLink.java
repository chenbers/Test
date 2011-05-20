package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class TextTableLink extends TextLink implements Clickable, TextBased {
    private TextTable table;
    public TextTableLink(SeleniumEnums anEnum) {
        super(anEnum);
        table = new TextTable(anEnum);
    }
    public TextTableLink(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
        table = new TextTable(anEnum);
    }
    public TextTableLink(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
        table = new TextTable(anEnum);
    }
    
    public TextTableLink(SeleniumEnums anEnum, String replaceWord, TextEnum column){
    	super(anEnum, replaceWord);
    	table = new TextTable(anEnum, replaceWord, column);
    	myEnum.replaceOldWithNew("*column*", column.getText());
    }
    public TextTableLink(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
        table = new TextTable(anEnum);
    }
    
    @Override
    @Deprecated
    /**
     * Please use click(Integer row) or <br />
     * click(Integer row, Integer column)<br />
     */
    public String getText(){
        return null;
    }
    
    @Override
    @Deprecated
    /**
     * Please use click(Integer row) or <br />
     * click(Integer row, Integer column)<br />
     */
    public TextTableLink click(){
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
        return table.getText(row);
    }
    
    
    /**
     * Choose the row, or Item number, that you want to click.<br />
     * If you are selecting the item from a 2D table<br />
     * then you want to count row by row left to right.<br />
     * @param row
     * @return
     */
    public TextTableLink click(Integer row){
        myEnum.replaceNumber(row.toString());
        super.click();
        return this;
    }
}
