package com.inthinc.pro.scheduler.dispatch;
/*
 * For alert phone calls
 * 
 *  For mocking up calls pick MockCallServiceMessageSender
 *  For Voxeo pick VoxeoCallServiceMessageSender
 *  
 *  in tiwipro.properties
 */
public interface CallServiceMessageSender {
    
    public void sendMessage(String phoneNumber, String messageText, Integer msgID, Boolean acknowledge);
}
