package com.android.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.android.transport.TransportOrder;
import com.example.transportgas.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

public class ForegroundListenService extends Service {


    private static final Class[] mStartForegroundSignature = new Class[] {
        int.class, Notification.class};
    private static final Class[] mStopForegroundSignature = new Class[] {
        boolean.class};
    private NotificationManager mNM;
    private Method mStartForeground;
    private Method mStopForeground;
    private Object[] mStartForegroundArgs = new Object[2];
    private Object[] mStopForegroundArgs = new Object[1];
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mNM = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		try {
			mStartForeground = ForegroundListenService.class.getMethod("startForeground", mStartForegroundSignature);
			mStopForeground = ForegroundListenService.class.getMethod("stopForeground", mStopForegroundSignature);
		} catch (NoSuchMethodException e) {
			mStartForeground = mStopForeground = null;
		}
		/*
		 * 我们并不需要为 notification.flags 设置 FLAG_ONGOING_EVENT，因为
		 * 前台服务的 notification.flags 总是默认包含了那个标志位
		 */
        Notification notification = new Notification(R.drawable.ic_launcher, "Foreground Service Started.",
                System.currentTimeMillis());
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, TransportOrder.class), 0);
        notification.setLatestEventInfo(this, "Foreground Service","Time:"+System.currentTimeMillis(), contentIntent);

        
        /*NotificationManager manger = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

        //自定义声音   声音文件放在ram目录下，没有此目录自己创建一个
        notification.sound=Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.mm); 

        //使用系统默认声音用下面这条

        notification.defaults=Notification.DEFAULT_SOUND;
        manger.notify(1, notification);*/
        
        /*
         * 注意使用  startForeground ，id 为 0 将不会显示 notification
         */
        startForegroundCompat(1, notification);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		stopForegroundCompat(1);
	}
	
	/**
	 * 以兼容性方式开始前台服务
	 */
	private void startForegroundCompat(int id, Notification n){
		if(mStartForeground != null){
			mStartForegroundArgs[0] = id;
			mStartForegroundArgs[1] = n;
			
			try {
				mStartForeground.invoke(this, mStartForegroundArgs);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			return;
		}
		startForeground(id, n);
		mNM.notify(id, n);
	}
	
	/**
	 * 以兼容性方式停止前台服务
	 */
	private void stopForegroundCompat(int id){
		if(mStopForeground != null){
			mStopForegroundArgs[0] = Boolean.TRUE;
			
			try {
				mStopForeground.invoke(this, mStopForegroundArgs);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			return;
		}
		
		/*
		 *  在 setForeground 之前调用 cancel，因为我们有可能在取消前台服务之后
		 *  的那一瞬间被kill掉。这个时候 notification 便永远不会从通知一栏移除
		 */
		mNM.cancel(id);
		stopForeground(false);
	}

}
