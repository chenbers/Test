package com.fusioncharts.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {
	
	public String checkForMaliciousCharaters(String fileName) {
		 
		fileName = fileName.toLowerCase();
		
		Pattern pattern = Pattern.compile("[<>{}\\[\\];\\&]");
		
		String maliciousCharaters = "[<>{}\\[\\];\\&]";
		
		Matcher match = pattern.matcher(fileName);
		 
		 if(match.find()) {
			 fileName = fileName.replaceAll(maliciousCharaters, " "); 
			 return fileName;
		 }
		 
		 return fileName;
		 
	}

}
