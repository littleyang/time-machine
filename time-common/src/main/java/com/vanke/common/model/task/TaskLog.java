package com.vanke.common.model.task;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Field;


public class TaskLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//为了使得mongo认得这个是Id而不是ObjectId
	private int id;
	
	@Field("ObjectId")
	private String objectId;

	@Column(name="status") 
	@Field("status") 
	private int status;
	
	@Column(name="created")
	@Field("created") 
	private Date created;
	
	@Column(name="source_id")
	@Field("source_id") 
	private int sourceId;
	
	@Column(name="rate")
	@Field("rate") 
	private int rate;
	
	@Column(name="task_no")
	@Field("task_no")
	private String taskNo;
	
	@Column(name="event")
	@Field("event")
	private String event;
	
	@Column(name="score")
	@Field("score")
	private String score;
	
	@Column(name="msg")
	@Field("msg")
	private String msg;

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
	
	
	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

}
