package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.AccountHOSType;
import com.inthinc.pro.model.PreferenceLevelOption;

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
            accountSelectList.add(new SelectItem(account.getAccountID(), account.getAccountID() + " - " + account.getAcctName()));
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
    public List<SelectItem> getGroupIDSelectItems(){
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
    	
        return selectItemList;
    }
    public List<SelectItem> getPasswordChangeOptions() {
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        selectItemList.add(new SelectItem(PreferenceLevelOption.NONE.getCode().toString(), "None"));
        selectItemList.add(new SelectItem(PreferenceLevelOption.WARN.getCode().toString(), "Warn"));
        selectItemList.add(new SelectItem(PreferenceLevelOption.REQUIRE.getCode().toString(), "Require"));
        return selectItemList;
    }
    public List<SelectItem> getHOSTypes() {
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        selectItemList.add(new SelectItem(AccountHOSType.NONE, "Disabled"));
        selectItemList.add(new SelectItem(AccountHOSType.HOS_SUPPORT, "Enabled"));
        return selectItemList;
    }
    
    public List<SelectItem> getPasswordStrengthOptions() {
        List<SelectItem> strengthOptions = new ArrayList<SelectItem>();
        //number of points (max = 50) required before password will be considered valid
        strengthOptions.add(new SelectItem(0 , "No Restrictions"));
        strengthOptions.add(new SelectItem(16, "Weak"));
        strengthOptions.add(new SelectItem(25, "Fair"));
        strengthOptions.add(new SelectItem(35, "Strong"));
        return strengthOptions;
    }
    
    public List<SelectItem> getLoginExpireOptions() {
        List<SelectItem> expireOptions = new ArrayList<SelectItem>();
        //number of days before password will expire regardless of use
        expireOptions.add(new SelectItem(null, "Never"));
        expireOptions.add(new SelectItem(15, "15"));
        expireOptions.add(new SelectItem(30, "30"));
        expireOptions.add(new SelectItem(45, "45"));
        expireOptions.add(new SelectItem(60, "60"));
        expireOptions.add(new SelectItem(75, "75"));
        expireOptions.add(new SelectItem(90, "90"));
        return expireOptions;
    }
    
    public List<SelectItem> getPasswordExpireOptions() {
        List<SelectItem> expireOptions = new ArrayList<SelectItem>();
        //number of days before login will expire if not used
        expireOptions.add(new SelectItem(null, "Never"));
        expireOptions.add(new SelectItem(90, "90"));
        expireOptions.add(new SelectItem(120, "120"));
        expireOptions.add(new SelectItem(180, "180"));
        expireOptions.add(new SelectItem(360, "360"));
        return expireOptions;
    }

    public void saveAction() {
        if (account != null) {
            accountDAO.update(account);
            setSaveActionMsg("Success: HOS is " + (account.getHos() == AccountHOSType.NONE ? "Disabled" : "Enabled") +
                                " , Waysmart is " + (account.hasWaySmartSupport() ? "Enabled" : "Disabled") + 
                                " , Login Expire is " +(account.getProps().getLoginExpire()) + 
                                " , Password Expire is "+(account.getProps().getPasswordExpire()) +
                                " , Password Strength is "+(account.getProps().getPasswordStrength()) +
                                " and Password Change Required is "+(account.getProps().getPasswordChange())+
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
