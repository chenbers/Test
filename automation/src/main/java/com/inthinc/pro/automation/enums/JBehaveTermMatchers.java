package com.inthinc.pro.automation.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.inthinc.pro.automation.jbehave.RegexTerms;

public class JBehaveTermMatchers {
    


    public enum ElementTypes {
        _button("button"), 
        _textField("field", "textfield"),
        _link("link", "textlink"),
        _dropDown("dropdown", "select"),
        _text("text", "error", "label"),
        _pager("pager", "paging", "index", "indexer"),
        _checkBox("checkbox"),

        ;
        
        private static final Map<String, String> aliases = new HashMap<String, String>();
        private String[] matches;
        
        private ElementTypes(String ...matches){
            this.matches = matches;
        }
        
        static {
            for (ElementTypes type : EnumSet.allOf(ElementTypes.class)){
                for (String match : type.matches){
                    aliases.put(match, type.name());
                }
            }
        }
        
        public static String getTypeFromString(String toMatch){
            String alias = getAlias(toMatch);
            if (alias!=null){
                return aliases.get(alias);
            }
            return null;
        }
        
        public static String getAlias(String toMatch){
            Pattern pat;
            Matcher mat;
            for (String pattern : RegexTerms.typeReg){
                pat = Pattern.compile(pattern);
                mat = pat.matcher(toMatch);
                if (mat.find()){
                    String type = toMatch.substring(mat.start(), mat.end());
                    if (aliases.containsKey(type)){
                        return type;
                    }
                }
            }
            return null;
        }
    
    }
    

}
