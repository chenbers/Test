package com.inthinc.pro.dao.jpa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.configurator.model.settingDefinitions.DeviceSettingValidation;
import com.inthinc.pro.domain.settingDefinitions.DeviceSettingDefinition;
import com.inthinc.pro.service.DeviceSettingDefinitionService;

@Component
public class SettingHelper {
    private static final Logger logger = LoggerFactory.getLogger(SettingHelper.class);

    private static final Pattern choiceEliminator = Pattern.compile("\\[|\\]|\\(|\\)|\\{|\\}");
    
    @Autowired
    private DeviceSettingDefinitionService deviceSettingDefinitionService;
    
    public List<DeviceSettingValidation> getDeviceSettingValidations(){
		List<DeviceSettingDefinition> deviceSettingDefinitions = deviceSettingDefinitionService.getDeviceSettingDefinitions();
		List<DeviceSettingValidation> deviceSettingValidations = convertDeviceSettingDefinitions(deviceSettingDefinitions);
		return deviceSettingValidations;
    }
	private List<DeviceSettingValidation> convertDeviceSettingDefinitions(List<DeviceSettingDefinition> deviceSettingDefinitions){
		List<DeviceSettingValidation> deviceSettingValidations = new ArrayList<DeviceSettingValidation>();
		for (DeviceSettingDefinition dsd : deviceSettingDefinitions){
			DeviceSettingValidation dsv = convertDeviceSettingDefinition(dsd);
			deviceSettingValidations.add(dsv);
		}
		return deviceSettingValidations;
	}
	private DeviceSettingValidation convertDeviceSettingDefinition(DeviceSettingDefinition dsd){
		DeviceSettingValidation dsv = new DeviceSettingValidation();
		String[] ignoreProperties={"choices","regex"};
		BeanUtils.copyProperties(dsd,dsv,ignoreProperties);
		processRegex(dsv, dsd.getRegex());
		
		return dsv;
	}
	private void processRegex(DeviceSettingValidation deviceSettingValidation, String regexCandidate){
		if (regexCandidate == null) return;
    	try {
    		
    		Pattern regex = Pattern.compile(regexCandidate);
    		
           	//Maybe just choices - then should interpret as such
        	if(containsChoicesOnly(regexCandidate)){
        		
        		deviceSettingValidation.setChoices(Arrays.asList(regexCandidate.split("\\|")));
        	}
        	else {
        		
        		deviceSettingValidation.setRegex(regex);
        	}
    	}
    	catch (PatternSyntaxException pse){
    	    
    		deviceSettingValidation.setRegex(null);
    		deviceSettingValidation.setChoices(null);
    		logger.info("regex "+pse.getPattern()+" threw a PatternSyntaxException - "+pse.getMessage());
    	}
    }    	
	private boolean containsChoicesOnly(String choiceCandidate){
		
		return !choiceEliminator.matcher(choiceCandidate).find() && choiceCandidate.contains("|");
	}
}
