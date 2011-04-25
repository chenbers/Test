package com.inthinc.pro.automation.elements;


public interface TypeableElementInterface extends ElementInterface {
    public ElementInterface type(String inputText);
    public ElementInterface clear();
}
