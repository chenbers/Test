package com.inthinc.device.emulation.enums;

import com.inthinc.pro.automation.interfaces.IndexEnum;

public enum ConstDDB implements IndexEnum{

    DDB_DLSTATUS_APPROVED(1),
    DDB_DLSTATUS_NOT_APPROVED(2),

    DDB_DLTYPE_AUDIO_FILE(1),
    DDB_DLTYPE_DMM(2),
    DDB_DLTYPE_EMU(3),
    DDB_DLTYPE_SMTOOLS_FIRMWARE(4),
    DDB_DLTYPE_TIWI_FIRMWARE(5),
    DDB_DLTYPE_TIWIPRO_FIRMWARE(6),
    DDB_DLTYPE_DMMPRO(7),

    DDB_FILENAME_LEN(50),
    DDB_MD5SUM_LEN(32),
    DDB_REGEX_LEN(100),
    DDB_MODEL_LEN(50),
    DDB_TRIM_LEN(50), 
    ;

    private int index;
    
    private ConstDDB(int index){
        this.index = index;
    }

    @Override
    public Integer getIndex() {
        return index;
    }
}
