package com.inthinc.pro.dao.hibernate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.inthinc.pro.configurator.model.VehicleSetting;
import com.inthinc.pro.dao.jpa.ConfiguratorJPADAO;
import com.inthinc.pro.domain.settings.DesiredVehicleSetting;
import com.inthinc.pro.domain.settings.VehicleSettingHistory;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ConfiguratorHibernateDAOTest {
	private static final String SIMPLE_DATE_FORMAT = "yyyyMMdd";

	@Autowired
	private ConfiguratorJPADAO configuratorJPADAO;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getVehicleHistoryTest() {
		Date startTime = buildDateFromString("20110829");
		Date endTime = buildDateFromString("20110902");

		List<VehicleSettingHistory> vehicleSettingHistories = configuratorJPADAO
				.getVehicleSettingsHistory(2, startTime, endTime);
		for (VehicleSettingHistory e : vehicleSettingHistories)
			System.out.println("Found history: " + e.toString());

	}
	protected Date buildDateFromString(String strDate) {
		DateFormat df = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
		try {
			Date convertedDate = df.parse(strDate);
			return convertedDate;
		} catch (ParseException e) {
			return null;
		}
	}
	@Test
	public void getVehicleSettingTest() {
		VehicleSetting vehicleSetting = configuratorJPADAO
				.getVehicleSettings(2);
		System.out.println(vehicleSetting.toString());
	}
	@Test
	public void getVehicleSettingsForGroupDeep(){
		List<VehicleSetting> vehicleSettings = configuratorJPADAO.getVehicleSettingsByGroupIDDeep(2);
		for(VehicleSetting vehicleSetting : vehicleSettings){
			System.out.println(vehicleSetting.toString());
		}
	}
	@Test
	public void updateVehicleSettingsNoSettingsInMap(){
		Integer vehicleID = 2;
		Map<Integer, String> setMap = new HashMap<Integer, String>();
		Integer userID = 10087;
		String reason = "configuratorJPADAO testing";
		configuratorJPADAO.updateVehicleSettings(null,vehicleID, setMap, userID, reason);
	}
	@Test
	@Transactional
	public void updateVehicleSettingsOneSettingInMap(){
		Map<Integer,DesiredVehicleSetting> settings = configuratorJPADAO.findDesiredVehicleSettings(2);
		Integer vehicleID = 2;
		Map<Integer, String> setMap = new HashMap<Integer, String>();
		setMap.put(157, "3000 40 1");
		Integer userID = 10087;
		String reason = "configuratorJPADAO testing";
		configuratorJPADAO.updateVehicleSettings(settings.get(157).getDesiredVehicleSettingID(), vehicleID, setMap, userID, reason);
		setMap.put(157, "700 25 11");
		configuratorJPADAO.updateVehicleSettings(settings.get(157).getDesiredVehicleSettingID(), vehicleID, setMap, userID, reason);
	}

	public ConfiguratorJPADAO getConfiguratorJPADAO() {
		return configuratorJPADAO;
	}

	public void setConfiguratorJPADAO(ConfiguratorJPADAO configuratorJPADAO) {
		this.configuratorJPADAO = configuratorJPADAO;
	}
}
