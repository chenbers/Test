package com.inthinc.pro.map;

import java.math.BigDecimal;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderAddressComponent;
import com.google.code.geocoder.model.GeocoderGeometry;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.GeocoderResultType;
import com.google.code.geocoder.model.GeocoderStatus;
import com.inthinc.pro.dao.util.GeoUtil;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.NoAddressFoundException;

public class GoogleAddressLookup extends AddressLookup {
	
    private MeasurementType measurementType = MeasurementType.ENGLISH;
    private boolean debugMode = true;
	
	private LatLng latLng;
	
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
            
            GeocodeResponse geocoderResponse = geocode(latLng);
            Placemark placemark = new Placemark();
            boolean gotPlacemarkInfo = false;
            for (GeocoderResult result : geocoderResponse.getResults()) {
                placemark.setAddress(result.getFormattedAddress());
                for (GeocoderAddressComponent component : result.getAddressComponents()) {
                    for (String type : component.getTypes()) {
                        try {
                            GeocoderResultType componentType = GeocoderResultType.fromValue(type);
                            if (componentType == GeocoderResultType.LOCALITY) {
                                placemark.setLocality(component.getLongName());
                            }
                            else if (componentType == GeocoderResultType.ADMINISTRATIVE_AREA_LEVEL_1) {
                                placemark.setState(component.getShortName());
                            }
                        } 
                        catch (IllegalArgumentException ex) {
                            // continue -- just a type the library doesn't know about
                        }
                    }
                    gotPlacemarkInfo =  (placemark.getLocality() != null && placemark.getState() != null);
                    if (gotPlacemarkInfo) {
                        break;
                    }
                }
                
                if (gotPlacemarkInfo) {
                    break;
                }
                
            }
            
            if (gotPlacemarkInfo) {
                // The location (lat, lng) in the original request is for the county not the city, so looking up the city to gauge the distance and heading)
                Geocoder geocoder = getGeocoder();
                GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(placemark.getCityState()).getGeocoderRequest();
                geocoderResponse = geocoder.geocode(geocoderRequest);
                if (geocoderResponse == null || geocoderResponse.getStatus() != GeocoderStatus.OK || geocoderResponse.getResults() == null || geocoderResponse.getResults().isEmpty()) {
                    return placemark.getDescription();
                }
                GeocoderResult result = geocoderResponse.getResults().get(0);
                GeocoderGeometry geometry = result.getGeometry();
                placemark.setLatLng(new LatLng(geometry.getLocation().getLat().doubleValue(), geometry.getLocation().getLng().doubleValue() ));
                placemark.setDistanceFromTarget(placemark.getDistanceFromTarget(this.latLng, measurementType));
                placemark.setHeadingToTarget(GeoUtil.headingBetween(placemark.getLatLng(), this.latLng));
            }
            
            if(this.debugMode) {
                double plat = (placemark == null || placemark.getLatLng() == null) ? 0.0d : placemark.getLatLng().getLat();
                double plng = (placemark == null || placemark.getLatLng() == null) ? 0.0d : placemark.getLatLng().getLng();
                System.out.println("http://maps.google.com/maps?f=d&source=s_d&saddr="+this.getLatLng().getLat()+","+this.getLatLng().getLng()+"&daddr="+plat+","+plng+"&geocode=&hl=en&mra=ls&vps=4&ie=UTF8");
            }
            return placemark.getDescription();
        }
        throw new NoAddressFoundException(null, null, NoAddressFoundException.reasons.CLIENTSIDE);
    }

	@Override
	public String getAddress(LatLng latLng) throws NoAddressFoundException {
    	GeocodeResponse geocoderResponse = geocode(latLng);
        GeocoderResult result = geocoderResponse.getResults().get(0);
        return result.getFormattedAddress();
		
	}

	
	private GeocodeResponse geocode(LatLng latLng) throws NoAddressFoundException	{
	    
        if (!isValidLatLngRange(latLng)){
            throw new NoAddressFoundException(latLng.getLat(),latLng.getLng(), NoAddressFoundException.reasons.INVALID_LATLNG);
        }
        Geocoder geocoder = getGeocoder();
        GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setLanguage(getLanguage()).setLocation(new com.google.code.geocoder.model.LatLng(BigDecimal.valueOf(latLng.getLat()), BigDecimal.valueOf(latLng.getLng()))).getGeocoderRequest();
        GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
        if (geocoderResponse == null || geocoderResponse.getStatus() != GeocoderStatus.OK || geocoderResponse.getResults() == null || geocoderResponse.getResults().isEmpty()) {
            throw new NoAddressFoundException(latLng.getLat(), latLng.getLng(), NoAddressFoundException.reasons.NO_ADDRESS_FOUND);
        }
        
        return geocoderResponse;
	}
	
    public class Placemark{
        private String address;
        private String locality;
        private LatLng latLng;
        private float distanceFromTarget;
        private String headingToTarget;
        private String state;
        public Placemark(){
        }
        public String toString() {
            return "Placemark: [ address=" + address + ", locality=" + locality + ", state=" + state+ ", latLng=" + latLng + ", distanceFromTarget=" + distanceFromTarget
                    + ", heading=" + headingToTarget + "]";
        }
        public float getDistanceFromTarget(LatLng target, MeasurementType measurementType){
            return GeoUtil.distBetween(this.getLatLng(), target, measurementType);
        }
        public String getAddress() {
            return address;
        }
        public void setAddress(String address) {
                this.address = address;
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
        public String getCityState() {
            return getLocality() + ", " + getState();
        }
        public String getDescription() {
            if (getLocality() == null) {
                return getState();
            }
            if(getDistanceFromTarget() > 5) {
                return String.format("%.2f", getDistanceFromTarget()) +" "+getDistanceType()+" "+getHeadingToTarget()+" of " + getLocality() +", "+ getState();
            }
            return getLocality() +", "+ getState();
        }
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

}
