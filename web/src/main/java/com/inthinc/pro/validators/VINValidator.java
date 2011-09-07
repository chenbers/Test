package com.inthinc.pro.validators;

import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import com.inthinc.pro.util.MessageUtil;

public class VINValidator extends RegexValidator {
    protected static final String REGEX_KEY = "regex";
    protected static final String DEFAULT_REGEX = "[A-Za-z0-9]+";
    private static final String WARN_MESSSAGE_KEY = "warnMessage";
    private static final String LENGTH_KEY = "length";
    boolean lengthOK = false;
    boolean regexOK = false;

    @Override
    public boolean isValid(String value, UIComponent component) {
        lengthOK = false;
        Integer expectedLength = Integer.parseInt((String)component.getAttributes().get(LENGTH_KEY));
        lengthOK = value.length() == expectedLength;
        regexOK = getRegex(component).matcher(value).matches();
        return regexOK && lengthOK;
    }

    @Override
    protected Pattern getRegex(UIComponent component)
    {
        Object regex = component.getAttributes().get(REGEX_KEY);
        if(regex == null || regex.toString().length()==0)
            regex = DEFAULT_REGEX;
        if (regex instanceof Pattern)
            return (Pattern) regex;
        else
            return Pattern.compile(regex.toString());
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (!isValid((String) value, component)) {
            if (!regexOK)
                throw new ValidatorException(getError(component ));
            if (!lengthOK){ 
                context.addMessage(component.getClientId(context), getWarn(component));
            }
        }
    }
    
    @Override
    protected String getDefaultErrorMessage() {
        return MessageUtil.getMessageString("editVehicle_VINlength");
    }

    protected FacesMessage getWarn(UIComponent component) {
        return new FacesMessage(FacesMessage.SEVERITY_WARN, getWarnMessage(component), null);
    }

    protected String getWarnMessage(UIComponent component) {
        String warnMessage = (String) component.getAttributes().get(WARN_MESSSAGE_KEY);
        if (warnMessage == null)
            warnMessage = getDefaultErrorMessage();
        return warnMessage;
    }
}
