package com.inthinc.pro.comm.parser.note;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inthinc.parser.util.NotebcUtil;
import com.inthinc.pro.comm.parser.attrib.Attrib;

public class NoteVersionParseTest {
    private static Logger logger = LoggerFactory.getLogger(NoteVersionParseTest.class);
    
//    @Test
    public void testFileData() {
    	 
    	BufferedReader br = null;
    	String line = "";
    	String cvsSplitBy = ",";

    	URL url = Thread.currentThread().getContextClassLoader().getResource("850_v4note_test_output.txt");
    	
    	try {
     
    		br = new BufferedReader(new FileReader(url.getFile()));
    		while ((line = br.readLine()) != null) {
     
    		        // use comma as separator
    			String[] packets = line.split(cvsSplitBy);
    			logger.info("{}, {}",  packets[0], packets[1]);
 //      			logger.info("{}",  packets[1].charAt(0));
       			if (packets[1].charAt(1) == '4')
       				assertTrue(compareV4(packets[0], packets[1].substring(2)));
       			else
       				assertTrue(compareV5(packets[0], packets[1].substring(2)));
       		      
    		}
     
    	} catch (FileNotFoundException e) {
    		logger.error("Error:", e);
    	} catch (IOException e) {
    		logger.error("Error:", e);
    	} finally {
    		if (br != null) {
    			try {
    				br.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
		logger.info("DONE!");
	}
  
  // @Test
    public void testData() throws Exception {
	   assertTrue(compareV4("f70353b211b4000b56aa815fba91dc050c460000000000ed236600c3ffffffffc0114889258d590ec330084439901331601cc7f7e96f7a80aa5217a9ea9a1e836bd6a43fa3e1310c7e63f2338cfc240c1d781a18e4d74e2f42fe41a9e4ef031a2c49534e224d389986b7397492966baa1c0adb42f8ef51e6ed4e4b9f8ee45ff2b5973e7af97dd92f3bf2573c850023b28a66f2e71888732051b64a3fab29295a805900087d0000000000000000",
			   "f70b53b211b456aa81ba91dc00ed236600000f0c1346fe118b4889258d590ec330084439901331601cc7f7e96f7a80aa5217a9ea9a1e836bd6a43fa3e1310c7e63f2338cfc240c1d781a18e4d74e2f42fe41a9e4ef031a2c49534e224d389986b7397492966baa1c0adb42f8ef51e6ed4e4b9f8ee45ff2b5973e7af97dd92f3bf2573c850023b28a66f2e71888732051b64a3fab29295a805900087d0000000000000000"));

	/*   
		assertTrue(compareV5("d00353ed541c00007fffffff0000000000500000000000ac83890000ffffffff00db000014c100dc0000000000f100000000805900008123","D00053ED541C00AC83898123DB000014C1"));
		
		assertTrue(compareV4("bf0353b5a5c0051a570ca973ba5117cc413c0096207d00f2904100c3ffffffff20205020320120236420361440080020203901400e003d20313c400f003f00c0ffffffff20460e20470e20450e40140000803c000001d5002a43004103204c18204d64204a18204b180082000020480e204918201564401ca5c000810006803d0000000f000244203a00000144203b41803053b5a59f00040000e70096207d205600201041008c02c0008e000080590000be9d",
		   	"bf1a53b5a5c0570ca9ba511700f29041be9d0f41133ce70096207dfa2050fa3201fa2364fa3614f60820fa3901f60e3dfa313cf60f3fc0fffffffffa460efa470efa450ef83c01d52a434103fa4c18fa4d64fa4a18fa4b18fa480efa4918fa1564fb1ca5c0810006f73d0f02440144fa3b41fc3053b5a59fe70096207dfa10418c02c0"));
		  
		assertTrue(compareV4("bf0353bd75fc053c4627d9aab0b2e55e2e2d01f73bbf00e98b010093ffffffff20205d20320120236420361440080017203901400e002020312d400f002100c0ffffffff20460e20470e20450e40140000803c000000dc002a2e004103204c11204d64204a11204b110082000020480e204911201563401c75fc00810003803d0000000f000231203a00000133203b2d803053bd75e400040000e701f73bbf20560020102e008c0636008e0000805900001d81",
		   "bf3c53bd75fc4627d9b0b2e500e98b011d810f2e132de701f73bbffa205dfa3201fa2364fa3614f60817fa3901f60e20fa312df60f21c0fffffffffa460efa470efa450ef73cdc2a2e4103fa4c11fa4d64fa4a11fa4b11fa480efa4911fa1563fb1c75fc810003f73d0f02310133fa3b2dfc3053bd75e4e701f73bbffa102e8c0636"));
	
		assertTrue(compareV4("140353b5fa2900095a918fdfba13e577005000000000000dc9f600c3ffffffff403a00f0004f03403c01dc403b002400e000001081009500a34037104000a603e8403d073f403e0651805900009e0b",
		   "140953b5fa295a918fba13e5000dc9f69e0bf63af04f03fb3c01dcf63b24e0000010819500a3fb371040a603e8fb3d073ffb3e0651"));

		assertTrue(compareV4("710353b65c84007958144ae0b8f1f51b00190000000000dfcf9000c30000d615600231313531383100000000200a0320640160063332206d69205357206f662043617272697a6f20537072696e67732c54582063008059000453b000000000",
				"717953b65c8458144ab8f1f500dfcf9053b0aad615fa0a03fa6401"));
		assertTrue(compareV4("420353b67dc7001b577573c0b8f98160004b0000000000dfe38300c30000d615403a136f004f09403c0055403b004d00e0000021d90095003a00a603e8403d05ab403e081b805900045521", 
				"421b53b67dc7577573b8f98100dfe3835521aad615fb3a136f4f09f63c55f63b4de0000021d995003aa603e8fb3d05abfb3e081b"));
		assertTrue(compareV4("400353b68973001a5775719bb8f97ffa004b0000000000dfe38300c3ffffffff600b3236363900600833363736393100205700805900045526", "401a53b68973577571b8f97f00dfe3835526fd0b3236363900fd0833363736393100"));
		assertTrue(compareV4("710353b7c296001c57756e28b8f97b5c004b0000000000e0511500c30000d615204240204380600231313531383100000000200a00206401600643617272697a6f20537072696e6773202d20545800000000000000000000206302805900045c8d", "711c53b7c29657756eb8f97b00e051155c8daad615fa4240fa4380fa6401fa6302"));
		assertTrue(compareV4("ec0353b7e0aa001a57756f17b8f97bc8004b0000000000e0511500c30000d5f46011564d31333032383030363446323039303433006012564d3133303238303036344632303930343300805900045c97", "ec1a53b7e0aa57756fb8f97b00e051155c97aad5f4fd11564d3133303238303036344632303930343300fd12564d3133303238303036344632303930343300"));
		assertTrue(compareV4("bf0353b97601010c577ef726b9bd54c43f3700b6fd460129b1a600c30000d5ec20204b20320120236420360a40080010203901400e001a203137400f001c00c0ffffffff20460d20470d20450d40140000803c00000046002a3e004103204c08204d64204a08204b080082000020480d204908201563401c760100810002803d0000000f000240203a00000143203b3c803053b975f100040000e700b6fd4620560020103f008c004a008e0000805900053bcb",
				"bf0c53b97601577ef7b9bd540129b1a63bcbaad5ec0f3f1337e700b6fd46fa204bfa3201fa2364fa360af60810fa3901f60e1afa3137f60f1cc0fffffffffa460dfa470dfa450df73c462a3e4103fa4c08fa4d64fa4a08fa4b08fa480dfa4908fa1563fb1c7601810002f73d0f02400143fa3b3cfc3053b975f1e700b6fd46fa103f8c004a"));
		assertTrue(compareV4("f70353b43a3d082c56bcbd72ba83e46c00460000000000ee207400c300009914c01148892d4fd74ec34010dc4fa0f7de5b7c7b3edbb1a982483c44e2955764ec8804c936d82924b4cfd9df642ee461e67676e7b6c88d66926b95c6dd5875f26ebb9327aadfcad3a224b9b2b54b1774a16d78ae49a21041a87d923a5400f8d6e07920634d2e4a1a69069c3a3352cadacf6eb356d94962d568835f0a921a4a2701e8545569129729c9913624c7ae8fe441659c388b47451e0f2a27293292433b7b0f7df73d929db16b176a0befb61db14eb2867813d820592659225925592199876deef181efb8113007debdebf99ad16531e408f6856ce84caeff1f356d985de371c06ee88f2d53c02c3043f2896e23dfc1f721c907c42ff23fc037c91764650c44a9edbaefc09bd60ed61ba0d49f9caafacfbd8aa4d7d491899e9abace618d593baf5591d31f3b4772fc8059000889e6000000000000000000000000",
		 "f72c53b43a3d56bcbdba83e400ee20740000aa9914fe110048892d4fd74ec34010dc4fa0f7de5b7c7b3edbb1a982483c44e2955764ec8804c936d82924b4cfd9df642ee461e67676e7b6c88d66926b95c6dd5875f26ebb9327aadfcad3a224b9b2b54b1774a16d78ae49a21041a87d923a5400f8d6e07920634d2e4a1a69069c3a3352cadacf6eb356d94962d568835f0a921a4a2701e8545569129729c9913624c7ae8fe441659c388b47451e0f2a27293292433b7b0f7df73d929db16b176a0befb61db14eb2867813d820592659225925592199876deef181efb8113007debdebf99ad16531e408f6856ce84caeff1f356d985de371c06ee88f2d53c02c3043f2896e23dfc1f721c907c42ff23fc037c91764650c44a9edbaefc09bd60ed61ba0d49f9caafacfbd8aa4d7d491899e9abace618d593baf5591d31f3b4772fc8059000889e6000000000000000000000000"));

		assertTrue(compareV4("c80353b477aa007c56b05821ba798f37264600a8b7270123890000000000afd5404b0297404afd404049017c4048006500e200000030404cff11404dfdf2404efdf2404602f8404fffe14047021d4050006040440d8b4051ffb54045072c80590006c901",
		 "c87c53b477aa56b058ba798f01238900c901aaafd50f261346fb4b0297fb4afd40fb49017cf64865e200000030fb4cff11fb4dfdf2fb4efdf2fb4602f8fb4fffe1fb47021df65060fb440d8bfb51ffb5fb45072c"));
		assertTrue(compareV4("e00353b2a625004a577592b2b8f97819054b000000000127dcd100c30000d5f460023131343733360000000080590005180e", "e04a53b2a625577592b8f9780127dcd1180eaad5f40f05134b"));
		assertTrue(compareV5("f60353bef5d700007fffffff00000000005d00000000000003c30000ffffffff00c30000015500070400c20000000000000000805900000143", "f60053bef5d7000003c30143c3000001550704"));
		*/
		/*
		assertTrue(compareV4("", ""));
		assertTrue(compareV4("", ""));
*/		
	}

	
    @Test
    public void parseVersion3() throws Exception {
        String textMessage = "This is a test of a byte array";
        byte[] byteMessage = textMessage.getBytes();
                
        byte[] note = NotebcUtil.createHeader(NoteType.CRASH_DATA_HI_RES.getCode());
        note = NotebcUtil.addAttrib(note, Attrib.SPEEDDATAHIRES, byteMessage);
        
        NoteParser parser = NoteParserFactory.getParserForMethod(NoteParserFactory.NOTEBC_METHOD);
        Map<String, Object> attribMap = parser.parseNote(note);
        
        
        assertEquals(NoteType.CRASH_DATA_HI_RES.getCode(), attribMap.get(Attrib.NOTETYPE.getFieldName()));
        assertEquals(new Double(-112.00584), (Double)attribMap.get("longitude"));
        assertEquals(new Double(40.70974), (Double)attribMap.get("latitude"));
        assertEquals(new Integer(111100), (Integer)attribMap.get("odometer"));

        String fetchedTextMessage = new String((byte[]) attribMap.get(Attrib.SPEEDDATAHIRES.getFieldName()));
        assertEquals(fetchedTextMessage, textMessage);
    }


    @Test
    public void parseVersion4() throws Exception {
        String textMessage = "This is a test of a byte array";
        byte[] byteMessage = textMessage.getBytes();
                
        byte[] note = NotebcUtil.createHeaderV4(NoteType.CRASH_DATA_HI_RES.getCode());
        note = NotebcUtil.addAttribV4(note, Attrib.SPEEDDATAHIRES, byteMessage);
        
        NoteParser parser = NoteParserFactory.getParserForMethod(NoteParserFactory.NOTEBC4_METHOD);
        Map<String, Object> attribMap = parser.parseNote(note);
        
        assertEquals(NoteType.CRASH_DATA_HI_RES.getCode(), attribMap.get(Attrib.NOTETYPE.getFieldName()));
        DecimalFormat df = new DecimalFormat("###.###");
        df.setRoundingMode(RoundingMode.DOWN);
        assertEquals(df.format(40.777d), df.format((Double)attribMap.get("latitude")));
        assertEquals(df.format(-112.333d), df.format((Double)attribMap.get("longitude")));
        assertEquals(new Integer(5), (Integer)attribMap.get(Attrib.NOTEFLAGS.getFieldName()));
        assertEquals(new Integer(111100), (Integer)attribMap.get("odometer"));
        assertEquals(new Integer(1001), (Integer)attribMap.get(Attrib.NOTIFICATION_ENUM.getFieldName()));

        String fetchedTextMessage = new String((byte[]) attribMap.get(Attrib.SPEEDDATAHIRES.getFieldName()));
        assertEquals(fetchedTextMessage, textMessage);
    }

    @Test
    public void parseVersion5() throws Exception {
        String textMessage = "This is a test of a byte array";
        byte[] byteMessage = textMessage.getBytes();
                
        byte[] note = NotebcUtil.createHeaderV5(NoteType.CRASH_DATA_HI_RES.getCode());
        note = NotebcUtil.addAttribV4(note, Attrib.SPEEDDATAHIRES, byteMessage);
        
        NoteParser parser = NoteParserFactory.getParserForMethod(NoteParserFactory.NOTEBC5_METHOD);
        Map<String, Object> attribMap = parser.parseNote(note);
        
        assertEquals(NoteType.CRASH_DATA_HI_RES.getCode(), attribMap.get(Attrib.NOTETYPE.getFieldName()));
        assertEquals(new Integer(5), (Integer)attribMap.get(Attrib.NOTEFLAGS.getFieldName()));
        assertEquals(new Integer(111100), (Integer)attribMap.get("odometer"));
        assertEquals(new Integer(1001), (Integer)attribMap.get(Attrib.NOTIFICATION_ENUM.getFieldName()));
        
        String fetchedTextMessage = new String((byte[]) attribMap.get(Attrib.SPEEDDATAHIRES.getFieldName()));
        assertEquals(fetchedTextMessage, textMessage);
    }

    private boolean compareV4(String v3, String v4) {
    	boolean retVal = false;
    	try {
	        NoteParser parser = NoteParserFactory.getParserForMethod(NoteParserFactory.NOTEBC_METHOD);
	        Map<String, Object> attribMap1 = parser.parseNote(Hex.decodeHex(v3.toCharArray()));
	        logger.info("attribMap: " + attribMap1);
	
	        parser = NoteParserFactory.getParserForMethod(NoteParserFactory.NOTEBC4_METHOD);
	        Map<String, Object> attribMap2 = parser.parseNote(Hex.decodeHex(v4.toCharArray()));
	        logger.info("attribMap: " + attribMap2);
	        retVal = compareMapsV4(attribMap1, attribMap2);
    	} catch (DecoderException e){
    		logger.error("Exception: ", e);
    	}     
		return retVal;
    }

    private boolean compareV5(String v3, String v5) {
    	boolean retVal = false;
    	try {
	        NoteParser parser = NoteParserFactory.getParserForMethod(NoteParserFactory.NOTEBC_METHOD);
	        Map<String, Object> attribMap1 = parser.parseNote(Hex.decodeHex(v3.toCharArray()));
	        logger.info("attribMap: " + attribMap1);
	
	        parser = NoteParserFactory.getParserForMethod(NoteParserFactory.NOTEBC5_METHOD);
	        Map<String, Object> attribMap2 = parser.parseNote(Hex.decodeHex(v5.toCharArray()));
	        logger.info("attribMap: " + attribMap2);
	        retVal = compareMapsV4(attribMap1, attribMap2);
    	} catch (DecoderException e){
    		logger.error("Exception: ", e);
    	}     
		return retVal;
    }

    private boolean compareMapsV4(Map<String, Object> mapV3, Map<String, Object> mapV4) {
    	return compareMaps(mapV3, mapV4, false);
    }
    		   
    private boolean compareMapsV5(Map<String, Object> mapV3, Map<String, Object> mapV4) {
    	return compareMaps(mapV3, mapV4, true);
    }
 
    private boolean compareMaps(Map<String, Object> mapV3, Map<String, Object> mapV4, boolean isV5) {
    	
    	if (mapV3.get(Attrib.LOCATION.getFieldName()) != null ||
    		mapV3.get(Attrib.DELTAVS.getFieldName()) != null)
    		return true;
    	
    	Object noteType = mapV3.get(Attrib.NOTETYPE.getFieldName()); 
    	if (noteType != null && ((Integer) noteType) == 250) 
        		return true;

    	for(Map.Entry<String, Object> entryMap1 : mapV3.entrySet())
    	{
    		logger.info("entryMap1: {}", entryMap1);
    		
    		Object value1 = entryMap1.getValue();
    		Object value2 = mapV4.get(entryMap1.getKey());
    		if (value1 instanceof String && ((String) value1).isEmpty())
    			continue;
    		if (value1 instanceof Number && ((Number) value1).intValue() == 0)
    			continue;
    		
    		if (Attrib.VSETTINGS.getFieldName().equals(entryMap1.getKey()) ||
				Attrib.CRASHTRACE.getFieldName().equals(entryMap1.getKey()) ||
				Attrib.CRASHDATA.getFieldName().equals(entryMap1.getKey()) ||
				Attrib.HOSRECORDS.getFieldName().equals(entryMap1.getKey())) 
			{
        		if (Arrays.equals((byte[]) value1, (byte[]) value2))
        			continue;
    		}
    		
        
    		if (Attrib.NOTESPEED.getFieldName().equals(entryMap1.getKey()) ||
				Attrib.NOTESPEEDLIMIT.getFieldName().equals(entryMap1.getKey()) ||
				Attrib.LINKID.getFieldName().equals(entryMap1.getKey()) ||
				Attrib.BOUNDARYID.getFieldName().equals(entryMap1.getKey()) ||
				Attrib.DRIVERID.getFieldName().equals(entryMap1.getKey()) ||
				Attrib.LOCATION.getFieldName().equals(entryMap1.getKey()) ||
				Attrib.NOTIFICATION_ENUM.getFieldName().equals(entryMap1.getKey()) ||
				Attrib.DRIVERSTR.getFieldName().equals(entryMap1.getKey()) ||
				Attrib.EMPID.getFieldName().equals(entryMap1.getKey()) ||
				"head".equals(entryMap1.getKey()) ||
				"heading".equals(entryMap1.getKey()))
				continue;
			
			
			
			if ("cmdid".equals(entryMap1.getKey())){
				continue;
			}

//			if (("cmdid".equals(entryMap1.getKey()) && (((Number) value1).intValue() == ((Number) value2).intValue()))){
//				continue;
//			}

			if (isV5 && ("latitude".equals(entryMap1.getKey()) || "longitude".equals(entryMap1.getKey()))) {
				continue;
			}
    		
    		if (value2 == null) {
    			return false;
    		}
    		
    		if (!value2.equals(entryMap1.getValue())) {
    			if (("latitude".equals(entryMap1.getKey()) || "longitude".equals(entryMap1.getKey())) && (((Number) value1).intValue() == ((Number) value2).intValue())){
    				continue;
    			}
    				
    			if (!Attrib.NOTEFLAGS.getFieldName().equals(entryMap1.getKey()))
    				return false;
    			else {
    				int flags1 = ((Number) value1).intValue();
    				int flags2 = ((Number) value2).intValue();
    				if ((flags1 & 0x000000FF) != flags2) {
    					return false;
    				}
    			}
    		}
    	}
    	return true;
    }


}
