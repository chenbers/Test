package com.inthinc.pro.reports.hos.testData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.Interval;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;

public class BaseDataSet {
    
    public Group topGroup;
    public List<Group> groupList = new ArrayList<Group>();
    public Interval interval;
    public int numDays;

    
    Map<String, Integer> groupIDMap = new HashMap<String, Integer>();
    
    
    protected void readInGroupHierarchy(String filename, String topGroupID) {
        BufferedReader in;
        try {
            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
            if (stream != null) {
                in = new BufferedReader(new InputStreamReader(stream));
                String str;
                while ((str = in.readLine()) != null) {
                    String values[] = str.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
                    for (int i = 0; i < values.length; i++)
                        if (values[i].startsWith("\"") && values[i].endsWith("\"")) {
                            values[i] = values[i].substring(1, values[i].length() - 1);
                        }
                    String groupId = values[0];
                    String groupName = values[1];
                    if (groupId.equals(topGroupID)) {
                        topGroup = new Group();
                        topGroup.setGroupID(calcGroupID(groupIDMap, topGroupID));
                        topGroup.setParentID(-1);
                        topGroup.setName(groupName);
                        topGroup.setAddress(MockData.createMockAddress(groupName));
                    } else {
                        Group group = new Group();
                        group.setGroupID(calcGroupID(groupIDMap, groupId));
                        group.setName(groupName);
                        String parentGroupID = groupId.substring(0, groupId.length() - 1);
                        group.setParentID(calcGroupID(groupIDMap, parentGroupID));
                        group.setAddress(MockData.createMockAddress(groupName));
                        groupList.add(group);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected Integer calcGroupID(Map<String, Integer> groupIdMap, String gainGroupID) {
        Integer groupID = groupIDMap.get(gainGroupID);
        if (groupID == null) {
            groupID = groupIDMap.size()+1;
            groupIDMap.put(gainGroupID, groupID);
        }
        return groupID;
    }
    public GroupHierarchy getGroupHierarchy()
    {
        List<Group> hierarchyGroupList = new ArrayList<Group>();
        hierarchyGroupList.add(topGroup);
        for (Group group : groupList)
            hierarchyGroupList.add(group);
        
        return new GroupHierarchy(hierarchyGroupList);
        
    }

}
