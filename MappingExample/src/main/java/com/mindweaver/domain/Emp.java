package com.mindweaver.domain;

import java.util.HashMap;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "emp")
public class Emp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "emp_name" , unique = true)
	private String empName;

//	@OneToMany
//	@JoinTable(name = "emp_dept" , joinColumns = @JoinColumn(name = "emp_id") , inverseJoinColumns = @JoinColumn(name = "dept_id"))

	@ManyToOne
	@JsonIgnore
	private Department dept;

	public Emp(int id, String empName, Department dept) {
		super();
		this.id = id;
		this.empName = empName;
		this.dept = dept;
	}

	public Emp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	@Override
	public String toString() {
		return "Emp [id=" + id + ", empName=" + empName + ", dept=" + dept + "]";
	}

}
