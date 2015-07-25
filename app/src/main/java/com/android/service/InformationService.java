package com.android.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Log;
import android.util.Xml;

import com.android.module.Information;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketConnectionHandler;
import de.tavendo.autobahn.WebSocketException;

public class InformationService {
	

//	public List<Information> getXmlPersons() throws Exception {
//		URL url = new URL("http://192.168.1.101:8080/05.web/address.xml");
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//		conn.setConnectTimeout(5000);
//
//		int code = conn.getResponseCode();
//		if (code == 200) {
//			InputStream in = conn.getInputStream();
//			return parseXml(in);
//		}
//
//		throw new RuntimeException("网络出错: " + code);
//	}
	
//	private List<Information> parseXml(InputStream in) throws Exception {
//		List<Information> list = new ArrayList<Information>();
//		Information i = null;
//
//		XmlPullParser parser = Xml.newPullParser();
//		parser.setInput(in, "UTF-8");
//		for (int type = parser.getEventType(); type != XmlPullParser.END_DOCUMENT; type = parser.next()) {
//			if (type == XmlPullParser.START_TAG) {
//				if ("information".equals(parser.getName())) {
//					i = new Information();
//					String id = parser.getAttributeValue(0);
//					i.setId(Integer.parseInt(id));
//					list.add(i);
//				} else if ("address".equals(parser.getName())) {
//					String address = parser.nextText();
//					i.setAddress(address);
//				} else if ("order".equals(parser.getName())) {
//					String order = parser.nextText();
//					i.setOrder(order);
//				}else if ("time".equals(parser.getName())) {
//					String time = parser.nextText();
//					i.setTime(time);
//				}else if ("consumer".equals(parser.getName())) {
//					String consumer = parser.nextText();
//					i.setConsumer(consumer);
//				}else if ("telephony".equals(parser.getName())) {
//					String telephony = parser.nextText();
//					i.setTelephony(telephony);
//				}
//		    }
//		}
//		return list;
//	}
	
	private final WebSocketConnection mConnection = new WebSocketConnection();
	public void getWebSocketPersons() throws Exception {
		final String wsuri = "ws://192.168.1.101:8080/myWeb/echo.ws";

		try {
			mConnection.connect(wsuri, new WebSocketConnectionHandler() {
				@Override
				public void onOpen() {
					Log.d("TAG", "Status: Connected to " + wsuri);
					//发送数据
					//mConnection.sendTextMessage("mm" + "!@#$%" + et.getText().toString());
					
				}
				//接收数据
				@Override
				public void onTextMessage(String payload) {
					Log.d("TAG", "Got echo: " + payload);
					try {
					    parseString(payload);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public void onClose(int code, String reason) {
					Log.d("TAG", "Connection lost.");
				}

			});
			
		} catch (WebSocketException e) {

		}

	}
	
	private List<Information> parseString(String in) throws Exception{
		
		List<Information> list = new ArrayList<Information>();
		Information i = null;
		if (in != null) {
			i = new Information();
		
			int id = 1;
//			i.setId(id++);
//			i.setAddress("北京市海淀区西北旺路东馨园小区6号楼3单元501");
//			i.setOrder(in);
//			i.setTime("2015-5-5");
//			i.setConsumer("张女士");
//			i.setTelephony("16709338922");
			list.add(i);
		}
		return list;
		
	}
}
