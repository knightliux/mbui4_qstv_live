package com.moon.android.live.custom007.util;

import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.moon.android.live.custom007.Configs;

public class RequestUtil {
	private static HttpClient client;
	private static RequestUtil util;

	private RequestUtil() {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			// ����ܳ׿�
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			// �ܳ׿������
			trustStore.load(null, null);
			// ע���ܳ׿�
			SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
			// ��У������
			socketFactory
					.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme sch = new Scheme("https", socketFactory, 800);
			httpclient.getConnectionManager().getSchemeRegistry().register(sch);
			client = httpclient;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private HttpClient getHttpClient() {
		return client;
	}

	public static RequestUtil getInstance() {
		if (util == null)
			util = new RequestUtil();
		return util;
	}

	public String request(String paramString) {
		HttpGet httpGet = new HttpGet(paramString);
		httpGet.setHeader("User-Agent", "cwhttp/v1.0");
		String str = null;
		try {
			str = EntityUtils.toString(getHttpClient().execute(httpGet)
					.getEntity(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return str;
	}

	// Post start
	public static void sendPost(String versionName,String versionCode,String errLog) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://192.168.1.153/Api/addlog.php");
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("eL", errLog));
			nameValuePairs.add(new BasicNameValuePair("appid", Configs.APPID));
			nameValuePairs.add(new BasicNameValuePair("vN", versionName));
			nameValuePairs.add(new BasicNameValuePair("vC", versionCode));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			httpclient.execute(httppost);
		} catch (ClientProtocolException e) { 
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}

	}

	// Post end
}
