package com.inthinc.pro.dao.mock.data;


import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Company;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Role;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.User;

public class MockData
{
    private static final Logger logger = Logger.getLogger(MockData.class);
    
    public static Integer TOP_GROUP_ID = 101; 
    
    static int NUM_COMPANIES = 1;
    static int MAX_GROUPS = 100;
    static int MAX_DRIVERS_IN_GROUP = 10;
    static int MAX_USERS_IN_GROUP = 10;
    static long timeNow = new Date().getTime();
    static int baseTimeSec = DateUtil.convertMillisecondsToSeconds(new Date().getTime());

    static String MST_TZ = "US/Mountain";
    static final String PASSWORD="nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password

    private MockDataContainer mockData;

    public void initializeStaticData(MockDataContainer mockData)
    {
        this.mockData = mockData;
        
        for (int i = 0; i < NUM_COMPANIES; i++)
        {
            Integer companyID = i+1;
            System.out.println("COMPANY: " + companyID);
            
            // company
            addCompanyData(companyID);
            dumpData(companyID);
        }

    }
    
    
    private void dumpData(Integer companyID)
    {
        Company company = mockData.retrieveObject(Company.class, "companyID", companyID);
        dumpObject(company,"");
        
        // get all groups
        List<Group> groups = mockData.lookupObjectList(Group.class, new Group());
        for (Group group : groups)
        {
            dumpObject(group, "  ");
            // get all users in group
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("groupID", group.getGroupID());

            List<User> users = mockData.retrieveObjectList(User.class, searchCriteria);
            for (User user : users)
            {
                dumpObject(user, "    ");
            }
            List<Driver> drivers = mockData.retrieveObjectList(Driver.class, searchCriteria);
            for (Driver driver : drivers)
            {
                dumpObject(driver, "    ");
            }
        }
        
    }

    private <T> void dumpObject(T obj, String indent)
    {
        Map<String, Object> objMap = mockData.createMapFromObject(obj);
        logger.debug(indent + obj.getClass().getName());
        StringBuffer buffer = new StringBuffer(indent);
        for (Map.Entry<String, Object> item : objMap.entrySet())
        {
            buffer.append(item.getKey());
            buffer.append("[");
            buffer.append(item.getValue().toString());
            buffer.append("] ");
        }
            
        logger.debug(buffer);
        
    }

    private void addCompanyData(Integer companyID)
    {
        Company company = new Company(companyID, "Company " + companyID);
        mockData.storeObject(company);
        addGroupData(companyID);
    }
    
    private void addGroupData(Integer companyID)
    {
        Integer idOffset = companyID * MAX_GROUPS;
        // groups in company
        // structure:
        //                                                  United States 
        //              Western                                 Eastern                             Miscellaneous
        //  Montana          Utah        Colorado        New York    New Jersey                     D1 D2 D3
        // T1   T2  T3     D1 D2 D3       D1 D2          T1     T2      D1 D2
        // D1   D1  D1 D2                               D1 D2   D1 D2               
        Group[] groups =
        {
                new Group(idOffset+1, companyID, "United States", 0),       // top level group (executive)
                
                new Group(idOffset+2, companyID, "Western", idOffset+1),    // region level
                new Group(idOffset+3, companyID, "Eastern", idOffset+1),
                new Group(idOffset+4, companyID, "Miscellaneous", idOffset+1),     
                
                new Group(idOffset+5, companyID, "Montana", idOffset+2),    // parent Western
                new Group(idOffset+6, companyID, "Utah", idOffset+2),
                new Group(idOffset+7, companyID, "Colorado", idOffset+2),
                
                new Group(idOffset+8, companyID, "New York", idOffset+3),   // parent Eastern
                new Group(idOffset+9, companyID, "Maine", idOffset+3),
                
                new Group(idOffset+10, companyID, "MT Team 1", idOffset+5), // parent Western/Montana
                new Group(idOffset+11, companyID, "MT Team 2", idOffset+5),
                new Group(idOffset+12, companyID, "MT Team 3", idOffset+5),

                new Group(idOffset+13, companyID, "NY Team 1", idOffset+8), // parent Eastern/New York
                new Group(idOffset+14, companyID, "NY Team 2", idOffset+8),

        };
        
        for (int cnt = 0; cnt < groups.length; cnt++)
        {
            mockData.storeObject(groups[cnt]);
            
            // users are people who can log in with various roles
            addUsersToGroup(companyID, groups[cnt].getGroupID());
            
            addScores(groups[cnt].getGroupID(), EntityType.ENTITY_GROUP, groups[cnt].getName());
            
            if (!groupIsParent(groups, groups[cnt].getGroupID()))
            {
                addDriversToGroup(companyID, groups[cnt].getGroupID(), randomInt(1, MAX_DRIVERS_IN_GROUP));
            }
        }
        
        
    }
    


    private void addUsersToGroup(Integer companyID, Integer groupID)
    {
        Integer idOffset = companyID * MAX_GROUPS + groupID * MAX_USERS_IN_GROUP;
        
        User[] users = 
                {
                    new User(idOffset+1, companyID, groupID, "expired"+groupID, "expired"+groupID+"@email.com", PASSWORD, Role.ROLE_NORMAL_USER, Boolean.FALSE),
                    new User(idOffset+2, companyID, groupID, "custom"+groupID, "custom"+groupID+"@email.com", PASSWORD, Role.ROLE_CUSTOM_USER, Boolean.TRUE),
                    new User(idOffset+3, companyID, groupID, "normal"+groupID, "normal"+groupID+"@email.com", PASSWORD, Role.ROLE_NORMAL_USER, Boolean.TRUE),
                    new User(idOffset+4, companyID, groupID, "readonly"+groupID, "readonly"+groupID+"@email.com", PASSWORD, Role.ROLE_READONLY, Boolean.TRUE),
                    new User(idOffset+5, companyID, groupID, "superuser"+groupID, "superuser"+groupID+"@email.com", PASSWORD, Role.ROLE_SUPER_USER, Boolean.TRUE),
                    new User(idOffset+6, companyID, groupID, "supervisor"+groupID, "supervisor"+groupID+"@email.com", PASSWORD, Role.ROLE_SUPERVISOR, Boolean.TRUE)
        };

        for (int userCnt = 0; userCnt < users.length; userCnt++)
        {
            mockData.storeObject(users[userCnt]);
        }
    }

    private void addScores(Integer entityID, EntityType entityType, String entityName)
    {
        // add other data that has userID as a foreign key
        Integer dateNow = DateUtil.getTodaysDate();
        int[] monthsBack = {
                0, 3, 6, 12
        };
        for (int monthsBackCnt = 0; monthsBackCnt < monthsBack.length; monthsBackCnt++)
        {
            for (ScoreType scoreType : EnumSet.allOf(ScoreType.class))
            {
                if (        scoreType == ScoreType.SCORE_OVERALL_TIME ) {
                    createOverallScoreOverTime(
                            entityID, 
                            entityType, 
                            entityName, 
                            scoreType, 
                            monthsBack[monthsBackCnt]);
                  
                } else if ( scoreType == ScoreType.SCORE_OVERALL_PERCENTAGES ) {
                    createOverallScorePercentages(
                            entityID, 
                            entityType, 
                            entityName, 
                            scoreType, 
                            monthsBack[monthsBackCnt]);
                } else {                    
                    mockData.storeObject(
                            new ScoreableEntity(
                                    entityID, 
                                    entityType, 
                                    entityName, 
                                    randomInt(0,50), 
                                    DateUtil.getDaysBackDate(dateNow, 30 * monthsBack[monthsBackCnt]),
                                    scoreType));
                }
            }
        }
    }
    
    private void createOverallScoreOverTime(Integer entityID, 
            EntityType entityType, String entityName, ScoreType scoreType, int monthsBack) {
      //Create dates per range
        for ( int i = 0; i < 30; i++ ) {
            mockData.storeObject(
                new ScoreableEntity(entityID, 
                    entityType, 
                    entityName, 
                    randomInt(0,50), 
                    DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), 
                            (30 * monthsBack) - i * DateUtil.SECONDS_IN_DAY),
                    scoreType));
        }
    }
    
    
    private void createOverallScorePercentages(Integer entityID, 
            EntityType entityType, String entityName, ScoreType scoreType, int monthsBack) {
      //Create the five values per date range
        for ( int i = 0; i < 5; i++ ) {
            try {
            Thread.sleep(5000);
            mockData.storeObject(
                new ScoreableEntity(entityID, 
                    entityType, 
                    entityName, 
                    randomInt(0,50), 
                    DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), 
                            (30 * monthsBack)),
                    scoreType));
            } catch (Exception e) {}
        }
    }

    private void addDriversToGroup(Integer companyID, Integer groupID, int numDriversInGroup)
    {
        Integer idOffset = companyID * MAX_GROUPS + groupID * MAX_DRIVERS_IN_GROUP;
        
        for (int i = 0; i < numDriversInGroup; i++)
        {
            int id = idOffset+i+1;
            Driver driver = new Driver(id, companyID, groupID, "John", "Driver"+id);
            mockData.storeObject(driver);
            addScores(driver.getDriverID(), EntityType.ENTITY_DRIVER, driver.getFirstName() + driver.getLastName());
        }
        
    }


    private boolean groupIsParent(Group[] groups, Integer groupID)
    {
     
        for (Group group : groups)
        {
            if (group.getParentID().equals(groupID))
                return true;
        }
        return false;
    }



    // helper Utility methods
    
    static String randomScore()
    {
        int score = randomInt(0, 4);
        int decimal = randomInt(0, 9);
        
        return (score + "." + decimal);
    }
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
