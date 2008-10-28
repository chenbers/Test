package com.inthinc.pro.util;

public class MiscUtil
{
    static public int randomInt(int min, int max)
    {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

}
