package com.inthinc.pro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthinc.pro.domain.ForwardCommand;
import com.inthinc.pro.repository.ForwardCommandRepository;

@Service
public class ForwardCommandService {
    @Autowired
    ForwardCommandRepository forwardCommandRepository;
    
	public Number getForwardCommandCount(){
		return forwardCommandRepository.count();
	}

	public ForwardCommand getForwardCommandByID(Integer id){
		return forwardCommandRepository.findByID(id);
	}
	public List<ForwardCommand> getForwardCommandsByVehicleID(Integer vehicleID){
		return forwardCommandRepository.getForwardCommandsByVehicleID(vehicleID);
	}
	public ForwardCommand queueForwardCommand(ForwardCommand forwardCommand){
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
		/* iridium
		 * 	DECLARE _acctID INT DEFAULT NULL;
			DECLARE _deviceID INT DEFAULT NULL;
			DECLARE _vehicleID INT DEFAULT NULL;
			DECLARE _personID INT DEFAULT NULL;
			DECLARE _driverID INT DEFAULT NULL;
			DECLARE _QUEUED_STATUS INT DEFAULT 1;
			
			
			SELECT acctID, deviceID INTO _acctID, _deviceID FROM device WHERE imei = _address LIMIT 1;
			SELECT driverID, vehicleID INTO _driverID, _vehicleID FROM vddlog WHERE deviceID = _deviceID ORDER BY start DESC LIMIT 1;
			SELECT personID INTO _personID FROM driver WHERE driverID = _driverID;
			
			IF _deviceID IS NOT NULL AND _vehicleID IS NOT NULL THEN
			INSERT INTO Fwd_WSiridium
				(command, data, datatype, status, deviceID, vehicleID, personID, driverID, created, modified)
					VALUES(_command, _data,	_dataType, _QUEUED_STATUS, _deviceID, _vehicleID, _personID, _driverID, UTC_TIMESTAMP(), UTC_TIMESTAMP());
			
			    SELECT LAST_INSERT_ID();
			ELSE 
			    SELECT 0;
			END IF;

		 */
		return forwardCommandRepository.save(forwardCommand);
	}
}
