package com.moon.android.live.custom007.model;


public class LiveData {

	private String id;
	private String dname;
	private String type;
	private String kbps;
	private int hd;
	private String url;
	private String streamIp;
	private String channelId;
	private String ico;
	private String order;
	private String orderid;
	private String inAllProgramPos;
	private String classname;
	
	private boolean hasGetAd = false;

	public boolean isHasGetAd() {
		return hasGetAd;
	}

	public void setHasGetAd(boolean hasGetAd) {
		this.hasGetAd = hasGetAd;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getInAllProgramPos() {
		return inAllProgramPos;
	}

	public void setInAllProgramPos(String inAllProgramPos) {
		this.inAllProgramPos = inAllProgramPos;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String name) {
		this.dname = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKbps() {
		return kbps;
	}
	public void setKbps(String kbps) {
		this.kbps = kbps;
	}
	public int getHd() {
		return hd;
	}
	public void setHd(int hd) {
		this.hd = hd;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getStreamIp() {
		return streamIp;
	}
	public void setStreamIp(String streamIp) {
		this.streamIp = streamIp;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
}
