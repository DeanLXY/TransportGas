package com.android.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.android.module.Information;

public class GrabListService {
	public List<Information> getXmlPersons(List list) throws Exception {

		String url = "http://192.168.2.112:8080/JustsyApp/Applet";
		// 第一步，创建HttpPost对象
		HttpPost httpPost = new HttpPost(url);

		// 设置HTTP POST请求参数必须用NameValuePair对象
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", list.toString()));

		HttpResponse httpResponse = null;
		try {
			// 设置httpPost请求参数
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			httpResponse = new DefaultHttpClient().execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// 第三步，使用getEntity方法活得返回结果
				String result = EntityUtils.toString(httpResponse.getEntity());
			}
		}catch (Exception e) {
			throw new RuntimeException("网络出错: " + e);
		}

		return null;

	}
}
