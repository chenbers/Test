package com.inthinc.pro.scheduler.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CleanMessageTest {
	
	@Test
	public void testCleanMessage(){
		
		String messageWithQuotes = "Hello, I am a message with a ' and a \" in it";
		String cleanedMessage = cleanMessage(messageWithQuotes);
		assertEquals("Hello, I am a message with a  and a  in it",cleanedMessage);
	}
    private String cleanMessage(String messageText){
    	return messageText.replaceAll("[\"']","");
    }


}
