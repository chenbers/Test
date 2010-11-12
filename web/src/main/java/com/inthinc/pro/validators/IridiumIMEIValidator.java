package com.inthinc.pro.validators;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.inthinc.pro.dao.util.NumberUtil;
import com.inthinc.pro.util.MessageUtil;

public class IridiumIMEIValidator implements Validator {

    private static final List<String> legalPrefixes;
    
    static {
        legalPrefixes = new ArrayList<String>();
        legalPrefixes.add("30000300");
        legalPrefixes.add("30003401");
        legalPrefixes.add("30012400");
        legalPrefixes.add("30012401");
        legalPrefixes.add("30023401");
    }
    
    public IridiumIMEIValidator() {
        super();
        
    }
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
      String stringValue = (String) value;
      
      if(stringValue.length() != 15) throw new ValidatorException(getError(component));
      
      boolean prefixOK = false;
      for(String prefix :legalPrefixes){
          
          if (stringValue.startsWith(prefix)){
              prefixOK = true;
              break;
          }
      }
      if(!prefixOK) throw new ValidatorException(getError(component));
          
       String suffix = stringValue.substring(8);
       if (!NumberUtil.isInteger(suffix)) {
           
           throw new ValidatorException(getError(component));
       }
    }
    protected FacesMessage getError(UIComponent component)
    {
        return new FacesMessage(FacesMessage.SEVERITY_ERROR, getDefaultErrorMessage(), null);
    }

    protected String getDefaultErrorMessage()
    {
        return MessageUtil.getMessageString("error_iridiumIMEIInvalid");
    }
}
