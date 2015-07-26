package com.android.websocket.module;

/**
 * Created by wxj on 2015-7-26.
 */
public class ImageMessage extends IMessage {
    private String picUrl;

    public ImageMessage() {
        setMsgType(MsgType.IMAGE.value());
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
