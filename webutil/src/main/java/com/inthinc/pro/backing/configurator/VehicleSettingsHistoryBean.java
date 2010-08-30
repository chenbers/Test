package com.inthinc.pro.backing.configurator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ajax4jsf.model.KeepAlive;

import com.inthinc.pro.backing.dao.DateFormatBean;
import com.inthinc.pro.configurator.model.VehicleSettingHistoryBean;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.hessian.UserHessianDAO;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.configurator.VehicleSettingHistory;
@KeepAlive
public class VehicleSettingsHistoryBean {

	private ConfiguratorDAO configuratorDAO;
	private UserHessianDAO userHessianDAO;
	private List<VehicleSettingHistoryBean> vehicleSettingHistories;
	
	private Integer vehicleID;
	private Date startDate;
	private Date endDate;
	private DateFormatBean dateFormatBean;


	public VehicleSettingsHistoryBean() {

    	startDate = new Date();
    	endDate = new Date();
	}
	public Integer getVehicleID() {
		return vehicleID;
	}
	public void setVehicleID(Integer vehicleID) {
		this.vehicleID = vehicleID;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public DateFormatBean getDateFormatBean() {
		return dateFormatBean;
	}
	public void setDateFormatBean(DateFormatBean dateFormatBean) {
		this.dateFormatBean = dateFormatBean;
	}
	
	public List<VehicleSettingHistoryBean> getVehicleSettingHistories() {
		return vehicleSettingHistories;
	}
	public boolean isHasSettings(){
		return (vehicleSettingHistories != null) &&  !vehicleSettingHistories.isEmpty();
	}
	public void setVehicleSettingHistories(List<VehicleSettingHistoryBean> vehicleSettingHistories) {
		
		this.vehicleSettingHistories = vehicleSettingHistories;
	}
	public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
		this.configuratorDAO = configuratorDAO;
	}
	
	public Object displayHistory(){
		
		List<VehicleSettingHistory> vehicleSettingTrail = configuratorDAO.getVehicleSettingsHistory(vehicleID, startDate, endDate);
		for (VehicleSettingHistory vsh:vehicleSettingTrail){
			
			VehicleSettingHistoryBean vshb = new VehicleSettingHistoryBean();
			vshb.setVehicleSettingHistory(vsh);
			User user = userHessianDAO.findByID(vsh.getUserID());
			if(user != null){
				vshb.setUsername(user.getUsername());
			}
		}
//		makeupData();
		return null;
	}

	private void makeupData(){
		
		vehicleSettingHistories = new ArrayList<VehicleSettingHistoryBean>();
		for(int i=0;i<10;i++){
			VehicleSettingHistory vsh = new VehicleSettingHistory();
			vsh.setDeviceID(i);
			vsh.setModified(new Date());
			vsh.setNewValue("newValue");
			vsh.setOldValue("oldValue");
			vsh.setReason("reason"+i);
			vsh.setSettingID(i);
			vsh.setUserID(i*10);
			VehicleSettingHistoryBean vshb = new VehicleSettingHistoryBean();
			vshb.setVehicleSettingHistory(vsh);
			vshb.setUsername("username_"+i);
			vehicleSettingHistories.add(vshb);
		}
	}
}
