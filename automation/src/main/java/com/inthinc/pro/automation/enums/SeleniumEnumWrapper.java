package com.inthinc.pro.automation.enums;

import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.openqa.selenium.By;

import com.inthinc.pro.automation.interfaces.IndexEnum;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;

public class SeleniumEnumWrapper implements SeleniumEnums {

    private final static String regex = "@id\\='.*###.*'";

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
            text = StringEscapeUtils.unescapeHtml(myEnum.getText());
            text = StringEscapeUtils.unescapeJava(text);
        }
    }
    
    public void makeReplacements(Object ...objects){
    	for (Object object : objects){
    		if (object instanceof TextEnum){
    			replaceWord(((TextEnum)object).getText());
    		} else if (object instanceof String){
    			replaceWord((String)object);
    		} else if (object instanceof IndexEnum){
    			replaceNumber(((IndexEnum)object).getIndex());
    		} else if (object instanceof Integer){
    			replaceNumber((Integer)object);
    		} else if (object instanceof Enum){
    			try {
					Method getText = object.getClass().getMethod("getText");
					replaceWord((String) getText.invoke(object));
				} catch (Exception e1) {
					try {
						Method getCode = object.getClass().getMethod("getCode");
						replaceNumber((Integer) getCode.invoke(object));
					} catch (Exception e2) {
						
					}
				}
    		}
    	}
    }
    
    public SeleniumEnumWrapper(TextEnum myEnum){
        name = myEnum.toString();
        setID(myEnum.getText());
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
    
    public List<By> getLocatorsForWebDriver() {
        List<By> locators = new ArrayList<By>();
        List<String> ids = getLocators();
        for (String id: ids){
            if (id.startsWith("//")){
                locators.add(By.xpath(id));
            } else if (!id.contains("=")){
                locators.add(By.id(id));
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
        String use = neww;
        for (int i = 0; i < IDs.length; i++) {
            if (original.equals("###")) {
                String id = IDs[i];
                if (id.startsWith("//")) {
                    Pattern pat = Pattern.compile(regex);
                    Matcher match = pat.matcher(id);
                    while (match.find()) {
                        int start = match.start();
                        int end = match.end();
                        String before = id.substring(0, start);
                        String middle = id.substring(start, end).replace("###", (Integer.parseInt(neww) - 1) + "");
                        String after = id.substring(end);
                        IDs[i] = before + middle + after;
                    }
                } else {
                    use = Integer.parseInt(neww) -1 + "";
                }

            }
            IDs[i] = IDs[i].replace(original, use);
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
