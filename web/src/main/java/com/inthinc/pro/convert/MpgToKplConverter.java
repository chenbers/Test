package com.inthinc.pro.convert;

import java.text.DecimalFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
public class MpgToKplConverter extends BaseConverter
{
    private static final DecimalFormat decimalFormat = new DecimalFormat("######.##");
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
    {
        throw new NotImplementedException();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
        if (Number.class.isInstance(value))
        {
            return decimalFormat.format(MeasurementConversionUtil.convertMpgToFuelEfficiencyType((Number.class.cast(value)).doubleValue(),
                                                                                                getUser().getUser().getPerson().getMeasurementType(), 
                                                                                                getUser().getUser().getPerson().getFuelEfficiencyType()));
//            if (getUser().getUser().getPerson().getMeasurementType().equals(MeasurementType.METRIC)){
//                
//                if (getUser().getUser().getPerson().getFuelEfficiencyType().equals(FuelEfficiencyType.KMPL)){
//                    
//                    return decimalFormat.format(MeasurementConversionUtil.fromMPGtoKPL(Number.class.cast(value)).doubleValue());
//                }
//                else if (getUser().getUser().getPerson().getFuelEfficiencyType().equals(FuelEfficiencyType.LP100KM)){
//                    
//                    return decimalFormat.format(MeasurementConversionUtil.fromMPGtoLP100KM(Number.class.cast(value)).doubleValue());
//                }
//            }
//                
//                return decimalFormat.format(MeasurementConversionUtil.fromMPGtoKPL(Number.class.cast(value)).doubleValue());
        }
        return value.toString();
    }
}
