package com.moon.android.live.custom007.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;

public class FileUtil {

	public static String getFileContent(String filePath) {
		String path = filePath;
		String content = ""; // �ļ������ַ�
		// ���ļ�
		File file = new File(path);
		// ���path�Ǵ��ݹ����Ĳ��������һ����Ŀ¼���ж�
		if (file.isDirectory()) {
			Log.d("TestFile", "The File doesn't not exist.");
		} else {
			try {
				InputStream instream = new FileInputStream(file);
				if (instream != null) {
					InputStreamReader inputreader = new InputStreamReader(
							instream);
					BufferedReader buffreader = new BufferedReader(inputreader);
					String line;
					// ���ж�ȡ
					while ((line = buffreader.readLine()) != null) {
						content += line + "\n";
					}
					instream.close();
				}
			} catch (java.io.FileNotFoundException e) {
				Log.d("TestFile", "The File doesn't not exist.");
			} catch (IOException e) {
				Log.d("TestFile", e.getMessage());
			}
		}
		return content;
	}
}
