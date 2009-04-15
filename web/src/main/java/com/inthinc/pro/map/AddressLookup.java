package com.inthinc.pro.map;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.NavigableMap;
import java.util.TreeMap;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.LatLng;
public class AddressLookup
{
    public static Logger logger = Logger.getLogger(AddressLookup.class);
    private String mapServerURLString;
    private NavigableMap<LatLng, String> addressMap = new TreeMap<LatLng, String>();

    public AddressLookup()
    {
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
        return getAddress(new LatLng(lat, lng));
    }
    public String getAddress(LatLng latLng)
    {
        if (addressMap.containsKey(latLng))
            return addressMap.get(latLng);
        if (getMapServerURLString().isEmpty())
        {
            logger.debug("AddressLookup - Map Server URL not set.");
            return "";
        }
        try
        {
            String address = sendRequest(new URL(getMapServerURLString() + "&lat=" + latLng.getLat() + "&lng=" + latLng.getLng()));
            if (address != null && !address.isEmpty())
            {
                checkMapSize();
                addressMap.put(latLng, address);
                if(logger.isDebugEnabled())
                    logger.debug("Address lookup for Latitude: " + latLng.getLat() + " Longitude: " + latLng.getLng() + " returned Address: " + address );
            }
            else
            {
                address = "No address found at location.";
            }
            return address;
        }
        catch (MalformedURLException e)
        {
            logger.debug(e);
            return "";
        }
    }

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
            }
            String status = httpConn.getResponseMessage();
            if (status.equals("OK"))
            {
                return parseAddress(httpConn.getInputStream());
            }
        }
        catch (IOException e)
        {
            logger.debug(e);
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
        Address address = new Address();
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader = null;
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
        }
        catch (XMLStreamException e)
        {
            logger.debug(e);
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
        return address.toString();
    }

    private void checkMapSize()
    {
        if (addressMap.size() > 2000)
        {
            for (int i = 0; i < 500; i++)
            {
                addressMap.pollFirstEntry();
            }
        }
    }

    class Address
    {
        String addr1 = "";
        String addr2 = "";
        String city = "";
        String state = "";
        String zip = "";

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
