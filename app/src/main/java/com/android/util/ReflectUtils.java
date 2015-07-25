package com.android.util;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashSet;

public class ReflectUtils {
    public static Field[] getDeclaredFields(Class<?> clazz) {
        HashSet<Field> fieldSet = new HashSet<Field>();
        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            for (Field field : c.getDeclaredFields()) {
                fieldSet.add(field);
            }
        }
        return fieldSet.toArray(new Field[fieldSet.size()]);
    }

    /**
     * Orders fields by their name and declaring class.
     *
     * @hide
     */
    public static final Comparator<Field> ORDER_BY_NAME_AND_DECLARING_CLASS = new Comparator<Field>() {
        @Override
        public int compare(Field a, Field b) {
            int comparison = a.getName().compareTo(b.getName());
            if (comparison != 0) {
                return comparison;
            }

            return a.getDeclaringClass().getName().compareTo(b.getDeclaringClass().getName());
        }
    };
}