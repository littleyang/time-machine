package com.vanke.status.machine.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.vanke.status.machine.model.base.BaseModel;

@Entity
@Table(name="task_events") 
public class TaskEvents extends BaseModel{
	
	@Id
	private int id;
	
	@Column(name="code") 
	private String code;
	
	@Column(name="name")
	private String name;
	
	@Column(name="msg")
	private String msg;
	
	@Column(name="type")
	private int type;
	
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
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
