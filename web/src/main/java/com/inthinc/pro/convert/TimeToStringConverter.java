package com.inthinc.pro.convert;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.NoAddressFoundException;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.MiscUtil;

public class TimeToStringConverter extends BaseConverter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
    {
        // Implement if an Address component is created
        throw new NotImplementedException("Implement TimeToStringConverter.getAsObject() if an Time component is created");
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
        Number inputTime = Number.class.cast(value);
        Integer integerTime = new Integer(inputTime.intValue());
        return DateUtil.getDurationFromSeconds(integerTime);
    }
    
}
