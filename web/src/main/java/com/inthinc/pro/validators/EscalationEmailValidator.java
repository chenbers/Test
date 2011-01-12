package com.inthinc.pro.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.inthinc.pro.util.MessageUtil;

public class EscalationEmailValidator implements Validator {

    private static final String ERROR_MESSSAGE_KEY = "error_title";

    @Override
    public void validate(FacesContext arg0, UIComponent component, Object value) throws ValidatorException {

        if (!isValid((String) value, component))
            throw new ValidatorException(getError(component));

    }

    private Boolean isValid(String value, UIComponent component) {
        boolean hasUserEmail = (value.contains("[") && value.contains("]"));
        String emailPortion = hasUserEmail? value.substring(value.indexOf("[")+1, value.indexOf("]")-1).trim():"ERROR";
        boolean isValidEmail = EmailValidator.EMAIL_REGEX.matcher(emailPortion).matches();
        return (value != null) && (!value.startsWith("ERROR")) && hasUserEmail && isValidEmail;
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
        return MessageUtil.getMessageString("editAlerts_picker_unknownEmail");
    }
}