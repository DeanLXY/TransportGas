package com.android.module;

/**
 * @author Administrator
 *
 */
public class Information implements Comparable<Information>{
    private int id;
    private String address;
    private String order;
    private String time;
    private String consumer;
    private String telephony;
    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getConsumer() {
		return consumer;
	}

	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}

	public String getTelephony() {
		return telephony;
	}

	public void setTelephony(String telephony) {
		this.telephony = telephony;
	}

	public Information() {
	}

    public Information(int id, String address, String order, String time, String consumer, String telephony){
    	this.id = id;
    	this.address = address;
    	this.order = order;
    	this.time = time;
    	this.consumer = consumer;
    	this.telephony = telephony;
    }
    
	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Information other = (Information) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Information [id=" + id + ", address=" + address + ", order=" + order + ", time=" + time + ", consumer="
				+ consumer + ", telephony=" + telephony + "]";
	}

	//排序使用 距离最近在上边
	@Override
	public int compareTo(Information another) {
		return 0;
	}
}
