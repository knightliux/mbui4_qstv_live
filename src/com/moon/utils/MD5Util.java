package com.moon.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.moon.android.live.custom007.util.Logger;

public class MD5Util {
	
	public static final Logger log = Logger.getInstance();
	/**
	 * �����ļ��� MD5 ֵ
	 * @param s ������ַ�
	 * @return   <strong>null</strong> ������ļ������ڻ��߲���һ���ļ� <br>
	 * 	 <strong>md5ֵ</strong>  �ַ��16λMD5ֵ  
	 */
	public static String getStringMD5_16(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(s.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString().substring(8, 24); 
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * �����ļ��� MD5 ֵ
	 * @param s ������ַ�
	 * @return   <strong>null</strong> ������ļ������ڻ��߲���һ���ļ� <br>
	 * 	 <strong>md5ֵ</strong>  �ַ��32λMD5ֵ  
	 */
	public static String getStringMD5_32(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(s.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16){
					buf.append("0");
				}
				String hexI = Integer.toHexString(i);
				buf.append(hexI);
			}
			return buf.toString(); //32λ�ļ���
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//��bug
	/**
	 * �����ļ��� MD5 ֵ
	 * @param file ������ļ�
	 * @return   <strong>null</strong> ������ļ������ڻ��߲���һ���ļ� <br>
	 * 	 <strong>md5ֵ</strong>  �ļ���MD5ֵ
	 */
	/*public static String getFileMD5(File file) {
	    if (!file.isFile()) {
	        return null;
	    }
	    MessageDigest digest = null;
	    FileInputStream in = null;
	    byte buffer[] = new byte[8192];
	    int len;
	    try {
	        digest = MessageDigest.getInstance("MD5");
	        in = new FileInputStream(file);
	        while ((len = in.read(buffer)) != -1) {
	            digest.update(buffer, 0, len);
	        }
	        BigInteger bigInt = new BigInteger(1, digest.digest());
	        return bigInt.toString(16);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        try {
	            in.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public static String getMd5ByFile(File file) {
		String value = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			MappedByteBuffer byteBuffer = in.getChannel().map(
					FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}*/
	
	
	protected static MessageDigest messagedigest = null;  
	static {  
        try {  
            messagedigest = MessageDigest.getInstance("MD5");  
        } catch (NoSuchAlgorithmException e) {  
            log.e("MD5FileUtil messagedigest��ʼ��ʧ��");  
        }  
    }  
  
    @SuppressWarnings("resource")
	public static String getFileMD5String(File file)  {  
    	if(null == file || !file.exists()) return "";
        FileInputStream in;
		try {
			in = new FileInputStream(file);
			FileChannel ch = in.getChannel();  
	        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,  
	                file.length());  
	        messagedigest.update(byteBuffer);  
	        return bufferToHex(messagedigest.digest());  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  catch(IOException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		return "";
    }  
    
    private static String bufferToHex(byte bytes[]) {  
        return bufferToHex(bytes, 0, bytes.length);  
    }  
    
    private static String bufferToHex(byte bytes[], int m, int n) {  
        StringBuffer stringbuffer = new StringBuffer(2 * n);  
        int k = m + n;  
        for (int l = m; l < k; l++) {  
            appendHexPair(bytes[l], stringbuffer);  
        }  
        return stringbuffer.toString();  
    } 
    
    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {  
        char c0 = hexDigits[(bt & 0xf0) >> 4];  
        char c1 = hexDigits[bt & 0xf];  
        stringbuffer.append(c0);  
        stringbuffer.append(c1);  
    }  
    
    protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',  
        '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };  
  
}
