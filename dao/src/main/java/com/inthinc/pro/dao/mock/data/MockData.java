package com.inthinc.pro.dao.mock.data;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.AccountStatus;
import com.inthinc.pro.model.BaseEnum;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Role;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreValueType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.User;

public class MockData
{
    private static final Logger logger = Logger.getLogger(MockData.class);
    
    public static final Integer TOP_GROUP_ID = 101; 
    static int NUM_ACCOUNTS = 1;
    static int MAX_GROUPS = 100;
    static int MAX_DRIVERS_IN_GROUP = 10;
    static int MAX_USERS_IN_GROUP = 10;
    static long timeNow = new Date().getTime();
    static int baseTimeSec = DateUtil.convertMillisecondsToSeconds(new Date().getTime());

    static String MST_TZ = "US/Mountain";
    static final String PASSWORD="nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password

    
    private Map<Class<?>, List<Object>> dataMap = new HashMap<Class<?>, List<Object>>();

    //  base all dates in the mock data after this, so that it is easier to unit test
    public Integer dateNow;    
    private static MockData mockData;
    private MockData()
    {
        dateNow = DateUtil.getTodaysDate();
        logger.debug("MockData current time:" + dateNow);
        initializeStaticData();
    }
    
    public static MockData getInstance()
    {
        if (mockData == null)
        {
            mockData = new MockData();
        }
        
        return mockData;
    }

    private void initializeStaticData()
    {
        for (int i = 0; i < NUM_ACCOUNTS; i++)
        {
            Integer accountID = i+1;
            System.out.println("ACCOUNT: " + accountID);
            
            // account
            addAccountData(accountID);
// Un-comment this block if you want to see the data that is generated.            
//          dumpData(companyID);
//
//            logger.debug("-------------------------------------------------------");
//            Group topGroup = lookupObject(Group.class, "groupID", TOP_GROUP_ID);
//            dumpGroupHierarchy(topGroup, "");
//            logger.debug("-------------------------------------------------------");
        }

    }
    
    //------------ DATA GENERATION METHODS ----------------
    
    private void addAccountData(Integer accountID)
    {
        Account account = new Account(accountID, 0, 0, AccountStatus.ACCOUNT_ACTIVE);
        storeObject(account);
        addGroupData(accountID);
    }
    
    private void addGroupData(Integer accountID)
    {
        Integer idOffset = accountID * MAX_GROUPS;
        // groups in company
        // structure:
        //                                                  United States 
        //              Western                                 Eastern                             Miscellaneous
        //  Montana          Utah        Colorado        New York    New Jersey                     D1 D2 D3
        // T1   T2  T3     D1 D2 D3       D1 D2          T1     T2      D1 D2
        // D1   D1  D1 D2                               D1 D2   D1 D2               
        Group[] groups =
        {
                new Group(idOffset+1, accountID, "United States", 0),       // top level group (executive)
                
                new Group(idOffset+2, accountID, "Western", idOffset+1),    // region level
                new Group(idOffset+3, accountID, "Eastern", idOffset+1),
                new Group(idOffset+4, accountID, "Miscellaneous", idOffset+1),     
                
                new Group(idOffset+5, accountID, "Montana", idOffset+2),    // parent Western
                new Group(idOffset+6, accountID, "Utah", idOffset+2),
                new Group(idOffset+7, accountID, "Colorado", idOffset+2),
                
                new Group(idOffset+8, accountID, "New York", idOffset+3),   // parent Eastern
                new Group(idOffset+9, accountID, "Maine", idOffset+3),
                
                new Group(idOffset+10, accountID, "MT Team 1", idOffset+5), // parent Western/Montana
                new Group(idOffset+11, accountID, "MT Team 2", idOffset+5),
                new Group(idOffset+12, accountID, "MT Team 3", idOffset+5),

                new Group(idOffset+13, accountID, "NY Team 1", idOffset+8), // parent Eastern/New York
                new Group(idOffset+14, accountID, "NY Team 2", idOffset+8),

        };
        
        for (int cnt = 0; cnt < groups.length; cnt++)
        {
            storeObject(groups[cnt]);
            
            // users are people who can log in with various roles
            addUsersToGroup(accountID, groups[cnt].getGroupID());
            
            addScores(groups[cnt].getGroupID(), EntityType.ENTITY_GROUP, groups[cnt].getName());
            
            if (!groupIsParent(groups, groups[cnt].getGroupID()))
            {
                addDriversToGroup(accountID, groups[cnt].getGroupID(), randomInt(1, MAX_DRIVERS_IN_GROUP));
            }
        }
        
        
    }
    


    private void addUsersToGroup(Integer accountID, Integer groupID)
    {
        Integer idOffset = accountID * MAX_GROUPS + groupID * MAX_USERS_IN_GROUP;
        
        User[] users = 
                {
                    createUser(idOffset+1, accountID, groupID, "expired"+groupID, "expired"+groupID+"@email.com", PASSWORD, Role.ROLE_NORMAL_USER, Boolean.FALSE),
                    createUser(idOffset+2, accountID, groupID, "custom"+groupID, "custom"+groupID+"@email.com", PASSWORD, Role.ROLE_CUSTOM_USER, Boolean.TRUE),
                    createUser(idOffset+3, accountID, groupID, "normal"+groupID, "normal"+groupID+"@email.com", PASSWORD, Role.ROLE_NORMAL_USER, Boolean.TRUE),
                    createUser(idOffset+4, accountID, groupID, "readonly"+groupID, "readonly"+groupID+"@email.com", PASSWORD, Role.ROLE_READONLY, Boolean.TRUE),
                    createUser(idOffset+5, accountID, groupID, "superuser"+groupID, "superuser"+groupID+"@email.com", PASSWORD, Role.ROLE_SUPER_USER, Boolean.TRUE),
                    createUser(idOffset+6, accountID, groupID, "supervisor"+groupID, "supervisor"+groupID+"@email.com", PASSWORD, Role.ROLE_SUPERVISOR, Boolean.TRUE)
        };

        for (int userCnt = 0; userCnt < users.length; userCnt++)
        {
            storeObject(users[userCnt]);
            storeObject(users[userCnt].getPerson());
        }
    }

    private User createUser(Integer id, Integer accountID, Integer groupID, String username, String email, String password, Role role, Boolean active)
    {
        User user = new User();
        user.setUserID(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setActive(active);
        user.setPerson(new Person());
        user.getPerson().setPersonID(id);
        user.getPerson().setEmpid(String.valueOf(id));
        user.getPerson().setAccountID(accountID);
        user.getPerson().setGroupID(groupID);
        user.getPerson().setEmail(email);
        user.getPerson().setFirst(username.substring(0, username.length() / 2));
        user.getPerson().setLast(username.substring(username.length() / 2));
        user.getPerson().setUser(user);
        return user;
    }

    private void addScores(Integer entityID, EntityType entityType, String entityName)
    {

        // create in reverse order -- this is a kludge so date range matches correctly

        int[] monthsBack = {
                12, 6, 3, 0
        };
        
        for (int monthsBackCnt = 0; monthsBackCnt < monthsBack.length; monthsBackCnt++)
        {
            for (ScoreType scoreType : EnumSet.allOf(ScoreType.class))
            {
                if (scoreType == ScoreType.SCORE_OVERALL_TIME ) {
                    createOverallScoreOverTime(
                            entityID, 
                            entityType, 
                            entityName, 
                            scoreType, 
                            monthsBack[monthsBackCnt]);
                  
                } else {
//                    storeObject(
//                            new ScoreableEntity(
//                                    entityID, 
//                                    entityType, 
//                                    entityName, 
//                                    randomInt(0,50), 
//                                    DateUtil.getDaysBackDate(dateNow, 30 * monthsBack[monthsBackCnt]),
//                                    scoreType,
//                                    ScoreValueType.SCORE_SCALE_0_50));
                }

                // percentages
//                createScorePercentages(
//                        entityID, 
//                        entityType, 
//                        entityName, 
//                        scoreType, 
//                        monthsBack[monthsBackCnt]);

            }
        }
        
        // create a score entity of each score type 1 per month
        for (int month = 0; month < 14; month++)
        {
            for (ScoreType scoreType : EnumSet.allOf(ScoreType.class))
            {
                if (scoreType != ScoreType.SCORE_OVERALL_TIME ) 
                {
                  storeObject(
                      new ScoreableEntity(
                              entityID, 
                              entityType, 
                              entityName, 
                              randomInt(0,50), 
                              DateUtil.getDaysBackDate(dateNow, 30 * month),
                              scoreType,
                              ScoreValueType.SCORE_SCALE_0_50));
                }
            }
        }
        
        
    }
    
    private void createOverallScoreOverTime(Integer entityID, 
            EntityType entityType, String entityName, ScoreType scoreType, int monthsBack) {
      //Create dates per range
        for ( int i = 0; i < 30; i++ ) {
            storeObject(
                new ScoreableEntity(entityID, 
                    entityType, 
                    entityName, 
                    randomInt(0,50), 
                    DateUtil.getDaysBackDate(dateNow, 
                            (30 * monthsBack) - i * DateUtil.SECONDS_IN_DAY),
                    scoreType,
                    ScoreValueType.SCORE_SCALE_0_50));
        }
    }
    
    
    private void createScorePercentages(Integer entityID, 
            EntityType entityType, String entityName, ScoreType scoreType, int monthsBack) {
      //Create the five values per date range
        int total = 0;
        for ( int i = 0; i < 5; i++ ) {
            try {
                
            int score = 0;
            if (i == 4)
            {
                score = 100-total;
            }
            else
            {
                score = randomInt(0, 25);
                total += score;
            }
            storeObject(
                new ScoreableEntity(entityID, 
                    entityType, 
                    (i+1) + "",     // name will be 1 to 5 for the 5 different score breakdowns 
                    score, 
                    DateUtil.getDaysBackDate(dateNow, (30 * monthsBack)),
                    scoreType,
                    ScoreValueType.SCORE_PERCENTAGE));
            } catch (Exception e) {}
        }
    }

    private void addDriversToGroup(Integer accountID, Integer groupID, int numDriversInGroup)
    {
        Integer idOffset = accountID * MAX_GROUPS + groupID * MAX_DRIVERS_IN_GROUP;
        
        for (int i = 0; i < numDriversInGroup; i++)
        {
            int id = idOffset+i+1;
            Driver driver = createDriver(id, accountID, groupID, "John", "Driver"+id);
            storeObject(driver);
            Person person = retrieveObject(Person.class, "personID", id);
            addScores(driver.getDriverID(), EntityType.ENTITY_DRIVER, person.getFirst() + person.getLast());
        }
    }

    private Driver createDriver(Integer id, Integer accountID, Integer groupID, String first, String last)
    {
        Person person = retrieveObject(Person.class, "personID", id);
        if (person == null)
        {
            person = new Person();
            storeObject(person);
        }
        person.setEmpid(String.valueOf(id));
        person.setPersonID(id);
        person.setAccountID(accountID);
        person.setGroupID(groupID);
        person.setFirst(first);
        person.setLast(last);
        person.setEmail(first.toLowerCase() + '.' + last.toLowerCase() + "@email.com");
        Driver driver = new Driver();
        driver.setDriverID(id);
        driver.setGroupID(groupID);
        driver.setPersonID(person.getPersonID());
        person.setDriver(driver);
        return driver;
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



    //------------ UTILITY METHODS ----------------
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
    
    
    //------------ LOOKUP METHODS ----------------
    public Map<String, Object> lookup(Class clas, String primaryKey, Object searchValue)
    {
        Object obj = retrieveObject(clas, primaryKey, searchValue);
        if (obj != null)
        {
            return createMapFromObject(obj);
        }
        return null;
    }
    public <T> T lookupObject(Class clas, String primaryKey, Object searchValue)
    {
       return (T)retrieveObject(clas, primaryKey, searchValue);
        
    }

    // get full list of a class
    public <T> List<T> lookupObjectList(Class clas, T object)
    {
        return (List<T>)dataMap.get(clas);
    }

    public <T> List<T> lookupObjectList(Class clas, T object, String key, Object value)
    {
        List<T> objList = (List<T>)dataMap.get(clas);
        if (objList != null && objList.size() > 0)
        {
            List<T> returnList = new ArrayList<T>();
            for (Object obj : objList)
            {
                Object fieldValue = getFieldValue(obj, key);
                if (fieldValue.equals(value))
                {
                    returnList.add((T)obj);
                }
            }
            
            return returnList;
        }
        return null;
    }

    public List<Map<String, Object>> lookupList(Class clas)
    {
        List<Object> objList = dataMap.get(clas);
        if (objList != null && objList.size() > 0)
        {
            List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
            for (Object obj : objList)
            {
                returnList.add(createMapFromObject(obj));
            }
            
            return returnList;
        }
        return null;
    }

    // lookup using multiple key/value pairs ANDed together
    public List<Map<String, Object>> lookupList(Class clas, SearchCriteria searchCriteria)
    {
        List<Object> objList = retrieveObjectList(clas, searchCriteria);
        if (objList != null && objList.size() > 0)
        {
            List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
            for (Object obj : objList)
            {
                returnList.add(createMapFromObject(obj));
            }
            
            return returnList;
        }
        return null;
    }


    public Map<String, Object> lookup(Class clas, SearchCriteria searchCriteria)
    {
        Object obj = retrieveObject(clas, searchCriteria);
        if (obj != null)
        {
            return createMapFromObject(obj);
        }
        return null;
    }

    
    public static <T> Map<String, Object> createMapFromObject(T object)
    {
        Map<String, Object> objMap = new HashMap<String, Object>();

        Class cls = object.getClass();
        BeanInfo beanInfo;
        try
        {
            beanInfo = Introspector.getBeanInfo(object.getClass());
        }
        catch (IntrospectionException e)
        {
            e.printStackTrace();
            return objMap;
        }

        PropertyDescriptor propertyDescriptors[] = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++)
        {
            String key = propertyDescriptors[i].getName();
            if (key.equals("class"))
                continue;

            // if the field represented by key is transient, skip it            
            try
            {
                //getDeclaredFields will not resolve inherited fields. It will throw
                //a NoSuchFieldException.
                if (Modifier.isTransient(cls.getDeclaredField(key).getModifiers()))
                    continue;
            }
            catch (NoSuchFieldException e)
            {
                //if the declared field doesn't exist, we don't care. Move on.
            }

            Method getMethod = propertyDescriptors[i].getReadMethod();
            Object value;
            try
            {
                value = getMethod.invoke(object);
                
                if (value == null)
                    continue;
                
                // backend represents booleans as integers
                if (value instanceof Boolean)
                {
                    value = new Integer((Boolean)value ? 1 : 0);
                }
                // backend represents enums as integers
                else if (value.getClass().isEnum())
                {
                    if (value instanceof BaseEnum)
                    {
                        value = new Integer(((BaseEnum)value).getCode());
                    }
                    else
                    {
                        // we can only handle enums derived from our BaseEnum
                        continue;
                    }
                }
                objMap.put(key, value);
            }
            catch (IllegalAccessException e)
            {
                System.out.println("IllegalAccessException occured while trying to invoke the method " + getMethod.getName());
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                System.out.println("InvocationTargetExcpetion occured while trying to invoke the method " + getMethod.getName());
                e.printStackTrace();
            }
        }

        return objMap;
    }
    
    public <T> void storeObject(T obj)
    {
        List<Object> objList = dataMap.get(obj.getClass());
        if (objList == null)
        {
            objList = new ArrayList<Object>();

            dataMap.put(obj.getClass(), objList);
        }
        objList.add(obj);

    }

    public <T> T retrieveObject(Class<T> clas, String key, Object value)
    {
        List<Object> objList = dataMap.get(clas);
        if (objList == null)
        {
            return null;
        }

        for (Object obj : objList)
        {
            Object fieldValue = getFieldValue(obj, key);
            if (fieldValue.equals(value))
            {
                return (T) obj;
            }
        }

        return null;
    }

    public <T> T retrieveObject(Class<T> clas, SearchCriteria searchCriteria)
    {
        
        Map<String, Object> searchMap = searchCriteria.getCriteriaMap();
        List<Object> objList = dataMap.get(clas);
        if (objList == null)
        {
            return null;
        }
        for (Object obj : objList)
        {
            boolean isMatch = true;
            for (Map.Entry<String,Object> searchItem : searchMap.entrySet())
            {
                Object fieldValue = getFieldValue(obj, searchItem.getKey().toString());
                
                if (searchItem.getValue() instanceof ValueRange)
                {
                    ValueRange valueRange = (ValueRange)searchItem.getValue();
                    if (valueRange.low.compareTo(fieldValue) > 0 ||
                            valueRange.high.compareTo(fieldValue) < 0)
                    {
                        isMatch = false;
                        break;
                    }
                            
                }
                else if (!fieldValue.equals(searchItem.getValue()))
                {
                    isMatch = false;
                    break;
                }
            }
            
            if (isMatch)
            {
                return (T)obj;
            }
        }


        return null;
    }
    public <T> List<T> retrieveObjectList(Class<T> clas, SearchCriteria searchCriteria)
    {
        List<T> returnObjList = new ArrayList<T>();
        
        Map<String, Object> searchMap = searchCriteria.getCriteriaMap();
        List<Object> objList = dataMap.get(clas);
        if (objList == null)
        {
            return null;
        }
        for (Object obj : objList)
        {
            boolean isMatch = true;
            for (Map.Entry<String,Object> searchItem : searchMap.entrySet())
            {
                Object fieldValue = getFieldValue(obj, searchItem.getKey().toString());
                
                if (searchItem.getValue() instanceof ValueRange)
                {
                    ValueRange valueRange = (ValueRange)searchItem.getValue();
                    if (valueRange.low.compareTo(fieldValue) > 0 ||
                            valueRange.high.compareTo(fieldValue) < 0)
                    {
                        isMatch = false;
                        break;
                    }
                            
                }
                else if (!fieldValue.equals(searchItem.getValue()))
                {
                    isMatch = false;
                    break;
                }
            }
            
            if (isMatch)
            {
                returnObjList.add((T)obj);
            }
        }


        return returnObjList;
    }

    public <T> Object getFieldValue(T object, String field)
    {
        Object value = null;

        Class cls = object.getClass();
        BeanInfo beanInfo;
        try
        {
            beanInfo = Introspector.getBeanInfo(object.getClass());
        }
        catch (IntrospectionException e)
        {
            e.printStackTrace();
            return value;
        }

        PropertyDescriptor propertyDescriptors[] = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++)
        {
            String key = propertyDescriptors[i].getName();
            if (!key.equals(field))
                continue;

            Method getMethod = propertyDescriptors[i].getReadMethod();
            try
            {
                value = getMethod.invoke(object);
            }
            catch (IllegalAccessException e)
            {
                System.out.println("IllegalAccessException occured while trying to invoke the method "
                        + getMethod.getName());
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                System.out.println("InvocationTargetExcpetion occured while trying to invoke the method "
                        + getMethod.getName());
                e.printStackTrace();
            }

            break;
        }

        return value;
    }

    //------------ DEBUG METHODS ----------------
    private void dumpGroupHierarchy(Group topGroup, String indent)
    {
        logger.debug(indent + topGroup.getName() + " (ID: " + topGroup.getGroupID() + ")");
        
        List<User> userList = lookupObjectList(User.class, new User(), "groupID", topGroup.getGroupID());
        for (User user : userList)
        {
            logger.debug(indent + " " + user.getUsername() + " (ID: " + user.getUserID() + ", ROLE: " + user.getRole().toString()+ ")");
        }
        
        for (Group group : lookupObjectList(Group.class, new Group()))
        {
            if (group.getParentID().equals(topGroup.getGroupID()))
            {
                //logger.debug(group.getName() + " (ID: " + group.getGroupID() + ")");
                indent += "    ";
                dumpGroupHierarchy(group, indent);
            }
        }
    }


    private void dumpData(Integer accountID)
    {
        Account account = retrieveObject(Account.class, "accountID", accountID);
        dumpObject(account,"");
        
        // get all groups
        List<Group> groups = lookupObjectList(Group.class, new Group());
        for (Group group : groups)
        {
            dumpObject(group, "  ");
            // get all users in group
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("groupID", group.getGroupID());

            List<User> users = retrieveObjectList(User.class, searchCriteria);
            for (User user : users)
            {
                dumpObject(user, "    ");
            }
            List<Driver> drivers = retrieveObjectList(Driver.class, searchCriteria);
            for (Driver driver : drivers)
            {
                dumpObject(driver, "    ");
            }
        }
        
    }

    private <T> void dumpObject(T obj, String indent)
    {
        Map<String, Object> objMap = createMapFromObject(obj);
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

    
}
