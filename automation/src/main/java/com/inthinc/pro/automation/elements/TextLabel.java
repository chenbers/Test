package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.AutomationEnum;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class TextLabel extends Text implements TextBased{
	
	public TextLabel(SeleniumEnums anEnum) {
        super(anEnum);
    }
    public TextLabel(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }
    public TextLabel(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
    public TextLabel(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }
    
    @Override
    public void setMyEnum(SeleniumEnums anEnum){
    	myEnum = AutomationEnum.PLACE_HOLDER.setEnum(anEnum);
    	String[] newIds = new String[myEnum.getIDs().length];
    	String oneUpFirstTd = parentXpath+"/td[1]";
    	for (int i=0;i<myEnum.getIDs().length;i++){
    		String newId = "";
    		String id = myEnum.getIDs()[i];
    		if(id.startsWith("//")){
    			newId = id + oneUpFirstTd;
    		}else if( !id.contains("=")){
    			newId = "//input[@id='"+id+"']"+oneUpFirstTd;
    		}
    		newIds[i]=newId;
    	}
    	myEnum.setID(newIds);
    }

}
