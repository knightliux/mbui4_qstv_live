package com.moon.android.live.custom007.model;

import java.io.Serializable;
import java.util.List;

public class VodProgram implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5276727549654399902L;
	private String catid;
	private String drama;
	private String grade;
	private String id;
	private String name;
	private String pic;
	private String protagonist;
	private String region;
	private String regisseur;
	private String subcount;
	private List<VodVideo> videos;
	private String year;
	private String videourl;
	private String link;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getVideourl() {
		return videourl;
	}

	public void setVideourl(String videourl) {
		this.videourl = videourl;
	}

	public String getCatid() {
		return this.catid;
	}

	public String getDrama() {
		return this.drama;
	}

	public String getGrade() {
		return this.grade;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getPic() {
		return this.pic;
	}

	public String getProtagonist() {
		return this.protagonist;
	}

	public String getRegion() {
		return this.region;
	}

	public String getRegisseur() {
		return this.regisseur;
	}

	public String getSubcount() {
		return this.subcount;
	}

	public List<VodVideo> getVideos() {
		return this.videos;
	}

	public String getYear() {
		return this.year;
	}

	public void setCatid(String paramString) {
		this.catid = paramString;
	}

	public void setDrama(String paramString) {
		this.drama = paramString;
	}

	public void setGrade(String paramString) {
		this.grade = paramString;
	}

	public void setId(String paramString) {
		this.id = paramString;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public void setPic(String paramString) {
		this.pic = paramString;
	}

	public void setProtagonist(String paramString) {
		this.protagonist = paramString;
	}

	public void setRegion(String paramString) {
		this.region = paramString;
	}

	public void setRegisseur(String paramString) {
		this.regisseur = paramString;
	}

	public void setSubcount(String paramString) {
		this.subcount = paramString;
	}

	public void setVideos(List<VodVideo> paramList) {
		this.videos = paramList;
	}

	public void setYear(String paramString) {
		this.year = paramString;
	}
}
