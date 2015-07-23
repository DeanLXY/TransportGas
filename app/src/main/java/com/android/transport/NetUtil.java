package com.android.transport;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;


public class NetUtil {
	

	public static String PROXY_IP = "";

	public static int PROXY_PORT = 0;
	private static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");// 4.0模拟器屏蔽掉该权�?


	public static boolean checkNet(Context context) {
		// WIFI处于连接
		boolean isWIFI = isWIFIConnectivity(context);
		// Mobile(APN列表)连接
		boolean isMobile = isMobileConnectivity(context);

		if (isWIFI == false && isMobile == false) {
			// 没有网络
			return false;
		}

		if (isMobile) {
			// 是否是wap方式,将代理的ip和端口写死，010.000.000.172
			readAPN(context);
		}

		return true;

	}

	/**
	 * 读取代理的ip和端�?
	 * 
	 * @param context
	 */
	private static void readAPN(Context context) {
		ContentResolver contentResolver = context.getContentResolver();
		Cursor query = contentResolver.query(PREFERRED_APN_URI, null, null, null, null);
		if (query != null && query.moveToFirst()) {
			PROXY_IP = query.getString(query.getColumnIndex("proxy"));
			PROXY_PORT = query.getInt(query.getColumnIndex("port"));
		}

	}

	/**
	 * Mobile(APN列表)连接
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isMobileConnectivity(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}

	/**
	 * WIFI是否处于连接
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isWIFIConnectivity(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}
}
