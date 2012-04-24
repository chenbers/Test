package com.inthinc.pro.automation.resources;


import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.inthinc.pro.automation.logging.Log;

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
            Log.error(ex);
        } catch (IOException ex) {
            Log.error(ex);
        } finally {
            //Close the ObjectOutputStream
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException ex) {
                Log.error(ex);
            }
        }
    }
    
    
	public ArrayList<Object> readObject(String filename) {
	
		ArrayList<Object> objects = new ArrayList<Object>();
	    ObjectInputStream inputStream = null;
	    
	    try {
	        
	        //Construct the ObjectInputStream object
	        System.out.println(filename);
	        inputStream = new ObjectInputStream(new FileInputStream(filename));
	        
	        Object obj = null;
	        while ((obj = inputStream.readObject()) != null) {
	            objects.add(obj);
	        }
	    } catch (EOFException ex) { //This exception will be caught when EOF is reached
	        Log.debug("End of file reached.");
	    } catch (ClassNotFoundException ex) {
	        Log.error(ex);
	    } catch (FileNotFoundException ex) {
	        try {
                inputStream = new ObjectInputStream(this.getClass().getResource(filename).openStream());
                Object obj = null;
                while ((obj = inputStream.readObject()) != null) {
                    objects.add(obj);
                }
            } catch (IOException e) {
                Log.error(e);
            } catch (ClassNotFoundException e) {
                Log.error(e);
            }
	        Log.error(ex);
	    } catch (IOException ex) {
	        Log.error(ex);
	    } finally {
	        //Close the ObjectInputStream
	        try {
	            if (inputStream != null) {
	                inputStream.close();
	            }
	        } catch (IOException ex) {
	            Log.error(ex);
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
        	Log.info(stuff.get(0));
        }
    }
}