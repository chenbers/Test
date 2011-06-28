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

    public interface Checkable extends ElementInterface {
	public Boolean assertChecked(Boolean checked);
    	public ElementInterface check();
    	public Boolean isChecked();
    	public ElementInterface uncheck();
    	public Boolean validateChecked(Boolean checked);
    }
    
    public interface CheckableTable extends ElementInterface {
    	public Boolean assertChecked(Integer number, Boolean checked);
    	public Boolean assertPresence(Integer number,Boolean present);
    	public Boolean assertVisibility(Integer number,Boolean visibile);
    	public ElementInterface check(Integer number);
    	public ElementInterface click(Integer number);
    	public ElementInterface focus(Integer number);
    	
        public Boolean hasFocus(Integer number);
        public Boolean isChecked(Integer number);
        public Boolean isPresent(Integer number);
        public Boolean isVisible(Integer number);
        public ElementInterface uncheck(Integer number);
        public Boolean validateChecked(Integer number, Boolean checked);
        public Boolean validatePresence(Integer number,Boolean present);
        public Boolean validateVisibility(Integer number,Boolean visible);
    }
    
    public interface ClickableTableBased extends TableBased {
	public ClickableTableBased click(Integer row);
        public Boolean isClickable(Integer row);
        public Boolean validateClickable(Integer row, Boolean clickable);
        public Boolean assertClickable(Integer row, Boolean clickable);
        

        public Clickable click();
        public Boolean isClickable();
        public Boolean validateClickable(Boolean clickable);
        public Boolean assertClickable(Boolean clickable);
    }
    
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
        public ElementInterface select(Integer matchNumber);
        
        /**
         * Selects the FIRST fullMatch in this Element.
         * @param fullMatch
         * @return this Element
         */
        public ElementInterface select(String fullMatch);
        
        /**
         * Selects the <code>matchNumber</code>th fullMatch 
         * @param fullMatch
         * @param matchNumber
         * @return this Element
         */
        public ElementInterface select(String fullMatch, Integer matchNumber);
        
        /**
         * Selects the FIRST partialMatch
         * @param partialMatch
         * @return this Element
         */
        public ElementInterface selectPartMatch(String partialMatch);
        
        /**
         * Selects the <code>matchNumber</code>th partialMatch 
         * @param partialMatch
         * @param matchNumber
         * @return this Element
         */
        public ElementInterface selectPartMatch(String partialMatch, Integer matchNumber);
        
    }
    public interface TableBased extends ElementInterface {
        public Boolean assertPresence(Integer row, Boolean present);

    	public Boolean assertVisibility(Integer row, Boolean visible);
        public ElementInterface focus(Integer row);
        public Boolean isPresent(Integer row);

        public Boolean isVisible(Integer row);
        public Boolean validatePresence(Integer row, Boolean present);
        public Boolean validateVisibility(Integer row, Boolean visible);
        
        
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
    public interface TextTableBased extends TableBased{
    	public Boolean assertEquals(Integer row);
        public Boolean assertEquals(Integer row, String compareAgainst);
        public Boolean assertNotEquals(Integer row, String compareAgainst);
        
        /**
         * Compares the value of this Element to the expected value stored in the Elements enum.
         * @return this Element
         */
        public Boolean compare(Integer row);
        
        /**
         * Compares the value of this Element to the expected value provided as a parameter.
         * @param expected
         * @return this Element
         */
        public Boolean compare(Integer row, String expected);
        
        /**
         * @return the actual (in browser at test runtime) value of this Element.
         */
        public String getText(Integer row);
        public Boolean validate(Integer row);
        public Boolean validate(Integer row, String expected);
        public Boolean validate(Integer row, TextEnum expected);
        public Boolean validate(Integer row, TextEnum expected, String replaceOld, String withNew);
        public Boolean validateContains(Integer row, String expectedPart);
    }
    public interface Typeable extends TextBased {
        public ElementInterface type(String inputText);
    }

    public interface URLBased extends ElementInterface {
        public File getBinaryFile();//? not sure about the best java object to use here
        // what do we want to do with binary files?
        // compare with existing binary files?
        // extract data? for comparison to input values?
        public String getURL();
        
    }

}
