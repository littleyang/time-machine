package com.vanke.status.machine.model.base;

import java.io.Serializable;

public class BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public String baseType;

	public String getBaseType() {
		return baseType;
	}

	public void setBaseType(String baseType) {
		this.baseType = baseType;
	}

}
