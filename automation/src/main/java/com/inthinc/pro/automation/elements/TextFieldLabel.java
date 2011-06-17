package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class TextFieldLabel extends Text implements TextBased {
	
	public TextFieldLabel(SeleniumEnums anEnum) {
        super(anEnum);
    }
	
	public TextFieldLabel(SeleniumEnums anEnum, TextEnum replaceWord){
		super(anEnum, replaceWord.getText());
	}
	
	public TextFieldLabel(SeleniumEnums anEnum, String prefix, TextEnum replaceWord){
		super(anEnum, prefix + replaceWord.getText());
	}
    public TextFieldLabel(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }
    public TextFieldLabel(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
    public TextFieldLabel(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }
   
    @Override
    public void setMyEnum(SeleniumEnums anEnum){
    	myEnum = new SeleniumEnumWrapper(anEnum);
    	String[] newIds = new String[myEnum.getIDs().length];
    	String twoUpFirstTd = parentXpath+parentXpath+"/td[1]";
    	for (int i=0;i<myEnum.getIDs().length;i++){
    		String newId = "";
    		String id = myEnum.getIDs()[i];
    		if(id.startsWith("//")){
    			newId = id + twoUpFirstTd;
    		}else if( !id.contains("=")){
    			newId = "//input[@id='"+id+"']"+twoUpFirstTd;
    		}
    		newIds[i]=newId;
    	}
    	myEnum.setID(newIds);
    }
}
