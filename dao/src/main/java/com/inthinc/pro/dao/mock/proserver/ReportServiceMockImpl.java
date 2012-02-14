package com.inthinc.pro.dao.mock.proserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.data.MockQuintileMap;
import com.inthinc.pro.dao.mock.data.SearchCriteria;
import com.inthinc.pro.dao.mock.data.TempConversionUtil;
import com.inthinc.pro.model.DVQMap;
import com.inthinc.pro.model.DriveQMap;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.GQMap;
import com.inthinc.pro.model.GQVMap;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Vehicle;

public class ReportServiceMockImpl extends AbstractServiceMockImpl implements ReportService
{
    //Cutover to real issues:
    //
    //  * Backend expects integer indicator for duration,
    //      we currently use start/end date.
    //  * Use current backend interface and embed relevant
    //      Davids methods for scores in them.
    //  * Need easy way to pick out of driveq what we want,
    //      some utility "thing".
    //  * For performance, maybe session beans for caching
    //      previously snagged data, used down in the 
    //      data access methods. A quick check there and 
    //      then on to the data store.  The data is static.
    
    private static final Logger logger = Logger.getLogger(ReportServiceMockImpl.class);
    
//Methods currently supported on REAL back end   
    
    
    @Override
    public Map<String, Object> getDScoreByDT(Integer driverID, Integer duration)
    {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("driver:driverID", driverID);

        DVQMap dvq = MockData.getInstance().retrieveObject(DVQMap.class, searchCriteria);
        if (dvq == null)
            throw new EmptyResultSetException("No score for driver: " + driverID, "getDScoreByDT", 0);
        return TempConversionUtil.createMapFromObject(dvq.getDriveQ());
    }
    
    @Override
    public List<Map<String, Object>> getDTrendByDTC(Integer driverID, Integer duration, Integer count)
    {
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("driver:driverID", driverID);

        List<DVQMap> dvqList = MockData.getInstance().retrieveObjectList(DVQMap.class, searchCriteria);

        if (dvqList == null)
            throw new EmptyResultSetException("No score for driver: " + driverID, "getDTrendByDTC", 0);
        
        int start = dvqList.size() - count;
        int i = 0;
        for (DVQMap dvq : dvqList)
        {    
            if (i++ < start)
                continue;
            returnList.add(TempConversionUtil.createMapFromObject(dvq.getDriveQ()));
        }
        
        return returnList;
    } 
    @Override
    public Map<String, Object> getVScoreByVT(Integer vehicleID, Integer duration)
    {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("vehicle:vehicleID", vehicleID);

        DVQMap dvq = MockData.getInstance().retrieveObject(DVQMap.class, searchCriteria);
        if (dvq == null)
            throw new EmptyResultSetException("No score for vehicle: " + vehicleID, "getVScoreByVT", 0);
        return TempConversionUtil.createMapFromObject(dvq.getDriveQ());
    }
    
    @Override
    public List<Map<String, Object>> getVTrendByVTC(Integer vehicleID, Integer duration, Integer count)
    {
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("vehicle:vehicleID", vehicleID);

        List<DVQMap> dvqList = MockData.getInstance().retrieveObjectList(DVQMap.class, searchCriteria);

        if (dvqList == null)
            throw new EmptyResultSetException("No score for vehicle: " + vehicleID, "getVTrendByVTC", 0);
        
        int start = dvqList.size() - count;
        int i = 0;
        for (DVQMap dvq : dvqList)
        {    
            if (i++ < start)
                continue;
            returnList.add(TempConversionUtil.createMapFromObject(dvq.getDriveQ()));
        }
        
        return returnList;
    }  
    
    @Override
    public Map<String, Object> getGDScoreByGT(Integer groupID, Integer duration)
    {
        Group topGroup = MockData.getInstance().lookupObject(Group.class, "groupID", groupID);
        List<Group> groupList = getGroupHierarchy(topGroup);
        
        for (Group group : groupList)
        {
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("driver:groupID", group.getGroupID());
    
            // get list of drivers that are in the specified group
            List<DVQMap> groupDVQList = MockData.getInstance().retrieveObjectList(DVQMap.class, searchCriteria);
            if (groupDVQList == null)
            {
                continue;
            }
            for (DVQMap dvq : groupDVQList)
            {
                // TODO:
                // for now just return the scores for 1st driver in list, should actually average all scores 
                return TempConversionUtil.createMapFromObject(dvq.getDriveQ());
            }
        }
        throw new EmptyResultSetException("No scores for group: " + groupID, "getGDScoreByGT", 0);
    }  
    
    @Override
    public List<Map<String, Object>> getGDTrendByGTC(Integer groupID, Integer duration, Integer count)
    {
        // TODO Auto-generated method stub
        return null;
    } 
    
    public List<Map<String, Object>> getDVScoresByGT(Integer groupID, Integer duration)
    {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("groupID", groupID);

        // get list of drivers that are in the specified group
        List<Map<String, Object>> entityList = MockData.getInstance().lookupList(Driver.class, searchCriteria);

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

        if (entityList == null)
        {
            return returnList;
        }
        for (Map<String, Object> driverMap : entityList)
        {
            searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("driver:driverID", driverMap.get("driverID"));
    
            DVQMap dvq = MockData.getInstance().retrieveObject(DVQMap.class, searchCriteria);
            returnList.add(TempConversionUtil.createMapFromObject(dvq));
        }
        return returnList;
    } 
    
    public List<Map<String, Object>> getVDScoresByGT(Integer groupID, Integer duration)
    {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("groupID", groupID);

        // get list of vehicles that are in the specified group
        List<Map<String, Object>> entityList = MockData.getInstance().lookupList(Vehicle.class, searchCriteria);

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

        if (entityList == null)
        {
            return returnList;
        }
        for (Map<String, Object> vehicleMap : entityList)
        {
            searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("vehicle:vehicleID", vehicleMap.get("vehicleID"));
    
            DVQMap dvq = MockData.getInstance().retrieveObject(DVQMap.class, searchCriteria);
            returnList.add(TempConversionUtil.createMapFromObject(dvq));
        }
        return returnList;
    } 
        
    public List<Map<String, Object>>  getSDScoresByGT(Integer groupID, Integer duration)
    {
        Group topGroup = MockData.getInstance().lookupObject(Group.class, "groupID", groupID);
        List<Group> groupList = getGroupHierarchy(topGroup);
        
        List<Map<String, Object>> gqMapList = new ArrayList<Map<String, Object>>();
        
        for (Group group : groupList)
        {
            if (group.getParentID().equals(groupID))
            {
                SearchCriteria searchCriteria = new SearchCriteria();
                searchCriteria.addKeyValue("group:groupID", group.getGroupID());
        
                // get list of drivers that are in the specified group
                GQMap gqMap = MockData.getInstance().retrieveObject(GQMap.class, searchCriteria);
                if (gqMap == null)
                {
                    continue;
                }
                gqMapList.add(TempConversionUtil.createMapFromObject(gqMap));
            }
        }
        return gqMapList;
    } 
    
    public List<Map<String, Object>>  getSDTrendsByGTC(Integer groupID, Integer duration, Integer metric)
    {
        Group topGroup = MockData.getInstance().lookupObject(Group.class, "groupID", groupID);
        List<Group> groupList = getGroupHierarchy(topGroup);
        
        List<Map<String, Object>> gqMapList = new ArrayList<Map<String, Object>>();
        
        for (Group group : groupList)
        {
            if (group.getParentID().equals(groupID))
            {
                SearchCriteria searchCriteria = new SearchCriteria();
                searchCriteria.addKeyValue("group:groupID", group.getGroupID());
        
                // get list of drivers that are in the specified group
                GQMap gqMap = MockData.getInstance().retrieveObject(GQMap.class, searchCriteria);
                if (gqMap == null)
                {
                    continue;
                }
                GQVMap gqvMap = new GQVMap();
                gqvMap.setGroup(group);
                List<DriveQMap> driveQVList = new ArrayList<DriveQMap>();
                for (int i = 0; i < 3; i++)
                    driveQVList.add(gqMap.getDriveQ());
                gqvMap.setDriveQV(driveQVList);
                gqMapList.add(TempConversionUtil.createMapFromObject(gqvMap));
            }
        }
        return gqMapList;
    }  
    
    
    public Map<String, Object> getDPctByGT(Integer groupID, Integer duration, Integer metric)
    {
        SearchCriteria searchCriteria;
        searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("groupID", groupID);
        searchCriteria.addKeyValue("driveQMetric", metric);
        searchCriteria.addKeyValue("duration", duration);

        MockQuintileMap mockQuintileMap = MockData.getInstance().retrieveObject(MockQuintileMap.class, searchCriteria);
        if (mockQuintileMap != null)
        {
            return TempConversionUtil.createMapFromObject(mockQuintileMap.getQuintileMap(), false);
        }
        throw new EmptyResultSetException("No scores for group: " + groupID, "getDPctByGT", 0);
    }
                             
    public Map<String, Object> getGDScoreByGSE(Integer groupID, Long start, Long end)
    {
        return null;
    }

//    public Map<String, Object> getGVScoreByGSE(Integer groupID, Long start, Long end)
//    {
//        return null;
//    }
    

    public List<Map<String, Object>> getDVScoresByGSE(Integer groupID, Long start, Long end)
    {
        return null;
    }
    
    public List<Map<String, Object>> getVDScoresByGSE(Integer groupID, Long start, Long end)
    {
        return null;
    }
}
