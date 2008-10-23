package com.inthinc.pro.dao.mock.data;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Company;
import com.inthinc.pro.model.OverallScore;
import com.inthinc.pro.model.Role;
import com.inthinc.pro.model.User;

public class MockData
{
    static int NUM_COMPANIES = 1;
    static long timeNow = new Date().getTime();
    static int baseTimeSec = DateUtil.convertMillisecondsToSeconds(new Date().getTime());

    static int companyID;
    static Integer idOffset;
    static String MST_TZ = "US/Mountain";
    static final String PASSWORD="nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password


    public static void initializeStaticData(MockDataContainer mockData)
    {
        for (int i = 0; i < NUM_COMPANIES; i++)
        {
            companyID = i+1;
            idOffset = i * NUM_COMPANIES * 100;
            System.out.println("COMPANY: " + companyID);
            
            // company
            addCompany(mockData);
            
            // users
            addUsers(mockData);
        }

    }
    
    
    private static void addCompany(MockDataContainer mockData)
    {
        Company company = new Company(companyID, "Company " + companyID);
        mockData.storeObject(company);
    }
    
    private static void addUsers(MockDataContainer mockData)
    {
        
        User[] users = 
                {
                    new User(0, companyID, "expired", "expired@email.com", PASSWORD, Role.ROLE_NORMAL_USER, Boolean.FALSE),
                    new User(0, companyID, "custom", "custom@email.com", PASSWORD, Role.ROLE_CUSTOM_USER, Boolean.TRUE),
                    new User(0, companyID, "normal", "normal@email.com", PASSWORD, Role.ROLE_NORMAL_USER, Boolean.TRUE),
                    new User(0, companyID, "readonly", "readonly@email.com", PASSWORD, Role.ROLE_READONLY, Boolean.TRUE),
                    new User(0, companyID, "superuser", "superuser@email.com", PASSWORD, Role.ROLE_SUPER_USER, Boolean.TRUE),
                    new User(0, companyID, "supervisor", "supervisor@email.com", PASSWORD, Role.ROLE_SUPERVISOR, Boolean.TRUE)
        };

        
        for (int userCnt = 0; userCnt < users.length; userCnt++)
        {
            Integer userID = idOffset + userCnt + 1;
            users[userCnt].setUserID(userID);
            mockData.storeObject(users[userCnt]);
            
            // add other data that has userID as a foreign key
            Integer dateNow = DateUtil.getTodaysDate();
            mockData.storeObject(new OverallScore(userID, randomInt(0,50), dateNow));   // past 30 days
            mockData.storeObject(new OverallScore(userID, randomInt(0,50), DateUtil.getDaysBackDate(dateNow, 30 * 3)));   // past 3 months
            mockData.storeObject(new OverallScore(userID, randomInt(0,50), DateUtil.getDaysBackDate(dateNow, 30 * 6)));   // past 6 months
            mockData.storeObject(new OverallScore(userID, randomInt(0,50), DateUtil.getDaysBackDate(dateNow, 30 * 12)));   // past 12 months
            
        }
    }


    // helper Utility methods
    
    static int randomInt(int min, int max)
    {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    private static float randomLng()
    {
        // longitude in range -117 to -101
        float base = -101.0f;

        return base + ((float) randomInt(0, 16000) / 1000.0f * -1.0f);
    }

    private static float randomLat()
    {
        // latitude in range 36 to 49
        float base = 36.0f;

        return base + ((float) randomInt(0, 15000) / 1000.0f);
    }
    
    
}
