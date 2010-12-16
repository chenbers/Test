package com.inthinc.pro.util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="groupList")
public class GroupList{
    private List<Integer> valueList;

    @XmlElement(name="groupID")
    public List<Integer> getValueList() {
        return valueList;
    }
    
    public void setValue(List<Integer> value) {
        this.valueList = value;
    }
    
    public GroupList() {
        valueList = new ArrayList<Integer>();
    }
    
    public GroupList(List<Integer> l) {
        valueList = l;
    }
}
