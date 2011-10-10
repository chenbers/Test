package com.inthinc.pro.dao.jpa;

import java.util.Date;

import com.inthinc.pro.configurator.model.ForwardCommandParamType;
import com.inthinc.pro.configurator.model.ForwardCommandStatus;
import com.inthinc.pro.domain.ForwardCommandHttp;

public class ForwardCommandHttpBuilder {

	private final static Integer intDataNegative1 = -1;
	
    public static ForwardCommandHttp buildStringForwardCommand(String strData, Integer deviceID, Integer command){
    	ForwardCommandHttp forwardCommand = 
    		getBasicForwardCommand(ForwardCommandParamType.STRING, deviceID, command, getData(strData), strData, -1);
    	return forwardCommand;
    }
    private static byte[] getData(String strData){
        if (strData != null)
        {
            int len = strData.getBytes().length;
            byte[] dataBytes = new byte[len + 1];
            
            byte[] strDataBytes = strData.getBytes(); 
            for (int i = 0; i < len; i++)
            {
                dataBytes[i] = strDataBytes[i];
            }
            dataBytes[len] = 0x0;
            
            return dataBytes; 
        }
    	return null;
    }
    public static ForwardCommandHttp buildIntegerForwardCommand(Integer intData, Integer deviceID, Integer command){
    	ForwardCommandHttp forwardCommand = 
    		getBasicForwardCommand(ForwardCommandParamType.INTEGER, deviceID, command, getData(intData), null, intData);
    	return forwardCommand;
    }

    private static byte[] getData(Integer intData){
    	
    	if(intDataNegative1.equals(intData)) intData = null;
        if (intData == null) return null;
        
        byte[] dataBytes = new byte[4];
        dataBytes[3] = (byte) (intData & 0x000000FF);
        intData = intData >>> 8;
        dataBytes[2] = (byte) (intData & 0x000000FF);
        intData = intData >>> 8;
        dataBytes[1] = (byte) (intData & 0x000000FF);
        intData = intData >>> 8;
        dataBytes[0] = (byte) (intData & 0x000000FF);
        
        return dataBytes;
    }
    public static ForwardCommandHttp buildBinaryForwardCommand(byte[] data, Integer deviceID, Integer command){
    	ForwardCommandHttp forwardCommand = 
    		getBasicForwardCommand(ForwardCommandParamType.BINARY, deviceID, command, data, null, null);
    	return forwardCommand;
    	
    }
    private static ForwardCommandHttp getBasicForwardCommand(ForwardCommandParamType dataType, Integer deviceID,
														 Integer command, byte[] data, String strData,
													 	 Integer intData){
    	ForwardCommandHttp forwardCommand = new ForwardCommandHttp(deviceID, null,
    			null, null, command,
    			intData, strData, 1,
    			ForwardCommandStatus.STATUS_QUEUED, new Date(), new Date());
//    		new ForwardCommand(dataType, deviceID,address, command, data, strData, intData);
		/*
		 * http
		 * IF _intData = -1 THEN
			SET _intData  = null;
		   END IF;

			SELECT deviceID INTO _deviceID FROM device WHERE mcmid = _address AND STATUS = 1 LIMIT 1;

			IF _deviceID > 0 THEN
    			INSERT INTO fwd(deviceID, fwdCmd, fwdInt, fwdStr, tries, status, created, modified)
        		VALUES(_deviceID,  _command, _intData, _strData, tries+1, _QUEUED_STATUS, UTC_TIMESTAMP(), UTC_TIMESTAMP());
			END IF;
			SELECT LAST_INSERT_ID(); 
		 */
    	return forwardCommand;
    	
    }
//)
//BEGIN
//DECLARE _acctID INT DEFAULT NULL;
//DECLARE _deviceID INT DEFAULT NULL;
//DECLARE _vehicleID INT DEFAULT NULL;
//DECLARE _personID INT DEFAULT NULL;
//DECLARE _driverID INT DEFAULT NULL;
//DECLARE _QUEUED_STATUS INT DEFAULT 1;
//
//
//SELECT acctID, deviceID INTO _acctID, _deviceID FROM device WHERE imei = _address LIMIT 1;
//SELECT driverID, vehicleID INTO _driverID, _vehicleID FROM vddlog WHERE deviceID = _deviceID ORDER BY start DESC LIMIT 1;
//SELECT personID INTO _personID FROM driver WHERE driverID = _driverID;
//
//IF _deviceID IS NOT NULL AND _vehicleID IS NOT NULL THEN
//INSERT INTO Fwd_WSiridium
//	(command, data, datatype, status, deviceID, vehicleID, personID, driverID, created, modified)
//		VALUES(_command, _data,	_dataType, _QUEUED_STATUS, _deviceID, _vehicleID, _personID, _driverID, UTC_TIMESTAMP(), UTC_TIMESTAMP());
//
//    SELECT LAST_INSERT_ID();
//ELSE 
//    SELECT 0;
//END IF;
//END
}
