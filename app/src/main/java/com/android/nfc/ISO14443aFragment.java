package com.android.nfc;

import com.example.transportgas.R;

import cn.pda.rfid.hf.HfReader;
import cn.pda.rfid.hf.Utils;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ISO14443aFragment extends Fragment implements OnClickListener{
	
	private View rootView ;
	
	private EditText editUID;
	private EditText editSector ;
	private EditText editBlock;
	private EditText editRead ;
	private EditText editWrite ;
	private EditText editPassword;
	private Button buttonFindCard ;
	private Button buttonAuth;
	private Button buttonRead ;
	private Button buttonWrite ;
	
	private RadioGroup radioGroup;
	private RadioButton radioPasswordA;
	private RadioButton radioPasswordB;
	
	private HfReader reader ;
	private Context context;
	private byte[] uid ;
	
	private int sector ;
	private int block;
	private byte[] password ;
	private byte[] dataWrite;
	private boolean authFlag ;//��֤��ʶ
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.iso14443a_fragment, container, false);
		init(rootView);
		return rootView;
	}
	
	private void init(View view){

		
		context = getActivity();
		editUID = (EditText) view.findViewById(R.id.editTextUid);
		editSector = (EditText) view.findViewById(R.id.editTextSector);
		editBlock = (EditText) view.findViewById(R.id.editTextBlock);
		editRead = (EditText) view.findViewById(R.id.editTextReadData);
		editWrite = (EditText) view.findViewById(R.id.editTextWriteData);
		editPassword = (EditText) view.findViewById(R.id.editTextPassword);
		buttonAuth = (Button) view.findViewById(R.id.buttonAuth);
		buttonFindCard = (Button) view.findViewById(R.id.buttonFind14443A);
		buttonRead = (Button) view.findViewById(R.id.buttonRead14443);
		buttonWrite = (Button) view.findViewById(R.id.buttonWrite14443);
		//��ʼ������
		PlayTips.initSoundPool(context);
		buttonFindCard.setOnClickListener(this);
		buttonAuth.setOnClickListener(this);
		buttonRead.setOnClickListener(this);
		buttonWrite.setOnClickListener(this);
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
		case R.id.buttonFind14443A:
			uid = reader.find14443aCardUid();
			if(uid != null){
				editUID.setText(Utils.Bytes2HexString(uid, uid.length));
				PlayTips.play(1, 0);
			}else{
				Toast.makeText(context, "δ��Ѱ����Ƭ��������", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.buttonAuth:
			if(uid == null){
				Toast.makeText(context, "����Ѱ������֤", Toast.LENGTH_SHORT).show();
				return;
			}
			if(checkAuthParameter()){
				/**
				 * sector = sector*4;
				 */
				int flag  = reader.auth14443aCard(0, sector*4, password, uid);
				if(flag == 0){
//					Toast.makeText(context, "��֤�ɹ�", 0).show();
					PlayTips.play(1, 0);
				}else{
					Toast.makeText(context, "��֤ʧ�ܣ������Ի�����Ѱ����֤",Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.buttonRead14443:
			if(checkParameter()){
				int blocks = sector*4 + block;
				byte[] dataRead = reader.read14443aCard(blocks);
				if(dataRead != null){
					editRead.setText(Utils.Bytes2HexString(dataRead, dataRead.length));
					PlayTips.play(1, 0);
				}else{
					Toast.makeText(context, "�����ʧ�ܣ������Ի�����Ѱ����֤", Toast.LENGTH_SHORT).show();
				}
			}
			
			break;
		case R.id.buttonWrite14443:
			if(checkWriteParameter()){
				int blocks = sector*4 + block;
				int writeFlag = reader.write14443aCard(blocks, dataWrite);
				if(writeFlag == 0){
					Toast.makeText(context, "д��ݳɹ�", Toast.LENGTH_SHORT).show();
					PlayTips.play(1, 0);
				}else{
					Toast.makeText(context, "д���ʧ��", Toast.LENGTH_SHORT).show();
				}
				
			}
			break;

		default:
			break;
		}
	}
	
	private boolean checkWriteParameter(){
		String sectorStr = editSector.getText().toString();
		String blockStr = editBlock.getText().toString();
		String dataWriteStr = editWrite.getText().toString();
		if(sectorStr != null){
			sector = Integer.valueOf(sectorStr);
		}else{
			Toast.makeText(context, "������Ϊ��", Toast.LENGTH_SHORT).show();
			return false ;
		}
		if(blockStr != null){
			block = Integer.valueOf(blockStr);
		}else{
			Toast.makeText(context, "��Ų���Ϊ��", Toast.LENGTH_SHORT).show();
			return false ;
		}	
		if(dataWriteStr != null && dataWriteStr.length() == 32){
			dataWrite = Utils.HexString2Bytes(dataWriteStr);
		}else{
			Toast.makeText(context, "д�����Ϊ32λʮ��������", Toast.LENGTH_SHORT).show();
			return false ;
		}
		
		return true;
	}
	
	private boolean checkParameter(){
		String sectorStr = editSector.getText().toString();
		String blockStr = editBlock.getText().toString();
		if(sectorStr != null){
			sector = Integer.valueOf(sectorStr);
		}else{
			Toast.makeText(context, "������Ϊ��", Toast.LENGTH_SHORT).show();
			return false ;
		}
		if(blockStr != null){
			block = Integer.valueOf(blockStr);
		}else{
			Toast.makeText(context, "��Ų���Ϊ��", Toast.LENGTH_SHORT).show();
			return false ;
		}
		return true;
	}
	
	//
	private boolean checkAuthParameter(){
		String sectorStr = editSector.getText().toString();
		String blockStr = editBlock.getText().toString();
		String passwordStr = editPassword.getText().toString();
		if(sectorStr != null){
			sector = Integer.valueOf(sectorStr);
		}else{
			Toast.makeText(context, "������Ϊ��", Toast.LENGTH_SHORT).show();
			return false ;
		}
		if(blockStr != null){
			block = Integer.valueOf(blockStr);
		}else{
			Toast.makeText(context, "��Ų���Ϊ��", Toast.LENGTH_SHORT).show();
			return false ;
		}
		if(passwordStr.length() == 12){
			password = Utils.HexString2Bytes(passwordStr);
		}else{
			Toast.makeText(context, "����Ϊ12λʮ��������", Toast.LENGTH_SHORT).show();
			return false ;
		}
		
		return true;
	}

}
