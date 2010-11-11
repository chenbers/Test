package com.inthinc.pro.web.selenium;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Data_Reader{

	private String inputFile;
	private HashMap<String, HashMap<String,HashMap<String, HashMap<String, String>>>> data_file;
	private HashMap<String, HashMap<String, HashMap<String, String>>> testCase;
	private HashMap<String, HashMap<String, String>> ID;
	private HashMap<String, String> keys;
	
	private final String testcase = "TestCase";

	private void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	private void read() throws IOException  {
		File inputWorkbook = new File(inputFile);
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			Sheet[] sheet = w.getSheets();
			// Loop over first 10 column and lines
			
			for (int s = 0; s < sheet.length; s++){
				if (sheet[s].getRows() != 0){
					Cell master = sheet[s].getCell(0, 0);
				}
				for (int r=1; r < sheet[s].getRows(); r++){
					Cell master = sheet[s].getCell(0, 0);
					for (int c=0; c < sheet[s].getColumns(); c++){
						add_data(sheet[s], master, sheet[s].getCell(0,r),
								sheet[s].getCell(c,0), sheet[s].getCell(c,r));
					}
				}
			}
	
		}catch (BiffException e){
			e.printStackTrace();
		}
	}
	
	private void add_sheet(String sheet){
		testCase = new HashMap<String,HashMap<String, HashMap<String, String>>>();
		data_file.put(sheet, testCase);
	}
		
	private void add_master(String sheet, String master){
		ID = new HashMap<String, HashMap<String, String>>();
		data_file.get(sheet).put(master, ID);
	}
	
	private void add_ID(String sheet, String master, String ID){
		keys = new HashMap<String, String>();
		data_file.get(sheet).get(master).put(ID, keys);
	}
	
	
	private void add_data( Sheet sheetSheet, Cell masterCell, Cell IDCell, Cell keyCell, Cell valueCell){
		String sheet = sheetSheet.getName();
		String master = masterCell.getContents();
		String ID = IDCell.getContents();
		String key = keyCell.getContents();
		String value = valueCell.getContents();
		

		if (!data_file.containsKey(sheet)){
			add_sheet(sheet);
		}
		if (!data_file.get(sheet).containsKey(master)){
			add_master(sheet, master);
		}
		if (!data_file.get(sheet).get(master).containsKey(ID)){
			add_ID(sheet, master, ID);
		}
		data_file.get(sheet).get(master).get(ID).put(key, value);
	}
	
	public HashMap<String, HashMap<String,HashMap<String, HashMap<String, String>>>> get_data(String input_file){
		setInputFile(input_file);
		data_file = new HashMap<String, HashMap<String,HashMap<String, HashMap<String, String>>>>();
		try{
			read();
		}catch( IOException e){
			e.printStackTrace();
		}
		return data_file;		
	}
	

	public static void main(String[] args) throws IOException {
		Data_Reader test = new Data_Reader();
		HashMap<String, HashMap<String,HashMap<String, HashMap<String, String>>>> data = test.get_data("c:/zohoData.xls");
		System.out.println(data.get("login").get("Username").get("larringt").get("Password"));
	}

}