package com.inthinc.pro.automation.utils;

import org.apache.commons.httpclient.HttpStatus;

public class AutoHTTPException extends Exception {
    


    /**
     * 
     */
    private static final long serialVersionUID = 5512026716664597535L;
    
    public AutoHTTPException(String message, int statusCode){
        this.setReason(message);
        this.setReasonCode(statusCode);
    }
    
    @Override
    public String toString(){
    	return String.format("Code: %d\n%s", getReasonCode(), getReason());
    }
    
    /**
     * Sets the text description of the reason for an exception.
     *
     * @param reason The reason for the exception.
     *
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Get the text description of the reason for an exception.
     *
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the status code description of the reason for an exception.
     *
     * @param code The reason for the exception.  This is intended to be an
     *  HTTP status code.
     *
     */
    public void setReasonCode(int code) {
        reasonCode = code;
    }

    /**
     * Get the status code description of the reason for an exception.
     *
     */
    public int getReasonCode() {
        return this.reasonCode;
    }

    /**
     * A "reason" string provided for compatibility with older clients.
     *
     */
    private String reason;

    /**
     * Reason code for compatibility with older clients.
     *
     */
    private int reasonCode = HttpStatus.SC_OK;

}
