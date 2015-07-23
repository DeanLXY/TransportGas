package com.android.transport;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.android.annotation.ContentView;
import com.android.annotation.ViewId;
import com.android.application.PortalApp;
import com.android.module.ListenModel;
import com.android.util.ConfigManager;
import com.android.widget.RangeBar;
import com.android.widget.RangeBar.OnRangeBarChangeListener;
import com.example.transportgas.R;

@ContentView(R.layout.transport_settings)
public class TransportSettings extends IActivity {
	@ViewId(R.id.rg_listen)
	private RadioGroup rgListen;
	@ViewId(R.id.rangebar1)
	private RangeBar mRangeBar;
	@ViewId(R.id.back)
	private Button mBtnBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (ListenModel.MODEL_STANDERD.ordinal() == ConfigManager.getInstance(PortalApp.getInstance()).getListenModel(
				ListenModel.MODEL_STANDERD.ordinal())) {
			rgListen.check(R.id.rb_listen_standerd);
		} else {
			rgListen.check(R.id.rb_listen_fast);
		}
		rgListen.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_listen_standerd:
					ConfigManager.getInstance(PortalApp.getInstance()).setListenModel(
							ListenModel.MODEL_STANDERD.ordinal());
					break;
				case R.id.rb_listen_fast:
					ConfigManager.getInstance(PortalApp.getInstance()).setListenModel(ListenModel.MODEL_FAST.ordinal());
					break;
				}
			}
		});
		mRangeBar.setOnRangeBarChangeListener(new OnRangeBarChangeListener() {

			@Override
			public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
				ConfigManager.getInstance(PortalApp.getInstance()).setListenRange(rightThumbIndex);
			}
		});
		mBtnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		mRangeBar.setRange(ConfigManager.getInstance(PortalApp.getInstance()).getListenRange(3));
	}

}
