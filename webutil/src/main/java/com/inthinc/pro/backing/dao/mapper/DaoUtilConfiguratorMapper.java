package com.inthinc.pro.backing.dao.mapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.dao.hessian.mapper.ConfiguratorMapper;
import com.inthinc.pro.model.SensitivitySliderValues;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class DaoUtilConfiguratorMapper extends DaoUtilMapper {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ConfiguratorMapper.class);
    private static final Pattern choiceEliminator = Pattern.compile("\\[|\\]|\\(|\\)|\\{|\\}");

    public Map<String, Object> convertToMap(Object modelObject, boolean includeNonUpdateables) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (modelObject != null) {
	        if (modelObject instanceof VehicleSetting) {
		        VehicleSetting vs = (VehicleSetting) modelObject;
		        map.put("vehicleID", vs.getVehicleID());
		        map.put("deviceID", vs.getDeviceID());
		        map.put("productVer", vs.getProductType().getVersion());
		        map.put("actual", vs.getActual());
		        map.put("desired", vs.getDesired());
	        }    
	        if (modelObject instanceof DeviceSettingDefinition) {
	        	DeviceSettingDefinition ds = (DeviceSettingDefinition) modelObject;
		        map.put("category", ds.getCategory());
		        map.put("settingID", ds.getSettingID());
		        map.put("name", ds.getName());
		        map.put("description", ds.getDescription());
		        map.put("choices", (ds.getChoices()!=null) ? ds.getChoices().toString().replace("[", "").replace("]", "") : "");
		        map.put("ignore", ds.getIgnore());
		        map.put("min", ds.getMin());
		        map.put("max", ds.getMax());
		        map.put("unit", ds.getUnit());
		        map.put("varType", ds.getVarType());
		        map.put("ignore", ds.getIgnore());
		        map.put("visibility", ds.getVisibility());
	        }
        }    
        return map;
    }

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
}
