package com.android.util;

import android.content.Context;
import android.location.LocationManager;

public class Utils {
	public static boolean isGpsOpen(Context context) {
		LocationManager alm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			return true;
		} else {
			return false;
		}
	}
}
