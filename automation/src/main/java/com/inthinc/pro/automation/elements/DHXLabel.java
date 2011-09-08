package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;

public class DHXLabel extends TextObject {

    public DHXLabel(SeleniumEnums anEnum, TextEnum replaceWord) {
	super(anEnum, replaceWord);
    }

    public DHXLabel(SeleniumEnums anEnum, String replaceWord,
	    Integer replaceNumber) {
	super(anEnum, replaceWord, replaceNumber);
    }

    public DHXLabel(SeleniumEnums anEnum, String replaceWord) {
	super(anEnum, replaceWord);
    }

    public DHXLabel(SeleniumEnums anEnum, Integer replaceNumber) {
	super(anEnum, replaceNumber);
    }

    public DHXLabel(SeleniumEnums anEnum) {
	super(anEnum);
    }

    @Override
    public void setMyEnum(SeleniumEnums anEnum) {
	myEnum = new SeleniumEnumWrapper(anEnum);
	String[] newIds = new String[myEnum.getIDs().length];
	String oneUpFirstTd = parentXpath + parentXpath + parentXpath
		+ parentXpath + "/td[1]";
	for (int i = 0; i < myEnum.getIDs().length; i++) {
	    String newId = "";
	    String id = myEnum.getIDs()[i];
	    if (id.startsWith("//")) {
		newId = id + oneUpFirstTd;
	    } else if (!id.contains("=")) {
		newId = "//input[@id='" + id + "']" + oneUpFirstTd;
	    } else {
		continue;
	    }
	    newIds[i] = newId;
	}
	myEnum.setID(newIds);
    }

}
