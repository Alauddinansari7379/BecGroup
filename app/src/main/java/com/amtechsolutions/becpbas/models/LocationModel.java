package com.amtechsolutions.becpbas.models;

/**
 * Created by Shourav Paul on 09-03-2022.
 **/
public class LocationModel 
{
    private double geoLat, geoLon;
    private String geoLocationName;

    public double getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(double geoLat) {
        this.geoLat = geoLat;
    }

    public double getGeoLon() {
        return geoLon;
    }

    public void setGeoLon(double geoLon) {
        this.geoLon = geoLon;
    }

    public String getGeoLocationName() {
        return geoLocationName;
    }

    public void setGeoLocationName(String geoLocationName) {
        this.geoLocationName = geoLocationName;
    }
}
