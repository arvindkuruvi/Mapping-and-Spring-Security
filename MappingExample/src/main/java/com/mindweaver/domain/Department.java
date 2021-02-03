package com.mindweaver.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "department")
public class Department {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "dept_name" , unique = true ,nullable = false)
	private String deptName;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "dept",fetch = FetchType.LAZY)
	private Set<Emp> emp;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "dept",fetch = FetchType.LAZY)
	private Set<Alien> alien;

	public Department(int id, String deptName, Set<Emp> emp, Set<Alien> alien) {
		super();
		this.id = id;
		this.deptName = deptName;
		this.emp = emp;
		this.alien = alien;
	}

	public Department() {	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Set<Emp> getEmp() {
		return emp;
	}

	public void setEmp(Set<Emp> emp) {
		this.emp = emp;
	}

	public Set<Alien> getAlien() {
		return alien;
	}

	public void setAlien(Set<Alien> alien) {
		this.alien = alien;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", deptName=" + deptName + ", emp=" + emp + "]";
	}

	
}
