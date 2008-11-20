package com.inthinc.pro.validators;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class EmailValidator implements Validator {

    private static final String EMAIL_REGEX = "([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    {
        String emailField = (String)value;
        if(!isEmailValid(emailField))
        {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary(" Email not valid.");
            message.setDetail(" Email expected format user@yourdomain.com");
            throw new ValidatorException(message);
        }
    }
    
    public boolean isEmailValid(String email)
    {
        Pattern mask = null;
        mask = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = mask.matcher(email);
        return matcher.matches();
    }	
	
	
	
}
