package com.inthinc.pro.backing.dao.mapper;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.dao.annotations.ConvertFieldToColumn;
import com.inthinc.pro.dao.hessian.mapper.ConfiguratorMapper;
import com.inthinc.pro.model.SensitivitySliderValues;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class DaoUtilConfiguratorMapper extends DaoUtilMapper {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ConfiguratorMapper.class);
    private static final Pattern choiceEliminator = Pattern.compile("\\[|\\]|\\(|\\)|\\{|\\}");

    @ConvertColumnToField(columnName="regex")
    public void regexToModel(DeviceSettingDefinition deviceSettingDefinition, Object value){
    	
        if (deviceSettingDefinition == null || value == null)  return;
        
        if (value instanceof String){
        	
         	String regexCandidate = (String)value;
        	try {
        		
        		Pattern regex = Pattern.compile(regexCandidate);
        		
               	//Maybe just choices - then should interpret as such
            	if(containsChoicesOnly(regexCandidate)){
            		
                    deviceSettingDefinition.setChoices(Arrays.asList(regexCandidate.split("\\|")));
            	}
            	else {
            		
             		deviceSettingDefinition.setRegex(regex);
            	}
        	}
        	catch (PatternSyntaxException pse){
        		
        		logger.debug("regex "+pse.getPattern()+" threw a PatternSyntaxException - "+pse.getMessage());
        	}
        }    	
    }
    private boolean containsChoicesOnly(String choiceCandidate){

    	return !choiceEliminator.matcher(choiceCandidate).find() && choiceCandidate.contains("|");
    }
    @ConvertColumnToField(columnName = "choices")
    public void choicesToModel(DeviceSettingDefinition deviceSettingDefinition, Object value)
    {
        if (deviceSettingDefinition == null || value == null) return;

        if (value instanceof String)
        {
            deviceSettingDefinition.setChoices(Arrays.asList(((String)value).split(":")));
        }
    }
    @SuppressWarnings("unchecked")
    @ConvertColumnToField(columnName = "actual")
    public void actualToModel(VehicleSetting vehicleSetting, Object value)
    {
        if (vehicleSetting == null || value == null) return;
        
        if (value instanceof Map){
            
            vehicleSetting.setActual((Map<Integer,String>) value);
        }
    }
    @SuppressWarnings("unchecked")
    @ConvertColumnToField(columnName = "desired")
    public void desiredToModel(VehicleSetting vehicleSetting, Object value)
    {
        if (vehicleSetting == null || value == null) return;
        
        if (value instanceof Map){
            
            vehicleSetting.setDesired((Map<Integer,String>) value);
        }
    }
    @ConvertColumnToField(columnName = "productVer")
    public void productVerToProductType(VehicleSetting vehicleSetting, Object value)
    {
        if (vehicleSetting == null || value == null) return;
        if (value instanceof Integer){
            
            vehicleSetting.setProductType(ProductType.getProductTypeFromVersion((Integer)value));
        }
    }
    @ConvertColumnToField(columnName = "productType")
    public void productTypeToProductType(SensitivitySliderValues sensitivitySliderValues, Object value)
    {
       if (sensitivitySliderValues == null || value == null) return;
       
       if (value instanceof Integer){
            
            sensitivitySliderValues.setProductType(ProductType.getProductTypeFromVersion((Integer)value));
        }
    }
    
    @SuppressWarnings("unchecked")
    @ConvertFieldToColumn(fieldName = "regex")
    public void regexToMap(DeviceSettingDefinition deviceSettingDefinition, Object value)
    {
        if (deviceSettingDefinition == null || value == null || deviceSettingDefinition.getRegex() == null) return;
        
        if (Map.class.isInstance(value))
        {
            ((Map<String, Object>)value).put("regex", deviceSettingDefinition.getRegex().pattern());
        }

    }

    @SuppressWarnings("unchecked")
    @ConvertFieldToColumn(fieldName = "choices")
    public void choicesMap(DeviceSettingDefinition deviceSettingDefinition, Object value)
    {
        if (deviceSettingDefinition == null || value == null || deviceSettingDefinition.getChoices() == null || deviceSettingDefinition.getChoices().isEmpty()) return;
        
        StringBuffer choicesRegEx = new StringBuffer();
        boolean first = true;
        for (String choice : deviceSettingDefinition.getChoices()) {
            if (!first) {
                choicesRegEx.append('|');
            }
            choicesRegEx.append(choice);
            first = false;
        }
        if (Map.class.isInstance(value))
        {
            ((Map<String, Object>)value).put("regex", choicesRegEx.toString());
        }

    }
}
