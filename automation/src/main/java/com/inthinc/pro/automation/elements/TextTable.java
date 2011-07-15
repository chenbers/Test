package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextTableBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class TextTable extends TextObject implements TextTableBased {
    
    private SeleniumEnumWrapper baseTable;
    private Integer currentRow;

    // TODO: jwimmer: to dtanner: shouldn't textTables have a size? or number of rows?
    public TextTable(SeleniumEnums anEnum) {
        super(anEnum);
        baseTable = new SeleniumEnumWrapper(anEnum);
    }

    public TextTable(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
        baseTable = new SeleniumEnumWrapper(anEnum);
    }

    public TextTable(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
        baseTable = new SeleniumEnumWrapper(anEnum);
    }

    public TextTable(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
        baseTable = new SeleniumEnumWrapper(anEnum);
    }

    public TextTable(SeleniumEnums anEnum, String replaceWord, TextEnum column) {
        super(anEnum, replaceWord);
        myEnum.replaceOldWithNew("*column*", column.getText());
        baseTable = new SeleniumEnumWrapper(anEnum);
        baseTable.replaceOldWithNew("*column*", column.getText());
    }
    
    @Override
    public ElementBase replaceNumber(Integer row){
        currentRow = row;
        return super.replaceNumber(row);
    }
    

    public TextTable(SeleniumEnums anEnum, TextEnum replaceWord) {
        super(anEnum, replaceWord);
        baseTable = new SeleniumEnumWrapper(anEnum);
    }

    @Override
    public Boolean assertEquals(Integer row) {
        return super.assertEquals();
    }

    @Override
    public Boolean assertEquals(Integer row, String compareAgainst) {
        replaceNumber(row);
        return super.assertEquals(compareAgainst);
    }

    @Override
    @Deprecated
    public Boolean assertEquals(String compareAgainst) {
        addError(".assertEquals()", "please supply an Integer number for the row on the table)", ErrorLevel.FAIL);
        return null;
    }

    @Override
    public Boolean assertNotEquals(Integer row, String compareAgainst) {
        replaceNumber(row);
        return super.assertNotEquals(compareAgainst);
    }

    @Override
    @Deprecated
    public Boolean assertNotEquals(String compareAgainst) {
        addError(".assertNotEquals()", "please supply an Integer number for the row on the table)", ErrorLevel.FAIL);
        return null;
    }

    @Override
    public Boolean assertPresence(Integer row, Boolean present) {
        return assertEquals(present, isPresent(row));
    }

    @Override
    @Deprecated
    public Boolean assertVisibility(Boolean visible) {
        addError("TextTable.assertVisibility()", "please supply an Integer number for the row on the table)", ErrorLevel.FAIL);
        return null;
    }

    @Override
    public Boolean assertVisibility(Integer row, Boolean visible) {
        replaceNumber(row);
        return super.assertVisibility(visible);
    }

    @Override
    @Deprecated
    public Boolean compare() {
        addError("TextTableLink.getText()", "cannot compare table Items.  please supply an Integer number)", ErrorLevel.FAIL);
        return null;
    }

    @Override
    public Boolean compare(Integer row) {
        return compare(row, myEnum.getText());
    }

    public Boolean compare(Integer row, String compareAgainst) {
        replaceNumber(row);
        return compare(compareAgainst, getText(row));
    }

    @Override
    @Deprecated
    public Boolean compare(String compareAgainst) {
        addError("TextTableLink.getText()", "more information is required to determine WHICH item to compare against.  please supply an Integer number)", ErrorLevel.FAIL);
        return null;
    }

    @Override
    @Deprecated
    public ElementInterface focus() {
        addError("TextTable.focus()", "please supply an Integer number for the row on the table)", ErrorLevel.FAIL);
        return null;
    }

    @Override
    public ElementInterface focus(Integer row) {
        replaceNumber(row);
        return super.focus();
    }

    @Override
    @Deprecated
    public String getText() {
        addError("TextTable.getText()", "more information is required to determine WHICH item to getText from.  please supply an Integer number)", ErrorLevel.FAIL);
        return null;
    }

    /**
     * Choose the row, or Item number, that you want to get the text of.<br />
     * If you are selecting the item from a 2D table<br />
     * then you want to count row by row left to right.<br />
     * 
     * @param row
     * @return
     */
    @Override
    public String getText(Integer row) {
        replaceNumber(row);
        return super.getText();
    }

    @Override
    @Deprecated
    public Boolean isPresent() {
        addError("TextTable.isPresent()", "please supply an Integer number for the row on the table)", ErrorLevel.FAIL);
        return null;
    }

    @Override
    public Boolean isPresent(Integer row) {
        replaceNumber(row);
        return super.isPresent();
    }

    @Override
    @Deprecated
    public Boolean isVisible() {
        addError("TextTable.isVisible()", "please supply an Integer number for the row on the table)", ErrorLevel.FAIL);
        return null;
    }

    @Override
    public Boolean isVisible(Integer row) {
        replaceNumber(row);
        return super.isVisible();
    }

    @Override
    @Deprecated
    public Boolean validate() {
        addError("validate()", "please supply an Integer number for the row on the table)", ErrorLevel.FAIL);
        return null;
    }

    @Override
    public Boolean validate(Integer row) {
        return validateEquals(myEnum.getText(), getText(row));
    }

    @Override
    public Boolean validate(Integer row, String expected) {
        return validateEquals(expected, getText(row));
    }

    @Override
    public Boolean validate(Integer row, TextEnum expected) {
        return validateEquals(expected.getText(), getText(row));
    }

    @Override
    public Boolean validate(Integer row, TextEnum expected, String replaceOld, String withNew) {
        replaceNumber(row);
        return super.validate(expected, replaceOld, withNew);
    }

    @Override
    @Deprecated
    public Boolean validate(String expected) {
        addError("validate()", "please supply an Integer number for the row on the table)", ErrorLevel.FAIL);
        return null;
    }

    @Override
    @Deprecated
    public Boolean validate(TextEnum expected) {
        addError("validate()", "please supply an Integer number for the row on the table)", ErrorLevel.FAIL);
        return null;
    }

    @Override
    @Deprecated
    public Boolean validate(TextEnum expected, String replaceOld, String withNew) {
        addError("validate()", "please supply an Integer number for the row on the table)", ErrorLevel.FAIL);
        return null;
    }

    @Override
    public Boolean validateContains(Integer row, String expectedPart) {
        return validateStringContains(expectedPart, getText(row));
    }

    @Override
    @Deprecated
    public Boolean validateContains(String expectedPart) {
        addError("validateContains()", "please supply an Integer number for the row on the table)", ErrorLevel.FAIL);
        return null;
    }

    @Override
    public Boolean validatePresence(Integer row, Boolean present) {
        return validateEquals(present, isPresent(row));
    }

    @Override
    public Boolean validateVisibility(Integer row, Boolean visible) {
        return validateEquals(visible, isVisible(row));
    }
}
