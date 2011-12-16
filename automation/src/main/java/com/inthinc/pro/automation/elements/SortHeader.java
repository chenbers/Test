package com.inthinc.pro.automation.elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;

public class SortHeader extends TextButton {
    
    private final static String regex = "@id\\='.*'";


    public SortHeader(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
    }

    @Override
    public void setMyEnum(SeleniumEnums anEnum){
        myEnum = new SeleniumEnumWrapper(anEnum);
        String[] newIds = new String[myEnum.getIDs().length];
        for (int i=0;i<myEnum.getIDs().length;i++){
            String newID = "";
            String id = myEnum.getIDs()[i];
            if(id.startsWith("//")){
                Pattern pat = Pattern.compile(regex);
                Matcher match = pat.matcher(id);
                while (match.find()){
                    int start = match.start()+5;
                    int end = match.end()-1;
                    String before = id.substring(0, start);
                    String middle = processID(id.substring(start, end));
                    String after = id.substring(end);
                    newID = before + middle + after;
                }
            }else if( !id.contains("=")){
                newID = processID(id);
            }
            print(newID);
            newIds[i]=newID;
        }
        myEnum.setID(newIds);
    }
    
    private String processID(String id){
        String newID = "";
        String[] temp = id.split(":");
        for (int j=0;j<temp.length-2;j++){
            newID += temp[j] + ":";
        }
        newID += temp[temp.length-1] + "header";
        return newID;
    }

}
