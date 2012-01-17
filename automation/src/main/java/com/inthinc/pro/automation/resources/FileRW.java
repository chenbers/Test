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

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.utils.StackToString;

public class FileRW {
	
	private final static Logger logger = Logger.getLogger(FileRW.class);
	private static BufferedImage image;
	private String fileName;
	private BufferedReader in;
//	private InputStream stream;

	public FileRW(String fileName){
		logger.debug(fileName);
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
	    File file = new File(fileName);
		try {
            in = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            logger.info(StackToString.toString(e));
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

	public ArrayList<String> read(String fileName){
		this.fileName = fileName;
		openReader();
		ArrayList<String> results = new ArrayList<String>();
		try {
			while (in.ready()){
				results.add(in.readLine());
			}
		} catch (IOException e) {
			logger.debug(StackToString.toString(e));
		}
		close();
		return results;
	}
	
	
	public void close(){
		try {
//			fis.close();
//			bis.close();
//			stream.close();
			in.close();
		} catch (IOException e) {
			logger.debug(StackToString.toString(e));
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
            logger.debug(StackToString.toString(e));
        }

        if (success) {
        	logger.info("File did not exist and was created.\n");
        } else {
        	logger.info("File already exists.\n");
        }
	}
	
	public static BufferedImage getImage(String fileName) {
		try {
			image = ImageIO.read(new File(fileName));
			return image;
		} catch (IOException e) {
			logger.debug(StackToString.toString(e));
		}
		return null;
	}
	
	public static ImageIcon getImageAsIcon(String fileName, String description) {
		URL imgURL = FileRW.class.getResource(fileName);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		}else {
			logger.fatal("Couldn't find file: " + fileName);
			return null;
		}
	}
}
