package com.android.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.Constants;
import com.android.module.Information;

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

    public void saveOrUpdateInfomation(Information information) {
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from orderInfo where orderNum=? ", new String[]{information.getOrderNum()});
        if(cursor.moveToFirst()){
            //exists
        }
    }

    private class DbOpenHelper extends SQLiteOpenHelper {

        public DbOpenHelper(Context context, String name) {
            super(context, name, null, 1);
        }

        private final String ORDERTABLECREATESQL = "CREATE TABLE orderInfo IF NOT EXISTS(_id integer primary key autoincrement," +//
                "orderNum varchar(100)," +//
                "orderAddress varchar(200) NOT NULL," +//
                "orderContacts varchar(200) NOT NULL," +//
                "orderPhoneNum varchar(200) NOT NULL," +//
                "orderTime varchar(200) NOT NULL," +//
                "orderContent varchar(200) NOT NULL" +//
                ")";


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(ORDERTABLECREATESQL);
            db.execSQL("CREATE INDEX orderIndex ON orderInfo (orderNum)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
