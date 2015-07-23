package com.android.transport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.adapter.TransportOrderAdapter;
import com.android.annotation.ContentView;
import com.android.annotation.ViewId;
import com.android.application.PortalApp;
import com.android.module.Information;
import com.android.util.ConfigManager;
import com.android.util.DialogUtils;
import com.android.util.GeoLocationUtil;
import com.example.transportgas.R;

//配送接单

/*1.抢单功能：30s倒计时，在搜索出订单的时候，进行抢单，过时不抢单，自动移除该单，播放下一条
 2.GPS的开启和语音功能的开启
 3.在搜索出多个订单的时候，用户也可以不等播放完毕，批量接单
 涉及功能点：
 1.向上滚动刷新的ListView列表功能
 2.抢单成功后，返回单子的状态为配送中。
 *
 */
@ContentView(R.layout.transport_orders)
public class TransportOrder extends IActivity implements OnClickListener {
    @ViewId(R.id.tv_distance)
    private TextView mDistance;
    @ViewId(R.id.open_gps)
    private ToggleButton mOpenGPS;
    @ViewId(R.id.voice_broadcast)
    private ToggleButton mVoiceBroadCast;
    @ViewId(R.id.grab_list)
    private Button mGrabList;
    @ViewId(R.id.reFresh)
    private Button mRefresh;
    @ViewId(R.id.listView)
    private ListView mListView;
    private TransportOrderAdapter adapter;

    private ArrayList<Integer> list; // listView的集合数据
    private List<Information> mInfomationList = new ArrayList<Information>();
    private AlertDialog gpsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVoiceBroadCast.setChecked(ConfigManager.getInstance(this).getSilence(false));
        mOpenGPS.setOnClickListener(this);
        mVoiceBroadCast.setOnClickListener(this);
        mGrabList.setOnClickListener(this);
        mRefresh.setOnClickListener(this);
        list = new ArrayList<Integer>();
        adapter = new TransportOrderAdapter(this);
        adapter.setData(mInfomationList);
        mListView.setAdapter(adapter);
        mDistance.setText(String.format("距离您%d公里内", ConfigManager.getInstance(this).getListenRange(3)));
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
    protected void onResume() {
        super.onResume();
        onGpsStatusChanged(GeoLocationUtil.isGpsOpen(this));
    }
    @Override
    public void onGpsStatusChanged(boolean isOpen) {
        super.onGpsStatusChanged(isOpen);
        gpsDialogTips(isOpen);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_gps:
                openGPS(mOpenGPS.isChecked());
                break;
            case R.id.voice_broadcast:
                voiceBroadcast(mVoiceBroadCast.isChecked());
                break;
            case R.id.grab_list:
                grabList();
                break;
            case R.id.reFresh:
                // dataChanged();
                break;
            default:
                break;
        }

    }

    private void openGPS(boolean isCheck) {
        GeoLocationUtil.openGps(this);
    }

    private void voiceBroadcast(boolean isCheck) {
        ConfigManager.getInstance(this).setSilence(isCheck);
    }

    @Override
    public void onWebSocketDataChange(Information info) {
        super.onWebSocketDataChange(info);
        mInfomationList.add(info);
        Collections.sort(mInfomationList);
        adapter.setData(mInfomationList);
    }

    private void grabList() {
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), alert);
        r.play();
    }

    abstract class OrderTask<Params> extends AsyncTask<Params, Void, List<Information>> {
        public final AsyncTask<Params, Void, List<Information>> excuteProxy(Params... params) {
            if (NetUtil.checkNet(PortalApp.getInstance())) {
                return execute(params);
            } else {
                Toast.makeText(PortalApp.getInstance(), "meiwang", Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    }
}
