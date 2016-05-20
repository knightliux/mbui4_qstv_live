package com.moon.android.live.custom007.model;

import java.io.Serializable;
import java.util.List;

public class VodCategory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8001366462145294318L;
	private String id;
	private String name;
	private String pic;
	private List<VodProgram> programs;
	private String subcount;

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getPic() {
		return this.pic;
	}

	public List<VodProgram> getPrograms() {
		return this.programs;
	}

	public String getSubcount() {
		return this.subcount;
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

	public void setPrograms(List<VodProgram> paramList) {
		this.programs = paramList;
	}

	public void setSubcount(String paramString) {
		this.subcount = paramString;
	}
}
