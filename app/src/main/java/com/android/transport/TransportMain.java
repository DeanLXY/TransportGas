package com.android.transport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.annotation.ContentView;
import com.android.annotation.ViewId;
import com.android.service.GeoLocationService;
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
        startService(new Intent(this, GeoLocationService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, GeoLocationService.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v == mOrders) {
            intent = new Intent(this, TransportOrder.class);
            startActivity(intent);
        } else if (v == mNavigation) {
            intent = new Intent(this, TransportNavigation.class);
            startActivity(intent);
        } else if (v == mTransfer) {
            intent = new Intent(this, TransportTransfer.class);
            startActivity(intent);
        } else if (v == mPay) {
            intent = new Intent(this, TransportPay.class);
            startActivity(intent);
        } else if (v == mTrack) {
            intent = new Intent(this, TransportTrack.class);
            startActivity(intent);
        } else if (v == mSales) {
            intent = new Intent(this, TransportSales.class);
            startActivity(intent);
        } else if (v == mService) {
            intent = new Intent(this, TransportService.class);
            startActivity(intent);
        } else if (v == mAnalysis) {
            intent = new Intent(this, TransportAnalysis.class);
            startActivity(intent);
        } else if (v == mSetting) {
            showSettingOptions(v);
        }
    }


    private void showSettingOptions(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenu().add(Menu.NONE, 0, Menu.NONE, "设备注册");
        popup.getMenu().add(Menu.NONE, 1, Menu.NONE, "系统设置");
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == 0) {
                    Intent intent = new Intent(TransportMain.this, TransportRegister.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(TransportMain.this, TransportSettings.class);
                    startActivity(intent);
                }
                return true;
            }
        });
        popup.show(); //showing popup menu
    }

}
