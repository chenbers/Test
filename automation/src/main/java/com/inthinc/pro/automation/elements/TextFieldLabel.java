package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.AutomationEnum;
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
    	myEnum = AutomationEnum.PLACE_HOLDER.setEnum(anEnum);
    	String[] newIds = new String[myEnum.getIDs().length];
    	String twoParents = "/../../tr[1]";
    	for (int i=0;i<myEnum.getIDs().length;i++){
    		String newId = "";
    		String id = myEnum.getIDs()[i];
    		if(id.startsWith("//")){
    			newId = id + twoParents;
    		}else if( id.contains("=")){
    			newId = "//input[@id='"+id+"']"+twoParents;
    		}
    		newIds[i]=newId;
    	}
    	myEnum.setID(newIds);
    }
}
