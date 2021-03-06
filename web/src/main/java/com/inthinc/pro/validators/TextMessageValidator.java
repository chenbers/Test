package com.inthinc.pro.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.inthinc.pro.util.MessageUtil;

/**
 * Validator for text messages sent from the portal to capable devices.
 *
 */
public class TextMessageValidator implements Validator {

    private static final String ERROR_MESSSAGE_KEY = "errorMessage";
    protected static final Integer MAX_MESSAGE_LENGTH = 200;

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

    /**
     * Validates text message content.
     * Text messages can contain Letters, Numbers, spaces, and period; but nothing else.
     * Additionally only the first <code>MAX_MESSAGE_LENGTH</code> characters are saved, and subsequently sent.
     * @param value the String to validate
     * @return false ONLY if value contains characters that cannot be sent to text message capable devices
     */
    public static Boolean isValid(String value) {
        //anything other than: words, spaces, digits, and periods
        Pattern p = Pattern.compile("[^\\w\\d. ]");
        final Matcher matcher = p.matcher(value);
        return !matcher.find() && (value.length() <= MAX_MESSAGE_LENGTH);
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
