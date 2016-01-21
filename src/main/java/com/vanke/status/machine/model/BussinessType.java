package com.vanke.status.machine.model;

import java.util.Date;

public class BussinessType {
	
	private int id;
	private String code;
	private String name;
	private String parentCode;
	private String url;
	private int relatedAddressType;
	private int standardWorkTimeInMinute;
	private String createdBy;
	private Date created;
	private Date updated;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getRelatedAddressType() {
		return relatedAddressType;
	}
	public void setRelatedAddressType(int relatedAddressType) {
		this.relatedAddressType = relatedAddressType;
	}
	public int getStandardWorkTimeInMinute() {
		return standardWorkTimeInMinute;
	}
	public void setStandardWorkTimeInMinute(int standardWorkTimeInMinute) {
		this.standardWorkTimeInMinute = standardWorkTimeInMinute;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
