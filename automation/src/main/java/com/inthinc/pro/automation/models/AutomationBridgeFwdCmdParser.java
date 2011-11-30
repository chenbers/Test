package com.inthinc.pro.automation.models;

import java.io.ByteArrayInputStream;

import com.inthinc.pro.automation.deviceEnums.DeviceForwardCommands;
import com.inthinc.pro.automation.device_emulation.NoteManager;

public class AutomationBridgeFwdCmdParser {
    
    public void processCommands(String fwdCmds){
        ByteArrayInputStream bais = new ByteArrayInputStream(fwdCmds.getBytes());
        readBytes(bais, 4);
        countNullPointers(bais);
        int superHeader = bais.read();
        int otherByte = bais.read();
        countNullPointers(bais);
        DeviceForwardCommands fwdcmd = DeviceForwardCommands.valueOf(NoteManager.byteToInt(bais, 2));
        countNullPointers(bais);
        bais.read();
        countNullPointers(bais);
        NoteManager.byteToInt(bais, 2);
        countNullPointers(bais);
        if (bais.read()==106){
            String driverStr = extractString(bais);
        }
        
    }
    
    private String extractString(ByteArrayInputStream bais){
        String string = "";
        char next = (char) bais.read();
        while (next != 0x0){
            string += next;
        }
        return string;
    }
    
    private int countNullPointers(ByteArrayInputStream bais){
        int count = 0;
        while (bais.read() == 0x0){
            count ++;
        }
        return count;
    }
    
    private void readBytes(ByteArrayInputStream baib, int numOfBytes){
        for (; numOfBytes>0;numOfBytes--){
            baib.read();    
        }
    }

}
