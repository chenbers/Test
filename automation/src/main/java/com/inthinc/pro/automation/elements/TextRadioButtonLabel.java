package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class TextRadioButtonLabel extends Text {

    public TextRadioButtonLabel(SeleniumEnums anEnum) {
        super(anEnum);
    }

    public TextRadioButtonLabel(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }

    public TextRadioButtonLabel(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }

    public TextRadioButtonLabel(SeleniumEnums anEnum, String replaceWord,
            Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }
    
    
    @Override
    public void setMyEnum(SeleniumEnums anEnum){
        myEnum = new SeleniumEnumWrapper(anEnum);
        String[] newIds = myEnum.getIDs();
        for (int i=0;i<myEnum.getIDs().length;i++){
            String newId = "";
            String id = myEnum.getIDs()[i];
            if(id.startsWith("//")){
                newId = id;
            }else if( !id.contains("=")){
                newId = "//label[@for='"+id+"']";
            }
            newIds[i]=newId;
        }
    }

}
