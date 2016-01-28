package com.vanke.common.model.task;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.mongodb.core.mapping.Field;

import com.vanke.common.model.base.BaseModel;


@Entity
@Table(name="task")
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
	
	@Column(name="task_no",unique=true)
	private String taskNo;
	
	@Column(name="business_type")
	private String businessType;
	
	@Column(name="status")
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	

}
