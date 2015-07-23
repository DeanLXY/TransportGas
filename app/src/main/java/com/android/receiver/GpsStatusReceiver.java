package com.android.receiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import com.android.util.AppUtil;

public class GpsStatusReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent)
		{
			System.out.print("");
			LocationManager lm = (LocationManager) context.getSystemService(Service.LOCATION_SERVICE);
			boolean isEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
			AppUtil.onGpsStatusChanged(isEnabled);
		}
	}