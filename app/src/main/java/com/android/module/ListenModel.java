package com.android.module;

public enum ListenModel {
	MODEL_STANDERD("标准听单"), MODEL_FAST("快速看单");
	private String listen;

	ListenModel(String listen) {
		this.listen = listen;
	}

	public String StringValue() {
		return listen;
	}
}