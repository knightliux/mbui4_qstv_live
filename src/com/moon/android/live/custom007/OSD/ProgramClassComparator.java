package com.moon.android.live.custom007.OSD;

import java.util.Comparator;

public class ProgramClassComparator implements Comparator<Object>{
	
	public int compare(Object arg0, Object arg1) {
		  String[] user0=(String[])arg0;
		  String[] user1=(String[])arg1;
		  int flag=user0[0].compareTo(user1[0]);
		  return flag;
	 }
}
