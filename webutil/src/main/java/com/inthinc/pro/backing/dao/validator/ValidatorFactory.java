package com.inthinc.pro.backing.dao.validator;

import java.util.HashMap;
import java.util.Map;

public class ValidatorFactory {
	private DefaultValidator defaultValidator;
	private AddressIDValidator addressIDValidator;
	private DeviceIDValidator deviceIDValidator;
	private DriverIDValidator driverIDValidator;
	private GroupIDValidator groupIDValidator;
	private NoteIDValidator noteIDValidator;
	private PersonIDValidator personIDValidator;
	private UserIDValidator userIDValidator;
	private VehicleIDValidator vehicleIDValidator;
	private TablePrefIDValidator tablePrefIDValidator;
	private RedFlagAlertIDValidator redFlagAlertIDValidator;
	private ZoneAlertIDValidator zoneAlertIDValidator;
	private ZoneIDValidator zoneIDValidator;
	private RoleIDValidator roleIDValidator;
	private ReportPrefIDValidator reportPrefIDValidator;
	private CrashReportIDValidator crashReportIDValidator;
	private DriverorVehicleIDValidator driverorVehicleIDValidator;
	
	private static Map<ValidatorType, GenericValidator> validatorMap;
	
	public void init()
	{
		validatorMap = new HashMap<ValidatorType, GenericValidator>();
		validatorMap.put(ValidatorType.DEFAULT, defaultValidator);
		validatorMap.put(ValidatorType.ADDRESS, addressIDValidator);
		validatorMap.put(ValidatorType.DEVICE, deviceIDValidator);
		validatorMap.put(ValidatorType.DRIVER, driverIDValidator);
		validatorMap.put(ValidatorType.GROUP, groupIDValidator);
		validatorMap.put(ValidatorType.NOTE, noteIDValidator);
		validatorMap.put(ValidatorType.PERSON, personIDValidator);
		validatorMap.put(ValidatorType.RED_FLAG_ALERT, redFlagAlertIDValidator);
		validatorMap.put(ValidatorType.TABLE_PREF, tablePrefIDValidator);
		validatorMap.put(ValidatorType.USER, userIDValidator);
		validatorMap.put(ValidatorType.VEHICLE, vehicleIDValidator);
		validatorMap.put(ValidatorType.ZONE_ALERT, zoneAlertIDValidator);
		validatorMap.put(ValidatorType.ZONE, zoneIDValidator);
		validatorMap.put(ValidatorType.ROLE, roleIDValidator);
		validatorMap.put(ValidatorType.REPORT_PREF, reportPrefIDValidator);
		validatorMap.put(ValidatorType.CRASH_REPORT, crashReportIDValidator);
		validatorMap.put(ValidatorType.DRIVER_OR_VEHICLE, driverorVehicleIDValidator);
	}

	public static GenericValidator getValidator(ValidatorType validatorType) {
		return validatorMap.get(validatorType);
	}
	

	public AddressIDValidator getAddressIDValidator() {
		return addressIDValidator;
	}

	public void setAddressIDValidator(AddressIDValidator addressIDValidator) {
		this.addressIDValidator = addressIDValidator;
	}
	
	public DefaultValidator getDefaultValidator() {
		return defaultValidator;
	}

	public void setDefaultValidator(DefaultValidator defaultValidator) {
		this.defaultValidator = defaultValidator;
	}

	public UserIDValidator getUserIDValidator() {
		return userIDValidator;
	}

	public void setUserIDValidator(UserIDValidator userIDValidator) {
		this.userIDValidator = userIDValidator;
	}

	public DeviceIDValidator getDeviceIDValidator() {
		return deviceIDValidator;
	}

	public void setDeviceIDValidator(DeviceIDValidator deviceIDValidator) {
		this.deviceIDValidator = deviceIDValidator;
	}

	public DriverIDValidator getDriverIDValidator() {
		return driverIDValidator;
	}

	public void setDriverIDValidator(DriverIDValidator driverIDValidator) {
		this.driverIDValidator = driverIDValidator;
	}

	public GroupIDValidator getGroupIDValidator() {
		return groupIDValidator;
	}

	public void setGroupIDValidator(GroupIDValidator groupIDValidator) {
		this.groupIDValidator = groupIDValidator;
	}

	public PersonIDValidator getPersonIDValidator() {
		return personIDValidator;
	}

	public void setPersonIDValidator(PersonIDValidator personIDValidator) {
		this.personIDValidator = personIDValidator;
	}

	public VehicleIDValidator getVehicleIDValidator() {
		return vehicleIDValidator;
	}

	public void setVehicleIDValidator(VehicleIDValidator vehicleIDValidator) {
		this.vehicleIDValidator = vehicleIDValidator;
	}

	public RedFlagAlertIDValidator getRedFlagAlertIDValidator() {
		return redFlagAlertIDValidator;
	}

	public void setIdValidator(
			RedFlagAlertIDValidator redFlagAlertIDValidator) {
		this.redFlagAlertIDValidator = redFlagAlertIDValidator;
	}

	public ZoneAlertIDValidator getZoneAlertIDValidator() {
		return zoneAlertIDValidator;
	}

	public void setIdValidator(ZoneAlertIDValidator zoneAlertIDValidator) {
		this.zoneAlertIDValidator = zoneAlertIDValidator;
	}

	public TablePrefIDValidator getTablePrefIDValidator() {
		return tablePrefIDValidator;
	}

	public void setTablePrefIDValidator(TablePrefIDValidator tablePrefIDValidator) {
		this.tablePrefIDValidator = tablePrefIDValidator;
	}

	public ZoneIDValidator getZoneIDValidator() {
		return zoneIDValidator;
	}

	public void setZoneIDValidator(ZoneIDValidator zoneIDValidator) {
		this.zoneIDValidator = zoneIDValidator;
	}

	public RoleIDValidator getRoleIDValidator() {
		return roleIDValidator;
	}

	public void setRoleIDValidator(RoleIDValidator roleIDValidator) {
		this.roleIDValidator = roleIDValidator;
	}

	public ReportPrefIDValidator getReportPrefIDValidator() {
		return reportPrefIDValidator;
	}

	public void setReportPrefIDValidator(ReportPrefIDValidator reportPrefIDValidator) {
		this.reportPrefIDValidator = reportPrefIDValidator;
	}

	public CrashReportIDValidator getCrashReportIDValidator() {
		return crashReportIDValidator;
	}

	public void setCrashReportIDValidator(
			CrashReportIDValidator crashReportIDValidator) {
		this.crashReportIDValidator = crashReportIDValidator;
	}

	public NoteIDValidator getNoteIDValidator() {
		return noteIDValidator;
	}

	public void setNoteIDValidator(NoteIDValidator noteIDValidator) {
		this.noteIDValidator = noteIDValidator;
	}

	public DriverorVehicleIDValidator getDriverorVehicleIDValidator() {
		return driverorVehicleIDValidator;
	}

	public void setDriverorVehicleIDValidator(
			DriverorVehicleIDValidator driverorVehicleIDValidator) {
		this.driverorVehicleIDValidator = driverorVehicleIDValidator;
	}


}
