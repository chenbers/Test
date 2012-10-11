package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.ClickableTableBased;
import com.inthinc.pro.automation.elements.ElementInterface.ClickableTextBased;
import com.inthinc.pro.automation.elements.ElementInterface.TextTableBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.IndexEnum;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;
import com.thoughtworks.selenium.SeleniumException;

public class TextTableLink implements ClickableTableBased<ClickableTextBased>, TextTableBased<ClickableTextBased> {

    protected SeleniumEnumWrapper myEnum;
    private final TextTable textPart;

    public TextTableLink(SeleniumEnums anEnum, Object ...objects) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.makeReplacements(objects);
        textPart = new TextTable(myEnum);
    }

    public TextTableLink(SeleniumEnums anEnum, String page, TextEnum column) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceWord(page);
        myEnum.replaceOldWithNew("*column*", column.getText());
        textPart = new TextTable(myEnum);
    }

    public TableIterator<ClickableTextBased> iterator() {
        return new TableIterator<ClickableTextBased>(this);
    }

    @SuppressWarnings("unchecked")
	public TextLink row(int rowNumber) {
        return new TextLink(myEnum, rowNumber);
    }
    
    @SuppressWarnings("unchecked")
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
    

	@Override
    public boolean isAscending(){
        return textPart.isAscending();
    }

	@Override
    public boolean isDescending(){
        return textPart.isDescending();
    }

	@Override
    public boolean validateAscending(){
        return textPart.validateAscending();
    }

	@Override
    public boolean validateDescending(){
        return textPart.validateDescending();
    }


	@Override
    public boolean assertDescending(){
        return textPart.assertDescending();
    }


	@Override
	public boolean assertAscending() {
		return textPart.assertAscending();
	}

	@Override
	public boolean validateContains(String text) {
		return textPart.validateContains(text);
	}

	@Override
	public boolean assertContains(String text) {
		return textPart.assertContains(text);
	}

}
