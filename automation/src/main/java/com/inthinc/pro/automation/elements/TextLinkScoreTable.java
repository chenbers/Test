package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TableBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class TextLinkScoreTable extends TextTableLink implements Clickable,
		TableBased {

    public TextLinkScoreTable(SeleniumEnums anEnum) {
	super(anEnum);
    }

    @Override
    public void setMyEnum(SeleniumEnums anEnum) {
	myEnum = new SeleniumEnumWrapper(anEnum);
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
