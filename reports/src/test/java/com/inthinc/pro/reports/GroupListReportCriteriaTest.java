package com.inthinc.pro.reports;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import org.junit.Test;

import java.util.Arrays;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link com.inthinc.pro.reports.GroupListReportCriteria}.
 */
public class GroupListReportCriteriaTest {

    @Test
    public void getShortGroupTest(){
        Group group1 = new Group();
        group1.setName("name1");
        group1.setGroupID(1);

        Group group2 = new Group();
        group2.setName("name2");
        group2.setGroupID(2);

        GroupHierarchy groupHierarchy = new GroupHierarchy();
        groupHierarchy.setGroupList(Arrays.asList(group1, group2));

        GroupListReportCriteria groupListReportCriteria = new GroupListReportCriteria(ReportType.BACKING_REPORT, Locale.CANADA);
        String result = groupListReportCriteria.getShortGroupName(groupHierarchy);

        assertEquals(result, "name1");
    }
}
