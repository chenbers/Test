package com.inthinc.pro.map;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Logger;



public class AddressLookup
{
    public static Logger logger = Logger.getLogger(AddressLookup.class);
    String mapServerURLString;

    public AddressLookup() 
    {
        logger.debug("AddressLookup - constructor");
      mapServerURLString = "http://testteen.iwiglobal.com:8081/geonames/servlet/iwiglobal?srv=findNearbyAddress";

    }
    public String getMapServerURLString()
    {
        return mapServerURLString;
    }

    public void setMapServerURLString(String mapServerURLString)
    {
        logger.debug("AddressLookup - setMapServerURLString [" + mapServerURLString + "]");
        this.mapServerURLString = mapServerURLString;
    }

    public String getAddress(double lat, double lng) 
    {
        try 
        {
            String address = sendRequest(new URL(getMapServerURLString()+"&lat=" + lat + "&lng=" + lng));
            return address.length() < 1 ? "No address found at location." : address;
        }
        catch (Exception e) 
        {
            logger.error(e);
        }
        
        return "";

    }

    protected String sendRequest(URL mapServerURL)
            throws Exception
    {

        URLConnection conn = mapServerURL.openConnection();
        HttpURLConnection httpConn = null;
        if (conn instanceof HttpURLConnection) {
            try {
                httpConn = (HttpURLConnection) conn;
            }
            catch (Throwable e) {
            }
        }


        try {
            String status = httpConn.getResponseMessage();
            if (status.equals("OK"))
            {
                return parseAddress(httpConn.getInputStream());
            }
        }
        finally
        {
            httpConn.disconnect();
        }
        
        return "";
    }

    String parseAddress(InputStream is) throws Exception
    {
        Address address = new Address();
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader  = inputFactory.createXMLStreamReader(is);

        while (reader.hasNext()) {
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
                        String text = reader.getText();
                        if (name.equalsIgnoreCase("streetNumber"))
                            address.setAddr1(text);
                        else if (name.equalsIgnoreCase("street"))
                            address.setAddr2(text);
                        else if (name.equalsIgnoreCase("placename"))
                            address.setCity(text);
                        else if (name.equalsIgnoreCase("adminCode1"))
                            address.setState(text);
                        else if (name.equalsIgnoreCase("postalcode") && text.length() >= 5)
                            address.setZip(text);
                        
                    }
                }
            }
        }
        
        is.close();
        return address.toString();
    }

    class Address
    {
        String addr1="";
        String addr2="";
        String city="";
        String state="";
        String zip="";
        
        
        public Address()
        {
            
        }
        public String getAddr1()
        {
            return addr1;
        }
        public void setAddr1(String addr1)
        {
            this.addr1 = addr1;
        }
        public String getAddr2()
        {
            return addr2;
        }
        public void setAddr2(String addr2)
        {
            this.addr2 = addr2;
        }
        public String getCity()
        {
            return city;
        }
        public void setCity(String city)
        {
            this.city = city;
        }
        public String getState()
        {
            return state;
        }
        public void setState(String state)
        {
            this.state = state;
        }
        public String getZip()
        {
            return zip;
        }
        public void setZip(String zip)
        {
            this.zip = zip;
        }
        public String toString()
        {
            StringBuffer buffer = new StringBuffer();
            buffer.append(addr1);
            if (buffer.length() > 0)
                buffer.append(" ");
            buffer.append(addr2);
            if (buffer.length() > 0)
                buffer.append(", ");
            buffer.append(city);
            if (buffer.length() > 0)
                buffer.append(", ");
            buffer.append(state);
            if (buffer.length() > 0)
                buffer.append(" ");
            buffer.append(zip);
            
            return buffer.toString();
        }
    }

}


