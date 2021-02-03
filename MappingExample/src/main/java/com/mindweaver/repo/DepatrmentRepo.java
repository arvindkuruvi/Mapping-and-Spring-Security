package com.mindweaver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindweaver.domain.Department;

@Repository
public interface DepatrmentRepo extends JpaRepository<Department, Integer>{
	
	Department findByDeptName(String name);

}
