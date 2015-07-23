package com.android.annotation.util;

import java.lang.reflect.Field;

import android.app.Activity;
import android.view.View;

import com.android.annotation.ViewId;

public class ViewFinderUtils {
	public static void findView(Activity activity) {
		findElements(activity, new ViewFinder(activity));
	}

	public static void findView(View view) {
		findElements(view, new ViewFinder(view));
	}

	public static void findElements(Object handler, ViewFinder finder) {
		  Class<?> handlerType = handler.getClass();
        // inject view
        Field[] fields = handlerType.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
            	ViewId viewid = field.getAnnotation(ViewId.class);
                if (viewid != null) {
                    try {
                        View view = finder.findViewById(viewid.value());
                        if (view != null) {
                            field.setAccessible(true);
                            field.set(handler, view);
                        }
                    } catch (Throwable e) {
                    }
                }  
            }
        }
	}
}
