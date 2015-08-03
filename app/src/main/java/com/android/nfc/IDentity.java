package com.android.nfc;

public class IDentity {
	private String idtype;
	private String uid;
	private int count;
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * @return the idtype
	 */
	public String getIdtype() {
		return idtype;
	}
	/**
	 * @param idtype the idtype to set
	 */
	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IDentity [idtype=" + idtype + ", uid=" + uid + ", count="
				+ count + "]";
	}
	
}
