package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TextTableBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class TextTableLink extends TextTable implements Clickable, TextTableBased {
	private LinkTable tableLink;

	public TextTableLink(SeleniumEnums anEnum) {
		super(anEnum);
		tableLink = new LinkTable(myEnum);
	}

	public TextTableLink(SeleniumEnums anEnum, Integer replaceNumber) {
		super(anEnum, replaceNumber);
		tableLink = new LinkTable(myEnum);
	}

	public TextTableLink(SeleniumEnums anEnum, String replaceWord) {
		super(anEnum, replaceWord);
		tableLink = new LinkTable(anEnum);
	}

	public TextTableLink(SeleniumEnums anEnum, String replaceWord,
			TextEnum column) {
		super(anEnum, replaceWord);
		myEnum.replaceOldWithNew("*column*", column.getText());
		tableLink = new LinkTable(myEnum);
	}

	public TextTableLink(SeleniumEnums anEnum, String replaceWord,
			Integer replaceNumber) {
		super(anEnum, replaceWord, replaceNumber);
		tableLink = new LinkTable(myEnum);
	}


	@Override
	@Deprecated
	/**
	 * This will only get the first row/item<br />
	 * Please use click(Integer row) or <br />
	 * click(Integer row, Integer column)<br />
	 */
	public TextTableLink click() {
		addError(
				"TextTableLink.click()",
				"more information is required to determine WHICH item to click.  please supply an Integer number)",
				ErrorLevel.FAIL);
		return null;
	}


	/**
	 * Choose the row, or Item number, that you want to click.<br />
	 * If you are selecting the item from a 2D table<br />
	 * then you want to count row by row left to right.<br />
	 * 
	 * @param row
	 * @return
	 */
	public TextTableLink click(Integer row) {
		tableLink.click(row);
		return this;
	}

}
