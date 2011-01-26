package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.AccountHOSType;

public class AccountOptionsBean extends BaseBean {
    
    private AccountDAO accountDAO;
    
    private List<SelectItem> accountSelectList;
    private Integer selectedAccountID;
    private Account account;
    private String saveActionMsg;

    private static final Integer NONE = -1;
    
    public void init() {
        accountSelectList = new ArrayList<SelectItem>();
        
        List<Account> accountList = accountDAO.getAllAcctIDs();

        accountSelectList.add(new SelectItem(NONE, "<Select>"));
        for (Account account : accountList) {
            accountSelectList.add(new SelectItem(account.getAcctID(), account.getAcctID() + " - " + account.getAcctName()));
        }
        
        sort(accountSelectList);
    }
    
    protected void sort(List<SelectItem> selectItemList) {
        Collections.sort(selectItemList, new Comparator<SelectItem>() {
            @Override
            public int compare(SelectItem o1, SelectItem o2) {
                return ((Integer)o1.getValue()).compareTo((Integer)o2.getValue());
            }
        });
    }


    public List<SelectItem> getHOSTypes() {
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        selectItemList.add(new SelectItem(AccountHOSType.NONE, "Disabled"));
        selectItemList.add(new SelectItem(AccountHOSType.HOS_SUPPORT, "Enabled"));
        return selectItemList;
    }

    public void saveAction() {
        if (account != null) {
            accountDAO.update(account);
            setSaveActionMsg("Success: HOS is " + (account.getHos() == AccountHOSType.NONE ? "Disabled" : "Enabled") +
                                " and Waysmart is " + (account.getWaySmartSupport() ? "Enabled" : "Disabled") +
                                " for Account: " + account.getAcctName());
        }
    }

    public Integer getSelectedAccountID() {
        return selectedAccountID;
    }

    public void setSelectedAccountID(Integer selectedAccountID) {
        this.selectedAccountID = selectedAccountID;
        setSaveActionMsg(null);
        if (selectedAccountID == NONE)
            setAccount(null);
        else setAccount(initAccount(selectedAccountID));
    }

    private Account initAccount(Integer accountID) {
        return accountDAO.findByID(accountID);
    }

    public List<SelectItem> getAccountSelectList() {
        return accountSelectList;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    public void setAccountSelectList(List<SelectItem> accountSelectList) {
        this.accountSelectList = accountSelectList;
    }

    public String getSaveActionMsg() {
        return saveActionMsg;
    }

    public void setSaveActionMsg(String saveActionMsg) {
        this.saveActionMsg = saveActionMsg;
    }
    
    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

}
