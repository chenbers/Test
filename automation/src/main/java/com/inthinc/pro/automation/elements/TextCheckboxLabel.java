package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.ClickableTextTableBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class TextCheckboxLabel extends TextTableLink implements ClickableTextTableBased {
	
	public TextCheckboxLabel(SeleniumEnums anEnum) {
        super(anEnum);
        setMyEnum();
    }
    public TextCheckboxLabel(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
        setMyEnum();
    }
    public TextCheckboxLabel(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
        setMyEnum();
    }
    public TextCheckboxLabel(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
        setMyEnum();
    }
    
    private void setMyEnum(){
    	String[] newIds = new String[myEnum.getIDs().length];
    	String downLable = "/label";
    	for (int i=0;i<myEnum.getIDs().length;i++){
    		String newId = "";
    		String id = myEnum.getIDs()[i];
    		if(id.startsWith("//")){
    			newId = id + downLable;
    		}else if( !id.contains("=")){
    			newId = "//td[@id='"+id+"']"+downLable;
    		}
    		newIds[i]=newId;
    	}
    	myEnum.setID(newIds);
    }
}
