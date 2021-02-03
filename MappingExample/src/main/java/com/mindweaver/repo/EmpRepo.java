package com.mindweaver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindweaver.domain.Emp;

@Repository
public interface EmpRepo extends JpaRepository<Emp	, Integer> {
	
	Emp findByEmpName(String name);
}
