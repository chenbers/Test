package com.inthinc.pro.automation.utils;


import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Level;

//TODO: Document
/**
 *
 * @author javadb.com
 */
public class ObjectReadWrite {
    
    /**
     * Example method for using the ObjectOutputStream class
     */
    public void writeObject(Object item, String filename) {
        
        ObjectOutputStream outputStream = null;
        
        try {
            //Construct the LineNumberReader object
            outputStream = new ObjectOutputStream(new FileOutputStream(filename));
            outputStream.writeObject(item);
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            //Close the ObjectOutputStream
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
	public ArrayList<Object> readObject(String filename) {
	
		ArrayList<Object> objects = new ArrayList<Object>();
	    ObjectInputStream inputStream = null;
	    
	    try {
	        
	        //Construct the ObjectInputStream object
	        inputStream = new ObjectInputStream(new FileInputStream(filename));
	        
	        Object obj = null;
	        while ((obj = inputStream.readObject()) != null) {
	            objects.add(obj);
	        }
	        
	     
	    } catch (EOFException ex) { //This exception will be caught when EOF is reached
	        MasterTest.print("End of file reached.", Level.DEBUG);
	    } catch (ClassNotFoundException ex) {
	        ex.printStackTrace();
	    } catch (FileNotFoundException ex) {
	        ex.printStackTrace();
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    } finally {
	        //Close the ObjectInputStream
	        try {
	            if (inputStream != null) {
	                inputStream.close();
	            }
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }
        return objects;
	}
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	HashMap<String, String> writeMe = new HashMap<String, String>();
    	writeMe.put("David", "Tanner");
        new ObjectReadWrite().writeObject(writeMe, "myFile.txt");
        ArrayList<Object> stuff = new ObjectReadWrite().readObject("myFile.txt");
        if (stuff.get(0) instanceof HashMap<?, ?>) {
        	System.out.println(stuff.get(0));
        }
    }
}