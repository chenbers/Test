package com.inthinc.pro.automation.elements.organization;

import java.io.StringWriter;
import java.util.Arrays;

import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class OrganizationBase {
    
    private final int[] structure;
    
    private String type;
    
    
    public OrganizationBase(int[] parent, int position) {
        structure = Arrays.copyOf(parent, parent.length + 1);
        structure[parent.length] = position;
    }
    
    public OrganizationBase(SeleniumEnums anEnum){
        structure = new int[]{0};
    }
    
    private SeleniumEnumWrapper getID(String type){
        StringWriter writer = new StringWriter();
        writer.write("display-form:tree:");
        for (int position:structure){
            writer.write(position + ":");
        }
        writer.write(":");
        writer.write(this.type);
        writer.write(type);
        SeleniumEnumWrapper anEnum = new SeleniumEnumWrapper(null, writer.toString());
        return anEnum;
    }
    
    private TextLink name(){
        
        return new TextLink(getID("name"));
    }

}
