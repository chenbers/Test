package com.inthinc.pro.automation.selenium;

import java.io.*;

public class DataSenderLib {

public static OutputStreamWriter OpenOutputStream( String path) {
	//Open OutStream to be used for test output
	try {
		OutputStream fout = new FileOutputStream(path);
		OutputStream bout = new BufferedOutputStream(fout);
		OutputStreamWriter out = new OutputStreamWriter(bout, "UTF-8");
		return out;
			} catch (UnsupportedEncodingException e) {
			System.out
			.println("This VM does not support the UTF-8 character set.");
			} catch (IOException ioException) {
			System.out.println(ioException.getMessage());
			}
	return null;
}

public static void writeLine (OutputStreamWriter ostream, String message){
	//This method writes one line to the file and a carriage return.
	//can be executed many times until OutputStream is closed
	try {
	ostream.write(message);
	ostream.write( " \r\n");
	} catch (UnsupportedEncodingException e) {
		System.out
		.println("This VM does not support the UTF-8 character set.");
		} catch (IOException ioException) {
		System.out.println(ioException.getMessage());
		}
}

public void closeOutputSteam (OutputStreamWriter ostream){
	//Close Stream 
	try {
	ostream.flush();
	ostream.close();
	} catch (UnsupportedEncodingException e) {
		System.out
		.println("This VM does not support the UTF-8 character set.");
		} catch (IOException ioException) {
		System.out.println(ioException.getMessage());
		}
}
	
}

