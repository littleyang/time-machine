package com.vanke.common.data.vo;

import java.util.List;

import com.vanke.common.model.task.Task;
import com.vanke.status.machine.model.TaskEvents;

/**
 * 主要展示当前的Task数据快照,包括各种task的元数据以及操作
 * @author yangyang
 *
 */
public class TaskSnapshot {
	
	private Task task;
	
	private List<TaskEvents> operations;
	
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public List<TaskEvents> getOperations() {
		return operations;
	}

	public void setOperations(List<TaskEvents> operations) {
		this.operations = operations;
	}

}
