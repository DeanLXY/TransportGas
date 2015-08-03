package com.android.transport;

import com.android.annotation.ContentView;
import com.android.annotation.util.ViewFinderUtils;
import com.android.module.Location;
import com.android.module.Order;
import com.android.util.AppUtil;

import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

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
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case android.R.id.home:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}

	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		AppUtil.remove(this);
	}

	protected int getLayoutResourceId() {
		return LAYOUTID_UNAVAILABLE;
	}

	public void onLocationChange(Location location) {
	}

	public void onWebSocketDataChange(Order info) {
	}

	public void onGpsStatusChanged(boolean isOpen) {
	}
}
