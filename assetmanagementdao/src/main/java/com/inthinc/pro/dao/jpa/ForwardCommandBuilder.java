package com.inthinc.pro.dao.jpa;

import com.inthinc.pro.configurator.model.ForwardCommandParamType;
import com.inthinc.pro.domain.ForwardCommand;

public class ForwardCommandBuilder {

    public static ForwardCommand buildStringForwardCommand(String strData, Integer deviceID, Integer command, String address){
    	ForwardCommand forwardCommand = 
    		getBasicForwardCommand(ForwardCommandParamType.STRING, deviceID,address, command, getData(strData), strData, -1);
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
    public static ForwardCommand buildIntegerForwardCommand(Integer intData, Integer deviceID, Integer command, String address){
    	ForwardCommand forwardCommand = 
    		getBasicForwardCommand(ForwardCommandParamType.INTEGER, deviceID,address, command, getData(intData), null, intData);
    	return forwardCommand;
    }

    private static byte[] getData(Integer intData){
        if (intData != null)
        {
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
        return null;
    }
    public static ForwardCommand buildBinaryForwardCommand(byte[] data, Integer deviceID, Integer command, String address){
    	ForwardCommand forwardCommand = 
    		getBasicForwardCommand(ForwardCommandParamType.BINARY, deviceID,address, command, data, null, null);
    	return forwardCommand;
    	
    }
    private static ForwardCommand getBasicForwardCommand(ForwardCommandParamType dataType, Integer deviceID,
														 String address, Integer command, byte[] data, String strData,
													 	 Integer intData){
    	ForwardCommand forwardCommand = new ForwardCommand(dataType, deviceID,address, command, data, strData, intData);
    	return forwardCommand;
    	
    }
}
