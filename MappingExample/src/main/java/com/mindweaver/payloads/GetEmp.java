package com.mindweaver.payloads;

public class GetEmp 
{
	private String empName;
	
	private String deptName;

	public GetEmp(String empName, String deptName) {
		super();
		this.empName = empName;
		this.deptName = deptName;
	}

	public GetEmp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Override
	public String toString() {
		return "GetEmp [empName=" + empName + ", deptName=" + deptName + "]";
	}
	
	
}
