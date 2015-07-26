package com.android.util;

public class MathUtils {
	public static int parseInt(String intStr) {
		int result = 0;
		try {
			result = Integer.parseInt(intStr);
		} catch (Exception e) {
			result = 0;
		}
		return result;
	}

	public static float parseFloat(String floatStr) {
		float result = 0;
		try {
			result = Float.parseFloat(floatStr);
		} catch (Exception e) {
			result = 0;
		}
		return result;
	}

	public static long parseLong(String intStr) {
		long result = 0;
		try {
			result = Long.parseLong(intStr);
		} catch (Exception e) {
			result = 0;
		}
		return result;
	}

	public static boolean parseBoolean(String bolintStr) {
		boolean result = false;
		try {
			result = Boolean.parseBoolean(bolintStr);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	public static int parseInt(String intStr, int defaultValue) {
		int result = defaultValue;
		try {
			result = Integer.parseInt(intStr);
		} catch (Exception e) {
			result = defaultValue;
		}
		return result;
	}
}
