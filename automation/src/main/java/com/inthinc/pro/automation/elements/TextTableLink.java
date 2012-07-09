package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.Assert;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.Validate;
import com.inthinc.pro.automation.elements.ElementInterface.ClickableTextBased;
import com.inthinc.pro.automation.elements.ElementInterface.TableBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.IndexEnum;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;
import com.thoughtworks.selenium.SeleniumException;

public class TextTableLink implements TableBased<ClickableTextBased> {

    protected SeleniumEnumWrapper myEnum;

    public TextTableLink(SeleniumEnums anEnum, Object ...objects) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.makeReplacements(objects);
    }

    public TextTableLink(SeleniumEnums anEnum, String page, TextEnum column) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceWord(page);
        myEnum.replaceOldWithNew("*column*", column.getText());
    }

    @Override
    public TableIterator<ClickableTextBased> iterator() {
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
        TableIterator<ClickableTextBased> iterator = this.iterator();
        while(iterator.hasNext() && (link == null || !link.isClickable())){ 
            link = (ClickableText) iterator.next();
        }
        if(link!=null && link.isClickable())
            return link;
        throw new SeleniumException("No ClickableText could be found.");
    }
    
    public ClickableObject clickTheFirstClickable(){
        return getFirstClickableLink().click();
    }

    public String getTheTextFromTheFirst(){
        return getFirstClickableLink().getText();
    }
    
    public boolean isEmpty(){
        return this.iterator().hasNext();
    }
    
    public ClickableText getLinkMatching(String matchText) {
        ClickableText link = null;
        TableIterator<ClickableTextBased> iterator = this.iterator();
        boolean matches = false;
        while(iterator.hasNext() && (!matches)){ 
            link = (ClickableText) iterator.next();
            matches = link.getText().equals(matchText);
        }
        if(link != null && matches)
            return link;
        throw new SeleniumException("No link with text matching '"+matchText+"' could be found");   
    }
    
    public ClickableObject clickTheFirstLinkMatching(String matchText){
        return getLinkMatching(matchText).click();
    }
    
    public String getTheTextFromTheLinkMatching(String matchText){
        return getLinkMatching(matchText).getText();
    }
    

    public boolean isAscending(){
        return new TextTable(myEnum).isAscending();
    }
    
    public boolean isDescending(){
        return new TextTable(myEnum).isDescending();
    }
    
    @Validate(englishName = "sorted in ascending order")
    public boolean validateAscending(){
        return new TextTable(myEnum).validateAscending();
    }
    
    @Validate(englishName = "sorted in descending order")
    public boolean validateDescending(){
        return new TextTable(myEnum).validateDescending();
    }
    
    @Assert(englishName = "sorted in ascending order")
    public boolean assetAscending(){
        return new TextTable(myEnum).assertAscending();
    }
    
    @Assert(englishName = "sorted in descending order")
    public boolean assertDescending(){
        return new TextTable(myEnum).assertDescending();
    }
    

}
