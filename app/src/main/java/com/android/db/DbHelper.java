package com.android.db;

import com.android.Constants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper {
	private DbOpenHelper helper;
	private static DbHelper mHelper = null;
	private DbHelper(Context context) {
		helper = new DbOpenHelper(context, Constants.DBNAME);
	}

	
	public static DbHelper getInstance(Context context) {
		if (mHelper == null) {
			synchronized (DbHelper.class) {
				if (mHelper == null)
					mHelper = new DbHelper(context);
			}
		}
		return mHelper;
	}

	private class DbOpenHelper extends SQLiteOpenHelper {

		public DbOpenHelper(Context context, String name) {
			super(context, name, null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	}
}
