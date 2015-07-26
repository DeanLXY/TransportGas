package com.android.websocket.module;

/**
 * Created by wxj on 2015-7-26.
 */
public enum MsgType {
    TEXT("text"), IMAGE("image"), LOCATION("location"), VOICE("voice");
    private String value;

    MsgType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
