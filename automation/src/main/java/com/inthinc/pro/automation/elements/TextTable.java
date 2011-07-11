package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextTableBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class TextTable extends TextObject implements TextTableBased {

    // TODO: jwimmer: to dtanner: shouldn't textTables have a size? or number of rows?
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

    public TextTable(SeleniumEnums anEnum, String replaceWord, TextEnum column) {
        super(anEnum, replaceWord);
        myEnum.replaceOldWithNew("*column*", column.getText());
    }

    public TextTable(SeleniumEnums anEnum, TextEnum replaceWord) {
        super(anEnum, replaceWord);
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
        return this.compare(row, getText(row));
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
        replaceNumber(row);
        return super.validate();
    }

    @Override
    public Boolean validate(Integer row, String expected) {
        replaceNumber(row);
        return super.validate(expected);
    }

    @Override
    public Boolean validate(Integer row, TextEnum expected) {
        replaceNumber(row);
        return super.validate(expected);
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
        replaceNumber(row);
        return validateStringContains(expectedPart, super.getText());
    }

    @Override
    @Deprecated
    public Boolean validateContains(String expectedPart) {
        addError("validateContains()", "please supply an Integer number for the row on the table)", ErrorLevel.FAIL);
        return null;
    }

    @Override
    public Boolean validateVisibility(Integer row, Boolean visible) {
        replaceNumber(row);
        return super.validateVisibility(visible);
    }

    @Override
    public Boolean validatePresence(Integer row, Boolean present) {
        replaceNumber(row);
        return super.validatePresence(present);
    }

    @Override
    public Boolean assertPresence(Integer row, Boolean present) {
        replaceNumber(row);
        return super.assertPresence(present);
    }

    @Override
    public Boolean assertEquals(Integer row) {
        replaceNumber(row);
        return super.assertEquals();
    }
}
