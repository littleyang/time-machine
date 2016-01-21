package com.vanke.status.machine.model;

public class TaskRoutes {
	
	private int id;
	private String bussinessCode;
	private String currentEvent;
	private int currentStatus;
	private String nextEvent;
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
