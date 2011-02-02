package com.inthinc.pro.validators;

import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.MiscUtil;

public class PhoneValidator implements Validator {

    private static final String ERROR_MESSSAGE_KEY = "regexErrorMessage";
    public static final Pattern PHONE_REGEX = Pattern.compile("[0-9 \\(\\)\\-]+");

	@Override
	public void validate(FacesContext arg0, UIComponent component, Object value)
			throws ValidatorException {
		
	       if (!isValid((String) value, component))
	            throw new ValidatorException(getError(component));
	 
	}
	
	private Boolean isValid(String value, UIComponent component){
		return (value != null) 
		    && (MiscUtil.unformatPhone(value).length() < 16) 
		    && (MiscUtil.unformatPhone(value).length() >= 10)
		    && PHONE_REGEX.matcher(value).matches();
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
