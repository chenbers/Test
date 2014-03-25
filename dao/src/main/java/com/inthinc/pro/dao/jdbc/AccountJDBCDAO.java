package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Status;
import com.mysql.jdbc.Statement;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountJDBCDAO extends SimpleJdbcDaoSupport implements AccountDAO {

    private static final String ALL_VALID_ACCOUNTIDS = "select acctID from account where status=1";
    private static final String FIND_ALL_ACCOUNT_IDS = "SELECT acctID FROM  account ";
    private static final String DEL_ACCOUNT_BY_ID = "delete from account where acctID = ?";
    private static final String INSERT_ACCOUNT="INSERT INTO account() values()";
    private static final String FIND_BY_ID = "select * from account ac where ac.acctID= :acctID" ;
    private static final String UPDATE_ACCOUNT_1 = "UPDATE account set zonePublishDate=?, status=?, billID=?, mailID=?, name=?, hos=?, unkDriverID=? where acctID = ?";
    //zonePublishDate=1395295237, status=1, billID=1, mailID=1, name=acct of racers, hos=1, acctID=1, serialVersionUID=2388000038869935798, unkDriverID=1

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
    public static Long objectToLong(Object theObj){
        Long theLong;
        if(theObj == null){
            theLong = null;
        } else if(theObj instanceof Long) {
            theLong = ((Long)theObj).longValue();
        } else if(theObj instanceof Integer){
            theLong = ((Long)theObj).longValue();
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
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("acctID", acctId);
        StringBuilder findAccount = new StringBuilder(FIND_BY_ID);
        Account account = getSimpleJdbcTemplate().queryForObject(findAccount.toString(), accountParameterizedRowMapper, args);
        return account;
//        throw new NotImplementedException();
    }

    @Override
    public Integer create(Integer id, final Account account) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS);

                return ps;
            }

        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
//        throw new NotImplementedException();
    }

    @Override
    public Integer update(final Account account) {
        /*
updateAcct( 1, {zonePublishDate=1395295237, status=1, billID=1, mailID=1, name=acct of racers, hos=1, acctID=1, serialVersionUID=2388000038869935798, unkDriverID=1,
 props={formsEnabled=true, driveTimeViolationsReportEnabled=false, supportContact3=, supportContact4=, eventQueueEnabled=false, supportContact5=, trailersEnabled=true,
 passwordChange=0, waySmart=true, rhaEnabled=true, noReplyEmail=, phoneAlertsActive=1, supportPhone1=, supportPhone2=, serialVersionUID=1, passwordStrength=0}})

"UPDATE account set zonePublishDate=?, status=?, billID=?, mailID=?, name=?, hos=?, unkDriverID=? where acctID = ?";
        * */

        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_ACCOUNT_1, Statement.RETURN_GENERATED_KEYS);
                ps.setDate(1, (Date) account.getZonePublishDate());

                if (account.getStatus() == null || account.getStatus().getCode() == null) {
                    ps.setNull(2, Types.NULL);
                } else {
                    ps.setInt(2, account.getStatus().getCode());
                }

                ps.setInt(3, account.getBillID());
                //mailId ?
                ps.setInt(4, 1);
                ps.setString(5, account.getAcctName());
                ps.setObject(6, account.getHos());
                ps.setInt(7,account.getUnkDriverID());
                ps.setInt(8, account.getAccountID());

                logger.debug(ps.toString());
                return ps;
            }

        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
//        throw new NotImplementedException();
    }

    @Override
    public Integer deleteByID(Integer id) {
        return getJdbcTemplate().update(DEL_ACCOUNT_BY_ID, new Object[]{id});
//        throw new NotImplementedException();
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
            return account;
        }
    };
    private ParameterizedRowMapper<Account> accountListParameterizedRowMapper = new ParameterizedRowMapper<Account>() {
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            Account accountList =new Account();
            accountList.setAccountID(rs.getInt("acctID"));
            return accountList;
        }
    };
}
