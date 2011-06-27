package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.enums.TextEnum;
import com.inthinc.pro.model.File;


public interface ElementInterface {
    public Boolean isVisible();
    public Boolean validateVisibility(Boolean visible);
    public Boolean assertVisibility(Boolean visibile);
    public Boolean isPresent();
    public Boolean validatePresence(Boolean present);
    public Boolean assertPresence(Boolean present);
    public ElementInterface focus();
    public Boolean hasFocus();
    
    public interface TableBased extends ElementInterface {
        public ElementInterface focus(Integer row);

    	public Boolean isVisible(Integer row);
        public Boolean validateVisibility(Integer row, Boolean visible);
        public Boolean assertVisibility(Integer row, Boolean visible);

        public Boolean isPresent(Integer row);
        public Boolean validatePresence(Integer row, Boolean present);
        public Boolean assertPresence(Integer row, Boolean present);
        
    }
    
    public interface TextTableBased extends TableBased{
    	/**
         * @return the actual (in browser at test runtime) value of this Element.
         */
        public String getText(Integer row);
        /**
         * Compares the value of this Element to the expected value provided as a parameter.
         * @param expected
         * @return this Element
         */
        public Boolean compare(Integer row, String expected);
        /**
         * Compares the value of this Element to the expected value stored in the Elements enum.
         * @return this Element
         */
        public Boolean compare(Integer row);
        
        public Boolean assertEquals(Integer row, String compareAgainst);
        
        public Boolean validateContains(Integer row, String expectedPart);
        
        public Boolean validate(Integer row, String expected);
        
        public Boolean validate(Integer row, TextEnum expected);
        
        public Boolean validate(Integer row, TextEnum expected, String replaceOld, String withNew);
        
        public Boolean validate(Integer row);

        public Boolean assertNotEquals(Integer row, String compareAgainst);
    }
    
    public interface TextBased extends ElementInterface {
        /**
         * @return the actual (in browser at test runtime) value of this Element.
         */
        public String getText();
        /**
         * Compares the value of this Element to the expected value provided as a parameter.
         * @param expected
         * @return this Element
         */
        public Boolean compare(String expected);
        /**
         * Compares the value of this Element to the expected value stored in the Elements enum.
         * @return this Element
         */
        public Boolean compare();
        
        public Boolean assertEquals(String compareAgainst);
        
        public Boolean validateContains(String expectedPart);
        
        public Boolean validate(String expected);
        
        public Boolean validate(TextEnum expected);
        
        public Boolean validate(TextEnum expected, String replaceOld, String withNew);
        
        public Boolean validate();
        
        public Boolean assertNotEquals(String compareAgainst);
        
    }
    
    public interface URLBased extends ElementInterface {
        public String getURL();
        public File getBinaryFile();//? not sure about the best java object to use here
        // what do we want to do with binary files?
        // compare with existing binary files?
        // extract data? for comparison to input values?
        
    }
    public interface Typeable extends TextBased {
        public ElementInterface type(String inputText);
    }
    
    public interface Checkable extends ElementInterface {
    	public ElementInterface check(Integer number);
    	public ElementInterface uncheck(Integer number);
    	public Boolean isChecked(Integer number);
    	public Boolean validateChecked(Integer number, Boolean checked);
    	public Boolean assertChecked(Integer number, Boolean checked);
    	public ElementInterface click(Integer number);
    	
        public Boolean isVisible(Integer number);
        public Boolean validateVisibility(Integer number,Boolean visible);
        public Boolean assertVisibility(Integer number,Boolean visibile);
        public Boolean isPresent(Integer number);
        public Boolean validatePresence(Integer number,Boolean present);
        public Boolean assertPresence(Integer number,Boolean present);
        public ElementInterface focus(Integer number);
        public Boolean hasFocus(Integer number);
    }
    
    public interface Clickable extends ElementInterface {
        public ElementInterface click();
    }
    

    public interface Selectable extends ElementInterface {
        /**
         * Selects the FIRST fullMatch in this Element.
         * @param fullMatch
         * @return this Element
         */
        public ElementInterface select(String fullMatch);
        /**
         * Selects the optionNumber'th option in this Element's choices
         * @param optionNumber
         * @return this Element
         */
        public ElementInterface select(Integer optionNumber);
        /**
         * Selects the <code>matchNumber</code>th fullMatch 
         * @param fullMatch
         * @param matchNumber
         * @return this Element
         */
        public ElementInterface select(String fullMatch, Integer matchNumber);
        /**
         * Selects the <code>matchNumber</code>th partialMatch 
         * @param partialMatch
         * @param matchNumber
         * @return this Element
         */
        public ElementInterface selectPartMatch(String partialMatch, Integer matchNumber);
        /**
         * Selects the FIRST partialMatch
         * @param partialMatch
         * @return this Element
         */
        public ElementInterface selectPartMatch(String partialMatch);
        
    }
}
