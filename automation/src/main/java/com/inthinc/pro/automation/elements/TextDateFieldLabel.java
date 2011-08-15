package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class TextDateFieldLabel extends Text {

    public TextDateFieldLabel(SeleniumEnums anEnum) {
        super(anEnum);
    }

    public TextDateFieldLabel(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }

    public TextDateFieldLabel(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }

    public TextDateFieldLabel(SeleniumEnums anEnum, String replaceWord,
            Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }

    
    @Override
    public void setMyEnum(SeleniumEnums anEnum){
        myEnum = new SeleniumEnumWrapper(anEnum);
        String[] newIds = new String[myEnum.getIDs().length];
        String threeUpFirstTd = parentXpath+parentXpath+parentXpath+"/td[1]";
        for (int i=0;i<myEnum.getIDs().length;i++){
            String newId = "";
            String id = myEnum.getIDs()[i];
            if(id.startsWith("//")){
                newId = id + threeUpFirstTd;
            }else if( !id.contains("=")){
                newId = "//input[@id='"+id+"']"+threeUpFirstTd;
            }
            newIds[i]=newId;
        }
        myEnum.setID(newIds);
    }
}
