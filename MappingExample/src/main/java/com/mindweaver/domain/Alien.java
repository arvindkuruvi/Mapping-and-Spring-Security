package com.mindweaver.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Alien {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String alien;
	
	@ManyToOne
	@JsonIgnore
	private Department dept;

	public Alien(int id, String alien, Department dept) {
		super();
		this.id = id;
		this.alien = alien;
		this.dept = dept;
	}

	public Alien() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Alien [id=" + id + ", alien=" + alien + ", dept=" + dept + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAlien() {
		return alien;
	}

	public void setAlien(String alien) {
		this.alien = alien;
	}

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}
	
	
}
