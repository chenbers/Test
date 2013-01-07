package com.inthinc.pro.automation.elements;

import java.util.ArrayList;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.Assert;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.Validate;
import com.inthinc.pro.automation.interfaces.IndexEnum;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;
import com.inthinc.pro.automation.objects.AutomationCalendar;


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
    public ElementInterface hover();
    public ElementInterface mouseUp();
    public ElementInterface mouseDown();

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
        public Clickable doubleClick();
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
    
    public interface Calendar extends Selectable{
        public Selectable select(AutomationCalendar date);
        
    }
    
    public interface Selectable extends TextBased {
        
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
        public String getTextFromOption(Integer optionNumber);
    }
    
    public interface TableBased<T> extends Iterable<T> {
        public <S extends T> S row(int rowNumber);
        public <S extends T> S row(IndexEnum indexByName);
    }
    
    public interface TextTableBased<S extends TextBased> extends TableBased<S> {

		boolean isAscending();

		boolean isDescending();

		boolean validateAscending();

		boolean validateDescending();

		boolean assertAscending();

		boolean assertDescending();

		boolean validateContains(String text);

		boolean assertContains(String text);
    	
    }
    
    public interface TypeableTableBased<T extends Typeable> extends TableBased<T> {
    	
    }
    
    public interface ClickableTableBased<T extends Clickable> extends TableBased<T> {
    	
    }
    
    public interface CheckBoxTableBased<T extends Checkable> extends TableBased<T> {

		boolean allChecked();

		boolean allUnchecked();

		boolean assertAllUnChecked();

		boolean assertAllChecked();

		boolean validateAllUnChecked();

		boolean validateAllChecked();

		void unCheckAll();

		void clickAll();

		void checkAll();
    	
    }
    
    public interface TextBased extends ElementInterface {
        /**
         * Asserts that the Element is the same as that provided by the Page Enum
         * @return
         */
        @Assert(englishName="the same as the default value")
        public Boolean assertTheDefaultValue();
        
        /**
         * Asserts that the Element is the same as the provided text
         * @param compareAgainst
         * @return
         */
        @Assert(englishName="")
        public Boolean assertTheSameAs(String compareAgainst);
        
        /**
         * Asserts that the Element is not the same as the provided text
         * @param compareAgainst
         * @return
         */
        @Assert(englishName="not the same as")
        public Boolean assertIsNot(String compareAgainst);
        
        /**
         * Asserts that the Element contains the provided text
         * @return
         */
        @Assert(englishName="close to")
        public Boolean assertContains(String compareAgainst);
        
        /**
         * Asserts that the Element does not contain the provided text
         * @param compareAgainst
         * @return
         */
        @Assert(englishName="not close to")
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
        @Validate(englishName="the same as the default value")
        public Boolean validateTheDefaultValue();
        
        /**
         * Validates the Element equals the provided text
         * @param expected
         * @return
         */
        @Validate(englishName="the same as")
        public Boolean validate(String expected);
        
        /** 
         * Validates the Element is not equal to the provided text
         * @param expected
         * @return
         */
        @Validate(englishName="not the same as")
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
        @Validate(englishName="close to")
        public Boolean validateContains(String expectedPart);
        
        /**
         * Validates that the Element does not contain the provided text
         * @param expectedPart
         * @return
         */
        @Validate(englishName="not close to")
        public Boolean validateDoesNotContain(String expectedPart);

        /**
         * Validates that the Element ignores capital letters
         * @param compareAgainst
         * @return
         */
        @Validate(englishName="capitalized contains")
		public Boolean capitalizedContains(String compareAgainst);

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
