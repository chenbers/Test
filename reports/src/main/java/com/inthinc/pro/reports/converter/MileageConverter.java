package com.inthinc.pro.reports.converter;

import java.math.BigDecimal;

public class MileageConverter
{
    /**
     * 
     * @param mileage (As Integer)
     * @return mileage with two decimals.
     */
    public static Float convertMileage(Integer mileage)
    {
        BigDecimal bigDecimal = new BigDecimal(mileage.intValue());
        bigDecimal.movePointLeft(2);
        return Float.valueOf(bigDecimal.floatValue());
    }

}
