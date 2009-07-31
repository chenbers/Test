package com.inthinc.pro.convert;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.util.MessageUtil;

public class LbsToKgConverter extends BaseConverter
{
    @Override
    public Object getAsObject(FacesContext context, UIComponent compoent, String value)
    {
        try
        {
            if (value != null && !value.equals(""))
            {
                if (getMeasurementType().equals(MeasurementType.METRIC))
                {
                    Long weight = Long.valueOf(value);
                    return MeasurementConversionUtil.fromKgToPounds(weight).intValue();
                }
                else
                {
                    return Integer.valueOf(value);
                }
            }
        }
        catch (NumberFormatException e)
        {
            final String summary = MessageUtil.getMessageString("error_number_format");
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
            context.addMessage(compoent.getClientId(context), message);
            if(compoent instanceof UIInput){
                ((UIInput)compoent).setValid(false);
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if (Integer.class.isInstance(value))
        {
            if (getMeasurementType().equals(MeasurementType.METRIC))
                return MeasurementConversionUtil.fromPoundsToKg(((Integer) value).longValue()).toString();
            else
                return value.toString();
        }
        return null;
    }

}
