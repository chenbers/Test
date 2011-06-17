package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class TextCheckboxLabel extends Text implements TextBased{
	
	public TextCheckboxLabel(SeleniumEnums anEnum) {
        super(anEnum);
    }
    public TextCheckboxLabel(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }
    public TextCheckboxLabel(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
    public TextCheckboxLabel(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }
    
    @Override
    public void setMyEnum(SeleniumEnums anEnum){
    	myEnum = new SeleniumEnumWrapper(anEnum);
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
