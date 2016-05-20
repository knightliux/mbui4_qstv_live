package com.moon.android.live.custom007.util;

import java.io.FileInputStream;
import java.io.IOException;

public class MACUtils {

	public static String getMac() {
		FileInputStream localFileInputStream;
		String mac = "";
		try{
			localFileInputStream = new FileInputStream(
					"/sys/class/net/eth0/address");
			byte[] arrayOfByte = new byte[17];
			localFileInputStream.read(arrayOfByte, 0, 17);
			mac = new String(arrayOfByte);
			localFileInputStream.close();
			if (mac.contains(":"))
				mac = mac.replace(":", "").trim();
			if (mac.contains("-"))
				mac = mac.replace("-", "").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mac.toLowerCase();
//		return "002157f3b51c";
//		return "002157f3d8f6";
	}
	
}
