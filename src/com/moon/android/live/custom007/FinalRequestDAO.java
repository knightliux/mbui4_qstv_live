package com.moon.android.live.custom007;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moon.android.live.custom007.R;
import com.moon.android.live.custom007.OSD.ProgramAdCache;
import com.moon.android.live.custom007.model.LiveData;
import com.moon.android.live.custom007.model.LiveUrl;
import com.moon.android.live.custom007.model.UpdateData;
import com.moon.android.live.custom007.util.Logger;
import com.moon.android.live.custom007.util.NetworkUtil;
import com.moon.android.live.custom007.util.StringUtil;
import com.moon.android.live.custom007.view.HorizontalMessageView;
import com.moon.android.live.custom007.view.MyMarquee;

public class FinalRequestDAO {
	
	public static final Logger logger = Logger.getInstance();
	private Handler mHandler;
	
	public FinalRequestDAO(){}
	
	public int mCheckAuthCount = 0;
//	private boolean readAuthCache = false;
	private Configs.RequestUrl mRequestUrl;
	SSLSocketFactory sf;
	
	
	//��ȡ�б�
	public void getListData(String url,final Handler handler){
		FinalHttp finalHttp = new FinalHttp();
		KeyStore trustStore = null;
		sf = null;
		try {
			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			sf = new SSLSocketFactoryEx(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER); 
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (KeyManagementException e) {
			e.printStackTrace();
		}catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}  
        
        if(null != sf){
        	finalHttp.configSSLSocketFactory(sf);
        }
        if(NetworkUtil.isConnectingToInternet(MyApplication.getApplication())){
        	finalHttp.get(url,new AjaxCallBack<Object>() {
    			public void onSuccess(Object t) {
    				sendIntentMsg(t.toString(),UpgradeAndRequest.FIANL_DAO_UPDATE_MSG,handler);
    			};
    			
    			public void onFailure(Throwable t, int errorNo, String strMsg) {
    				sendIntentMsg("",UpgradeAndRequest.FIANL_DAO_UPDATE_MSG,handler);
    			};
    		});
        }
	}
	
	
	
	//��ȡ���
	public void getProgramAd(final LiveData liveData,int requestTimes,final Context ctx){
		liveData.setHasGetAd(true);
		String tvid = liveData.getId();
		final List<String> list = Configs.RequestUrl.mListGetAd;
		
		final int adPos = requestTimes;
		FinalHttp finalHttp = new FinalHttp();
		KeyStore trustStore = null;
		sf = null;
		try {
			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			sf = new SSLSocketFactoryEx(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER); 
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (KeyManagementException e) {
			e.printStackTrace();
		}catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}  
        
        if(null != sf){
        	finalHttp.configSSLSocketFactory(sf);
        }
        if(NetworkUtil.isConnectingToInternet(MyApplication.getApplication())){
        	String url = list.get(adPos) + tvid;
        	finalHttp.get(url,new AjaxCallBack<Object>() {
        		public void onSuccess(Object t) {
        			String gsonStr = (String)t;
        			logger.i("Get tvid = "+liveData.getId() + " return " + gsonStr);
        			if(!gsonStr.equals("[]"))
        				ProgramAdCache.save(gsonStr, liveData.getId(),ctx);
        		};
        		
        		public void onFailure(Throwable t, int errorNo, String strMsg) {
        			int pos = adPos + 1;
        			if(pos < list.size())
        				getProgramAd(liveData,pos,ctx);
        		};
        	});
        }
	} 
	
	
	//��ȡ����Ϣ��ʼ
	public void getAppUpdateMsg(final String url,final Handler handler){
//		FinalHttp finalHttp = new FinalHttp();
//		KeyStore trustStore = null;
//		sf = null;
//		try {
//			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//			trustStore.load(null, null);
//			sf = new SSLSocketFactoryEx(trustStore);
//			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER); 
//		} catch (KeyStoreException e) {
//			e.printStackTrace();
//		} catch (CertificateException e) {
//			e.printStackTrace();
//		} catch(NoSuchAlgorithmException e){
//			e.printStackTrace();
//		}catch (IOException e) {
//			e.printStackTrace();
//		}catch (KeyManagementException e) {
//			e.printStackTrace();
//		}catch (UnrecoverableKeyException e) {
//			e.printStackTrace();
//		}  
//        
//        if(null != sf){
//        	finalHttp.configSSLSocketFactory(sf);
//        }
//        if(NetworkUtil.isConnectingToInternet(MyApplication.getApplication())){
//        	finalHttp.get(url,new AjaxCallBack<Object>() {
//    			public void onSuccess(Object t) {
//    				logger.i("���������Ϣ�ɹ�"+t);
//    				sendIntentMsg(t.toString(),UpgradeAndRequest.FIANL_DAO_UPDATE_MSG,handler);
//    			};
//    			
//    			public void onFailure(Throwable t, int errorNo, String strMsg) {
//    				logger.e("���������Ϣʧ�ܣ���������");
//    				getAppUpdateMsg(url,handler);
//    			};
//    		});
//        }
	}
		
		//��ȡ����Ϣ����
		
		
	
	//��ȡӦ����Ϣ��ʼ
	public void getAppMsg(final String url,final Handler handler){
		logger.i(url);
		mHandler = handler;
		FinalHttp finalHttp = new FinalHttp();
		KeyStore trustStore = null;
		sf = null;
		try {
			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			sf = new SSLSocketFactoryEx(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER); 
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (KeyManagementException e) {
			e.printStackTrace();
		}catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}  
        
        if(null != sf){
        	finalHttp.configSSLSocketFactory(sf);
        }
        if(NetworkUtil.isConnectingToInternet(MyApplication.getApplication())){
        	finalHttp.get(url,new AjaxCallBack<Object>() {
        		public void onSuccess(Object t) {
        			logger.i("���������Ϣ�ɹ�");
        			sendIntentMsg(t.toString(),UpgradeAndRequest.FIANL_DAO_APP_MSG);
        		};
        		
        		public void onFailure(Throwable t, int errorNo, String strMsg) {
//        			sendIntentMsg("",MsgService.FIANL_DAO_APP_MSG);
        			logger.e("���������Ϣʧ�ܣ���������"+"strMsg="+strMsg+" errorNo="+errorNo);
        			getAppMsg(url,handler);
        		};
        	});
        }
	}
	
	private void sendIntentMsg( String data,int what){
		Message msg = new Message();
		msg.what = what;
		Bundle bundle = new Bundle();
		bundle.putString(Configs.INTENT_PARAM, data);
		msg.setData(bundle);
		mHandler.sendMessage(msg);
	}
	
	private void sendIntentMsg( String data,int what,Handler handler){
		Message msg = new Message();
		msg.what = what;
		Bundle bundle = new Bundle();
		bundle.putString(Configs.INTENT_PARAM, data);
		msg.setData(bundle);
		handler.sendMessage(msg);
	}
	//��ȡӦ����Ϣ����
	
	
	
	////��Ȩ���  ��ʼ....
	//�Ƿ��Ѿ��ӻ����ж�ȡ�������
	//����ȡ�˻����е������ͨ���ˣ�����֤��ͨ����ر�Ӧ��
	private Handler mHandlerCheckAuth;
	public void checkAuth(LiveActivity a, Handler h){
		mActivity = a;
		mHandlerCheckAuth = h;
//		readAuthCache = isCheckedFromCache;
//		mRequestUrl = Configs.equestUrl;
		mCheckAuthCount = 0;
		FinalHttp finalHttp = new FinalHttp();
		
		KeyStore trustStore = null;
		sf = null;
		try {
			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			sf = new SSLSocketFactoryEx(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER); 
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (KeyManagementException e) {
			e.printStackTrace();
		}catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}  
        
        if(null != sf){
        	finalHttp.configSSLSocketFactory(sf);
        }
     //   logger.i(mRequestUrl.mListAuthAdress.get(mCheckAuthCount));
        if(NetworkUtil.isConnectingToInternet(MyApplication.getApplication())){
        	finalHttp.get(mRequestUrl.mListAuthAdress.get(mCheckAuthCount),mCheckAuthCallBack);
        }
	}
	
	private AjaxCallBack<Object> mCheckAuthCallBack = new AjaxCallBack<Object>() {
		@Override
		public void onSuccess(Object t) {
			super.onSuccess(t);
			logger.i((String)t);
			LiveUrl vodUrl = parseAuthMsg((String)t);
			//auth fail 
			if(null == vodUrl) {
				VodHistory.saveCache(mActivity, false);
//				if(readAuthCache) {
//					//����ͨ���ˣ�Ȼ�������ȡ�����ʧ����
//					//��֤ʧ�ܣ�5s��رճ���
//					mHandlerCheckAuth.sendEmptyMessage(Configs.AUTH_FAIL_FINISH);
//				} else {
//					mHandlerCheckAuth.sendEmptyMessage(Configs.AUTH_FAIL);
//				}
				mHandlerCheckAuth.sendEmptyMessage(Configs.AUTH_FAIL);
				logger.i("[��Ȩʧ��]");
				return;
			} else {
				MyApplication.serverAdd=Configs.ServerHttpAdd.serverList.get(mCheckAuthCount);//��ʼ��Ӧ�÷�Χ�ķ�������ַ
				VodHistory.saveCache(mActivity, true);
				VodHistory.saveKey(mActivity, vodUrl.getKey());
				VodHistory.saveLink(mActivity, vodUrl.getLink());
				Message message = new Message();
				message.what = Configs.AUTH_SUCCESS_CACHE;
				Bundle bundle = new Bundle();
//				bundle.putString(Configs.INTENT_PARAM, vodUrl.getUrl());//�Ϸ�ʽ����Ȩ�ɹ�,��̨���ػ�ȡ��Ŀ�б��URL
				bundle.putString(Configs.INTENT_PARAM, Configs.getProgramListApi(MyApplication.serverAdd));//�·�ʽ: ��Ȩ�ɹ�,�Լ������ȡ��Ŀ�б��URL
				message.setData(bundle);
				Configs.key = vodUrl.getKey();
				Configs.link = vodUrl.getLink();
				
				mHandlerCheckAuth.sendMessage(message);
				logger.i("[��Ȩ�ɹ�]");
			}
		}
		
		@Override
		public void onFailure(Throwable t, int errorNo, String strMsg) {
			super.onFailure(t, errorNo, strMsg);
			logger.i("��ȨerrorNo " + errorNo +" strMsg " + strMsg);
			int size = mRequestUrl.mListAuthAdress.size();
			
			FinalHttp finalHttp = new FinalHttp();
			if(null != sf){
	        	finalHttp.configSSLSocketFactory(sf);
	        }
			mCheckAuthCount++;
			logger.i("mCheckAuthCount = " + mCheckAuthCount+"  server="+mRequestUrl.mListAuthAdress.get(mCheckAuthCount % size));
			finalHttp.get(mRequestUrl.mListAuthAdress.get(mCheckAuthCount % size),mCheckAuthCallBack);
		}
	};
	
	private LiveUrl parseAuthMsg(String authMsg){
		try{
			if (StringUtil.isBlank(authMsg)) return null;
			Gson localGson = new Gson();
			LiveUrl vodUrl = (LiveUrl) localGson.fromJson(authMsg,
					new TypeToken<LiveUrl>() {}.getType());
			if (null != vodUrl && vodUrl.getCode().equals("0")) 
				return vodUrl;
		}catch(Exception e){
			logger.e(e.toString());
		}
		return null;
	}
	////��Ȩ���  ����....
	
	
	//����APK -- ��ʼ
	
	private String mUpdateUrl;
	private String mUpdateMsg;
	private Button mBtnSubmit;
	private Button mBtnCancel;
	private PopupWindow mUpdatePopupWindow;
	private LiveActivity mActivity;
	@SuppressWarnings("deprecation")
	public void initPopWindow(final String appUrl,
			UpdateData updata, LiveActivity a, Handler h) {
		try{
			mActivity = a;
			mHandler = h;
			mUpdateUrl = appUrl;
			String updateMsg = updata.getMsg();
			logger.i("updata.getType() = " + updata.getType());
			boolean isMustUpdate = "0".equals(updata.getType()) ? true : false;
			mUpdateMsg = null == updateMsg ? "" : updateMsg;
			View view = LayoutInflater.from(mActivity).inflate(R.layout.update_dialog, null);
			mBtnSubmit = (Button) view.findViewById(R.id.dialog_submit);
			mBtnCancel = (Button) view.findViewById(R.id.dialog_cancel);
			mBtnSubmit.setOnClickListener(mDialogClickListener);
			if(!isMustUpdate){
				logger.i("Must update");
				mBtnCancel.setOnClickListener(mDialogClickListener);
			} else{
				logger.i("Not must update");
				mBtnCancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						System.exit(0);
					}
				});
			}
			TextView textContent = (TextView) view.findViewById(R.id.text_content);
			Spanned text = Html.fromHtml(mUpdateMsg); 
			textContent.setText(text);
			
			int width = mActivity.getWindowManager().getDefaultDisplay().getWidth();
			int height = mActivity.getWindowManager().getDefaultDisplay().getHeight();
			mUpdatePopupWindow = new PopupWindow(view,width,height,true);
			mUpdatePopupWindow.showAsDropDown(view,0,0);
			mUpdatePopupWindow.setOutsideTouchable(false);
		}catch(Exception e){
			logger.e(e.toString());
		}
	}
	
	private OnClickListener mDialogClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			mUpdatePopupWindow.dismiss();
			if(v == mBtnSubmit){
				Toast.makeText(mActivity, R.string.start_download, Toast.LENGTH_LONG).show();
				FinalRequestDAO.downFile(mUpdateUrl,mActivity,mHandler);
			}
		}
	};
	
	
	public static void downFile(final String paramString,final Context context, final Handler handler) {
		new Thread() {
			public void run() {
				try {
					DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
					HttpGet localHttpGet = new HttpGet(paramString.trim());
					HttpEntity localHttpEntity = localDefaultHttpClient
							.execute(localHttpGet).getEntity();
					localHttpEntity.getContentLength();
					InputStream localInputStream = localHttpEntity.getContent();
					FileOutputStream localFileOutputStream = null;
					byte[] arrayOfByte;
					if (localInputStream != null) {
						localFileOutputStream = context.openFileOutput(Configs.APK_NAME,1);
						arrayOfByte = new byte[1024];
						int j = 0;
						while ((j = localInputStream.read(arrayOfByte)) != -1) {
							localFileOutputStream.write(arrayOfByte, 0, j);
						}
						localFileOutputStream.flush();
					}
					if (localFileOutputStream != null)
						localFileOutputStream.close();
					handler.sendEmptyMessage(LiveActivity.START_INSTALL);
					return;
				} catch (ClientProtocolException localClientProtocolException) {
					localClientProtocolException.printStackTrace();
					return;
				} catch (IOException localIOException) {
					localIOException.printStackTrace();
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		}.start();
	}
	//����APK --����
	
	
	//��ʾ�����  --��ʼ
	private MyMarquee mMarquee;
	public void showMsg(String paramString,LiveActivity a, Handler h) {
		mHandler = h;
		mActivity =a;
		if(null == mMarquee)
			mMarquee = new MyMarquee(mActivity);
		if ((paramString != null) && (!paramString.equals(""))) {
			mMarquee.remove();
			mMarquee.setFocusable(false);
			mMarquee.setGravity(53, 1275, 10, 20);
			HorizontalMessageView localHorizontalMessageView = new HorizontalMessageView(mActivity, paramString);
			localHorizontalMessageView.setTextColor(-1);
			localHorizontalMessageView.setTextSize(28);
			localHorizontalMessageView.startScroll();
			mMarquee.setView(localHorizontalMessageView);
			mMarquee.show();
			return;
		}
		mMarquee.remove();
	}
	
	public void dismissMarquee(){
		if(null != mMarquee)
			mMarquee.remove();
	}
	//��ʾ����� --����
	
	
	//�˳�ѡ���  -��ʼ
	private PopupWindow mExitPopupWindow;
    @SuppressWarnings("deprecation")
	public void showExitWindow(LiveActivity a, Handler h){  
    	mActivity = a;
    	mHandler = h;
    	LayoutInflater mInflater = (LayoutInflater) mActivity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    	View view = mInflater.inflate(R.layout.p_exit_pop, null);
    	int width = mActivity.getWindowManager().getDefaultDisplay().getWidth();
		int height = mActivity.getWindowManager().getDefaultDisplay().getHeight();
		mExitPopupWindow = new PopupWindow(view,width,height,true);
		mExitPopupWindow.showAsDropDown(view,0,0);
		mExitPopupWindow.setOutsideTouchable(false);
		Button sure = (Button) view.findViewById(R.id.p_eixt_sure);
		Button cancel = (Button) view.findViewById(R.id.p_eixt_cancel);
		cancel.requestFocus();
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismisExitWindow();
			}
		});
		sure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mActivity.exit();
			}
		});
    }
    
    public void dismisExitWindow(){
    	if(null != mExitPopupWindow && mExitPopupWindow.isShowing())
    		mExitPopupWindow.dismiss();
    }
	//�˳�ѡ���  ����
}
