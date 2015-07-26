package com.android.application;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.SystemClock;


public class PortalApp extends Application {

    private static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//		loopCoreSs();
    }

    public static Context getInstance() {

        return instance;
    }

//	private void loopCoreSs() {
//		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
//		int requestCode = 0;
//		Intent ssIntent = new Intent(this, CoreService.class);
//		PendingIntent pendIntent = PendingIntent.getService(getApplicationContext(), requestCode, ssIntent,
//				PendingIntent.FLAG_UPDATE_CURRENT);
//		int triggerAtTime = (int) (SystemClock.elapsedRealtime() + 5 * 1000);
//		int interval = 10 * 1000;
//		am.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerAtTime, interval, pendIntent);
//	}

//	@Override
//	public void onConfigurationChanged(Configuration newConfig) {
//		super.onConfigurationChanged(newConfig);
//		int requestCode = 0;
//		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
//		Intent ssIntent = new Intent(this, CoreService.class);
//		PendingIntent pendIntent = PendingIntent.getService(getApplicationContext(), requestCode, ssIntent,
//				PendingIntent.FLAG_UPDATE_CURRENT);
//		am.cancel(pendIntent);
//
//	}

}
