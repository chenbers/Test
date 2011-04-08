package com.inthinc.pro.automation.selenium;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.lang.StringUtils;

public class RallyStack {
    
    private final static String newLineChar = "<br />";
    private final static String tab = StringUtils.repeat("&nbsp;", 5);
    
    public final static String toString(Throwable stack){
        StackTraceElement[] stackElements = stack.getStackTrace();
        return toString(stackElements);
    }
    
    public final static String toString(StackTraceElement[] stack){
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);

        for (StackTraceElement element : stack){
            String line = StringUtils.repeat(tab, 2) + element.toString();
            if (line.contains("com.inthinc.")){
                printWriter.write("<font color=\"RED\">" + line + "</font>"+newLineChar);
            }else{
                printWriter.write(line + newLineChar);
            }
        }
        return result.toString();
    }

}
