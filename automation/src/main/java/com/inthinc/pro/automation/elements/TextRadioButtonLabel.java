package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class TextRadioButtonLabel extends Text {

    public TextRadioButtonLabel(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
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
