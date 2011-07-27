package com.inthinc.pro.automation.elements;

import java.util.ArrayList;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;
import com.inthinc.pro.model.File;


public interface ElementInterface {
    public Boolean assertPresence(Boolean present);
    public Boolean assertVisibility(Boolean visibile);
    public ElementInterface focus();
    public SeleniumEnums getMyEnum();
    public Boolean hasFocus();
    public Boolean isPresent();
    public Boolean isVisible();
    public void setMyEnum(SeleniumEnums anEnum);
    public Boolean validateElementsPresent(ArrayList<SeleniumEnums> enums);
    public Boolean validateElementsPresent(Object ...enums);
    public Boolean validatePresence(Boolean present);
    public Boolean validateVisibility(Boolean visible);
    public void waitForElement();
    public void waitForElement(int i);

    public interface Checkable extends Clickable {
        public Boolean assertChecked(Boolean checked);
    	public Checkable check();
    	public Boolean isChecked();
    	public ElementInterface uncheck();
    	public Boolean validateChecked(Boolean checked);
    }
    
    public interface CheckableTable extends Iterable<Checkable>, TableBased<Checkable>  {
    	public Checkable row(int rowNumber);
    }
    
    public interface ClickableTableBased extends Iterable<Clickable>, TableBased<Clickable>  {
        public Clickable row(int rowNumber);
    }
    
    public interface ClickableTextTableBased extends Iterable<ClickableTextBased>, TableBased<ClickableTextBased>{
        public TextLink row(int rowNumber);
    }
    
    public interface TableBasedWithText extends TableBased<TextBased> {
        public TextBased row(int rowNumber);
    }
    
    public interface ClickableTextBased extends Clickable, TextBased {}
    
    public interface Clickable extends ElementInterface {
        public Clickable click();
        public Boolean isClickable();
        public Boolean validateClickable(Boolean clickable);
        public Boolean assertClickable(Boolean clickable);
    }
    
    public interface Selectable extends ElementInterface {
        
	/**
         * Selects the optionNumber'th option in this Element's choices
         * @param optionNumber
         * @return this Element
         */
        public Selectable select(Integer matchNumber);
        
        /**
         * Selects the FIRST fullMatch in this Element.
         * @param fullMatch
         * @return this Element
         */
        public Selectable select(String fullMatch);
        
        /**
         * Selects the <code>matchNumber</code>th fullMatch 
         * @param fullMatch
         * @param matchNumber
         * @return this Element
         */
        public Selectable select(String fullMatch, Integer matchNumber);
        
        /**
         * Selects the FIRST partialMatch
         * @param partialMatch
         * @return this Element
         */
        public Selectable selectPartMatch(String partialMatch);
        
        /**
         * Selects the <code>matchNumber</code>th partialMatch 
         * @param partialMatch
         * @param matchNumber
         * @return this Element
         */
        public Selectable selectPartMatch(String partialMatch, Integer matchNumber);
        
        /**
         * Returns the <code>entryNumber</code> text
         * @param entryNumber
         * @return
         */
        public String getText(Integer optionNumber);
    }
    public interface TableBased<T> {
        public T row(int rowNumber);
    }
    public interface TextBased extends ElementInterface {
        public Boolean assertEquals();
        public Boolean assertEquals(String compareAgainst);
        public Boolean assertNotEquals(String compareAgainst);
        
        /**
         * Compares the value of this Element to the expected value stored in the Elements enum.
         * @return this Element
         */
        public Boolean compare();
        
        /**
         * Compares the value of this Element to the expected value provided as a parameter.
         * @param expected
         * @return this Element
         */
        public Boolean compare(String expected);
        
        /**
         * @return the actual (in browser at test runtime) value of this Element.
         */
        public String getText();
        public Boolean validate();
        public Boolean validate(String expected);
        public Boolean validate(TextEnum expected);
        public Boolean validate(TextEnum expected, String replaceOld, String withNew);
        public Boolean validateContains(String expectedPart);
        
    }
    
    public interface TextFieldWithSuggestions extends TextBased {
	public TextLink getSuggestion(Integer row);
	public TextLink getSuggestion(String fullName);
    }
    
    public interface TextTableBased extends Iterable<TextBased>, TableBasedWithText  {
        public TextBased row(int rowNumber);
    }
    public interface Typeable extends TextBased {
        public Typeable type(Object inputText);
        public TextField clear();
    }

    public interface URLBased extends ElementInterface {
        public File getBinaryFile();//? not sure about the best java object to use here
        // what do we want to do with binary files?
        // compare with existing binary files?
        // extract data? for comparison to input values?
        public String getURL();
        
    }

}
