package com.inthinc.pro.service.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Holds an error.
 */
@XmlRootElement
public class WsError {
    private String message;

    public WsError() {
    }

    public WsError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
