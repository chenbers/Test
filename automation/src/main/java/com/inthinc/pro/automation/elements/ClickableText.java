package com.inthinc.pro.automation.elements;

import java.lang.reflect.Method;

import org.jbehave.core.steps.StepCreator.PendingStep;

import com.inthinc.pro.automation.elements.ElementInterface.ClickableTextBased;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;

public class ClickableText extends ClickableObject implements ClickableTextBased {

    public static Object[] getParametersS(PendingStep step, Method method) {
        if (method.getDeclaringClass().isAssignableFrom(TextBased.class)) {
            return TextObject.getParametersS(step, method);
        }
        return ClickableObject.getParametersS(step, method);
    }

    private TextObject textStuff;

    public ClickableText(SeleniumEnums anEnum, Object... objects) {
        super(anEnum, objects);
        textStuff = new Text(anEnum);
    }

    @Override
    public Boolean assertContains(String compareAgainst) {
        return this.textStuff.assertContains(compareAgainst);
    }

    @Override
    public Boolean assertDoesNotContain(String compareAgainst) {
        return this.textStuff.assertDoesNotContain(compareAgainst);
    }

    @Override
    public Boolean assertEquals() {
        return textStuff.assertEquals();
    }

    @Override
    public Boolean assertEquals(String compareAgainst) {
        return textStuff.assertEquals(compareAgainst);
    }

    @Override
    public Boolean assertNotEquals(String compareAgainst) {
        return textStuff.assertNotEquals(compareAgainst);
    }

    @Override
    public Boolean compare() {
        return textStuff.compare();
    }

    @Override
    public Boolean compare(String expected) {
        return textStuff.compare(expected);
    }

    @Override
    public String getText() {
        return textStuff.getText();
    }

    @Override
    public Boolean validate() {
        return textStuff.validate();
    }

    @Override
    public Boolean validate(String expected) {
        return textStuff.validate(expected);
    }

    @Override
    public Boolean validate(TextEnum expected) {
        return textStuff.validate(expected);
    }

    @Override
    public Boolean validate(TextEnum expected, String replaceOld, String withNew) {
        return textStuff.validate(expected, replaceOld, withNew);
    }

    @Override
    public Boolean validateContains(String expectedPart) {
        return textStuff.validateContains(expectedPart);
    }

    @Override
    public Boolean validateDoesNotContain(String expectedPart) {
        return this.textStuff.validateDoesNotContain(expectedPart);
    }

    @Override
    public Boolean validateIsNot(String expected) {
        return this.textStuff.validateIsNot(expected);
    }

}
