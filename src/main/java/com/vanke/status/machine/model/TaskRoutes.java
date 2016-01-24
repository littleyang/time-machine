package com.vanke.status.machine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="task_routes") 
public class TaskRoutes {
	
	@Id
	private int id;
	
	@Column(name="bussiness_code")
	private String bussinessCode;
	
	@Column(name="current_event")
	private String currentEvent;
	
	@Column(name="current_status")
	private int currentStatus;
	
	@Column(name="next_event")
	private String nextEvent;
	
	@Column(name="next_status")
	private int nextStatus;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBussinessCode() {
		return bussinessCode;
	}
	public void setBussinessCode(String bussinessCode) {
		this.bussinessCode = bussinessCode;
	}
	public String getCurrentEvent() {
		return currentEvent;
	}
	public void setCurrentEvent(String currentEvent) {
		this.currentEvent = currentEvent;
	}
	public int getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(int currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getNextEvent() {
		return nextEvent;
	}
	public void setNextEvent(String nextEvent) {
		this.nextEvent = nextEvent;
	}
	public int getNextStatus() {
		return nextStatus;
	}
	public void setNextStatus(int nextStatus) {
		this.nextStatus = nextStatus;
	}
	

}
