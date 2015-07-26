package com.android.websocket.module;

/**
 * Created by wxj on 2015-7-26.
 */
public class TextMessage extends  IMessage {
    private String content;
    public TextMessage(){
        setMsgType(MsgType.TEXT.value());
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
