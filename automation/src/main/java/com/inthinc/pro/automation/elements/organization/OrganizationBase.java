package com.inthinc.pro.automation.elements.organization;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.utils.MasterTest;

public abstract class OrganizationBase extends MasterTest{



    
    protected final int[] structure;
    
    protected final OrganizationType type;
    
    public static enum OrganizationType{
        FLEET,
        DIVISION,
        TEAM,
        USER,
        DRIVER,
        VEHICLE,
        ;
        
        @Override 
        public String toString(){
            return this.name().toLowerCase();
        }
    }
    
    
    public OrganizationBase(int[] parent, int position, OrganizationType type) {
        structure = Arrays.copyOf(parent, parent.length + 1);
        structure[parent.length] = getFirstOfType(type);
        this.type = type;
    }
    
    public OrganizationBase(int[] parent, String name, OrganizationType type) {
        structure = Arrays.copyOf(parent, parent.length + 1);
        structure[parent.length] = getFirstByName(name, type);
        this.type = type;
    }
    
    public OrganizationBase(OrganizationType type){
        structure = new int[]{0};
        this.type = type;
    }
    
    private int getFirstOfType(OrganizationType type){
        String xpath = "//td[contains(@id,'::"+type+":text')]";
        return stripForPosition(xpath);
    }
    
    private int stripForPosition(String xpath){
        List<WebElement> matches = getSelenium().getWrappedDriver().findElements(By.xpath(xpath));
        
        String[] id = matches.get(0).getAttribute("id").split(":");
        int position = 0;
        for (int i = id.length-1;i>=0;i--){
            try {
                position = Integer.parseInt(id[i]);
                break;
            } catch (NumberFormatException e){
                continue;
            }
        }
        return position;
    }
    
    public int getFirstByName(String name, OrganizationType type){
        String xpath = "//td[contains(@id,'::"+type+":text')][text()='"+name+"']";
        return stripForPosition(xpath);
    }
    
    protected SeleniumEnumWrapper getID(String type){
        StringWriter writer = new StringWriter();
        writer.write("display-form:tree:");
        for (int position:structure){
            writer.write(position + ":");
        }
        writer.write(":");
        writer.write(this.type.toString());
        writer.write(":");
        writer.write(type);
        SeleniumEnumWrapper anEnum = new SeleniumEnumWrapper(this.type.toString(), writer.toString());
        return anEnum;
    }
    
    public TextLink text(){
        return new TextLink(getID("text"));
    }
    
    
    public Button icon(){
        return new Button(getID("icon"));
    }
    
}
