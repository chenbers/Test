package com.inthinc.pro.map;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.NoAddressFoundException;

public class GoogleAddressLookup extends AddressLookup {
	
//	private GoogleMapKeyFinder googleMapKeyFinder;
    private String googleMapGeoUrl;
    private MeasurementType measurementType = MeasurementType.ENGLISH;
    private boolean debugMode = false;
	
	private LatLng latLng;
	private enum ResultType {
        ADDRESS, CLOSEST_TOWN;
    }
	
	public GoogleAddressLookup() {
		super();
		setAddressFormat(AddressLookup.AddressFormat.ADDRESS);
	}

	public String getClosestTownString(LatLng latLng, MeasurementType measurementType) throws NoAddressFoundException {
	    return getClosestTownString(latLng, measurementType, false);
	}
    public String getClosestTownString(LatLng latLng, MeasurementType measurementType, boolean debugMode) throws NoAddressFoundException {
        if(latLng != null){
            this.debugMode = debugMode;
            setMeasurementType(measurementType);
            this.latLng = latLng;
    
            StringBuilder request = new StringBuilder(googleMapGeoUrl).append(latLng.getLat()).append(",").append(latLng.getLng()).append("&output=xml");
            if(this.debugMode)
                System.out.println("request: "+request);
            String address = null;
            try {
                address = sendRequest(new URL(request.toString()), ResultType.CLOSEST_TOWN);
                if ((address == null) || address.isEmpty()) {
                    throw new NoAddressFoundException(latLng.getLat(), latLng.getLng(), NoAddressFoundException.reasons.NO_ADDRESS_FOUND);
                }
            } catch (MalformedURLException murle) {
                throw new NoAddressFoundException(latLng.getLat(), latLng.getLng(), NoAddressFoundException.reasons.COULD_NOT_REACH_SERVICE);
            }
            return address;
        }
        throw new NoAddressFoundException(null, null, NoAddressFoundException.reasons.CLIENTSIDE);
    }

	@Override
	public String getAddress(LatLng latLng, boolean returnLatLng)
			throws NoAddressFoundException {
		
//		if ((googleMapKeyFinder == null) || (googleMapKeyFinder.getKey()==null) ||googleMapKeyFinder.getKey().isEmpty()) {
			if (returnLatLng){
				
				return latLng.getLat() + ", " + latLng.getLng();
			}
//			else {
//				
//				throw new NoAddressFoundException(latLng.getLat(),latLng.getLng(), NoAddressFoundException.reasons.NO_MAP_KEY);
//			}
//		}
		this.latLng = latLng;
		
//		StringBuilder request = new StringBuilder("http://maps.google.com/maps/geo?q=");
//		request.append(latLng.getLat());
//		request.append(",");
//		request.append(latLng.getLng());
//		request.append("&output=xml&sensor=true&key=");
//		request.append(getGoogleMapKeyFinder().getKey());
		
		StringBuilder request = new StringBuilder(googleMapGeoUrl)
		    .append(latLng.getLat())
		    .append(",")
		    .append(latLng.getLng())
		    .append("&output=xml");
		
		String address = null;
		try{
			address = sendRequest(new URL(request.toString()), ResultType.ADDRESS);
			if ((address == null) || address.isEmpty()) {
				
               if(returnLatLng){
            	   
                    return latLng.getLat() + ", " + latLng.getLng();
               }
               else{

                	throw new NoAddressFoundException(latLng.getLat(),latLng.getLng(), NoAddressFoundException.reasons.NO_ADDRESS_FOUND);
               }
			}
		}
		catch(MalformedURLException murle){
			
            if(returnLatLng){
         	   
                return latLng.getLat() + ", " + latLng.getLng();
           }
           else{

        	   throw new NoAddressFoundException(latLng.getLat(),latLng.getLng(), NoAddressFoundException.reasons.COULD_NOT_REACH_SERVICE);
           }
		}
		return address;
		
	}


	@Override
	public String getAddress(double lat, double lng)
			throws NoAddressFoundException {
		
		return getAddress(new LatLng(lat,lng), false);
	}

	@Override
	public String getAddress(LatLng latLng) throws NoAddressFoundException {
		
		return getAddress(latLng,false);
		
	}
	protected String sendRequst(URL mapServerURL) throws NoAddressFoundException {
	    return sendRequest(mapServerURL, ResultType.ADDRESS);
	}
    protected String sendRequest(URL mapServerURL, ResultType resultType) throws NoAddressFoundException
    {
        URLConnection conn;
        HttpURLConnection httpConn = null;
        try
        {
            conn = mapServerURL.openConnection();
            if (conn instanceof HttpURLConnection)
            {
                httpConn = (HttpURLConnection) conn;
	            String status = httpConn.getResponseMessage();
	            if (status.equals("OK"))
	            {
	                switch (resultType) {
	                    case ADDRESS:      return parseAddress(httpConn.getInputStream());
	                    case CLOSEST_TOWN: return describeBestPlacemark(getPlacemarks(httpConn.getInputStream()));
	                    default: throw new NoAddressFoundException(latLng.getLat(), latLng.getLng(), NoAddressFoundException.reasons.CLIENTSIDE_UNRECOGNISED_RESULTTYPE);
	                }
	            }
	            else {	
	            	throw new NoAddressFoundException(latLng.getLat(),latLng.getLng(), NoAddressFoundException.reasons.COULD_NOT_REACH_SERVICE);
	            }
	        }
        }
        catch (IOException e)
        {
        	throw new NoAddressFoundException(latLng.getLat(),latLng.getLng(), NoAddressFoundException.reasons.COULD_NOT_REACH_SERVICE);       
        }
        finally
        {
            if (httpConn != null)
            {
                httpConn.disconnect();
                httpConn = null;
            }
        }
        return null;
    }
    private String parseAddress(InputStream is)
    {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader = null;
        String text="";
        try
        {
            reader = inputFactory.createXMLStreamReader(is);
            while (reader.hasNext())
            {
                int event = reader.next();
                if (event == XMLStreamConstants.START_ELEMENT)
                {
                    String name = reader.getLocalName();
                    if (name == null)
                        continue;
                    if (reader.hasNext())
                    {
                        event = reader.next();
                        if (event == XMLStreamConstants.CHARACTERS)
                        {
                            String nextText = reader.getText();
                            if (name.equalsIgnoreCase("address")){
                                text = nextText;
                                break;
                            }
                        }
                    }
                }
            }
        }
        catch (XMLStreamException e)
        {
            return "";
        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                    reader = null;
                }
            }
            catch (XMLStreamException e)
            {
                reader = null;
            }
        }
        return text;
    }


    public String getGoogleMapGeoUrl() {
        return googleMapGeoUrl;
    }


    public void setGoogleMapGeoUrl(String googleMapGeoUrl) {
        this.googleMapGeoUrl = googleMapGeoUrl;
    }

    private ArrayList<Placemark> getPlacemarks(InputStream is) { 
        ArrayList<Placemark> results = new ArrayList<Placemark>();
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader = null;
        StringBuffer text = new StringBuffer();;
        try {
            reader = inputFactory.createXMLStreamReader(is);
            Placemark placemark = new Placemark();
            int event;
            HashMap<String, String> attributes = new HashMap<String, String>();
            String name = "NOT YET SET";
            while (reader.hasNext()) {
                event = reader.next();

                if (event == XMLStreamConstants.END_ELEMENT) {

                    name = reader.getLocalName();
                    if (name.equalsIgnoreCase("Placemark")) {
                        placemark.setDistanceFromTarget(placemark.getDistanceFromTarget(this.latLng, measurementType));
                        placemark.setHeadingToTarget(placemark.headingBetween(this.latLng, placemark.getLatLng()));
                        results.add(placemark);
                    }
                }
                if (event == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    attributes.putAll(getAttributes(reader));
                }
                if (name == null) {
                    continue;
                }

                if (event == XMLStreamConstants.CHARACTERS) {
                    String nextText = reader.getText(); 
                    if ("placemark".equalsIgnoreCase(name)) {
                        placemark = new Placemark(attributes.get("id"));
                    } else if ("AddressDetails".equalsIgnoreCase(name)) {
                        if (attributes != null) {
                            placemark.setAccuracy(Integer.parseInt(attributes.get("Accuracy")));
                        } else {
                            //not finding accuracy is a problem, but because there are typicaly multiple results we don't want to Throw an exception
                            System.out.println("there was a problem... accuracy was not found on ONE of the placemarks?");
                        }
                    } else if ("address".equalsIgnoreCase(name)) {
                        if(placemark.getAddress() == null)//TODO: catching weird artifacts on address, looks like it's because of the newline?
                            placemark.setAddress(nextText);
                    } else if ("LocalityName".equalsIgnoreCase(name)) {
                        placemark.setLocality(nextText);
                    } else if ("coordinates".equalsIgnoreCase(name)) {
                        String[] latlngValues = nextText.split(",");
                        placemark.setLatLng(new LatLng(Double.parseDouble(latlngValues[1]), Double.parseDouble(latlngValues[0])));
                    } else if ("AdministrativeAreaName".equalsIgnoreCase(name)) {
                        placemark.setState(nextText);
                    } else {
                        //System.out.println("currently ignoring XML (1): " + name + " : " + nextText);
                    }
                } else {
                    //System.out.println("currently ignoring XML (2): " + name + " : " + getEventTypeString(event));
                }
            }
        } catch (XMLStreamException e) {
            //System.out.println("XMLStreamException: "+e);
            return results; //return what you have
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                    reader = null;
                }
            } catch (XMLStreamException e) {
                reader = null;
            }
        }
       return results;
    }
    public String describeBestPlacemark(ArrayList<Placemark> placemarks){
        StringBuffer text = new StringBuffer();
        double tempLat = 0d;
        double tempLng = 0d;
        int bestPlacemarkAccuracy =0;
        for (Placemark p : placemarks) {
            if(p != null && p.getAccuracy() != null) {
                if(p.getAccuracy()>bestPlacemarkAccuracy){
                    bestPlacemarkAccuracy = p.getAccuracy();
                    if(5< p.getDistanceFromTarget()){
                        text.append(String.format("%.2f",p.getDistanceFromTarget()) +" "+getDistanceType()+" "+p.getHeadingToTarget()+" of ");
                    }
                    text.append( p.getLocality() +", "+ p.getState());
                    tempLat = p.getLatLng().getLat();
                    tempLng = p.getLatLng().getLng();
                    if(p.getAccuracy()>=8 && p.getLocality() != null && !p.getLocality().equals(""))
                        break;
                }
            }
        }
        if(this.debugMode)
            System.out.println("http://maps.google.com/maps?f=d&source=s_d&saddr="+this.getLatLng().getLat()+","+this.getLatLng().getLng()+"&daddr="+tempLat+","+tempLng+"&geocode=&hl=en&mra=ls&vps=4&ie=UTF8");
        return text.toString();
    }
    public class KMLElement{
        HashMap<String, String> attributes;
        String name;
        String value;
        ArrayList<KMLElement> children;
        
        public String toString(){
            return "KMLElement: [name="+name+", value="+value+", attributes="+attributes+", children="+children+", ]";
        }
    }
    public class Placemark{
        private String id;
        private String address;
        private Integer accuracy;
        private String locality;
        private LatLng latLng;
        private float distanceFromTarget;
        private String headingToTarget;
        private String state;
        public Placemark(){
        }
        public Placemark(String id){
            this.id = id;
        }

        public String toString() {
            return "Placemark: [ id=" + id + ", address=" + address + ", accuracy=" + accuracy + ", locality=" + locality + ", latLng=" + latLng + ", distanceFromTarget=" + distanceFromTarget
                    + ", heading=" + headingToTarget + "]";
        }
        public float getDistanceFromTarget(LatLng target, MeasurementType measurementType){
            return distBetween(this.getLatLng(), target, measurementType);
        }
        public String headingBetween(LatLng orig, LatLng dest) {
          //TODO: jwimmer: could be static if there was a better place for it to live? (i.e. not in an inner class)
            String results = " * ";
            double deltaLong = Math.abs(orig.getLng()-dest.getLng());
            double y = Math.sin(deltaLong) * Math.cos(dest.getLat());
            double x = Math.cos(orig.getLat())*Math.sin(dest.getLat()) -
                    Math.sin(orig.getLat())*Math.cos(orig.getLat())*Math.cos(deltaLong);
            double bearing = Math.toDegrees(Math.atan2(y, x));
            double north = 360; //or full circle in degrees
            String[] directionals = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
            double bearingDegreesDelta = 360; //starting at zero and the farthest bearing couldn't be more than 360 degrees away
            for(int i = 0; i < directionals.length; i++){
                double degrees = (north/directionals.length)*i;   
                double tempDelta = Math.abs(bearing - degrees);
                if(tempDelta < bearingDegreesDelta) {
                    bearingDegreesDelta = tempDelta;
                    results = directionals[i]; 
                } else {
                    break;
                }
            }
            return results;
        }
        public float distBetween(LatLng orig, LatLng dest, MeasurementType units) {
            //TODO: jwimmer: could be static if there was a better place for it to live? (i.e. not in an inner class)
            double earthRadiusMiles = 3958.75;
            double earthRadiusKilo = 6371;
            double earthRadius = (MeasurementType.ENGLISH.equals(units))?earthRadiusMiles:earthRadiusKilo;
            
            double dLat = Math.toRadians(dest.getLat()-orig.getLat());
            double dLng = Math.toRadians(dest.getLng()-orig.getLng());
            double halfChordSquared = Math.sin(dLat/2) * Math.sin(dLat/2) +
                       Math.cos(Math.toRadians(orig.getLat())) * Math.cos(Math.toRadians(dest.getLat())) *
                       Math.sin(dLng/2) * Math.sin(dLng/2);
            double radianDistance = 2 * Math.atan2(Math.sqrt(halfChordSquared), Math.sqrt(1-halfChordSquared));
            double dist = earthRadius * radianDistance;
            return new Float(dist).floatValue();
        }

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getAddress() {
            return address;
        }
        public void setAddress(String address) {
                this.address = address;
        }
        public Integer getAccuracy() {
            return accuracy;
        }
        public void setAccuracy(Integer accuracy) {
            this.accuracy = accuracy;
        }
        public String getLocality() {
            return locality;
        }
        public void setLocality(String locality) {
            this.locality = locality;
        }
        public LatLng getLatLng() {
            return latLng;
        }
        public void setLatLng(LatLng latLng) {
            this.latLng = latLng;
        }
        public void setDistanceFromTarget(float distanceFromTarget) {
            this.distanceFromTarget = distanceFromTarget;
        }
        public float getDistanceFromTarget() {
            return distanceFromTarget;
        }
        public String getHeadingToTarget() {
            return headingToTarget;
        }
        public void setHeadingToTarget(String headingToTarget) {
            this.headingToTarget = headingToTarget;
        }
        public void setState(String state) {
            this.state = state;
        }
        public String getState() {
            return state;
        }
    }
    
    public final static String getEventTypeString(int eventType) {
        switch (eventType) {
            case XMLEvent.START_ELEMENT:
                return "START_ELEMENT";
            case XMLEvent.END_ELEMENT:
                return "END_ELEMENT";
            case XMLEvent.PROCESSING_INSTRUCTION:
                return "PROCESSING_INSTRUCTION";
            case XMLEvent.CHARACTERS:
                return "CHARACTERS";
            case XMLEvent.COMMENT:
                return "COMMENT";
            case XMLEvent.START_DOCUMENT:
                return "START_DOCUMENT";
            case XMLEvent.END_DOCUMENT:
                return "END_DOCUMENT";
            case XMLEvent.ENTITY_REFERENCE:
                return "ENTITY_REFERENCE";
            case XMLEvent.ATTRIBUTE:
                return "ATTRIBUTE";
            case XMLEvent.DTD:
                return "DTD";
            case XMLEvent.CDATA:
                return "CDATA";
            case XMLEvent.SPACE:
                return "SPACE";
        }
        return "UNKNOWN_EVENT_TYPE , " + eventType;
    }

    private static HashMap<String, String> getAttributes(XMLStreamReader xmlr) {
        HashMap<String,String> results = new HashMap<String,String>();
        if (xmlr.getAttributeCount() > 0) {
            int count = xmlr.getAttributeCount();
            for (int i = 0; i < count; i++) { 
                results.put(xmlr.getAttributeLocalName(i), xmlr.getAttributeValue(i));
            }
        }
        return results;
    }

    public void setMeasurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
    }

    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    public String getDistanceType() {
        return (MeasurementType.ENGLISH.equals(measurementType))?"miles":"kilometers";
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

//	public GoogleMapKeyFinder getGoogleMapKeyFinder() {
//		return googleMapKeyFinder;
//	}
//
//
//	public void setGoogleMapKeyFinder(GoogleMapKeyFinder googleMapKeyFinder) {
//		this.googleMapKeyFinder = googleMapKeyFinder;
//	}

}
