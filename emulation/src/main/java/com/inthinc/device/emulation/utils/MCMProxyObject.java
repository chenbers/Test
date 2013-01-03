package com.inthinc.device.emulation.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;

import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.interfaces.MCMService;
import com.inthinc.device.emulation.notes.DeviceNote;
import com.inthinc.device.emulation.notes.NoteBC;
import com.inthinc.device.emulation.notes.SatNote;
import com.inthinc.device.emulation.notes.SatelliteEvent;
import com.inthinc.device.emulation.notes.SatelliteEvent_t;
import com.inthinc.device.emulation.notes.SatelliteStrippedConfigurator;
import com.inthinc.device.emulation.notes.TiwiNote;
import com.inthinc.device.hessian.tcp.AutomationHessianFactory;
import com.inthinc.device.hessian.tcp.HessianException;
import com.inthinc.device.hessian.tcp.ProDAOException;
import com.inthinc.device.noteservice.NoteService;
import com.inthinc.device.resources.DeviceStatistics;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.utils.AutoServers;
import com.inthinc.pro.automation.utils.AutomationStringUtil;
import com.inthinc.pro.automation.utils.HTTPCommands;


public class MCMProxyObject implements MCMService{
    
    /**
     * 
     */
    private static final long serialVersionUID = 3563177742129607776L;



    private MCMService proxy;


    private AutoServers server;


    public static boolean regularNote = true;
    

    public static Map<String, String> drivers;


    private static NoteService notes;
    
    public static NoteService getService(){
        return notes;
    }
    
    public MCMProxyObject(AutoSilos silo){
    	this(new AutoServers(silo));
    }
    
    public MCMProxyObject(AutoServers server) {
        this.server = server;
        AutomationHessianFactory getHessian = new AutomationHessianFactory();
        Log.info("MCM Server is " + server);
        proxy = getHessian.getMcmProxy(server);
    }
    

    private void printReply(Object reply){
    	Log.debug("printReply %s", reply);
    }
    
    private void printNote(DeviceNote note){
    	Log.debug("printNote %s", note);
    }
    
    private void printOther(Object other){
    	Log.debug("printOther %s", other);
    }
    
    public static void processDrivers(Map<Integer, Map<String, String>> driversMap){
        drivers = new HashMap<String, String>();
        Iterator<Integer> itr = driversMap.keySet().iterator();
        while (itr.hasNext()){
            Integer next = itr.next();
            Map<String, String> map = driversMap.get(next);
            drivers.put(map.get("device"), map.get("deviceID"));
        }
        notes = NoteService.createNode();
    }
    
    public List<Map<String, Object>> tiwiNote(String mcmID, List<? extends DeviceNote> noteList){
        if (regularNote ){
            List<byte[]> temp = new ArrayList<byte[]>(noteList.size());
            for (DeviceNote note : noteList){
                byte[] array = note.Package();
                temp.add(array);
                printNote(note);
            }
            return note(mcmID, temp);
        } else {
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            for (DeviceNote note : noteList){
                Map<String, String> temp = ((TiwiNote)note).packageToMap();
                temp.put("32900", drivers.get(mcmID).toString());
                list.add(temp);
            }
            notes.insertNote(list);
        }
        return null;
    }
    

    public byte[] ws850Note(String mcmID, Direction waysDirection, List<DeviceNote> noteList) {
        List<byte[]> temp = new ArrayList<byte[]>(noteList.size());
        for (DeviceNote note : noteList){
            byte[] array = ((SatelliteEvent_t)note).Package(false);
            temp.add(array); 
            printNote(note);
        }
        return notes(mcmID,waysDirection.getIndex(), temp);
    }

    @Override
    public byte[] notes(String mcmID, int connectType, List<byte[]> noteList) throws HessianException {
        byte[] reply = proxy.notes(mcmID, connectType, noteList);
        printReply(new String(reply));
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public List<Map<String, Object>> note(String mcmID, List<byte[]> noteList) {
        List<Map<String, Object>> reply = proxy.note(mcmID, noteList);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }
    
    @Override
    public List<Map<String, Object>> dumpSet(String mcmID, Integer version,
            Map<Integer, String> settings) {
        printOther(settings);
        Log.debug("IMEI:%s, Version:%d", mcmID, version);
        List<Map<String, Object>> reply = proxy.dumpSet(mcmID, version, settings);
        printReply(reply);
        return reply;
//        return null;
    }

    @Override
    public List<Map<Integer, String>> reqSet(String imei) {
        List<Map<Integer, String>> reply = proxy.reqSet(imei);
        printReply(reply);
        return reply;
//    	return null;
    }
    
    @Override
    public List<Map<String, Object>> getRH(String mcmID, int lat, int lng, int radius) {
        List<Map<String,Object>> reply = proxy.getRH(mcmID, lat, lng, radius);
        printReply(reply);
        return reply;
    }
    
    @Override
    public Map<String, Object> audioUpdate(String mcmID, Map<String, Object> map) {
        printOther(map);
        Map<String, Object> reply = proxy.audioUpdate(mcmID, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> tiwiproUpdate(String mcmID,
            Map<String, Object> map) {
        printOther(map);
        Map<String, Object> reply = proxy.tiwiproUpdate(mcmID, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> witnessUpdate(String mcmID,
            Map<String, Object> map) {
        printOther(map);
        Map<String, Object> reply = proxy.witnessUpdate(mcmID, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> emuUpdate(String mcmID, Map<String, Object> map) {
        printOther(map);
        Map<String, Object> reply = proxy.emuUpdate(mcmID, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> zoneUpdate(String mcmID, Map<String, Object> map) {
        printOther(map);
        Map<String, Object> reply = proxy.zoneUpdate(mcmID, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> sbsUpdate(String mcmID, Map<String, Object> map) {
        printOther(map);
        Map<String, Object> reply = proxy.sbsUpdate(mcmID, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public List<Map<String, Object>> sbsCheck(String mcmID, int baselineID,
            List<Map<String, Object>> mapList) {
        printOther(mapList);
        List<Map<String, Object>> reply = proxy.sbsCheck(mcmID, baselineID, mapList);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public List<Map<String, Object>> checkSbsSubscribed(String mcmID,
            Map<String, Object> map) {
        printOther(map);
        List<Map<String, Object>> reply = proxy.checkSbsSubscribed(mcmID, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> getSbsBase(String mcmID, Map<String, Object> map) {
        printOther(map);
        Map<String, Object> reply = proxy.getSbsBase(mcmID, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> getSbsEdit(String mcmID, Map<String, Object> map) {
        printOther(map);
        Map<String, Object> reply = proxy.getSbsEdit(mcmID, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public List<Map<String, Object>> checkSbsEdit(String mcmID,
            List<Map<String, Object>> map) {
        printOther(map);
        List<Map<String, Object>> reply = proxy.checkSbsEdit(mcmID, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }
    
    public List<Map<String, Object>> notebc(String mcmID, Direction comType,
            List<? extends DeviceNote> noteList, String imei){
        List<byte[]> temp = new ArrayList<byte[]>(noteList.size());
        Log.debug("\nnotebc(mcmID=%s, connectType=%s, noteList=%s)", mcmID, comType, noteList);
        
        for (DeviceNote note : noteList){
            if (note.getType() == DeviceNoteTypes.INSTALL || comType.equals(Direction.sat)){
                List<byte[]> install = new ArrayList<byte[]> (1);
                install.add(note.Package());
                notebc(imei, Direction.sat.getIndex(), install);
            } else {
                temp.add(note.Package());
            }
            printNote(note);
        }
        if (!temp.isEmpty()){
            return notebc(mcmID, comType.getIndex(), temp);
        } else {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> notebc(String mcmID, int connectType,
            List<byte[]> noteList) {
        List<Map<String, Object>> reply = proxy.notebc(mcmID, connectType, noteList);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }
    
    public void sendSatNote(String imei, List<? extends DeviceNote> sendingQueue){

    	for (DeviceNote note: sendingQueue){
	    	byte[] packaged = new SatNote(note, imei).Package();
	    	try {
	    		Log.info("Sending " + note);
	    		Log.info("Creating socket");
	        	Socket socket =  new Socket(server.getMcmUrl(), server.getSatPort());
	    		ByteArrayOutputStream out =  new ByteArrayOutputStream(); 
	    		out.write(packaged, 0, packaged.length);
	    		Log.info("Writing to socket");
	    		out.writeTo(socket.getOutputStream());
	    		out.flush();
	    		out.close();
	    		
	    		socket.getOutputStream().flush();
	    		socket.close();
	            DeviceStatistics.addCall();
	    		
			} catch (UnknownHostException e) {
				Log.error("%s", e);
			} catch (IOException e) {
				Log.error("%s", e);
			}
    	}
    }
    
    public void sendSatSMTP(String imei, List<? extends DeviceNote> sendingQueue){
        Properties props = new Properties();
        props.put("mail.smtp.host", "qa.tiwipro.com");
        props.put("mail.smtp.port", 2526);
        Session session = Session.getDefaultInstance(props, null);
        InternetAddress from, to1;
        try {
            from = new InternetAddress("sbdservice@sbd.iridium.com", "Emulation Project");
            to1 = new InternetAddress("iridium@" + server.getPortalUrl().replace("inthinc", "tiwipro"), "Bridge");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Couldn't encode the Email addresses");
        }
        
        for (DeviceNote note: sendingQueue){
            try {
//                SatNote note = new SatNote(payload, imei);
                Message msg = new MimeMessage(session);
                msg.setFrom(from);
                msg.addRecipient(Message.RecipientType.TO, to1);
                
                msg.setSubject("SBD Msg From Unit: " + imei);
    
                // create and fill the first message part
                MimeBodyPart mbp1 = new MimeBodyPart();
                mbp1.setText(note.toString());
                printNote(note);
    
                // create the second message part
                MimeBodyPart mbp2 = new MimeBodyPart();
                
                // attach the file to the message
                DataSource ds = new ByteArrayDataSource(note.Package(), "application/x-any");
                mbp2.setDataHandler(new DataHandler(ds));
                mbp2.setFileName("note");
    
                // create the Multipart and add its parts to it
                Multipart mp = new MimeMultipart();
//                mp.addBodyPart(mbp1);
                mp.addBodyPart(mbp2);
    
                // add the Multipart to the message
                msg.setContent(mp);
    
                // set the Date: header
                msg.setSentDate(note.getTime().getDate());
                
                // send the message
                Transport.send(msg);
                DeviceStatistics.addCall();
            } catch (AddressException e) {
                Log.error("%s", e);
            } catch (MessagingException e) {
                Log.error("%s", e);
            } 
        }
    }
    
    public String[] sendHttpNote(String mcmID, Direction comType,
            List<? extends DeviceNote> sendingQueue, String imei) throws ClientProtocolException, IOException{
        
        HTTPCommands http = new HTTPCommands();
        List<String> reply = new ArrayList<String>();
        for (DeviceNote note : sendingQueue){
        	if (note.getType().equals(DeviceNoteTypes.INSTALL)){
        		List<DeviceNote> list = new ArrayList<DeviceNote>();
        		list.add(note);
        		//sendSatSMTP(imei, list);
        		sendSatNote(imei, list);
        	}
        	byte[] packaged = note.Package();
        	String uri = 
                    "http://" + server.getPortalUrl() + 
                    ":" + server.getWaysPort() + 
                    "/gprs_wifi/gprs.do?mcm_id=" +""+(comType.equals(Direction.sat) ? imei: mcmID )+
                    "&commType="+comType.getIndex()+
                    "&sat_cmd="+note.getType().getIndex()+
                    "&event_time="+(note.getTime().toInt());
                
            HttpPost method = new HttpPost(uri.toLowerCase());
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            
            try {
            	// Essential
                entity.addPart("mcm_id", new StringBody(mcmID, Charset.forName("UTF-8")));
                entity.addPart("commType", new StringBody("" + comType.getIndex(), Charset.forName("UTF-8")));
                
                // For debugging purposes.
//                entity.addPart("imei", new StringBody(imei, Charset.forName("UTF-8")));
//                entity.addPart("event_time", new StringBody("" + note.getTime(), Charset.forName("UTF-8")));
//                entity.addPart("sat_cmd", new StringBody("" + note.getType(), Charset.forName("UTF-8")));
//                entity.addPart("url", new StringBody(uri, Charset.forName("UTF-8")));
//                entity.addPart("note", new StringBody(note.toString(), Charset.forName("UTF-8")));
//                entity.addPart("vehicle_id_str", new StringBody("654", Charset.forName("UTF-8")));
//                entity.addPart("company_id", new StringBody("3", Charset.forName("UTF-8")));
            } catch (Exception e) {
                
            }
            
            
            entity.addPart("filename", new ByteArrayBody(packaged, "filename"));
            method.setEntity(entity);
                
        	reply.add(http.httpRequest(method));
            DeviceStatistics.addCall();
            printNote(note);
        }
        return reply.toArray(new String[]{});
    }
    
    @Override
    public List<Map<String, Object>> notews(String mcmID, int connectType,
            List<byte[]> noteList) {
        List<Map<String, Object>> reply = proxy.notews(mcmID, connectType, noteList);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    public static void closeService() {
        notes.shutdown();
    }

    public static void setupCassandra(NoteService service) {
        notes = service;
        regularNote = false;
    }


    public Object sendNotes(DeviceState state, DeviceNote note) {
        LinkedList<DeviceNote> list = new LinkedList<DeviceNote>();
        list.add(note);
        Log.debug("sentNotes %s", note);
        try {
			return sendNotes(state, list);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public Object sendNotes(DeviceState state, List<DeviceNote> notes) throws ClientProtocolException, IOException{
    	Object reply = null;
    	Class<? extends DeviceNote> clazz = notes.get(0).getClass();
    	if (clazz.equals(NoteBC.class)) {
            reply = notebc(state.getMcmID(),
                    state.getWaysDirection(), notes, state.getImei());
            
        } else if (clazz.equals(SatelliteEvent.class)) {
        	if (state.getWaysDirection().equals(Direction.sat)){
//        		sendSatNote(state.getImei(), notes);
        		sendSatSMTP(state.getImei(), notes);
        	} else {
                reply = sendHttpNote(state.getMcmID(),
                        state.getWaysDirection(),
                        notes, state.getImei());
        	}
        } else if (clazz.equals(TiwiNote.class)) {
            reply = tiwiNote(state.getImei(),
            		notes);
        } else if (clazz.equals(SatelliteEvent_t.class)){
        	if (state.getWaysDirection().equals(Direction.sat)){
//        		sendSatNote(state.getImei(), notes);
        		sendSatSMTP(state.getImei(), notes);
            } else if (state.getProductVersion().shouldSendNoteHessian()) {
                reply = ws850Note(state.getMcmID(), state.getWaysDirection(), notes);
        	} else {
        		reply = sendHttpNote(state.getMcmID(),
                        state.getWaysDirection(),
                        notes, state.getImei()); 
        	}
        } else if (clazz.equals(SatelliteStrippedConfigurator.class)){
//        	sendSatNote(state.getImei(), notes);
        	sendSatSMTP(state.getImei(), notes);
        	reply = null;
        }
    	return reply;
    }
    
    
    public Object[] sendNotes(DeviceState state, Map<Class<? extends DeviceNote>, LinkedList<DeviceNote>> sendingQueue){
        Object[] reply = new Object[sendingQueue.size()];
        Class<?> noteClass = DeviceNote.class;
        for (int i=0;i<reply.length;i++){
	        try {
	            if (sendingQueue.containsKey(NoteBC.class)) {
	                noteClass = NoteBC.class;
	                reply[i] = notebc(state.getMcmID(),
	                        state.getWaysDirection(),
	                        sendingQueue.get(noteClass), state.getImei());
	            } else if (sendingQueue.containsKey(SatelliteEvent.class)) {
	                noteClass = SatelliteEvent.class;
	            	if (state.getWaysDirection().equals(Direction.sat)){
//	            		sendSatNote(state.getImei(), sendingQueue.get(noteClass));
	            		sendSatSMTP(state.getImei(), sendingQueue.get(noteClass));
	            	} else {
	            		reply[i] = sendHttpNote(state.getMcmID(),
		                        state.getWaysDirection(),
		                        sendingQueue.get(noteClass), state.getImei());
	            	}
	            } else if (sendingQueue.containsKey(TiwiNote.class)) {
	                noteClass = TiwiNote.class;
	                reply[i] = tiwiNote(state.getImei(),
	                        sendingQueue.get(noteClass));
	            } else if (sendingQueue.containsKey(SatelliteEvent_t.class)){
	            	noteClass = SatelliteEvent_t.class;
	            	if (state.getWaysDirection().equals(Direction.sat)){
//	            		sendSatNote(state.getImei(), sendingQueue.get(noteClass));
	            	    sendSatSMTP(state.getImei(), sendingQueue.get(noteClass));
	            	} else if (state.getProductVersion().shouldSendNoteHessian()) {
            	        reply[i] = ws850Note(state.getMcmID(), state.getWaysDirection(), sendingQueue.get(noteClass));
	            	} else {
	            		reply[i] = sendHttpNote(state.getMcmID(),
	                            state.getWaysDirection(),
	                            sendingQueue.get(noteClass), state.getImei()); 
	            	}
	            } else if (sendingQueue.containsKey(SatelliteStrippedConfigurator.class)){
	            	noteClass = SatelliteStrippedConfigurator.class;
//	            	sendSatNote(state.getImei(), sendingQueue.get(noteClass));
	            	sendSatSMTP(state.getImei(), sendingQueue.get(noteClass));
	            }
	            sendingQueue.remove(noteClass);
	        } catch (SocketException e){
	        	i--;
	        	Log.error("IMEI: %s\n%s: %s\nCalls made: %s, Calls per Minute: %d",
	        			state.getImei(),
	        			e.getClass().getSimpleName(), e.getMessage(), 
	        			DeviceStatistics.getHessianCalls(), DeviceStatistics.getCallsPerMinute());
	        	continue;
	        } catch (Exception e) {
	        	Log.error("Error from Note with IMEI: " + state.getImei() + "  "
	                            + AutomationStringUtil.toString(e) + "\n"
	                            + sendingQueue + "\nCurrent Note Count is "
	                            + DeviceStatistics.getHessianCalls()
	                            + "\nCurrent time is: "
	                            + System.currentTimeMillis()
	                            + "\nNotes Started at: "
	                            + DeviceStatistics.getStart().epochTime()
	                   );
	        }
        }
        
        return reply;
    }


    @Override
    public Integer crash(String mcmID, List<byte[]> crashDataList)
            throws ProDAOException {
        return proxy.crash(mcmID, crashDataList);
    }

	public Object dumpSet(DeviceState state, Map<Integer, String> settings) {
		try {
				if (state.getProductVersion().isWaysmart()){
				return dumpSet(state.getMcmID(), state.getProductVersion().getIndex(), settings);
			} else {
				return dumpSet(state.getImei(), state.getProductVersion().getIndex(), settings);
			}
		} catch (HessianException e){
			if (e.getErrorCode()==304){
				Log.info("Device probably not assigned to a vehicle, got 304");
			} else {
				throw e;
			}
		}
		return null;
	}

	public Object reqSet(DeviceState state) {
		try {
			if (state.getProductVersion().isWaysmart()){
				return reqSet(state.getMcmID());
			} else {
				return reqSet(state.getImei());
			}
		} catch (HessianException e){
			if (e.getErrorCode()==304){
				Log.info("Vehicle doesn't have any actuals yet, got 304");
			} else {
				throw e;
			}
		}
	return null;
	}

    @Override
    public List<Map<String, Object>> checkSbsEditNG(String mcmid, List<Map<String, Object>> maps) {
        printOther(maps);
        List<Map<String, Object>> reply = proxy.checkSbsEditNG(mcmid, maps);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> getSbsEditNG(String mcmid, Map<String, Object> map) {
        printOther(map);
        Map<String, Object> reply = proxy.getSbsEditNG(mcmid, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }
    
    @Override
    public List<Map<String, Object>> checkSbsSubscribedNG(String mcmID,
            Map<String, Object> map) {
        printOther(map);
        List<Map<String, Object>> reply = proxy.checkSbsSubscribedNG(mcmID, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }
    

}
