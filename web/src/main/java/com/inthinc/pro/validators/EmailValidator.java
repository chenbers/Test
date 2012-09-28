package com.inthinc.pro.validators;

import java.util.regex.Pattern;

import javax.faces.component.UIComponent;

import com.inthinc.pro.util.MessageUtil;

public class EmailValidator extends RegexValidator
{
    public static final Pattern EMAIL_REGEX = Pattern.compile("([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
    public static final Pattern DELETED_REGEX = Pattern.compile("del[0-9]+@tiwi.com");

    @Override
    protected Pattern getRegex(UIComponent component)
    {
        return EMAIL_REGEX;
    }

    @Override
    public boolean isValid(String value, UIComponent component)
    {
        if (super.isValid(value, component))
            return !DELETED_REGEX.matcher(value).matches();
        return false;
    }

    @Override
    protected String getDefaultErrorMessage()
    {
        return MessageUtil.getMessageString("error_emailInvalid");
    }
}
