package com.moon.android.live.custom007.model;

public class UpdateData {
	private String code;
	private String msg;
	private String type;
	private String url;
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCode() {
		return this.code;
	}

	public String getMsg() {
		return this.msg;
	}

	public String getType() {
		return this.type;
	}

	public String getUrl() {
		return this.url;
	}

	public void setCode(String paramString) {
		this.code = paramString;
	}

	public void setMsg(String paramString) {
		this.msg = paramString;
	}

	public void setType(String paramString) {
		this.type = paramString;
	}

	public void setUrl(String paramString) {
		this.url = paramString;
	}

	@Override
	public String toString() {
		return "UpdateData [code=" + code + ", msg=" + msg + ", type=" + type
				+ ", url=" + url + ", version=" + version + "]";
	}
	
}

