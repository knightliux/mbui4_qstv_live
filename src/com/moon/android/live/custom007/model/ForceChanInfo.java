package com.moon.android.live.custom007.model;

public class ForceChanInfo {
	private int ret;
	private String reason;
	private int checkRet;
	private String checkReason;
	private int cacheTime;
	private int downloadKbps;

	public ForceChanInfo(int ret, String reason, int checkRet,
			String checkReason, int cacheTime, int downloadKbps) {
		super();
		this.ret = ret;
		this.reason = reason;
		this.checkRet = checkRet;
		this.checkReason = checkReason;
		this.cacheTime = cacheTime;
		this.downloadKbps = downloadKbps;
	}
	
	public int getCheckRet() {
		return checkRet;
	}

	public void setCheckRet(int checkRet) {
		this.checkRet = checkRet;
	}

	public String getCheckReason() {
		return checkReason;
	}

	public void setCheckReason(String checkReason) {
		this.checkReason = checkReason;
	}

	public int getRet() {
		return ret;
	}
	public void setRet(int ret) {
		this.ret = ret;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getCacheTime() {
		return cacheTime;
	}
	public void setCacheTime(int cacheTime) {
		this.cacheTime = cacheTime;
	}
	public int getDownloadKbps() {
		return downloadKbps;
	}
	public void setDownloadKbps(int downloadKbps) {
		this.downloadKbps = downloadKbps;
	}
}
