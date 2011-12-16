package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class TextDateFieldLabel extends Text {

    public TextDateFieldLabel(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
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
