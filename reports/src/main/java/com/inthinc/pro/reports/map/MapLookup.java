package com.inthinc.pro.reports.map;


import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;

import com.inthinc.pro.model.LatLng;

/**
 * MapLookup
 * <p/>
 * http://code.google.com/apis/maps/documentation/staticmaps/index.html
 *
 * @author Nazmul Idris
 * @version 1.0
 * @since Apr 16, 2008, 10:55:50 PM
 */
public class MapLookup {

    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // constants
    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    public static final String GmapStaticURI = "http://maps.google.com/staticmap";
    public static final String GmapLicenseKey = "key";

    public static final String CenterKey = "center";

    public static final String ZoomKey = "zoom";
    public static final int ZoomMax = 19;
    public static final int ZoomMin = 0;
    public static final int ZoomDefault = 10;

    public static final String SizeKey = "size";
    public static final String SizeSeparator = "x";
    public static final int SizeMin = 10;
    public static final int SizeMax = 512;
    public static final int SizeDefault = SizeMax;

    public static final String MarkerSeparator = "|";
    public static final String MarkersKey = "markers";

    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // data
    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    public static final MapLookup _map = new MapLookup();

    //tiwipro.com google maps API key.  Static API will use any key.
    public static String GmapLicense = "ABQIAAAARtgq9YcTFWIMNsIRoOX_WBR3PiFI7mZMjH8yU_TtZbYz5XJthxR-VrFMwQ0YsmHb63Licp69vZdoCw"; 

    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // set the license key
    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    public static void setLicenseKey(String lic) {
        GmapLicense = lic;
    }

    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // methods
    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    public static String getMap(double lat, double lon) {
        return getMap(lat, lon, SizeMax, SizeMax);
    }

    public static String getMap(double lat, double lon, int sizeW, int sizeH) {
        return getMap(lat, lon, sizeW, sizeH, ZoomDefault);
    }

    public static String getMap(double lat, double lon, int sizeW, int sizeH, int zoom) {
        return _map.getURI(lat, lon, sizeW, sizeH, zoom);
    }

    public static String getMap(double lat, double lon, int sizeW, int sizeH, MapMarker... markers) {
        return _map.getURI(lat, lon, sizeW, sizeH, markers);
    }

    public static String getMap(double lat, double lon, MapMarker... markers) {
        return getMap(lat, lon, SizeMax, SizeMax, markers);
    }
    public static String getMap(int sizeW, int sizeH, List<LatLng> path){
        return _map.getURI(sizeW, sizeH, path);
    }
    public static String getMap(int sizeW, int sizeH, List<LatLng> path, MapMarker... marks){
        return _map.getURI(sizeW, sizeH, path, marks);
    }

    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // param handling and uri generation
    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    public String getURI(int sizeW, int sizeH, List<LatLng> path ) {
        _validateParams(sizeW, sizeH, ZoomDefault);

        // generate the URI
        StringBuilder sb = new StringBuilder();
        sb.append(GmapStaticURI);

        // size key
        sb.
        append("?").
        append(SizeKey).append("=").append(sizeW).append(SizeSeparator).append(sizeH);

        // markers key
        sb.
        append("&").
        append(MarkerUtils.pathToString(path));

        // maps key
        sb.
        append("&").
        append(GmapLicenseKey).append("=").append(GmapLicense);


        return sb.toString();
    }
    
    public String getURI(int sizeW, int sizeH, List<LatLng> path, MapMarker ... marks ) {
        _validateParams(sizeW, sizeH, ZoomDefault);

        // generate the URI
        StringBuilder sb = new StringBuilder();
        sb.append(GmapStaticURI);

        // size key
        sb.
        append("?").
        append(SizeKey).append("=").append(sizeW).append(SizeSeparator).append(sizeH);

        // markers key, start and end
        sb.
        append("&").
        append(MarkerUtils.toString(marks));        

        // markers key
        sb.
        append("&").
        append(MarkerUtils.pathToString(path));

        // maps key
        sb.
        append("&").
        append(GmapLicenseKey).append("=").append(GmapLicense);


        return sb.toString();
    }    

    public String getURI(double lat, double lon, int sizeW, int sizeH, MapMarker... markers) {
        _validateParams(sizeW, sizeH, ZoomDefault);

        // generate the URI
        StringBuilder sb = new StringBuilder();
        sb.append(GmapStaticURI);

        // size key
        sb.
        append("?").
        append(SizeKey).append("=").append(sizeW).append(SizeSeparator).append(sizeH);

        // markers key
        sb.
        append("&").
        append(MarkerUtils.toString(markers));

        // maps key
        sb.
        append("&").
        append(GmapLicenseKey).append("=").append(GmapLicense);


        return sb.toString();
    }

    public String getURI(double lat, double lon, int sizeW, int sizeH, int zoom) {
        _validateParams(sizeW, sizeH, zoom);

        // generate the URI
        StringBuilder sb = new StringBuilder();
        sb.append(GmapStaticURI);

        // center key
        sb.
        append("?").
        append(CenterKey).append("=").append(lat).append(",").append(lon);

        // zoom key
        sb.
        append("&").
        append(ZoomKey).append("=").append(zoom);

        // size key
        sb.
        append("&").
        append(SizeKey).append("=").append(sizeW).append(SizeSeparator).append(sizeH);

        // markers key
        sb.
        append("&").
        append(MarkerUtils.toString(new MapMarker(lat, lon)));


        // maps key
        sb.
        append("&").
        append(GmapLicenseKey).append("=").append(GmapLicense);

        return sb.toString();
    }

    private void _validateParams(int sizeW, int sizeH, int zoom) {
        if (zoom < ZoomMin || zoom > ZoomMax)
            throw new IllegalArgumentException("zoom value is out of range [" + ZoomMin + "-" + ZoomMax + "]");

        if (sizeW < SizeMin || sizeW > SizeMax)
            throw new IllegalArgumentException("width is out of range [" + SizeMin + "-" + SizeMax + "]");

        if (sizeH < SizeMin || sizeH > SizeMax)
            throw new IllegalArgumentException("height is out of range [" + SizeMin + "-" + SizeMax + "]");
    }

    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // actually get the map from Google
    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    /** use httpclient to get the data */
    public static InputStream getDataFromURI(String uri) throws IOException
    {
        GetMethod get = new GetMethod(uri);
        try
        {
            new HttpClient().executeMethod(get);
            InputStream is = get.getResponseBodyAsStream();
            return is;
        }
        finally
        {
            get.releaseConnection();
        }
    }

    public static void SaveToFile(String url, String fileName) throws IOException
    {
        InputStream is = getDataFromURI(url);
        BufferedOutputStream fOut = null;

        try
        {
            fOut = new BufferedOutputStream(new FileOutputStream(fileName));
            byte[] buffer = new byte[32 * 1024];
            int bytesRead = 0;
            while ((bytesRead = is.read(buffer)) != -1)
            {
                fOut.write(buffer, 0, bytesRead);
            }
        }
        catch (Exception e)
        {
            throw new IOException(e.toString());
        }
        finally
        {
            close(is, fOut);
        }
    }

    protected static void close(InputStream iStream, OutputStream oStream)
    throws IOException
    {
        try
        {
            if (iStream != null)
            {
                iStream.close();
            }
        }
        finally
        {
            if (oStream != null)
            {
                oStream.close();
            }
        }
    }



    public static InputStream getImageFromUrl(String url) throws URIException, NullPointerException
    {
        HttpClient client = new HttpClient();
        client.getParams().setParameter("http.useragent", "static map client");
        client.getParams().setParameter("http.connection.timeout",new Integer(5000));

        GetMethod method  = new GetMethod();
        method.setURI(new URI(url, true));

        try
        {
            InputStream is = method.getResponseBodyAsStream(); 
            return is;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }



    /** markers=40.702147,-74.015794,blues|40.711614,-74.012318,greeng&key=MAPS_API_KEY */
    public static class MarkerUtils {
        public static String toString(MapMarker... markers) {
            if (markers.length > 0) {
                StringBuilder sb = new StringBuilder();

                sb.append(MarkersKey).append("=");

                for (int i = 0; i < markers.length; i++) {
                    sb.append(markers[i].toString());
                    if (i != markers.length - 1) sb.append(MarkerSeparator);
                }

                return sb.toString();
            }
            else {
                return "";
            }
        }

        /**path=rgb:0x0000ff,weight:5|40.737102,-73.990318|40.749825,-73.987963|40.752946,-73.987384|40.755823,-73.986397 */
        public static String pathToString(List<LatLng> path)
        {
            if(path.size() > 0)
            {
                StringBuilder sb = new StringBuilder();
                sb.append("path=rgb:0x0000ff,weight:5");

                for (LatLng point : path)
                {
                    sb.append("|" + point.getLat() + "," + point.getLng());
                }

                return sb.toString();
            }
            else
                return "";

        }
    }// class MarkerUtils



    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // self test method
    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    public static void main(String[] args) {

        // make sure to set a valid license key
        setLicenseKey("ABCDEFGHIJKLMNOPQ");

        double lat = 38.931099;
        double lon = -77.3489;

        double lat1 = 40.742100;
        double lon1 = -74.001801;

        String u1 = getMap(lat, lon);
        System.out.println(u1);

        String u2 = getMap(lat, lon, 256, 256);
        System.out.println(u2);

        String u3 = getMap(lat, lon, new MapMarker(lat, lon, MapMarker.MarkerColor.blue, 'a'));
        System.out.println(u3);

        String u4 = getMap(lat, lon,
                250, 500,
                new MapMarker(lat, lon, MapMarker.MarkerColor.green, 'v'),
                new MapMarker(lat1, lon1, MapMarker.MarkerColor.red, 'n')
        );
        System.out.println(u4);

    }
//    getExternalContext().getRequestContextPath()+"/images/ico_idle.png"
}//end class MapLookup
