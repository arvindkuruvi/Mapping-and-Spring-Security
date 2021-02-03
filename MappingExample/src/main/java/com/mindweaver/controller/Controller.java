package com.mindweaver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindweaver.domain.Alien;
import com.mindweaver.domain.Department;
import com.mindweaver.domain.Emp;
import com.mindweaver.payloads.GetEmp;
import com.mindweaver.repo.AlienRepo;
import com.mindweaver.repo.DepatrmentRepo;
import com.mindweaver.repo.EmpRepo;

@RestController
@RequestMapping("/")
public class Controller {

	@Autowired
	EmpRepo empRepo;

	@Autowired
	DepatrmentRepo deptRepo;
	
	@Autowired
	AlienRepo alienRepo;
	
	@PostMapping("/alien")
	public String addAlien(@RequestBody GetEmp e) {
		System.out.println(e.getEmpName() + " " + e.getDeptName());

		if (deptRepo.findByDeptName(e.getDeptName()) != null )
		{
			Alien alien = new Alien();
			alien.setAlien(e.getEmpName());
			alien.setDept(deptRepo.findByDeptName(e.getDeptName()));
			
			alienRepo.save(alien);
			return "Saved Successfully";
		}
		else {
			return "Error Occured";
		}
	}
	

	@PostMapping("/emp")
	public String addEmp(@RequestBody GetEmp e) {
		System.out.println(e.getEmpName() + " " + e.getDeptName());

		if (deptRepo.findByDeptName(e.getDeptName()) != null && empRepo.findByEmpName(e.getEmpName()) == null) {
			Emp emp = new Emp();
			emp.setDept(deptRepo.findByDeptName(e.getDeptName()));
			emp.setEmpName(e.getEmpName());
			empRepo.save(emp);
			return "Saved Successfully";
		} else {
			return "Error Occured";
		}
	}

	@PostMapping("/dept")
	public String addDept(@RequestBody GetEmp d) {
		System.out.println(d.getDeptName());
		if (deptRepo.findByDeptName(d.getDeptName()) == null) {
			Department dept = new Department();
			dept.setDeptName(d.getDeptName());
			deptRepo.save(dept);
			return "Saved Successfully";
		} else {
			return "Error Occured";
		}

	}

	@GetMapping("/dept")
	public List<Department> getDept() {
		return deptRepo.findAll();
	}

	@GetMapping("/emp")
	public List<Emp> getEmp() {
		return empRepo.findAll();
	}

	@DeleteMapping("/dept")
	public String delDept(@RequestBody GetEmp d)
	{
		try
		{
			if(deptRepo.findByDeptName(d.getDeptName()) != null)
			{
				deptRepo.delete(deptRepo.findByDeptName(d.getDeptName()));
				return "successfully deleted";
			}
			else
			{
				return "No data to delete!";
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();	
		}
	}

}
