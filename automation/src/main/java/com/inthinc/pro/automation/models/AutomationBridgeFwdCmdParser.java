package com.inthinc.pro.automation.models;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.automation.deviceEnums.DeviceForwardCommands;
import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.models.WaysmartClasses.EventHeader;
import com.inthinc.pro.automation.models.WaysmartClasses.ForwardCommandEvent;
import com.inthinc.pro.automation.models.WaysmartClasses.ForwardCommandEventEx;
import com.inthinc.pro.automation.models.WaysmartClasses.HttpHeader;
import com.inthinc.pro.automation.models.WaysmartClasses.MultiForwardCmd;
import com.inthinc.pro.automation.utils.AutomationByteArrayInputStream;
import com.inthinc.pro.automation.utils.MasterTest;

public class AutomationBridgeFwdCmdParser {
    

    public static final int MULTIPLE_FORWARDS = 0x46434D44;
    public static final WaysmartClasses classes = new WaysmartClasses();
    private static final int SENDER_SATELLITE = 109;
    private static final int EVENT_FORWARD_COMMAND = 0x0E;
    
    public static List<MultiForwardCmd> processCommands(String fwdCmds, DeviceNoteTypes typeSent){
        List<MultiForwardCmd> commands = new ArrayList<MultiForwardCmd>();
        String[] strings = fwdCmds.split("FCMD");
        for (String string: strings){
            if (string.isEmpty() || string.equals("[OK]")){
                continue;
            }
            AutomationByteArrayInputStream bais = new AutomationByteArrayInputStream(("FCMD" + string).getBytes());
            MultiForwardCmd packetHeader = classes.new MultiForwardCmd(bais);
            commands.add(packetHeader);
            if (packetHeader.m_ID == MULTIPLE_FORWARDS){
                for (int i=0;i<packetHeader.m_nCount;i++){
                    HttpHeader forwardHeader = classes.new HttpHeader(bais);
                    
                    if (forwardHeader.nForwardCommand == -1){
                        continue;
                    } else if (DeviceForwardCommands.UPDATE_DRIVER_CHANGE_HISTORY_HTTP.isEqual(forwardHeader.exFwdCmdID)){
                        packetHeader.events.add(updateDriverChangeHistoryHttp(bais, forwardHeader));
                    } else {
                        packetHeader.events.add(standardProcess(bais, packetHeader, forwardHeader));
                    }
                }
            }
        }
        return commands;
    }
    
    private static ForwardCommandEventEx standardProcess(AutomationByteArrayInputStream bais, MultiForwardCmd packetHeader, HttpHeader forwardHeader){
        /* uniqIdLen is the length of the unique id (eg. timestamp) at the
        end of forward commands, unless we are sending SAT_EVENT_NEWDRIVER_HOSRULE
        in which case the portal will not be following this convention. This is 
        becuase of backwards compatability.
    */
        int uniqIdLen = 8;
        ForwardCommandEventEx pEventEx = classes.new ForwardCommandEventEx(forwardHeader.nForwardCommand);
        pEventEx.m_header.m_nSender = SENDER_SATELLITE;
        pEventEx.m_header.m_nEventType = EVENT_FORWARD_COMMAND;

        // We are not going to read the extra 8 bytes, unique id after the forward command.
        if (forwardHeader.exFwdCmdID >= 340 || forwardHeader.exFwdCmdID <= 346) {
                uniqIdLen = 0;
        } else if (packetHeader.m_nCount > 1) {
                uniqIdLen = 0;
        }


        /*
        The value for m_header.m_nLength is length of all the data after the event Header.

        So, since ForwardCommandEventEx contains EventHeader we remove that.
        and it contains 1 byte instead of 4 bytes for m_pData we subtract 1
        and add 4 for m_pData.
        Then add 8 for the UniqueID for the command, and what ever additional data in between.
        */
        pEventEx.m_header.m_nLength = (ForwardCommandEventEx.SIZE - EventHeader.SIZE - 1) + 4 + uniqIdLen + forwardHeader.nLength;

        pEventEx.addData(forwardHeader.m_data);

        int bytesRead = 0;
        while (bytesRead < forwardHeader.nLength + uniqIdLen) {
            pEventEx.addData(bais);
            bytesRead ++;
        }
        return pEventEx;
    }
    
    private static ForwardCommandEvent updateDriverChangeHistoryHttp(AutomationByteArrayInputStream bais, HttpHeader forwardHeader){
        MasterTest.print("UPDATE_DRIVER_CHANGE_HISTORY_HTTP");
        int bytesRead = 0;
        ForwardCommandEvent Event = classes.new ForwardCommandEvent(forwardHeader.nForwardCommand);
        int length = 0;
        while (bytesRead < forwardHeader.nLength) {
            length = (1024 < (forwardHeader.nLength - bytesRead))? 1024 : (forwardHeader.nLength - bytesRead);
            Event.addData(bais, length);
            bytesRead += length;
        }
            
        Event.m_header.m_nSender = SENDER_SATELLITE;
        Event.m_header.m_nEventType = EVENT_FORWARD_COMMAND;
        Event.m_header.m_nLength = ForwardCommandEvent.SIZE - EventHeader.SIZE;
        Event.addData(forwardHeader.m_data);
        MasterTest.print(Event.toString());
        return Event;
    }
}
