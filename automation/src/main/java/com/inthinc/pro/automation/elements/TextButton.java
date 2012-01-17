package com.inthinc.pro.automation.elements;

import com.inthinc.device.emulation.utils.DeviceUtil.TextEnum;
import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class TextButton extends ClickableText implements TextBased, Clickable {
	private TextObject text;

	public TextButton(SeleniumEnums anEnum, Object... objects) {
		super(anEnum, objects);
		text = new TextObject(anEnum, objects);
	}

	@Override
	public Boolean validateContains(String expectedPart) {
		return text.validateContains(expectedPart);
	}

	@Override
	public Boolean validate(String expected) {
		return text.validate(expected);
	}

	@Override
	public Boolean validate(TextEnum expected) {
		return validate(expected);
	}

	@Override
	public Boolean validate(TextEnum expected, String replaceOld, String withNew) {
		return validate(expected, replaceOld, withNew);
	}

	@Override
	public Boolean validate() {
		return super.validate();
	}
}