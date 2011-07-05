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

    /**
     * Construct a DhxDropDown.
     * @param anEnum
     * NOTE: SeleniumEnums in <code>anEnum</code> must be in the order they are added to the page. 
     */
    public DhxDropDown(SeleniumEnums... anEnum) {
        super(anEnum[0], null, null);
        init(anEnum[0], null, anEnum);
    }

    /**
     * Construct a DhxDropDown.
     * @param anEnum
     * @param enums
     * NOTE: SeleniumEnums in <code>enums</code> must be in the order they are added to the page.
     */
    public DhxDropDown(SeleniumEnums anEnum, SeleniumEnums[] enums) {
        super(anEnum, null, null);
        init(anEnum, null, enums);
    }

    /**
     * Construct a DhxDropDown.
     * @param anEnum
     * @param replaceNumber
     */
    public DhxDropDown(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, null, replaceNumber);
        init(anEnum, null, null);
    }

    /**
     * Construct a DhxDropDown.
     * @param anEnum
     * @param replaceWord
     */
    public DhxDropDown(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord, null);
        init(anEnum, replaceWord, null);
    }

    /**
     * Construct a DhxDropDown.
     * @param anEnum
     * @param replaceWord
     * @param enums
     * NOTE: the SeleniumEnums in <code>enums</code> must be in the order they are added to the page.
     */
    public DhxDropDown(SeleniumEnums anEnum, String replaceWord, SeleniumEnums[] enums) {
        super(anEnum, replaceWord, null);
        init(anEnum, replaceWord, enums);
    }

    /**
     * Construct a DhxDropDown.
     * @param anEnum
     * @param replaceWord
     * @param replaceNumber
     */
    public DhxDropDown(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
        init(anEnum, replaceWord, null);
    }

    /**
     * Consolidates initialization of new DhxDropDown Objects into a single method.
     * @param anEnum
     * @param replaceWord
     * @param enums
     * NOTE: enums in <code>enums</code> must be in the order they are added to the page.
     */
    private void init(SeleniumEnums anEnum, String replaceWord, SeleniumEnums[] enums) {
        page = replaceWord;
        if (enums == null) {
            this.enums = new SeleniumEnums[] { myEnum };
        } else {
            this.enums = enums;
        }
        makeDropDown = new SeleniumEnumWrapper(anEnum);
    }

    /**
     * Assigns slow/complicated xpaths for DhxDropDown elements on a page to ID's for easy retrieval.
     * NOTE: if <code>this</code> Object's enums are NOT in the same order that the DhxDropDown's appear on the rendered page they will not be assigned correctly.
     * @return this DhxDropDown (to allow method chaining)
     */
    private DhxDropDown assignIDs() {
        Integer div = 1;
        if (page == null) {
            page = "";
        }
        for (SeleniumEnums enume : enums) {
            if (selenium.isElementPresent(new SeleniumEnumWrapper(enume).replaceWord(page)) && enume != null) {
                selenium.assignId("//body/div[" + div + "]", enume.toString());
                div++;
            }
        }
        makeDropDown.setID("//input[@name='" + makeDropDown.replaceWord(page).getIDs()[0] + "']/../img");
        myEnum.setID(myEnum.toString());
        return this;
    }

    @Override
    public SelectableObject select(TextEnum value) {
        if (value instanceof SeleniumValueEnums) {
            return select(((SeleniumValueEnums) value).getPosition() + 1);
        }
        return select(value.getText());
    }

    @Override
    public DhxDropDown select(Integer optionNumber) {
        assignIDs();
        selenium.click(makeDropDown);
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
        String xpath = makeXpath(Id.text(fullMatch));
        selenium.click(makeDropDown);
        getMatches(xpath, matchNumber).click();
        selenium.pause(pause, "Wait for elements to refresh");
        return this;
    }

    @Override
    public DhxDropDown selectPartMatch(String partialMatch) {
        return selectPartMatch(partialMatch, 1);
    }

    @Override
    public DhxDropDown selectPartMatch(String partialMatch, Integer matchNumber) {
        assignIDs();
        matchNumber--;
        String xpath = makeXpath(Id.contains(Id.text(), partialMatch));
        selenium.click(makeDropDown);
        getMatches(xpath, matchNumber).click();
        selenium.pause(pause, "Wait for elements to refresh");
        return this;
    }

    private String makeXpath(String secondDiv) {
        String xpath = Xpath.start().div(Id.id(myEnum.getIDs()[0])).div(secondDiv).toString();
        return xpath;
    }
}
