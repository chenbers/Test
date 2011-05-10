package com.inthinc.pro.automation.elements;

import com.inthinc.pro.model.File;


public interface ElementInterface {
    public boolean isVisible();
    public ElementInterface focus();
    public ElementInterface validate();
    public ElementInterface addError(String errorName);
    public ElementInterface addError(String errorName, String error);
    
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
        public ElementInterface compareText(String expected);
        /**
         * Compares the value of this Element to the expected value stored in the Elements enum.
         * @return this Element
         */
        public ElementInterface compareText();
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
