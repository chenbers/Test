package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextTableBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class TextScoreTable extends TextTable implements TextTableBased {

    public TextScoreTable(SeleniumEnums anEnum) {
        super(anEnum);
        // TODO Auto-generated constructor stub
    }

    public TextScoreTable(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
        // TODO Auto-generated constructor stub
    }

    public TextScoreTable(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
        // TODO Auto-generated constructor stub
    }

    public TextScoreTable(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
        // TODO Auto-generated constructor stub
    }

    public TextScoreTable(SeleniumEnums anEnum, TextEnum replaceWord) {
        super(anEnum, replaceWord);
        // TODO Auto-generated constructor stub
    }

    @Override
    public TextScoreTable replaceNumber(Integer row) {
        super.replaceNumber(--row);
        return this;
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
