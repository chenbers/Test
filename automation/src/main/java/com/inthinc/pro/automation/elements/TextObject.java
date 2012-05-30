package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;
import com.inthinc.pro.automation.logging.Log;

public class TextObject extends ElementBase implements TextBased {


    public TextObject(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
    }

    @Override
    public Boolean assertContains(String compareAgainst) {
        return assertTrue(getText().contains(compareAgainst), myEnum + " does not contain the text: \"" + compareAgainst +"\"");
    }

    @Override
    public Boolean assertDoesNotContain(String compareAgainst) {
        return assertFalse(getText().contains(compareAgainst), myEnum + " does not contain the text: \"" + compareAgainst +"\"");
    }

    @Override
    public Boolean assertEquals() {
        return assertEquals(myEnum, myEnum, myEnum);
    }

    @Override
    public Boolean assertEquals(String compareAgainst) {
        return assertEquals(compareAgainst, myEnum);
    }

    @Override
    public Boolean assertNotEquals(String compareAgainst) {
        return assertNotEquals(compareAgainst, myEnum);
    }

    @Override
    public Boolean compareDefault() {
        return compare(myEnum.getText());
    }

    @Override
    public Boolean compare(String expected) {
        return compare(expected, getText());
    }

    @Override
    public String getText() {
        String toReturn = getSelenium().getText(myEnum);
        if (toReturn.isEmpty()){
            try {
                toReturn = getAttribute("value");
                if (toReturn == null){
                    toReturn = "";
                }
            } catch (Exception e ){
                Log.info(e);
            }
        }
        return toReturn;
    }

    @Override
    public String toString() {
        return getText();
    }

    @Override
    public Boolean validate() {
        return validateEquals(myEnum.getText(), getText());
    }

    @Override
    public Boolean validate(String expected) {
        return validateEquals(expected, getText());
    }
    
    @Override
    public Boolean validate(TextEnum expected) {
        return validate(expected.getText());
    }

    @Override
    public Boolean validate(TextEnum expected, String replaceOld, String withNew) {
        return validate(expected.getText().replace(replaceOld, withNew));
    }

    @Override
    public Boolean validateContains(String expectedPart) {
        String actualFullString = getText();
        return validateStringContains(expectedPart, actualFullString);
    }

    @Override
    public Boolean validateDoesNotContain(String expectedPart) {
        return assertFalse(getText().contains(expectedPart), myEnum + " should not, but does contain the text: \"" + expectedPart +"\"");
    }

    @Override
    public Boolean validateIsNot(String expected) {
        return assertTrue(getText().contains(expected), myEnum + " does not contain the text: \"" + expected +"\"");
    }
}
