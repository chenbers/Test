package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Status;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountJDBCDAO extends SimpleJdbcDaoSupport implements AccountDAO {

    private static final String ALL_VALID_ACCOUNTIDS = "select acctID from account where status=1";
    private static final String FIND_ALL_ACCOUNT_IDS = "SELECT acctID FROM  account ";

    private static final String FIND_BY_ID = "select * from account ac where ac.acctID= :acctID" ;

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
        List<Account> accountList = (List<Account>) getSimpleJdbcTemplate().queryForObject(FIND_ALL_ACCOUNT_IDS, accountListParameterizedRowMapper,args );
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
    public Integer create(Integer id, Account entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer update(Account entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer deleteByID(Integer id) {
        throw new NotImplementedException();
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
            accountList.setAccountID(rs.getInt("ac.acctID"));
            accountList.setStatus(Status.valueOf(rs.getInt("ac.status")));
            accountList.setAcctName(rs.getString("ac.name"));
            accountList.setUnkDriverID(rs.getInt("ac.unkDriverID"));
            accountList.setBillID(rs.getInt("ac.billID"));
            return accountList;
        }
    };
}
