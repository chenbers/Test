package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.*;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.DOTOfficeType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupStatus;
import com.inthinc.pro.model.GroupType;
import com.mysql.jdbc.Statement;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GroupJDBCDAO extends SimpleJdbcDaoSupport implements GroupDAO {

    private static final long serialVersionUID = 2855781183498326572L;

    private static final String GET_GROUP = "select * from groups g ";
    private static final String GET_GROUP_ACCT = "select *  from groups g LEFT OUTER JOIN  address a ON g.addrID=a.addrID LEFT OUTER JOIN state s ON a.stateID=s.stateID where g.status <> 3 and g.acctID = :acctID";
    private static final String FIND_GROUP_BY_ID = "select *  from groups g LEFT OUTER JOIN  address a ON g.addrID=a.addrID LEFT OUTER JOIN state s ON a.stateID=s.stateID where g.groupID =:groupID";

    private static final String DEL_GROUP_BY_ID = "DELETE FROM groups WHERE groupID = ?";
    private static final String ADD_NEW_ADDRESS = "INSERT INTO address (acctID, addr1, addr2, city, stateID, zip) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String DEL_ADDRESS_BY_ID = "DELETE FROM address WHERE addrID = ?";

    private static final String UPDATE_ADDRESS = "UPDATE address set addr1=?, addr2=?, city=?, stateID=?, zip=? where addrID=?";

    private static final String INSERT_GROUP_ACCOUNT = "insert into groups (acctID, parentID, name, `desc`, status, groupPath, dotOfficeType , level, managerID, mapZoom, mapLat, mapLng, zoneRev, aggDate, addrID)  values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_GROUP_ACCOUNT = "UPDATE groups " +
            " set " +
            " acctID = ?," +
            " parentID = ?," +
            " name = ?," +
            " `desc` = ?," +
            " status = ?," +
            " groupPath = ?," +
            " dotOfficeType = ?," +
            " level = ?," +
            " managerID = ?," +
            " mapZoom = ?," +
            " mapLat = ?," +
            " mapLng = ?," +
            " zoneRev = ?," +
            " aggDate = ?," +
            " addrID = ?" +
            " where groupID = ?";


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
            groupItem.setType(rs.getObject("level") == null ? null : GroupType.valueOf(rs.getInt("level")));

//            show state
            State state = new State();
            state.setStateID(getIntOrNullFromRs(rs, "s.stateID"));
            state.setAbbrev(getStrOrNullFromRs(rs,"s.abbrev"));
            state.setName(getStrOrNullFromRs(rs, "s.name"));

            //show address
            Address address = new Address();
            address.setAddrID(getIntOrNullFromRs(rs, "a.addrID"));
            address.setAddr1(getStrOrNullFromRs(rs,"a.addr1"));
            address.setAddr2(getStrOrNullFromRs(rs,"a.addr2"));
            address.setCity(getStrOrNullFromRs(rs,"a.city"));
            address.setZip(getStrOrNullFromRs(rs,"a.zip"));
            address.setState(state);

            groupItem.setAddress(address);

            groupItem.setAddressID(address.getAddrID());
            groupItem.setType(rs.getObject("level") == null ? null : GroupType.valueOf(rs.getInt("level")));

            return groupItem;
        }
    };

    private ParameterizedRowMapper<Group> groupPathParameterizedRow = new ParameterizedRowMapper<Group>() {
        @Override
        public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
            Group groupItem = new Group();
            groupItem.setGroupID(rs.getInt("groupID"));
            groupItem.setParentID(getIntOrNullFromRs(rs, "parentID"));

            return groupItem;
        }
    };


    public Integer getIntOrNullFromRs(ResultSet rs, String key) throws SQLException {
        Object obj = rs.getObject(key);
        if (obj == null)
            return null;
        else return rs.getInt(key);
    }

    public String getStrOrNullFromRs(ResultSet rs, String key) throws SQLException {
        Object obj = rs.getObject(key);
        if (obj == null)
            return null;
        else return rs.getString(key);
    }

    @Override
    public List<Group> getGroupHierarchy(Integer acctID, Integer groupID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("acctID", acctID);
        params.put("groupID", groupID);

        List<Group> allGroups = getGroupsByAcctID(acctID);
        List<Group> groupHierarchy = new ArrayList<Group>();

        for (Group group : allGroups) {
            if (group.getGroupID().equals(groupID)) {
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
        deleteAddressByID(group.getAddressID());
        return deleteByID(group.getGroupID());
    }

    @Override
    public Group findByID(Integer groupID) {
        try {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("groupID", groupID);
        StringBuilder groupSelectAcct = new StringBuilder(FIND_GROUP_BY_ID);
        return getSimpleJdbcTemplate().queryForObject(groupSelectAcct.toString(), groupParameterizedRow, args);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Integer create(Integer id, final Group entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        createAddressIfNeeded(entity.getAccountID(), entity.getAddress());
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_GROUP_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, entity.getAccountID());

                if (entity.getParentID().intValue() == 0 || entity.getParentID().equals(null)) {
                    ps.setNull(2, Types.NULL);
                } else {
                    ps.setInt(2, entity.getParentID());
                }

                ps.setString(3, entity.getName() == null ? "" : entity.getName().trim());

                if (entity.getDescription().equalsIgnoreCase("") || entity.getDescription().isEmpty()) {
                    ps.setNull(4, Types.NULL);
                } else {
                    ps.setString(4, entity.getDescription());
                }

                if (entity.getStatus() == null || entity.getStatus().getCode() == null) {
                    ps.setNull(5, Types.NULL);
                } else {
                    ps.setInt(5, entity.getStatus().getCode());
                }

                ps.setString(6, entity.getPath());

                if (entity.getDotOfficeType() == null || entity.getDotOfficeType().getCode() == null) {
                    ps.setNull(7, Types.NULL);
                } else {
                    ps.setInt(7, entity.getDotOfficeType().getCode());
                }

                if (entity.getType() == null || entity.getType().getCode() == null) {
                    ps.setNull(8, Types.NULL);
                } else {
                    ps.setInt(8, entity.getType().getCode());
                }

                if (entity.getManagerID() == null) {
                    ps.setNull(9, Types.NULL);
                } else {
                    ps.setInt(9, entity.getManagerID());
                }

                if (entity.getMapZoom().intValue() == 0 || entity.getMapZoom().equals(null)) {
                    ps.setNull(10, Types.NULL);
                } else {
                    ps.setInt(10, entity.getMapZoom());
                }

                if (entity.getMapLat().intValue() == 0 || entity.getMapLat().equals(null)) {
                    ps.setNull(10, Types.NULL);
                } else {
                    ps.setDouble(11, entity.getMapLat());
                }

                if (entity.getMapLng().intValue() == 0 || entity.getMapLng().equals(null)) {
                    ps.setNull(10, Types.NULL);
                } else {
                    ps.setDouble(12, entity.getMapLng());
                }
                if (entity.getZoneRev() == null) {
                    ps.setInt(13, Types.NULL);
                } else {
                    ps.setInt(13, entity.getZoneRev());
                }

                if (entity.getAggDate() == null) {
                    ps.setNull(14, Types.NULL);
                } else {
                    ps.setDate(14, new java.sql.Date(dateFormatter.parseDateTime(entity.getAggDate()).getMillis()));
                }

                if (entity.getAddress() == null) {
                    ps.setNull(15, Types.NULL);
                } else {
                    ps.setInt(15, entity.getAddress().getAddrID());
                }
                logger.debug(ps.toString());
                return ps;
            }
        };

        jdbcTemplate.update(psc, keyHolder);
        updateGroupPathById(keyHolder.getKey().intValue());
        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer update(final Group entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                if (entity.getGroupID() == null)
                    throw new SQLException("Cannot update group with null id.");

                PreparedStatement ps = con.prepareStatement(UPDATE_GROUP_ACCOUNT);
                ps.setInt(1, entity.getAccountID());
                ps.setInt(2, entity.getParentID());
                ps.setString(3, entity.getName() == null ? "" : entity.getName().trim());

                if (entity.getDescription().equalsIgnoreCase("") || entity.getDescription().isEmpty()) {
                    ps.setNull(4, Types.NULL);
                } else {
                    ps.setString(4, entity.getDescription());
                }

                if (entity.getStatus() == null || entity.getStatus().getCode() == null) {
                    ps.setNull(5, Types.NULL);
                } else {
                    ps.setInt(5, entity.getStatus().getCode());
                }

                ps.setString(6, entity.getPath());

                if (entity.getDotOfficeType() == null || entity.getDotOfficeType().getCode() == null) {
                    ps.setNull(7, Types.NULL);
                } else {
                    ps.setInt(7, entity.getDotOfficeType().getCode());
                }

                if (entity.getType() == null || entity.getType().getCode() == null) {
                    ps.setNull(8, Types.NULL);
                } else {
                    ps.setInt(8, entity.getType().getCode());
                }

                if (entity.getManagerID() == null) {
                    ps.setNull(9, Types.NULL);
                } else {
                    ps.setInt(9, entity.getManagerID());
                }
                if (entity.getMapZoom().intValue() == 0 || entity.getMapZoom().equals(null)) {
                    ps.setNull(10, Types.NULL);
                } else {
                    ps.setInt(10, entity.getMapZoom());
                }

                if (entity.getMapLat().intValue() == 0 || entity.getMapLat().equals(null)) {
                    ps.setNull(11, Types.NULL);
                } else {
                    ps.setDouble(11, entity.getMapLat());
                }

                if (entity.getMapLng().intValue() == 0 || entity.getMapLng().equals(null)) {
                    ps.setNull(12, Types.NULL);
                } else {
                    ps.setDouble(12, entity.getMapLng());
                }

                if (entity.getZoneRev() == null) {
                    ps.setInt(13, Types.NULL);
                } else {
                    ps.setInt(13, entity.getZoneRev());
                }

                if (entity.getAggDate() == null) {
                    ps.setNull(14, Types.NULL);
                } else {
                    ps.setDate(14, new java.sql.Date(dateFormatter.parseDateTime(entity.getAggDate()).getMillis()));
                }

                if (entity.getAddress() == null) {
                    ps.setNull(15, Types.NULL);
                } else {
                    ps.setInt(15, entity.getAddress().getAddrID());
                }

                ps.setInt(16, entity.getGroupID());

                logger.debug(ps.toString());
                return ps;
            }
        };

        jdbcTemplate.update(psc);
        updateGroupPathById(entity.getGroupID());
        updateAddressIfNeeded(entity.getAddress());

        return entity.getGroupID();
    }

    @Override
    public Integer deleteByID(Integer groupID) {
        return getJdbcTemplate().update(DEL_GROUP_BY_ID, new Object[]{groupID});
    }

    private void addChildren(List<Group> allGroups, List<Group> groupHierarchy, Integer parentID) {
        for (Group group : allGroups) {
            if (group.getParentID().equals(parentID)) {
                groupHierarchy.add(group);
                addChildren(allGroups, groupHierarchy, group.getGroupID());
            }
        }
    }

    public Address createAddressIfNeeded(Integer accountID, final Address address) {

        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(ADD_NEW_ADDRESS, Statement.RETURN_GENERATED_KEYS);

//                acctID, addr1, addr2, city, stateID, zip
                ps.setInt(1, address.getAccountID());
                if (address.getAddr1() == null) {
                    ps.setNull(2, Types.NULL);
                } else {
                    ps.setString(2, address.getAddr1());
                }
                if (address.getAddr2() == null) {
                    ps.setNull(3, Types.NULL);
                } else {
                    ps.setString(3, address.getAddr2());
                }
                if (address.getCity() == null) {
                    ps.setNull(4, Types.NULL);
                } else {
                    ps.setString(4, address.getCity());
                }

                if (address.getState() == null) {
                    ps.setNull(5, Types.NULL);
                } else {
                    ps.setInt(5, address.getState().getStateID());
                }

                if (address.getZip() == null) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setString(6, address.getZip());
                }

                return ps;
            }
        };
        jdbcTemplate.update(psc, keyHolder);
        address.setAddrID(keyHolder.getKey().intValue());
        return address;
    }

    public Address updateAddressIfNeeded(final Address address) {

        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_ADDRESS, Statement.RETURN_GENERATED_KEYS);

                if (address.getAddr1() == null) {
                    ps.setNull(1, Types.NULL);
                } else {
                    ps.setString(1, address.getAddr1());
                }
                if (address.getAddr2() == null) {
                    ps.setNull(2, Types.NULL);
                } else {
                    ps.setString(2, address.getAddr2());
                }
                if (address.getCity() == null) {
                    ps.setNull(3, Types.NULL);
                } else {
                    ps.setString(3, address.getCity());
                }

                if (address.getState() == null) {
                    ps.setNull(4, Types.NULL);
                } else {
                    ps.setInt(4, address.getState().getStateID());
                }

                if (address.getZip() == null) {
                    ps.setNull(5, Types.NULL);
                } else {
                    ps.setString(5, address.getZip());
                }

               ps.setInt(6, address.getAddrID());

                return ps;
            }
        };
        jdbcTemplate.update(psc, keyHolder);
        return address;

    }


    /**
     * Calculates the group path by id (recursive).
     *
     * @param groupID group id
     * @return path
     */
    public String determineGroupPathById(Integer groupID) {
        if (groupID.equals(0)) {
            return "/0/";
        } else {
            Group group = findFastById(groupID);
            return determineGroupPathById(group.getParentID()) + groupID + "/";
        }
    }

    /**
     * Updates a group's path by id.
     *
     * @param groupID group id
     */
    public void updateGroupPathById(Integer groupID) {
        try {
            String path = determineGroupPathById(groupID);
            updateGroupPath(groupID, path);
        } catch (Throwable t) {
            logger.error("Unable to update group path for id: " + groupID);
        }
    }

    /**
     * Finds a group by id but only binds the group id and parent id.
     * It's used to speed up {@link #updateGroupPathById}
     *
     * @param groupID group id
     * @return group with id and parent id bound
     */
    public Group findFastById(Integer groupID) {
        String curGroupsSql = "select groupID, parentID from siloDB.groups where groupID = :groupID";
        Map<String, String> params = new HashMap<String, String>();
        params.put("groupID", String.valueOf(groupID));

        return getSimpleJdbcTemplate().queryForObject(curGroupsSql, groupPathParameterizedRow, params);
    }

    /**
     * Updates a group's path by group id.
     *
     * @param groupID group id
     * @param path    new path
     */
    public void updateGroupPath(final Integer groupID, final String path) {
        final String updateGroupPath = "update groups set groupPath = :groupPath where groupID = :groupID and(groupPath != :groupPath OR groupPath is null)";

        Map<String, String> params = new HashMap<String, String>();
        params.put("groupPath", path);
        params.put("groupID", String.valueOf(groupID));

        getSimpleJdbcTemplate().update(updateGroupPath, params);
    }

    public Integer deleteAddressByID(Integer addrID) {
        return getJdbcTemplate().update(DEL_ADDRESS_BY_ID, new Object[]{addrID});
    }

}

