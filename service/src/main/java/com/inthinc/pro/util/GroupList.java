package com.inthinc.pro.util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "groupList")
public class GroupList {
    private List<Integer> valueList;

    @XmlElement(name = "groupID")
    public List<Integer> getValueList() {
        return this.valueList;
    }

    public void setValue(List<Integer> value) {
        this.valueList = value;
    }

    public GroupList() {
        this.valueList = new ArrayList<Integer>();
    }

    public GroupList(List<Integer> l) {
        this.valueList = l;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((valueList == null) ? 0 : valueList.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GroupList other = (GroupList) obj;
        if (valueList == null) {
            if (other.valueList != null)
                return false;
        } else if (!valueList.equals(other.valueList))
            return false;
        return true;
    }
}
