package com.inthinc.pro.automation.utils;

public class Id {

    public static String clazz(String clazz) {
        return "@class" + end(clazz);
    }

    private static String end(String end) {
        if (!end.equals("")){
            return "='" + end + "'";    
        }
        return end;
    }

    public static String href(String href) {
        return "@href" + end(href);
    }

    public static String id(String id) {
        return "@id" + end(id);
    }

    public static String name(String name) {
        return "@name" + end(name);
    }

    public static String rel(String rel) {
        return "@rel" + end(rel);
    }

    public static String style(String type) {
        return "@tpe" + end(type);
    }

    public static String text(String text) {
        return "text()" + end(text);
    }

    public static String title(String title) {
        return "@title" + end(title);
    }

    public static String type(String type) {
        return "@type" + end(type);
    }

    public static String contains(String identifier, String partial) {
        return "contains("+identifier+",'"+partial+"')";
    }

}
