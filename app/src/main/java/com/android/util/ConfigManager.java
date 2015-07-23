package com.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ConfigManager {
	private final String SPNAME = "transport";
	private SharedPreferences sp;

	private ConfigManager(Context context) {
		sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
	}

	public static class Config {
		// 语音播报
		public static final String KEY_SILENCE = "key_silence";
		// 听单模式
		public static final String KEY_LISTENMODEL = "key_listenmodel";
		// 事实听单范围
		public static final String KEY_LISTENRANGE = "key_listenrange";
	}

	private static ConfigManager instance = null;

	public static ConfigManager getInstance(Context context) {
		if (instance == null) {
			synchronized (ConfigManager.class) {
				if (instance == null)
					instance = new ConfigManager(context);
			}
		}
		return instance;
	}

	public void setSilence(boolean bSilence) {
		put(Config.KEY_SILENCE, bSilence);
	}

	public boolean getSilence(boolean defaultVal) {
		return getBoolean(Config.KEY_SILENCE, defaultVal);
	}

	public void setListenModel(int model) {
		put(Config.KEY_LISTENMODEL, model);
	}

	public int getListenModel(int defaultModel) {
		return getInt(Config.KEY_LISTENMODEL, defaultModel);
	}

	public void setListenRange(int range) {
		put(Config.KEY_LISTENRANGE, range);
	}

	public int getListenRange(int defalutRange) {
		return getInt(Config.KEY_LISTENRANGE, defalutRange);
	}

	public void put(String key, Object value) {
		Editor edit = sp.edit();
		if (value == null) {
			edit.remove(key);
			return;
		}
		if (value.getClass() == Integer.class) {
			edit.putInt(key, (Integer) value);
		} else if (value.getClass() == Float.class) {
			edit.putFloat(key, (Float) value);
		} else if (value.getClass() == String.class) {
			edit.putString(key, (String) value);
		} else if (value.getClass() == Long.class) {
			edit.putLong(key, (Long) value);
		} else if (value.getClass() == Boolean.class) {
			edit.putBoolean(key, (Boolean) value);
		}
		edit.commit();
	}

	public boolean getBoolean(String key, boolean defVal) {
		return sp.getBoolean(key, defVal);
	}

	public String getString(String key, String defVal) {
		return sp.getString(key, defVal);
	}

	public float getFloat(String key, float defVal) {
		return sp.getFloat(key, defVal);
	}

	public int getInt(String key, int defVal) {
		return sp.getInt(key, defVal);
	}

	public long getLong(String key, int defVal) {
		return sp.getLong(key, defVal);
	}

	public void clear() {
		Editor edit = sp.edit();
		edit.clear().commit();
	}
}
