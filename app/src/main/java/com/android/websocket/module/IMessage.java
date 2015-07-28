package com.android.websocket.module;

import android.content.ContentValues;

import com.android.annotation.DBField;
import com.android.util.MathUtils;

import java.lang.reflect.Field;

/**
 * Created by wxj on 2015-7-26.
 */
public abstract class IMessage {
    private String openId;
    private String createTime;
    private String msgType;
    private String msgId;
    //    private String content;
//    private String longitude;
//    private String latitude;
    private String label;
    private String scale;
    //    private String picUrl;
    private String title;
    private String description;
    private String url;
    //    private String mediaId;
    private String thumbMediaId;
    //    private String format;
//    private String recognition;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IMessage iMessage = (IMessage) o;

        return !(openId != null ? !openId.equals(iMessage.openId) : iMessage.openId != null);

    }

    @Override
    public int hashCode() {
        return openId != null ? openId.hashCode() : 0;
    }

    public ContentValues toContentValues() throws Exception {
        ContentValues cvs = new ContentValues();
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            addFieldToContentValues(cvs, field);
        }
        fields = IMessage.class.getDeclaredFields();
        for (Field field : fields) {
            addFieldToContentValues(cvs, field);
        }
        return cvs;
    }

    private void addFieldToContentValues(ContentValues cvs, Field field) throws Exception {
        DBField messageField = field.getAnnotation(DBField.class);
        String name = field.getName();
        if (messageField != null) {
            name = messageField.value();
        }

        field.setAccessible(true);
        Object value = field.get(this);
        if (value instanceof String) {
            cvs.put(name, value.toString());
        } else if (value instanceof Integer) {
            cvs.put(name, MathUtils.parseInt(value.toString()));
        } else if (value instanceof Long) {
            cvs.put(name, MathUtils.parseLong(value.toString()));
        } else if (value instanceof Boolean) {
            cvs.put(name, MathUtils.parseBoolean(value.toString()));
        }
    }

    public static Class<? extends IMessage> getMessageClassByMsgtype(String msgType) {
        Class<? extends IMessage> clazz = null;
        switch (msgType) {
            case "text":
                clazz = TextMessage.class;
                break;
            case "voice":
                clazz = VoiceMessage.class;
                break;
            case "image":
                clazz = ImageMessage.class;
                break;
            case "location":
                clazz = LocationMessage.class;
                break;
            default:
               throw new RuntimeException(msgType +"  unsupport msgtype!!");
        }
        return clazz;
    }
}
