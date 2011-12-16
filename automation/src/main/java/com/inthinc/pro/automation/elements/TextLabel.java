package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class TextLabel extends Text implements TextBased {

	public TextLabel(SeleniumEnums anEnum, Object... objects) {
		super(anEnum, objects);
	}

	@Override
	public void setMyEnum(SeleniumEnums anEnum) {
		myEnum = new SeleniumEnumWrapper(anEnum);
		String[] newIds = new String[myEnum.getIDs().length];
		String oneUpFirstTd = parentXpath + "/td[1]";
		for (int i = 0; i < myEnum.getIDs().length; i++) {
			String newId = "";
			String id = myEnum.getIDs()[i];
			if (id.startsWith("//")) {
				newId = id + oneUpFirstTd;
			} else if (!id.contains("=")) {
				newId = "//td[@id='" + id + "']" + oneUpFirstTd;
			}
			newIds[i] = newId;
		}
		myEnum.setID(newIds);
	}
}
