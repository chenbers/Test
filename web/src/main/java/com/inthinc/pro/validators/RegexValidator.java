package com.inthinc.pro.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.inthinc.pro.util.MessageUtil;

public class RegexValidator implements Validator
{
    private static final String REGEX_KEY          = "regex";
    private static final String ERROR_MESSSAGE_KEY = "regexErrorMessage";

    protected Pattern getRegex(UIComponent component)
    {
        final Object regex = component.getAttributes().get(REGEX_KEY);
        if (regex instanceof Pattern)
            return (Pattern) regex;
        else
            return Pattern.compile(regex.toString());
    }

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException
    {
        if (!isValid((String) value, component))
            throw new ValidatorException(getError(component));
    }

    public boolean isValid(String value, UIComponent component)
    {
        final Matcher matcher = getRegex(component).matcher(value);
        return matcher.matches();
    }

    protected FacesMessage getError(UIComponent component)
    {
        return new FacesMessage(FacesMessage.SEVERITY_ERROR, getErrorMessage(component), null);
    }

    protected String getErrorMessage(UIComponent component)
    {
        String errorMessage = (String) component.getAttributes().get(ERROR_MESSSAGE_KEY);
        if (errorMessage == null)
            errorMessage = getDefaultErrorMessage();
        return errorMessage;
    }

    protected String getDefaultErrorMessage()
    {
        return MessageUtil.getMessageString("error_invalid");
    }
}
