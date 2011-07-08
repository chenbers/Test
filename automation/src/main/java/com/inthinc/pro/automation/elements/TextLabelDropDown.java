package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class TextLabelDropDown extends Text implements TextBased {

    public TextLabelDropDown(SeleniumEnums anEnum) {
        super(anEnum);
    }

    public TextLabelDropDown(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }

    public TextLabelDropDown(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }

    public TextLabelDropDown(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }
    

    @Override
    public void setMyEnum(SeleniumEnums anEnum){
        myEnum = new SeleniumEnumWrapper(anEnum);
        String[] newIds = new String[myEnum.getIDs().length];
        String upTwoFirstTd = parentXpath+parentXpath+"/td[1]";
        for (int i=0;i<myEnum.getIDs().length;i++){
            String newId = "";
            String id = myEnum.getIDs()[i];
            if(id.startsWith("//")){
                newId = id + upTwoFirstTd;
            }else if( !id.contains("=")){
                newId = "//select[@id='"+id+"']"+upTwoFirstTd;
            }
            newIds[i]=newId;
        }
        myEnum.setID(newIds);
    }

}
