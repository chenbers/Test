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
	
	private void setInputFile(String inputFile) {
		this.inputFile = inputFile; //Set the Input file, makes for easy reuse
	}

	private void read() throws IOException  {
		File inputWorkbook = new File(inputFile);
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			Sheet[] sheet = w.getSheets(); // Get the sheets as an array
			Cell master = null; // Establish the first master
			for (int s = 0; s < sheet.length; s++){ // Loop through the sheets
				if (sheet[s].getRows() != 0){ // If there are no rows, it is an empty sheet
					master = sheet[s].getCell(0, 0); // Return the heading in the first column as our master key to that sheet
				}
				for (int r=1; r < sheet[s].getRows(); r++){// Loop through each row
					for (int c=0; c < sheet[s].getColumns(); c++){ // Now loop through every column for this row
						add_data(sheet[s], master, sheet[s].getCell(0,r),sheet[s].getCell(c,0), sheet[s].getCell(c,r));
						// Send the data for what Sheet, master, first column, and then the first row for that column
						// as the header, and finally the cell its self as the value
					}
				}
			}
	
		}catch (BiffException e){
			e.printStackTrace(); // If we crash, print a stack trace
		}
	}
	
	private void add_sheet(String sheet){ // Add the Sheet to our HashMap
		testCase = new HashMap<String,HashMap<String, HashMap<String, String>>>();// necessary to instantiate a value for the key
		data_file.put(sheet, testCase); // add the sheet and new value to our HashMap
	}
		
	private void add_master(String sheet, String master){
		ID = new HashMap<String, HashMap<String, String>>(); // necessary to instantiate a value for the key
		data_file.get(sheet).put(master, ID); // add the master label and then the new ID value to our HashMap
	}
	
	private void add_ID(String sheet, String master, String ID){
		keys = new HashMap<String, String>(); // necessary to instantiate a value for the key
		data_file.get(sheet).get(master).put(ID, keys); // add the ID from the first column x row and new value to our HashMap
	}
	
	
	private void add_data( Sheet sheetSheet, Cell masterCell, Cell IDCell, Cell keyCell, Cell valueCell){ // Add values to the HashMap
		//Get the name for ease of refence for each entry
		String sheet = sheetSheet.getName();
		String master = masterCell.getContents();
		String ID = IDCell.getContents();
		String key = keyCell.getContents();
		String value = valueCell.getContents();
		
		//Make sure our sheet is added first
		if (!data_file.containsKey(sheet)){
			add_sheet(sheet);
		}
		//Then make sure our Master is a value for the sheet
		if (!data_file.get(sheet).containsKey(master)){
			add_master(sheet, master);
		}
		//Finally add our individual ID to the master key
		if (!data_file.get(sheet).get(master).containsKey(ID)){
			add_ID(sheet, master, ID);
		}
		data_file.get(sheet).get(master).get(ID).put(key, value); // Add the row data as values, with the column headers as keys
	}
	
	public HashMap<String, HashMap<String,HashMap<String, HashMap<String, String>>>> get_data(String input_file){
		setInputFile(input_file); //set the input file
		data_file = new HashMap<String, HashMap<String,HashMap<String, HashMap<String, String>>>>(); //Instantiate our overall Map
		try{
			read();//Try to read the data and create the HashMaps
		}catch( IOException e){
			e.printStackTrace(); //Oops we broke something
		}
		return data_file; // Return the newly created HashMap		
	}
	

	public static void main(String[] args) throws IOException {
		Data_Reader test = new Data_Reader(); // Instantiate our class
		HashMap<String, HashMap<String,HashMap<String, HashMap<String, String>>>> data = test.get_data("c:/zohoData.xls"); //Get the data
		System.out.println(data.get("login").get("Username").get("larringt").get("Password")); // Test that it works
	}

}