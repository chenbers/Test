package com.inthinc.pro.automation.utils;

import java.io.StringWriter;

public class Id {

    public static String align() {
        return "@align";
    }

    public static String align(String align) {
        return "@align" + end(align);
    }
    
    public static String and(String ...strings){
        StringWriter writer = new StringWriter();
        for(int i=0;i<strings.length;i++){
            writer.write(strings[i]);
            if (i<strings.length-1){
                writer.write(" and ");
            }
        }
        return writer.toString();
    }

    public static String clazz(String clazz) {
        return "@class" + end(clazz);
    }

    public static String contains(String identifier, String partial) {
        return "contains(" + identifier + ",'" + partial + "')";
    }

    private static String end(String end) {
        if (!end.equals("")) {
            return "='" + end + "'";
        }
        return end;
    }

    public static String href() {
        return "@href";
    }

    public static String href(String href) {
        return "@href" + end(href);
    }

    public static String id() {
        return "@id";
    }

    public static String id(String id) {
        return "@id" + end(id);
    }

    public static String name() {
        return "@name";
    }

    public static String name(String name) {
        return "@name" + end(name);
    }

    public static String onClick() {
        return "@onClick";
    }

    public static String onClick(String onClick) {
        return "@onClick" + end(onClick);
    }

    public static String rel() {
        return "@rel";
    }

    public static String rel(String rel) {
        return "@rel" + end(rel);
    }

    public static String style() {
        return "@style";
    }

    public static String style(String style) {
        return "@style" + end(style);
    }

    public static String text() {
        return "text()";
    }

    public static String text(String text) {
        return "text()" + end(text);
    }

    public static String title() {
        return "@title";
    }

    public static String title(String title) {
        return "@title" + end(title);
    }

    public static String type() {
        return "@type";
    }

    public static String type(String type) {
        return "@type" + end(type);
    }
    
    public static String valign() {
        return "@valign";
    }

    public static String valign(String valign) {
        return "@valign" + end(valign);
    }

}
