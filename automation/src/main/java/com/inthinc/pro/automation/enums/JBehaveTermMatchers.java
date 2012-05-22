package com.inthinc.pro.automation.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.inthinc.pro.automation.jbehave.RegexTerms;

public enum JBehaveTermMatchers {
        
    _button("button"), 
    _textField("field", "textfield"),
    _link("link", "textlink", "tab"),
    _dropDown("dropdown", "select"),
    _text("text", "error", "label"),
    _pager("pager", "paging", "index", "indexer"),
    _checkBox("checkbox"),
    
    _navTree("navtree", "tree", "grouptree"),
    
    _popUp,
    
    closePopup("is closed", "closes", "disappears", "is removed"),
    openPopup("is opened", "opens", "appears"),

    ;
    
    private static final Map<String, String> aliases = new HashMap<String, String>();
    private String[] matches;
    
    private JBehaveTermMatchers(String ...matches){
        this.matches = matches;
    }
    
    static {
        for (JBehaveTermMatchers type : EnumSet.allOf(JBehaveTermMatchers.class)){
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
        for (Map.Entry<String, String> entry : aliases.entrySet()){
            if (toMatch.endsWith(entry.getKey())){
                return entry.getValue();
            }
        }
        return null;
    }
}
