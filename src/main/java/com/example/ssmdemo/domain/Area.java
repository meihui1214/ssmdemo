package com.example.ssmdemo.domain;

public class Area {
    private String ID;
    private String areaCode;
    private String longitude;
    private String latitude;
    private String areaName;

    public Area() {
    }


    public Area(String ID, String areaCode, String longitude, String latitude, String areaName) {
        this.ID = ID;
        this.areaCode = areaCode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.areaName = areaName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public String toString() {
        return "Area{" +
                "ID='" + ID + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", areaName='" + areaName + '\'' +
                '}';
    }
}
