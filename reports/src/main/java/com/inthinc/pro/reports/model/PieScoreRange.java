package com.inthinc.pro.reports.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


public enum PieScoreRange
{
    SCORE_0("0.0 - 1.0",0),
    SCORE_4("4.1 - 5.0",4),
    SCORE_3("3.1 - 4.0",3),
    SCORE_2("2.1 - 3.0",2),
    SCORE_1("1.1 - 2.0",1);
    
    private String label;
    

    private Integer index;
    
    private static final Map<Integer, PieScoreRange> lookup = new HashMap<Integer, PieScoreRange>();
    static
    {
        for (PieScoreRange p : EnumSet.allOf(PieScoreRange.class))
        {
            lookup.put(p.index, p);
        }
    }
    
    private PieScoreRange(String label, Integer index){
        this.label = label;
        this.index = index;
    }
    
    public static PieScoreRange valueOf(Integer code)
    {
        return lookup.get(code);
    }
    
    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public Integer getIndex()
    {
        return index;
    }

    public void setIndex(Integer index)
    {
        this.index = index;
    }

}
