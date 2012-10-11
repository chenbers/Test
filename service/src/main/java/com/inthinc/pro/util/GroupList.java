package com.inthinc.pro.util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "groupList")
public class GroupList {
    private List<String> valueList;

    @XmlElement(name = "groupID")
    public List<String> getValueList() {
        return this.valueList;
    }

    public List<Integer> getValueListAsIntegers() {

        ArrayList<Integer> result = new ArrayList<Integer>();

        for (String value : valueList) {
            result.add(Integer.parseInt(value));
        }

        return result;
    }

    public void setValue(List<String> value) {
        this.valueList = value;
    }

    public GroupList() {
        this.valueList = new ArrayList<String>();
    }

    public GroupList(List<Integer> l) {
        ArrayList<String> stringList = new ArrayList<String>();

        for (Integer value : l) {
            stringList.add(Integer.toString(value));
        }

        this.valueList = stringList;
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
