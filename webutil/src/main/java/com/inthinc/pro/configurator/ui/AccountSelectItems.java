package com.inthinc.pro.configurator.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.model.Account;

public class AccountSelectItems implements Serializable {

	private static final long serialVersionUID = 1L;
	private AccountDAO accountDAO;
	
	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	private List<SelectItem> accountSelectItems;

	public void init(){
		
		List<Account> accounts = accountDAO.getAllAcctIDs();
		accountSelectItems = new ArrayList<SelectItem>();
		Collections.sort(accounts);
		for(Account account: accounts){
			
			accountSelectItems.add(new SelectItem(account.getAccountID(),account.getAcctName()));
		}
	}

	public List<SelectItem> getSelectItems() {
		return accountSelectItems;
	}
}
