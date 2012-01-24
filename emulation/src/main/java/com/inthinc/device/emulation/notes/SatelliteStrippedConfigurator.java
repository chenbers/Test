package com.inthinc.device.emulation.notes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.log4j.Level;

import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.enums.DeviceProps;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.automation.utils.StackToString;

public class SatelliteStrippedConfigurator extends DeviceNote {
	

	private final Map<DeviceProps, String> settings;
	
	public SatelliteStrippedConfigurator(DeviceNoteTypes type, GeoPoint location, Map<DeviceProps, String> settings) {
		super(type, new AutomationCalendar(), location);
		this.settings = new HashMap<DeviceProps, String>();
		for (DeviceProps prop: settings.keySet()){
			this.settings.put(prop, settings.get(prop));
		}
	}

	@Override
	public byte[] Package() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		baos.write(0);  // length of struct - m_header
		longToByte(baos, type.getIndex(), 1);
		List<byte[]> list = VSettingsUtil.compressSettings(settings);
		for (byte[] bits : list){
			baos.write(bits, 0, bits.length);
		}
		byte[] temp = baos.toByteArray();
		temp[0] = (byte)(temp.length & 0xFF);
		return temp;
	}
	

	@Override
	public SatelliteStrippedConfigurator unPackage(byte[] packagedNote) {
		return unPackageS(packagedNote);
	}
	
	public static SatelliteStrippedConfigurator unPackageS(byte[] packagedNote){
		ByteArrayInputStream bais = new ByteArrayInputStream(packagedNote);
		bais.read(); // package size;
		DeviceNoteTypes type = DeviceNoteTypes.valueOf(byteToInt(bais, 1));
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bais.available());
		while (bais.available() > 0){
			baos.write(bais.read());
		}
		Map<DeviceProps, String> settings = null;
		try {
			settings = VSettingsUtil.decompressSettings(baos.toByteArray());
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
		SatelliteStrippedConfigurator note = new SatelliteStrippedConfigurator(type, new GeoPoint(), settings);
		return note;
	}
	

	@Override
	public DeviceNote copy() {
		return new SatelliteStrippedConfigurator(type, location, settings) ;
	}
	
	public static class VSettingsUtil {

		public final static double PACKET_SIZE = 270.0;

		public static Map<DeviceProps, String> decompressSettings(byte[] data) throws DataFormatException
		{
			Map<DeviceProps, String> settingsMap = new HashMap<DeviceProps, String>();
			byte[] decompressedData = decompressData(data);
			InputStream settingsStream = new ByteArrayInputStream(decompressedData);
			int num = 0;
			try {
				while (settingsStream.available() > 0)
				{
					DeviceProps key = DeviceProps.valueOf(((Long)(decodeUnsignedInteger(settingsStream) + 1000)).intValue());
					String value = getString(settingsStream);
					MasterTest.print("Setting # %d, %s = %s", ++num, key, value);
					settingsMap.put(key, value);
				}
			} catch (IOException exception)
			{
				MasterTest.print("IOException reading compressed settings: %s", StackToString.toString(exception));
			}
			return settingsMap;
		}
		
		
		public static List<byte[]> compressSettings(Map<DeviceProps, String> map)
		{
			int packets = 0;
			List<byte[]> packetList = new ArrayList<byte[]>();
			while (true)
			{
				packetList = compressSettings(map, packets);

				//Check to see if packetList has a packet that exceeds limit.
				//If so increase # of of packets and try again.  Else we're done.
				boolean increasePacketSize = false;
				for (byte[] packet : packetList)
				{
					if ((packet.length > PACKET_SIZE))
					{
						increasePacketSize = true;
						break;
					}
				}

				MasterTest.print("packetList.size(): " + packetList.size(), Level.DEBUG);
				MasterTest.print("increasePacketSize: " + increasePacketSize, Level.DEBUG);
				
				if (increasePacketSize)
					packets = packetList.size() + 1;
				else
					break;
			}
				
			return packetList;
		}
		
		private static List<byte[]> compressSettings(Map<DeviceProps, String> map, int packets)
		{
			ArrayList<byte[]> packetList = new ArrayList<byte[]>();
			
			try {
				byte[] compressedData = compressData(map);
				
				if (packets == 0)
					packets = (int)Math.ceil(compressedData.length/PACKET_SIZE); 

				MasterTest.print("Compressed data length: " + compressedData.length, Level.DEBUG);
				MasterTest.print("Packets: " + packets, Level.DEBUG);
				
				if (packets == 1)
					packetList.add(compressedData);
				else
				{
					int settingsPerPacket = (int) Math.ceil((double)(map.size()/packets));
					MasterTest.print("settingsPerPacket: " + settingsPerPacket, Level.DEBUG);
					Map<DeviceProps, String> partialMap = new HashMap<DeviceProps, String>();
					int totalCounter = 0;
					int packetCounter = 0;
					for (Map.Entry<DeviceProps, String> setting : map.entrySet())
					{
						totalCounter++;
						packetCounter++;
						partialMap.put(setting.getKey(), setting.getValue());
						if (packetCounter == settingsPerPacket || totalCounter == map.size())
						{
							compressedData = compressData(partialMap);
							packetList.add(compressedData);
							partialMap.clear();
							packetCounter = 0;
						}
							
						MasterTest.print(setting.getKey() + " = " + setting.getValue(), Level.DEBUG);
					}
					
				}
			} catch (IOException e)
			{
				MasterTest.print("compressSettings IO error: " + StackToString.toString(e));
			}
			return packetList;
		}
		
		private static byte[] compressData(Map<DeviceProps, String> map) throws IOException
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream(4000);
			
			for (Map.Entry<DeviceProps, String> setting : map.entrySet())
			{
			    byte[] keyVal = encodeUnsignedInteger(setting.getKey().getIndex().longValue() - 1000);
			    String value = setting.getValue();
			    bos.write(keyVal, 0, keyVal.length);
			    bos.write(value.getBytes(), 0, value.length());
			    bos.write(0x0);
			}
			return compressPacket(bos.toByteArray());
		}
		
		private static byte[] compressPacket(byte[] bytes)
		{
			 Deflater deflater = new Deflater();
			 deflater.setLevel(Deflater.BEST_COMPRESSION);
			 deflater.setInput(bytes);
			 deflater.finish();
			  
			 ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length);
			  
			 byte[] buffer = new byte[1024];
			  
			 while(!deflater.finished())
			 {
				 int bytesCompressed = deflater.deflate(buffer);
				 bos.write(buffer,0,bytesCompressed);
			 }
			  
			 try
			 {
				 bos.close();
			 }
			 catch(IOException ioe)
			 {
				 MasterTest.print("Error while closing the stream : " + StackToString.toString(ioe));
			 }
			  
			 //get the compressed byte array from output stream
			 byte[] compressedArray = bos.toByteArray();
			  
			 MasterTest.print("Byte array has been compressed!", Level.DEBUG);
			 MasterTest.print("Size of original array is:" + bytes.length, Level.DEBUG);
			 MasterTest.print("Size of compressed array is:" + compressedArray.length, Level.DEBUG);
			  
			return compressedArray;
		}

		
		private static byte[] decompressData(byte[] data) throws java.util.zip.DataFormatException
		{
			ArrayList<Byte> arrayList = new ArrayList<Byte>();


			Inflater decomp = new Inflater();
			decomp.setInput(data);
			int lenDecoded = 0;
			do
			{
				byte[] dataBlock = new byte[512];

				lenDecoded = decomp.inflate(dataBlock);
				if(lenDecoded > 0)
				{
					for(int i = 0; i < lenDecoded; i++)
						arrayList.add(dataBlock[i]);
				}
			} while(lenDecoded > 0);

			decomp.end();

			byte[] dataOut = new byte[arrayList.size()];

			for(int i = 0; i < arrayList.size(); i++)
			{
				dataOut[i] = (Byte)arrayList.get(i);
			}
			return dataOut;
		}

		private static byte[] encodeUnsignedInteger(long value)
		{
			if(value < 128)
			{
				byte[] b = new byte[1];
				b[0] = (byte)value;
				return b;
			}
			else if(value < 16384)
			{
				byte[] b = new byte[2];
				b[0] = (byte)(0x00ff &(0x80 | (value>>8)));
				b[1] = (byte)(0x00ff & value);
				return b;
			}
			else if(value < 2097152)
			{
				byte[] b = new byte[3];
				b[0] = (byte)(0x00ff &(0xc0 | (value>>16)));
				b[1] = (byte)(0x00ff & (value >> 8));
				b[2] = (byte)(0x00ff & value);
				return b;
			}
			else if(value < 268435456)
			{
				byte[] b = new byte[4];
				b[0] = (byte)(0x00ff &(0xe0 | (value>>24)));
				b[1] = (byte)(0x00ff & (value >> 16));
				b[2] = (byte)(0x00ff & (value >> 8));
				b[3] = (byte)(0x00ff & value);
				return b;
			}
			else //if(value < 268435456)
			{
				byte[] b = new byte[5];
				b[0] = (byte)(0x00ff & 0xf0);
				b[1] = (byte)(0x00ff & (value >> 24));
				b[2] = (byte)(0x00ff & (value >> 16));
				b[3] = (byte)(0x00ff & (value >> 8));
				b[4] = (byte)(0x00ff & value);
				return b;
			}
		}
		
		private static long decodeUnsignedInteger(InputStream is) throws IOException
		{
			int bt = is.read();
			if(0 == (0x80 & bt))
			{
				return bt;
			}
			else if( 0x80 == (0xc0 & bt) )
			{
				return ((bt & 0x3f) << 8) | is.read();
			}
			else if( 0xc0 == (0xe0 & bt) )
			{
				return ((bt & 0x1f) << 16) | (is.read() << 8) | is.read();
			}
			else if( 0xe0 == (0xf0 & bt) )
			{
				return ((bt & 0x0f) << 24) | (is.read() << 16) | (is.read() << 8) | is.read();
			}
			else if( 0xf0 == (0xf8 & bt) )
			{
				// ignore bits (0x07 & bt)
				return (is.read() << 24) | (is.read() << 16) | (is.read() << 8) | is.read();
			}                             

			// In this case the data is invalide, should abort reading from here 
			throw new IOException();
		}

		private static String getString(InputStream is) throws IOException
		{
			StringBuffer sb = new StringBuffer();

			while(is.available() > 0)
			{
				char c = (char)is.read();
				if(0 == c)
					break;

				sb.append(c);
			}

			return sb.toString();
		}

	}


}
