package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.aggregation.util.DateUtil;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.*;
import com.mysql.jdbc.Statement;
import org.apache.commons.lang.NotImplementedException;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.opengis.filter.expression.Add;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.sql.Date;
import java.util.*;


public class GroupJDBCDAO extends SimpleJdbcDaoSupport implements GroupDAO {

    private static final long serialVersionUID = 2855781183498326572L;

    private static final String GET_GROUP = "select g.groupID, g.acctID, g.parentID, g.name, g.desc, g.status, g.groupPath, g.addrID, g.addrID2, g.level, g.managerID, g.mapZoom, g.mapLat, g.mapLng, g.zoneRev, g.aggDate, g.newAggDate, g.dotOfficeType  from groups g ";
    private static final String GET_GROUP_ACCT = "select g.groupID, g.acctID, g.parentID, g.name, g.desc, g.status, g.groupPath, g.addrID, g.addrID2, g.level, g.managerID, g.mapZoom, g.mapLat, g.mapLng, g.zoneRev, g.aggDate, g.newAggDate, g.dotOfficeType  from groups g where g.status <> 3 ";
    private static final String FIND_GROUP_BY_ID = "select g.groupID, g.acctID, g.parentID, g.name, g.desc, g.status, g.groupPath, g.addrID, g.addrID2, g.level, g.managerID, g.mapZoom, g.mapLat, g.mapLng, g.zoneRev, g.aggDate, g.newAggDate, g.dotOfficeType  from groups g where g.groupID =:groupID";
    private static final String DEL_GROUP_BY_ID = "DELETE FROM groups WHERE groupID = ?";
    private static final String ADD_NEW_ADDRESS = "INSERT INTO address (acctID, addr1, addr2, city, stateID, zip) VALUES (?, ?, ?, ?, ?, ?);";

    private static final String INSERT_GROUP_ACCOUNT = "insert into groups (groupID, acctID, parentID, name, `desc`, status, groupPath, dotOfficeType , level, managerID, mapZoom, mapLat, mapLng, zoneRev, aggDate, addrID)  values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? );";

    private static final String ADD_ADDRID = "SELECT LAST_INSERT_ID();";

    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    private ParameterizedRowMapper<Group> groupParameterizedRow = new ParameterizedRowMapper<Group>() {
        @Override
        public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
            Group groupItem = new Group();
            groupItem.setGroupID(rs.getInt("groupID"));
            groupItem.setAccountID(rs.getInt("acctID"));
            groupItem.setParentID(getIntOrNullFromRs(rs, "parentID"));
            groupItem.setName(rs.getString("name"));
            groupItem.setDescription(rs.getString("desc"));
            groupItem.setStatus(rs.getObject("status") == null ? null : GroupStatus.valueOf(rs.getInt("status")));
            groupItem.setPath(rs.getString("groupPath"));
            groupItem.setManagerID(getIntOrNullFromRs(rs, "managerID"));
            groupItem.setMapZoom(getIntOrNullFromRs(rs, "mapZoom"));
            groupItem.setZoneRev(getIntOrNullFromRs(rs, "zoneRev"));
            groupItem.setAggDate(rs.getString("aggDate"));
            groupItem.setMapLat(groupItem.getMapLat());
            groupItem.setMapLng(groupItem.getMapLng());
            groupItem.setDotOfficeType(rs.getObject("dotOfficeType") == null ? null : DOTOfficeType.valueOf(rs.getInt("dotOfficeType")));
            groupItem.setAddress(new Address());
            groupItem.setAddressID(getIntOrNullFromRs(rs, "addrID"));
            groupItem.setType(rs.getObject("level") == null? null : GroupType.valueOf(rs.getInt("level")));

            return groupItem;
        }
    };

    

    public Integer getIntOrNullFromRs(ResultSet rs, String key) throws SQLException {
        Object obj = rs.getObject(key);
        if (obj == null)
            return null;
        else return rs.getInt(key);
    }

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
    public Integer create(Integer integer,final Group entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        createAddress(entity.getAddress());
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_GROUP_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
//                groupID, acctID, parentID, name, desc, status, groupPath, addrID, addrID2, level, managerID, mapZoom, mapLat, mapLng, zoneRev, aggDate, dotOfficeType
                ps.setInt(1, entity.getGroupID());
                ps.setInt(2, entity.getAccountID());
                ps.setInt(3, entity.getParentID());
                ps.setString(4, entity.getName());
                ps.setString(5, entity.getDescription());
                ps.setInt(6, entity.getStatus().getCode());

                //TODO see how i can make getpath
                ps.setString(7, entity.getPath());

                //TODO study and modify address & insert in table address with new id
                ps.setInt(8, entity.getDotOfficeType().getCode());

                ps.setInt(9, entity.getType().getCode());
                if(entity.getManagerID()==null){
                    ps.setNull(10, Types.NULL);
                }else{
                    ps.setInt(10, entity.getManagerID());
                }
                ps.setInt(11, entity.getMapZoom());
                ps.setDouble(12, entity.getMapLat());
                ps.setDouble(13, entity.getMapLng());

                //TODO study and modify zoneRev
                if(entity.getZoneRev()==null){
                    ps.setInt(14, 1);
                }else{
                    ps.setInt(14, entity.getZoneRev());
                }
                java.util.Date rightNow = new java.util.Date();
                ps.setDate(15, new Date(rightNow.getTime()));

                if(entity.getAddress()==null){
                    ps.setNull(16, Types.NULL);
                }else{
                    ps.setInt(16, entity.getAddressID());
                }
                logger.debug(ps.toString());
                return ps;

            }
        };

        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
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

    public Integer createAddress(final Address address) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(ADD_NEW_ADDRESS, Statement.RETURN_GENERATED_KEYS);

//                acctID, addr1, addr2, city, stateID, zip
                ps.setInt(1, address.getAccountID());
                if(address.getAddr1()==null){
                    ps.setNull(2, Types.NULL);
                }else{
                    ps.setString(2, address.getAddr1());
                }
                if(address.getAddr2()==null){
                    ps.setNull(3, Types.NULL);
                }else{
                    ps.setString(3, address.getAddr2());
                }
                if(address.getCity()==null){
                    ps.setNull(4, Types.NULL);
                }else{
                    ps.setString(4, address.getCity());
                }

                if(address.getState()==null){
                    ps.setNull(5, Types.NULL);
                }else{
                    ps.setInt(5, address.getState().getStateID());
                }

                if(address.getZip()==null){
                    ps.setNull(6, Types.NULL);
                }else{
                    ps.setString(6, address.getZip());
                }

                return ps;
            }
        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }

}

