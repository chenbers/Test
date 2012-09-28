package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.model.Asset;
import com.inthinc.pro.model.AssetReportItem;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.TempColumns;

public class AssetsReportBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(AssetsReportBean.class);

    // assetsData is the ONE read from the db, assetData is what is displayed
    private List<Asset> assetsData = new ArrayList<Asset>();
    private List<AssetReportItem> assetData = new ArrayList<AssetReportItem>();

    static final List<String> AVAILABLE_COLUMNS;
    private Map<String, TableColumn> assetColumns;
    private Vector tmpColumns = new Vector();

    private TablePreference tablePref;
    private TablePreferenceDAO tablePreferenceDAO;

    private AssetReportItem art = null;

    private Integer numRowsPerPg = 1;
    private final static String COLUMN_LABEL_PREFIX = "assetreport_";

    private Integer maxCount = null;
    private Integer start = 1;
    private Integer end = numRowsPerPg;

    private String searchFor = "";

    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("tiwiproID");
        AVAILABLE_COLUMNS.add("assignedVehicle");
        AVAILABLE_COLUMNS.add("IMEI");
        AVAILABLE_COLUMNS.add("phoneNbr");
        AVAILABLE_COLUMNS.add("status");
    }

    public void init()
    {
        // Live DAO here
        // assetsData = driverDAO.getAllDrivers(getUser().getPerson().getGroupID());
        // Fake data
        art = new AssetReportItem();
        Asset asset = new Asset();
        asset.setAssignedVehicle("LV-8453");
        asset.setIMEI("1c3lc46h67n601905a");
        asset.setPhoneNbr("801-555-1212");
        asset.setStatus("Disabled");
        asset.setTiwiproID("TP-98344");
        assetsData.add(asset);

        art = new AssetReportItem();
        asset = new Asset();
        asset.setAssignedVehicle("RV-1153");
        asset.setIMEI("1c3lc46h67n6016i4a");
        asset.setPhoneNbr("801-642-3159");
        asset.setStatus("Enabled");
        asset.setTiwiproID("TP-11422");
        assetsData.add(asset);

        art = new AssetReportItem();
        asset = new Asset();
        asset.setAssignedVehicle("ZV-9910");
        asset.setIMEI("1c3lc46h67n601449a");
        asset.setPhoneNbr("801-772-0456");
        asset.setStatus("Disabled");
        asset.setTiwiproID("TP-98143");
        assetsData.add(asset);

        loadResults(assetsData);
        maxCount = assetData.size();
        resetCounts();
    }

    public List<AssetReportItem> getAssetData()
    {
        return assetData;
    }

    public void setAssetData(List<AssetReportItem> assetData)
    {
        this.assetData = assetData;
    }

    public void search()
    {
        if (this.assetData.size() > 0)
        {
            this.assetData.clear();
        }

        // Search by tiwi pro ID
        if (this.searchFor.trim().length() != 0)
        {
            String trimmedSearch = this.searchFor.trim();
            List<Asset> matchedAssets = new ArrayList<Asset>();

            for (Asset a : assetsData)
            {
                // Fuzzy
                String lowerCaseLast = a.getTiwiproID();
                int index1 = lowerCaseLast.indexOf(trimmedSearch);
                if (index1 != -1)
                {
                    matchedAssets.add(a);
                }
            }

            loadResults(matchedAssets);
            this.maxCount = matchedAssets.size();

            // Nothing entered, show them all
        }
        else
        {
            loadResults(assetsData);
            this.maxCount = assetsData.size();
        }

        resetCounts();
    }

    private void loadResults(List<Asset> assetsData)
    {
        for (Asset a : assetsData)
        {
            art = new AssetReportItem();
            art.setAsset(a);
            assetData.add(art);
        }
    }

    private void resetCounts()
    {
        this.start = 1;

        // None found
        if (this.assetData.size() < 1)
        {
            this.start = 0;
        }

        this.end = this.numRowsPerPg;

        // Fewer than a page
        if (this.assetData.size() <= this.end)
        {
            this.end = this.assetData.size();
        }
        else if (this.start == 0)
        {
            this.end = 0;
        }
    }

    public void saveColumns()
    {
        // To data store
        TablePreference pref = getTablePref();
        int cnt = 0;
        for (String column : AVAILABLE_COLUMNS)
        {
            pref.getVisible().set(cnt++, assetColumns.get(column).getVisible());
        }
        setTablePref(pref);
        tablePreferenceDAO.update(pref);

        // Update tmp
        Iterator it = this.assetColumns.keySet().iterator();
        while (it.hasNext())
        {
            Object key = it.next();
            Object value = this.assetColumns.get(key);

            for (int i = 0; i < tmpColumns.size(); i++)
            {
                TempColumns tc = (TempColumns) tmpColumns.get(i);
                if (tc.getColName().equalsIgnoreCase((String) key))
                {
                    Boolean b = ((TableColumn) value).getVisible();
                    tc.setColValue(b);
                }
            }
        }
    }

    public void cancelColumns()
    {
        // Update live
        Iterator it = this.assetColumns.keySet().iterator();
        while (it.hasNext())
        {
            Object key = it.next();
            Object value = this.assetColumns.get(key);

            for (int i = 0; i < tmpColumns.size(); i++)
            {
                TempColumns tc = (TempColumns) tmpColumns.get(i);
                if (tc.getColName().equalsIgnoreCase((String) key))
                {
                    this.assetColumns.get(key).setVisible(tc.getColValue());
                }
            }
        }

    }

    public TablePreference getTablePref()
    {
        if (tablePref == null)
        {
            // TODO: refactor -- could probably keep in a session bean
            List<TablePreference> tablePreferenceList = tablePreferenceDAO.getTablePreferencesByUserID(getUser().getUserID());
            for (TablePreference pref : tablePreferenceList)
            {
                if (pref.getTableType().equals(TableType.ASSET_REPORT))
                {
                    setTablePref(pref);
                    return tablePref;
                }
            }
            tablePref = new TablePreference();
            tablePref.setUserID(getUser().getUserID());
            tablePref.setTableType(TableType.ASSET_REPORT);
            List<Boolean> visibleList = new ArrayList<Boolean>();
            for (String column : AVAILABLE_COLUMNS)
            {
                visibleList.add(Boolean.TRUE);
            }
            tablePref.setVisible(visibleList);
            Integer tablePrefID = getTablePreferenceDAO().create(getUser().getUserID(), tablePref);
            tablePref.setTablePrefID(tablePrefID);
            setTablePref(tablePref);

        }

        return tablePref;
    }

    public void setTablePref(TablePreference tablePref)
    {
        this.tablePref = tablePref;
    }

    public Map<String, TableColumn> getAssetColumns()
    {
        if (assetColumns == null)
        {
            List<Boolean> visibleList = getTablePref().getVisible();
            assetColumns = new HashMap<String, TableColumn>();
            int cnt = 0;
            for (String column : AVAILABLE_COLUMNS)
            {
                TableColumn tableColumn = new TableColumn(visibleList.get(cnt++), MessageUtil.getMessageString(COLUMN_LABEL_PREFIX + column));
                if (column.equals("clear"))
                    tableColumn.setCanHide(false);

                assetColumns.put(column, tableColumn);
                tmpColumns.add(new TempColumns(column, tableColumn.getVisible()));

            }
        }

        return assetColumns;
    }

    public void setAssetColumns(Map<String, TableColumn> assetColumns)
    {
        this.assetColumns = assetColumns;
    }

    public void scrollerListener(DataScrollerEvent se)
    {
        this.start = (se.getPage() - 1) * this.numRowsPerPg + 1;
        this.end = (se.getPage()) * this.numRowsPerPg;

        // Partial page
        if (this.end > this.assetData.size())
        {
            this.end = this.assetData.size();
        }
    }

    public Integer getMaxCount()
    {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount)
    {
        this.maxCount = maxCount;
    }

    public Integer getStart()
    {
        return start;
    }

    public void setStart(Integer start)
    {
        this.start = start;
    }

    public Integer getEnd()
    {
        return end;
    }

    public void setEnd(Integer end)
    {
        this.end = end;
    }

    public Integer getNumRowsPerPg()
    {
        return numRowsPerPg;
    }

    public void setNumRowsPerPg(Integer numRowsPerPg)
    {
        this.numRowsPerPg = numRowsPerPg;
    }

    public String getSearchFor()
    {
        return searchFor;
    }

    public void setSearchFor(String searchFor)
    {
        this.searchFor = searchFor;
    }

    public TablePreferenceDAO getTablePreferenceDAO()
    {
        return tablePreferenceDAO;
    }

    public void setTablePreferenceDAO(TablePreferenceDAO tablePreferenceDAO)
    {
        this.tablePreferenceDAO = tablePreferenceDAO;
    }

}
