package com.vanke.common.model.task;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Field;

import com.vanke.common.model.base.BaseModel;


@Entity
public class Task extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3453343503574602011L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//为了使得mongo认得这个是Id而不是ObjectId
	@Field("id")
	private int id;
	
	@Column(name="task_no")
	private String taskNo;
	
	@Column(name="business_type")
	private String businessType;
	
	@Column(name="status")
	private int status;
	
	

}
