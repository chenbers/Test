package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.AccountAttributes;
import com.inthinc.pro.model.AccountHOSType;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.mysql.jdbc.Statement;
import org.apache.commons.lang.NotImplementedException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class AccountJDBCDAO extends SimpleJdbcDaoSupport implements AccountDAO {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String ALL_VALID_ACCOUNTIDS = "select acctID from account where status=1";
    private static final String FIND_ALL_ACCOUNT_IDS = "SELECT acctID FROM  account ";
    private static final String DEL_ACCOUNT_BY_ID = "delete from account where acctID = ?";
    private static final String INSERT_ACCOUNT = "INSERT INTO account() values()";
    private static final String FIND_BY_ID = "select * from account ac LEFT JOIN address ad on ac.mailId= ad.addrId, state st where  ac.acctID= :acctID and ad.stateId=st.stateID  ";
    private static final String UPDATE_ACCOUNT_1 = "UPDATE account set zonePublishDate=?, status=?, billID=?, name=?, hos=?, unkDriverID=? where acctID = ?";
    private static final String UPDATE_ACCOUNT_SUPPORT_PHONE1 = "UPDATE accountProp set value=? where acctID = ? and name like 'supportPhone1'";
    private static final String UPDATE_ACCOUNT_SUPPORT_PHONE2 = "UPDATE accountProp set value=? where acctID = ? and name like 'supportPhone2'";
    private static final String UPDATE_ACCOUNT_SUPPORT_PHONE3 = "UPDATE accountProp set value=? where acctID = ? and name like 'supportContact3'";
    private static final String UPDATE_ACCOUNT_SUPPORT_PHONE4 = "UPDATE accountProp set value=? where acctID = ? and name like 'supportContact4'";
    private static final String UPDATE_ACCOUNT_SUPPORT_PHONE5 = "UPDATE accountProp set value=? where acctID = ? and name like 'supportContact5'";
    private static final String UPDATE_ACCOUNT_PHONE_ALERT = "UPDATE accountProp set value=? where acctID = ? and name like 'phoneAlertsActive'";
    private static final String FIND_PHONE1 = "select ap.value from accountProp ap where   ap.name like 'supportPhone1' and ap.acctId =:acctID";
    private static final String FIND_PHONE2 = "select  ap.value from accountProp ap where   ap.name like 'supportPhone2' and ap.acctId =:acctID";
    private static final String FIND_CONTACT3 = "select ap.value from accountProp ap where   ap.name like 'supportContact3' and ap.acctId =:acctID";
    private static final String FIND_CONTACT4 = "select  ap.value from accountProp ap where   ap.name like 'supportContact4' and ap.acctId =:acctID";
    private static final String FIND_CONTACT5 = "select  ap.value from accountProp ap where   ap.name like 'supportContact5' and ap.acctId =:acctID";
    private static final String FIND_PHONE_ALERT = "select  ap.value from accountProp ap where   ap.name like 'phoneAlertsActive' and ap.acctId =:acctID";

    @Override
    public List<Long> getAllValidAcctIDs() {
        List<Map<String, Object>> results = getSimpleJdbcTemplate().queryForList(ALL_VALID_ACCOUNTIDS);
        List<Long> accountIDs = convertToListLongs(results);
        return accountIDs;
    }

    private List<Long> convertToListLongs(List<Map<String, Object>> results) {
        List<Long> accountIDs = new ArrayList<Long>();
        for (Map<String, Object> result : results) {
            Long acctID = objectToLong(result.get("acctID"));
            if (acctID != null) {
                accountIDs.add(acctID);
            }
        }
        return accountIDs;
    }

    public static Long objectToLong(Object theObj) {
        Long theLong;
        if (theObj == null) {
            theLong = null;
        } else if (theObj instanceof Long) {
            theLong = ((Long) theObj).longValue();
        } else if (theObj instanceof Integer) {
            theLong = ((Long) theObj).longValue();
        } else {
            theLong = null;
        }
        return theLong;
    }

    @Override
    public Integer create(Account entity) {
        throw new NotImplementedException();
    }

    @Override
    public List<Account> getAllAcctIDs() {
        Map<String, Object> args = new HashMap<String, Object>();
        List<Account> accountList = getSimpleJdbcTemplate().query(FIND_ALL_ACCOUNT_IDS, accountListParameterizedRowMapper, args);
        return accountList;
    }

    @Override
    public Account findByID(Integer acctId) {
        try {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("acctID", acctId);
        StringBuilder findAccount = new StringBuilder(FIND_BY_ID);
        Account account = getSimpleJdbcTemplate().queryForObject(findAccount.toString(), accountParameterizedRowMapper, args);
        return account;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Integer create(Integer id, final Account account) {
        throw new NotImplementedException();
    }

    @Override
    public Integer update(final Account account) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_ACCOUNT_1, Statement.RETURN_GENERATED_KEYS);
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                String ZonePublishDate = df.format(toUTC(new Date()));
                ps.setString(1, ZonePublishDate);

                if (account.getStatus() == null || account.getStatus().getCode() == null) {
                    ps.setNull(2, Types.NULL);
                } else {
                    ps.setInt(2, account.getStatus().getCode());
                }

                ps.setInt(3, account.getBillID());
                ps.setString(4, account.getAcctName());
                ps.setInt(5, account.getHos().getCode());
                ps.setInt(6, account.getUnkDriverID());
                ps.setInt(7, account.getAccountID());

                logger.debug(ps.toString());
                return ps;
            }

        };
        PreparedStatementCreator psc_contact1 = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_ACCOUNT_SUPPORT_PHONE1, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, account.getProps().getSupportContact1());
                ps.setInt(2, account.getAccountID());

                logger.debug(ps.toString());
                return ps;
            }

        };
        PreparedStatementCreator psc_contact2 = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_ACCOUNT_SUPPORT_PHONE2, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, account.getProps().getSupportContact2());
                ps.setInt(2, account.getAccountID());

                logger.debug(ps.toString());
                return ps;
            }

        };
        PreparedStatementCreator psc_contact3 = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_ACCOUNT_SUPPORT_PHONE3, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, account.getProps().getSupportContact3());
                ps.setInt(2, account.getAccountID());

                logger.debug(ps.toString());
                return ps;
            }

        };

        PreparedStatementCreator psc_contact4 = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_ACCOUNT_SUPPORT_PHONE4, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, account.getProps().getSupportContact4());
                ps.setInt(2, account.getAccountID());

                logger.debug(ps.toString());
                return ps;
            }

        };
        PreparedStatementCreator psc_contact5 = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_ACCOUNT_SUPPORT_PHONE5, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, account.getProps().getSupportContact5());
                ps.setInt(2, account.getAccountID());

                logger.debug(ps.toString());
                return ps;
            }

        };
        PreparedStatementCreator psc_phone_alert = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_ACCOUNT_PHONE_ALERT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, account.getProps().getPhoneAlertsActive());
                ps.setInt(2, account.getAccountID());

                logger.debug(ps.toString());
                return ps;
            }

        };
        jdbcTemplate.update(psc);
        jdbcTemplate.update(psc_contact1);
        jdbcTemplate.update(psc_contact2);
        jdbcTemplate.update(psc_contact3);
        jdbcTemplate.update(psc_contact4);
        jdbcTemplate.update(psc_contact5);
        jdbcTemplate.update(psc_phone_alert);

        return account.getAccountID();
    }

    @Override
    public Integer deleteByID(Integer id) {
        return getJdbcTemplate().update(DEL_ACCOUNT_BY_ID, new Object[]{id});
    }

    private ParameterizedRowMapper<Account> accountParameterizedRowMapper = new ParameterizedRowMapper<Account>() {
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            Account account = new Account();
            account.setAccountID(rs.getInt("ac.acctID"));
            account.setStatus(Status.valueOf(rs.getInt("ac.status")));
            account.setAcctName(rs.getString("ac.name"));
            account.setUnkDriverID(rs.getInt("ac.unkDriverID"));
            account.setBillID(rs.getInt("ac.billID"));
            account.setAddressID(rs.getInt("ac.mailId"));
            account.setZonePublishDate(rs.getDate("ac.zonePublishDate"));

            AccountAttributes accountAttributes = new AccountAttributes();
            Map<String, Object> args = new HashMap<String, Object>();
            args.put("acctID", 1);
            String accountPhone1 = getSimpleJdbcTemplate().queryForObject(FIND_PHONE1, String.class, args);
            String accountPhone2 = getSimpleJdbcTemplate().queryForObject(FIND_PHONE2, String.class, args);
            String accountContact3 = getSimpleJdbcTemplate().queryForObject(FIND_CONTACT3, String.class, args);
            String accountContact4 = getSimpleJdbcTemplate().queryForObject(FIND_CONTACT4, String.class, args);
            String accountContact5 = getSimpleJdbcTemplate().queryForObject(FIND_CONTACT5, String.class, args);
            String phoneActive = getSimpleJdbcTemplate().queryForObject(FIND_PHONE_ALERT, String.class, args);
            accountAttributes.setSupportContact1(accountPhone1);
            accountAttributes.setSupportContact2(accountPhone2);
            accountAttributes.setSupportContact3(accountContact3);
            accountAttributes.setSupportContact4(accountContact4);
            accountAttributes.setSupportContact5(accountContact5);
            accountAttributes.setPhoneAlertsActive(phoneActive);

            State state = new State();
            state.setAbbrev(rs.getString("st.abbrev"));
            state.setName(rs.getString("st.name"));
            state.setStateID(rs.getInt("st.stateId"));

            Address addres = new Address();
            addres.setAccountID(rs.getInt("ad.acctID"));
            addres.setAddr1(rs.getString("ad.addr1"));
            addres.setAddr2(rs.getString("ad.addr2"));
            addres.setAddrID(rs.getInt("ad.addrID"));
            addres.setCity(rs.getString("ad.city"));

            addres.setState(state);
            addres.setZip(rs.getString("ad.zip"));

            account.setAddress(addres);
            account.setProps(accountAttributes);
            account.setHos(AccountHOSType.valueOf(rs.getInt("ac.hos")));
            return account;
        }
    };
    private ParameterizedRowMapper<Account> accountListParameterizedRowMapper = new ParameterizedRowMapper<Account>() {
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            Account accountList = new Account();
            accountList.setAccountID(rs.getInt("acctID"));
            return accountList;
        }
    };

    private Date toUTC(Date date) {
        DateTime dt = new DateTime(date.getTime()).toDateTime(DateTimeZone.UTC);
        return dt.toDate();
    }
}
