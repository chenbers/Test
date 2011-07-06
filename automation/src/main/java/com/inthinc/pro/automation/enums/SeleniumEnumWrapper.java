package com.inthinc.pro.automation.enums;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SeleniumEnumWrapper implements SeleniumEnums {

    private final static String regex = ".*@id='.*###.*'.*";

    private String text, url, name;
    private String[] IDs = new String[0];

    public SeleniumEnumWrapper(String name, String... IDs) {
	this.name = name;
	this.IDs = IDs;
    }

    public SeleniumEnumWrapper(SeleniumEnums myEnum) {
	name = new String(myEnum.toString());
	if (myEnum.getIDs() != null) {
	    IDs = myEnum.getIDs().clone();
	}
	if (myEnum.getURL() != null) {
	    url = new String(myEnum.getURL());
	}
	if (myEnum.getText() != null) {
	    text = new String(myEnum.getText());
	}
    }

    @Override
    public String[] getIDs() {
	return IDs;
    }

    /**
     * Returns a List of Strings representing the non-null locators for anEnum
     * 
     * @param anEnum
     * @return non-null element locator strings
     */
    public List<String> getLocators() {
	List<String> locators = new ArrayList<String>();
	if (IDs != null) {
	    for (String ID : IDs) {
		locators.add(ID);
	    }
	}
	return locators;
    }

    public String getLocatorsAsString() {
	StringWriter results = new StringWriter();
	results.write("Element Identifiers( ");
	int length = getLocators().size();
	for (String s : getLocators()) {
	    results.write(s);
	    if (--length != 0) {
		results.write(", ");
	    }
	}
	results.write(" )");
	return results.toString();
    }

    @Override
    public String getText() {
	return text;
    }

    @Override
    public String getURL() {
	return url;
    }

    public SeleniumEnumWrapper replaceNumber(Integer number) {
	replaceOldWithNew("###", number.toString());
	return this;
    }

    public SeleniumEnumWrapper replaceOldWithNew(String original, String neww) {
	for (int i = 0; i < IDs.length; i++) {
	    if (original.equals("###")
		    && (!IDs[i].startsWith("//")
			    || Pattern.matches(regex, IDs[i]))) {
		neww = Integer.parseInt(neww) - 1 + "";
	    }
	    IDs[i] = IDs[i].replace(original, neww);
	}

	return this;
    }

    public SeleniumEnumWrapper updateURL(Integer number) {
	return updateURL("###", number.toString());
    }

    public SeleniumEnumWrapper updateURL(String word) {
	return updateURL("***", word);
    }

    private SeleniumEnumWrapper updateURL(String original, String replacement) {
	if (url == null)
	    throw new NullPointerException("Cannot updateURL() if url is null");
	url = url.replace("###", replacement);
	return this;
    }

    public SeleniumEnumWrapper replaceWord(String word) {
	replaceOldWithNew("***", word);
	return this;
    }

    public SeleniumEnumWrapper setID(String... IDs) {
	this.IDs = IDs;
	return this;
    }

    public SeleniumEnumWrapper setName(String name) {
	this.name = name;
	return this;
    }

    public SeleniumEnumWrapper setText(String text) {
	this.text = text;
	return this;
    }

    public SeleniumEnumWrapper setUrl(String url) {
	this.url = url;
	return this;
    }

    public String toString() {
	return name;
    }

}
