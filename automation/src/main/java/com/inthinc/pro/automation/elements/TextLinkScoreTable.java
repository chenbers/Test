package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TableBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class TextLinkScoreTable extends TextTableLink implements Clickable,
		TableBased {

	public TextLinkScoreTable(SeleniumEnums anEnum) {
		super(anEnum);
	}
	
	@Override
	public TextLinkScoreTable replaceNumber(Integer row){
		super.replaceNumber(row--);
		return this;
	}

}
