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
	
	private String removeScript =
		"var myBody = document.getElementsByTagName(\"body\"[1]);" +
		"var div = document.getElementsByTagName(\"div\")[1];" +
		"myBody.removeChild(div);";

	private SeleniumEnums[] enums;

	public DhxDropDown(SeleniumEnums anEnum) {
		super(anEnum);
		enums = new SeleniumEnums[]{myEnum};
		dhxImage = AutomationEnum.TEMP_ONLY.setEnum(anEnum);
	}

	public DhxDropDown(SeleniumEnums anEnum, Integer replaceNumber) {
		super(anEnum, replaceNumber);
		enums = new SeleniumEnums[]{myEnum};
		dhxImage = AutomationEnum.TEMP_ONLY.setEnum(anEnum);
	}

	public DhxDropDown(SeleniumEnums anEnum, String replaceWord) {
		super(anEnum, replaceWord);
		page = replaceWord;
		enums = new SeleniumEnums[]{myEnum};
		dhxImage = AutomationEnum.TEMP_ONLY.setEnum(anEnum);
	}
	
	public DhxDropDown(SeleniumEnums anEnum, String replaceWord, SeleniumEnums ...enums) {
		super(anEnum, replaceWord);
		page = replaceWord;
		this.enums = enums;
		dhxImage = AutomationEnum.TEMP_ONLY.setEnum(anEnum);
	}

	public DhxDropDown(SeleniumEnums anEnum, String replaceWord,
			Integer replaceNumber) {
		super(anEnum, replaceWord, replaceNumber);
		page = replaceWord;
		enums = new SeleniumEnums[]{myEnum};
		dhxImage = AutomationEnum.TEMP_ONLY.setEnum(anEnum);
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
//		removeOldDivs(div--);
		selenium.click(dhxImage);
		myEnum.setID(myEnum.toString());
		return this;
	}

	public DhxDropDown dropDownButton(SeleniumEnums enume) {
		dhxImage = AutomationEnum.TEMP_ONLY.setEnum(enume).replaceWord(page);
		return this;
	}

	@Override
	public DhxDropDown select(Integer optionNumber) {
		assignIDs();
		selenium.selectDhx(myEnum, optionNumber.toString());
		return this;
	}

	public DhxDropDown select(SeleniumValueEnums option) {
		assignIDs();
		return select(option.getPosition() + 1);
	}

	@Override
	public DhxDropDown select(String fullMatch) {
		assignIDs();
		select(fullMatch, 1);
		return this;
	}

	@Override
	public DhxDropDown select(String fullMatch, Integer matchNumber) {
		assignIDs();
		matchNumber--;
		getMatches(makeXpath(Id.text(fullMatch)), matchNumber).setSelected();
		return this;
	}

	@Override
	public DhxDropDown selectPartMatch(String partialMatch) {
		assignIDs();
		return selectPartMatch(partialMatch, 1);
	}

	@Override
	public DhxDropDown selectPartMatch(String partialMatch,
			Integer matchNumber) {
		assignIDs();
		matchNumber--;
		getMatches(makeXpath(Id.contains(Id.text(), partialMatch)), matchNumber).setSelected();
		return this;
	}
	
	private String makeXpath(String secondDiv){
		String xpath = Xpath.start().div(Id.id(myEnum.getIDs()[0]))
		.div(secondDiv).toString();
		return xpath;
	}
	
	private DhxDropDown removeOldDivs(Integer divs){
		Integer newCount = webDriver.findElements(By.xpath("//body/div[@class='dhx_combo_list']")).size();
		Integer elementsToRemove = newCount - divs;
		logger.info(elementsToRemove);
		for (int i=0;i<elementsToRemove;i++){
			selenium.runScript(removeScript);
		}
		return this;
	}
}
