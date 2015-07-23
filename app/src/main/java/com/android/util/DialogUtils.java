package com.android.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.transportgas.R;

/**
 * Created by Administrator on 2015-7-23.
 */
public class DialogUtils {
    public static AlertDialog createGpsTipsDialog(final Activity activity) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity, R.style.AlertDialogTheme
        );
        builder.setTitle("提示");
        builder.setMessage("GPS未开启，是否马上设置？");
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finish();
            }
        });
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GeoLocationUtil.openGps(activity);
            }
        });
        builder.setCancelable(false);
        return builder.create();
    }
}
