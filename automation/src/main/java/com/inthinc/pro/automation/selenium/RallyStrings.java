package com.inthinc.pro.automation.selenium;

import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;

public class RallyStrings {
    
    private final static String newLineChar = "\n";
    private final static String tab = "\t";
    
    public final static String toString(Throwable stack){
        StackTraceElement[] stackElements = stack.getStackTrace();
        return toString(stackElements);
    }
    
    public final static String toString(StackTraceElement[] stack){
        StringWriter result = new StringWriter();
        Boolean passedTest = false;
        for (StackTraceElement element : stack){
            String line = StringUtils.repeat(tab, 2) + element.toString();
            if (line.contains("selenium.testSuites")&&!passedTest){
                passedTest = true;
            }else if (!line.contains("selenium.testSuites") && passedTest){
                break;
            }
            result.write(line + newLineChar);
        }
        return result.toString();
    }
    
    public final static String toString(String string){
        StringWriter writer = new StringWriter();
        String[] lines = string.split("\n");
        for (String line: lines){
            String formatted = line.replace("\t",  StringUtils.repeat("&nbsp;", 5));
            if (line.contains("com.inthinc.")){
                writer.write("<font color=\"RED\">" + formatted + "</font>");
            }else{
                writer.write(formatted);
            }writer.write("<br />");
        }
        return writer.toString();
    }

}
