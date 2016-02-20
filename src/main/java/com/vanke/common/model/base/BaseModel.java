package com.vanke.common.model.base;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("base_type")
	public String baseType;

	public String getBaseType() {
		return baseType;
	}

	public void setBaseType(String baseType) {
		this.baseType = baseType;
	}

}
