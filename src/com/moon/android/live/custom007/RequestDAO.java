package com.moon.android.live.custom007;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moon.android.live.custom007.model.UpdateData;
import com.moon.android.live.custom007.util.AppMessageData;
import com.moon.android.live.custom007.util.Logger;
import com.moon.android.live.custom007.util.MACUtils;
import com.moon.android.live.custom007.util.RequestUtil;
import com.moon.android.live.custom007.util.VersionUtil;

public class RequestDAO {
	
	public static Logger logger = Logger.getInstance();
	private static RequestDAO reuqestDAO;
	private RequestDAO(){}
	
	public static RequestDAO getInstance(){
		new RequestDAOInstance();
		return reuqestDAO;
	}
	
	private static class RequestDAOInstance{
		public RequestDAOInstance(){
			reuqestDAO = new RequestDAO();
		}
	}
	
	
	
	public static UpdateData checkUpate(Context context) {
		try {
			String str1 = Configs.RequestUrl.getUpdateApi(MyApplication.serverAdd)+"appid="
					+ Configs.APPID + "&version=" + VersionUtil.getVerName(context) + "&mac="
					+ MACUtils.getMac();
			logger.i("update url = " + str1);
			String str2 = RequestUtil.getInstance().request(str1);
			logger.i("update result = " + str2);
			if (isBlank(str2))
				return null;
			UpdateData localUpdateData = (UpdateData) new Gson().fromJson(str2,
					new TypeToken<UpdateData>() {
					}.getType());
			if ((localUpdateData != null) && (!localUpdateData.equals(""))) {
				if ((localUpdateData.getCode() != null)
						&& (!localUpdateData.getCode().equals(""))
						&& (localUpdateData.getCode().equals("0"))) {
					return localUpdateData;
				}
				return null;
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			return null;
		}
		return null;
	}
	
	
	public static String getAppMessage() {
		try {
			String str1 = Configs.RequestUrl.getAppMsgApi(MyApplication.serverAdd)+"appid="
					+ Configs.APPID + "&mac=" + MACUtils.getMac();
			logger.i("get message url = " + str1);
			String str2 = RequestUtil.getInstance().request(str1);
			logger.i("get message result = " + str2);
			boolean bool = isBlank(str2);
			if (bool)
				return "";
			String str3 = ((AppMessageData) new Gson().fromJson(str2,
					new TypeToken<AppMessageData>() {
					}.getType())).getMsg();
			return str3;
		} catch (Exception localException) {
		}
		return "";
	}
	
	private static boolean isBlank(String paramString) {
		return (paramString == null) || (paramString.trim().equals(""));
	}
}
