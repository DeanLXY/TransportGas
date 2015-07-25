package com.android.service;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.android.module.Information;
import com.android.util.AppUtil;
import com.android.util.LogUtils;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketConnectionHandler;
import de.tavendo.autobahn.WebSocketException;

public class CoreService extends Service {

	private final WebSocketConnection mConnection = new WebSocketConnection();
	private List<Information> webData = new ArrayList<Information>();
	private WeakHandler mHandler;

	private class WeakHandler extends Handler {
		private WeakReference<CoreService> ref;

		public WeakHandler(CoreService t) {
			ref = new WeakReference<CoreService>(t);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			CoreService service = ref.get();
			if (service != null) {
				service.getWebSocketData();
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mHandler = new WeakHandler(this);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mHandler.sendEmptyMessage(100);
		return START_STICKY;
	}

	public List<Information> getWebData() {
		return webData;
	}

	public void getWebSocketData() {
		final String wsuri = "ws://cloudservice.qpsafe.cn:9090/sys/message22";
		try {
			mConnection.connect(wsuri, new WebSocketConnectionHandler() {
				@Override
				public void onOpen() {
					LogUtils.d("%s", "Status: service :Connected to ");
					mConnection.sendTextMessage("mm" + "!@#$%" + "我要接单");
					AppUtil.onWebSocketDataChange(new Information());
				}

				@Override
				public void onTextMessage(String payload) {
					AppUtil.onWebSocketDataChange(new Information());
					LogUtils.d("%s", "Got echo: " + payload);
					Information i = null;
					if (payload != null) {
						i = new Information();
						int id = 1;
//						i.setId(id++);
//						i.setAddress("北京市海淀区西北旺路东馨园小区6号楼3单元501");
//						i.setOrder(payload);
//						i.setTime("2015-5-5");
//						i.setConsumer("张女士");
//						i.setTelephony("16709338922");
						webData.add(i);
					}
				}

				@Override
				public void onClose(int code, String reason) {
					LogUtils.d("%s", "Connection lost-service.");
				}

			});
		} catch (WebSocketException e) {

		}
	}

}
