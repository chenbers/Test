package com.inthinc.pro.reports.hos.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.inthinc.pro.model.Group;


public class GroupHierarchyTest {
    
    private static final Integer ACCOUNT_ID = Integer.valueOf(1);
//    public Group(Integer groupID, Integer accountID, String name, Integer parentID)
    private static Group topGroup = new Group(1, ACCOUNT_ID, "TOP", -1);
    private static Group childGroup1 = new Group(2, ACCOUNT_ID, "CHILD 1", 1);
    private static Group childGroup2 = new Group(3, ACCOUNT_ID, "CHILD 2", 1);
    private static Group childGroup3 = new Group(4, ACCOUNT_ID, "CHILD 3", 1);
    private static Group grandChildGroup1_1 = new Group(5, ACCOUNT_ID, "GRANDCHILD 1", 2);
    private static Group grandChildGroup1_2 = new Group(6, ACCOUNT_ID, "GRANDCHILD 2", 2);
    private static Group grandChildGroup1_3 = new Group(7, ACCOUNT_ID, "GRANDCHILD 3", 2);
    private static Group greatgrandChildGroup1_3_1 = new Group(8, ACCOUNT_ID, "GREATGRANDCHILD 3 1", 7);
    private static List<Group> desendantList = new ArrayList<Group>();
    
    static {
        
        desendantList.add(childGroup1);
        desendantList.add(childGroup2);
        desendantList.add(childGroup3);
        desendantList.add(grandChildGroup1_1);
        desendantList.add(grandChildGroup1_2);
        desendantList.add(grandChildGroup1_3);
        desendantList.add(greatgrandChildGroup1_3_1);

    }
    
    @Test
    public void hierarchy() {
        GroupHierarchyForReports groupHierarchy = new GroupHierarchyForReports(topGroup, desendantList);
        
        List<Group>childList = groupHierarchy.getChildren(topGroup);
        assertEquals("expected 3 children", 3, childList.size());
        
        Group topAncestor = groupHierarchy.getTopAncestor(grandChildGroup1_1, childList);
        assertEquals("topAncestor", childGroup1, topAncestor);
        
        
        topAncestor = groupHierarchy.getTopAncestor(greatgrandChildGroup1_3_1, childList);
        assertEquals("topAncestor", childGroup1, topAncestor);
        
        
    }
    @Test
    public void fullname() {
        GroupHierarchyForReports groupHierarchy = new GroupHierarchyForReports(topGroup, desendantList);
        
        String fullname = groupHierarchy.getFullName(greatgrandChildGroup1_3_1);
        assertEquals("group fullname", "TOP->CHILD 1->GRANDCHILD 3->GREATGRANDCHILD 3 1", fullname);

        fullname = groupHierarchy.getFullName(childGroup2);
        assertEquals("group fullname", "TOP->CHILD 2", fullname);

        fullname = groupHierarchy.getFullName(topGroup);
        assertEquals("group fullname", "TOP", fullname);

    }

}
