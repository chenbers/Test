package com.inthinc.pro.service.adapters;

import java.util.List;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.model.Account;

/**
 * Adapter for the Account resources.
 * 
 * @author dcueva
 *
 */
public class AccountDAOAdapter extends BaseDAOAdapter<Account> {

	private AccountDAO accountDAO;	
	
    /**
     * Creates an Account entity.
     * 
	 * @see com.inthinc.pro.service.adapters.BaseDAOAdapter#create(java.lang.Object)
	 */
	@Override
	public Integer create(Account account) {
		return accountDAO.create(account);
	}
	
	@Override
	public List<Account> getAll() {
		return accountDAO.getAllAcctIDs();
	}

	@Override
	public GenericDAO<Account, Integer> getDAO() {
		return accountDAO;
	}


	@Override
	protected Integer getResourceID(Account account) {
		return account.getAcctID();
	}
	
	/**
	 * @param accountDAO the accountDAO to set
	 */
	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	/**
	 * @return the accountDAO
	 */
	public AccountDAO getAccountDAO() {
		return accountDAO;
	}


}
