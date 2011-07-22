package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextFieldWithSuggestions;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;
import com.inthinc.pro.automation.utils.Id;

public class TextFieldSuggestions extends TextField implements TextFieldWithSuggestions {

    private SeleniumEnums suggestionBox;

    public TextFieldSuggestions(SeleniumEnums anEnum, SeleniumEnums suggestionBox) {
        super(anEnum);
        this.suggestionBox = new SeleniumEnumWrapper(suggestionBox);
    }

    public TextFieldSuggestions(SeleniumEnums anEnum, SeleniumEnums suggestionBox, TextEnum replacement) {
        super(anEnum, replacement);
        this.suggestionBox = new SeleniumEnumWrapper(suggestionBox);
    }

    public TextFieldSuggestions(SeleniumEnums anEnum, SeleniumEnums suggestionBox, Integer replaceNumber) {
        super(anEnum, replaceNumber);
        this.suggestionBox = new SeleniumEnumWrapper(suggestionBox);
    }

    public TextFieldSuggestions(SeleniumEnums anEnum, SeleniumEnums suggestionBox, String replaceWord) {
        super(anEnum, replaceWord);
        this.suggestionBox = new SeleniumEnumWrapper(suggestionBox);
    }

    public TextFieldSuggestions(SeleniumEnums anEnum, SeleniumEnums suggestionBox, String prefix, TextEnum replacement) {
        super(anEnum, prefix, replacement);
        this.suggestionBox = new SeleniumEnumWrapper(suggestionBox);
    }

    @Override
    public TextFieldSuggestions type(String toType) {
        selenium.type(myEnum, "");
        selenium.typeKeys(myEnum, toType);
        return this;
    }

    @Override
    public TextLink getSuggestion(Integer row) {
        return new TextLink(setIds("["+row+"]/td/span"));
    }
    
    private SeleniumEnumWrapper setIds(String qualifier){
        SeleniumEnumWrapper temp = new SeleniumEnumWrapper(suggestionBox);
        String[] newIds = new String[temp.getIDs().length];
        String downToTr = "/tbody/tr" + qualifier;
        for (int i = 0; i < temp.getIDs().length; i++) {
            String newId = "";
            String id = temp.getIDs()[i];
            if (id.startsWith("//")) {
                newId = id + downToTr;
            } else if (!id.contains("=")) {
                newId = "//table[@id='" + id + "']" + downToTr;
            }
            newIds[i] = newId;
        }
        temp.setID(newIds);
        return temp;
    }

    @Override
    public TextLink getSuggestion(String fullName) {
        return new TextLink(setIds("/td[2]/span["+Id.text(fullName)+"]"));
    }

}
