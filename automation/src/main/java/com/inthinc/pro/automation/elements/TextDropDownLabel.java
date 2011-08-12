package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class TextDropDownLabel extends Text {

    public TextDropDownLabel(SeleniumEnums anEnum) {
        super(anEnum);
    }

    public TextDropDownLabel(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }

    public TextDropDownLabel(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }

    public TextDropDownLabel(SeleniumEnums anEnum, String replaceWord,
            Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }
    
    @Override
    public void setMyEnum(SeleniumEnums anEnum){
        myEnum = new SeleniumEnumWrapper(anEnum);
        String[] newIds = new String[myEnum.getIDs().length];
        String twoUpFirstTD = parentXpath+parentXpath+"/td[1]";
        for (int i=0;i<myEnum.getIDs().length;i++){
            String newId = "";
            String id = myEnum.getIDs()[i];
            if(id.startsWith("//")){
                newId = id + twoUpFirstTD;
            }else if( !id.contains("=")){
                newId = "//select[@id='"+id+"']"+twoUpFirstTD;
            }
            newIds[i]=newId;
        }
        myEnum.setID(newIds);
    }

}
