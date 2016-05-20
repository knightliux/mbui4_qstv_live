package com.moon.android.live.custom007.model;

public class LiveUrl {
	private String code;
	private String link;
	private String msg;
	private String url;
	private String key;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCode() {
		return this.code;
	}

	public String getLink() {
		return this.link;
	}

	public String getMsg() {
		return this.msg;
	}

	public String getUrl() {
		return this.url;
	}

	public void setCode(String paramString) {
		this.code = paramString;
	}

	public void setLink(String paramString) {
		this.link = paramString;
	}

	public void setMsg(String paramString) {
		this.msg = paramString;
	}

	public void setUrl(String paramString) {
		this.url = paramString;
	}
}

