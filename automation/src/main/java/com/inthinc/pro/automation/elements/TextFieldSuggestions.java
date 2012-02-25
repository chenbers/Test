package com.inthinc.pro.automation.elements;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.automation.elements.ElementInterface.TextFieldWithSuggestions;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;

public class TextFieldSuggestions extends TextField implements TextFieldWithSuggestions {

    private SeleniumEnums suggestionBox;

    public TextFieldSuggestions(SeleniumEnums anEnum, SeleniumEnums suggestionBox, Object ...objects) {
        super(anEnum, objects);
        this.suggestionBox = new SeleniumEnumWrapper(suggestionBox);
    }

    @Override
    public TextFieldSuggestions type(Object toType) {
        getSelenium().type(myEnum, "");
        getSelenium().typeKeys(myEnum, toType.toString());
        return this;
    }

    @Override
    public TextLink getSuggestion(Integer row) {
        return new TextLink(setIds("["+row+"]/td"));
    }
    
    private SeleniumEnumWrapper setIds(String qualifier){
        SeleniumEnumWrapper temp = new SeleniumEnumWrapper(suggestionBox);
        List<String> newIds = new ArrayList<String>();
        String downToTr = "/tbody/tr" + qualifier;
        for (int i = 0; i < temp.getIDs().length; i++) {
            String newId = "";
            String id = temp.getIDs()[i];
            if (id.startsWith("//")) {
                newId = id + downToTr;
            } else if (!id.contains("=")) {
                newId = "//table[@id='" + id + "']" + downToTr;
            }
            if (!newId.contains("/span")){
                newIds.add(newId + "/span");
            }
            newIds.add(newId);
        }
        temp.setID(newIds.toArray(new String[]{}));
        return temp;
    }

    @Override
    public TextLink getSuggestion(String fullName) {
        return new TextLink(setIds("/td[2]/span["+Id.text(fullName)+"]"));
    }

}
