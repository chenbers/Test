package com.inthinc.pro.automation.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum WordConverterEnum {
    ZERO(),
    ONE("first"),
    TWO("second"),
    THREE("third"),
    FOUR("fourth"),
    FIVE("fifth"),
    SIX("sixth"),
    SEVEN("seventh"),
    EIGHT("eighth"),
    NINE("ninth"),
    TEN("tenth"),
    ELEVEN("eleventh"),
    TWELVE("twelth", "twelfth"),
    THIRTEEN("thirteenth"),
    FOURTEEN("fourteenth"),
    FIFTEEN("fifteenth"),
    SIXTEEN("sixteenth"),
    SEVENTEEN("seventeenth"),
    EIGHTEEN("eighteenth"),
    NINETEEN("nineteenth"),
    TWENTY("twentieth"),
    
    ;
    
    private List<String> matches;
    
    private WordConverterEnum(String ...strings){
        matches = new ArrayList<String>();
        for (String string: strings){
            matches.add(string);
        }
    }
    
    private static Map<String, Integer> matcher = new HashMap<String, Integer>();
    
    static {
        for (WordConverterEnum word: EnumSet.allOf(WordConverterEnum.class)){
            int place = word.ordinal();
            matcher.put(word.name().toLowerCase(), place);
            for (String match : word.matches){
                matcher.put(match, place);
            }
        }
    }
    
    public static Integer getNumber(String sentence){
        for (String word : matcher.keySet()){
            if (sentence.contains(word)){
                return matcher.get(word);
            }
        }
        return null;
    }

}
