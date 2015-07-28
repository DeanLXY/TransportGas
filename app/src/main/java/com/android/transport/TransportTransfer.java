package com.android.transport;


import com.android.annotation.ContentView;
import com.android.annotation.ViewId;
import com.android.module.Location;
import com.example.transportgas.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/*
 * 配送交接
 * 
 * 1.打开GSP，需要获取到用户的经纬度以确定位置，同时要获取系统时间
 * 2.交瓶，收瓶，安全告知
 * 3.交瓶：NFC扫描结果，获取到页面，同时点击确认交瓶，将该瓶的信息存储到后台数据库
 * 4.收瓶：NFC扫描结果，获取到界面，同时点击确认收瓶，将该瓶的信息存储到后台数据库
 * 5.安全告知：流程性提醒一下，要确认在交瓶和收瓶动作完成之后，点击确认，记录时间，地点。
 * 
 * */
@ContentView(R.layout.transport_transfer)
public class TransportTransfer extends IActivity {
	@ViewId(R.id.tv_location)
	private TextView tvLocation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onLocationChange(Location location) {
		super.onLocationChange(location);
		tvLocation.setText(location.getAddrStr());
	}
}
