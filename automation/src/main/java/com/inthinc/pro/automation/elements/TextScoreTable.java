package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextTableBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class TextScoreTable extends TextTable implements TextTableBased {

    public TextScoreTable(SeleniumEnums anEnum) {
        super(anEnum);
    }

    public TextScoreTable(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }

    public TextScoreTable(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }

    public TextScoreTable(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }

    public TextScoreTable(SeleniumEnums anEnum, TextEnum replaceWord) {
        super(anEnum, replaceWord);
    }

    @Override
    public void setMyEnum(SeleniumEnums anEnum) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        String[] newIds = new String[myEnum.getIDs().length];
        String scoreBox = "/table/tbody/tr/td";
        for (int i = 0; i < myEnum.getIDs().length; i++) {
            String newId = "";
            String id = myEnum.getIDs()[i];
            if (id.startsWith("//")) {
                newId = id + scoreBox;
            } else if (!id.contains("=")) {
                newId = "//td[@id='" + id + "']" + scoreBox;
            } else {
                continue;
            }
            newIds[i] = newId;
        }
        myEnum.setID(newIds);
    }

}
