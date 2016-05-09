package com.vanke.common.model.task;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Field;


public class TaskLogCommon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//为了使得mongo认得这个是Id而不是ObjectId
	@Field("ObjectId")
	private long id;
	
	@Column(name="status") 
	private int status;
	
	@Column(name="created") 
	private String created;
	
	@Column(name="source_id") 
	private int sourceId;
	
	@Column(name="rate") 
	private int rate;
	
	@Column(name="task_no") 
	private int taskNo;
	
	@Column(name="event") 
	private String event;
	
	@Column(name="score") 
	private String score;
	
	@Column(name="msg") 
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

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
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

	public int getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(int taskNo) {
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
