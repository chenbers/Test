package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Selectable;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.SeleniumValueEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public class DHXDropDown extends DropDown implements Selectable {

    private SeleniumEnumWrapper makeDropDown;
    private String page;

    private SeleniumEnums[] enums;

    /**
     * Construct a DHXDropDown.
     * @param anEnum
     * NOTE: SeleniumEnums in <code>anEnum</code> must be in the order they are added to the page. 
     */
    public DHXDropDown(SeleniumEnums... anEnum) {
        super(anEnum[0], null, null);
        init(anEnum[0], null, anEnum);
    }

    /**
     * Construct a DHXDropDown.
     * @param anEnum
     * @param enums
     * NOTE: SeleniumEnums in <code>enums</code> must be in the order they are added to the page.
     */
    public DHXDropDown(SeleniumEnums anEnum, SeleniumEnums[] enums) {
        super(anEnum, null, null);
        init(anEnum, null, enums);
    }

    /**
     * Construct a DHXDropDown.
     * @param anEnum
     * @param replaceNumber
     */
    public DHXDropDown(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, null, replaceNumber);
        init(anEnum, null, null);
    }

    /**
     * Construct a DHXDropDown.
     * @param anEnum
     * @param replaceWord
     */
    public DHXDropDown(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord, null);
        init(anEnum, replaceWord, null);
    }

    /**
     * Construct a DHXDropDown.
     * @param anEnum
     * @param replaceWord
     * @param enums
     * NOTE: the SeleniumEnums in <code>enums</code> must be in the order they are added to the page.
     */
    public DHXDropDown(SeleniumEnums anEnum, String replaceWord, SeleniumEnums[] enums) {
        super(anEnum, replaceWord, null);
        init(anEnum, replaceWord, enums);
    }

    /**
     * Construct a DHXDropDown.
     * @param anEnum
     * @param replaceWord
     * @param replaceNumber
     */
    public DHXDropDown(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
        init(anEnum, replaceWord, null);
    }

    /**
     * Consolidates initialization of new DHXDropDown Objects into a single method.
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
     * Assigns slow/complicated xpaths for DHXDropDown elements on a page to ID's for easy retrieval.
     * NOTE: if <code>this</code> Object's enums are NOT in the same order that the DHXDropDown's appear on the rendered page they will not be assigned correctly.
     * @return this DHXDropDown (to allow method chaining)
     */
    private DHXDropDown assignIDs() {
        Integer div = 1;
        if (page == null) {
            page = "";
        }
        for (SeleniumEnums enume : enums) {
            if (getSelenium().isElementPresent(new SeleniumEnumWrapper(enume).replaceWord(page)) && enume != null) {
                Log.debug("//body/div[" + div + "]"+", "+enume.toString());
                getSelenium().assignId("//body/div[" + div + "]", enume.toString());
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
            return selectRow(((SeleniumValueEnums) value).getPosition() + 1);
        }
        return select(value.getText());
    }

    @Override
    public DHXDropDown selectRow(Integer optionNumber) {
        assignIDs();
        getSelenium().click(makeDropDown);
        getSelenium().selectDhx(myEnum, optionNumber.toString());
        return this;
    }

    public DHXDropDown select(SeleniumValueEnums option) {
        assignIDs();
        return selectRow(option.getPosition() + 1);
    }

    @Override
    public DHXDropDown select(String fullMatch) {
        selectThe(fullMatch, 1);
        return this;
    }

    @Override
    public DHXDropDown selectThe(String fullMatch, Integer matchNumber) {
        assignIDs();
        matchNumber--;
        String xpath = makeXpath(Id.text(fullMatch));
        getSelenium().click(makeDropDown);
        getSelenium().click(xpath, matchNumber);
        return this;
    }

    @Override
    public DHXDropDown selectTheOptionContaining(String partialMatch, Integer matchNumber) {
        assignIDs();
        matchNumber--;
        String xpath = makeXpath(Id.contains(Id.text(), partialMatch));
        getSelenium().click(makeDropDown);
        getSelenium().click(xpath, matchNumber);
        return this;
    }
    
    @Override
    public String getText(Integer optionNumber){
        assignIDs();
        return getSelenium().getDHXText(myEnum, optionNumber.toString());
    }

    private String makeXpath(String secondDiv) {
        String xpath = Xpath.start().div(Id.id(myEnum.getIDs()[0])).div(secondDiv).toString();
        return xpath;
    }
}
