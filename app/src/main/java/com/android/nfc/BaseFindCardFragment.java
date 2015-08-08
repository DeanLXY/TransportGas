package com.android.nfc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.transportgas.R;


import cn.pda.rfid.hf.HfReader;
import cn.pda.rfid.hf.Utils;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class BaseFindCardFragment extends Fragment implements OnClickListener {

    private Button buttonStart;
    private Button buttonClear;
    private Button buttonConnect;
    private TextView textViewVersion;
    private ListView listViewData;
    private List<Map<String, Object>> listData;
    private List<IDentity> listID;
    private HfReader reader;
    boolean running = true;
    boolean startRead = false;
    private byte[] uid;

    private FindCardThread findThread;

    private Context context;

    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        context = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.basefindcard_fragment, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View view) {

        buttonConnect = (Button) view.findViewById(R.id.button_connect);
        buttonStart = (Button) view.findViewById(R.id.button_scan);
        buttonClear = (Button) view.findViewById(R.id.button_clear);
        textViewVersion = (TextView) view.findViewById(R.id.textView_version);
        listViewData = (ListView) view.findViewById(R.id.listView_data);
        buttonConnect.setOnClickListener(this);
        buttonStart.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
//		setButtonTag(buttonStart, false);
        listID = new ArrayList<IDentity>();


    }

    @Override
    public void onResume() {
        reader = HfReader.getInstance();
        if (reader == null) {
            Toast.makeText(getActivity(), "模块初始化失败", Toast.LENGTH_SHORT).show();
            return;
        }

        running = true;
        findThread = new FindCardThread();
        findThread.start();
        PlayTips.initSoundPool(context);
        super.onResume();
    }

    private void setButtonTag(Button button, boolean flag) {
        if (flag) {
            button.setClickable(flag);
            button.setTextColor(getResources().getColor(android.R.color.black));
        } else {
            button.setClickable(false);
            button.setTextColor(getResources().getColor(android.R.color.darker_gray));
        }
    }

    private void clear() {
        listID.removeAll(listID);
        listViewData.setAdapter(null);
    }

    class FindCardThread extends Thread {
        @Override
        public void run() {
            Log.e("uid", "running ....");
            while (running) {

                while (startRead) {
                    //14443A
                    uid = reader.find14443aCardUid();
                    listData = new ArrayList<Map<String, Object>>();
                    if (uid != null) {
                        Log.e("uid", Utils.Bytes2HexString(uid, uid.length));
                        reflashListView("ISO14443A");
                        PlayTips.play(1, 0);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    //15693
                    uid = reader.find15693CardUid();
                    listData = new ArrayList<Map<String, Object>>();
                    if (uid != null) {
                        Log.e("uid", Utils.Bytes2HexString(uid, uid.length));
                        reflashListView("ISO15693");
                        PlayTips.play(1, 0);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    uid = reader.findIDcard();
                    listData = new ArrayList<Map<String, Object>>();
                    if (uid != null) {
                        Log.e("uid", Utils.Bytes2HexString(uid, uid.length));
                        reflashListView("基 本 寻 卡");
                        PlayTips.play(1, 0);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
            super.run();
        }
    }

    //ˢ�½���
    private void reflashListView(final String cardType) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                IDentity entity = new IDentity();
                if (uid == null) return;
                entity.setUid(Utils.Bytes2HexString(uid, uid.length));
                entity.setIdtype(cardType);
                listID = sortAddIDentity(listID, entity);
                int idXuhao = 1;
                for (IDentity identity : listID) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("id", idXuhao);
                    map.put("idtype", identity.getIdtype());
                    map.put("uid", identity.getUid());
                    map.put("idcount", identity.getCount());
                    idXuhao++;
                    listData.add(map);
                }
                listViewData.setAdapter(new SimpleAdapter(context, listData,
                        R.layout.listview_item,
                        new String[]{"id", "idtype", "uid", "idcount"},
                        new int[]{R.id.textView_id, R.id.textView_cardtype, R.id.textView_uid, R.id.textView_count}));


            }
        });
    }


    public List<IDentity> sortAddIDentity(List<IDentity> list, IDentity identity) {
        int temp = 1;
        //��һ��д��IDentity
        if (list == null || list.size() == 0 || list.isEmpty()) {
            identity.setCount(temp);
            list.add(identity);
            return list;
        } else {
            //ԭ�б��������
            for (int i = 0; i < list.size(); i++) {
                //
                if (identity.getUid().equals(list.get(i).getUid())) {
                    temp = list.get(i).getCount() + temp;
                    identity.setCount(temp);
                    for (int j = i; j > 0; j--) {
                        list.set(j, list.get(j - 1));  //�ƶ����
                    }
                    list.set(0, identity);
                    return list;
                }
            }
            IDentity lastIDentity = list.get(list.size() - 1);
            for (int j = list.size() - 1; j >= 0; j--) {
                if (j == 0) {
                    identity.setCount(temp);
                    list.set(j, identity);
                } else {
                    list.set(j, list.get(j - 1));
                }

            }
            list.add(lastIDentity);
        }
        return list;
    }

    @Override
    public void onPause() {
        Log.e("", "onPause");
        startRead = false;
        running = false;
        if (reader != null) {
            reader.close();
        }

        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e("", "onStop");
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_connect:
                byte[] version = reader.getVersion();
                if (version != null) {
                    setButtonTag(buttonStart, true);
                    setButtonTag(buttonConnect, false);
                }
                break;
            case R.id.button_scan:
                if (startRead) {
                    startRead = false;
                    buttonStart.setText("��ʼɨ��");
                } else {
                    startRead = true;
                    buttonStart.setText("ֹͣɨ��");
                }
                break;
            case R.id.button_clear:
                clear();
                break;

            default:
                break;
        }


    }
}
