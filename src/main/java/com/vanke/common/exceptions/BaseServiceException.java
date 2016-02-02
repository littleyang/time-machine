package com.vanke.common.exceptions;

public class BaseServiceException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2280788613889100855L;
	
	
	private int code;
	private String msg;
	
	public BaseServiceException(){
		super();
	}
	
	public BaseServiceException(int code){
		this.code = code;
	}
	
	public BaseServiceException(String msg){
		this.msg = msg;
	}
	
	public BaseServiceException(int code,String msg){
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
