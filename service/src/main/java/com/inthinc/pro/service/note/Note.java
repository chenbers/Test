package com.inthinc.pro.service.note;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import com.inthinc.pro.model.event.NoteType;

public class Note {

	private NoteType noteType;
	private Date date;
	private Double lat;
	private Double lng;
	private Integer speed;
	private Integer odometer;
	private Integer sats;
	private Integer heading;

	private List<Attribute> attributeList;
	
	public Note(NoteType noteType, Date date, Double lat, Double lng,
			Integer speed, Integer odometer, Integer sats,Integer heading, Attribute... attributes) {
		super();
		this.noteType = noteType;
		this.date = date;
		this.lat = lat;
		this.lng = lng;
		this.speed = speed;
		this.odometer = odometer;
		this.heading = heading;
		this.sats = sats;
		if (attributes != null)
			this.setAttributeList(Arrays.asList(attributes));
	}
	
	public byte[] getBytes(){
		
        String headingString = Integer.toBinaryString(heading);
        String satsString = Integer.toBinaryString(sats);
        String flagsString = headingString + satsString;
        Integer flags = Integer.parseInt(flagsString, 2);
	    
		byte[] noteBytes = new byte[200];
		int idx = 0;
		noteBytes[idx++] = (byte) (noteType.getCode() & 0x000000FF);
        idx = puti4(noteBytes, idx, (int)(date.getTime()/1000l));
        noteBytes[idx++] = (byte) (flags & 0x000000FF); 
        noteBytes[idx++] = (byte) 8; // maprev
        idx = putlat(noteBytes, idx, lat);
        idx = putlng(noteBytes, idx, lng);
        noteBytes[idx++] = (byte) (speed & 0x000000FF);
        idx = puti2(noteBytes, idx, odometer);
        
        for(Attribute attribute:attributeList){
        	byte[] attributeBytes = attribute.getBytes();
        	for(int i = 0;i < attributeBytes.length;i++){
        	    noteBytes[idx++] = attributeBytes[i];
        	}
        }
        
        byte[] compressedBytes = new byte[idx];
        for(int i = 0;i < idx;i++){
            compressedBytes[i] = noteBytes[i];
        }
        
        return compressedBytes;
	}

	private int putlat(byte[] eventBytes, int idx, Double latitude) {
		latitude = 90.0 - latitude;
		latitude = latitude / 180.0;

		return putlatlng(eventBytes, idx, latitude);
	}

	private int putlng(byte[] eventBytes, int idx, Double longitude) {
		if (longitude < 0.0)
			longitude = longitude + 360;
		longitude = longitude / 360.0;

		return putlatlng(eventBytes, idx, longitude);
	}

	private int putlatlng(byte[] eventBytes, int idx, Double latlng) {
		int i = (int) (latlng * 0x00ffffff);
		eventBytes[idx++] = (byte) ((i >> 16) & 0x000000FF);
		eventBytes[idx++] = (byte) ((i >> 8) & 0x000000FF);
		eventBytes[idx++] = (byte) (i & 0x000000FF);
		return idx;
	}

	private int puti4(byte[] eventBytes, int idx, Integer i) {
		eventBytes[idx++] = (byte) ((i >> 24) & 0x000000FF);
		eventBytes[idx++] = (byte) ((i >> 16) & 0x000000FF);
		eventBytes[idx++] = (byte) ((i >> 8) & 0x000000FF);
		eventBytes[idx++] = (byte) (i & 0x000000FF);
		return idx;
	}

	private int puti2(byte[] eventBytes, int idx, Integer i) {
		eventBytes[idx++] = (byte) ((i >> 8) & 0x000000FF);
		eventBytes[idx++] = (byte) (i & 0x000000FF);
		return idx;
	}

	public NoteType getNoteType() {
		return noteType;
	}

	public void setNoteType(NoteType noteType) {
		this.noteType = noteType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Integer getOdometer() {
		return odometer;
	}

	public void setOdometer(Integer odometer) {
		this.odometer = odometer;
	}

	public void setAttributeList(List<Attribute> attributeList) {
		this.attributeList = attributeList;
	}

	public List<Attribute> getAttributeList() {
		return attributeList;
	}

}
