package com.vanke.status.machine.dao.crud;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vanke.common.model.task.BussinessType;


@Repository("taskBusinessCrudDao")
public interface TaskBusinessCrudDao extends CrudRepository<BussinessType,Integer>{
	
	@Query("from BussinessType where id = :id")
	public BussinessType findById(@Param("id")int id);
	
	@Query("from BussinessType")
	public int getAllBussinessTypeCount();
}
