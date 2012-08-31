package com.inthinc.device.emulation.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.utils.AutoHTTPException;
import com.inthinc.pro.automation.utils.HTTPCommands;

public class GoogleTrips {
    
    private HTTPCommands http;
    private static final String directionsAPI = "http://maps.googleapis.com/maps/api/directions/";
    private static final String geocodeAPI = "http://maps.googleapis.com/maps/api/geocode/";
    private static enum Mode{json, xml};
    
    public GoogleTrips(){
        http = new HTTPCommands();
    }
    
    public LinkedList<GeoPoint> getTrip(String start, String stop){
        String query = http.constructQuery("sensor", "false", "origin", start, "destination", stop);
        GetMethod getRequest = new GetMethod(directionsAPI + Mode.json);
        getRequest.setQueryString(query);
        try {
            return processReply(sendRequest(getRequest));
        } catch (JSONException e) {
        	Log.error("%s", e);
        }
        return null;
    }
    
    private JSONObject sendRequest(GetMethod getMethod){
    	try {
    		Log.debug("sendRequest... %s", getMethod.getURI().toString());
            String response = http.httpRequest(getMethod);
            return new JSONObject(response);
            
        } catch (HttpException e) {
        	Log.error("%s", e);
        } catch (JSONException e) {
        	Log.error("%s", e);
        } catch (AutoHTTPException e) {
			Log.error(e);
		}
    	return null;
    }
    
    public GeoPoint getLocation(String location){
    	GetMethod getRequest = new GetMethod(geocodeAPI + Mode.json);
    	getRequest.setQueryString(http.constructQuery("address", location, "sensor", "false"));
    	JSONObject response = sendRequest(getRequest);
    	try {
			JSONObject latLng = response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
			return new GeoPoint(latLng.getDouble("lat"), latLng.getDouble("lng"));
		} catch (JSONException e) {
        	Log.error("%s", e);
		}
    	return new GeoPoint(-1.0, -1.0);
    }
    
    private LinkedList<GeoPoint> processReply(JSONObject reply) throws JSONException{
        JSONArray routes = reply.getJSONArray("routes");
        LinkedList<GeoPoint> points = new LinkedList<GeoPoint>();
        for (int i=0;i<routes.length();i++){
            JSONArray legs = routes.getJSONObject(i).getJSONArray("legs");
            for (int j=0;j<legs.length();j++){
                JSONArray steps = legs.getJSONObject(j).getJSONArray("steps");
                for (int k=0;k<steps.length();k++){
                    JSONObject polyline = steps.getJSONObject(k).getJSONObject("polyline");
                    List<GeoPoint> poly = decodePoly(polyline.getString("points"));
                    points.addAll(poly);
                }
            }
        }
        return points;
    }
    

    /**
     * Code borrowed from:<BR />
     * http://jeffreysambells.com/posts/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java/
     * @param encoded
     * @return
     */
    private final List<GeoPoint> decodePoly(final String encoded) {

        final List<GeoPoint> poly = new ArrayList<GeoPoint>();
        int index = 0;
        final int len = encoded.length();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            GeoPoint p = new GeoPoint((int) (((double) lat / 1E5) * 1E6),
                    (int) (((double) lng / 1E5) * 1E6));
            poly.add(p);
        }
        return poly;
    }

}
