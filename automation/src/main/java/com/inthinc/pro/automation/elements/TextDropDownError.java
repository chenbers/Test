package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class TextDropDownError extends Text {

    public TextDropDownError(SeleniumEnums anEnum) {
        super(anEnum);
    }

    public TextDropDownError(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }

    public TextDropDownError(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }

    public TextDropDownError(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
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
                newId = "//select[@id='"+id+"']"+oneUpSpan;
            }
            newIds[i]=newId;
        }
        myEnum.setID(newIds);
    }

}
