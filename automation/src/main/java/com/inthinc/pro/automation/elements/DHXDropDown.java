package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Selectable;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.SeleniumValueEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.utils.AutomationThread;
import com.inthinc.pro.automation.utils.Id;

public class DHXDropDown extends DropDown implements Selectable {

    private SeleniumEnumWrapper makeDropDown;
    private String page;
    

    private String xpath = "//body/div[@id='***']/div[###]";

    private SeleniumEnums[] enums;

    /**
     * Construct a DHXDropDown.
     * @param anEnum
     * NOTE: SeleniumEnums in <code>anEnum</code> must be in the order they are added to the page. 
     */
    public DHXDropDown(SeleniumEnums... anEnum) {
        super(anEnum[0]);
        init(anEnum[0], null, anEnum);
    }

    /**
     * Construct a DHXDropDown.
     * @param anEnum
     * @param enums
     * NOTE: SeleniumEnums in <code>enums</code> must be in the order they are added to the page.
     */
    public DHXDropDown(SeleniumEnums anEnum, SeleniumEnums[] enums) {
        super(anEnum);
        init(anEnum, null, enums);
    }

    /**
     * Construct a DHXDropDown.
     * @param anEnum
     * @param replaceNumber
     */
    public DHXDropDown(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
        init(anEnum, null, null);
    }

    /**
     * Construct a DHXDropDown.
     * @param anEnum
     * @param replaceWord
     */
    public DHXDropDown(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
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
        super(anEnum, replaceWord);
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
        xpath = xpath.replace("***", page);
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
        setDropDownID();
        return this;
    }
    
    private void setDropDownID(){
    	makeDropDown.setID("//input[@name='" + makeDropDown.replaceWord(page).getIDs()[0] + "']/../img");
    }

    @Override
    public SelectableObject selectValue(TextEnum value) {
        if (value instanceof SeleniumValueEnums) {
            return selectRow(((SeleniumValueEnums) value).getPosition() + 1);
        }
        return select(value.getText());
    }

    @Override
    public DHXDropDown selectRow(Integer optionNumber) {
        assignIDs();
        getSelenium().click(makeDropDown);
        myEnum.setID(xpath);
        myEnum.replaceNumber(optionNumber).replaceWord(myEnum.toString());
        getSelenium().click(myEnum);
        AutomationThread.pause(3, "Pause so the browser has a chance to catch up");
        return this;
    }
    
    
    
    public DHXDropDown selectPosition(SeleniumValueEnums option) {
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
        myEnum.setID(xpath);
        myEnum.replaceNumber(optionNumber).replaceWord(myEnum.toString());
        return getSelenium().getText(myEnum);
    }

    private String makeXpath(String secondDiv) {
        String xpath = "//div["+Id.id(myEnum.toString())+"]/div["+secondDiv+"]";
        return xpath;
    }
    
    
    @Override
    public Boolean isPresent(){
    	setDropDownID();
        return getSelenium().isElementPresent(makeDropDown);
    }
    
    @Override
    public Boolean isVisible(){
    	setDropDownID();
        return getSelenium().isVisible(makeDropDown);
    }
    
}
