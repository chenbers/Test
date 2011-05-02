package com.inthinc.pro.backing.fwdcmd;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.dao.jdbc.FwdCmdSpoolWS;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.ForwardCommandType;
import com.inthinc.pro.util.MessageUtil;

public class WirelineKillMotorCommand extends WaysmartForwardCommand {
    public WirelineKillMotorCommand()
    {
        super();
    }
    public WirelineKillMotorCommand(Device device, String address, FwdCmdSpoolWS fwdCmdSpoolWS) {
        super(device, address, fwdCmdSpoolWS);
    }

    @Override
    public List<SelectItem> getSelectItems() {
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        selectItemList.add(new SelectItem(ForwardCommandType.WIRELINE_ENABLE_KILL_MOTOR,MessageUtil.getMessageString(ForwardCommandType.WIRELINE_ENABLE_KILL_MOTOR.toString())));
        selectItemList.add(new SelectItem(ForwardCommandType.WIRELINE_DISABLE_KILL_MOTOR,MessageUtil.getMessageString(ForwardCommandType.WIRELINE_DISABLE_KILL_MOTOR.toString())));
        return selectItemList;
    }
    
}
