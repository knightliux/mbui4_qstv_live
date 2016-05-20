package com.moon.utils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class SHA1Util {

	/**
	 * 计算文件的 SHA-1 值
	 * @param file 传入的文件
	 * @return   <strong>null</strong> 输入的文件不存在或者不是一个文件 <br>
	 * 	 <strong>md5值</strong>  文件的 SHA-1值
	 */
	public static String getFileSha1(File file) {
	    if (!file.isFile()) {
	        return null;
	    }
	    MessageDigest digest = null;
	    FileInputStream in = null;
	    byte buffer[] = new byte[8192];
	    int len;
	    try {
	        digest =MessageDigest.getInstance("SHA-1");
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
}
