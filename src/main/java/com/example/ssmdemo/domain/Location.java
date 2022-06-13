package com.example.ssmdemo.domain;

public class Location {
   private Double lat =0.0;

    private Double lng =0.0;

    public Location(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Location() {
    }

    @Override
    public String toString() {
        return "Location{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
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
}
