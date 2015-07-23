package com.android.transport;


import android.os.Bundle;

import com.android.annotation.ContentView;
import com.example.transportgas.R;
/*
 * 确认支付
 * 1.各种方式的支付功能由后台支持，只是返回已经支付的信息给我接收
 * 2.进入订单完善环节，完善订单的不完整支持，完善之后，点击关闭订单，订单结束。
 * 
 * */
@ContentView(R.layout.transport_pay)
public class TransportPay extends IActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

}
