package com.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.android.module.Location;
import com.android.util.AppUtil;
import com.baidu.navi.location.BDLocation;
import com.baidu.navi.location.BDLocationListener;
import com.baidu.navi.location.LocationClient;
import com.baidu.navi.location.LocationClientOption;

public class GeoLocationService extends Service {

	private LocationClient mLocationClient;
	private Location mLocation;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mLocation = new Location();
		mLocationClient = new LocationClient(this.getApplicationContext());
		MyLocationListener mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		initLocationClient();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mLocationClient.start();
		return START_STICKY;
	}
	@Override
	public void onDestroy() {
		mLocationClient.stop();
		super.onDestroy();
	}
	private void initLocationClient() {
		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setOpenGps(true); // 打开GPS
		int span = 5000;
		option.setScanSpan(span);// 设置发起定位请求的间隔时间为5000ms
		option.setAddrType("all");
		mLocationClient.setLocOption(option);
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			mLocation.setLocType(location.getLocType());
			mLocation.setLongitude(location.getLongitude());
			mLocation.setLatitude(location.getLatitude());
			mLocation.setAddrStr(location.getAddrStr());
			AppUtil.onLocationChange(mLocation);
		}

		@Override
		public void onReceivePoi(BDLocation arg0) {
		}

	}

}
