package com.moon.android.live.custom007.model;

public class ForceLoadItem {

	private int flag;
	private String reason;
	
	public ForceLoadItem(int flag, String reason) {
		super();
		this.flag = flag;
		this.reason = reason;
	}

	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
}

