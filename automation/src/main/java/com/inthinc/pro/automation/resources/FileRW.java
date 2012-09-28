package com.inthinc.pro.automation.resources;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;

import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.utils.AutomationStringUtil;

public class FileRW {
	
	private static BufferedImage image;
	private String fileName;
	private BufferedReader in;
//	private InputStream stream;

	public FileRW(String fileName){
		Log.debug(fileName);
		this.fileName = fileName;
	}
	public FileRW(){
	}
	
//	private void openWriter(){
//		try {
//			;
//			out = ClassLoader.getSystemResourceAsStream(fileName);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) { 
//			e.printStackTrace();
//		}
//	}
	
	private void openReader(){
		openReader(this.getClass());
	}
	
	private void openReader(Class<?> local){
		try {
			String location;
			try {
				location = local.getResource(fileName).getFile();
			} catch (NullPointerException e){
				location = fileName;
			}
	        location = new URLCodec().decode(location);
	        File file = new File(location);
            in = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            Log.info(e);
            throw new NullPointerException(fileName + " was not found");
        } catch (DecoderException e) {
            Log.info(e);
            throw new NullPointerException(fileName + " was not found");
        }
	}
	
//	public void write(String fileName, String text){
//		this.fileName = fileName;
//		try {
//			openWriter();
//			out.write(text);
//			out.newLine();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally{
//			try {
//				out.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	public ArrayList<String> read(Class<?> localized, String fileName){
		this.fileName = fileName;
		openReader(localized);
		return readFromFile();
	}
	
	private ArrayList<String> readFromFile(){
		ArrayList<String> results = new ArrayList<String>();
		try {
			while (in.ready()){
				results.add(in.readLine());
			}
		} catch (IOException e) {
			Log.debug(AutomationStringUtil.toString(e));
		}
		close();
		return results;
	}

	public ArrayList<String> read(String fileName){
		this.fileName = fileName;
		openReader();
		return readFromFile();
	}
	
	
	public void close(){
		try {
//			fis.close();
//			bis.close();
//			stream.close();
			in.close();
		} catch (IOException e) {
			Log.debug(AutomationStringUtil.toString(e));
		}
	}
	
	public static void createFile(String fileName){
		
		// Create a File object
		File file = new File(fileName);

		boolean success = false;
		
		try {
            // Create file on disk (if it doesn't exist)
            success = file.createNewFile();
        } catch (IOException e) {
            Log.debug(AutomationStringUtil.toString(e));
        }

        if (success) {
        	Log.info("File did not exist and was created.\n");
        } else {
        	Log.info("File already exists.\n");
        }
	}
	
	public static BufferedImage getImage(String fileName) {
		try {
			image = ImageIO.read(new File(fileName));
			return image;
		} catch (IOException e) {
			Log.debug(AutomationStringUtil.toString(e));
		}
		return null;
	}
	
	public static ImageIcon getImageAsIcon(String fileName, String description) {
		URL imgURL = FileRW.class.getResource(fileName);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		}else {
			Log.error("Couldn't find file: " + fileName);
			return null;
		}
	}
}
