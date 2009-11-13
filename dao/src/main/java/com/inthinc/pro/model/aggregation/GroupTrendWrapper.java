package com.inthinc.pro.model.aggregation;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.Group;

@XmlRootElement
public class GroupTrendWrapper {
    private Group group;
    @Column(name = "driveQV")
    private List<Trend> trendList;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @XmlElement(name="trend")
    public List<Trend> getTrendList() {
        return trendList;
    }

    public void setTrendList(List<Trend> trendList) {
        this.trendList = trendList;
    }

    @Override
    public String toString() {
        return "GroupTrendWrapper [group=" + group + ", trendList=" + trendList + "]";
    }

}
