package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class TextScoreTable extends TextTable {

    public TextScoreTable(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
        setMyEnum();
    }

    private void setMyEnum() {
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
