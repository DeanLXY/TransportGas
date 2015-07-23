package com.android.transport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.annotation.ContentView;
import com.android.annotation.ViewId;
import com.example.transportgas.R;

@ContentView(R.layout.transport_main)
public class TransportMain extends IActivity implements OnClickListener {

	@ViewId(R.id.orders)
	private Button mOrders; // 配送接单
	@ViewId(R.id.navigation)
	private Button mNavigation;// 配送导航
	@ViewId(R.id.transfer)
	private Button mTransfer; // 配送交接
	@ViewId(R.id.pay)
	private Button mPay; // 确认支付
	@ViewId(R.id.track)
	private Button mTrack; // 气瓶跟踪
	@ViewId(R.id.sales)
	private Button mSales; // 促销活动
	@ViewId(R.id.service)
	private Button mService; // 服务评价
	@ViewId(R.id.analysis)
	private Button mAnalysis; // 配销分析
	@ViewId(R.id.btn_setting)
	private View mSetting; // 系统设置

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mOrders.setOnClickListener(this);
		mNavigation.setOnClickListener(this);
		mTransfer.setOnClickListener(this);
		mPay.setOnClickListener(this);
		mTrack.setOnClickListener(this);
		mSales.setOnClickListener(this);
		mService.setOnClickListener(this);
		mAnalysis.setOnClickListener(this);
		mSetting.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		moveTaskToBack(true);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.orders:
			intent = new Intent(this, TransportOrder.class);
			startActivity(intent);
			break;
		case R.id.navigation:
			intent = new Intent(this, TransportNavigation.class);
			startActivity(intent);
			break;
		case R.id.transfer:
			intent = new Intent(this, TransportTransfer.class);
			startActivity(intent);
			break;
		case R.id.pay:
			intent = new Intent(this, TransportPay.class);
			startActivity(intent);
			break;
		case R.id.track:
			intent = new Intent(this, TransportTrack.class);
			startActivity(intent);
			break;
		case R.id.sales:
			intent = new Intent(this, TransportSales.class);
			startActivity(intent);
			break;
		case R.id.service:
			intent = new Intent(this, TransportService.class);
			startActivity(intent);
			break;
		case R.id.analysis:
			intent = new Intent(this, TransportAnalysis.class);
			startActivity(intent);
			break;
		case R.id.btn_setting:
			intent = new Intent(this, TransportSettings.class);
			startActivity(intent);
			break;
		}

	}

}
