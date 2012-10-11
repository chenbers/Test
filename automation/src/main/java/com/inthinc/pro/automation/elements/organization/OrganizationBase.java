package com.inthinc.pro.automation.elements.organization;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.organization.OrganizationLevels.DivisionLevel;
import com.inthinc.pro.automation.elements.organization.OrganizationLevels.FleetLevel;
import com.inthinc.pro.automation.elements.organization.OrganizationLevels.GroupLevels;
import com.inthinc.pro.automation.elements.organization.OrganizationLevels.TeamLevel;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.utils.MasterTest;

public class OrganizationBase extends MasterTest{

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
        
        public GroupLevels getGroup(){
            String name = toString();
            if (name.equals("fleet")){
                return OrganizationLevels.getFleet();
            } else if (name.equals("division")){
                return OrganizationLevels.getDivision();
            } else {
                return OrganizationLevels.getTeam();
            }
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
    
    protected String getID(){
        StringWriter writer = new StringWriter();
        writer.write("display-form:tree:");
        for (int position:structure){
            writer.write(position + ":");
        }
        writer.write(":");
        return writer.toString();
    }
    
    public TextLink text(){
        return new TextLink(getID("text"));
    }
    
    
    public Button icon(){
        return new Button(getID("icon"));
    }
    
    public GroupLevels goToGroup(String groupList){
        String[] groups = groupList.split(" - ");
        int pos = 1;
        OrganizationType top = getTopGroupType();
        GroupLevels topGroup = top.getGroup();
        return clickDownTree(topGroup, groups, pos);
        
    }
    
    private GroupLevels clickDownTree(GroupLevels currentGroup, String[] groupList, int position){
        if (currentGroup == null){
            return currentGroup;
        }
        currentGroup.arrow().click();
        if (currentGroup instanceof TeamLevel || position == groupList.length-1){
            return currentGroup;
        } else if (currentGroup instanceof FleetLevel){
            FleetLevel group = (FleetLevel) currentGroup;
            if (clickDownTree(group.getDivision(groupList[position++]), groupList, position)==null){
                return clickDownTree(group.getTeam(groupList[position]), groupList, position);   
            }
        } else if (currentGroup instanceof DivisionLevel){
            DivisionLevel group = (DivisionLevel) currentGroup;
            if (clickDownTree(group.getDivision(groupList[position++]), groupList, position)==null){
                return clickDownTree(group.getTeam(groupList[position]), groupList, position);   
            }
        }
        
        return currentGroup;
    }
       

    private OrganizationType getTopGroupType() {
        WebElement element = getSelenium().getWrappedDriver().findElement(By.xpath("//td[contains(@id,'display-form:tree:0::')][contains(@id,':text')]"));
        String id = element.getAttribute("id");
        Log.info(id);
        String[] split = id.split(":");
        String type = split[split.length-2];
        if (type.equals("fleet")){
            return OrganizationType.FLEET;
        } else if (type.equals("division")){
            return OrganizationType.DIVISION;
        } else {
            return OrganizationType.TEAM;
        }
    }
}
