package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;

public class TextLinkTableHeader extends TextLink {

    public TextLinkTableHeader(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
    }


    public TextLinkTableHeader(SeleniumEnums anEnum, String replaceWord,
            TextEnum column) {
        super(anEnum, replaceWord, column);
    }
    
    @Override
    public void setMyEnum(SeleniumEnums anEnum){
        myEnum = new SeleniumEnumWrapper(anEnum);
        String[] newIds = new String[myEnum.getIDs().length];
        
        for (int i=0;i<myEnum.getIDs().length;i++){
            String newId = "";
            String id = myEnum.getIDs()[i];
            newId = id.replace(":###:", ":") + "header:sortDiv";
            newIds[i]=newId;
        }
        
        myEnum.setID(newIds);
    }

}
