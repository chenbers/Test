package com.inthinc.pro.backing.fwdcmd;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.dao.jdbc.FwdCmdSpoolWS;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.ForwardCommandType;
import com.inthinc.pro.util.MessageUtil;

public class WirelineDoorAlarmCommand extends WaysmartForwardCommand {
    public WirelineDoorAlarmCommand()
    {
        super();
    }
    public WirelineDoorAlarmCommand(Device device, String address, FwdCmdSpoolWS fwdCmdSpoolWS) {
        super(device, address, fwdCmdSpoolWS);
    }

    
    @Override
    public List<SelectItem> getSelectItems() {
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        selectItemList.add(new SelectItem(ForwardCommandType.WIRELINE_ENABLE_DOOR_ALARM,MessageUtil.getMessageString(ForwardCommandType.WIRELINE_ENABLE_DOOR_ALARM.toString())));
        selectItemList.add(new SelectItem(ForwardCommandType.WIRELINE_DISABLE_DOOR_ALARM,MessageUtil.getMessageString(ForwardCommandType.WIRELINE_DISABLE_DOOR_ALARM.toString())));
        return selectItemList;
    }

}
