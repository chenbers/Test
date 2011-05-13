package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class TableTextLink extends TextLink implements Clickable, TextBased {
    private TableText table;
    public TableTextLink(SeleniumEnums anEnum) {
        super(anEnum);
        table = new TableText(anEnum);
    }
    public TableTextLink(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
        table = new TableText(anEnum);
    }
    public TableTextLink(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
        table = new TableText(anEnum);
    }
    public TableTextLink(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
        table = new TableText(anEnum);
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
    public TableTextLink click(){
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
     * Needs to be figured out how to make second replacement
     * @param row
     * @param column
     * @return
     */
    public String getText(Integer row, Integer column){
        return table.getText(row, column);
    }
    
    /**
     * Choose the row, or Item number, that you want to click.<br />
     * If you are selecting the item from a 2D table<br />
     * then you want to count row by row left to right.<br />
     * @param row
     * @return
     */
    public TableTextLink click(Integer row){
        myEnum.replaceNumber(row.toString());
        super.click();
        return this;
    }
    
    /**
     * Needs to be figured out how to make second replacement
     * @param row
     * @param column
     * @return
     */
    public TableTextLink click(Integer row, Integer column){
        
        return this;
    }
}
