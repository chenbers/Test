package com.inthinc.device.emulation.utils;

import java.io.ByteArrayInputStream;

public class AutomationByteArrayInputStream extends ByteArrayInputStream {

    public AutomationByteArrayInputStream(byte[] buf, int offset, int length) {
        super(buf, offset, length);
    }

    public AutomationByteArrayInputStream(byte[] buf) {
        super(buf);
    }

    
    public int getPosition(){
        return pos;
    }

}
