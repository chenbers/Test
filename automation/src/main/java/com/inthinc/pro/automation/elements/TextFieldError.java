package com.inthinc.pro.automation.elements;

import com.inthinc.device.emulation.utils.DeviceUtil.TextEnum;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class TextFieldError extends Text implements TextBased {
	public TextFieldError(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
    }
	
	public TextFieldError(SeleniumEnums anEnum, String prefix, TextEnum replaceWord){
		super(anEnum, prefix + replaceWord.getText());
	}
    
    @Override
    public void setMyEnum(SeleniumEnums anEnum){
    	myEnum = new SeleniumEnumWrapper(anEnum);
    	String[] newIds = new String[myEnum.getIDs().length];
    	String oneUpSpan = parentXpath+"/span";
    	for (int i=0;i<myEnum.getIDs().length;i++){
    		String newId = "";
    		String id = myEnum.getIDs()[i];
    		if(id.startsWith("//")){
    			newId = id + oneUpSpan;
    		}else if( !id.contains("=")){
    			newId = "//input[@id='"+id+"']"+oneUpSpan;
    		}
    		newIds[i]=newId;
    	}
    	myEnum.setID(newIds);
    }
}
