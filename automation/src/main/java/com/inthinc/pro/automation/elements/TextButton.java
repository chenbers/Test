package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class TextButton extends ClickableText implements TextBased, Clickable {

	public TextButton(SeleniumEnums anEnum, Object... objects) {
		super(anEnum, objects);
	}

}