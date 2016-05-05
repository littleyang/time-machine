package com.vanke.common.model.task;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
//import javax.persistence.Id;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class CopyOfTaskLog {
	
	//@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//为了使得mongo认得这个是Id而不是ObjectId
	@Field("ObjectId")
	private long id;
	
	@Field("status") 
	private int status;
	
	@Field("created") 
	private Date created;
	
	@Field("source_id") 
	private int sourceId;
	
	@Field("rate") 
	private int rate;
	
	@Field("task_no") 
	private String taskNo;
	
	@Field("event") 
	private String event;
	
	@Field("score") 
	private String score;
	
	@Field("msg") 
	private String msg;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
