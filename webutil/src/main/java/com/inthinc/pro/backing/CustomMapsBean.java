package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.CustomMapDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.GoogleMapType;
import com.inthinc.pro.model.CustomMap;

public class CustomMapsBean extends BaseBean {
    
    private AccountDAO accountDAO;
    private CustomMapDAO customMapDAO;
    private List<SelectItem> accountSelectList;
    private List<SelectItem> customMapSelectList;
    private Integer selectedAccountID;
    private Account selectedAccount;
    private Map<Integer, CustomMap> customMaps;
    private Integer selectedCustomMapID;
    private Boolean editing;
    
    private static final Integer NONE = -1;
    private static final Integer NEW_MAP_ID = -1;
    
    public void init() {
        if (isSuperuser()) {
            accountSelectList = new ArrayList<SelectItem>();
        
            List<Account> accountList = accountDAO.getAllAcctIDs();

            accountSelectList.add(new SelectItem(NONE, "<Select>"));
            for (Account account : accountList) {
                accountSelectList.add(new SelectItem(account.getAccountID(), account.getAccountID() + " - " + account.getAcctName()));
            }
        
            sort(accountSelectList);
        }
        else {
            setSelectedAccountID(getAccountID());
        }
    }
    
    protected void sort(List<SelectItem> selectItemList) {
        Collections.sort(selectItemList, new Comparator<SelectItem>() {
            @Override
            public int compare(SelectItem o1, SelectItem o2) {
                return ((Integer)o1.getValue()).compareTo((Integer)o2.getValue());
            }
        });
    }

    public void saveAction() {
        if (getSelectedCustomMapID() == null) {
            initCustomMaps();
            return;
        }
        if (getSelectedCustomMapID().equals(NEW_MAP_ID)) {
            customMapDAO.create(getSelectedAccountID(), getSelectedCustomMap());
        }
        else {
            customMapDAO.update(getSelectedCustomMap());
        }
        initCustomMaps();
    }
    public void cancelAction() {
        initCustomMaps();
    }
    public void newAction() {
      CustomMap customMap = new CustomMap();
      customMap.setAcctID(getSelectedAccountID());
      customMap.setCustomMapID(NEW_MAP_ID);
      customMap.setUrl("");
      customMap.setName("");
      customMap.setMinZoom(5);
      customMap.setMaxZoom(16);
      customMap.setOpacity(0.5);
      customMap.setPngFormat(Boolean.TRUE);
      customMap.setLayer("");
      customMaps.put(customMap.getCustomMapID(), customMap);
      setSelectedCustomMapID(NEW_MAP_ID);
      setEditing(true);
    }
    public void deleteAction() {
        if (getSelectedCustomMapID() == null || getSelectedCustomMapID().equals(NEW_MAP_ID)) {
            initCustomMaps();
            return;
        }
        customMapDAO.deleteByID(getSelectedCustomMapID());
        initCustomMaps();
    }
    public void refreshAction() {
    }
    public void switchAccountAction() {
        System.out.println("switchAccountAction() selected = " + this.getSelectedAccountID());
    }
    public void editAction() {
        setEditing(true);
    }
    public Integer getSelectedAccountID() {
        return selectedAccountID;
    }

    public void setSelectedAccountID(Integer selectedAccountID) {
        this.selectedAccountID = selectedAccountID;
        if (selectedAccountID == NONE)
            setSelectedAccount(null);
        else {
            setSelectedAccount(initAccount(selectedAccountID));
            initCustomMaps();
        }
    }

    private void initCustomMaps() {
        setEditing(false);
        setSelectedCustomMapID(null);
        customMaps = new HashMap<Integer, CustomMap> ();
        if (selectedAccount == null) {
            customMapSelectList = null;
        }
        else {
            customMapSelectList = new ArrayList<SelectItem>();
            customMapSelectList.add(new SelectItem(null, "<Select>"));
            
            List<CustomMap> customMapList = customMapDAO.getCustomMapsByAcctID(getSelectedAccountID());
            for (CustomMap customMap : customMapList) {
                customMaps.put(customMap.getCustomMapID(), customMap);
                customMapSelectList.add(new SelectItem(customMap.getCustomMapID(), customMap.getName()));
            }
        }
    }
    
    public CustomMap getSelectedCustomMap()
    {
        if (customMaps == null || selectedCustomMapID == null)
            return null;

        return customMaps.get(selectedCustomMapID);
    }

    private Account initAccount(Integer accountID) {
        return accountDAO.findByID(accountID);
    }

    public List<SelectItem> getAccountSelectList() {
        return accountSelectList;
    }
    public List<SelectItem> getGoogleMapTypes() {
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        for (GoogleMapType e : EnumSet.allOf(GoogleMapType.class))
        {
              selectItemList.add(new SelectItem(e,e.name()));
        }
        return selectItemList;
    }

    public Account getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    public void setAccountSelectList(List<SelectItem> accountSelectList) {
        this.accountSelectList = accountSelectList;
    }

    public List<SelectItem> getCustomMapSelectList() {
        return customMapSelectList;
    }

    public void setCustomMapSelectList(List<SelectItem> customMapSelectList) {
        this.customMapSelectList = customMapSelectList;
    }
    
    public List<CustomMap> getCustomMapList() {
        List<CustomMap> customMapList = new ArrayList<CustomMap>();
        if (customMaps != null) {
            for (CustomMap customMap : customMaps.values())
                customMapList.add(customMap);
        }
        return customMapList;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    
    public CustomMapDAO getCustomMapDAO() {
        return customMapDAO;
    }

    public void setCustomMapDAO(CustomMapDAO customMapDAO) {
        this.customMapDAO = customMapDAO;
    }

    public void setEditing(Boolean editing) {
        this.editing = editing;
    }

    public Boolean getEditing() {
        return editing;
    }

    public Integer getSelectedCustomMapID() {
        return selectedCustomMapID;
    }

    public void setSelectedCustomMapID(Integer selectedCustomMapID) {
        this.selectedCustomMapID = selectedCustomMapID;
    }

}
