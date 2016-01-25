package com.vanke.status.machine.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.mongodb.core.mapping.Field;

@Entity
@Table(name="business_type")
public class BussinessType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//为了使得mongo认得这个是Id而不是ObjectId
	@Field("id")
	private int id;
	
	@Column(name="code")
	private String code;
	
	@Column(name="name")
	private String name;
	
	@Column(name="parent_code")
	private String parentCode;
	
	@Column(name="url")
	private String url;
	
	@Column(name="related_address_type")
	private int relatedAddressType;
	
	@Column(name="standard_work_time_in_minute")
	private int standardWorkTimeInMinute;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="created")
	private Date created;
	
	@Column(name="updated")
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
