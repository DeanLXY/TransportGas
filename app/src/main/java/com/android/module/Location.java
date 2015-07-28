package com.android.module;

/**
 * Created by wxj on 2015-7-28.
 *
 * @info location info
 */
public class Location {
    private int locType;  //gps network
    private double longitude;
    private double latitude;
    private String addrStr;

    public int getLocType() {
        return locType;
    }

    public void setLocType(int locType) {
        this.locType = locType;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddrStr() {
        return addrStr;
    }

    public void setAddrStr(String addrStr) {
        this.addrStr = addrStr;
    }


    public static enum LocType {
        TypeGpsLocation(61), TypeNetWorkException(63), TypeOffLineLocation(66);
        private int value;

        LocType(int value) {
            this.value = value;
        }

        public int intValue() {
            return this.value;
        }
    }
}
