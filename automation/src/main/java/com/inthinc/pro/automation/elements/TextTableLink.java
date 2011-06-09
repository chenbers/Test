package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class TextTableLink extends TextLink implements Clickable, TextBased {
    private TextTable tableText;
    private LinkTable tableLink;
    public TextTableLink(SeleniumEnums anEnum) {
        super(anEnum);
        tableText = new TextTable(myEnum);
        tableLink = new LinkTable(myEnum);
    }
    public TextTableLink(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
        tableText = new TextTable(myEnum);
        tableLink = new LinkTable(myEnum);
    }
    public TextTableLink(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
        tableText = new TextTable(anEnum);
        tableLink = new LinkTable(anEnum);
    }
    
    public TextTableLink(SeleniumEnums anEnum, String replaceWord, TextEnum column){
    	super(anEnum, replaceWord);
    	myEnum.replaceOldWithNew("*column*", column.getText());
    	tableText = new TextTable(myEnum, replaceWord, column);
    	tableLink = new LinkTable(myEnum);
    }
    public TextTableLink(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
        tableText = new TextTable(myEnum);
        tableLink = new LinkTable(myEnum);
    }
    
    @Override
    /**
     * This will only get the first row/item<br />
     * Please use click(Integer row) or <br />
     * click(Integer row, Integer column)<br />
     */
    public String getText(){
        return getText(1);
    }
    
    @Override
    /**
     * This will only get the first row/item<br />
     * Please use click(Integer row) or <br />
     * click(Integer row, Integer column)<br />
     */
    public TextTableLink click(){
        return click(1);
    }
    
    /**
     * Choose the row, or Item number, that you want to get the text of.<br />
     * If you are selecting the item from a 2D table<br />
     * then you want to count row by row left to right.<br />
     * @param row
     * @return
     */
    public String getText(Integer row){
        return tableText.getText(row);
    }
    
    
    /**
     * Choose the row, or Item number, that you want to click.<br />
     * If you are selecting the item from a 2D table<br />
     * then you want to count row by row left to right.<br />
     * @param row
     * @return
     */
    public TextTableLink click(Integer row){
        tableLink.click(row);
        return this;
    }
    
    @Override
    public TextTableLink assertEquals(String compareAgainst){ //TODO: dtanner or jwimmer: Should we default, or fail
    	logger.info("Defaulting to the first entry");
    	assertEquals(1,compareAgainst);
    	return this;
    }
    
    public TextTableLink assertEquals(Integer row, String compareAgainst){
    	assertEquals(getText(row),compareAgainst);
    	return this;
    }
}
