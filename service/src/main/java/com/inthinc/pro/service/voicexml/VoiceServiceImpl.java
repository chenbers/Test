package com.inthinc.pro.service.voicexml;


import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import com.inthinc.pro.dao.AlertMessageDAO;
import com.inthinc.pro.service.VoiceService;
import com.inthinc.pro.util.StringUtil;


public class VoiceServiceImpl implements VoiceService {

    private static final String VXML_ACK_FILE = "vxml/voicealertvxml.xml";
    private static final String VXML_NOACK_FILE = "vxml/voicealertnoackvxml.xml";
    private String voxeoAudioURL;
    private AlertMessageDAO alertMessageDAO;
    
    @Override
    public Response get(HttpServletRequest context, Integer msgID, String msg, Integer ack) throws IOException {

        String response;
        //TODO log callback for msgID?
        if (ack!=null && ack!=0)
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            StringBuffer sb = context.getRequestURL();
            sb.setLength(sb.length()-context.getPathInfo().length());
            sb.insert(sb.indexOf("://")+3, authentication.getName() + ":" + authentication.getCredentials() + "@");
            sb.append("/voiceack/");
            sb.append(msgID);

            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(VXML_ACK_FILE);
            response = String.format(StringUtil.convertInputStreamToString(stream), getVoxeoAudioURL(), sb.toString(), msg);
        }
        else
        {
            if(msg == null) msg = "hello";
            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(VXML_NOACK_FILE);
            response = String.format(StringUtil.convertInputStreamToString(stream), getVoxeoAudioURL(), msg);
            getAlertMessageDAO().acknowledgeMessage(msgID);
        }
        return Response.ok(response).build();
    }

    public String getVoxeoAudioURL() {
        return voxeoAudioURL;
    }

    public void setVoxeoAudioURL(String voxeoAudioURL) {
        this.voxeoAudioURL = voxeoAudioURL;

    }

    public AlertMessageDAO getAlertMessageDAO() {
        return alertMessageDAO;
    }

    public void setAlertMessageDAO(AlertMessageDAO alertMessageDAO) {
        this.alertMessageDAO = alertMessageDAO;
    }
}
