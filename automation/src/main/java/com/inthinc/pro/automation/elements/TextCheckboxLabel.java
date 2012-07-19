package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class TextCheckboxLabel extends TextTableLink {
	
	public TextCheckboxLabel(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
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
