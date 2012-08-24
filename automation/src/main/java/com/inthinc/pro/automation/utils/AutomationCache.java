package com.inthinc.pro.automation.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.inthinc.pro.automation.logging.Log;

public class AutomationCache {
	
	private File file;
	private HashMap<String, String> map;
	private String fileName;
	private static final String divider = "=autoDivider=";
	
	public AutomationCache(){
		map = new HashMap<String, String>();
	}
	
	private void parseArray(List<String> array) {
		for (String entry : array){
			String[] split = entry.split(divider);
			map.put(split[0], split[1]);
		}
	}

	public String getValue(String key){
		if (map.isEmpty()){
			List<String> array = readFromFile();
			parseArray(array);
		}
		if (map.containsKey(key)) {
			return map.get(key);
		} else {
			return "";
		}
	}

	private ArrayList<String> readFromFile(){
		ArrayList<String> results = new ArrayList<String>();
		BufferedReader in = null;
		try {
	        file = new File(fileName);
	        if (!file.exists()){
	        	file.createNewFile();
	        }
	        in = new BufferedReader(new FileReader(file));
			
			while (in.ready()) {
				results.add(in.readLine());
			}
		} catch (IOException e) {
			Log.debug(e);
		} finally {
			if (in != null){
				try {
					in.close();
				} catch (IOException e) {
					Log.debug(e);
				}
			}
		}
		return results;
	}

	public void setFileName(String fileName) {
		this.fileName = "target/cache/" + fileName;
	}
	
	public String getFileName(){
		return fileName;
	}

	public void cache(String key, String value) {
		if (map.containsKey(key)){
			return;
		}
		map.put(key, value);
		BufferedWriter out = null;
		try {
			FileWriter fw = new FileWriter(file, true);
			out = new BufferedWriter(fw);
			out.write(String.format("\n%s%s%s", key, divider, value));
			out.flush();
		} catch (IOException e) {
			Log.error(e);
		} finally {
			if (out != null){
				try {
					out.close();
				} catch (IOException e) {
					Log.error(e);
				}
			}
		}
	}
}
