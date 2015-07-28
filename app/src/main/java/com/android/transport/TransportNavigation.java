package com.android.transport;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.annotation.ContentView;
import com.android.annotation.ViewId;
import com.android.db.DbHelper;
import com.android.module.Location;
import com.android.module.Order;
import com.android.service.GeoLocationService;
import com.android.util.DialogUtils;
import com.android.util.GeoLocationUtil;
import com.baidu.lbsapi.auth.LBSAuthManagerListener;
import com.baidu.navisdk.BNaviEngineManager.NaviEngineInitListener;
import com.baidu.navisdk.BNaviPoint;
import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.BaiduNaviManager.OnStartNavigationListener;
import com.baidu.navisdk.comapi.routeplan.RoutePlanParams.NE_RoutePlan_Mode;
import com.example.transportgas.R;

import java.util.List;

/*
 *  配送导航
 *  1.点击“选择订单”，从抢到的多个订单中选择一个，选中之后，自动调用经纬度，
 *  2.选择开始导航进入地图的导航界面
 *  涉及功能点：
 *  百度地图导航
 * */

@ContentView(R.layout.transport_navigation)
public class TransportNavigation extends IActivity implements OnClickListener {
    @ViewId(R.id.btn_orderselect)
    private Button btnOrderselect;
    @ViewId(R.id.start_navigation)
    private Button btnNavigation;
    @ViewId(R.id.GPS_state)
    private ToggleButton mOpenGPS;
    private boolean mIsEngineInitSuccess;
    @ViewId(R.id.tv_longitude)
    private TextView tvLongitude;
    @ViewId(R.id.tv_latitude)
    private TextView tvLatitude;
    @ViewId(R.id.back)
    private Button back;
    private AlertDialog gpsDialog;
    private double longitude;
    private double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mOpenGPS.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                GeoLocationUtil.openGps(TransportNavigation.this);
            }
        });
        initBaduEngine(false);
        btnNavigation.setOnClickListener(this);
        btnOrderselect.setOnClickListener(this);
    }

    private void initBaduEngine(boolean jump2Nav) {
        BaiduNaviManager.getInstance().initEngine(this, getSdcardDir(), new BaiduNaviEngineInitListener(jump2Nav),
                new LBSAuthManagerListener() {
                    @Override
                    public void onAuthResult(int status, String msg) { // status
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        onGpsStatusChanged(GeoLocationUtil.isGpsOpen(this));
    }

    private void gpsDialogTips(boolean isOpen) {
        mOpenGPS.setChecked(GeoLocationUtil.isGpsOpen(this));
        if (!isOpen) {
            if (gpsDialog == null) {
                gpsDialog = DialogUtils.createGpsTipsDialog(this);
            }
            if (!gpsDialog.isShowing())
                gpsDialog.show();
        } else {
            if (gpsDialog != null)
                gpsDialog.dismiss();
        }
    }


    @Override
    public void onGpsStatusChanged(boolean isOpen) {
        super.onGpsStatusChanged(isOpen);
        gpsDialogTips(isOpen);
    }


    private class BaiduNaviEngineInitListener implements NaviEngineInitListener {
        boolean jump2nav;

        public BaiduNaviEngineInitListener(boolean jump2nav) {
            this.jump2nav = jump2nav;
        }

        public void engineInitSuccess() {
            mIsEngineInitSuccess = true;
            if (jump2nav)
                launchNavigator();
        }

        public void engineInitStart() {
        }

        public void engineInitFail() {
        }
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    @Override
    public void onLocationChange(Location location) {
        super.onLocationChange(location);
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        tvLongitude.setText("经度:" + longitude);
        tvLatitude.setText("纬度:" + latitude);

    }

    /**
     * 指定导航起终点启动GPS导航.起终点可为多种类型坐标系的地理坐标。 前置条件：导航引擎初始化成功
     */
    private void launchNavigator() {
        // 这里给出一个起终点示例，实际应用中可以通过POI检索、外部POI来源等方式获取起终点坐标
        BNaviPoint startPoint = new BNaviPoint(longitude, latitude, "派送位置", BNaviPoint.CoordinateType.BD09_MC);
        BNaviPoint endPoint = new BNaviPoint(longitude + 1, latitude, "我的位置", BNaviPoint.CoordinateType.BD09_MC);
        BaiduNaviManager.getInstance().launchNavigator(this, startPoint, // 起点（可指定坐标系）
                endPoint, // 终点（可指定坐标系）
                NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME, // 算路方式
                true, // 真实导航
                BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, // 在离线策略
                new OnStartNavigationListener() { // 跳转监听

                    @Override
                    public void onJumpToNavigator(Bundle configParams) {
                        Intent intent = new Intent(TransportNavigation.this, BNavigatorActivity.class);
                        intent.putExtras(configParams);
                        startActivity(intent);
                    }

                    @Override
                    public void onJumpToDownloader() {
                    }
                });
    }
    private void showOrderList() {
        List<Order> orderList = DbHelper.getInstance(this).queryOrders();
        String [] items = new String[orderList.size()];
        for (int i = 0 ;i< items.length ;i++) {
            items[i] = orderList.get(i).getOpenId();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogTheme);
        builder.setTitle("选择订单");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
    @Override
    public void onClick(View v) {
        if (v == btnNavigation) {
            if (!mIsEngineInitSuccess)
                initBaduEngine(true);
            else
                launchNavigator();
        }else if(v == btnOrderselect){
            showOrderList();
        }
    }


}
