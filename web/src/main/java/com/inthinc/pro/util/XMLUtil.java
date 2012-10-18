package com.inthinc.pro.util;

public class XMLUtil {
    
    private static final String AMPERSAND = "&";
    private static final String XML_AMPERSAND = "&amp;";
    private static final String LESS_THAN = "<";
    private static final String XML_LESS_THAN = "&lt;";
    private static final String GREATER_THAN = ">";
    private static final String XML_GREATER_THAN = "&gt;";
    private static final String QUOTE = "\"";
    private static final String XML_QUOTE = "&quot;";
    private static final String APOSTROPHE = "'";
    private static final String XML_APOSTROPHE = "&apos;";

    public static Object escapeXMLChars(Object input) {
        if (!(input instanceof String))
            return input;
        return (input == null) ? "" : input.toString().replace(AMPERSAND, XML_AMPERSAND).replace(LESS_THAN, XML_LESS_THAN).replace(GREATER_THAN, XML_GREATER_THAN).replace(QUOTE, XML_QUOTE)
                .replace(APOSTROPHE, XML_APOSTROPHE);
    }


}
