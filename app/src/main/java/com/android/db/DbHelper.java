package com.android.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.Constants;
import com.android.module.Order;
import com.android.util.CursorUtils;
import com.android.util.LogUtils;

import java.util.List;

public class DbHelper {
    private DbOpenHelper helper;
    private static DbHelper mHelper = null;
    private SQLiteDatabase database;

    private DbHelper(Context context) {
        helper = new DbOpenHelper(context, Constants.DBNAME);
    }


    public static DbHelper getInstance(Context context) {
        LogUtils.e("context = %s",""+context);
        if (mHelper == null) {
            synchronized (DbHelper.class) {
                if (mHelper == null)
                    mHelper = new DbHelper(context);
            }
        }
        return mHelper;
    }

    //------------------------------------------------------------------
    public long saveOrderInfo(Order order) {
        long result = -1;
        try {
            String openId = order.getOpenId();
            boolean bExists;
            Cursor query = query(DbOpenHelper.TABLEORDER, null, "open_id=?", new String[]{openId}, null, null, null);
            bExists = query.moveToFirst();
            CursorUtils.closeCursor(query);
            if (bExists) {
                LogUtils.d("%s", "order_table openId exists. May update status  ");
            } else {
                result = insert(DbOpenHelper.TABLEORDER, null, order.toContentValues());
            }
        } catch (Exception e) {
            LogUtils.e("%s",e.getMessage());
        }
        return result;
    }
    public List<Order> queryOrders() {
        Cursor query = query(DbOpenHelper.TABLEORDER, null, null, null, null, null, null);
        List<Order> dbOrders = CursorUtils.getBeanListFromCursor(Order.class, query);
        CursorUtils.closeCursor(query);
        return dbOrders;
    }


    // ---------------------------------------------------------------------
    public synchronized void execSQL(String sql) {
        SQLiteDatabase db = getDatabase();
        db.execSQL(sql);
    }

    public synchronized void execSQL(String sql, Object[] bindArgs) {
        SQLiteDatabase db = getDatabase();
        db.execSQL(sql, bindArgs);
    }

    /**
     * Convenience method for inserting a row into the database.
     *
     * @param table          the table to insert the row into
     * @param nullColumnHack SQL doesn't allow inserting a completely empty row, so if initialValues is empty this column will explicitly be assigned a NULL
     *                       value
     * @param values         this map contains the initial column values for the row. The keys should be the column names and the values the column values
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public synchronized long insert(String table, String nullColumnHack, ContentValues values) {
        SQLiteDatabase db = getDatabase();
        long result = db.insert(table, nullColumnHack, values);
        return result;
    }

    /**
     * Convenience method for updating rows in the database
     *
     * @param table       the table to update in
     * @param values      a map from column names to new column values. null is a valid value that will be translated to NULL.
     * @param whereClause the optional WHERE clause to apply when updating. Passing null will update all rows.
     * @param whereArgs
     * @return the number of rows affected
     */
    public synchronized long update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getDatabase();
        long result = db.update(table, values, whereClause, whereArgs);
        return result;
    }

    /**
     * Convenience method for deleting rows in the database.
     *
     * @param table       the table to delete from
     * @param whereClause the optional WHERE clause to apply when deleting. Passing null will delete all rows.
     * @param whereArgs
     * @return the number of rows affected if a whereClause is passed in, 0 otherwise. To remove all rows and get a count pass "1" as the whereClause.
     */
    public synchronized long delete(String table, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getDatabase();
        long result = db.delete(table, whereClause, whereArgs);
        return result;
    }

    /**
     * Query the given table, returning a Cursor over the result set.
     *
     * @param table         The table name to compile the query against.
     * @param columns       A list of which columns to return. Passing null will return all columns, which is discouraged to prevent reading data from storage
     *                      that isn't going to be used.
     * @param selection     A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all
     *                      rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the
     *                      selection. The values will be bound as Strings.
     * @param groupBy       A filter declaring how to group rows, formatted as an SQL GROUP BY clause (excluding the GROUP BY itself). Passing null will cause
     *                      the rows to not be grouped.
     * @param having        A filter declare which row groups to include in the cursor, if row grouping is being used, formatted as an SQL HAVING clause
     *                      (excluding the HAVING itself). Passing null will cause all row groups to be included, and is required when row grouping is not being
     *                      used.
     * @param orderBy       How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *                      order, which may be unordered.
     * @return A Cursor object, which is positioned before the first entry. Note that Cursors are not synchronized, see the documentation for more
     * details.
     */
    public synchronized Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
                                     String orderBy) {
        SQLiteDatabase db = getDatabase();
        Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
        return cursor;
    }

    public synchronized SQLiteDatabase getDatabase() {
        if (database == null || !database.isOpen()) {
            LogUtils.e("%s","before database = "+database);
            try {
                database = helper.getWritableDatabase();
            }catch (Exception e){
                LogUtils.e("%s","after database = "+database+">>>e"+e.getMessage());
            }
        }
        return database;
    }

    public void dissconnect() {
        if (database != null) {
            database.close();
        }
        if (helper != null) {
            helper.close();
        }
        helper = null;
        mHelper = null;
    }



    private class DbOpenHelper extends SQLiteOpenHelper {
        public static final String TABLEMESSAGE = "message";
        public static final String TABLEORDER = "orderInfo";

        public DbOpenHelper(Context context, String name) {
            super(context, name, null, 1);
        }

        private final String CREATETABLEMESSAGE = "CREATE TABLE " + TABLEMESSAGE + "(_id integer primary key autoincrement," +//
                "open_id varchar(100)," +//
                "create_time varchar(200)," +//
                "msg_type varchar(200)," +//
                "msg_id varchar(200)," +//
                "content varchar(200)," +//
                "lng varchar(200)," +//
                "lat varchar(20)," +//
                "label varchar(20)," +//
                "scale varchar(20)," +//
                "pic_url varchar(20)," +//
                "title varchar(200)," +//
                "description varchar(200)," +//
                "url varchar(200)," +//
                "media_id varchar(200)," +//
                "thumb_media_id varchar(200)," +//
                "format varchar(200)," +//
                "recognition varchar(200)" +//
                ")";
        private final String CREATETABLEORDER = "CREATE TABLE " + TABLEORDER + "(_id integer primary key autoincrement," +//
                "open_id varchar(100)," +//
                "status varchar(100)," +//
                "order_time varchar(100)," +//
                "end_time varchar(100)," +//
                "name varchar(100)," +//
                "tel varchar(100)," +//
                "type varchar(100)," +//
                "address varchar(100)," +//
                "sub_address varchar(100)," +//
                "img varchar(100)," +//
                "message varchar(200)," +//
                "voice varchar(100)," +//
                "new_img varchar(100)," +//
                "sender varchar(100)," +//
                "num varchar(100)," +//
                "remark varchar(200)," +//
                "location varchar(100)" +//
                ")";


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATETABLEMESSAGE);
            db.execSQL(CREATETABLEORDER);
            db.execSQL("CREATE INDEX messageIndex ON message (open_id)");
            db.execSQL("CREATE INDEX orderIndex ON orderInfo (open_id)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
