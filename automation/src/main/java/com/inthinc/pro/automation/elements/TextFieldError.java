package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;

public class TextFieldError extends Text implements TextBased {
	public TextFieldError(SeleniumEnums anEnum) {
        super(anEnum);
    }
    public TextFieldError(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }
    public TextFieldError(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
    public TextFieldError(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }
    
    public TextFieldError(SeleniumEnums anEnum, TextEnum replaceWord){
		super(anEnum, replaceWord.getText());
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
