package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.ClickableTextTableBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class TextLinkScoreTable extends TextTableLink implements ClickableTextTableBased {

    public TextLinkScoreTable(SeleniumEnums anEnum) {
	super(anEnum);
	setMyEnum();
    }

    public void setMyEnum() {
	String[] newIds = new String[myEnum.getIDs().length];
	String scoreBoxLink = "/span/div/div/div/div/div/a";
	for (int i = 0; i < myEnum.getIDs().length; i++) {
	    String newId = "";
	    String id = myEnum.getIDs()[i];
	    if (id.startsWith("//")) {
		newId = id + scoreBoxLink;
	    } else if (!id.contains("=")) {
		newId = "//td[@id='" + id + "']" + scoreBoxLink;
	    } else {
		continue;
	    }
	    newIds[i] = newId;
	}
	myEnum.setID(newIds);
    }

}
