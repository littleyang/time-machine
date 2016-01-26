package com.vanke.common.exceptions;

public class BaseDaoException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2280788613889100855L;
	
	
	private int code;
	private String message;
	
	public BaseDaoException(){
		super();
	}
	
	public BaseDaoException(int code){
		this.code = code;
	}
	
	public BaseDaoException(String message){
		this.message = message;
	}
	
	public BaseDaoException(int code,String message){
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
