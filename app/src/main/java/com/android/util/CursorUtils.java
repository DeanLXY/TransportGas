package com.android.util;

import android.database.Cursor;

import com.android.annotation.DBField;
import com.android.annotation.DBName;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by wxj on 2015-7-25.
 */
public class CursorUtils {
//    public static void cursorCover2BeanList(Cursor cursor, Class clazz) {
//        if (cursor == null) {
//            throw new UnsupportedOperationException("cursor must not null");
//        }
//        Field[] declaredFileds = clazz.getDeclaredFields();
//        boolean canmove = cursor.moveToFirst();
//        Object object;
//        if (canmove) {
//            try {
//                object = clazz.newInstance();
//            } catch (Exception e) {
//                throw new UnsupportedOperationException("javabean must has an empty constructor");
//            }
//        }
//
//        while (cursor.moveToNext()) {
//
//        }
//
//    }

    public static <T> T getBeanFromCursor(Class<T> clazz, Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        Field[] fields = getFilterField(clazz);
        T t = null;
        if (cursor.moveToNext()) {
            try {
                t = clazz.newInstance();
            } catch (Exception e1) {
                e1.printStackTrace();
                return null;
            }
            t = getBeanFromCurrentCursor(cursor, fields, t);
        }
        return t;
    }

    public static <T> T getBeanFromCurrentCursor(Class<T> clazz, Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        Field[] fields = getFilterField(clazz);
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }
        t = getBeanFromCurrentCursor(cursor, fields, t);
        return t;
    }

    public static <T> List<T> getBeanListFromCursor(Class<T> clazz, Cursor cursor) {
        List<T> result = new ArrayList<T>();
        if (cursor == null) {
            return result;
        }
        Field[] fields = getFilterField(clazz);
        while (cursor.moveToNext()) {
            T t = null;
            try {
                t = clazz.newInstance();
            } catch (Exception e1) {
                e1.printStackTrace();
                continue;
            }

            t = getBeanFromCurrentCursor(cursor, fields, t);
            result.add(t);
        }
        return result;
    }

    public static <T> T getBeanFromCurrentCursor(Cursor cursor, Field[] fields, T t) {
        Object value = null;
        String name = null;
        for (Field field : fields) {
            DBField dbField = field.getAnnotation(DBField
                    .class);
            if (dbField == null) {
                continue;
            }
            name = dbField.value();
            int columnIndex = cursor.getColumnIndex(name);
            if (columnIndex == -1) {
                continue;
            }
            Class<?> type = field.getType();
            if (type == String.class) {
                value = cursor.getString(columnIndex);
            } else if (type == Integer.class) {
                value = cursor.getInt(columnIndex);
            } else if (type == Long.class) {
                value = cursor.getLong(columnIndex);
            } else if (type == Float.class) {
                value = cursor.getFloat(columnIndex);
            }
            field.setAccessible(true);
            try {
                field.set(t, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return t;
    }


    public static Field[] getFilterField(Class<?> clazz) {
        HashSet<Field> fieldSet = new HashSet<Field>();
        Field[] fields = ReflectUtils.getDeclaredFields(clazz);
        for (Field field : fields) {
            DBField dbField = field.getAnnotation(DBField.class);
            if (dbField != null) {
                fieldSet.add(field);
            }
        }
        return fieldSet.toArray(new Field[fieldSet.size()]);
    }
    public static void closeCursor(Cursor cursor) {
        if (cursor != null)
            if (!cursor.isClosed())
                cursor.close();
    }
}
