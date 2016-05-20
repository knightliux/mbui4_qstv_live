package com.moon.android.live.custom007;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.os.Environment;

import com.moon.android.live.custom007.util.MACUtils;
import com.moon.ndk.URL;

public class Configs {

	public static final boolean debug = true;
	public static final boolean isShowClassify = false;
	public static final String APPID = "852001";
	public static final String PKG_NAME = "com.moonlivehk.android.iptv";

	public static String link;
	public static String key;
	public static String chip;

	public static final int NETWORK_NOT_CONNECT = 0x25025001;
	public static final int NETWORK_CONNECT = 0x25025002;
	public static final int AUTH_STOP = 0x25025003;
	public static final int AUTH_FAIL = 0x25025004;
	public static final int SUCCESS = 0x25025005;
	public static final int FAIL = 0x25025006;
	public static final int VIDEO_PLAY = 0x25025007;
	public static final int NETWORK_EXCEPTION = 0x25025008;
	public static final int CONTENT_IS_NULL = 0x25025009;
	public static final int LOAD_DATA_SUCCESS = 0x25025010;
	public static final int AUTH_SUCCESS_CACHE = 0x25025011;
	public static final int VOD_CAN_PLAY = 0x25025012;
	public static final int AUTH_FAIL_FINISH = 0x25025013;
	public static final int AUTH_SUCCESS_NETWORK = 0x25025014;
	public static final int READ_CACHE = 0x25025015;
	public static final int EXIT = 0x25025016;
	public static final int FORCE_SO_LOADED = 0x25025017;
	public static final int AD_SHOW_FINISH = 0x25025018;

	public static final String APK_NAME = "update.apk";
	public static final String INTENT_PARAM = "intent_param";
	public static final String INTENT_PARAM02 = "intent_param2";

	public static final class ServerHttpAdd {

		public static final String SERVER_HTTP_ADDRESS01 = getHttpServer01();
		public static final String SERVER_HTTP_ADDRESS02 = getHttpServer02();
		public static final String SERVER_HTTP_ADDRESS03 = getHttpServer03();

		public static final List<String> serverList = Arrays.asList(
				SERVER_HTTP_ADDRESS01, SERVER_HTTP_ADDRESS02,
				SERVER_HTTP_ADDRESS03);

	}//
    public static String AllServer="http://alivehk.videohk.video:9008";
    public static String getHttpServer01(){
    	return "http://alivehk.videohk.video:9008";
    }
    public static String getHttpServer02(){
    	return "http://alivehk.adfex.click:9008";
    }
    public static String getHttpServer03(){
    	return "http://alivehk.gfhjc.work:9008";
    }
	public static class RequestUrl {

		public static final String AUTH_PARAMTER = "mac=" + MACUtils.getMac() + "&appid=" + Configs.APPID + "&jsontype=2";
		private static final String AUTH_TAIL = "/index.php/Api/App/auth?" + AUTH_PARAMTER;
		public static final List<String> mListAuthAdress = Arrays.asList(
				ServerHttpAdd.serverList.get(0) + AUTH_TAIL,
				ServerHttpAdd.serverList.get(1) + AUTH_TAIL,
				ServerHttpAdd.serverList.get(2) + AUTH_TAIL);

		
		public static String getAppMsgApi(String server){
			return server + "/index.php/Api/App/appmsg?";
		}
		public static String getUpdateApi(String server){
			return server + "/index.php/Api/App/upgrade?";
		}

		public static final String GET_LIST_TAIL = "/Api/TvAd?appid=" + APPID + "&mac=" + MACUtils.getMac() + "&tvid=";
		public static final List<String> mListGetAd = Arrays.asList(
				ServerHttpAdd.serverList.get(0) + GET_LIST_TAIL,
				ServerHttpAdd.serverList.get(1) + GET_LIST_TAIL,
				ServerHttpAdd.serverList.get(2) + GET_LIST_TAIL);

	}

	public static final class BroadCast {
		public static final String UPDATE_MSG = PKG_NAME + ".update";
		public static final String APP_GET_MSG = PKG_NAME + ".msg";
	}

	public static final class Force {
		public static final int Force_request_success = 0;
	}

	public static final String APP_DATA_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PKG_NAME + "/";

//	public static final String CONTENT_CACHE_FILE = MyApplication.getApplication().getCacheDir().getAbsolutePath() + "/just" + APPID;
	public static String getCacheFile(Context context){
		return context.getApplicationContext().getCacheDir().getAbsoluteFile()+"/just"+APPID;
	}
	public static final String getContentCachaFile(Context context) {
		return context.getExternalCacheDir().getAbsolutePath();
	}

	public static final String WHITE_URL = "http://api.cloudtv.bz/v3/fire_login/1/";
	
	public static String getHBSApi(String server){
		return server + "/index.php/Api/App/Beat?appid=" + APPID;
	}
	
	// ��ȡֱ���б��API:http://live1.itvpad.co:9011/index.php/Api/App/tvjson?appid=100004&mac=002157f3b51c&jsontype=2
	public static String getProgramListApi(String server){
		return server+ "/index.php/Api/App/tvjson?appid=" + APPID + "&mac=" + MACUtils.getMac() + "&jsontype=2";
	}
}
