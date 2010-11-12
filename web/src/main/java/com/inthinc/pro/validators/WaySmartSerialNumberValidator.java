package com.inthinc.pro.validators;

import java.util.regex.Pattern;
import javax.faces.component.UIComponent;
import com.inthinc.pro.util.MessageUtil;

public class WaySmartSerialNumberValidator extends RegexValidator {

    public static final Pattern WAYSMART_SERIAL_NUMBER_REGEX = Pattern.compile("MCM[0-9][0-9][0-9][0-9][0-9][0-9]");

    @Override
    protected String getDefaultErrorMessage() {

        return MessageUtil.getMessageString("error_waysmartSerialNumberInvalid");
    }

    @Override
    protected Pattern getRegex(UIComponent component) {
 
        return WAYSMART_SERIAL_NUMBER_REGEX;
    }
}
