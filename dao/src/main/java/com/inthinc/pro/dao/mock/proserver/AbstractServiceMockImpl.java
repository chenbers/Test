package com.inthinc.pro.dao.mock.proserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.data.SearchCriteria;
import com.inthinc.pro.dao.mock.data.TempConversionUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;

public abstract class AbstractServiceMockImpl
{

    public AbstractServiceMockImpl()
    {
        super();
    }

    protected void addChildren(List<Group> hierarchyGroups, List<Group> allGroups, Integer groupID)
    {
        for (Group group : allGroups)
        {
            if (group.getParentID().equals(groupID))
            {
                hierarchyGroups.add(group);
                addChildren(hierarchyGroups, allGroups, group.getGroupID());
            }
        }
    
    }
    protected List<Group> getGroupHierarchy(Group topGroup)
    {
        List<Group> hierarchyGroups = new ArrayList<Group>();
        hierarchyGroups.add(topGroup);

        List<Group> allGroups = MockData.getInstance().lookupObjectList(Group.class, topGroup);
        addChildren(hierarchyGroups, allGroups, topGroup.getGroupID());
        
        return hierarchyGroups;
        
    }

    protected List<Driver> getAllDriversInGroup(Group topGroup)
    {
        List<Group> hierarchyGroups = getGroupHierarchy(topGroup);
    
        List<Driver> returnDriverList = new ArrayList<Driver>();
        for (Group group : hierarchyGroups)
        {
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("groupID", group.getGroupID());
    
            List<Driver> driverList = MockData.getInstance().retrieveObjectList(Driver.class, searchCriteria);
            returnDriverList.addAll(driverList);
    
        }
    
        return returnDriverList;
    }
    
    protected void computePercentages(Integer groupID, Integer startDate, Integer scoreType, List<ScoreableEntity> scoreList, int[] totals, int totalDrivers)
    {
        int percentTotal = 0;
        for (int i = 0; i < 5; i++)
        {
            int percent = 0;
            if (i < 4)
            {
                percent = Math.round((float) ((float) totals[i] * 100f) / (float) totalDrivers);
                percentTotal += percent;
            }
            else
            {
                percent = 100 - percentTotal;
            }
            ScoreableEntity scoreableEntity = new ScoreableEntity(groupID, EntityType.ENTITY_GROUP, 
                        (i + 1) + "", // name will be 1 to 5 for the 5 different score breakdowns
                    new Integer(percent), startDate, ScoreType.valueOf(scoreType));
            scoreList.add(scoreableEntity);
        }
    }

    protected int[] getScoreCategoryTotals(int totals[], Integer startDate, Integer endDate, ScoreType scoreType, List<Driver> allDriversInGroup)
    {
        if (scoreType.getSubTypes() != null)
        {
            for (ScoreType subType : scoreType.getSubTypes())
            {
                if (subType.equals(scoreType))
                {
                    continue;
                }
                totals = getScoreCategoryTotals(totals, startDate, endDate, subType, allDriversInGroup);
            }
            
        }
        else
        {
            for (Driver driver : allDriversInGroup)
            {
                SearchCriteria searchCriteria = new SearchCriteria();
                searchCriteria.addKeyValue("entityID", driver.getDriverID());
                searchCriteria.addKeyValue("entityType", EntityType.ENTITY_DRIVER);
                searchCriteria.addKeyValue("scoreType", scoreType);
                searchCriteria.addKeyValueRange("date", startDate, endDate);
                List<ScoreableEntity> allScores = MockData.getInstance().retrieveObjectList(ScoreableEntity.class, searchCriteria);
                Map<String, Object> scoreMap = getAverageScore(startDate, allScores);
    
                Integer score = (Integer) scoreMap.get("score");
                int idx = (score - 1) / 10;
                totals[idx]++;
            }
        }
        return totals;
    }

    protected Map<String, Object> getAverageScore(Integer startDate, List<ScoreableEntity> allScores)
    {
        int total = 0;
        ScoreableEntity firstEntity = allScores.get(0);
        ScoreableEntity returnEntity = new ScoreableEntity(firstEntity.getEntityID(), EntityType.ENTITY_GROUP, firstEntity.getIdentifier(), 0, startDate, firstEntity
                .getScoreType());
        for (ScoreableEntity entity : allScores)
        {
            total += entity.getScore();
        }
        returnEntity.setScore(total / allScores.size());

        return TempConversionUtil.createMapFromObject(returnEntity);
    }




}