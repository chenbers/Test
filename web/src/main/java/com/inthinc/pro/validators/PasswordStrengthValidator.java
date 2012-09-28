package com.inthinc.pro.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.inthinc.pro.util.MessageUtil;

public class PasswordStrengthValidator implements Validator {

    private static final String ERROR_MESSSAGE_KEY = "error_title";

    @Override
    public void validate(FacesContext arg0, UIComponent component, Object value) throws ValidatorException {
        if (!isValid((String) value))
            throw new ValidatorException(getError(component));
    }

    /**
     * Validates a given password strength string.
     * @param value a String in the form of  ACTUAL_PASSWORD_STRENGTH_INT:MIN_REQUIRED_PASSWORD_STRENGTH_INT
     * @return true if valid, otherwise false
     */
    public static Boolean isValid(String value) {
        String[] values = value.split(":");
        try{
            Integer actual = Integer.parseInt(values[0]);
            Integer minimum = Integer.parseInt(values[1]);
            return actual >= minimum;
        } catch(NumberFormatException nfe) {
            return false;
        } catch(IndexOutOfBoundsException ioobe) {
            return false;
        }
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
        return MessageUtil.getMessageString("passwordStrength_minimumStrengthNotMet");
    }

}