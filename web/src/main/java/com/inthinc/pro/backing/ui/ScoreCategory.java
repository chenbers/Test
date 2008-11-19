package com.inthinc.pro.backing.ui;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


public enum ScoreCategory
{
    CAT_1(1, "0mi", 0, 10, "FF0101", "0 - 1.0"),
    CAT_2(2, "500mi", 11, 20, "FF6601", "1.1 - 2.0"),
    CAT_3(3, "1,000mi", 21, 30, "F6B305", "2.1 - 3.0"),
    CAT_4(4, "5,000mi", 31, 40, "1E88C8", "3.1 - 4.0"),
    CAT_5(5, "10,000mi", 41, 50, "6B9D1B", "4.1 - 5.0");
    
    private String description;
    private int code;
    private int min;
    private int max;
    private String color;
    private String range;
    
    private ScoreCategory(int code, String description, int min, int max, String color, String range)
    {
        this.code = code;
        this.description = description;
        this.min = min;
        this.max = max;
        this.color = color;
        this.range = range;
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
        return this.description;
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

    public String getRange()
    {
        return range;
    }

    public void setRange(String range)
    {
        this.range = range;
    }
}

