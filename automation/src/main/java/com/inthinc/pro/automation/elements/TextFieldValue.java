package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class TextFieldValue extends Text {

    public TextFieldValue(SeleniumEnums anEnum) {
        super(anEnum);
    }

    public TextFieldValue(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }

    public TextFieldValue(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }

    public TextFieldValue(SeleniumEnums anEnum, String replaceWord,
            Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
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
