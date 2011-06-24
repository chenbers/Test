package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Selectable;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.SeleniumValueEnums;
import com.inthinc.pro.automation.enums.TextEnum;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public class DhxDropDown extends DropDown implements Selectable {
	private SeleniumEnumWrapper makeDropDown;
	private String page;
	private final int pause = 3;
	

	private SeleniumEnums[] enums;

	public DhxDropDown(SeleniumEnums anEnum) {
		super(anEnum);
		enums = new SeleniumEnums[]{myEnum};
		makeDropDown = new SeleniumEnumWrapper(anEnum);
	}
	
	public DhxDropDown(SeleniumEnums anEnum, Integer replaceNumber) {
		super(anEnum, replaceNumber);
		enums = new SeleniumEnums[]{myEnum};
		makeDropDown = new SeleniumEnumWrapper(anEnum);
	}

	public DhxDropDown(SeleniumEnums anEnum, String replaceWord) {
		super(anEnum, replaceWord);
		page = replaceWord;
		enums = new SeleniumEnums[]{myEnum};
		makeDropDown = new SeleniumEnumWrapper(anEnum);
	}
	
	public DhxDropDown(SeleniumEnums anEnum, String replaceWord, SeleniumEnums ...enums) {
		super(anEnum, replaceWord);
		page = replaceWord;
		this.enums = enums;
		makeDropDown = new SeleniumEnumWrapper(anEnum);
	}

	public DhxDropDown(SeleniumEnums anEnum, String replaceWord,
			Integer replaceNumber) {
		super(anEnum, replaceWord, replaceNumber);
		page = replaceWord;
		enums = new SeleniumEnums[]{myEnum};
		makeDropDown = new SeleniumEnumWrapper(anEnum);
	}

	private DhxDropDown assignIDs() {
		Integer div = 1;
		for (SeleniumEnums enume : enums) {
			if (selenium.isElementPresent(new SeleniumEnumWrapper(enume).replaceWord(page))
					&& enume != null) {
				selenium.assignId("//body/div[" + div + "]", enume.toString());
				div++;
			}
		}
		selenium.click(makeDropDown.setID(makeDropDown.getIDs()[0]+"/../img"));
		myEnum.setID(myEnum.toString());
		return this;
	}

	@Override
	public SelectableObject select(TextEnum value){
    	if (value instanceof SeleniumValueEnums){
    		return select(((SeleniumValueEnums) value).getPosition()+1);
    	}
    	return select(value.getText());
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
		select(fullMatch, 1);
		return this;
	}
	
	

	@Override
	public DhxDropDown select(String fullMatch, Integer matchNumber) {
		assignIDs();
		matchNumber--;
		String xpath =makeXpath(Id.text(fullMatch));
		getMatches(xpath, matchNumber).click();
		selenium.pause(pause, "Wait for elements to refresh");
		return this;
	}

	@Override
	public DhxDropDown selectPartMatch(String partialMatch) {
		return selectPartMatch(partialMatch, 1);
	}

	@Override
	public DhxDropDown selectPartMatch(String partialMatch,
			Integer matchNumber) {
		assignIDs();
		matchNumber--;
		String xpath =makeXpath(Id.contains( Id.text(), partialMatch));
		getMatches(xpath, matchNumber).click();
		selenium.pause(pause, "Wait for elements to refresh");
		return this;
	}
	
	private String makeXpath(String secondDiv){
		String xpath = Xpath.start().div(Id.id(myEnum.getIDs()[0]))
		.div(secondDiv).toString();
		return xpath;
	}
}
