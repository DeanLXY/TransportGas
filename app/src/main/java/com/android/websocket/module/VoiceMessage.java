package com.android.websocket.module;

/**
 * Created by wxj on 2015-7-26.
 */
public class VoiceMessage extends IMessage {
    private String mediaId;
    private String format; //amr
    private String recognition;

    public VoiceMessage() {
        setMsgType(MsgType.VOICE.value());
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getRecognition() {
        return recognition;
    }

    public void setRecognition(String recognition) {
        this.recognition = recognition;
    }
}
