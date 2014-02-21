package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Group;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GroupJDBCDAO extends SimpleJdbcDaoSupport implements GroupDAO   {

    private static final long serialVersionUID = 1L;

    private static final String GET_GROUP = " select g.groupID, g.acctID, g.name, g.parentID, g.addrID from groups g ";

    private static final String GET_GROUP_ACCT = " select g.groupID, g.acctID, g.name, g.parentID, g.addrID from groups g ";

    private static final String DEL_GROUP_BY_ID = "DELETE FROM groups WHERE groupID = ?";


    private ParameterizedRowMapper<Group> groupParameterizedRow = new ParameterizedRowMapper<Group>() {
        @Override
        public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
            Group groupItem = new Group();
            groupItem.setGroupID(rs.getInt("groupID"));
            groupItem.setAccountID(rs.getInt("acctID"));
            groupItem.setName(rs.getString("name"));
            groupItem.setParentID(rs.getInt("parentID"));
            groupItem.setAddressID(rs.getInt("addrID"));
            groupItem.setPath(rs.getString("groupPath"));

            return groupItem;
        }
    };

    @Override
    public List<Group> getGroupHierarchy(Integer acctID, Integer groupID) {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("groupID", groupID);
        params.put("acctID", acctID);

        StringBuilder groupSelect = new StringBuilder(GET_GROUP);
        List <Group> groupHierarchy = new ArrayList<Group>();
        List <Group> groupList = getSimpleJdbcTemplate().query(groupSelect.toString(), groupParameterizedRow, params);
        List <Group> allGroups = getGroupsByAcctID(acctID);

        for (Group group : allGroups)
        {
            if (group.getGroupID().equals(groupID))
            {
                groupHierarchy.add(group);
                addChildren(allGroups, groupHierarchy, group.getGroupID());
                break;
            }
        }

        return groupList;
    }

    @Override
    public List<Group> getGroupsByAcctID(Integer acctID) {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("acctID", acctID);

        StringBuilder groupSelectAcct = new StringBuilder(GET_GROUP_ACCT);
        List<Group> groupAcctID = getSimpleJdbcTemplate().query(groupSelectAcct.toString(), groupParameterizedRow, params);

        return groupAcctID;
    }

    @Override
    public Integer delete(Group group) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", group.getGroupID());
        return getSimpleJdbcTemplate().update(DEL_GROUP_BY_ID, params);
    }

    @Override
    public Group findByID(Integer integer) {
        throw new NotImplementedException();
    }

    @Override
    public Integer create(Integer integer, Group entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer update(Group entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer deleteByID(Integer integer) {
        throw new NotImplementedException();
    }

    private void addChildren(List<Group> allGroups , List<Group> groupHierarchy, Integer parentID)
    {
        for (Group group : allGroups)
        {
            if (group.getParentID().equals(parentID))
            {
                groupHierarchy.add(group);
                addChildren(allGroups, groupHierarchy, group.getGroupID());
            }
        }
    }

    public void createTestDevice(int testAccountId, int testGroupId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("groupID", String.valueOf(testGroupId));
        params.put("acctID", String.valueOf(testAccountId));
        getSimpleJdbcTemplate().update("insert into groups (groupID, acctID, name, desc) values (:groupID, :acctID, 'test-name', 'test-desc')", params);
    }

    public void deleteTestDevice(int testGroupId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("groupID", String.valueOf(testGroupId));
        getSimpleJdbcTemplate().update("delete from groups where groupID = :groupID", params);
    }
}
