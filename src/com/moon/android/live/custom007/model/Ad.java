package com.moon.android.live.custom007.model;


/**
 * Model--���
 * @author Jervis
 *	
 */
public class Ad {
	
	/**
	 * �����ʾ������
	 */
	private String sec;
	
	private String adurl;
	
	public String getSec() {
		return sec;
	}
	public void setSec(String sec) {
		this.sec = sec;
	}
	public String getAdurl() {
		return adurl;
	}
	public void setAdurl(String adurl) {
		this.adurl = adurl;
	}
	
	@Override
	public String toString() {
		return "Ad [sec=" + sec + ", adurl=" + adurl + "]";
	}
	
}
