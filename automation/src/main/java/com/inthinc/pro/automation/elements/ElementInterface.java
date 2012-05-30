package com.inthinc.pro.automation.elements;

import java.util.ArrayList;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.Assert;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.Validate;
import com.inthinc.pro.automation.interfaces.IndexEnum;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;


public interface ElementInterface {
    @Assert(englishName="present")
    public Boolean assertPresence(Boolean present);
    @Assert(englishName="visible")
    public Boolean assertVisibility(Boolean visibile);
    public ElementInterface focus();
    public SeleniumEnums getMyEnum();
    public Boolean hasFocus();
    public Boolean isPresent();
    public Boolean isVisible();
    public Boolean isEditable();
    public void setMyEnum(SeleniumEnums anEnum);
    public Boolean validateElementsPresent(ArrayList<SeleniumEnums> enums);
    public Boolean validateElementsPresent(Object ...enums);
    @Validate(englishName="present")
    public Boolean validatePresence(Boolean present);
    @Validate(englishName="visible")
    public Boolean validateVisibility(Boolean visible);
    public void waitForElement();
    public void waitForElement(int i);
    public String getAttribute(String attributeToGet);

    public interface Checkable extends Clickable {
        @Assert(englishName="checked")
        public Boolean assertChecked(Boolean checked);
    	public Checkable check();
    	public Boolean isChecked();
    	public ElementInterface uncheck();
        @Validate(englishName="checked")
    	public Boolean validateChecked(Boolean checked);
    }
    
    public interface ClickableTextBased extends Clickable, TextBased {}
    
    public interface Clickable extends ElementInterface {
        public Clickable click();
        public Boolean isClickable();
        @Validate(englishName="clickable")
        public Boolean validateClickable(Boolean clickable);
        @Assert(englishName="clickable")
        public Boolean assertClickable(Boolean clickable);
    }
    
    public interface NavigationTree extends ClickableTextBased {
        @Override 
        @Deprecated
        public Clickable click();
        public Clickable clickGroup(String group);
        public Clickable clickThe(String group, Integer match);
    }
    
    public interface Selectable extends ElementInterface{
        
	/**
         * Selects the n'th option in this Element's choices
         * @param optionNumber
         * @return this Element
         */
        public Selectable selectRow(Integer matchNumber);
        
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
        public Selectable selectThe(String fullMatch, Integer matchNumber);
        
        /**
         * Selects the <code>matchNumber</code>th partialMatch 
         * @param partialMatch
         * @param matchNumber
         * @return this Element
         */
        public Selectable selectTheOptionContaining(String partialMatch, Integer matchNumber);
        
        /**
         * Returns the <code>entryNumber</code> text
         * @param entryNumber
         * @return
         */
        public String getText(Integer optionNumber);
    }
    
    public interface TableBased<T> extends Iterable<T> {
        public T row(int rowNumber);
        public T row(IndexEnum indexByName);
    }
    
    public interface TextBased extends ElementInterface {
        /**
         * Asserts that the Element is the same as that provided by the Page Enum
         * @return
         */
        @Assert(englishName="equals the default")
        public Boolean assertEquals();
        
        /**
         * Asserts that the Element is the same as the provided text
         * @param compareAgainst
         * @return
         */
        @Assert(englishName="equal to")
        public Boolean assertEquals(String compareAgainst);
        
        /**
         * Asserts that the Element is not the same as the provided text
         * @param compareAgainst
         * @return
         */
        @Assert(englishName="not equal to")
        public Boolean assertNotEquals(String compareAgainst);
        
        /**
         * Asserts that the Element contains the provided text
         * @return
         */
        @Assert(englishName="contains")
        public Boolean assertContains(String compareAgainst);
        
        /**
         * Asserts that the Element does not contain the provided text
         * @param compareAgainst
         * @return
         */
        @Assert(englishName="does not contain")
        public Boolean assertDoesNotContain(String compareAgainst);
        
        /**
         * Compares the value of this Element to the expected value stored in the Elements enum.
         * @return this Element
         */
        public Boolean compareDefault();
        
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
        
        /** 
         * Validates the Element against the default value provided in the Page Enum object
         * @return
         */
        @Validate(englishName="the default value")
        public Boolean validate();
        
        /**
         * Validates the Element equals the provided text
         * @param expected
         * @return
         */
        @Validate(englishName="")
        public Boolean validate(String expected);
        
        /** 
         * Validates the Element is not equal to the provided text
         * @param expected
         * @return
         */
        @Validate(englishName="")
        public Boolean validateIsNot(String expected);
        
        /**
         * Validates the Element is equal to the value provided in the TextEnum
         * @param expected
         * @return
         */
        public Boolean validate(TextEnum expected);
        
        /**
         * Validates the Element is equal to the value provided in the TextEnum with a value<br />
         * that is to be specified at run time.
         * @param expected
         * @param replaceOld
         * @param withNew
         * @return
         */
        public Boolean validate(TextEnum expected, String replaceOld, String withNew);
        
        /**
         * Validates that the Element contains the provided text
         * @param expectedPart
         * @return
         */
        @Validate(englishName="contains")
        public Boolean validateContains(String expectedPart);
        
        /**
         * Validates that the Element does not contain the provided text
         * @param expectedPart
         * @return
         */
        @Validate(englishName="does not contain")
        public Boolean validateDoesNotContain(String expectedPart);
    }
    
    public interface TextFieldWithSuggestions extends Typeable {
        public ClickableTextBased getSuggestedRow(Integer row);
        public ClickableTextBased getSuggestedText(String fullName);
    }
    
    public interface Typeable extends TextBased {
        public Typeable type(Object inputText);
        public Typeable clear();
    }

    public interface URLBased extends ElementInterface {
//        public File getBinaryFile();//? not sure about the best java object to use here
        // what do we want to do with binary files?
        // compare with existing binary files?
        // extract data? for comparison to input values?
        public String getURL();
    }

}
