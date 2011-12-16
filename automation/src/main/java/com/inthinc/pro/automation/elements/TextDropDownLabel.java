package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class TextDropDownLabel extends Text {

    public TextDropDownLabel(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
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
