package com.android.websocket.module;

/**
 * Created by wxj on 2015-7-26.
 */
public class LocationMessage extends IMessage {
    private String longitude;
    private String latitude;

    public LocationMessage() {
        setMsgType(MsgType.LOCATION.value());
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
}
