package com.inthinc.pro.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.inthinc.pro.util.MessageUtil;

public class TextMessageValidator implements Validator {

    private static final String ERROR_MESSSAGE_KEY = "errorMessage";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        if (!isValid((String) value, component)) {
            context.addMessage(component.getId(), getError(component));
            throw new ValidatorException(getError(component));
        }

    }

    private Boolean isValid(String value, UIComponent component) {
        return isValid(value);
    }

    public static Boolean isValid(String value) {
        Pattern p = Pattern.compile("[*\\-%~,]");
        final Matcher matcher = p.matcher(value);
        return !matcher.find();
    }

    protected FacesMessage getError(UIComponent component) {
        return new FacesMessage(FacesMessage.SEVERITY_ERROR, getErrorMessage(component), null);
    }

    protected String getErrorMessage(UIComponent component) {
        String errorMessage = (String) component.getAttributes().get(ERROR_MESSSAGE_KEY);
        if (errorMessage == null)
            errorMessage = getDefaultErrorMessage();
        return errorMessage;
    }

    protected String getDefaultErrorMessage() {
        return MessageUtil.getMessageString("error_invalid");
    }

}
