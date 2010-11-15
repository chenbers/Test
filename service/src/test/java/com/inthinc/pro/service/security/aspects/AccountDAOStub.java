/**
 * 
 */
package com.inthinc.pro.service.security.aspects;

import java.util.List;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.model.Account;

/**
 * @author dfreitas
 *
 */
public class AccountDAOStub implements AccountDAO {

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.AccountDAO#create(com.inthinc.pro.model.Account)
     */
    @Override
    public Integer create(Account entity) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.AccountDAO#getAllAcctIDs()
     */
    @Override
    public List<Account> getAllAcctIDs() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.GenericDAO#create(java.lang.Object, java.lang.Object)
     */
    @Override
    public Integer create(Integer id, Account entity) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.GenericDAO#deleteByID(java.lang.Object)
     */
    @Override
    public Integer deleteByID(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.GenericDAO#findByID(java.lang.Object)
     */
    @Override
    public Account findByID(Integer id) {
        if (id == 23) {
            return new Account(666, null);
        } else if (id == 25) {
            return new Account(10, null);
        }
        
        else return null;
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.GenericDAO#update(java.lang.Object)
     */
    @Override
    public Integer update(Account entity) {
        return 99;
    }

}
