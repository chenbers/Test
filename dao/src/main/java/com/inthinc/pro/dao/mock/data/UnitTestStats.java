package com.inthinc.pro.dao.mock.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.inthinc.pro.model.ScoreType;

public class UnitTestStats
{
    public static final Integer UNIT_TEST_GROUP_ID = 114;
    
    public static final String UNIT_TEST_LOGIN = "custom114";
    public static final Integer UNIT_TEST_DRIVER_ID = 1222;

    public static final String ACCOUNT_CONTACT1 = "PHONE: 1-555-555-5555";
    public static final String ACCOUNT_CONTACT2 = "E-MAIL: test@email.com";
    
    // stats for the test group
    public int totalRedFlags = 0;
    public int totalWarningRedFlags = 0;
    public int totalTripsForDriver = 0;
    public int totalEventsInLastTripForDriver = 0;
    public int totalDrivers = 0;
    
    // for the test group, this holds the total number of drivers in each of the 5 score categories (0.0-5.0)
    // for each score type
    public Map<ScoreType, List<Integer>> totalScoreCntPerType = new HashMap<ScoreType, List<Integer>>();
    
    public void addToTotalScore(ScoreType scoreType, Integer score)
    {
        List<Integer> valueList = totalScoreCntPerType.get(scoreType);
        if (valueList == null)
        {
            valueList = new ArrayList<Integer>();
            for (int i = 0; i < 5; i++)
                valueList.add(new Integer(0));
            totalScoreCntPerType.put(scoreType, valueList);
        }
        int index = 0;
        if (score <= 10)
            index = 0;
        else if (score <= 20)
            index = 1;
        else if (score <= 30)
            index = 2;
        else if (score <= 40)
            index = 3;
        else index = 4;
        Integer value = valueList.get(index);
        valueList.set(index, (value + 1));
            
    }

    public void fixupTotalScores()
    {
        List<ScoreType> scoreTypeList = Collections.list(Collections.enumeration(EnumSet.allOf(ScoreType.class)));
        Collections.reverse(scoreTypeList);

        for (ScoreType scoreType : scoreTypeList)
        {
            List<ScoreType> subTypeList = scoreType.getSubTypes();
            if (subTypeList != null)
            {
                List<Integer> valueList = new ArrayList<Integer>();
                for (int i = 0; i < 5; i++)
                    valueList.add(new Integer(0));
                totalScoreCntPerType.put(scoreType, valueList);
                
                for (ScoreType subType : subTypeList)
                {
                    if (subType.equals(scoreType))
                        continue;
                    if (totalScoreCntPerType.get(subType) == null)
                    {
                        System.out.println(subType + " null");
                        continue;
                    }
                    for (int i = 0; i < 5; i++)
                    {
                        Integer value = valueList.get(i) + totalScoreCntPerType.get(subType).get(i);
                        valueList.set(i, value);
                    }
                }
            }
        }
    }
    
}
