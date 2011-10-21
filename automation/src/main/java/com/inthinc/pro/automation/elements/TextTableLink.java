package com.inthinc.pro.automation.elements;

import java.util.Iterator;

import com.inthinc.pro.automation.elements.ElementInterface.ClickableTextBased;
import com.inthinc.pro.automation.elements.ElementInterface.TableBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.IndexEnum;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;
import com.thoughtworks.selenium.SeleniumException;

public class TextTableLink implements TableBased<ClickableTextBased> {

    protected SeleniumEnumWrapper myEnum;

    public TextTableLink(SeleniumEnums anEnum, String replaceWord,
            Integer replaceNumber) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceNumber(replaceNumber);
        myEnum.replaceWord(replaceWord);
    }

    public TextTableLink(SeleniumEnums anEnum) {
        myEnum = new SeleniumEnumWrapper(anEnum);
    }

    public TextTableLink(SeleniumEnums anEnum, Integer replaceNumber) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceNumber(replaceNumber);
    }

    public TextTableLink(SeleniumEnums anEnum, String replaceWord) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceWord(replaceWord);
    }

    public TextTableLink(SeleniumEnums anEnum, TextEnum replaceWord) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceWord(replaceWord.getText());
    }

    public TextTableLink(SeleniumEnums anEnum, String page, TextEnum column) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceWord(page);
        myEnum.replaceOldWithNew("*column*", column.getText());
    }

    @Override
    public Iterator<ClickableTextBased> iterator() {
        return new TableIterator<ClickableTextBased>(this);
    }

    @Override
    public TextLink row(int rowNumber) {
        return new TextLink(myEnum, rowNumber);
    }
    
    @Override
    public ClickableTextBased row(IndexEnum indexByName) {
        return row(indexByName.getIndex());
    }
    
    public ClickableText getFirstClickableLink(){
        ClickableText link = null;
        Iterator<ClickableTextBased> iterator = this.iterator();
        while(iterator.hasNext() && (link == null || !link.isClickable())){ 
            link = (ClickableText) iterator.next();
        }
        if(link!=null && link.isClickable())
            return link;
        throw new SeleniumException("No ClickableText could be found.");
    }
    
    public boolean isEmpty(){
        return this.iterator().hasNext();
    }
    
    public ClickableText getLinkMatching(String matchText) {
        ClickableText link = null;
        Iterator<ClickableTextBased> iterator = this.iterator();
        boolean matches = false;
        while(iterator.hasNext() && (!matches)){ 
            link = (ClickableText) iterator.next();
            matches = link.getText().equals(matchText);
        }
        if(link != null && matches)
            return link;
        throw new SeleniumException("No link with text matching '"+matchText+"' could be found");   
    }

}
