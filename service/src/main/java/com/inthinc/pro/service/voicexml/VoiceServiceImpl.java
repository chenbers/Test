package com.inthinc.pro.service.voicexml;


import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import javax.servlet.http.HttpServletRequest;
import com.inthinc.pro.service.VoiceService;
import com.inthinc.pro.util.StringUtil;


public class VoiceServiceImpl implements VoiceService {

    private static final String XML_DATA_FILE = "vxml/voicealertvxml.xml";
    private String voxeoAudioURL;
    
    @Override
    public Response get(HttpServletRequest context, Integer msgID, String msg) throws IOException {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);
        String response = StringUtil.convertInputStreamToString(stream);
                
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        
        StringBuffer sb = context.getRequestURL();
        sb.setLength(sb.length()-context.getPathInfo().length());
        sb.insert(sb.indexOf("://")+3, authentication.getName() + ":" + authentication.getCredentials() + "@");
        sb.append("/voiceack/");
        sb.append(msgID);
                
        response = String.format(response, getVoxeoAudioURL(), sb.toString(), msg);
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
