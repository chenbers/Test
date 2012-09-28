package com.inthinc.pro.model;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.annotations.Column;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class QuintileMap extends BaseEntity
{
    private Integer percent1;
    private Integer percent2;
    private Integer percent3;
    private Integer percent4;
    private Integer percent5;
    
    @Column(updateable=false)
    private transient List<Integer> percentList;
    
    public Integer getPercent1()
    {
        return percent1;
    }
    public void setPercent1(Integer percent1)
    {
        this.percent1 = percent1;
    }
    public Integer getPercent2()
    {
        return percent2;
    }
    public void setPercent2(Integer percent2)
    {
        this.percent2 = percent2;
    }
    public Integer getPercent3()
    {
        return percent3;
    }
    public void setPercent3(Integer percent3)
    {
        this.percent3 = percent3;
    }
    public Integer getPercent4()
    {
        return percent4;
    }
    public void setPercent4(Integer percent4)
    {
        this.percent4 = percent4;
    }
    public Integer getPercent5()
    {
        return percent5;
    }
    public void setPercent5(Integer percent5)
    {
        this.percent5 = percent5;
    }
    public List<Integer> getPercentList()
    {
        if (percentList == null)
        {
            percentList = new ArrayList<Integer>();
            percentList.add(percent1);
            percentList.add(percent2);
            percentList.add(percent3);
            percentList.add(percent4);
            percentList.add(percent5);
        }
        return percentList;
    }
    public void setPercentList(List<Integer> percentList)
    {
        this.percentList = percentList;
    }

}
