package com.inthinc.pro.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.math.BigInteger;



public class RFIDBean {

	private String rfidCSVFile;
	
	public void init()
	{
		
	}
	
	public Long findRFID(Long barcode, boolean useFob)
	{
		File file = new File(rfidCSVFile);
		
		FileReader fr;
		
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		LineNumberReader r = new LineNumberReader(fr);
		
		String line = null;
		
		try {
			line = r.readLine();
			while (line!=null)
			{
				String pair[] = line.split(",");
				
				Long i = Long.parseLong(pair[0]);
				if (barcode.equals(i))
				{
					int id=1;
					if (useFob)
						id=2;
					BigInteger bi = new BigInteger(pair[id],16);
					return bi.longValue();
				}
				
				line = r.readLine();			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
		
		return null;
	}
	
	public Long findBarcode(Long rfid)
	{
		Long barcode=null;

		File file = new File(rfidCSVFile);
		
		FileReader fr;
		
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		LineNumberReader r = new LineNumberReader(fr);
		
		String line = null;
		
		try {
			line = r.readLine();
			while (line!=null)
			{
				String pair[] = line.split(",");
				barcode=Long.parseLong(pair[0]);
				BigInteger card = new BigInteger(pair[1],16);
				BigInteger fob = new BigInteger(pair[2],16);
				
				if (card.longValue()==rfid.longValue()
					|| fob.longValue()==rfid.longValue())
				{
					return barcode.longValue();
				}
				
				line = r.readLine();			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		

		
		return null;
	}
	
	public String getRfidCSVFile() {
		return rfidCSVFile;
	}
	public void setRfidCSVFile(String rfidCSVFile) {
		this.rfidCSVFile = rfidCSVFile;
	}
	
}