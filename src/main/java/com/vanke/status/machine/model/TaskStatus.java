package com.vanke.status.machine.model;

import java.util.Date;

public class TaskStatus {
	
	private int id;
	private int status;
	private String name;
	private int outerStatus;
	private String outerName;
	private int type;
	private Date created;
	private Date updated;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOuterStatus() {
		return outerStatus;
	}
	public void setOuterStatus(int outerStatus) {
		this.outerStatus = outerStatus;
	}
	public String getOuterName() {
		return outerName;
	}
	public void setOuterName(String outerName) {
		this.outerName = outerName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
