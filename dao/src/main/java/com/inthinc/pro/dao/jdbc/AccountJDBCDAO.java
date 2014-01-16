package com.inthinc.pro.dao.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.model.Account;

public class AccountJDBCDAO extends SimpleJdbcDaoSupport implements AccountDAO {

    private static final String ALL_VALID_ACCOUNTIDS = "select acctID from account where status=1";

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
        throw new NotImplementedException();
    }

    @Override
    public Account findByID(Integer id) {
        throw new NotImplementedException();
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
}
