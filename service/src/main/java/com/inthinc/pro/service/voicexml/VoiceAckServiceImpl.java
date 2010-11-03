package com.inthinc.pro.service.voicexml;


import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.service.VoiceAckService;
import com.inthinc.pro.util.StringUtil;

public class VoiceAckServiceImpl implements VoiceAckService {

    private static final String XML_DATA_FILE = "vxml/acknowledgevxml.xml";
    private String voxeoAudioURL;
    
    @Override
    public Response get(Integer msgID) throws IOException {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);
        
        String response = StringUtil.convertInputStreamToString(stream);
        response=String.format(response, getVoxeoAudioURL());
        
        //TODO Ack msgID here
        System.out.println(msgID);
        
        if (true)
            return Response.ok(response).build();
        return Response.status(Status.NOT_FOUND).build();
    }

    public String getVoxeoAudioURL() {
        return voxeoAudioURL;
    }

    public void setVoxeoAudioURL(String voxeoAudioURL) {
        this.voxeoAudioURL = voxeoAudioURL;
    }
}
