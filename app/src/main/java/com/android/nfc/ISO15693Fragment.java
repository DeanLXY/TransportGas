package com.android.nfc;

import com.example.transportgas.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.pda.rfid.hf.HfReader;
import cn.pda.rfid.hf.Utils;

public class ISO15693Fragment extends Fragment implements OnClickListener{

	private View rootView;
	
	private EditText editUid;
	private EditText editStart;
	private EditText editRead;
	private EditText editBlocks;
	private EditText editWrite;
	
	private Button buttonFind;
	private Button buttonSelect;
	private Button buttonRead;
	private Button buttonWrite;
	
	private HfReader reader ;
	private byte[] uid ;
	private boolean selectFlag = false ;
	
	private int startAddr = 0 ;
	private int blocks = 1;
	private byte[] dataWriteBytes = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.iso15693_fragment, container, false);
		init(rootView);
		return rootView;
	}
	
	private void init(View view){
		
		editUid = (EditText) view.findViewById(R.id.editTextUid15693);
		editStart = (EditText) view.findViewById(R.id.editTextStartArr);
		editRead = (EditText) view.findViewById(R.id.editTextRead15693);
		editWrite = (EditText) view.findViewById(R.id.editTextWrite15693);
		editBlocks = (EditText) view.findViewById(R.id.editTextBlocks);
		
		buttonFind = (Button) view.findViewById(R.id.buttonFind15693);
		buttonSelect = (Button) view.findViewById(R.id.buttonSelect15693);
		buttonRead = (Button) view.findViewById(R.id.buttonRead15693);
		buttonWrite = (Button) view.findViewById(R.id.buttonWrite15693);
		
		buttonFind.setOnClickListener(this);
		buttonSelect.setOnClickListener(this);
		buttonRead.setOnClickListener(this);
		buttonWrite.setOnClickListener(this);
		
		PlayTips.initSoundPool(getActivity());
		
	}
	
	@Override
	public void onResume() {
		reader = HfReader.getInstance();
		super.onResume();
	}
	
	@Override
	public void onPause() {
		if(reader != null){
			reader.close();
		}
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonFind15693:
			uid = reader.find15693CardUid();
			if(uid != null){
				editUid.setText(Utils.Bytes2HexString(uid, uid.length));
				PlayTips.play(1, 0);
			}else{
				Toast.makeText(getActivity(), "δѰ����Ƭ��������", Toast.LENGTH_SHORT).show();
			}
			
			break;
		case R.id.buttonSelect15693:
			if(uid != null){
				selectFlag = reader.select15693Card(uid);
				if(selectFlag){
					Toast.makeText(getActivity(), "ѡ���ɹ�", Toast.LENGTH_SHORT).show();
					PlayTips.play(1, 0);
				}else{
					Toast.makeText(getActivity(), "ѡ��Ƭʧ�ܣ�������", Toast.LENGTH_SHORT).show();
				}
				
			}else{
				Toast.makeText(getActivity(), "����Ѱ������ѡ��", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.buttonRead15693:
			/**
			 * ģ��ֻ�ܶ�����һ��������
			 */
			if(selectFlag){
				if(checkParameter()){
					byte[] dataRead = reader.read15693(0, 1);
					if(dataRead != null){
						editRead.setText(Utils.Bytes2HexString(dataRead, dataRead.length));
						PlayTips.play(1, 0);
					}else{
						Toast.makeText(getActivity(), "�����ʧ��", Toast.LENGTH_SHORT).show();
					}
				}
			}
			break;
		case R.id.buttonWrite15693:
			if(selectFlag){
				if(checkWriteParameter()){
					boolean writeFlag = reader.write15693(startAddr, dataWriteBytes);
					if(writeFlag){
						Toast.makeText(getActivity(), "д��ݳɹ�", Toast.LENGTH_SHORT).show();
						PlayTips.play(1, 0);
					}else{
						Toast.makeText(getActivity(), "д���ʧ��", Toast.LENGTH_SHORT).show();
					}
				}
			}
			break;

		default:
			break;
		}
	}
	
	
	private boolean checkParameter(){
		String startAddrStr = editStart.getText().toString();
		String blocksStr = editBlocks.getText().toString();
		if(startAddrStr != null){
			startAddr = Integer.valueOf(startAddr);
		}else{
			Toast.makeText(getActivity(), "��ʼ��Ų���Ϊ��", Toast.LENGTH_SHORT).show();
			return false ;
		}
		
		if(blocksStr != null){
			blocks = Integer.valueOf(blocksStr);
		}else{
			Toast.makeText(getActivity(), "����������Ϊ��", Toast.LENGTH_SHORT).show();
			return false ;
		}
		return true;
	}
	
	private boolean checkWriteParameter(){
		String startAddrStr = editStart.getText().toString();
		String dataWrite = editWrite.getText().toString();
		if(startAddrStr != null){
			startAddr = Integer.valueOf(startAddr);
		}else{
			Toast.makeText(getActivity(), "��ʼ��Ų���Ϊ��", Toast.LENGTH_SHORT).show();
			return false ;
		}
		if(dataWrite != null && dataWrite.length() == 8){
			dataWriteBytes = Utils.HexString2Bytes(dataWrite);
		}else{
			Toast.makeText(getActivity(), "д�����Ϊ8λʮ��������", Toast.LENGTH_SHORT).show();
			return false ;
		}
		return true;
	}
}
