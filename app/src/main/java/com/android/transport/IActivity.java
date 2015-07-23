package com.android.transport;

import com.android.annotation.ContentView;
import com.android.annotation.util.ViewFinderUtils;
import com.android.module.Information;
import com.android.util.AppUtil;

import android.app.Activity;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class IActivity extends AppCompatActivity {
	public final int LAYOUTID_UNAVAILABLE = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getLayoutResourceId() == LAYOUTID_UNAVAILABLE){
			ContentView contentView = getClass().getAnnotation(ContentView.class);
			if(contentView != null){
				setContentView(contentView.value());
			}
		}else{
			setContentView(getLayoutResourceId());
		}
		ViewFinderUtils.findView(this); 
		AppUtil.add(this);
		 new IntentFilter(LocationManager.	PROVIDERS_CHANGED_ACTION) ;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		AppUtil.remove(this);
	}

	protected int getLayoutResourceId() {
		return LAYOUTID_UNAVAILABLE;
	}

	public void onLocationChange(double longitude, double latitude) {
	}

	public void onWebSocketDataChange(Information info) {
		
	}

	public void onGpsStatusChanged(boolean isOpen) {
	}
}
