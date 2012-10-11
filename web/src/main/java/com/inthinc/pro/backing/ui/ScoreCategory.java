package com.inthinc.pro.backing.ui;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


public enum ScoreCategory
{
    CAT_1(1, 0, 10, "FF0101"),
    CAT_2(2, 11, 20, "FF6601"),
    CAT_3(3, 21, 30, "F6B305"),
    CAT_4(4, 31, 40, "1E88C8"),
    CAT_5(5, 41, 50, "6B9D1B");
    
    private int code;
    private int min;
    private int max;
    private String color;
    
    private ScoreCategory(int code, int min, int max, String color)
    {
        this.code = code;
        this.min = min;
        this.max = max;
        this.color = color;
    }

    private static final Map<Integer, ScoreCategory> lookup = new HashMap<Integer, ScoreCategory>();
    static
    {
        for (ScoreCategory p : EnumSet.allOf(ScoreCategory.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static ScoreCategory getScoreCategory(Integer code)
    {
        return lookup.get(code);
    }
    
    public static ScoreCategory getCategoryForScore(int score)
    {
        for (ScoreCategory p : EnumSet.allOf(ScoreCategory.class))
        {
            if (score >= p.getMin() && score <= p.getMax())
            {
                return p;
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName()+"."+super.toString();

    }

    public int getMin()
    {
        return min;
    }

    public void setMin(int min)
    {
        this.min = min;
    }

    public int getMax()
    {
        return max;
    }

    public void setMax(int max)
    {
        this.max = max;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

}

