package com.android.module;

import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * @author Administrator
 */
public class Information implements Comparable<Information> {
    private String orderNum;
    private String orderAddress;
    private String orderContacts;
    private String orderPhoneNum;
    private String orderTime;
    private String orderContent;
    private int orderStatus = OrderStatus.STATUS_DONOTHING.intValue();


    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getOrderContacts() {
        return orderContacts;
    }

    public void setOrderContacts(String orderContacts) {
        this.orderContacts = orderContacts;
    }

    public String getOrderPhoneNum() {
        return orderPhoneNum;
    }

    public void setOrderPhoneNum(String orderPhoneNum) {
        this.orderPhoneNum = orderPhoneNum;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(String orderContent) {
        this.orderContent = orderContent;
    }

    @Override
    public String toString() {
        return "Information{" +
                "orderNum='" + orderNum + '\'' +
                ", orderAddress='" + orderAddress + '\'' +
                ", orderContacts='" + orderContacts + '\'' +
                ", orderPhoneNum='" + orderPhoneNum + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", orderContent='" + orderContent + '\'' +
                '}';
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put("orderNum", orderNum);
        values.put("orderAddress", orderAddress);
        values.put("orderContacts", orderContacts);
        values.put("orderPhoneNum", orderPhoneNum);
        values.put("orderNum", orderNum);
        values.put("orderTime", orderTime);
        values.put("orderContent", orderContent);
        values.put("orderStatus", orderStatus);
        return values;
    }

    public enum OrderStatus {
        STATUS_DISPATCHING(1), STATUS_DISPATCHED(2), STATUS_DONOTHING(0);
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
    public int compareTo(@NonNull Information another) {
        return 0;
    }
}
