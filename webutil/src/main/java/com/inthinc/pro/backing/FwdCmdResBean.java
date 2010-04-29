package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlExtendedDataTable;
import org.richfaces.model.selection.Selection;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.ForwardCommandDefDAO;
import com.inthinc.pro.dao.ReportDAO;
import com.inthinc.pro.model.DeviceReportItem;
import com.inthinc.pro.model.ForwardCommandDef;
import com.inthinc.pro.model.ForwardCommandType;
import com.inthinc.pro.model.pagination.PageParams;

public class FwdCmdResBean extends BaseBean {

	private Integer fwdcmd;
	private Integer intData;
	private String stringData;

	// Device Selection
	private Integer acctID;
	private List<DeviceReportItem> devices;
	private DeviceReportItem filter;
	private List<DeviceReportItem> selectedDevices;
	private Integer deviceTotalCount;

//	private Selection deviceSelection;
//	private HtmlExtendedDataTable scrollableDataTable;

	private DeviceDAO deviceDAO;
	private ReportDAO reportDAO;
	private ForwardCommandDefDAO forwardCommandDefDAO;
	private Integer page;
	Map<Integer, ForwardCommandDef> forwardCommandDefMap;
	List<SelectItem> forwardCommandSelectList;
	
    private boolean               selectAll;


	public FwdCmdResBean() {
		super();
	}

	public void init() {
System.out.println("FwdCmdResBean init()");		
		List<ForwardCommandDef> fullForwardCommandDefList = forwardCommandDefDAO.getFwdCmdDefs();
		if (!isSuperuser()) {
			acctID = getUser().getPerson().getAcctID();
System.out.println("FwdCmdResBean loadDevices()");
			loadDevices();
System.out.println("FwdCmdResBean totaldevices: " + devices.size());
			forwardCommandDefMap = new HashMap<Integer, ForwardCommandDef>();
			for (ForwardCommandDef forwardCommandDef : fullForwardCommandDefList) {
				if (forwardCommandDef.getAllAccess()) {
					forwardCommandDefMap.put(forwardCommandDef.getCmdID(), forwardCommandDef);
				}
			}
			fwdcmd = (Integer) getForwardCommandSelectList().get(0).getValue();
			initFilter();
System.out.println("FwdCmdResBean init() - done");
		} else {
			// this should not happen -- bean only used in case of non -
			// superuser
		}
	}

	public List<SelectItem> getForwardCommandSelectList() {
		if (forwardCommandSelectList == null) {
			forwardCommandSelectList = new ArrayList<SelectItem>();

			for (ForwardCommandDef forwardCommandDef : forwardCommandDefMap.values()) {
				forwardCommandSelectList.add(new SelectItem(forwardCommandDef.getCmdID(), forwardCommandDef.getName()));
			}

			// TODO: SORT
		}

		return forwardCommandSelectList;
	}

	public Integer getFwdcmd() {
		return fwdcmd;
	}

	public void setFwdcmd(Integer fwdcmd) {
		this.fwdcmd = fwdcmd;
	}

	public ForwardCommandDef getForwardCommandDef() {
		if (fwdcmd != null)
			return forwardCommandDefMap.get(fwdcmd);

		return null;
	}

	public Integer getIntData() {
		return intData;
	}

	public void setIntData(Integer intData) {
		this.intData = intData;
	}

	public String getStringData() {
		return stringData;
	}

	public void setStringData(String stringData) {
		this.stringData = stringData;
	}

	public void loadDevices() {
		if (devices != null)
			devices.clear();
//		selectedDevices.clear();
		if (acctID != null) {
			devices = new ArrayList<DeviceReportItem>();

			deviceTotalCount = reportDAO.getDeviceReportCount(getUser().getGroupID(), null);
			PageParams pageParams = new PageParams(0, deviceTotalCount, null, null);
			devices = reportDAO.getDeviceReportPage(getUser().getGroupID(), pageParams);
		}
	}
	
	public boolean validateDeviceSelected() {
		selectedDevices = new ArrayList<DeviceReportItem>();
        for (DeviceReportItem device : getDevices())
            if (device.getSelected() && matchesFilter(device))
            	selectedDevices.add(device);
        
        if (selectedDevices.size() > 0)
        	return true;

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select one or more devices.", null);
		FacesContext.getCurrentInstance().addMessage(null, message);
		return false;
	}

	private boolean matchesFilter(DeviceReportItem device) {
		
		if (device.getDeviceIMEI().startsWith(filter.getDeviceIMEI()) &&
			device.getDeviceName().startsWith(filter.getDeviceName()) &&
			device.getVehicleName().startsWith(filter.getVehicleName()))
			return true;
				
		return false;
	}

	public void sendFwdCmdAction() {
		if (!validateDeviceSelected())
			return;
		
		Object data = null;
		if (stringData != null && !stringData.isEmpty()) {
			data = stringData;
		} else if (intData != null) {
			data = intData;
		} else {
			data = Integer.valueOf(0);
		}
//		selectedDevices.clear();
//		Iterator<Object> iterator = deviceSelection.getKeys();
//		while (iterator.hasNext()) {
//			Object key = iterator.next();
//			scrollableDataTable.setRowKey(key);
//			if (scrollableDataTable.isRowAvailable()) {
//				DeviceReportItem d = (DeviceReportItem) scrollableDataTable.getRowData();
//				selectedDevices.add(d);
//			}
//		}

//		for (DeviceReportItem d : selectedDevices) {
			// deviceDAO.queueForwardCommand(d.getDeviceID(),
			// new ForwardCommand(0, fwdcmd, data,
			// ForwardCommandStatus.STATUS_QUEUED));
//		}
		for (DeviceReportItem device : getSelectedDevices()) {
//				deviceDAO.queueForwardCommand(device.getDeviceID(), new ForwardCommand(0, fwdcmd, data, ForwardCommandStatus.STATUS_QUEUED));
		}
	}

	public void clearSelection() {
		selectedDevices = null;
		selectAll = false;
		for (DeviceReportItem device : getDevices())
		  device.setSelected(false);
	//
		page = 1;
		// device = null;
		
		// clear filter?
	}

	public DeviceDAO getDeviceDAO() {
		return deviceDAO;
	}

	public void setDeviceDAO(DeviceDAO deviceDAO) {
		this.deviceDAO = deviceDAO;
	}

	public ForwardCommandType[] getFwdCmdDescriptions() {
		return ForwardCommandType.values();
	}

	public void setAcctID(Integer acctID) {
		this.acctID = acctID;
	}

	public Integer getAcctID() {
		return acctID;
	}

//	public void setSelectedDevices(List<DeviceReportItem> selectedDevices) {
//		this.selectedDevices = selectedDevices;
//	}
//
//	public List<DeviceReportItem> getSelectedDevices() {
//		return selectedDevices;
//	}

	public List<DeviceReportItem> getDevices() {
		return devices;
	}

	public void setDevices(List<DeviceReportItem> devices) {
		this.devices = devices;
	}

//	public void setDeviceSelection(Selection deviceSelection) {
//		this.deviceSelection = deviceSelection;
//	}
//
//	public Selection getDeviceSelection() {
//		return deviceSelection;
//	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPage() {
		return page;
	}

//	public void setScrollableDataTable(HtmlExtendedDataTable scrollableDataTable) {
//		this.scrollableDataTable = scrollableDataTable;
//	}
//
//	public HtmlExtendedDataTable getScrollableDataTable() {
//		return scrollableDataTable;
//	}

	public ReportDAO getReportDAO() {
		return reportDAO;
	}

	public void setReportDAO(ReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}

	public ForwardCommandDefDAO getForwardCommandDefDAO() {
		return forwardCommandDefDAO;
	}

	public void setForwardCommandDefDAO(ForwardCommandDefDAO forwardCommandDefDAO) {
		this.forwardCommandDefDAO = forwardCommandDefDAO;
	}

	public boolean getSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

    public void doSelectAll()
    {
        for (DeviceReportItem device : this.getDevices())
            device.setSelected(selectAll);
    }
	public List<DeviceReportItem> getSelectedDevices() {
		return selectedDevices;
	}

	public void setSelectedDevices(List<DeviceReportItem> selectedDevices) {
		this.selectedDevices = selectedDevices;
	}

    public DeviceReportItem getFilter() {
		return filter;
	}

	public void setFilter(DeviceReportItem filter) {
		this.filter = filter;
	}

	public void initFilter()
	{
		filter = new DeviceReportItem();
		filter.setDeviceIMEI("");
		filter.setDeviceName("");
		filter.setVehicleName("");
	}
}
