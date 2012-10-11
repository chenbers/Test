package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class TextFieldValue extends Text {

    public TextFieldValue(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
    }

    
    @Override
    public void setMyEnum(SeleniumEnums anEnum){
        myEnum = new SeleniumEnumWrapper(anEnum);
        String[] newIds = new String[myEnum.getIDs().length];
        String upOneText = parentXpath+"/text()";
        for (int i=0;i<myEnum.getIDs().length;i++){
            String newId = "";
            String id = myEnum.getIDs()[i];
            if(id.startsWith("//")){
                newId = id + upOneText;
            }else if( !id.contains("=")){
                newId = "//input[@id='"+id+"']"+upOneText;
            }
            newIds[i]=newId;
        }
        myEnum.setID(newIds);
    }
}
