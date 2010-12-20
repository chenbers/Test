/**********************************************************
 * @author dtanner
 *
 */

package com.inthinc.pro.web.selenium;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class DataReaderLib{

	private String inputFile;
	private static HashMap<String, HashMap<String, HashMap<String, String>>> data_file;
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
			for (int s = 0; s < sheet.length; s++){ // Loop through the sheets
				for (int r=1; r < sheet[s].getRows(); r++){// Loop through each row
					for (int c=0; c < sheet[s].getColumns(); c++){ // Now loop through every column for this row
						add_data(sheet[s], sheet[s].getCell(0,r),sheet[s].getCell(c,0), sheet[s].getCell(c,r));
						// Send the data for what Sheet, first column, and then the first row for that column
						// as the header, and finally the cell its self as the value
					}
				}
			}
	
		}catch (BiffException e){
			e.printStackTrace(); // If we crash, print a stack trace
		}
	}
	
	private void add_sheet(String sheet){ // Add the Sheet to our HashMap
		ID = new HashMap<String,HashMap<String, String>>();// necessary to instantiate a value for the key
		data_file.put(sheet, ID); // add the sheet and new value to our HashMap
	}
		
	private void add_ID(String sheet, String ID){
		keys = new HashMap<String, String>(); // necessary to instantiate a value for the key
		data_file.get(sheet).put(ID, keys); // add the ID from the first column x row and new value to our HashMap
	}
	
	
	private void add_data( Sheet sheetSheet, Cell IDCell, Cell keyCell, Cell valueCell){ // Add values to the HashMap
		// Get the name for ease of refence for each entry
		String sheet = sheetSheet.getName();
		String ID = IDCell.getContents();
		String key = keyCell.getContents();
		String value = valueCell.getContents();
		
		// Make sure our sheet is added first
		if (!data_file.containsKey(sheet)){
			add_sheet(sheet);
		}
		// Finally add our individual ID to the master key
		if (!data_file.get(sheet).containsKey(ID)){
			add_ID(sheet, ID);
		}
		data_file.get(sheet).get(ID).put(key, value); // Add the row data as values, with the column headers as keys
	}
		
	public HashMap<String, HashMap<String, String>> get_testcase_data(String input_file, String testCase){
		if (inputFile == null || input_file.compareTo(inputFile) != 0){ // Create a new data_file if we don't have one, or if we are
																		// using a new file
			setInputFile(input_file);
			data_file = new HashMap<String, HashMap<String,HashMap<String, String>>>(); // Instantiate our overall Map
			
			try{
				read();// Try to read the data and create the HashMaps
			}catch( IOException e){
				e.printStackTrace(); // Oops we broke something
			}	
		}
			
		String sheet = "";
		ID = new HashMap<String,HashMap<String,String>>(); // Instantiate a new HashMap for the data to return
		
		Collection<String> sheets = data_file.keySet(); // get the sheets for iteration
		Iterator<String> itr = sheets.iterator(); // create the actual iterator
		
		while(itr.hasNext()){ // iterate through the sheets 
			sheet = (String) itr.next(); // get the next sheet name
			if (data_file.get(sheet).containsKey(testCase)){ // if the test case we want is in the sheet, add it for returning
				ID.put(sheet, data_file.get(sheet).get(testCase));
			}
		}
		return ID;
	}
	

	public static void main(String[] args) throws IOException {
		DataReaderLib test = new DataReaderLib(); // Instantiate our class
		HashMap<String,HashMap<String, String>> data = test.get_testcase_data("c:/Tiwi_data.xls", "TC1240"); // Get the data
		System.out.println(data.get("Login").get("USERNAME")); // Test that it works
	}
}