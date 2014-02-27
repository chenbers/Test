package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.DOTOfficeType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupStatus;
import com.inthinc.pro.model.LatLng;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GroupJDBCDAO extends SimpleJdbcDaoSupport implements GroupDAO {

    private static final long serialVersionUID = 2855781183498326572L;

    private static final String GET_GROUP = "select g.groupID, g.acctID, g.parentID, g.name, g.desc, g.status, g.groupPath, g.addrID, g.addrID2, g.level, g.managerID, g.mapZoom, g.mapLat, g.mapLng, g.zoneRev, g.aggDate, g.newAggDate, g.dotOfficeType  from groups g ";
    private static final String GET_GROUP_ACCT = "select g.groupID, g.acctID, g.parentID, g.name, g.desc, g.status, g.groupPath, g.addrID, g.addrID2, g.level, g.managerID, g.mapZoom, g.mapLat, g.mapLng, g.zoneRev, g.aggDate, g.newAggDate, g.dotOfficeType  from groups g where g.status <> 3 ";

    private static final String FIND_GROUP_BY_ID = "select g.groupID, g.acctID, g.parentID, g.name, g.desc, g.status, g.groupPath, g.addrID, g.addrID2, g.level, g.managerID, g.mapZoom, g.mapLat, g.mapLng, g.zoneRev, g.aggDate, g.newAggDate, g.dotOfficeType  from groups g where g.groupID =:groupID";

    private static final String DEL_GROUP_BY_ID = "DELETE FROM groups WHERE groupID = ?";


    private ParameterizedRowMapper<Group> groupParameterizedRow = new ParameterizedRowMapper<Group>() {
        @Override
        public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
            Group groupItem = new Group();
            groupItem.setGroupID(rs.getInt("groupID"));
            groupItem.setAccountID(rs.getInt("acctID"));
            groupItem.setParentID(rs.getInt("parentID"));
            groupItem.setName(rs.getString("name"));
            groupItem.setDescription(rs.getString("desc"));
            groupItem.setStatus(rs.getObject("status") == null ? null : GroupStatus.valueOf(rs.getInt("status")));
            groupItem.setPath(rs.getString("groupPath"));
            groupItem.setManagerID(rs.getInt("managerID"));
            groupItem.setMapZoom(rs.getInt("mapZoom"));
            groupItem.setZoneRev(rs.getInt("zoneRev"));
            groupItem.setAggDate(rs.getString("aggDate"));
            groupItem.setMapLat(groupItem.getMapLat());
            groupItem.setMapLng(groupItem.getMapLng());
            groupItem.setDotOfficeType(rs.getObject("dotOfficeType") == null ? null : DOTOfficeType.valueOf(rs.getInt("dotOfficeType")));
            groupItem.setAddressID(rs.getInt("addrID"));
            groupItem.setPath(rs.getString("groupPath"));

            return groupItem;
        }
    };

    @Override
    public List<Group> getGroupHierarchy(Integer acctID, Integer groupID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("acctID", acctID);
        params.put("groupID", groupID);

        StringBuilder groupSelect = new StringBuilder(GET_GROUP);
        List <Group> allGroups = getGroupsByAcctID(acctID);
        List <Group> groupHierarchy = new ArrayList<Group>();

        for (Group group : allGroups)
        {
            if (group.getGroupID().equals(groupID))
            {
                groupHierarchy.add(group);
                addChildren(allGroups, groupHierarchy, group.getGroupID());
                break;
            }
        }
        return groupHierarchy;
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
    public Group findByID(Integer groupID) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("groupID", groupID);
        StringBuilder groupSelectAcct = new StringBuilder(FIND_GROUP_BY_ID);
        return getSimpleJdbcTemplate().queryForObject(groupSelectAcct.toString(), groupParameterizedRow, args );
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
    public Integer deleteByID(Integer groupID) {
        return getJdbcTemplate().update(DEL_GROUP_BY_ID, new Object[] { groupID });
    }

    public void createTestDevice(int testAccountId, int testGroupId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("groupID", String.valueOf(testGroupId));
        params.put("acctID", String.valueOf(testAccountId));
        getSimpleJdbcTemplate().update("insert into groups (groupID, acctID, name, `desc`, parentID, status, groupPath, addrID, mapZoom, zoneRev, mapLat, mapLng) values (:groupID, :acctID, 'test-name', 'test-desc', 4, 1, '/0/4/5/', 972, 17, 2, '28.065', '-82.3664')", params);
    }

    public void deleteTestDevice(int testGroupId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("groupID", String.valueOf(testGroupId));
        getSimpleJdbcTemplate().update("delete from groups where groupID = :groupID", params);
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
}
