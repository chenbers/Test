package com.inthinc.pro.automation.elements;

import org.openqa.selenium.By;

import com.inthinc.pro.automation.elements.ElementInterface.Selectable;

import com.inthinc.pro.automation.enums.AutomationEnum;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.SeleniumValueEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public class DhxDropDown extends DropDown implements Selectable {
	private AutomationEnum dhxImage;
	private String page;

	private SeleniumEnums[] enums;

	public DhxDropDown(SeleniumEnums anEnum) {
		super(anEnum);
	}

	public DhxDropDown(SeleniumEnums anEnum, Integer replaceNumber) {
		super(anEnum, replaceNumber);
	}

	public DhxDropDown(SeleniumEnums anEnum, String replaceWord) {
		super(anEnum, replaceWord);
		page = replaceWord;
	}

	public DhxDropDown(SeleniumEnums anEnum, String replaceWord,
			Integer replaceNumber) {
		super(anEnum, replaceWord, replaceNumber);
	}

	private DhxDropDown assignIDs() {
		Integer div = 1;
		for (SeleniumEnums enume : enums) {
			if (selenium.isElementPresent(AutomationEnum.CORE_ONLY.setEnum(
					enume).replaceWord(page))
					&& enume != null) {
				selenium.assignId("//body/div[" + div + "]", enume.toString());
				div++;
			}
		}
		selenium.click(dhxImage);
		myEnum.setID(myEnum.toString());
		return this;
	}

	public DhxDropDown dropDownButton(SeleniumEnums enume) {
		dhxImage = AutomationEnum.TEMP_ONLY.setEnum(enume).replaceWord(page);
		return this;
	}

	@Override
	public ElementInterface select(Integer optionNumber) {
		assignIDs();
		optionNumber--;
		String xpath = Xpath.start().body().div(Id.id(myEnum.getIDs()[0]))
				.div(optionNumber.toString()).toString();
		selenium.click(xpath);
		return this;
	}

	public ElementInterface select(SeleniumValueEnums option) {
		assignIDs();
		return select(option.getPosition() + 1);
	}

	@Override
	public ElementInterface select(String fullMatch) {
		assignIDs();
		select(fullMatch, 1);
		return this;
	}

	@Override
	public ElementInterface select(String fullMatch, Integer matchNumber) {
		assignIDs();
		matchNumber--;
		String xpath = Xpath.start().div(Id.id(myEnum.getIDs()[1]))
				.div(Id.text(fullMatch)).toString();
		webDriver.findElements(By.xpath(xpath)).get(matchNumber).setSelected();
		return this;
	}

	@Override
	public ElementInterface selectPartMatch(String partialMatch) {
		assignIDs();
		return selectPartMatch(partialMatch, 1);
	}

	@Override
	public ElementInterface selectPartMatch(String partialMatch,
			Integer matchNumber) {
		assignIDs();
		matchNumber--;
		String xpath = Xpath.start().div(Id.id(myEnum.getIDs()[1]))
				.div(Id.contains(Id.text(), partialMatch)).toString();
		webDriver.findElements(By.xpath(xpath)).get(matchNumber).setSelected();
		return this;
	}

	public DhxDropDown tableOptions(SeleniumEnums... enums) {
		this.enums = enums;
		return this;
	}

}
