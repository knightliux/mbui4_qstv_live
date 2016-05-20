package com.moon.android.live.custom007;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.moon.android.live.custom007.R;
import com.moon.android.live.custom007.util.Logger;
  
public class CrashHandler implements UncaughtExceptionHandler {  
      
	private static final Logger log = Logger.getInstance();
      
    private Thread.UncaughtExceptionHandler mDefaultHandler;  
    private static CrashHandler INSTANCE = new CrashHandler();  
    private Context mContext;  
  
    private CrashHandler() {  
    }  
  
    public static CrashHandler getInstance() {  
        return INSTANCE;  
    }  
  
    public void init(Context context) {  
        mContext = context;  
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();  
        Thread.setDefaultUncaughtExceptionHandler(this);  
    }  
  
    @Override  
    public void uncaughtException(Thread thread, Throwable ex) {  
    	log.i("App get a uncaughtException");
//        if (!handleException(ex) && mDefaultHandler != null) {  
//            mDefaultHandler.uncaughtException(thread, ex);  
//        } else {  
//            try {  
//                Thread.sleep(3000);  
//            } catch (InterruptedException e) {  
//            	log.e(e.toString());
//            }  
//            android.os.Process.killProcess(android.os.Process.myPid());  
//            System.exit(1);  
//        }  
    	Toast.makeText(mContext, "������һ������", Toast.LENGTH_LONG).show();  
    	
    }  
  
    private boolean handleException(Throwable ex) {  
        if (ex == null) {  
            return false;  
        }  
        new Thread() {  
            @Override  
            public void run() {  
                Looper.prepare();  
                Toast.makeText(mContext, R.string.action_settings, Toast.LENGTH_LONG).show();  
                Looper.loop();  
            }  
        }.start();  
//        collectDeviceInfo(mContext);  
//        saveCrashInfo2File(ex);  
        return true;  
    }  
    
//    public void collectDeviceInfo(Context ctx) {  
//        try {  
//            PackageManager pm = ctx.getPackageManager();  
//            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);  
//            if (pi != null) {  
//                String versionName = pi.versionName == null ? "null" : pi.versionName;  
//                String versionCode = pi.versionCode + "";  
//                infos.put("versionName", versionName);  
//                infos.put("versionCode", versionCode);  
//            }  
//        } catch (NameNotFoundException e) {  
//        	log.e(e.toString());
//        }  
//        Field[] fields = Build.class.getDeclaredFields();  
//        for (Field field : fields) {  
//            try {  
//                field.setAccessible(true);  
//                infos.put(field.getName(), field.get(null).toString());  
////                log.e(e.toString()); 
//            } catch (Exception e) {  
//            	log.e(e.toString()); 
//            }  
//        }  
//    }  
//  
//    private String saveCrashInfo2File(Throwable ex) {  
//          
//        StringBuffer sb = new StringBuffer();  
//        for (Map.Entry<String, String> entry : infos.entrySet()) {  
//            String key = entry.getKey();  
//            String value = entry.getValue();  
//            sb.append(key + "=" + value + "\n");  
//        }  
//          
//        Writer writer = new StringWriter();  
//        PrintWriter printWriter = new PrintWriter(writer);  
//        ex.printStackTrace(printWriter);  
//        Throwable cause = ex.getCause();  
//        while (cause != null) {  
//            cause.printStackTrace(printWriter);  
//            cause = cause.getCause();  
//        }  
//        printWriter.close();  
//        String result = writer.toString();  
//        sb.append(result);  
//        try {  
//            long timestamp = System.currentTimeMillis();  
//            String time = formatter.format(new Date());  
//            String fileName = "crash-" + time + "-" + timestamp + ".log";  
//            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {  
//            	String path = "/sdcard/crash/";  
//                File dir = new File(path);  
//                if (!dir.exists()) {  
//                    dir.mkdirs();  
//                }  
//                FileOutputStream fos = new FileOutputStream(path + fileName);  
//                fos.write(sb.toString().getBytes());  
//                fos.close();  
//            }  
//            return fileName;  
//        } catch (Exception e) {  
//        	log.e(e.toString());
//        }  
//        return null;  
//    }  
}  
