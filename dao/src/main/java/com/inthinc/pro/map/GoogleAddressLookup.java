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

public class GoogleAddressLookup implements AddressLookup {
	
//	GoogleMapBacking googleMapBacking;

	@Override
	public String getAddress(LatLng latLng, boolean returnLatLng)
			throws NoAddressFoundException {
		
		StringBuilder request = new StringBuilder("http://maps.google.com/maps/geo?q=");
		request.append(latLng.getLat());
		request.append(",");
		request.append(latLng.getLng());
		request.append("&output=xml&sensor=true_or_false&key=wtf");
//		request.append(googleMapBacking.getKey());
		
		String address = null;
		try{
			address = sendRequest(new URL(request.toString()));
			if ((address == null) || address.isEmpty()) {
	               if(returnLatLng){
	                    address = latLng.getLat() + ", " + latLng.getLng();
	                }else{
//	                    address = "No address found at location.";
	                	//Changed to an exception because the hardcoded string needed to be replaced by a resource bundle message
	                	//so it can be i18ned
	                	throw new NoAddressFoundException(latLng.getLat(),latLng.getLng());
	                }
			}
		}
		catch(MalformedURLException murle){
			
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
	
//	public GoogleMapBacking getGoogleMapBacking() {
//		return googleMapBacking;
//	}
//
//	public void setGoogleMapBacking(GoogleMapBacking googleMapBacking) {
//		this.googleMapBacking = googleMapBacking;
//	}
    protected String sendRequest(URL mapServerURL)
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
	            	
	            	return null;
	            }
	        }
        }
        catch (IOException e)
        {
        }
        finally
        {
            if (httpConn != null)
            {
                httpConn.disconnect();
                httpConn = null;
            }
        }
        return "";
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

}
