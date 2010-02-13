package com.inthinc.pro.map;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.NoAddressFoundException;

public class GoogleAddressLookup extends AddressLookup {
	
//	private GoogleMapKeyFinder googleMapKeyFinder;
    private String googleMapGeoUrl;
	
	private LatLng latLng;
	
	
	public GoogleAddressLookup() {
		super();
		setAddressFormat(AddressLookup.AddressFormat.ADDRESS);
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
			address = sendRequest(new URL(request.toString()));
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
	
    protected String sendRequest(URL mapServerURL) throws NoAddressFoundException
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
	                return parseAddress(httpConn.getInputStream());
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


//	public GoogleMapKeyFinder getGoogleMapKeyFinder() {
//		return googleMapKeyFinder;
//	}
//
//
//	public void setGoogleMapKeyFinder(GoogleMapKeyFinder googleMapKeyFinder) {
//		this.googleMapKeyFinder = googleMapKeyFinder;
//	}

}
