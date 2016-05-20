package com.moon.android.live.custom007;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moon.android.live.custom007.model.UpdateData;
import com.moon.android.live.custom007.util.AppMessageData;
import com.moon.android.live.custom007.util.Logger;
import com.moon.android.live.custom007.util.MACUtils;
import com.moon.android.live.custom007.util.StringUtil;
import com.moon.android.live.custom007.util.VersionUtil;

public class UpgradeAndRequest {
	private Logger logger = Logger.getInstance();
	private Context mContext;
	
	public UpgradeAndRequest(Context context){
		mContext = context;
	}
	
	public void sendRequest(){
		//��������Ϣ
		String str1 = Configs.RequestUrl.getUpdateApi(MyApplication.serverAdd) + "appid="
				+ Configs.APPID + "&version="
				+ VersionUtil.getVerName(mContext) + "&mac="
				+ MACUtils.getMac();
		logger.i(str1);
		//new FinalRequestDAO().getAppUpdateMsg(str1, mHandler);
		
		//���������Ϣ
		String str2 = Configs.RequestUrl.getAppMsgApi(MyApplication.serverAdd) + "appid=" + Configs.APPID + "&mac=" + MACUtils.getMac();
		logger.i(str2);
		new FinalRequestDAO().getAppMsg(str2, mHandler);
	}
	
	
	public static final int FIANL_DAO_APP_MSG = 0;
	public static final int FIANL_DAO_UPDATE_MSG = 1;
	@SuppressLint("HandlerLeak")
	public Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			logger.i("����MsgService mHandler  what = " + msg.what);
			switch (msg.what) {
			case FIANL_DAO_APP_MSG:
				Bundle bundle = msg.getData();
				String str2 = bundle.getString(Configs.INTENT_PARAM);
				logger.i("Mssage = " + str2);
				boolean bool = StringUtil.isBlank(str2);
				if (!bool) {
					try{
						String str3 = ((AppMessageData) new Gson().fromJson(str2,
								new TypeToken<AppMessageData>() {
								}.getType())).getMsg();
						MyApplication.appMsg = str3;
						if (!"".equals(str3) && null != str3) {
							Intent intent = new Intent();
							intent.setAction(Configs.BroadCast.APP_GET_MSG);
							mContext.sendBroadcast(intent);
							logger.i("Get app msg and send broadcast : "+ Configs.BroadCast.APP_GET_MSG);
						}
					}catch(Exception e){
						logger.e(e.toString());
					}
				}
				break;
			case FIANL_DAO_UPDATE_MSG:
				Bundle bundle2 = msg.getData();
				String ustr2 = bundle2.getString(Configs.INTENT_PARAM);
				logger.i(ustr2);
				if (!StringUtil.isBlank(ustr2)) {
					try{
						UpdateData localUpdateData = (UpdateData) new Gson()
								.fromJson(ustr2, new TypeToken<UpdateData>() {
								}.getType());
						logger.e(localUpdateData.toString());
						if ((localUpdateData != null)
								&& (!localUpdateData.equals(""))) {
							if ((localUpdateData.getCode() != null)&& (!localUpdateData.getCode().equals(""))&& (localUpdateData.getCode().equals("0"))) {
								if (null != localUpdateData) {
									Intent intent = new Intent();
									intent.setAction(Configs.BroadCast.UPDATE_MSG);
									MyApplication.updateData = localUpdateData;
									mContext.sendBroadcast(intent);
									logger.i("Get update msg and send broadcast : " + Configs.BroadCast.UPDATE_MSG);
								}
							}
						} 
					}catch(Exception e){
						logger.e(e.toString());
					}
				}
				break;
			}
		};
	};

	public void cancel(){
	}
	
}
