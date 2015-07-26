package com.android.module;

import android.support.annotation.NonNull;

import com.android.annotation.DBField;

/**
 * @author Administrator
 */
public class Order implements Comparable<Order> {
    @DBField("open_id")
    private String openId;
    private int status; //预约状态 0信息录入中 1预约中 2已配送 3 已接收
    @DBField("order_time")
    private String orderTime;
    @DBField("end_time")
    private String endTime;
    private String name;
    private String tel;
    private String type;
    private String address;
    private String subAddress;
    private String img;
    private String message;
    private String voice;
    private String newImg;
    private String sender;
    private String num;
    private String remark;
    private String location;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubAddress() {
        return subAddress;
    }

    public void setSubAddress(String subAddress) {
        this.subAddress = subAddress;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getNewImg() {
        return newImg;
    }

    public void setNewImg(String newImg) {
        this.newImg = newImg;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public enum OrderStatus {
        STATUS_DISPATCHING(2), STATUS_DISPATCHED(3), STATUS_RESERVATION(1), STATUS_RECORDING(0);
        private int intStatus;

        OrderStatus(int intStatus) {
            this.intStatus = intStatus;
        }

        public int intValue() {
            return this.intStatus;
        }
    }

    //排序使用 距离最近在上边
    @Override
    public int compareTo(@NonNull Order another) {
        return 0;
    }
}
